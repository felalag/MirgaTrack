package com.migratrack.app.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.migratrack.app.ui.icons.AppIcons
import com.migratrack.app.ui.icons.Icon
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import com.migratrack.app.ui.theme.WithoutFontScaling

val HeaderHeight = 64.dp
val TabBarHeight = 60.dp

/** Space the floating tab bar takes at the bottom of the current screen; zero where it is hidden. */
val LocalBottomBarInset = compositionLocalOf { 0.dp }

/** "MIGRATRACK / Summary" header with the brain mark, or a back button on pushed screens. */
@Composable
fun BrandHeader(
    title: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    onSettings: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth().height(HeaderHeight).padding(horizontal = MtSpacing.screen),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MtColors.Card)
                .border(1.dp, Color.White.copy(alpha = 0.1f), CircleShape)
                .then(
                    if (onBack != null) Modifier.semantics { contentDescription = "Back" }.pressable(onClick = onBack)
                    else Modifier,
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (onBack != null) {
                Icon(Symbols.ArrowBackIosNew, tint = MtColors.BrandLabel, size = 20.dp)
            } else {
                Icon(AppIcons.Brain, tint = MtColors.PinkSoft, size = 24.dp, modifier = Modifier.glow(MtColors.Pink, 8.dp, alpha = 0.35f))
            }
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f).semantics(mergeDescendants = true) { heading() }) {
            WithoutFontScaling {
                MtText("MIGRATRACK", MtTheme.type.caption.copy(letterSpacing = 0.16.em, fontWeight = FontWeight.SemiBold), color = MtColors.BrandLabel)
                MtText(title, MtTheme.type.title1, color = MtColors.TextPrimary, maxLines = 1)
            }
        }
        if (onSettings != null) {
            Box(
                Modifier.size(MtSpacing.minTouch).semantics { contentDescription = "Settings" }.pressable(onClick = onSettings),
                contentAlignment = Alignment.Center,
            ) {
                Icon(AppIcons.Settings, tint = Color(0xFFA1A1AA), size = 24.dp)
            }
        }
    }
}

/**
 * A scrolling page under a fixed header, padded for the status bar and the tab bar.
 * Content scrolls beneath the header as it does under a UINavigationBar.
 */
@Composable
fun Page(
    title: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    onSettings: (() -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    spacing: Dp = MtSpacing.lg,
    content: @Composable ColumnScope.() -> Unit,
) {
    val top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val bottom = maxOf(LocalBottomBarInset.current, WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
    Box(modifier.fillMaxSize().background(MtColors.Canvas)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = top + HeaderHeight + MtSpacing.xs, bottom = bottom + MtSpacing.lg)
                .padding(horizontal = MtSpacing.screen),
            verticalArrangement = Arrangement.spacedBy(spacing),
            content = content,
        )
        BrandHeader(
            title = title,
            onBack = onBack,
            onSettings = onSettings,
            modifier = Modifier.background(MtColors.Canvas).padding(top = top),
        )
    }
}

enum class Tab(val title: String, val icon: ImageVector) {
    Summary("Summary", AppIcons.TabSummary),
    Log("Log", AppIcons.TabLog),
    Insights("Insights", AppIcons.TabInsights),
    Export("Export", AppIcons.TabExport),
}

@Composable
fun TabBar(selected: Tab, onSelect: (Tab) -> Unit, modifier: Modifier = Modifier) {
    val bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Column(modifier.fillMaxWidth().background(MtColors.Canvas)) {
        Box(Modifier.fillMaxWidth().height(0.5.dp).background(Color.White.copy(alpha = 0.1f)))
        Row(
            modifier = Modifier.fillMaxWidth().height(TabBarHeight).padding(horizontal = MtSpacing.xs),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Tab.entries.forEach { tab ->
                val active = tab == selected
                val color = if (active) MtColors.Pink else MtColors.TextSecondary
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(TabBarHeight)
                        .semantics { this.selected = active }
                        .pressable(role = Role.Tab, pressedScale = 0.92f) { onSelect(tab) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        tab.icon,
                        tint = color,
                        size = 24.dp,
                        modifier = if (active) Modifier.glow(MtColors.Pink, radius = 10.dp, alpha = 0.35f) else Modifier,
                    )
                    Spacer(Modifier.height(4.dp))
                    WithoutFontScaling { MtText(tab.title, MtTheme.type.tabLabel, color = color) }
                }
            }
        }
        Spacer(Modifier.height(bottom))
    }
}

/** Short confirmation that slides in under the status bar, like the design's "Logger ready" toast. */
@Composable
fun Banner(text: String, icon: ImageVector, tint: Color, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MtSpacing.screen)
            .clip(RoundedCornerShape(14.dp))
            .background(MtColors.ControlHigh)
            .border(1.dp, MtColors.BorderSubtle, RoundedCornerShape(14.dp))
            .padding(horizontal = MtSpacing.md, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, tint = tint, size = 20.dp)
        Spacer(Modifier.width(MtSpacing.sm))
        MtText(text, MtTheme.type.subhead, color = MtColors.TextBody)
    }
}
