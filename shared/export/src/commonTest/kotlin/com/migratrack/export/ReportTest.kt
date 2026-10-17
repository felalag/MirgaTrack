package com.migratrack.export

import com.migratrack.domain.model.ExportPreferences
import com.migratrack.domain.model.ExportRange
import com.migratrack.export.format.DateFormats
import com.migratrack.export.format.NumberFormats
import com.migratrack.export.pdf.PdfRenderer
import com.migratrack.export.pdf.ReportCanvas
import com.migratrack.export.pdf.ReportPdf
import com.migratrack.export.pdf.TextStyle
import com.migratrack.export.pdf.withoutTriggersText
import com.migratrack.export.xlsx.Cell
import com.migratrack.export.xlsx.ReportWorkbook
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.hours
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.minus
import kotlinx.datetime.number

class ReportTest {

    @Test
    fun reportKeepsOnlyEpisodesInsideTheRange() {
        val episodes = listOf(episode(24), episode(1), episode(25, month = 7))
        val report = report(episodes, ExportPreferences(range = ExportRange.Last30Days))

        assertEquals(2, report.stats.episodes)
        assertEquals(28, report.stats.painFreeDays)
        assertEquals(30, report.days.size)
        assertEquals(listOf("Ibuprofen 400mg", "Riboflavin 400mg"), report.baseline?.treatments)
    }

    @Test
    fun freshInstallReportHasOnePainFreeDayNotNinety() {
        val report = report(emptyList(), trackedSince = TODAY)

        assertEquals(1, report.stats.painFreeDays)
    }

    @Test
    fun reportCountsPainFreeDaysOnlyFromTheTrackingStart() {
        // Tracking started on Oct 20. The user also logged an earlier attack on Oct 10.
        val report = report(listOf(episode(22), episode(10)), trackedSince = LocalDate(2026, 10, 20))

        assertEquals(2, report.stats.migraineDays)
        assertEquals(4, report.stats.painFreeDays)
        assertEquals(84, report.stats.untrackedDays)
        assertEquals(listOf(false, true), report.days.filter { it.date in LocalDate(2026, 10, 9)..LocalDate(2026, 10, 10) }.map { it.isTracked })

        val daily = ReportWorkbook.sheets(report).single { it.name == "Daily Pain" }.rows
        assertEquals(84, daily.count { it.last() == Cell.Text("Not Tracked") })
        assertEquals(4, daily.count { it.last() == Cell.Text("Pain-Free") })

        val canvas = RecordingRenderer()
        ReportPdf.render(report, canvas, EnglishDateFormats, EnglishNumberFormats)
        assertTrue(canvas.texts.any { it.startsWith("Tracking started on Oct 20, 2026. 84 earlier days") })
        assertTrue("Not tracked" in canvas.texts)
    }

    @Test
    fun reportOfAFullyTrackedRangeHasNoTrackingNote() {
        val canvas = RecordingRenderer()
        ReportPdf.render(report(listOf(episode(20))), canvas, EnglishDateFormats, EnglishNumberFormats)

        assertFalse(canvas.texts.any { it.startsWith("Tracking started") || it == "Not tracked" })
    }

    @Test
    fun workbookDropsSheetsAndColumnsThatTheTogglesExclude() {
        val all = ReportWorkbook.sheets(report(listOf(episode(20))))
        assertEquals(listOf("Episodes", "Medications", "Daily Pain", "Profile"), all.map { it.name })
        assertEquals("Possible Triggers", all.first().columns.last().title)
        assertEquals(6, all.first().columns.size)

        val bare = ReportWorkbook.sheets(
            report(
                listOf(episode(20)),
                ExportPreferences(includeMedications = false, includeSymptoms = false, includePainCharts = false, includeTriggers = false),
            ),
        )
        assertEquals(listOf("Episodes", "Profile"), bare.map { it.name })
        assertEquals(3, bare.first().columns.size)
    }

    @Test
    fun longEpisodeLogBreaksOntoNewPagesInsideTheMargins() {
        val episodes = (1..24).map { episode(it) } + (1..30).map { episode(it, month = 9) }
        val canvas = RecordingRenderer()
        ReportPdf.render(report(episodes), canvas, EnglishDateFormats, EnglishNumberFormats)

        assertTrue(canvas.pages >= 2, "expected several pages, got ${canvas.pages}")
        assertTrue(canvas.maxContentBottom <= ReportPdf.PAGE_HEIGHT - 40f, "content ran into the bottom margin")
        assertTrue(canvas.texts.any { it == "Аня Морозова" || it.contains("Аня Морозова") })
    }

    @Test
    fun disclaimerNeverFillsTheLastPageAlone() {
        val days = (0 until 80).map { TODAY.minus(it, DateTimeUnit.DAY) }
        for (count in 1..days.size) {
            val canvas = RecordingRenderer()
            ReportPdf.render(report(days.take(count).map { episode(it.day, it.month.number) }), canvas, EnglishDateFormats, EnglishNumberFormats)

            val lastPage = canvas.pageTexts.last().filterNot { it.startsWith("MigraTrack · Page") }
            assertTrue(lastPage.size > 1, "$count episodes: the last page has only $lastPage")
            assertTrue(canvas.maxContentBottom <= ReportPdf.PAGE_HEIGHT - 40f, "$count episodes: content ran into the footer")
        }
    }

    @Test
    fun pdfLeavesOutTheChartWhenItIsSwitchedOff() {
        val canvas = RecordingRenderer()
        ReportPdf.render(report(listOf(episode(20)), ExportPreferences(includePainCharts = false)), canvas, EnglishDateFormats, EnglishNumberFormats)
        assertFalse(canvas.texts.any { it.startsWith("Pain Intensity Timeline") })
    }

