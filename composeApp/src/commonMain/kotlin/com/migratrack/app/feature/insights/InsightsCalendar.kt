package com.migratrack.app.feature.insights

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.calendar_choose_month_a11y
import com.migratrack.app.generated.resources.calendar_day_a11y
import com.migratrack.app.generated.resources.calendar_day_episode_count
import com.migratrack.app.generated.resources.calendar_day_episodes
import com.migratrack.app.generated.resources.calendar_day_header
import com.migratrack.app.generated.resources.calendar_day_no_episodes
import com.migratrack.app.generated.resources.calendar_day_not_available
import com.migratrack.app.generated.resources.calendar_day_not_tracked
import com.migratrack.app.generated.resources.calendar_day_today_a11y
import com.migratrack.app.generated.resources.calendar_edit_episode_details
import com.migratrack.app.generated.resources.calendar_medication
import com.migratrack.app.generated.resources.calendar_migraine_days
import com.migratrack.app.generated.resources.calendar_next_month
import com.migratrack.app.generated.resources.calendar_out_of_ten
import com.migratrack.app.generated.resources.calendar_peak_intensity
import com.migratrack.app.generated.resources.calendar_previous_month
import com.migratrack.app.generated.resources.common_delete_episode
import com.migratrack.app.generated.resources.common_log_migraine_episode
import com.migratrack.app.generated.resources.common_none
import com.migratrack.app.ui.components.Card
import com.migratrack.app.ui.components.DestructiveButton
import com.migratrack.app.ui.components.Dot
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.PrimaryButton
import com.migratrack.app.ui.components.glow
import com.migratrack.app.ui.components.pressable
import com.migratrack.app.ui.icons.Icon
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import com.migratrack.domain.analytics.CalendarDay
import com.migratrack.domain.analytics.DateText
import com.migratrack.domain.analytics.DayEpisodes
import com.migratrack.domain.analytics.MonthCalendar
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minusMonth
import kotlinx.datetime.plusMonth
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

private val WEEKDAY_INITIALS = listOf("M", "T", "W", "T", "F", "S", "S")
private val CellGap = 6.dp

/**
 * The month calendar under the Insights chart. A horizontal swipe on the grid moves by a month,
 * as in the system calendars; a tap on the title opens month and year wheels.
 */
@Composable
fun CalendarCard(
    calendar: MonthCalendar,
    selected: LocalDate?,
    day: DayEpisodes?,
    timeZone: TimeZone,
    canShowNextMonth: Boolean,
    onShowMonth: (YearMonth) -> Unit,
    onPickMonth: () -> Unit,
    onSelect: (LocalDate) -> Unit,
    onEdit: (DayEpisodes) -> Unit,
    onDelete: (DayEpisodes) -> Unit,
    onLog: (LocalDate) -> Unit,
) {
    val previousMonth = stringResource(Res.string.calendar_previous_month)
    val nextMonth = stringResource(Res.string.calendar_next_month)
    Card(bordered = true, contentPadding = PaddingValues(MtSpacing.md)) {
        Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.md)) {
            CalendarHeader(calendar, onPickMonth)
            Column(
                Modifier
                    .semantics {
                        customActions = buildList {
                            add(CustomAccessibilityAction(previousMonth) { onShowMonth(calendar.month.minusMonth()); true })
                            if (canShowNextMonth) add(CustomAccessibilityAction(nextMonth) { onShowMonth(calendar.month.plusMonth()); true })
                        }
                    }
                    .pointerInput(calendar.month, canShowNextMonth) {
                        var travel = 0f
                        detectHorizontalDragGestures(
                            onDragStart = { travel = 0f },
                            onDragEnd = {
                                val threshold = 48.dp.toPx()
                                when {
                                    travel < -threshold && canShowNextMonth -> onShowMonth(calendar.month.plusMonth())
                                    travel > threshold -> onShowMonth(calendar.month.minusMonth())
                                }
                            },
                        ) { change, amount ->
                            travel += amount
                            change.consume()
                        }
                    },
                verticalArrangement = Arrangement.spacedBy(MtSpacing.xs),
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(CellGap)) {
                    WEEKDAY_INITIALS.forEach { initial ->
                        MtText(
                            initial,
                            MtTheme.type.caption,
                            color = MtColors.TextSecondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f).padding(vertical = MtSpacing.xs),
                        )
                    }
                }
                AnimatedContent(
                    targetState = calendar,
                    contentKey = { it.month },
                    transitionSpec = {
                        val forward = targetState.month > initialState.month
                        (slideInHorizontally { width -> if (forward) width else -width } + fadeIn()) togetherWith
                            (slideOutHorizontally { width -> if (forward) -width else width } + fadeOut()) using
                            SizeTransform(clip = false)
                    },
                ) { shown ->
                    DayGrid(shown, selected, onSelect)
                }
            }
            if (day != null) DayPreview(day, timeZone, onEdit, onDelete, onLog)
        }
    }
}

