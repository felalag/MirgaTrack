package com.migratrack.app.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.migratrack.app.ui.icons.Icon
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import com.migratrack.app.ui.uppercaseInAppLanguage

@Composable
fun MtText(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    autoSize: TextAutoSize? = null,
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = style.merge(TextStyle(color = color, textAlign = textAlign)),
        maxLines = maxLines,
        overflow = overflow,
        autoSize = autoSize,
    )
}

/** Uppercase caption above sections, as in "CLINICAL BASELINE & PROTOCOL". */
@Composable
fun SectionCaption(text: String, modifier: Modifier = Modifier, color: Color = MtColors.Caption) {
    MtText(text.uppercaseInAppLanguage(), MtTheme.type.caption, modifier.padding(horizontal = MtSpacing.xs), color = color)
}

/**
 * Tap handling with iOS press feedback (dim and a slight shrink) instead of a Material ripple.
 */
fun Modifier.pressable(
    enabled: Boolean = true,
    role: Role = Role.Button,
    pressedScale: Float = 0.97f,
    onClick: () -> Unit,
): Modifier = composed {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) pressedScale else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessHigh),
    )
    graphicsLayer {
        scaleX = scale
        scaleY = scale
        alpha = if (pressed) 0.8f else 1f
    }.clickable(interactionSource = interaction, indication = null, enabled = enabled, role = role, onClick = onClick)
}

/** A colored blur behind a shape, the "luminescent halo" of the specification. */
fun Modifier.glow(color: Color, radius: Dp = 16.dp, cornerRadius: Dp = 999.dp, alpha: Float = 0.45f): Modifier =
    dropShadow(RoundedCornerShape(cornerRadius), Shadow(radius = radius, color = color.copy(alpha = alpha)))

@Composable
fun Card(
    modifier: Modifier = Modifier,
    color: Color = MtColors.Card,
    radius: Dp = MtRadius.lg,
    contentPadding: PaddingValues = PaddingValues(MtSpacing.md),
    bordered: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(radius)
    Column(
        modifier = modifier
            .clip(shape)
            .background(color, shape)
            .then(if (bordered) Modifier.border(1.dp, MtColors.BorderSubtle, shape) else Modifier)
            .padding(contentPadding),
        content = content,
    )
}

/** Rounded square that holds a row icon, as in the Settings and Export lists. */
@Composable
fun IconTile(icon: ImageVector, tint: Color, modifier: Modifier = Modifier, size: Dp = 32.dp, background: Color = MtColors.CardRaised) {
    Box(
        modifier = modifier.size(size).clip(RoundedCornerShape(MtRadius.sm)).background(background),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, tint = tint, size = size * 0.56f)
    }
}

@Composable
fun Dot(color: Color, size: Dp = 10.dp, glow: Boolean = true) {
    Box(
        Modifier
            .size(size)
            .then(if (glow) Modifier.glow(color, radius = 6.dp, alpha = 0.7f) else Modifier)
            .background(color, CircleShape),
    )
}
