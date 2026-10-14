package com.migratrack.sync

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.migratrack.data.LocalStore
import com.migratrack.data.db.MigraTrackDatabase
import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.NotedTrigger
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.UserProfile
import com.migratrack.domain.usecase.GetLoggingPreset
import com.migratrack.domain.usecase.LogEpisode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant

class SyncTest {
    private var now = Instant.parse("2026-10-24T09:00:00Z")
    private val clock = object : Clock { override fun now() = now }

    private val phone = store("phone")
    private val watch = store("watch")
    private val phoneSync = PhoneSync(
        phone.episodes, phone.profiles, phone.catalog,
        GetLoggingPreset(phone.profiles, phone.catalog), phone.documents, phone.eraser, clock,
    )
    private val watchSync = WatchSync(watch.episodes, watch.outbox, watch.documents)

    @BeforeTest
    fun setUp() = runTest {
        onboardPhone()
        watchSync.applyContext(phoneSync.context())
    }

    @Test
    fun watchTakesThePresetAndRecentEpisodesFromThePhone() = runTest {
        phone.profiles.saveBaseline(
            BaselineProfile.Initial.copy(typicalIntensity = PainIntensity(6), medicationIds = setOf("ibuprofen-400")),
        )
        val recent = phoneLog(daysAgo = 3)
        phoneLog(daysAgo = 90)

        watchSync.applyContext(phoneSync.context())

        assertEquals(listOf(recent.id), watch.episodes.observeAll().first().map { it.id })
        val preset = watchSync.preset()
        assertEquals(PainIntensity(6), preset.intensity)
        assertEquals(listOf("Ibuprofen 400mg"), preset.medications.map { it.displayName })
        assertTrue(watchSync.isLinked())
    }

    @Test
    fun triggersAddedOnThePhoneReachTheWatchMirrorAndWatchLogsHaveNone() = runTest {
        phone.profiles.saveBaseline(BaselineProfile.Initial.copy(triggerIds = setOf("poor-sleep")))
        watchSync.applyContext(phoneSync.context())
        val logged = watchLog()
        assertEquals(emptyList(), logged.triggers)
        phoneSync.receive(assertNotNullBatch())

        val noted = listOf(NotedTrigger("poor-sleep", "Poor / Irregular Sleep"))
        phone.episodes.add(phone.episodes.find(logged.id)!!.copy(triggers = noted))
        watchSync.applyContext(phoneSync.context())

        assertEquals(noted, watch.episodes.find(logged.id)?.triggers)
    }

    @Test
    fun episodeFromAnOlderBuildWithoutTriggersStillDecodes() {
        val payload = """{"id":"old","startedAt":1761300000000,"intensity":5,"source":"QuickLog"}"""
        assertEquals(emptyList(), SyncJson.decodeFromString(SyncEpisode.serializer(), payload).toDomain()?.triggers)
    }

    @Test
    fun watchTakesTheTrackingStartFromThePhone() = runTest {
        assertNull(watchSync.trackedSince())

        phone.profiles.saveUser(UserProfile(name = "", memberSince = LocalDate(2026, 10, 20)))
        watchSync.applyContext(phoneSync.contexts().first())

        assertEquals(LocalDate(2026, 10, 20), watchSync.trackedSince())
    }

    @Test
    fun watchDraftKeepsThePresetMedicationsAndTheScale() = runTest {
        phone.profiles.saveBaseline(
            BaselineProfile.Initial.copy(typicalIntensity = PainIntensity(6), medicationIds = setOf("ibuprofen-400")),
        )
        watchSync.applyContext(phoneSync.context())

        val draft = watchSync.draft(intensity = 9)
        assertEquals(PainIntensity(9), draft.intensity)
        assertEquals(listOf("Ibuprofen 400mg"), draft.medications.map { it.displayName })
        assertEquals(PainIntensity(10), watchSync.draft(intensity = 12).intensity)
        assertEquals(PainIntensity(1), watchSync.draft(intensity = 0).intensity)
    }

