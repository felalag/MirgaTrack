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
) {
    enum class Weight { Regular, Semibold, Bold }
    enum class Align { Start, Center, End }
}
