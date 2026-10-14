package com.migratrack.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.migratrack.app.ui.icons.Icon
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme

enum class SelectionStyle {
    /** Step 1: round icon that fills with the accent, small check on the right. */
    Diagnosis,

    /** Step 2 and 3: rounded icon tile, a "+" badge that turns into a colored check. */
    Catalog,
}

/** A selectable row card of the onboarding steps. */
@Composable
fun SelectionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    selected: Boolean,
    accent: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: SelectionStyle = SelectionStyle.Catalog,
) {
    val haptics = LocalHapticFeedback.current
    val background by animateColorAsState(if (selected) MtColors.Control else MtColors.CardRaised)
    val radius = if (style == SelectionStyle.Diagnosis) MtRadius.md else MtRadius.lg
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(radius))
            .background(background)
            .semantics(mergeDescendants = true) { this.selected = selected }
            .pressable(role = Role.Checkbox, pressedScale = 0.98f) {
                haptics.performHapticFeedback(if (selected) HapticFeedbackType.ToggleOff else HapticFeedbackType.ToggleOn)
                onClick()
            }
            .padding(if (style == SelectionStyle.Diagnosis) 14.dp else MtSpacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (style) {
            SelectionStyle.Diagnosis -> Box(
                Modifier
                    .size(32.dp)
                    .then(if (selected) Modifier.glow(accent, 10.dp, alpha = 0.5f) else Modifier)
                    .background(if (selected) accent else MtColors.ControlHigh, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, tint = if (selected) MtColors.TextPrimary else MtColors.Caption, size = 18.dp)
            }
            SelectionStyle.Catalog -> Box(
                Modifier.size(40.dp).clip(RoundedCornerShape(MtRadius.md)).background(if (selected) MtColors.CardRaised else MtColors.Card),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, tint = if (selected) accent else MtColors.TextSecondary, size = 22.dp)
            }
        }
        Spacer(Modifier.width(if (style == SelectionStyle.Diagnosis) 12.dp else MtSpacing.md))
        Column(Modifier.weight(1f).alpha(if (selected || style == SelectionStyle.Diagnosis) 1f else 0.85f)) {
            MtText(
                title,
                if (style == SelectionStyle.Diagnosis) MtTheme.type.bodyStrong else MtTheme.type.headline,
                color = MtColors.TextPrimary,
                maxLines = 2,
            )
            MtText(subtitle, MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal), color = MtColors.TextSecondary, maxLines = 2)
        }
        Spacer(Modifier.width(MtSpacing.sm))
        val badgeSize = if (style == SelectionStyle.Diagnosis) 24.dp else 28.dp
        Box(
            Modifier
                .size(badgeSize)
                .alpha(if (!selected && style == SelectionStyle.Diagnosis) 0.4f else 1f)
                .background(if (selected) accent else MtColors.ControlHigh, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            when {
                selected -> Icon(
                    Symbols.Check,
                    tint = if (style == SelectionStyle.Diagnosis) MtColors.TextPrimary else MtColors.CanvasDeep,
                    size = badgeSize * 0.65f,
                )
                style == SelectionStyle.Catalog -> Icon(Symbols.Add, tint = MtColors.TextTertiary.copy(alpha = 1f), size = 18.dp)
            }
        }
    }
}