    @Test
    fun reportCountsTheEpisodesThatNotedEachTrigger() {
        val episodes = listOf(episode(20), episode(21, triggers = listOf("caffeine", "poor-sleep")), episode(22, triggers = emptyList()))
        val report = report(episodes)

        assertEquals(listOf("Caffeine Withdrawal / Excess" to 2, "Poor / Irregular Sleep" to 1), report.triggers.counts.map { it.name to it.episodes })
        assertEquals(1, report.triggers.withoutTriggers)
        assertEquals(listOf("Caffeine Withdrawal / Excess", "Poor / Irregular Sleep"), report.episodes[1].triggers)
    }

    @Test
    fun pdfShowsTriggerFrequencyAsAShareOfEpisodesOnlyWhenSwitchedOn() {
        val episodes = listOf(episode(20), episode(21, triggers = emptyList()))
        val on = RecordingRenderer().also { ReportPdf.render(report(episodes), it, EnglishDateFormats, EnglishNumberFormats) }
        assertTrue("Possible Triggers Noted" in on.texts)
        assertTrue("1 of 2 episodes (50%)" in on.texts)
        assertTrue(on.texts.any { it.contains("do not show a cause") && it.contains("1 of 2 episodes has no triggers noted") })
        assertTrue("TRIGGERS" in on.texts)

        val off = RecordingRenderer().also { ReportPdf.render(report(episodes, ExportPreferences(includeTriggers = false)), it, EnglishDateFormats, EnglishNumberFormats) }
        assertFalse("Possible Triggers Noted" in off.texts)
        assertFalse("TRIGGERS" in off.texts)
    }

    @Test
    fun reportShowsNoMedicationTimeBecauseTheUserNeverEntersOne() {
        val logged = episode(20)
        val later = logged.copy(medications = logged.medications.map { it.copy(takenAt = it.takenAt + 2.hours) })
        val report = report(listOf(later))

        val canvas = RecordingRenderer().also { ReportPdf.render(report, it, EnglishDateFormats, EnglishNumberFormats) }
        assertTrue("Ibuprofen 400mg" in canvas.texts)
        assertFalse(canvas.texts.any { it.contains(" at ") && it.startsWith("Ibuprofen") })

        val medications = ReportWorkbook.sheets(report).single { it.name == "Medications" }
        assertEquals("Episode Started", medications.columns.first().title)
        assertEquals(Cell.DateTime(LocalDateTime(2026, 10, 20, 9, 30)), medications.rows.single().first())
    }

    @Test
    fun pdfWritesDatesAndNumbersInTheFormatsOfTheRegion() {
        val dayFirst = object : DateFormats by EnglishDateFormats {
            override fun fullDate(date: LocalDate) = "${date.day} ${shortMonth(date.month)} ${date.year}"
        }
        val decimalComma = object : NumberFormats by EnglishNumberFormats {
            override fun decimal(value: Double) = EnglishNumberFormats.decimal(value).replace('.', ',')
        }
        val episodes = listOf(episode(20, intensity = 5), episode(21, intensity = 6))
        val canvas = RecordingRenderer().also { ReportPdf.render(report(episodes), it, dayFirst, decimalComma) }

        assertTrue("5,5" in canvas.texts)
        assertTrue(canvas.texts.any { it.contains("Generated 24 Oct 2026") })
        assertTrue(canvas.texts.any { it.startsWith("20 Oct 2026\n") })
    }

    /** Records draw calls. Text wraps at half the font size per character, 1.2 × font size per line. */
    private class RecordingRenderer : PdfRenderer, ReportCanvas {
        override val pageWidth = ReportPdf.PAGE_WIDTH
        override val pageHeight = ReportPdf.PAGE_HEIGHT
        var pages = 0
        var maxContentBottom = 0f
        val texts = mutableListOf<String>()
        val pageTexts = mutableListOf<MutableList<String>>()

        override fun render(pageWidth: Float, pageHeight: Float, draw: (ReportCanvas) -> Unit): ByteArray {
            draw(this)
            return ByteArray(0)
        }

        override fun beginPage() {
            pages++
            pageTexts += mutableListOf<String>()
        }

        override fun measureHeight(text: String, style: TextStyle, maxWidth: Float): Float {
            val charsPerLine = (maxWidth / (style.size * 0.5f)).toInt().coerceAtLeast(1)
            return ((text.length + charsPerLine - 1) / charsPerLine).coerceAtLeast(1) * style.size * 1.2f
        }

        override fun drawText(text: String, style: TextStyle, x: Float, y: Float, maxWidth: Float) {
            texts += text
            pageTexts.last() += text
            // The footer sits in the bottom margin on purpose.
            if (!text.startsWith("MigraTrack · Page")) {
                maxContentBottom = maxOf(maxContentBottom, y + measureHeight(text, style, maxWidth))
            }
        }

        override fun fillRect(x: Float, y: Float, width: Float, height: Float, color: Long, cornerRadius: Float) {
            if (y > 6f) maxContentBottom = maxOf(maxContentBottom, y + height)
        }

        override fun strokeLine(x1: Float, y1: Float, x2: Float, y2: Float, color: Long, width: Float) = Unit
    }

    @Test
    fun episodesWithoutTriggersReadAsCorrectEnglish() {
        assertEquals("1 of 3 episodes has no triggers noted", withoutTriggersText(1, 3))
        assertEquals("2 of 3 episodes have no triggers noted", withoutTriggersText(2, 3))
        assertEquals("1 of 1 episode has no triggers noted", withoutTriggersText(1, 1))
    }
}
