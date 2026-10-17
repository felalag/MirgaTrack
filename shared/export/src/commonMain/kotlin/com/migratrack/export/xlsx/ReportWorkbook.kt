package com.migratrack.export.xlsx

import com.migratrack.export.report.ClinicalReport
import com.migratrack.export.report.ReportStrings

/**
 * Lays the report out as sheets: Episodes, then Medications and Profile when they apply.
 * Dates stay ISO 8601 (`LocalDate.toString()`) in every language, so a spreadsheet can sort and parse them.
 */
object ReportWorkbook {

    fun write(report: ClinicalReport, strings: ReportStrings): ByteArray = XlsxWriter.write(sheets(report, strings), strings.rightToLeft)

    internal fun sheets(report: ClinicalReport, strings: ReportStrings): List<Sheet> = buildList {
        add(episodesSheet(report, strings))
        if (report.options.includeMedications) add(medicationsSheet(report, strings))
        if (report.options.includePainCharts) add(dailySheet(report, strings))
        report.baseline?.let { add(profileSheet(report, strings)) }
    }

    private fun episodesSheet(report: ClinicalReport, strings: ReportStrings): Sheet {
        val columns = buildList {
            add(Column(strings.headerStarted, 18.0))
            add(Column(strings.headerPainIntensity, 20.0))
            add(Column(strings.headerEpicenter, 26.0))
            if (report.options.includeSymptoms) add(Column(strings.headerSymptoms, 48.0))
            if (report.options.includeMedications) add(Column(strings.headerMedications, 36.0))
            if (report.options.includeTriggers) add(Column(strings.headerTriggers, 40.0))
        }
        val rows = report.episodes.map { episode ->
            buildList {
                add(Cell.DateTime(episode.startedAt))
                add(Cell.Number(episode.intensity.toDouble()))
                add(episode.region?.let(Cell::Text) ?: Cell.Empty)
                if (report.options.includeSymptoms) add(Cell.Text(episode.symptoms.joinToString(", ")))
                if (report.options.includeMedications) {
                    add(Cell.Text(episode.medications.joinToString(", ") { it.name + " " + it.dose }.trim()))
                }
                if (report.options.includeTriggers) add(Cell.Text(episode.triggers.joinToString(", ")))
            }
        }
        return Sheet(strings.sheetEpisodes, columns, rows)
    }

    private fun medicationsSheet(report: ClinicalReport, strings: ReportStrings): Sheet {
        val rows = report.episodes.flatMap { episode ->
            episode.medications.map { intake ->
                listOf(
                    Cell.DateTime(episode.startedAt),
                    Cell.Text(intake.name),
                    Cell.Text(intake.dose),
                    Cell.Number(episode.intensity.toDouble()),
                )
            }
        }
        val columns = listOf(
            Column(strings.headerEpisodeStarted, 18.0),
            Column(strings.headerMedication, 28.0),
            Column(strings.headerDose, 12.0),
            Column(strings.headerPainIntensity, 20.0),
        )
        return Sheet(strings.sheetMedications, columns, rows)
    }

    private fun dailySheet(report: ClinicalReport, strings: ReportStrings): Sheet {
        val rows = report.days.map { day ->
            listOf(
                Cell.Text(day.date.toString()),
                when {
                    day.peak != null -> Cell.Number(day.peak.toDouble())
                    day.isTracked -> Cell.Number(0.0)
                    else -> Cell.Empty
                },
                Cell.Text(
                    when {
                        day.peak != null -> strings.dayMigraine
                        day.isTracked -> strings.dayPainFree
                        else -> strings.dayNotTracked
                    },
                ),
            )
        }
        val columns = listOf(Column(strings.headerDate, 14.0), Column(strings.headerPeakPain, 18.0), Column(strings.headerDay, 12.0))
        return Sheet(strings.sheetDailyPain, columns, rows)
    }

    private fun profileSheet(report: ClinicalReport, strings: ReportStrings): Sheet {
        val baseline = requireNotNull(report.baseline)
        val rows = listOf(
            strings.profilePatient to report.patientName.ifBlank { strings.notSet },
            strings.profileRange to strings.isoRange(report.from.toString(), report.to.toString()),
            strings.profileTrackedSince to report.trackedSince.toString(),
            strings.profileDiagnoses to baseline.diagnoses.joinToString(", ").ifBlank { strings.notSet },
            strings.profileFrequency to baseline.frequency,
            strings.profileDuration to baseline.typicalDuration,
            strings.profileIntensity to strings.intensityOfTen(baseline.typicalIntensity),
            strings.profileTriggers to baseline.triggers.joinToString(", ").ifBlank { strings.none },
            strings.profileTreatments to baseline.treatments.joinToString(", ").ifBlank { strings.none },
        ).map { (key, value) -> listOf(Cell.Text(key), Cell.Text(value)) }
        return Sheet(strings.sheetProfile, listOf(Column(strings.headerField, 22.0), Column(strings.headerValue, 70.0)), rows)
    }
}
