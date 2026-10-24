package com.migratrack.export.pdf

/**
 * Drawing primitives for the PDF report. The layout lives in common code; each platform
 * draws with its native text engine (UIKit on iOS, android.graphics later), so any script renders.
 * Coordinates are points with the origin at the top left of the page.
 */
interface ReportCanvas {
    val pageWidth: Float
    val pageHeight: Float

    fun beginPage()

    /** Height of [text] wrapped to [maxWidth]. */
    fun measureHeight(text: String, style: TextStyle, maxWidth: Float): Float

    fun drawText(text: String, style: TextStyle, x: Float, y: Float, maxWidth: Float)

    fun fillRect(x: Float, y: Float, width: Float, height: Float, color: Long, cornerRadius: Float = 0f)

    fun strokeLine(x1: Float, y1: Float, x2: Float, y2: Float, color: Long, width: Float)
}

/**
 * Draws the layout of [canvas] mirrored on the x axis, for a report language that reads right to left.
 * Only the positions mirror: each text keeps its own order of letters and digits.
 */
internal class MirroredCanvas(private val canvas: ReportCanvas) : ReportCanvas by canvas {
    override fun measureHeight(text: String, style: TextStyle, maxWidth: Float) =
        canvas.measureHeight(text, style.copy(rightToLeft = true), maxWidth)

    override fun drawText(text: String, style: TextStyle, x: Float, y: Float, maxWidth: Float) =
        canvas.drawText(text, style.copy(rightToLeft = true), pageWidth - x - maxWidth, y, maxWidth)

    override fun fillRect(x: Float, y: Float, width: Float, height: Float, color: Long, cornerRadius: Float) =
        canvas.fillRect(pageWidth - x - width, y, width, height, color, cornerRadius)

    override fun strokeLine(x1: Float, y1: Float, x2: Float, y2: Float, color: Long, width: Float) =
        canvas.strokeLine(pageWidth - x1, y1, pageWidth - x2, y2, color, width)
}

/** Runs [draw] against a platform PDF context and returns the document bytes. */
fun interface PdfRenderer {
    fun render(pageWidth: Float, pageHeight: Float, draw: (ReportCanvas) -> Unit): ByteArray
}

data class TextStyle(
    val size: Float,
    val weight: Weight = Weight.Regular,
    /** ARGB, for example 0xFF1C1C1E. */
    val color: Long = 0xFF1C1C1E,
    val align: Align = Align.Start,
    /** The paragraph belongs to a right-to-left layout: [Align.Start] is the right edge, [Align.End] the left. */
    val rightToLeft: Boolean = false,
) {
    enum class Weight { Regular, Semibold, Bold }
    enum class Align { Start, Center, End }
}

/**
 * The line height of [text] as a multiple of the font's own. The system fonts of the Indian scripts (SF Devanagari
 * to SF Malayalam, U+0900–U+0DFF) are 1.3 times as tall as SF, but a paragraph takes its line height from SF.
 */
internal fun lineHeightMultiple(text: String): Float = if (text.any { it in '\u0900'..'\u0DFF' }) 1.3f else 1f
