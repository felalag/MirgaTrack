package com.migratrack.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.migratrack.app.ui.theme.MtColors

private const val GRID = 5
private const val ACCENT_ROW = 1
private const val ACCENT_COLUMN = 3

/**
 * The app icon at header size: a 5 × 5 grid of day dots with one bright, glowing dot.
 * The proportions follow the 1024 pt icon, where the dots have a radius of 44 on a pitch of 110.
 */
@Composable
fun BrandMark(modifier: Modifier = Modifier, size: Dp = 22.dp) {
    Canvas(modifier.size(size)) {
        val pitch = this.size.minDimension / 4.8f
        val radius = pitch * 0.4f
        val origin = (this.size.minDimension - pitch * (GRID - 1)) / 2
        fun centre(row: Int, column: Int) = Offset(origin + column * pitch, origin + row * pitch)

        for (row in 0 until GRID) {
            for (column in 0 until GRID) {
                drawCircle(MtColors.PinkSoft, radius, centre(row, column))
            }
        }
        // The halo lies over the neighbours, as in the icon, so the accent stays visible at 22 pt.
        val accent = centre(ACCENT_ROW, ACCENT_COLUMN)
        val halo = radius * 2.4f
        drawCircle(Brush.radialGradient(listOf(MtColors.Pink.copy(alpha = 0.5f), MtColors.Pink.copy(alpha = 0f)), accent, halo), halo, accent)
        drawCircle(MtColors.Pink, radius * 1.15f, accent)
    }
}