@Composable
private fun CalendarHeader(calendar: MonthCalendar, onPickMonth: () -> Unit) {
    val description = stringResource(Res.string.calendar_choose_month_a11y, DateText.monthYear(calendar.month))
    Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.xs)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                Modifier
                    .weight(1f)
                    .semantics(mergeDescendants = true) {
                        contentDescription = description
                        role = Role.Button
                    }
                    .pressable(pressedScale = 0.97f, onClick = onPickMonth),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Symbols.CalendarMonth, tint = MtColors.PinkSoft, size = 20.dp)
                Spacer(Modifier.width(MtSpacing.sm))
                MtText(
                    DateText.monthYear(calendar.month),
                    MtTheme.type.title2.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    modifier = Modifier.weight(1f, fill = false),
                )
                Icon(Symbols.ChevronRight, tint = MtColors.TextSecondary, size = 18.dp)
            }
            Spacer(Modifier.width(MtSpacing.sm))
            AttackDaysPill(calendar.attackDays)
        }
        Box(Modifier.fillMaxWidth().height(0.5.dp).background(Color.White.copy(alpha = 0.05f)))
    }
}

@Composable
private fun AttackDaysPill(count: Int) {
    Row(
        Modifier
            .clip(CircleShape)
            .background(MtColors.CardRaised)
            .border(1.dp, Color.White.copy(alpha = 0.05f), CircleShape)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Dot(MtColors.Pink, size = 8.dp)
        Spacer(Modifier.width(6.dp))
        MtText(
            pluralStringResource(Res.plurals.calendar_migraine_days, count, count),
            MtTheme.type.caption.copy(fontWeight = FontWeight.SemiBold, letterSpacing = MtTheme.type.subhead.letterSpacing),
            color = MtColors.TextPrimary,
            maxLines = 1,
        )
    }
}

