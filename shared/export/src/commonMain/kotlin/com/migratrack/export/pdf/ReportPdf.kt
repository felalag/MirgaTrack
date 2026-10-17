package com.migratrack.export.pdf

import com.migratrack.export.format.DateFormats
import com.migratrack.export.format.NumberFormats
import com.migratrack.export.pdf.TextStyle.Align
import com.migratrack.export.pdf.TextStyle.Weight
import com.migratrack.export.report.ClinicalReport
import com.migratrack.export.report.ReportEpisode
import kotlin.math.roundToInt

/** Lays out the clinical report on A4 pages, light theme, for printing and sharing with a clinician. */
object ReportPdf {
    const val PAGE_WIDTH = 595f
    const val PAGE_HEIGHT = 842f

    fun render(report: ClinicalReport, renderer: PdfRenderer, dates: DateFormats, numbers: NumberFormats): ByteArray =
        renderer.render(PAGE_WIDTH, PAGE_HEIGHT) { canvas ->
            val page = PageCursor(canvas, dates, numbers)
            page.newPage()
            page.header(report)
            page.statTiles(report)
            report.baseline?.let { page.baseline(report) }
            if (report.options.includePainCharts) page.painChart(report)
            if (report.options.includeMedications) page.medicationUse(report)
            if (report.options.includeSymptoms) page.symptomFrequency(report)
            if (report.options.includeTriggers) page.triggerFrequency(report)
            page.episodeLog(report)
            page.disclaimer()
        }
}

private object Palette {
    const val PINK = 0xFFFF2D55
    const val GREEN = 0xFF30D158
    const val ORANGE = 0xFFFF9F0A
    const val CYAN = 0xFF00B8C4 // darker than the app cyan, which is unreadable on white
    const val TEXT = 0xFF1C1C1E
    const val SECONDARY = 0xFF6E6E73
    const val PURPLE = 0xFFAF52DE // the iOS light-mode purple; the app's violet is too pale on white
    const val TILE = 0xFFF2F2F7
    const val DIVIDER = 0xFFE5E5EA
}

private const val MARGIN = 40f
private const val FOOTER_SPACE = 28f
private const val DISCLAIMER = "Self-reported data logged in MigraTrack. This report supports, and does not replace, a clinical assessment."

