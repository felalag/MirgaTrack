package com.migratrack.domain.model

data class ExportPreferences(
    val includeMedications: Boolean = true,
    val includeSymptoms: Boolean = true,
    val includePainCharts: Boolean = true,
    val range: ExportRange = ExportRange.Last90Days,
)

enum class ExportRange(val days: Int, val title: String) {
    Last30Days(30, "Last 30 Days"),
    Last90Days(90, "Last 90 Days"),
    Last180Days(180, "Last 6 Months"),
    Last365Days(365, "Last 12 Months"),
}