/** Radio tile of the Cranial Epicenter card, orange when chosen. */
@Composable
fun RegionOption(title: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val haptics = LocalHapticFeedback.current
    val shape = RoundedCornerShape(MtRadius.md)
    val background by animateColorAsState(if (selected) MtColors.Amber.copy(alpha = 0.15f) else MtColors.Control)
    Row(
        modifier = modifier
            .heightIn(min = 48.dp)
            .then(if (selected) Modifier.glow(MtColors.Amber, 12.dp, cornerRadius = MtRadius.md, alpha = 0.35f) else Modifier)
            .clip(shape)
            .background(background)
            .then(if (selected) Modifier.border(1.dp, MtColors.Amber, shape) else Modifier)
            .semantics(mergeDescendants = true) { this.selected = selected }
            .pressable(role = Role.RadioButton, pressedScale = 0.97f) {
                haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
                onClick()
            }
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            if (selected) Symbols.AdjustFilled else Symbols.RadioButtonUnchecked,
            tint = if (selected) MtColors.Amber else MtColors.Caption,
            size = 20.dp,
        )
        Spacer(Modifier.width(MtSpacing.sm))
        MtText(
            title,
            MtTheme.type.subhead.copy(fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium, fontSize = MtTheme.type.body.fontSize),
            color = if (selected) MtColors.Amber else MtColors.Caption,
            maxLines = 2,
        )
    }
}

/** Multi-select capsule of the Aura & Sensory card, cyan when chosen. */
@Composable
fun SymptomCapsule(title: String, icon: ImageVector, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val haptics = LocalHapticFeedback.current
    val background by animateColorAsState(if (selected) MtColors.Cyan.copy(alpha = 0.2f) else MtColors.Control)
    Row(
        modifier = modifier
            .heightIn(min = 40.dp)
            .then(if (selected) Modifier.glow(MtColors.Cyan, 10.dp, alpha = 0.3f) else Modifier)
            .clip(CircleShape)
            .background(background)
            .then(if (selected) Modifier.border(1.dp, MtColors.Cyan.copy(alpha = 0.4f), CircleShape) else Modifier)
            .semantics(mergeDescendants = true) { this.selected = selected }
            .pressable(role = Role.Checkbox, pressedScale = 0.95f) {
                haptics.performHapticFeedback(if (selected) HapticFeedbackType.ToggleOff else HapticFeedbackType.ToggleOn)
                onClick()
            }
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, tint = if (selected) MtColors.Cyan else MtColors.Caption, size = 18.dp)
        Spacer(Modifier.width(6.dp))
        MtText(
            title,
            MtTheme.type.subhead.copy(fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium, fontSize = MtTheme.type.body.fontSize),
            color = if (selected) MtColors.Cyan else MtColors.Caption,
            maxLines = 2,
        )
    }
}

/** A medication taken during the episode, green as in the Acute Abortive Therapy card. */
@Composable
fun MedicationCard(name: String, detail: String, onRemove: () -> Unit, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(MtRadius.md)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .glow(MtColors.Green, 12.dp, cornerRadius = MtRadius.md, alpha = 0.2f)
            .clip(shape)
            .background(Color(0xFF1A2E1F))
            .border(1.dp, MtColors.Green.copy(alpha = 0.35f), shape)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(36.dp).background(MtColors.Green.copy(alpha = 0.2f), CircleShape), contentAlignment = Alignment.Center) {
            Icon(Symbols.Prescriptions, tint = MtColors.Green, size = 20.dp)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            MtText(name, MtTheme.type.bodyStrong.copy(fontWeight = FontWeight.Bold), color = MtColors.Green, maxLines = 1)
            MtText(detail, MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal), color = MtColors.TextSecondary, maxLines = 1)
        }
        Box(
            Modifier.size(MtSpacing.minTouch).semantics { contentDescription = "Remove $name" }.pressable(onClick = onRemove),
            contentAlignment = Alignment.Center,
        ) {
            Icon(Symbols.Close, tint = MtColors.TextSecondary, size = 18.dp)
        }
    }
}

/** The "Add …" row under a list. */
@Composable
fun AddRow(text: String, accent: Color, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(MtRadius.md))
            .background(MtColors.CardRaised.copy(alpha = 0.6f))
            .pressable(pressedScale = 0.98f, onClick = onClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(Symbols.AddCircle, tint = accent, size = 20.dp)
        Spacer(Modifier.width(MtSpacing.sm))
        MtText(text, MtTheme.type.subhead.copy(fontWeight = FontWeight.SemiBold), color = MtColors.TextPrimary, maxLines = 2)
    }
}
