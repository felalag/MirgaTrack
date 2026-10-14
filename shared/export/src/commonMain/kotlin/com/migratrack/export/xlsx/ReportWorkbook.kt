package com.migratrack.export.xlsx

import com.migratrack.domain.analytics.DateText
import com.migratrack.export.report.ClinicalReport

/** Lays the report out as sheets: Episodes, then Medications and Profile when they apply. */
object ReportWorkbook {

    fun write(report: ClinicalReport): ByteArray = XlsxWriter.write(sheets(report))

    internal fun sheets(report: ClinicalReport): List<Sheet> = buildList {
        add(episodesSheet(report))
        if (report.options.includeMedications) add(medicationsSheet(report))
        if (report.options.includePainCharts) add(dailySheet(report))
        report.baseline?.let { add(profileSheet(report)) }
    }

    private fun episodesSheet(report: ClinicalReport): Sheet {
        val columns = buildList {
            add(Column("Started", 18.0))
            add(Column("Pain Intensity (1-10)", 20.0))
            add(Column("Cranial Epicenter", 26.0))
            if (report.options.includeSymptoms) add(Column("Aura & Sensory Symptoms", 48.0))
            if (report.options.includeMedications) add(Column("Medications", 36.0))
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
            }
        }
        return Sheet("Episodes", columns, rows)
    }

    private fun medicationsSheet(report: ClinicalReport): Sheet {
        val rows = report.episodes.flatMap { episode ->
            episode.medications.map { intake ->
                listOf(
                    Cell.DateTime(intake.takenAt),
                    Cell.Text(intake.name),
                    Cell.Text(intake.dose),
                    Cell.Number(episode.intensity.toDouble()),
                )
            }
        }
        val columns = listOf(
            Column("Taken", 18.0),
            Column("Medication", 28.0),
            Column("Dose", 12.0),
            Column("Episode Pain (1-10)", 20.0),
        )
        return Sheet("Medications", columns, rows)
    }

    private fun dailySheet(report: ClinicalReport): Sheet {
        val rows = report.days.map { day ->
            listOf(
                Cell.Text(DateText.iso(day.date)),
                when {
                    day.peak != null -> Cell.Number(day.peak.toDouble())
                    day.isTracked -> Cell.Number(0.0)
                    else -> Cell.Empty
                },
                Cell.Text(
                    when {
                        day.peak != null -> "Migraine"
                        day.isTracked -> "Pain-Free"
                        else -> "Not Tracked"
                    },
                ),
            )
        }
        return Sheet("Daily Pain", listOf(Column("Date", 14.0), Column("Peak Pain (0-10)", 18.0), Column("Day", 12.0)), rows)
    }

    private fun profileSheet(report: ClinicalReport): Sheet {
        val baseline = requireNotNull(report.baseline)
        val rows = listOf(
            "Patient" to report.patientName.ifBlank { "Not set" },
            "Report Range" to "${DateText.iso(report.from)} to ${DateText.iso(report.to)}",
            "Tracked Since" to DateText.iso(report.trackedSince),
            "Diagnoses" to baseline.diagnoses.joinToString(", ").ifBlank { "Not set" },
            "Monthly Frequency" to baseline.frequency,
            "Typical Duration" to baseline.typicalDuration,
            "Typical Intensity" to "${baseline.typicalIntensity}/10",
            "Known Triggers" to baseline.triggers.joinToString(", ").ifBlank { "None" },
            "Treatment Blueprint" to baseline.treatments.joinToString(", ").ifBlank { "None" },
        ).map { (key, value) -> listOf(Cell.Text(key), Cell.Text(value)) }
        return Sheet("Profile", listOf(Column("Field", 22.0), Column("Value", 70.0)), rows)
    }
}
