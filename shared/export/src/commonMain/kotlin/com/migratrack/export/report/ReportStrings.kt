package com.migratrack.export.report

/**
 * Every text of the PDF and the XLSX, in the language of the app.
 * Counts come in as numbers, and the implementation formats them and picks the plural form;
 * dates come in already formatted. The examples are the English texts.
 */
interface ReportStrings {
    // PDF: header and footer

    /** "Clinical Migraine Report" */
    val title: String

    /** "MigraTrack · Page 2" */
    fun page(number: Int): String

    /** "Patient: Alex Morgan" */
    fun patient(name: String): String

    /** "Jul 20 – Oct 17 (Last 90 Days)" */
    fun rangeWithTitle(range: String, title: String): String

    /** "Generated Oct 17, 2026" */
    fun generated(date: String): String

    /** "Self-reported data logged in MigraTrack. …" */
    val disclaimer: String

    // PDF: stat tiles

    /** "Episodes recorded" */
    val statEpisodes: String

    /** "Migraine days" */
    val statMigraineDays: String

    /** "Pain-free days" */
    val statPainFreeDays: String

    /** "Average intensity /10" */
    val statAverageIntensity: String

    /** "Tracking started on Oct 20, 2026. 84 earlier days of this range have no records …" */
    fun untrackedNote(trackedSince: String, days: Int): String

    // PDF: sections

    /** "Baseline Profile" */
    val baselineTitle: String

    /** "Diagnoses" */
    val baselineDiagnoses: String

    /** "Monthly frequency" */
    val baselineFrequency: String

    /** "Typical duration" */
    val baselineDuration: String

    /** "Typical intensity" */
    val baselineIntensity: String

    /** "Known triggers" */
    val baselineTriggers: String

    /** "Treatment blueprint" */
    val baselineTreatments: String

    /** "Pain Intensity Timeline (1–10)" */
    val painChartTitle: String

    /** "Mild 1–3" */
    fun legendMild(from: Int, to: Int): String

    /** "Moderate 4–7" */
    fun legendModerate(from: Int, to: Int): String

    /** "Severe 8–10" */
    fun legendSevere(from: Int, to: Int): String

    /** "Not tracked" */
    val legendNotTracked: String

    /** "Medication Use" */
    val medicationTitle: String

    /** "No medication intakes logged in this range." */
    val noMedications: String

    /** "8 intakes" */
    fun intakes(count: Int): String

    /** "Aura & Warning Symptoms" */
    val symptomsTitle: String

    /** "No aura or sensory symptoms logged in this range." */
    val noSymptoms: String

    /** "3 of 11 episodes (27%)" */
    fun shareOfEpisodes(count: Int, episodes: Int, percent: Int): String

    /** "Possible Triggers Noted" */
    val triggersTitle: String

    /** "No triggers noted in this range." */
    val noTriggers: String

    /** "Counts show how often the patient noted a trigger with an episode. They do not show a cause." */
    val triggersNote: String

    /** "1 of 3 episodes has no triggers noted." */
    fun withoutTriggers(withoutTriggers: Int, episodes: Int): String

    /** "Episodes" */
    val episodesTitle: String

    /** "No episodes recorded in this range." */
    val noEpisodes: String

    // PDF: episode table, shown in upper case

    /** "Date & Time" */
    val columnDateTime: String

    /** "Pain" */
    val columnPain: String

    /** "Epicenter" */
    val columnEpicenter: String

    /** "Symptoms" */
    val columnSymptoms: String

    /** "Medications" */
    val columnMedications: String

    /** "Triggers" */
    val columnTriggers: String

    // PDF and XLSX

    /** "6/10" */
    fun intensityOfTen(intensity: Int): String

    /** "Not set", for an empty name or an empty diagnosis list */
    val notSet: String

    /** "None", for an empty list of triggers or treatments */
    val none: String

    // XLSX: sheet names

    /** "Episodes" */
    val sheetEpisodes: String

    /** "Medications" */
    val sheetMedications: String

    /** "Daily Pain" */
    val sheetDailyPain: String

    /** "Profile" */
    val sheetProfile: String

    // XLSX: column headers

    /** "Started" */
    val headerStarted: String

    /** "Pain Intensity (1–10)" */
    val headerPainIntensity: String

    /** "Cranial Epicenter" */
    val headerEpicenter: String

    /** "Aura & Sensory Symptoms" */
    val headerSymptoms: String

    /** "Medications" */
    val headerMedications: String

    /** "Possible Triggers" */
    val headerTriggers: String

    /** "Episode Started" */
    val headerEpisodeStarted: String

    /** "Medication" */
    val headerMedication: String

    /** "Dose" */
    val headerDose: String

    /** "Date" */
    val headerDate: String

    /** "Peak Pain Intensity (1–10, 0 = pain-free)" */
    val headerPeakPain: String

    /** "Day" */
    val headerDay: String

    /** "Field" */
    val headerField: String

    /** "Value" */
    val headerValue: String

    // XLSX: values

    /** "Migraine" */
    val dayMigraine: String

    /** "Pain-Free" */
    val dayPainFree: String

    /** "Not Tracked" */
    val dayNotTracked: String

    /** "Patient" */
    val profilePatient: String

    /** "Report Range" */
    val profileRange: String

    /** "Tracked Since" */
    val profileTrackedSince: String

    /** "Diagnoses" */
    val profileDiagnoses: String

    /** "Monthly Frequency" */
    val profileFrequency: String

    /** "Typical Duration" */
    val profileDuration: String

    /** "Typical Intensity" */
    val profileIntensity: String

    /** "Known Triggers" */
    val profileTriggers: String

    /** "Treatment Blueprint" */
    val profileTreatments: String

    /** "2026-07-20 to 2026-10-17" */
    fun isoRange(from: String, to: String): String
}
