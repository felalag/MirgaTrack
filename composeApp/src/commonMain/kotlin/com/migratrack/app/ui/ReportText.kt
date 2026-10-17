package com.migratrack.app.ui

import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.allPluralStringResources
import com.migratrack.app.generated.resources.allStringResources
import com.migratrack.app.generated.resources.report_baseline_diagnoses
import com.migratrack.app.generated.resources.report_baseline_duration
import com.migratrack.app.generated.resources.report_baseline_frequency
import com.migratrack.app.generated.resources.report_baseline_intensity
import com.migratrack.app.generated.resources.report_baseline_title
import com.migratrack.app.generated.resources.report_baseline_treatments
import com.migratrack.app.generated.resources.report_baseline_triggers
import com.migratrack.app.generated.resources.report_column_date_time
import com.migratrack.app.generated.resources.report_column_epicenter
import com.migratrack.app.generated.resources.report_column_medications
import com.migratrack.app.generated.resources.report_column_pain
import com.migratrack.app.generated.resources.report_column_symptoms
import com.migratrack.app.generated.resources.report_column_triggers
import com.migratrack.app.generated.resources.report_day_migraine
import com.migratrack.app.generated.resources.report_day_not_tracked
import com.migratrack.app.generated.resources.report_day_pain_free
import com.migratrack.app.generated.resources.report_disclaimer
import com.migratrack.app.generated.resources.report_episode_count
import com.migratrack.app.generated.resources.report_episodes_title
import com.migratrack.app.generated.resources.report_generated
import com.migratrack.app.generated.resources.report_header_date
import com.migratrack.app.generated.resources.report_header_day
import com.migratrack.app.generated.resources.report_header_dose
import com.migratrack.app.generated.resources.report_header_epicenter
import com.migratrack.app.generated.resources.report_header_episode_started
import com.migratrack.app.generated.resources.report_header_field
import com.migratrack.app.generated.resources.report_header_medication
import com.migratrack.app.generated.resources.report_header_medications
import com.migratrack.app.generated.resources.report_header_pain_intensity
import com.migratrack.app.generated.resources.report_header_peak_pain
import com.migratrack.app.generated.resources.report_header_started
import com.migratrack.app.generated.resources.report_header_symptoms
import com.migratrack.app.generated.resources.report_header_triggers
import com.migratrack.app.generated.resources.report_header_value
import com.migratrack.app.generated.resources.report_intakes
import com.migratrack.app.generated.resources.report_intensity_of_ten
import com.migratrack.app.generated.resources.report_iso_range
import com.migratrack.app.generated.resources.report_legend_mild
import com.migratrack.app.generated.resources.report_legend_moderate
import com.migratrack.app.generated.resources.report_legend_not_tracked
import com.migratrack.app.generated.resources.report_legend_severe
import com.migratrack.app.generated.resources.report_medication_title
import com.migratrack.app.generated.resources.report_no_episodes
import com.migratrack.app.generated.resources.report_no_medications
import com.migratrack.app.generated.resources.report_no_symptoms
import com.migratrack.app.generated.resources.report_no_triggers
import com.migratrack.app.generated.resources.report_none
import com.migratrack.app.generated.resources.report_not_set
import com.migratrack.app.generated.resources.report_page
import com.migratrack.app.generated.resources.report_pain_chart_title
import com.migratrack.app.generated.resources.report_patient
import com.migratrack.app.generated.resources.report_profile_diagnoses
import com.migratrack.app.generated.resources.report_profile_duration
import com.migratrack.app.generated.resources.report_profile_frequency
import com.migratrack.app.generated.resources.report_profile_intensity
import com.migratrack.app.generated.resources.report_profile_patient
import com.migratrack.app.generated.resources.report_profile_range
import com.migratrack.app.generated.resources.report_profile_tracked_since
import com.migratrack.app.generated.resources.report_profile_treatments
import com.migratrack.app.generated.resources.report_profile_triggers
import com.migratrack.app.generated.resources.report_range_with_title
import com.migratrack.app.generated.resources.report_share_of_episodes
import com.migratrack.app.generated.resources.report_sheet_daily_pain
import com.migratrack.app.generated.resources.report_sheet_episodes
import com.migratrack.app.generated.resources.report_sheet_medications
import com.migratrack.app.generated.resources.report_sheet_profile
import com.migratrack.app.generated.resources.report_stat_average_intensity
import com.migratrack.app.generated.resources.report_stat_episodes
import com.migratrack.app.generated.resources.report_stat_migraine_days
import com.migratrack.app.generated.resources.report_stat_pain_free_days
import com.migratrack.app.generated.resources.report_symptoms_title
import com.migratrack.app.generated.resources.report_title
import com.migratrack.app.generated.resources.report_triggers_note
import com.migratrack.app.generated.resources.report_triggers_title
import com.migratrack.app.generated.resources.report_untracked_note
import com.migratrack.app.generated.resources.report_without_triggers
import com.migratrack.export.format.NumberFormats
import com.migratrack.export.report.ReportStrings
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getPluralString
import org.jetbrains.compose.resources.getString

