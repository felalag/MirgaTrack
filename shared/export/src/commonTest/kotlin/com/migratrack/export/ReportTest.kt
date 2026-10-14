package com.migratrack.export

import com.migratrack.domain.model.ExportPreferences
import com.migratrack.domain.model.ExportRange
import com.migratrack.export.pdf.PdfRenderer
import com.migratrack.export.pdf.ReportCanvas
import com.migratrack.export.pdf.ReportPdf
import com.migratrack.export.pdf.TextStyle
import com.migratrack.export.xlsx.ReportWorkbook
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ReportTest {

    @Test
    fun reportKeepsOnlyEpisodesInsideTheRange() {
        val episodes = listOf(episode(24), episode(1), episode(25, month = 7))
        val report = report(episodes, ExportPreferences(range = ExportRange.Last30Days))

        assertEquals(2, report.stats.episodes)
        assertEquals(28, report.stats.painFreeDays)
        assertEquals(30, report.dailyPeaks.size)
        assertEquals(listOf("Ibuprofen 400mg", "Riboflavin 400mg"), report.baseline?.treatments)
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
        ReportPdf.render(report(episodes), canvas)

        assertTrue(canvas.pages >= 2, "expected several pages, got ${canvas.pages}")
        assertTrue(canvas.maxContentBottom <= ReportPdf.PAGE_HEIGHT - 40f, "content ran into the bottom margin")
        assertTrue(canvas.texts.any { it == "Аня Морозова" || it.contains("Аня Морозова") })
    }

    @Test
    fun pdfLeavesOutTheChartWhenItIsSwitchedOff() {
        val canvas = RecordingRenderer()
        ReportPdf.render(report(listOf(episode(20)), ExportPreferences(includePainCharts = false)), canvas)
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
        val on = RecordingRenderer().also { ReportPdf.render(report(episodes), it) }
        assertTrue("Possible Triggers Noted" in on.texts)
        assertTrue("1 of 2 episodes (50%)" in on.texts)
        assertTrue(on.texts.any { it.contains("do not show a cause") && it.contains("1 of 2 episodes have no triggers noted") })
        assertTrue("TRIGGERS" in on.texts)

        val off = RecordingRenderer().also { ReportPdf.render(report(episodes, ExportPreferences(includeTriggers = false)), it) }
        assertFalse("Possible Triggers Noted" in off.texts)
        assertFalse("TRIGGERS" in off.texts)
    }

    /** Records draw calls. Text wraps at half the font size per character, 1.2 × font size per line. */
    private class RecordingRenderer : PdfRenderer, ReportCanvas {
        override val pageWidth = ReportPdf.PAGE_WIDTH
        override val pageHeight = ReportPdf.PAGE_HEIGHT
        var pages = 0
        var maxContentBottom = 0f
        val texts = mutableListOf<String>()

        override fun render(pageWidth: Float, pageHeight: Float, draw: (ReportCanvas) -> Unit): ByteArray {
            draw(this)
            return ByteArray(0)
        }

        override fun beginPage() { pages++ }

        override fun measureHeight(text: String, style: TextStyle, maxWidth: Float): Float {
            val charsPerLine = (maxWidth / (style.size * 0.5f)).toInt().coerceAtLeast(1)
            return ((text.length + charsPerLine - 1) / charsPerLine).coerceAtLeast(1) * style.size * 1.2f
        }

        override fun drawText(text: String, style: TextStyle, x: Float, y: Float, maxWidth: Float) {
            texts += text
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
}
