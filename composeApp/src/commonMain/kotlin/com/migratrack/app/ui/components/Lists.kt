package com.migratrack.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.migratrack.app.ui.icons.Icon
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme

/** An inset grouped list, as UITableView.Style.insetGrouped draws it, with a caption above. */
@Composable
fun GroupedSection(
    caption: String?,
    modifier: Modifier = Modifier,
    background: Color = MtColors.Card,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
        if (caption != null) SectionCaption(caption)
        Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(MtRadius.lg)).background(background), content = content)
    }
}

@Composable
fun ListDivider(inset: Boolean = true) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(start = if (inset) 64.dp else 0.dp)
            .height(0.5.dp)
            .background(Color.White.copy(alpha = 0.1f)),
    )
}

sealed interface RowTrailing {
    data object Chevron : RowTrailing
    data class Value(val text: String) : RowTrailing
    data class Toggle(val checked: Boolean, val onChange: (Boolean) -> Unit) : RowTrailing
}

@Composable
fun ListRow(
    title: String,
    icon: ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    trailing: RowTrailing? = null,
    titleColor: Color = MtColors.TextPrimary,
    // One step lighter than the list it sits in, as in the screens.
    iconBackground: Color = MtColors.CardRaised,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .then(if (onClick != null) Modifier.pressable(pressedScale = 1f, onClick = onClick) else Modifier.semantics(mergeDescendants = true) {})
            .padding(horizontal = MtSpacing.md, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconTile(icon, tint = iconTint, background = iconBackground)
        Spacer(Modifier.width(MtSpacing.md))
        Column(Modifier.weight(1f)) {
            MtText(title, MtTheme.type.bodyStrong, color = titleColor, maxLines = 2)
            if (subtitle != null) {
                MtText(subtitle, MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal), color = MtColors.TextSecondary, maxLines = 2)
            }
        }
        when (trailing) {
            RowTrailing.Chevron -> Icon(Symbols.ChevronRight, tint = MtColors.TextSecondary.copy(alpha = 0.6f), size = 22.dp)
            is RowTrailing.Value -> MtText(trailing.text, MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal), color = MtColors.TextSecondary)
            is RowTrailing.Toggle -> IosSwitch(trailing.checked, trailing.onChange)
            null -> Unit
        }
    }
}