/** Loads every report text in the current language, so the PDF and the XLSX can use them without suspending. */
suspend fun loadReportStrings(numbers: NumberFormats = Numbers): ReportStrings {
    val reportKeys = { key: String -> key.startsWith("report_") }
    val texts = Res.allStringResources.filterKeys(reportKeys).values.associateWith { getString(it) }
    val plurals = Res.allPluralStringResources.filterKeys(reportKeys).values.associateWith { resource ->
        List(PLURAL_SAMPLES) { count -> getPluralString(resource, count) }
    }
    return ResourceReportStrings(texts, plurals, numbers)
}

// Compose resources pick a plural form only in a suspend call. The CLDR rules for whole numbers look at n % 100
// and at exact values below 100, so 100 + n % 100 has the form of n. Only a "millions" form in some languages differs.
private const val PLURAL_SAMPLES = 200

private fun pluralSample(count: Int): Int = if (count < PLURAL_SAMPLES) count else 100 + count % 100

private class ResourceReportStrings(
    private val texts: Map<StringResource, String>,
    private val plurals: Map<PluralStringResource, List<String>>,
    private val numbers: NumberFormats,
) : ReportStrings {
    private fun text(id: StringResource, vararg args: String) = fillPlaceholders(texts.getValue(id), args)

    private fun plural(id: PluralStringResource, count: Int, vararg args: String) =
        fillPlaceholders(plurals.getValue(id)[pluralSample(count)], args)

    private fun n(value: Int) = numbers.integer(value)

    override val title = text(Res.string.report_title)
    override fun page(number: Int) = text(Res.string.report_page, n(number))
    override fun patient(name: String) = text(Res.string.report_patient, name)
    override fun rangeWithTitle(range: String, title: String) = text(Res.string.report_range_with_title, range, title)
    override fun generated(date: String) = text(Res.string.report_generated, date)
    override val disclaimer = text(Res.string.report_disclaimer)

    override val statEpisodes = text(Res.string.report_stat_episodes)
    override val statMigraineDays = text(Res.string.report_stat_migraine_days)
    override val statPainFreeDays = text(Res.string.report_stat_pain_free_days)
    override val statAverageIntensity = text(Res.string.report_stat_average_intensity)
    override fun untrackedNote(trackedSince: String, days: Int) = plural(Res.plurals.report_untracked_note, days, trackedSince, n(days))

    override val baselineTitle = text(Res.string.report_baseline_title)
    override val baselineDiagnoses = text(Res.string.report_baseline_diagnoses)
    override val baselineFrequency = text(Res.string.report_baseline_frequency)
    override val baselineDuration = text(Res.string.report_baseline_duration)
    override val baselineIntensity = text(Res.string.report_baseline_intensity)
    override val baselineTriggers = text(Res.string.report_baseline_triggers)
    override val baselineTreatments = text(Res.string.report_baseline_treatments)
    override val painChartTitle = text(Res.string.report_pain_chart_title)
    override fun legendMild(from: Int, to: Int) = text(Res.string.report_legend_mild, n(from), n(to))
    override fun legendModerate(from: Int, to: Int) = text(Res.string.report_legend_moderate, n(from), n(to))
    override fun legendSevere(from: Int, to: Int) = text(Res.string.report_legend_severe, n(from), n(to))
    override val legendNotTracked = text(Res.string.report_legend_not_tracked)
    override val medicationTitle = text(Res.string.report_medication_title)
    override val noMedications = text(Res.string.report_no_medications)
    override fun intakes(count: Int) = plural(Res.plurals.report_intakes, count, n(count))
    override val symptomsTitle = text(Res.string.report_symptoms_title)
    override val noSymptoms = text(Res.string.report_no_symptoms)
    override fun shareOfEpisodes(count: Int, episodes: Int, percent: Int) =
        plural(Res.plurals.report_share_of_episodes, episodes, n(count), n(episodes), numbers.percent(percent))
    override val triggersTitle = text(Res.string.report_triggers_title)
    override val noTriggers = text(Res.string.report_no_triggers)
    override val triggersNote = text(Res.string.report_triggers_note)
    override fun withoutTriggers(withoutTriggers: Int, episodes: Int) = plural(
        Res.plurals.report_without_triggers,
        withoutTriggers,
        n(withoutTriggers),
        plural(Res.plurals.report_episode_count, episodes, n(episodes)),
    )
    override val episodesTitle = text(Res.string.report_episodes_title)
    override val noEpisodes = text(Res.string.report_no_episodes)

    override val columnDateTime = text(Res.string.report_column_date_time)
    override val columnPain = text(Res.string.report_column_pain)
    override val columnEpicenter = text(Res.string.report_column_epicenter)
    override val columnSymptoms = text(Res.string.report_column_symptoms)
    override val columnMedications = text(Res.string.report_column_medications)
    override val columnTriggers = text(Res.string.report_column_triggers)

    override fun intensityOfTen(intensity: Int) = text(Res.string.report_intensity_of_ten, n(intensity))
    override val notSet = text(Res.string.report_not_set)
    override val none = text(Res.string.report_none)

    override val sheetEpisodes = text(Res.string.report_sheet_episodes)
    override val sheetMedications = text(Res.string.report_sheet_medications)
    override val sheetDailyPain = text(Res.string.report_sheet_daily_pain)
    override val sheetProfile = text(Res.string.report_sheet_profile)

    override val headerStarted = text(Res.string.report_header_started)
    override val headerPainIntensity = text(Res.string.report_header_pain_intensity)
    override val headerEpicenter = text(Res.string.report_header_epicenter)
    override val headerSymptoms = text(Res.string.report_header_symptoms)
    override val headerMedications = text(Res.string.report_header_medications)
    override val headerTriggers = text(Res.string.report_header_triggers)
    override val headerEpisodeStarted = text(Res.string.report_header_episode_started)
    override val headerMedication = text(Res.string.report_header_medication)
    override val headerDose = text(Res.string.report_header_dose)
    override val headerDate = text(Res.string.report_header_date)
    override val headerPeakPain = text(Res.string.report_header_peak_pain)
    override val headerDay = text(Res.string.report_header_day)
    override val headerField = text(Res.string.report_header_field)
    override val headerValue = text(Res.string.report_header_value)

    override val dayMigraine = text(Res.string.report_day_migraine)
    override val dayPainFree = text(Res.string.report_day_pain_free)
    override val dayNotTracked = text(Res.string.report_day_not_tracked)
    override val profilePatient = text(Res.string.report_profile_patient)
    override val profileRange = text(Res.string.report_profile_range)
    override val profileTrackedSince = text(Res.string.report_profile_tracked_since)
    override val profileDiagnoses = text(Res.string.report_profile_diagnoses)
    override val profileFrequency = text(Res.string.report_profile_frequency)
    override val profileDuration = text(Res.string.report_profile_duration)
    override val profileIntensity = text(Res.string.report_profile_intensity)
    override val profileTriggers = text(Res.string.report_profile_triggers)
    override val profileTreatments = text(Res.string.report_profile_treatments)
    override fun isoRange(from: String, to: String) = text(Res.string.report_iso_range, from, to)
}
