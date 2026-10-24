package com.migratrack.export

import com.migratrack.export.report.ReportStrings

/** The English report texts of version 1.0, so the report tests keep their text. */
object EnglishReportStrings : ReportStrings {
    override val rightToLeft = false
    override val listSeparator = ", "
    override fun uppercase(text: String) = text.uppercase()

    override val title = "Clinical Migraine Report"
    override fun page(number: Int) = "MigraTrack · Page $number"
    override fun patient(name: String) = "Patient: $name"
    override fun rangeWithTitle(range: String, title: String) = "$range ($title)"
    override fun generated(date: String) = "Generated $date"
    override val disclaimer = "Self-reported data logged in MigraTrack. This report supports, and does not replace, a clinical assessment."

    override val statEpisodes = "Episodes recorded"
    override val statMigraineDays = "Migraine days"
    override val statPainFreeDays = "Pain-free days"
    override val statAverageIntensity = "Average intensity /10"
    override fun untrackedNote(trackedSince: String, days: Int) =
        if (days == 1) {
            "Tracking started on $trackedSince. 1 earlier day of this range has no records and does not count as a pain-free day."
        } else {
            "Tracking started on $trackedSince. $days earlier days of this range have no records and do not count as pain-free days."
        }

    override val baselineTitle = "Baseline Profile"
    override val baselineDiagnoses = "Diagnoses"
    override val baselineFrequency = "Monthly frequency"
    override val baselineDuration = "Typical duration"
    override val baselineIntensity = "Typical intensity"
    override val baselineTriggers = "Known triggers"
    override val baselineTreatments = "Treatment blueprint"
    override val painChartTitle = "Pain Intensity Timeline (1–10)"
    override fun legendMild(from: Int, to: Int) = "Mild $from–$to"
    override fun legendModerate(from: Int, to: Int) = "Moderate $from–$to"
    override fun legendSevere(from: Int, to: Int) = "Severe $from–$to"
    override val legendNotTracked = "Not tracked"
    override val medicationTitle = "Medication Use"
    override val noMedications = "No medication intakes logged in this range."
    override fun intakes(count: Int) = "$count ${if (count == 1) "intake" else "intakes"}"
    override val symptomsTitle = "Aura & Warning Symptoms"
    override val noSymptoms = "No aura or sensory symptoms logged in this range."
    override fun shareOfEpisodes(count: Int, episodes: Int, percent: Int) =
        "$count of $episodes ${if (episodes == 1) "episode" else "episodes"} ($percent%)"
    override val triggersTitle = "Possible Triggers Noted"
    override val noTriggers = "No triggers noted in this range."
    override val triggersNote = "Counts show how often the patient noted a trigger with an episode. They do not show a cause."
    override fun withoutTriggers(withoutTriggers: Int, episodes: Int) =
        "$withoutTriggers of $episodes ${if (episodes == 1) "episode" else "episodes"} " +
            "${if (withoutTriggers == 1) "has" else "have"} no triggers noted."
    override val episodesTitle = "Episodes"
    override val noEpisodes = "No episodes recorded in this range."

    override val columnDateTime = "Date & Time"
    override val columnPain = "Pain"
    override val columnEpicenter = "Epicenter"
    override val columnSymptoms = "Symptoms"
    override val columnMedications = "Medications"
    override val columnTriggers = "Triggers"

    override fun intensityOfTen(intensity: Int) = "$intensity/10"
    override val notSet = "Not set"
    override val none = "None"

    override val sheetEpisodes = "Episodes"
    override val sheetMedications = "Medications"
    override val sheetDailyPain = "Daily Pain"
    override val sheetProfile = "Profile"

    override val headerStarted = "Started"
    override val headerPainIntensity = "Pain Intensity (1–10)"
    override val headerEpicenter = "Cranial Epicenter"
    override val headerSymptoms = "Aura & Sensory Symptoms"
    override val headerMedications = "Medications"
    override val headerTriggers = "Possible Triggers"
    override val headerEpisodeStarted = "Episode Started"
    override val headerMedication = "Medication"
    override val headerDose = "Dose"
    override val headerDate = "Date"
    override val headerPeakPain = "Peak Pain Intensity (1–10, 0 = pain-free)"
    override val headerDay = "Day"
    override val headerField = "Field"
    override val headerValue = "Value"

    override val dayMigraine = "Migraine"
    override val dayPainFree = "Pain-Free"
    override val dayNotTracked = "Not Tracked"
    override val profilePatient = "Patient"
    override val profileRange = "Report Range"
    override val profileTrackedSince = "Tracked Since"
    override val profileDiagnoses = "Diagnoses"
    override val profileFrequency = "Monthly Frequency"
    override val profileDuration = "Typical Duration"
    override val profileIntensity = "Typical Intensity"
    override val profileTriggers = "Known Triggers"
    override val profileTreatments = "Treatment Blueprint"
    override fun isoRange(from: String, to: String) = "$from to $to"
}
