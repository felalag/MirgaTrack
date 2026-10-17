package com.migratrack.export

import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.Diagnosis
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.ExportPreferences
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.MedicationCatalog
import com.migratrack.domain.model.MedicationIntake
import com.migratrack.domain.model.NotedTrigger
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.model.TriggerCatalog
import com.migratrack.domain.model.UserProfile
import com.migratrack.export.report.ClinicalReport
import com.migratrack.export.report.ClinicalReportBuilder
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

val TODAY = LocalDate(2026, 10, 24)

fun episode(
    day: Int,
    month: Int = 10,
    intensity: Int = 6,
    withMedication: Boolean = true,
    triggers: List<String> = listOf("caffeine"),
): Episode {
    val startedAt = LocalDateTime(2026, month, day, 9, 30).toInstant(TimeZone.UTC)
    val ibuprofen = MedicationCatalog.defaults.first()
    return Episode(
        id = EpisodeId("$month-$day"),
        startedAt = startedAt,
        intensity = PainIntensity(intensity),
        region = CranialRegion.LeftTemple,
        symptoms = setOf(Symptom.Photophobia, Symptom.Nausea),
        medications = if (withMedication) listOf(MedicationIntake(ibuprofen.id, ibuprofen.name, ibuprofen.dose, startedAt)) else emptyList(),
        triggers = triggers.map { id -> TriggerCatalog.defaults.first { it.id == id }.let { NotedTrigger(it.id, it.name) } },
        source = LogSource.App,
    )
}

fun report(
    episodes: List<Episode>,
    options: ExportPreferences = ExportPreferences(),
    name: String = "Аня Морозова",
    trackedSince: LocalDate = LocalDate(2026, 1, 3),
): ClinicalReport = ClinicalReportBuilder.build(
    episodes = episodes,
    baseline = BaselineProfile.Initial.copy(
        diagnoses = setOf(Diagnosis.ChronicMigraine),
        triggerIds = setOf("caffeine"),
        medicationIds = setOf("ibuprofen-400", "riboflavin-400"),
    ),
    triggers = TriggerCatalog.defaults,
    medications = MedicationCatalog.defaults,
    user = UserProfile(name, trackedSince),
    options = options,
    today = TODAY,
    timeZone = TimeZone.UTC,
    names = EnglishCatalogNames,
)