    @Test
    fun episodeLoggedOnTheWatchReachesThePhoneOnce() = runTest {
        val logged = watchLog(intensity = 8)
        val batch = assertNotNullBatch()

        assertEquals(1, phoneSync.receive(batch))
        assertEquals(0, phoneSync.receive(batch))
        val received = phone.episodes.observeAll().first().single { it.id == logged.id }
        assertEquals(PainIntensity(8), received.intensity)
        assertEquals(LogSource.App, received.source)

        watchSync.markDelivered(batch)
        watchSync.applyContext(phoneSync.context())
        assertNull(watchSync.pendingBatch())
    }

    @Test
    fun watchKeepsAnEpisodeUntilThePhoneContextConfirmsIt() = runTest {
        // The transfer reports success, but the phone app has not stored the episode yet.
        val logged = watchLog()
        watchSync.markDelivered(assertNotNullBatch())
        watchSync.applyContext(phoneSync.context())

        assertEquals(listOf(logged.id), watch.episodes.observeAll().first().map { it.id })
        assertEquals(1, phoneSync.receive(assertNotNullBatch()))
    }

    @Test
    fun contextKeepsUnsentWatchEpisodesAndDropsOnesThePhoneDeleted() = runTest {
        val shared = phoneLog(daysAgo = 2)
        watchSync.applyContext(phoneSync.context())
        val unsent = watchLog()

        phone.episodes.delete(shared.id)
        watchSync.applyContext(phoneSync.context())

        assertEquals(listOf(unsent.id), watch.episodes.observeAll().first().map { it.id })
    }

    @Test
    fun deleteRequestFromAnEarlierWatchBuildDeletesTheEpisodeOnThePhone() = runTest {
        val logged = watchLog()
        phoneSync.receive(assertNotNullBatch())

        phoneSync.receive(deleteRequest(logged))

        assertEquals(emptyList(), phone.episodes.observeAll().first())
    }

    @Test
    fun episodeDeletedOnThePhoneStaysDeletedWhenTheWatchSendsItAgain() = runTest {
        val logged = watchLog()
        phoneSync.receive(assertNotNullBatch())
        phone.episodes.delete(logged.id)

        // The context that listed the episode was replaced by a newer one before the watch woke up.
        watchSync.applyContext(phoneSync.context())
        watchSync.pendingBatch()?.let { phoneSync.receive(it) }

        assertEquals(emptyList(), phone.episodes.observeAll().first())
        assertEquals(emptyList(), watch.episodes.observeAll().first())
        assertNull(watchSync.pendingBatch())
    }

    @Test
    fun deleteRequestThatOvertakesItsEpisodeStillDeletesIt() = runTest {
        val logged = watchLog()
        val inFlight = assertNotNullBatch()

        phoneSync.receive(deleteRequest(logged))
        phoneSync.receive(inFlight)

        assertEquals(emptyList(), phone.episodes.observeAll().first())
    }

    @Test
    fun deleteRequestStoredBeforeAnUpdateIsSentUntilDelivered() = runTest {
        // A build with Undo stored this request, and the watch app was updated before it went out.
        watch.documents.write(WatchSync.DELETED, """["watch-old"]""")

        val batch = assertNotNullBatch()
        assertEquals(listOf("watch-old"), SyncJson.decodeFromString(WatchBatch.serializer(), batch).deletedIds)

        watchSync.markDelivered(batch)
        assertNull(watchSync.pendingBatch())
    }

    @Test
    fun editOnThePhoneReplacesTheWatchCopy() = runTest {
        val logged = watchLog()
        phoneSync.receive(assertNotNullBatch())
        phone.episodes.add(logged.copy(intensity = PainIntensity(9)))

        watchSync.applyContext(phoneSync.context())
        watchSync.pendingBatch()?.let { phoneSync.receive(it) }

        assertEquals(listOf(9), watch.episodes.observeAll().first().map { it.intensity.value })
        assertEquals(listOf(9), phone.episodes.observeAll().first().map { it.intensity.value })
    }

