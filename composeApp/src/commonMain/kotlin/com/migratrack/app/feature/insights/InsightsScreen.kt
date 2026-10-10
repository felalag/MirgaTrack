package com.migratrack.app.feature.insights

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.migratrack.app.di.LocalAppGraph
import com.migratrack.app.ui.components.Card
import com.migratrack.app.ui.components.Dot
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.Page
import com.migratrack.app.ui.components.SegmentedControl
import com.migratrack.app.ui.components.glow
import com.migratrack.app.ui.components.pressable
import com.migratrack.app.ui.icons.Icon
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import com.migratrack.domain.analytics.DateText
import com.migratrack.domain.analytics.Insights
import com.migratrack.domain.analytics.InsightsBar
import com.migratrack.domain.analytics.InsightsPeriod
import com.migratrack.domain.model.EpisodeId
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlin.math.abs

@Composable
fun InsightsScreen(
    onOpenSettings: () -> Unit,
    onEditEpisode: (EpisodeId) -> Unit,
    onLogOn: (LocalDate) -> Unit,
) {
    val graph = LocalAppGraph.current
    val viewModel = viewModel { InsightsViewModel(graph) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Page(title = "Insights", onSettings = onOpenSettings) {
        val current = state ?: return@Page
        Row(verticalAlignment = Alignment.CenterVertically) {
            SegmentedControl(
                options = InsightsPeriod.entries,
                selected = current.period,
                label = { it.shortLabel },
                onSelect = viewModel::selectPeriod,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(MtSpacing.sm))
            MonthPill(current.insights.month, current.calendarOpen, onClick = viewModel::toggleCalendar)
        }
        Column {
            AttackCard(current.insights)
            AnimatedVisibility(
                visible = current.calendarOpen,
                enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
            ) {
                Column {
                    Spacer(Modifier.height(MtSpacing.lg))
                    CalendarCard(
                        calendar = current.calendar,
                        selected = current.selected,
                        day = current.day,
                        timeZone = graph.timeZone(),
                        canShowNextMonth = current.canShowNextMonth,
                        onShowMonth = viewModel::showMonth,
                        onPickMonth = viewModel::pickMonth,
                        onSelect = viewModel::selectDay,
                        onEdit = { day -> viewModel.editEpisode(day, onEditEpisode) },
                        onDelete = viewModel::deleteEpisode,
                        onLog = onLogOn,
                    )
                }
            }
        }
    }
}

/** "Oct 2026" next to the period control; it opens and closes the calendar. */
@Composable
private fun MonthPill(month: YearMonth, expanded: Boolean, onClick: () -> Unit) {
    val rotation by animateFloatAsState(if (expanded) 180f else 0f)
    val background by animateColorAsState(if (expanded) MtColors.ControlHigh else MtColors.Card)
    Row(
        Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .border(1.dp, Color.White.copy(alpha = 0.1f), CircleShape)
            .semantics(mergeDescendants = true) {
                contentDescription = "Calendar, ${DateText.monthYear(month)}"
                stateDescription = if (expanded) "Expanded" else "Collapsed"
                role = Role.Button
            }
            .pressable(pressedScale = 0.95f, onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(Symbols.CalendarToday, tint = MtColors.PinkSoft, size = 18.dp)
        Spacer(Modifier.width(6.dp))
        MtText(DateText.shortMonthYear(month), MtTheme.type.subhead.copy(fontWeight = FontWeight.SemiBold), color = MtColors.TextPrimary, maxLines = 1)
        Spacer(Modifier.width(6.dp))
        Icon(Symbols.ExpandMore, tint = MtColors.TextSecondary, size = 16.dp, modifier = Modifier.rotate(rotation))
    }
}

private val InsightsPeriod.shortLabel: String
    get() = when (this) {
        InsightsPeriod.Week -> "W"
        InsightsPeriod.Month -> "M"
        InsightsPeriod.SixMonths -> "6M"
        InsightsPeriod.Year -> "Y"
    }

private fun Insights.headline(): String {
    val noun = if (attackCount == 1) "Attack" else "Attacks"
    return when (period) {
        InsightsPeriod.Week -> if (includesToday) "$noun this Week" else "$noun in Week of ${DateText.monthDay(range.start)}"
        InsightsPeriod.Month -> "$noun in ${DateText.shortMonth(month.month)}"
        InsightsPeriod.SixMonths -> "$noun in 6 Mos"
        InsightsPeriod.Year -> "$noun in ${range.start.year}"
    }
}

@Composable
private fun AttackCard(insights: Insights) {
    Card {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
            Row(
                Modifier.weight(1f).semantics(mergeDescendants = true) {},
                verticalAlignment = Alignment.Bottom,
            ) {
                MtText(insights.attackCount.toString(), MtTheme.type.hero)
                Spacer(Modifier.width(MtSpacing.xs))
                MtText(insights.headline(), MtTheme.type.headline, color = MtColors.Caption, modifier = Modifier.padding(bottom = 8.dp))
            }
            insights.trendPercent?.let { TrendBadge(it) }
        }
        Spacer(Modifier.height(MtSpacing.md))
        Row(
            Modifier.fillMaxWidth().height(170.dp).padding(horizontal = MtSpacing.sm),
            horizontalArrangement = Arrangement.spacedBy(if (insights.bars.size > 7) 6.dp else 12.dp),
        ) {
            insights.bars.forEach { bar ->
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    AttackBar(bar, insights.period, Modifier.weight(1f))
                    Spacer(Modifier.height(6.dp))
                    MtText(
                        bar.label,
                        MtTheme.type.subhead.copy(fontWeight = if (bar.isCurrent) FontWeight.Bold else FontWeight.Medium),
                        color = if (bar.isCurrent) MtColors.TextPrimary else MtColors.Caption,
                        maxLines = 1,
                    )
                }
            }
        }
        Spacer(Modifier.height(MtSpacing.md))
        Row(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(MtRadius.sm)).background(MtColors.CardRaised.copy(alpha = 0.4f)).padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(MtSpacing.lg, Alignment.CenterHorizontally),
        ) {
            LegendItem("Migraine Day", MtColors.Pink)
            LegendItem("Pain-Free Day", MtColors.Green)
        }
    }
}

