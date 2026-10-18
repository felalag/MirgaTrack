package com.migratrack.app

import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.MedicationIntake
import com.migratrack.domain.model.PainIntensity
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant
import kotlinx.coroutines.test.runTest

class QuickLogBridgeTest {
    private val now = Instant.fromEpochMilliseconds(1_792_000_000_000)

    @Test
    fun siriNamesABuiltInMedicationByItsIdAndNotByTheStoredCopy() = runTest {
        // The stored copy differs from the catalog name, as an English copy does under another app language.
        val summary = episode(MedicationIntake("ibuprofen-400", "Ibuprofen (stored copy)", "400mg", now)).quickLogSummary()

        assertEquals(6, summary.intensity)
        assertEquals(listOf("Ibuprofen 400mg"), summary.medications)
    }

    @Test
    fun siriKeepsTheWordsOfACustomMedication() = runTest {
        val summary = episode(MedicationIntake("custom-medication-1", "Eletriptan 40mg", "", now)).quickLogSummary()

        assertEquals(listOf("Eletriptan 40mg"), summary.medications)
    }

    private fun episode(intake: MedicationIntake) = Episode(
        id = EpisodeId("quick"),
        startedAt = now,
        intensity = PainIntensity(6),
        region = null,
        symptoms = emptySet(),
        medications = listOf(intake),
        triggers = emptyList(),
        source = LogSource.QuickLog,
    )
}
