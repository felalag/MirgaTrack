package com.migratrack.sync

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.migratrack.data.LocalStore
import com.migratrack.data.db.MigraTrackDatabase
import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.usecase.GetLoggingPreset
import com.migratrack.domain.usecase.LogEpisode
import com.migratrack.domain.usecase.QuickLogEpisode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

class SyncTest {
    private val now = Instant.parse("2026-10-24T09:00:00Z")
    private val clock = object : Clock { override fun now() = now }

    private val phone = store("phone")
    private val watch = store("watch")
    private val phoneSync = PhoneSync(
        phone.episodes, phone.profiles, phone.catalog,
        GetLoggingPreset(phone.profiles, phone.catalog), clock,
    )
    private val watchSync = WatchSync(watch.episodes, watch.outbox, watch.documents)
    private val watchQuickLog = QuickLogEpisode(watchSync, LogEpisode(watch.episodes, clock))

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
    fun episodeLoggedOnTheWatchReachesThePhoneOnce() = runTest {
        val logged = watchQuickLog().also { watchSync.queue(it) }
        val batch = assertNotNullBatch()

        assertEquals(1, phoneSync.receive(batch))
        assertEquals(0, phoneSync.receive(batch))
        assertEquals(LogSource.QuickLog, phone.episodes.observeAll().first().single { it.id == logged.id }.source)

        watchSync.markDelivered(batch)
        watchSync.applyContext(phoneSync.context())
        assertNull(watchSync.pendingBatch())
    }

    @Test
    fun watchKeepsAnEpisodeUntilThePhoneContextConfirmsIt() = runTest {
        // The transfer reports success, but the phone app has not stored the episode yet.
        val logged = watchQuickLog().also { watchSync.queue(it) }
        watchSync.markDelivered(assertNotNullBatch())
        watchSync.applyContext(phoneSync.context())

        assertEquals(listOf(logged.id), watch.episodes.observeAll().first().map { it.id })
        assertEquals(1, phoneSync.receive(assertNotNullBatch()))
    }

    @Test
    fun contextKeepsUnsentWatchEpisodesAndDropsOnesThePhoneDeleted() = runTest {
        val shared = phoneLog(daysAgo = 2)
        watchSync.applyContext(phoneSync.context())
        val unsent = watchQuickLog().also { watchSync.queue(it) }

        phone.episodes.delete(shared.id)
        watchSync.applyContext(phoneSync.context())

        assertEquals(listOf(unsent.id), watch.episodes.observeAll().first().map { it.id })
    }

    @Test
    fun undoBeforeConfirmationStillReachesThePhone() = runTest {
        // The first batch may already be on its way when the user taps Undo.
        val logged = watchQuickLog().also { watchSync.queue(it) }
        val inFlight = assertNotNullBatch()
        watchSync.undo(logged.id)
        val undo = assertNotNullBatch()

        phoneSync.receive(inFlight)
        phoneSync.receive(undo)

        assertEquals(emptyList(), watch.episodes.observeAll().first())
        assertEquals(emptyList(), phone.episodes.observeAll().first())
    }

    @Test
    fun undoAfterSendingDeletesTheEpisodeOnThePhone() = runTest {
        val logged = watchQuickLog().also { watchSync.queue(it) }
        val first = assertNotNullBatch()
        phoneSync.receive(first)
        watchSync.markDelivered(first)

        watchSync.undo(logged.id)
        val second = assertNotNullBatch()
        phoneSync.receive(second)
        watchSync.markDelivered(second)

        assertEquals(emptyList(), phone.episodes.observeAll().first())
        assertNull(watchSync.pendingBatch())
    }

    private suspend fun assertNotNullBatch(): String = requireNotNull(watchSync.pendingBatch()) { "expected a batch" }

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