@Composable
private fun LegendItem(text: String, color: androidx.compose.ui.graphics.Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Dot(color)
        Spacer(Modifier.width(6.dp))
        MtText(text, MtTheme.type.subhead.copy(fontWeight = FontWeight.SemiBold), color = MtColors.Caption)
    }
}

@Composable
private fun TrendBadge(percent: Int) {
    val fewer = percent <= 0
    val color = if (fewer) MtColors.Green else MtColors.Pink
    Row(
        Modifier
            .clip(CircleShape)
            .background(MtColors.ControlHigh.copy(alpha = 0.8f))
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .semantics(mergeDescendants = true) {
                contentDescription = "${abs(percent)} percent ${if (fewer) "fewer" else "more"} attacks than the previous period"
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(if (fewer) Symbols.TrendingDown else Symbols.TrendingUp, tint = color, size = 16.dp)
        Spacer(Modifier.width(4.dp))
        MtText("${abs(percent)}%", MtTheme.type.subhead.copy(fontWeight = FontWeight.Bold), color = color)
    }
}

/**
 * One stacked pill: migraine share on top in pink, pain-free share below in green.
 * In the week view a single day has no share, so the pink part shows that day's peak pain instead.
 */
@Composable
private fun AttackBar(bar: InsightsBar, period: InsightsPeriod, modifier: Modifier) {
    val target = when {
        bar.elapsedDays == 0 -> 0f
        period == InsightsPeriod.Week -> (bar.peakIntensity?.value ?: 0) / 10f
        else -> bar.migraineDays.toFloat() / bar.elapsedDays
    }
    val share by animateFloatAsState(target, tween(600))
    val description = when {
        bar.elapsedDays == 0 -> "${bar.label}: no data yet"
        bar.migraineDays == 0 -> "${bar.label}: pain-free"
        else -> "${bar.label}: ${bar.migraineDays} migraine ${if (bar.migraineDays == 1) "day" else "days"}, peak ${bar.peakIntensity?.value} of 10"
    }
    Column(
        modifier.widthIn(max = 28.dp).fillMaxWidth().semantics { contentDescription = description },
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Bottom),
    ) {
        when {
            bar.elapsedDays == 0 -> Box(Modifier.fillMaxWidth().weight(1f).background(MtColors.ControlHigh.copy(alpha = 0.4f), CircleShape))
            bar.migraineDays == 0 -> {
                if (period != InsightsPeriod.Week) {
                    Box(Modifier.fillMaxWidth().height(8.dp).background(MtColors.ControlHigh.copy(alpha = 0.4f), CircleShape))
                }
                GreenPill(Modifier.weight(1f))
            }
            else -> {
                // A floor keeps a single migraine day visible in a month-long bar.
                val pink = share.coerceIn(0.14f, 0.86f)
                Box(Modifier.fillMaxWidth().weight(pink).background(MtColors.Pink, CircleShape))
                GreenPill(Modifier.weight(1f - pink))
            }
        }
    }
}

@Composable
private fun GreenPill(modifier: Modifier) {
    Box(modifier.fillMaxWidth().glow(MtColors.Green, 10.dp, alpha = 0.35f).background(MtColors.Green, CircleShape))
}
