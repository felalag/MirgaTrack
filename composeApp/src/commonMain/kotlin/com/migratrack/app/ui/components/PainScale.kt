package com.migratrack.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.pain_scale_high
import com.migratrack.app.generated.resources.pain_scale_label
import com.migratrack.app.generated.resources.pain_scale_low
import com.migratrack.app.generated.resources.pain_scale_middle
import com.migratrack.app.generated.resources.pain_scale_value
import com.migratrack.app.ui.Numbers
import com.migratrack.app.ui.stringResource
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtTheme
import com.migratrack.domain.model.PainIntensity
import kotlin.math.roundToInt

private val DimGreen = Color(0xFF3C5222)
private val DimSlate = Color(0xFF34464A)
private val EmptySegment = Color(0xFF24272C)

/**
 * The ten-segment pain selector of the Log Episode and onboarding screens.
 * Tap a segment or drag across the bar; each change ticks a selection haptic.
 */
@Composable
fun PainScale(value: PainIntensity, onValueChange: (PainIntensity) -> Unit, modifier: Modifier = Modifier) {
    val haptics = LocalHapticFeedback.current
    val current by rememberUpdatedState(value)
    val onChange by rememberUpdatedState(onValueChange)
    fun select(level: Int) {
        val clamped = level.coerceIn(PainIntensity.RANGE)
        if (clamped != current.value) {
            haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
            onChange(PainIntensity(clamped))
        }
    }

    val rtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val label = stringResource(Res.string.pain_scale_label)
    val state = stringResource(Res.string.pain_scale_value, Numbers.integer(value.value))
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .semantics {
                    contentDescription = label
                    stateDescription = state
                    setProgress { target ->
                        select(target.roundToInt())
                        true
                    }
                }
                .pointerInput(rtl) {
                    detectTapGestures { offset -> select(levelAt(offset.x, size.width, rtl)) }
                }
                .pointerInput(rtl) {
                    detectHorizontalDragGestures { change, _ ->
                        select(levelAt(change.position.x, size.width, rtl))
                    }
                },
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            for (level in PainIntensity.RANGE) {
                Segment(level, value.value, Modifier.weight(1f))
            }
        }
        // Each label gets at most a third of the row, so a long translation wraps and does not squeeze the last one.
        Row(Modifier.fillMaxWidth().padding(horizontal = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf(
                stringResource(Res.string.pain_scale_low, Numbers.integer(1)),
                stringResource(Res.string.pain_scale_middle, Numbers.integer(5)),
                stringResource(Res.string.pain_scale_high, Numbers.integer(10)),
            ).forEach {
                MtText(it, MtTheme.type.subhead, Modifier.weight(1f, fill = false), color = MtColors.TextSecondary)
            }
        }
    }
}

/** The pointer sees the x axis from the left edge, but the mirrored row puts level 1 on the right. */
internal fun levelAt(x: Float, width: Int, rtl: Boolean): Int {
    val fromStart = if (rtl) width - x else x
    return (fromStart / width * 10).toInt() + 1
}

@Composable
private fun Segment(level: Int, selected: Int, modifier: Modifier) {
    // The screens color the bar by fixed zones: 1–2 calm, 3–5 noticeable, 6–10 severe.
    val zoneBright = when {
        level <= 2 -> MtColors.Green
        level <= 5 -> MtColors.Cyan
        else -> MtColors.Pink
    }
    val zoneDim = when {
        level <= 2 -> DimGreen
        level <= 5 -> DimSlate
        else -> MtColors.Pink.copy(alpha = 0.8f)
    }
    val isSelected = level == selected
    val color by animateColorAsState(
        when {
            isSelected -> zoneBright
            level < selected -> zoneDim
            else -> EmptySegment
        },
    )
    val height by animateDpAsState(if (isSelected) 44.dp else 40.dp)
    val shape = RoundedCornerShape(6.dp)
    Box(
        modifier = modifier
            .height(height)
            .then(if (isSelected) Modifier.glow(zoneBright, radius = 12.dp, cornerRadius = 6.dp, alpha = 0.5f) else Modifier)
            .clip(shape)
            .background(color, shape),
        contentAlignment = Alignment.Center,
    ) {
        if (isSelected) {
            MtText(
                Numbers.integer(level),
                MtTheme.type.number.copy(fontWeight = FontWeight.Bold, fontSize = MtTheme.type.body.fontSize),
                color = if (level <= 5) MtColors.CanvasDeep else MtColors.TextPrimary,
            )
        }
    }
}
