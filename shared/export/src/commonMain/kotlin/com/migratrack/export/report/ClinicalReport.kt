package com.migratrack.export.report

import com.migratrack.domain.analytics.MigraineDays
import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.ExportPreferences
import com.migratrack.domain.model.Medication
import com.migratrack.domain.model.Trigger
import com.migratrack.domain.model.UserProfile
import com.migratrack.domain.model.trackedSince
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
    /** The first tracked day. It can be before [from]. */
    val trackedSince: LocalDate,
    val rangeTitle: String,
    val options: ExportPreferences,
    val stats: ReportStats,
    val baseline: ReportBaseline?,
    val episodes: List<ReportEpisode>,
    val days: List<ReportDay>,
)

/** One day of the range. A tracked day without a [peak] is pain-free. */
data class ReportDay(val date: LocalDate, val peak: Int?, val isTracked: Boolean)

data class ReportStats(
    val episodes: Int,
    val migraineDays: Int,
    val painFreeDays: Int,
    /** Days of the range that are not tracked: neither migraine days nor pain-free days. */
    val untrackedDays: Int,
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
        val trackedSince = user.trackedSince(today)
        val days = MigraineDays(inRange, timeZone, trackedSince)
        val migraineDays = days.migraineDaysIn(from..today)
        val trackedDays = days.trackedDaysIn(from..today)

        return ClinicalReport(
            patientName = user?.name.orEmpty(),
            generatedOn = today,
            from = from,
            to = today,
            trackedSince = trackedSince,
            rangeTitle = options.range.title,
            options = options,
            stats = ReportStats(
                episodes = inRange.size,
                migraineDays = migraineDays,
                painFreeDays = trackedDays - migraineDays,
                untrackedDays = options.range.days - trackedDays,
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
                )
            },
            days = generateSequence(from) { it.plus(DatePeriod(days = 1)) }
                .takeWhile { it <= today }
                .map { ReportDay(it, days.peak(it)?.value, days.isTracked(it)) }
                .toList(),
        )
    }
}
