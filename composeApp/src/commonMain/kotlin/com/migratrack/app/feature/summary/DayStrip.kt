package com.migratrack.app.feature.summary

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.strip_legend_migraine
import com.migratrack.app.generated.resources.strip_legend_not_tracked
import com.migratrack.app.generated.resources.strip_legend_pain_free
import com.migratrack.app.ui.components.Dot
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import com.migratrack.domain.analytics.SummaryDay
import org.jetbrains.compose.resources.stringResource

enum class StripDay(val color: Color) {
    Attack(MtColors.Pink),
    PainFree(MtColors.Green),
    NotTracked(MtColors.Control),
}

fun SummaryDay.toStripDay(): StripDay = when {
    isMigraineDay -> StripDay.Attack
    isTracked -> StripDay.PainFree
    else -> StripDay.NotTracked
}

private const val COLUMNS = 10

/** One dot per day in rows of 10, oldest first. The last day is today and has an outline. */
@Composable
fun DayStrip(days: List<StripDay>, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        days.chunked(COLUMNS).forEachIndexed { row, week ->
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                week.forEachIndexed { column, day ->
                    val isToday = row * COLUMNS + column == days.lastIndex
                    Box(
                        Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .then(if (isToday) Modifier.border(1.5.dp, MtColors.TextPrimary, CircleShape).padding(3.dp) else Modifier),
                    ) {
                        Box(Modifier.fillMaxSize().background(day.color, CircleShape))
                    }
                }
                // Keeps the dots of a short last row as wide as the others.
                repeat(COLUMNS - week.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
fun DayStripLegend(modifier: Modifier = Modifier) {
    FlowRow(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MtSpacing.md),
        verticalArrangement = Arrangement.spacedBy(MtSpacing.xs),
    ) {
        LegendItem(stringResource(Res.string.strip_legend_migraine), StripDay.Attack.color)
        LegendItem(stringResource(Res.string.strip_legend_pain_free), StripDay.PainFree.color)
        LegendItem(stringResource(Res.string.strip_legend_not_tracked), StripDay.NotTracked.color)
    }
}

@Composable
private fun LegendItem(text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Dot(color, size = 10.dp, glow = false)
        Spacer(Modifier.width(6.dp))
        MtText(text, MtTheme.type.subhead, color = MtColors.Caption, maxLines = 1)
    }
}
