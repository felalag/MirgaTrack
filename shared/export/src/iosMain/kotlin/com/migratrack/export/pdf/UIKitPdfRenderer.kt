package com.migratrack.export.pdf

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.create
import platform.UIKit.NSFontAttributeName
import platform.UIKit.NSForegroundColorAttributeName
import platform.UIKit.NSLineBreakByWordWrapping
import platform.UIKit.NSLineBreakStrategyHangulWordPriority
import platform.UIKit.NSMutableParagraphStyle
import platform.UIKit.NSParagraphStyleAttributeName
import platform.UIKit.NSStringDrawingUsesLineFragmentOrigin
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.NSTextAlignmentLeft
import platform.UIKit.NSTextAlignmentNatural
import platform.UIKit.NSTextAlignmentRight
import platform.UIKit.UIBezierPath
import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UIFontWeightBold
import platform.UIKit.UIFontWeightRegular
import platform.UIKit.UIFontWeightSemibold
import platform.UIKit.UIGraphicsPDFRenderer
import platform.UIKit.UIGraphicsPDFRendererContext
import platform.UIKit.UIGraphicsPDFRendererFormat
import platform.UIKit.boundingRectWithSize
import platform.UIKit.drawWithRect
import platform.posix.memcpy
import kotlin.math.ceil

/** Renders with UIGraphicsPDFRenderer and the system font, so every script and emoji draws correctly. */
@OptIn(ExperimentalForeignApi::class)
class UIKitPdfRenderer : PdfRenderer {
    override fun render(pageWidth: Float, pageHeight: Float, draw: (ReportCanvas) -> Unit): ByteArray {
        val bounds = CGRectMake(0.0, 0.0, pageWidth.toDouble(), pageHeight.toDouble())
        val renderer = UIGraphicsPDFRenderer(bounds = bounds, format = UIGraphicsPDFRendererFormat.defaultFormat())
        val data = renderer.PDFDataWithActions { context ->
            draw(UIKitCanvas(requireNotNull(context), pageWidth, pageHeight))
        }
        return data.toByteArray()
    }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private class UIKitCanvas(
    private val context: UIGraphicsPDFRendererContext,
    override val pageWidth: Float,
    override val pageHeight: Float,
) : ReportCanvas {

    override fun beginPage() = context.beginPage()

    override fun measureHeight(text: String, style: TextStyle, maxWidth: Float): Float {
        val rect = NSString.create(string = text).boundingRectWithSize(
            size = CGSizeMake(maxWidth.toDouble(), Double.MAX_VALUE),
            options = NSStringDrawingUsesLineFragmentOrigin,
            attributes = attributes(style),
            context = null,
        )
        return rect.useContents { ceil(size.height).toFloat() }
    }

    override fun drawText(text: String, style: TextStyle, x: Float, y: Float, maxWidth: Float) {
        NSString.create(string = text).drawWithRect(
            rect = CGRectMake(x.toDouble(), y.toDouble(), maxWidth.toDouble(), pageHeight.toDouble()),
            options = NSStringDrawingUsesLineFragmentOrigin,
            attributes = attributes(style),
            context = null,
        )
    }

    override fun fillRect(x: Float, y: Float, width: Float, height: Float, color: Long, cornerRadius: Float) {
        val rect = CGRectMake(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())
        val path = if (cornerRadius > 0f) {
            UIBezierPath.bezierPathWithRoundedRect(rect, cornerRadius.toDouble())
        } else {
            UIBezierPath.bezierPathWithRect(rect)
        }
        color.toUIColor().setFill()
        path.fill()
    }

    override fun strokeLine(x1: Float, y1: Float, x2: Float, y2: Float, color: Long, width: Float) {
        val path = UIBezierPath()
        path.moveToPoint(CGPointMake(x1.toDouble(), y1.toDouble()))
        path.addLineToPoint(CGPointMake(x2.toDouble(), y2.toDouble()))
        path.lineWidth = width.toDouble()
        color.toUIColor().setStroke()
        path.stroke()
    }

    private fun attributes(style: TextStyle): Map<Any?, Any?> {
        val weight = when (style.weight) {
            TextStyle.Weight.Regular -> UIFontWeightRegular
            TextStyle.Weight.Semibold -> UIFontWeightSemibold
            TextStyle.Weight.Bold -> UIFontWeightBold
        }
        val paragraph = NSMutableParagraphStyle().apply {
            setLineBreakMode(NSLineBreakByWordWrapping)
            // Without it, Korean breaks inside a word ("수 / 마트립탄"); the strategy changes no other script.
            setLineBreakStrategy(NSLineBreakStrategyHangulWordPriority)
            setAlignment(
                when (style.align) {
                    // Natural follows the script of the text, so a mirrored layout needs the edges by name.
                    TextStyle.Align.Start -> if (style.rightToLeft) NSTextAlignmentRight else NSTextAlignmentNatural
                    TextStyle.Align.Center -> NSTextAlignmentCenter
                    TextStyle.Align.End -> if (style.rightToLeft) NSTextAlignmentLeft else NSTextAlignmentRight
                },
            )
        }
        return mapOf(
            NSFontAttributeName to UIFont.systemFontOfSize(style.size.toDouble(), weight),
            NSForegroundColorAttributeName to style.color.toUIColor(),
            NSParagraphStyleAttributeName to paragraph,
        )
    }
}

private fun Long.toUIColor(): UIColor = UIColor.colorWithRed(
    red = ((this shr 16) and 0xFF) / 255.0,
    green = ((this shr 8) and 0xFF) / 255.0,
    blue = (this and 0xFF) / 255.0,
    alpha = ((this shr 24) and 0xFF) / 255.0,
)

@OptIn(ExperimentalForeignApi::class)
internal fun NSData.toByteArray(): ByteArray {
    val size = length.toInt()
    val result = ByteArray(size)
    if (size > 0) result.usePinned { memcpy(it.addressOf(0), bytes, length) }
    return result
}
