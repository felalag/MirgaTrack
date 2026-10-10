package com.migratrack.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.migratrack.app.ui.icons.Icon
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme

/** Full-width pink pill: "Log Migraine Episode", "Save Episode Log", "Export PDF Report". */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    enabled: Boolean = true,
    height: Dp = 54.dp,
) {
    val background by animateColorAsState(if (enabled) MtColors.Pink else MtColors.Control)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .then(if (enabled) Modifier.glow(MtColors.Pink, radius = 24.dp, alpha = 0.4f) else Modifier)
            .clip(CircleShape)
            .background(background)
            .pressable(enabled = enabled, pressedScale = 0.98f, onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val content = if (enabled) MtColors.TextPrimary else MtColors.TextSecondary
        if (icon != null) {
            Icon(icon, tint = content, size = 24.dp)
            Spacer(Modifier.width(MtSpacing.sm))
        }
        MtText(text, MtTheme.type.headline.copy(fontWeight = FontWeight.Bold), color = content, maxLines = 1)
        if (trailingIcon != null) {
            Spacer(Modifier.width(MtSpacing.sm))
            Icon(trailingIcon, tint = content, size = 22.dp)
        }
    }
}

/** Graphite pill: "Export as Excel (.xlsx)". With [outlined], the lighter "Share" variant. */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconTint: Color = MtColors.TextPrimary,
    outlined: Boolean = false,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(CircleShape)
            .background(if (outlined) MtColors.Control.copy(alpha = 0.6f) else MtColors.Control)
            .then(if (outlined) Modifier.border(1.dp, MtColors.BorderSubtle.copy(alpha = 0.12f), CircleShape) else Modifier)
            .pressable(enabled = enabled, pressedScale = 0.98f, onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Icon(icon, tint = iconTint, size = 20.dp)
            Spacer(Modifier.width(MtSpacing.sm))
        }
        MtText(text, MtTheme.type.subhead.copy(fontWeight = FontWeight.SemiBold), color = MtColors.TextPrimary, maxLines = 1)
    }
}

/** Dark pill with pink text that deletes something: "Delete Episode Log". */
@Composable
fun DestructiveButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(CircleShape)
            .background(MtColors.Card)
            .border(1.dp, Color.White.copy(alpha = 0.1f), CircleShape)
            .pressable(enabled = enabled, pressedScale = 0.98f, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        MtText(text, MtTheme.type.headline, color = MtColors.Pink, maxLines = 1)
    }
}

/** Small accent text button, as the "Clear" links of the Log Episode cards. */
@Composable
fun TextAction(text: String, color: Color, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(width = 56.dp, height = MtSpacing.minTouch)
            .pressable(onClick = onClick),
        contentAlignment = Alignment.CenterEnd,
    ) {
        MtText(text, MtTheme.type.subhead.copy(fontWeight = FontWeight.SemiBold), color = color)
    }
}

/** The iOS switch, drawn to the UISwitch metrics: 51 × 31 track, 27 point thumb. */
@Composable
fun IosSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    val haptics = LocalHapticFeedback.current
    val track by animateColorAsState(if (checked) MtColors.Green else MtColors.SwitchOff)
    val offset by animateDpAsState(
        targetValue = if (checked) 22.dp else 2.dp,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = Spring.StiffnessMedium),
    )
    Box(
        modifier = modifier
            .size(width = 51.dp, height = 31.dp)
            .clip(CircleShape)
            .background(track)
            .semantics {
                role = Role.Switch
                stateDescription = if (checked) "On" else "Off"
            }
            .pressable(role = Role.Switch, pressedScale = 1f) {
                haptics.performHapticFeedback(if (checked) HapticFeedbackType.ToggleOff else HapticFeedbackType.ToggleOn)
                onCheckedChange(!checked)
            },
    ) {
        Box(
            Modifier
                .offset(x = offset, y = 2.dp)
                .size(27.dp)
                .shadow(3.dp, CircleShape)
                .background(Color.White, CircleShape),
        )
    }
}

/** Sliding segmented control of the Insights tab (W / M / 6M / Y). */
@Composable
fun <T> SegmentedControl(
    options: List<T>,
    selected: T,
    label: (T) -> String,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    val haptics = LocalHapticFeedback.current
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(CircleShape)
            .background(MtColors.Card)
            .padding(4.dp),
    ) {
        val segmentWidth = maxWidth / options.size
        val indicatorOffset by animateDpAsState(
            targetValue = segmentWidth * options.indexOf(selected),
            animationSpec = spring(dampingRatio = 0.8f, stiffness = Spring.StiffnessMediumLow),
        )
        Box(
            Modifier
                .offset(x = indicatorOffset)
                .width(segmentWidth)
                .fillMaxHeight()
                .shadow(4.dp, CircleShape)
                .background(MtColors.ControlHigh, CircleShape),
        )
        Row(Modifier.fillMaxWidth().fillMaxHeight()) {
            options.forEach { option ->
                val isSelected = option == selected
                Box(
                    modifier = Modifier
                        .width(segmentWidth)
                        .fillMaxHeight()
                        .semantics { this.selected = isSelected; role = Role.Tab }
                        .pressable(role = Role.Tab, pressedScale = 0.95f) {
                            if (!isSelected) {
                                haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
                                onSelect(option)
                            }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    MtText(
                        label(option),
                        MtTheme.type.subhead.copy(fontWeight = FontWeight.SemiBold),
                        color = if (isSelected) MtColors.TextPrimary else MtColors.Caption,
                    )
                }
            }
        }
    }
}

/** A row of equal chips where one is chosen, as the frequency and duration pickers of onboarding. */
@Composable
fun <T> ChoiceChips(
    options: List<T>,
    selected: T,
    label: (T) -> String,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    grouped: Boolean = false,
) {
    val haptics = LocalHapticFeedback.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (grouped) Modifier.clip(RoundedCornerShape(MtRadius.md)).background(MtColors.CardRaised).padding(4.dp)
                else Modifier,
            ),
        horizontalArrangement = Arrangement.spacedBy(if (grouped) 4.dp else MtSpacing.sm),
    ) {
        options.forEach { option ->
            val isSelected = option == selected
            val background by animateColorAsState(
                when {
                    isSelected -> MtColors.Pink
                    grouped -> Color.Transparent
                    else -> MtColors.CardRaised
                },
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(42.dp)
                    .then(if (isSelected) Modifier.glow(MtColors.Pink, radius = 12.dp, cornerRadius = MtRadius.md, alpha = 0.5f) else Modifier)
                    .clip(RoundedCornerShape(if (grouped) MtRadius.sm else MtRadius.md))
                    .background(background)
                    .semantics { this.selected = isSelected }
                    .pressable(pressedScale = 0.95f) {
                        if (!isSelected) {
                            haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
                            onSelect(option)
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                MtText(
                    label(option),
                    MtTheme.type.subhead.copy(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold),
                    color = if (isSelected) MtColors.TextPrimary else MtColors.Caption,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                )
            }
        }
    }
}
