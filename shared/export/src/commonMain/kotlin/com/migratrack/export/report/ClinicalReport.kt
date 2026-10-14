package com.migratrack.export.report

import com.migratrack.domain.analytics.MigraineDays
import com.migratrack.domain.analytics.TriggerFrequency
import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.ExportPreferences
import com.migratrack.domain.model.Medication
import com.migratrack.domain.model.Trigger
import com.migratrack.domain.model.UserProfile
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt

/** Everything an export shows, already filtered by the export toggles and the date range. */
data class ClinicalReport(
    val patientName: String,
    val generatedOn: LocalDate,
    val from: LocalDate,
    val to: LocalDate,
    val rangeTitle: String,
    val options: ExportPreferences,
    val stats: ReportStats,
    val baseline: ReportBaseline?,
    val episodes: List<ReportEpisode>,
    /** Possible triggers noted in the episodes of the range. */
    val triggers: TriggerFrequency,
    /** Peak pain per day of the range, null for a pain-free day. */
    val dailyPeaks: List<Pair<LocalDate, Int?>>,
)

data class ReportStats(
    val episodes: Int,
    val migraineDays: Int,
    val painFreeDays: Int,
    val averageIntensity: Double?,
    val medicationIntakes: Int,
)

data class ReportBaseline(
    val diagnoses: List<String>,
    val frequency: String,
    val typicalDuration: String,
    val typicalIntensity: Int,
    val triggers: List<String>,
    val treatments: List<String>,
)

data class ReportEpisode(
    val startedAt: LocalDateTime,
    val intensity: Int,
    val region: String?,
    val symptoms: List<String>,
    val medications: List<ReportIntake>,
    val triggers: List<String>,
)

data class ReportIntake(val name: String, val dose: String, val takenAt: LocalDateTime)

object ClinicalReportBuilder {

    fun build(
        episodes: List<Episode>,
        baseline: BaselineProfile?,
        triggers: List<Trigger>,
        medications: List<Medication>,
        user: UserProfile?,
        options: ExportPreferences,
        today: LocalDate,
        timeZone: TimeZone,
    ): ClinicalReport {
        val from = today.minus(DatePeriod(days = options.range.days - 1))
        val inRange = episodes
            .filter { it.startedAt.toLocalDateTime(timeZone).date in from..today }
            .sortedBy { it.startedAt }
        val days = MigraineDays(inRange, timeZone)
        val migraineDays = days.migraineDaysIn(from..today)

        return ClinicalReport(
            patientName = user?.name.orEmpty(),
            generatedOn = today,
            from = from,
            to = today,
            rangeTitle = options.range.title,
            options = options,
            stats = ReportStats(
                episodes = inRange.size,
                migraineDays = migraineDays,
                painFreeDays = options.range.days - migraineDays,
                averageIntensity = inRange.takeIf { it.isNotEmpty() }
                    ?.let { list -> (list.sumOf { it.intensity.value }.toDouble() / list.size * 10).roundToInt() / 10.0 },
                medicationIntakes = inRange.sumOf { it.medications.size },
            ),
            baseline = baseline?.let { profile ->
                ReportBaseline(
                    diagnoses = profile.diagnoses.sortedBy { it.ordinal }.map { it.title },
                    frequency = profile.frequency.title,
                    typicalDuration = profile.typicalDuration.title,
                    typicalIntensity = profile.typicalIntensity.value,
                    triggers = triggers.filter { it.id in profile.triggerIds }.map { it.name },
                    treatments = medications.filter { it.id in profile.medicationIds }.map { it.displayName },
                )
            },
            episodes = inRange.map { episode ->
                ReportEpisode(
                    startedAt = episode.startedAt.toLocalDateTime(timeZone),
                    intensity = episode.intensity.value,
                    region = episode.region?.title,
                    symptoms = episode.symptoms.sortedBy { it.ordinal }.map { it.title },
                    medications = episode.medications.map {
                        ReportIntake(it.name, it.dose, it.takenAt.toLocalDateTime(timeZone))
                    },
                    triggers = episode.triggers.map { it.name },
                )
            },
            triggers = TriggerFrequency.of(inRange),
            dailyPeaks = generateSequence(from) { it.plus(DatePeriod(days = 1)) }
                .takeWhile { it <= today }
                .map { it to days.peak(it)?.value }
                .toList(),
        )
    }
}
