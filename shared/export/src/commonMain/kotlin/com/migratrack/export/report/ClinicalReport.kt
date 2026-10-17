package com.migratrack.export.report

import com.migratrack.domain.analytics.MigraineDays
import com.migratrack.domain.analytics.TriggerCount
import com.migratrack.domain.analytics.TriggerFrequency
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
    /** Possible triggers noted in the episodes of the range. */
    val triggers: TriggerFrequency,
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
    val triggers: List<String>,
)

/** Has no intake time: the app sets it to the start of the attack, so a time in the report misleads the doctor. */
data class ReportIntake(val name: String, val dose: String)

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
        names: CatalogNames,
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
            rangeTitle = names.range(options.range),
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
                    diagnoses = profile.diagnoses.sortedBy { it.ordinal }.map(names::diagnosis),
                    frequency = names.frequency(profile.frequency),
                    typicalDuration = names.duration(profile.typicalDuration),
                    typicalIntensity = profile.typicalIntensity.value,
                    triggers = triggers.filter { it.id in profile.triggerIds }.map { names.trigger(it.id, it.name) },
                    treatments = medications.filter { it.id in profile.medicationIds }.map { medication ->
                        val intake = names.intake(medication.id, medication.name, medication.dose)
                        "${intake.name} ${intake.dose}".trim()
                    },
                )
            },
            episodes = inRange.map { episode ->
                ReportEpisode(
                    startedAt = episode.startedAt.toLocalDateTime(timeZone),
                    intensity = episode.intensity.value,
                    region = episode.region?.let(names::region),
                    symptoms = episode.symptoms.sortedBy { it.ordinal }.map(names::symptom),
                    medications = episode.medications.map { names.intake(it.medicationId, it.name, it.dose) },
                    triggers = episode.triggers.map { names.trigger(it.triggerId, it.name) },
                )
            },
            triggers = TriggerFrequency.of(inRange).named(names),
            days = generateSequence(from) { it.plus(DatePeriod(days = 1)) }
                .takeWhile { it <= today }
                .map { ReportDay(it, days.peak(it)?.value, days.isTracked(it)) }
                .toList(),
        )
    }

    private fun CatalogNames.intake(id: String, name: String, dose: String) =
        ReportIntake(medicationName(id, name), medicationDose(id, dose))

    /** Shows the names in the language of the report, and orders equal counts by those names. */
    private fun TriggerFrequency.named(names: CatalogNames) = copy(
        counts = counts
            .map { it.copy(name = names.trigger(it.triggerId, it.name)) }
            .sortedWith(compareByDescending<TriggerCount> { it.episodes }.then { a, b -> names.compare(a.name, b.name) }),
    )
}