    @Test
    fun deleteAllDataOnThePhoneAlsoDeletesAnEpisodeTheWatchHasNotSent() = runTest {
        watchLog()
        now += 1.minutes
        phone.eraser.eraseAll()

        watchSync.applyContext(phoneSync.context())

        assertEquals(emptyList(), watch.episodes.observeAll().first())
        assertNull(watchSync.pendingBatch())
    }

    @Test
    fun deleteAllDataOnThePhoneAlsoDeletesAnEpisodeTheContextHasNotConfirmed() = runTest {
        watchLog()
        phoneSync.receive(assertNotNullBatch())
        now += 1.minutes
        phone.eraser.eraseAll()

        watchSync.applyContext(phoneSync.context())
        onboardPhone()
        watchSync.pendingBatch()?.let { phoneSync.receive(it) }

        assertEquals(emptyList(), phone.episodes.observeAll().first())
        assertEquals(emptyList(), watch.episodes.observeAll().first())
    }

    @Test
    fun batchSentBeforeDeleteAllDataDoesNotRestoreTheEpisode() = runTest {
        // The watch queued the batch before the phone erased, and the batch arrives after the user set up again.
        watchLog()
        val inFlight = assertNotNullBatch()
        now += 1.minutes
        phone.eraser.eraseAll()
        onboardPhone()

        assertEquals(0, phoneSync.receive(inFlight))
        assertEquals(emptyList(), phone.episodes.observeAll().first())
    }

    @Test
    fun watchDoesNotLogUntilThePhoneFinishesOnboardingAgain() = runTest {
        phone.eraser.eraseAll()
        watchSync.applyContext(phoneSync.context())

        assertFalse(watchSync.isLinked())
        assertFailsWith<IllegalStateException> { watchSync.draft(intensity = 7) }

        onboardPhone()
        watchSync.applyContext(phoneSync.context())
        now += 1.minutes
        val logged = watchLog()

        assertTrue(watchSync.isLinked())
        assertEquals(1, phoneSync.receive(assertNotNullBatch()))
        assertEquals(listOf(logged.id), phone.episodes.observeAll().first().map { it.id })
    }

    @Test
    fun episodeLoggedOnTheWatchAfterDeleteAllDataWaitsForOnboarding() = runTest {
        // The watch has not seen the context from after the erase yet, so it still logs.
        phone.eraser.eraseAll()
        now += 1.minutes
        val logged = watchLog()

        assertEquals(0, phoneSync.receive(assertNotNullBatch()))
        watchSync.applyContext(phoneSync.context())
        assertEquals(listOf(logged.id), watch.episodes.observeAll().first().map { it.id })

        onboardPhone()
        assertEquals(1, phoneSync.receive(assertNotNullBatch()))
    }

    private suspend fun onboardPhone() = phone.profiles.saveBaseline(BaselineProfile.Initial)

    private suspend fun assertNotNullBatch(): String = requireNotNull(watchSync.pendingBatch()) { "expected a batch" }

    /** What WatchCore.log does when the user saves on the watch. */
    private suspend fun watchLog(intensity: Int = 7): Episode =
        LogEpisode(watch.episodes, clock)(watchSync.draft(intensity), LogSource.App).also { watchSync.queue(it) }

    private fun deleteRequest(episode: Episode): String =
        SyncJson.encodeToString(WatchBatch.serializer(), WatchBatch(deletedIds = listOf(episode.id.value)))

    private suspend fun phoneLog(daysAgo: Int): Episode {
        val logged = LogEpisode(phone.episodes, object : Clock { override fun now() = now - daysAgo.days })
        return logged(GetLoggingPreset(phone.profiles, phone.catalog)())
    }

    private fun store(name: String) = LocalStore(
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also { MigraTrackDatabase.Schema.create(it) },
        dispatcher = Dispatchers.Unconfined,
        clock = clock,
        newId = { "$name-${counter++}" },
    )

    private var counter = 0
}