private class PageCursor(
    private val canvas: ReportCanvas,
    private val dates: DateFormats,
    private val numbers: NumberFormats,
) {
    var y = 0f
    private var pageNumber = 0
    private val left = MARGIN
    private val width = canvas.pageWidth - 2 * MARGIN
    private val bottom = canvas.pageHeight - MARGIN - FOOTER_SPACE

    fun newPage() {
        canvas.beginPage()
        pageNumber++
        y = MARGIN
        canvas.drawText(
            "MigraTrack · Page $pageNumber",
            TextStyle(8f, color = Palette.SECONDARY, align = Align.Center),
            left, canvas.pageHeight - MARGIN, width,
        )
    }

    private fun ensure(height: Float) {
        if (y + height > bottom) newPage()
    }

    private fun text(value: String, style: TextStyle, x: Float = left, maxWidth: Float = width): Float {
        val height = canvas.measureHeight(value, style, maxWidth)
        canvas.drawText(value, style, x, y, maxWidth)
        return height
    }

    fun header(report: ClinicalReport) {
        canvas.fillRect(0f, 0f, canvas.pageWidth, 6f, Palette.PINK)
        y += text("MIGRATRACK", TextStyle(9f, Weight.Bold, Palette.PINK)) + 4f
        y += text("Clinical Migraine Report", TextStyle(22f, Weight.Bold)) + 6f
        val details = buildList {
            if (report.patientName.isNotBlank()) add("Patient: ${report.patientName}")
            add("${dates.range(report.from, report.to)} (${report.rangeTitle})")
            add("Generated ${dates.fullDate(report.generatedOn)}")
        }.joinToString("  ·  ")
        y += text(details, TextStyle(10f, color = Palette.SECONDARY)) + 18f
    }

    fun statTiles(report: ClinicalReport) {
        val stats = report.stats
        val tiles = listOf(
            Triple(numbers.integer(stats.episodes), "Episodes recorded", Palette.PINK),
            Triple(numbers.integer(stats.migraineDays), "Migraine days", Palette.PINK),
            Triple(numbers.integer(stats.painFreeDays), "Pain-free days", Palette.GREEN),
            Triple(stats.averageIntensity?.let(numbers::decimal) ?: "–", "Average intensity /10", Palette.ORANGE),
        )
        val gap = 10f
        val tileWidth = (width - gap * (tiles.size - 1)) / tiles.size
        val tileHeight = 58f
        ensure(tileHeight)
        tiles.forEachIndexed { index, (value, label, color) ->
            val x = left + index * (tileWidth + gap)
            canvas.fillRect(x, y, tileWidth, tileHeight, Palette.TILE, cornerRadius = 10f)
            canvas.drawText(value, TextStyle(20f, Weight.Bold, color), x + 12f, y + 10f, tileWidth - 24f)
            canvas.drawText(label, TextStyle(8.5f, color = Palette.SECONDARY), x + 12f, y + 38f, tileWidth - 24f)
        }
        y += tileHeight
        if (stats.untrackedDays > 0) {
            val days = if (stats.untrackedDays == 1) "1 earlier day" else "${stats.untrackedDays} earlier days"
            val note = "Tracking started on ${dates.fullDate(report.trackedSince)}. " +
                "$days of this range have no records and do not count as pain-free days."
            y += 14f + text(note, TextStyle(9.5f, color = Palette.SECONDARY))
        }
        y += 22f
    }

    private fun sectionTitle(title: String, accent: Long) {
        ensure(40f)
        canvas.fillRect(left, y + 2f, 3f, 14f, accent, cornerRadius = 1.5f)
        y += text(title, TextStyle(13f, Weight.Bold), x = left + 10f, maxWidth = width - 10f) + 10f
    }

    fun baseline(report: ClinicalReport) {
        val baseline = report.baseline ?: return
        sectionTitle("Baseline Profile", Palette.CYAN)
        val rows = listOf(
            "Diagnoses" to baseline.diagnoses.joinToString(", ").ifBlank { "Not set" },
            "Monthly frequency" to baseline.frequency,
            "Typical duration" to baseline.typicalDuration,
            "Typical intensity" to "${baseline.typicalIntensity}/10",
            "Known triggers" to baseline.triggers.joinToString(", ").ifBlank { "None" },
            "Treatment blueprint" to baseline.treatments.joinToString(", ").ifBlank { "None" },
        )
        keyValueRows(rows)
        y += 14f
    }

    private fun keyValueRows(rows: List<Pair<String, String>>) {
        val keyWidth = 130f
        val keyStyle = TextStyle(9.5f, color = Palette.SECONDARY)
        val valueStyle = TextStyle(10f)
        for ((key, value) in rows) {
            val height = maxOf(
                canvas.measureHeight(key, keyStyle, keyWidth),
                canvas.measureHeight(value, valueStyle, width - keyWidth),
            )
            ensure(height + 6f)
            canvas.drawText(key, keyStyle, left, y, keyWidth)
            canvas.drawText(value, valueStyle, left + keyWidth, y, width - keyWidth)
            y += height + 6f
        }
    }

    fun painChart(report: ClinicalReport) {
        val chartHeight = 120f
        val axisWidth = 22f
        sectionTitle("Pain Intensity Timeline (1–10)", Palette.PINK)
        ensure(chartHeight + 44f)
        val chartLeft = left + axisWidth
        val chartWidth = width - axisWidth
        val axisStyle = TextStyle(7.5f, color = Palette.SECONDARY)
        val days = report.days
        val slot = chartWidth / days.size.coerceAtLeast(1)
        // One rect per run of untracked days: a rect per day leaves hairline seams between the slots.
        var index = 0
        while (index < days.size) {
            if (days[index].isTracked) {
                index++
                continue
            }
            val start = index
            while (index < days.size && !days[index].isTracked) index++
            canvas.fillRect(chartLeft + start * slot, y, (index - start) * slot, chartHeight, Palette.TILE)
        }
        for (level in listOf(0, 5, 10)) {
            val lineY = y + chartHeight - chartHeight * level / 10f
            canvas.strokeLine(chartLeft, lineY, chartLeft + chartWidth, lineY, Palette.DIVIDER, 0.5f)
            canvas.drawText(numbers.integer(level), axisStyle.copy(align = Align.End), left, lineY - 5f, axisWidth - 6f)
        }
        val barWidth = (slot * 0.7f).coerceIn(1f, 10f)
        days.forEachIndexed { index, day ->
            val peak = day.peak ?: return@forEachIndexed
            val barHeight = chartHeight * peak / 10f
            val x = chartLeft + index * slot + (slot - barWidth) / 2
            canvas.fillRect(x, y + chartHeight - barHeight, barWidth, barHeight, severityColor(peak), cornerRadius = barWidth / 2)
        }
        y += chartHeight + 4f
        if (days.isNotEmpty()) {
            val labelWidth = 70f
            canvas.drawText(dates.monthDay(days.first().date), axisStyle, chartLeft, y, labelWidth)
            canvas.drawText(dates.monthDay(days[days.size / 2].date), axisStyle.copy(align = Align.Center), chartLeft + chartWidth / 2 - labelWidth / 2, y, labelWidth)
            canvas.drawText(dates.monthDay(days.last().date), axisStyle.copy(align = Align.End), chartLeft + chartWidth - labelWidth, y, labelWidth)
        }
        y += 16f
        legend(
            buildList {
                add("Mild 1–3" to Palette.GREEN)
                add("Moderate 4–7" to Palette.ORANGE)
                add("Severe 8–10" to Palette.PINK)
                if (days.any { !it.isTracked }) add("Not tracked" to Palette.DIVIDER)
            },
        )
        y += 20f
    }

    private fun legend(items: List<Pair<String, Long>>) {
        var x = left + 22f
        val style = TextStyle(8.5f, color = Palette.SECONDARY)
        for ((label, color) in items) {
            canvas.fillRect(x, y + 2.5f, 7f, 7f, color, cornerRadius = 3.5f)
            canvas.drawText(label, style, x + 11f, y, 90f)
            x += 100f
        }
        y += 12f
    }

    fun medicationUse(report: ClinicalReport) {
        val counts = report.episodes
            .flatMap { it.medications }
            .groupingBy { (it.name + " " + it.dose).trim() }
            .eachCount()
            .entries.sortedByDescending { it.value }
        sectionTitle("Medication Use", Palette.GREEN)
        if (counts.isEmpty()) {
            y += text("No medication intakes logged in this range.", TextStyle(10f, color = Palette.SECONDARY)) + 16f
            return
        }
        keyValueRows(counts.map { (name, count) -> name to "$count ${if (count == 1) "intake" else "intakes"}" })
        y += 14f
    }

    fun symptomFrequency(report: ClinicalReport) {
        val total = report.episodes.size
        val counts = report.episodes
            .flatMap { it.symptoms }
            .groupingBy { it }
            .eachCount()
            .entries.sortedByDescending { it.value }
        sectionTitle("Aura & Warning Symptoms", Palette.CYAN)
        if (counts.isEmpty()) {
            y += text("No aura or sensory symptoms logged in this range.", TextStyle(10f, color = Palette.SECONDARY)) + 16f
            return
        }
        keyValueRows(counts.map { (symptom, count) ->
            symptom to "$count of $total episodes (${(count * 100f / total).roundToInt()}%)"
        })
        y += 14f
    }

    fun triggerFrequency(report: ClinicalReport) {
        val frequency = report.triggers
        sectionTitle("Possible Triggers Noted", Palette.PURPLE)
        if (frequency.counts.isEmpty()) {
            y += text("No triggers noted in this range.", TextStyle(10f, color = Palette.SECONDARY)) + 16f
            return
        }
        keyValueRows(frequency.counts.map { count ->
            count.name to "${count.episodes} of ${frequency.episodes} episodes (${(count.episodes * 100f / frequency.episodes).roundToInt()}%)"
        })
        val note = buildString {
            append("Counts show how often the patient noted a trigger with an episode. They do not show a cause.")
            if (frequency.withoutTriggers > 0) {
                append(" ${withoutTriggersText(frequency.withoutTriggers, frequency.episodes)}.")
            }
        }
        ensure(24f)
        y += text(note, TextStyle(8.5f, color = Palette.SECONDARY)) + 18f
    }

    fun episodeLog(report: ClinicalReport) {
        sectionTitle("Episodes", Palette.ORANGE)
        if (report.episodes.isEmpty()) {
            y += text("No episodes recorded in this range.", TextStyle(10f, color = Palette.SECONDARY)) + 16f
            return
        }
        val columns = tableColumns(report)
        tableHeader(columns)
        val cellStyle = TextStyle(9f)
        for ((index, episode) in report.episodes.withIndex()) {
            val values = columns.map { it.value(episode) }
            val rowHeight = columns.zip(values).maxOf { (column, value) ->
                canvas.measureHeight(value, cellStyle, column.width - 8f)
            } + 10f
            if (y + rowHeight > bottom) {
                newPage()
                tableHeader(columns)
            }
            if (index % 2 == 1) canvas.fillRect(left, y, width, rowHeight, Palette.TILE)
            var x = left
            columns.zip(values).forEach { (column, value) ->
                val style = if (column.title == "Pain") cellStyle.copy(weight = Weight.Bold, color = severityColor(episode.intensity)) else cellStyle
                canvas.drawText(value, style, x + 4f, y + 5f, column.width - 8f)
                x += column.width
            }
            y += rowHeight
        }
        canvas.strokeLine(left, y, left + width, y, Palette.DIVIDER, 0.5f)
        y += 18f
    }

    private class TableColumn(val title: String, val width: Float, val value: (ReportEpisode) -> String)

    private fun tableColumns(report: ClinicalReport): List<TableColumn> {
        val fixed = listOf(
            TableColumn("Date & Time", 92f) { "${dates.fullDate(it.startedAt.date)}\n${dates.time(it.startedAt)}" },
            TableColumn("Pain", 36f) { "${it.intensity}/10" },
            TableColumn("Epicenter", 96f) { it.region ?: "–" },
        )
        val flexible = buildList {
            if (report.options.includeSymptoms) add("Symptoms" to { e: ReportEpisode -> e.symptoms.joinToString(", ").ifBlank { "–" } })
            if (report.options.includeMedications) add("Medications" to { e: ReportEpisode ->
                e.medications.joinToString(", ") { "${it.name} ${it.dose}".trim() }.ifBlank { "–" }
            })
            if (report.options.includeTriggers) add("Triggers" to { e: ReportEpisode -> e.triggers.joinToString(", ").ifBlank { "–" } })
        }
        val remaining = width - fixed.sumOf { it.width.toDouble() }.toFloat()
        val fixedColumns = if (flexible.isEmpty()) {
            fixed.dropLast(1) + TableColumn(fixed.last().title, fixed.last().width + remaining, fixed.last().value)
        } else fixed
        return fixedColumns + flexible.map { (title, value) -> TableColumn(title, remaining / flexible.size, value) }
    }

    private fun tableHeader(columns: List<TableColumn>) {
        val style = TextStyle(8.5f, Weight.Bold, Palette.SECONDARY)
        val height = 20f
        ensure(height + 24f)
        canvas.fillRect(left, y, width, height, Palette.DIVIDER, cornerRadius = 4f)
        var x = left
        for (column in columns) {
            canvas.drawText(column.title.uppercase(), style, x + 4f, y + 5.5f, column.width - 8f)
            x += column.width
        }
        y += height
    }

    fun disclaimer() {
        val style = TextStyle(8f, color = Palette.SECONDARY)
        val height = canvas.measureHeight(DISCLAIMER, style, width)
        // The line may go into the footer space above the page number, so it never fills a page alone.
        y = minOf(y, bottom + FOOTER_SPACE - height - 6f)
        text(DISCLAIMER, style)
    }
}

private fun severityColor(intensity: Int): Long = when (intensity) {
    in 1..3 -> Palette.GREEN
    in 4..7 -> Palette.ORANGE
    else -> Palette.PINK
}

/** "1 of 3 episodes has no triggers noted": the noun follows the total, the verb the first number. Stage 1d translates it. */
internal fun withoutTriggersText(withoutTriggers: Int, episodes: Int): String =
    "$withoutTriggers of $episodes ${if (episodes == 1) "episode" else "episodes"} " +
        "${if (withoutTriggers == 1) "has" else "have"} no triggers noted"
