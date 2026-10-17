package com.migratrack.domain.model

data class ExportPreferences(
    val includeMedications: Boolean = true,
    val includeSymptoms: Boolean = true,
    val includePainCharts: Boolean = true,
    val includeTriggers: Boolean = true,
    val range: ExportRange = ExportRange.Last90Days,
)

enum class ExportRange(val days: Int) {
    Last30Days(30),
    Last90Days(90),
    Last180Days(180),
    Last365Days(365),
}
