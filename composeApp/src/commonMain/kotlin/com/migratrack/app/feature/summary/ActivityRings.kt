package com.migratrack.app.feature.summary

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtTheme

private val Track = Color(0xFF2A2A2C)

/**
 * Two concentric Apple Fitness rings with a centre score.
 * The rings sweep in from zero, as the Activity rings do when the screen opens.
 */
@Composable
fun ActivityRings(
    outer: Float,
    inner: Float?,
    outerColor: Color,
    innerColor: Color,
    centre: String,
    description: String,
    modifier: Modifier = Modifier,
    size: Dp = 240.dp,
) {
    val outerProgress = remember { Animatable(0f) }
    val innerProgress = remember { Animatable(0f) }
    LaunchedEffect(outer) { outerProgress.animateTo(outer.coerceIn(0f, 1f), tween(1_000, easing = FastOutSlowInEasing)) }
    LaunchedEffect(inner) { innerProgress.animateTo((inner ?: 0f).coerceIn(0f, 1f), tween(1_000, 150, FastOutSlowInEasing)) }

    Box(modifier.size(size).semantics { contentDescription = description }, contentAlignment = Alignment.Center) {
        Canvas(Modifier.size(size)) {
            // The SVG of the screens uses radii 88 and 68 with a 12 point stroke in a 240 point box.
            val scale = this.size.minDimension / 240f
            val stroke = 12f * scale
            ring(88f * scale, stroke, outerProgress.value, outerColor)
            ring(68f * scale, stroke, innerProgress.value, innerColor)
        }
        MtText(centre, MtTheme.type.display.copy(fontSize = 46.sp, lineHeight = 46.sp), color = MtColors.TextPrimary)
    }
}

private fun DrawScope.ring(radius: Float, stroke: Float, progress: Float, color: Color) {
    val topLeft = Offset(center.x - radius, center.y - radius)
    val arcSize = Size(radius * 2, radius * 2)
    drawArc(Track, 0f, 360f, useCenter = false, topLeft = topLeft, size = arcSize, style = Stroke(stroke))
    if (progress <= 0f) return
    val sweep = 360f * progress
    // A soft halo: the same arc, wider and fainter, under the solid stroke.
    for ((extra, alpha) in listOf(10f to 0.08f, 6f to 0.12f, 3f to 0.18f)) {
        drawArc(color.copy(alpha = alpha), -90f, sweep, false, topLeft, arcSize, style = Stroke(stroke + extra, cap = StrokeCap.Round))
    }
    drawArc(color, -90f, sweep, false, topLeft, arcSize, style = Stroke(stroke, cap = StrokeCap.Round))
}
