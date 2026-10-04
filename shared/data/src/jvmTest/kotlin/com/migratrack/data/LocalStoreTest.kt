package com.migratrack.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.migratrack.data.db.MigraTrackDatabase
import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.Diagnosis
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.ExportPreferences
import com.migratrack.domain.model.ExportRange
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.MedicationIntake
import com.migratrack.domain.model.MedicationRole
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.model.TriggerCatalog
import com.migratrack.domain.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Instant

class LocalStoreTest {
    private val store = LocalStore(
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also { MigraTrackDatabase.Schema.create(it) },
        dispatcher = Dispatchers.Unconfined,
        newId = { "fixed" },
    )

    private val episode = Episode(
        id = EpisodeId("e1"),
        startedAt = Instant.fromEpochMilliseconds(1_761_300_000_000),
        intensity = PainIntensity(7),
        region = CranialRegion.LeftTemple,
        symptoms = setOf(Symptom.Nausea, Symptom.Photophobia),
        medications = listOf(
            MedicationIntake("ibuprofen-400", "Ibuprofen", "400mg", Instant.fromEpochMilliseconds(1_761_300_060_000)),
            MedicationIntake("electrolytes", "Hydration Electrolytes", "", Instant.fromEpochMilliseconds(1_761_300_120_000)),
        ),
        source = LogSource.QuickLog,
    )

    @Test
    fun episodeSurvivesARoundTripWithItsIntakesInOrder() = runTest {
        store.episodes.add(episode)
        assertEquals(listOf(episode), store.episodes.observeAll().first())
    }

    @Test
    fun rangeQueryIncludesTheStartAndExcludesTheEnd() = runTest {
        store.episodes.add(episode)
        val start = episode.startedAt
        assertEquals(1, store.episodes.inRange(start, Instant.fromEpochMilliseconds(start.toEpochMilliseconds() + 1)).size)
        assertEquals(0, store.episodes.inRange(Instant.fromEpochMilliseconds(0), start).size)
    }

    @Test
    fun deleteRemovesTheEpisodeAndItsIntakes() = runTest {
        store.episodes.add(episode)
        store.episodes.add(episode.copy(id = EpisodeId("e2"), medications = emptyList()))
        store.episodes.delete(EpisodeId("e1"))
        assertEquals(listOf("e2"), store.episodes.observeAll().first().map { it.id.value })
    }

    @Test
    fun profilesKeepTheirValues() = runTest {
        val baseline = BaselineProfile.Initial.copy(
            diagnoses = setOf(Diagnosis.ChronicMigraine, Diagnosis.MigraineWithAura),
            triggerIds = setOf("caffeine", "custom-trigger-x"),
            medicationIds = setOf("ibuprofen-400"),
        )
        assertNull(store.profiles.observeBaseline().first())
        store.profiles.saveBaseline(baseline)
        store.profiles.saveUser(UserProfile("Alex", LocalDate(2026, 1, 3)))
        val preferences = ExportPreferences(includeSymptoms = false, range = ExportRange.Last30Days)
        store.profiles.saveExportPreferences(preferences)

        assertEquals(baseline, store.profiles.observeBaseline().first())
        assertEquals(UserProfile("Alex", LocalDate(2026, 1, 3)), store.profiles.user())
        assertEquals(preferences, store.profiles.observeExportPreferences().first())
    }

    @Test
    fun customEntriesFollowTheBuiltInCatalog() = runTest {
        store.catalog.addCustomTrigger("  Flashing neon ")
        store.catalog.addCustomMedication("Magnesium glycinate", "200mg", MedicationRole.Preventive)

        val triggers = store.catalog.observeTriggers().first()
        assertEquals(TriggerCatalog.defaults.size + 1, triggers.size)
        assertEquals("Flashing neon", triggers.last().name)
        assertTrue(store.catalog.medications().last().isCustom)
    }

    @Test
    fun eraseAllLeavesAnEmptyStore() = runTest {
        store.episodes.add(episode)
        store.profiles.saveBaseline(BaselineProfile.Initial)
        store.catalog.addCustomTrigger("Neon")
        store.eraser.eraseAll()

        assertEquals(emptyList(), store.episodes.observeAll().first())
        assertNull(store.profiles.baseline())
        assertEquals(TriggerCatalog.defaults, store.catalog.triggers())
    }
}