@Composable
private fun DayGrid(calendar: MonthCalendar, selected: LocalDate?, onSelect: (LocalDate) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(CellGap)) {
        calendar.days.chunked(7).forEach { week ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(CellGap)) {
                week.forEach { day -> DayCell(day, day.date == selected, onSelect, Modifier.weight(1f)) }
                repeat(7 - week.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

/** A day of the grid, drawn as the screens draw attack days, the picked day, and the rest. */
@Composable
private fun DayCell(day: CalendarDay, selected: Boolean, onSelect: (LocalDate) -> Unit, modifier: Modifier) {
    val haptics = LocalHapticFeedback.current
    val shape = RoundedCornerShape(MtRadius.sm)
    val attack = day.inMonth && day.isMigraineDay
    val selectable = day.inMonth && !day.isFuture
    val fill = when {
        selected && attack -> MtColors.Pink
        selected -> MtColors.ControlHigh
        attack -> MtColors.Pink.copy(alpha = 0.15f)
        day.isToday -> MtColors.Control
        else -> Color.Transparent
    }
    val textColor = when {
        !day.inMonth -> MtColors.TextTertiary
        day.isFuture -> MtColors.TextSecondary
        selected -> MtColors.TextPrimary
        attack -> MtColors.PinkSoft
        !day.isTracked -> MtColors.TextSecondary
        else -> MtColors.TextPrimary
    }
    val ring = if (attack) MtColors.Pink else Color.White.copy(alpha = 0.8f)
    val spoken = day.spoken()
    Box(
        modifier
            .height(36.dp)
            .then(
                if (selectable) {
                    Modifier
                        .semantics(mergeDescendants = true) {
                            contentDescription = spoken
                            this.selected = selected
                        }
                        .pressable(pressedScale = 0.95f) {
                            haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
                            onSelect(day.date)
                        }
                } else {
                    Modifier.semantics { contentDescription = spoken }
                },
            )
            .then(if (selected) Modifier.ring(ring) else Modifier)
            .then(if (selected && attack) Modifier.glow(MtColors.Pink, 12.dp, cornerRadius = MtRadius.sm, alpha = 0.7f) else Modifier)
            .clip(shape)
            .background(fill)
            .then(if (attack && !selected) Modifier.border(1.dp, MtColors.Pink.copy(alpha = 0.4f), shape) else Modifier),
        contentAlignment = Alignment.Center,
    ) {
        MtText(
            day.date.day.toString(),
            MtTheme.type.subhead.copy(fontWeight = if (attack || selected) FontWeight.Bold else FontWeight.SemiBold),
            color = textColor,
        )
        if (attack) {
            Box(
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(6.dp)
                    .then(if (selected) Modifier else Modifier.glow(MtColors.Pink, 4.dp, alpha = 0.8f))
                    .background(if (selected) Color.White else MtColors.Pink, CircleShape),
            )
        }
    }
}

/** A 2 pt ring 2 pt outside the cell, as CSS ring-offset draws it. */
private fun Modifier.ring(color: Color): Modifier = drawBehind {
    val stroke = 2.dp.toPx()
    val inset = 2.dp.toPx() + stroke / 2
    drawRoundRect(
        color = color,
        topLeft = Offset(-inset, -inset),
        size = Size(size.width + 2 * inset, size.height + 2 * inset),
        cornerRadius = CornerRadius(MtRadius.sm.toPx() + inset),
        style = Stroke(stroke),
    )
}

@Composable
private fun CalendarDay.spoken(): String {
    val name = DateText.weekdayMonthDay(date)
    val detail = when {
        !inMonth || isFuture -> stringResource(Res.string.calendar_day_not_available)
        !isTracked -> stringResource(Res.string.calendar_day_not_tracked)
        attacks == 0 -> stringResource(Res.string.calendar_day_no_episodes)
        else -> pluralStringResource(Res.plurals.calendar_day_episodes, attacks, attacks, peak?.value ?: 0)
    }
    return stringResource(if (isToday) Res.string.calendar_day_today_a11y else Res.string.calendar_day_a11y, name, detail)
}

/** The picked day: its episodes with Edit and Delete, or Log for a day without one. */
@Composable
private fun DayPreview(
    day: DayEpisodes,
    timeZone: TimeZone,
    onEdit: (DayEpisodes) -> Unit,
    onDelete: (DayEpisodes) -> Unit,
    onLog: (LocalDate) -> Unit,
) {
    if (day.episodes.isEmpty()) {
        PrimaryButton(
            stringResource(Res.string.common_log_migraine_episode),
            onClick = { onLog(day.date) },
            icon = Symbols.Add,
            height = 56.dp,
            modifier = Modifier.padding(top = MtSpacing.sm),
        )
        return
    }
    val shape = RoundedCornerShape(MtRadius.md)
    Column(
        Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(MtColors.CardRaised)
            .border(1.dp, Color.White.copy(alpha = 0.1f), shape)
            .padding(MtSpacing.md),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        val detail = day.episodes.singleOrNull()
            ?.let { DateText.time(it.startedAt.toLocalDateTime(timeZone)) }
            ?: pluralStringResource(Res.plurals.calendar_day_episode_count, day.episodes.size, day.episodes.size)
        MtText(stringResource(Res.string.calendar_day_header, DateText.weekdayMonthDay(day.date), detail).uppercase(), MtTheme.type.caption, color = MtColors.BrandLabel)
        PreviewTile(Symbols.Bolt, stringResource(Res.string.calendar_peak_intensity)) {
            Row(verticalAlignment = Alignment.Bottom) {
                MtText(day.peak?.value?.toString() ?: "–", MtTheme.type.bodyStrong.copy(fontWeight = FontWeight.Bold), color = MtColors.PinkSoft)
                Spacer(Modifier.width(MtSpacing.xs))
                MtText(stringResource(Res.string.calendar_out_of_ten), MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal), color = MtColors.TextSecondary)
            }
        }
        PreviewTile(Symbols.Medication, stringResource(Res.string.calendar_medication)) {
            MtText(day.medications.joinToString(", ").ifEmpty { stringResource(Res.string.common_none) }, MtTheme.type.bodyStrong, color = MtColors.TextPrimary)
        }
        PrimaryButton(stringResource(Res.string.calendar_edit_episode_details), onClick = { onEdit(day) }, icon = Symbols.Edit, height = 56.dp)
        DestructiveButton(stringResource(Res.string.common_delete_episode), onClick = { onDelete(day) })
    }
}

@Composable
private fun PreviewTile(icon: ImageVector, label: String, value: @Composable () -> Unit) {
    val shape = RoundedCornerShape(MtRadius.sm)
    Row(
        Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(MtColors.Card)
            .border(1.dp, Color.White.copy(alpha = 0.05f), shape)
            .semantics(mergeDescendants = true) {}
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, tint = MtColors.PinkSoft, size = 18.dp)
        Spacer(Modifier.width(MtSpacing.sm))
        Column {
            MtText(label, MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal), color = MtColors.TextSecondary)
            value()
        }
    }
}
