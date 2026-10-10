package com.migratrack.app.feature.summary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.migratrack.app.di.LocalAppGraph
import com.migratrack.app.ui.components.Card
import com.migratrack.app.ui.components.Dot
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.Page
import com.migratrack.app.ui.components.PrimaryButton
import com.migratrack.app.ui.components.SecondaryButton
import com.migratrack.app.ui.components.SectionCaption
import com.migratrack.app.ui.components.pressable
import com.migratrack.app.ui.formatHoursMinutes
import com.migratrack.app.ui.icon
import com.migratrack.app.ui.icons.Icon
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import com.migratrack.domain.analytics.DateText
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.SleepSummary
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun SummaryScreen(onOpenSettings: () -> Unit, onLogEpisode: () -> Unit, onEditEpisode: (EpisodeId) -> Unit) {
    val graph = LocalAppGraph.current
    val viewModel = viewModel { SummaryViewModel(graph) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Sleep from the Health app changes while MigraTrack is in the background.
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) { viewModel.refreshSleep() }
    }

    Page(title = "Summary", onSettings = onOpenSettings) {
        val current = state ?: return@Page
        val snapshot = current.snapshot
        SectionCaption("Today, ${DateText.monthDay(snapshot.today)}")
        BalanceCard(snapshot.painFreeDays, snapshot.windowDays, snapshot.restScore, snapshot.balanceScore)
        PrimaryButton("Log Migraine Episode", onClick = onLogEpisode, icon = Symbols.AddCircle)
        when (val sleep = current.sleep) {
            SleepCard.Hidden, SleepCard.Loading -> Unit
            SleepCard.NeedsAccess -> SleepConnectCard(onConnect = viewModel::connectHealth)
            is SleepCard.Loaded -> SleepDepthCard(sleep.sleep)
        }
        LastEventCard(snapshot.lastEpisode, graph.timeZone(), onClick = { viewModel.manageEpisode(it, onEditEpisode) })
    }
}

@Composable
private fun BalanceCard(painFreeDays: Int, windowDays: Int, rest: Int?, balance: Int) {
    Card(color = MtColors.CardRaised, radius = MtRadius.xl, contentPadding = androidx.compose.foundation.layout.PaddingValues(MtSpacing.md)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Symbols.VitalSignsFilled, tint = MtColors.Pink, size = 20.dp)
            Spacer(Modifier.width(MtSpacing.sm))
            MtText("BIOMETRIC BALANCE", MtTheme.type.subhead.copy(fontWeight = FontWeight.SemiBold), color = MtColors.TextBody)
        }
        Spacer(Modifier.height(MtSpacing.lg))
        ActivityRings(
            outer = painFreeDays.toFloat() / windowDays,
            inner = rest?.div(100f),
            outerColor = MtColors.Pink,
            innerColor = MtColors.Green,
            centre = balance.toString(),
            description = "Biometric balance $balance. Pain-free $painFreeDays of $windowDays days. Rest ${rest ?: "unknown"}.",
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Spacer(Modifier.height(MtSpacing.lg))
        Row(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(MtRadius.md)).background(MtColors.Control.copy(alpha = 0.6f)).padding(12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LegendValue("Pain-Free", MtColors.Pink, painFreeDays.toString(), "/${windowDays}d")
            LegendValue("Rest", MtColors.Green, rest?.toString() ?: "–", null)
        }
    }
}

@Composable
private fun LegendValue(label: String, color: Color, value: String, unit: String?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Dot(color)
            Spacer(Modifier.width(6.dp))
            MtText(label, MtTheme.type.subhead, color = MtColors.Caption)
        }
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            MtText(value, MtTheme.type.title2.copy(fontWeight = FontWeight.Bold), color = MtColors.TextPrimary)
            if (unit != null) {
                Spacer(Modifier.width(2.dp))
                MtText(unit, MtTheme.type.subhead, color = MtColors.Caption, modifier = Modifier.padding(bottom = 2.dp))
            }
        }
    }
}

@Composable
private fun SleepHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Symbols.BedtimeFilled, tint = MtColors.Green, size = 20.dp)
        Spacer(Modifier.width(MtSpacing.xs))
        SectionCaption("Sleep Sync & Depth")
    }
}

@Composable
private fun SleepDepthCard(sleep: SleepSummary?) {
    Card(color = MtColors.CardRaised) {
        SleepHeader()
        Spacer(Modifier.height(MtSpacing.sm))
        if (sleep == null || sleep.asleep.inWholeMinutes == 0L) {
            MtText("No sleep recorded for last night", MtTheme.type.headline, color = MtColors.TextPrimary)
            Spacer(Modifier.height(4.dp))
            MtText(
                "Wear Apple Watch to bed or track sleep in the Health app. If you turned off access, enable it in Settings › Health.",
                MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal),
                color = MtColors.TextSecondary,
            )
            return@Card
        }
        val hours = sleep.asleep.inWholeHours
        val minutes = sleep.asleep.inWholeMinutes % 60
        Row(verticalAlignment = Alignment.Bottom) {
            MtText(hours.toString(), MtTheme.type.hero)
            MtText(" h ", MtTheme.type.headline, color = MtColors.Caption, modifier = Modifier.padding(bottom = 8.dp))
            MtText(minutes.toString(), MtTheme.type.hero)
            MtText(" m", MtTheme.type.headline, color = MtColors.Caption, modifier = Modifier.padding(bottom = 8.dp))
        }
        Spacer(Modifier.height(MtSpacing.sm))
        val stages = listOf(
            Triple(sleep.deep, MtColors.Green, "Deep"),
            Triple(sleep.core, MtColors.Cyan, "Core"),
            Triple(sleep.rem, MtColors.Pink, "REM"),
        ).filter { it.first.inWholeMinutes > 0 }
        Row(
            Modifier.fillMaxWidth().height(12.dp).clip(CircleShape).background(MtColors.Control).padding(2.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            stages.forEach { (duration, color, _) ->
                Box(Modifier.weight(duration.inWholeMinutes.toFloat()).height(8.dp).background(color, CircleShape))
            }
        }
        Spacer(Modifier.height(MtSpacing.sm))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            stages.forEach { (duration, color, label) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Dot(color, size = 8.dp, glow = false)
                    Spacer(Modifier.width(4.dp))
                    MtText("${formatHoursMinutes(duration)} $label", MtTheme.type.caption.copy(letterSpacing = MtTheme.type.subhead.letterSpacing), color = MtColors.Caption)
                }
            }
        }
    }
}

@Composable
private fun SleepConnectCard(onConnect: () -> Unit) {
    Card(color = MtColors.CardRaised) {
        SleepHeader()
        Spacer(Modifier.height(MtSpacing.sm))
        MtText("See how sleep shapes your attacks", MtTheme.type.headline)
        Spacer(Modifier.height(4.dp))
        MtText(
            "MigraTrack reads last night's sleep stages from Apple Health. The data stays on this iPhone.",
            MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal),
            color = MtColors.TextSecondary,
        )
        Spacer(Modifier.height(MtSpacing.md))
        SecondaryButton("Connect Apple Health", onClick = onConnect, icon = Symbols.BedtimeFilled, iconTint = MtColors.Green)
    }
}

@Composable
private fun LastEventCard(episode: Episode?, timeZone: TimeZone, onClick: (Episode) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MtRadius.lg))
            .background(MtColors.CardRaised)
            .then(if (episode != null) Modifier.pressable(pressedScale = 0.98f) { onClick(episode) } else Modifier)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(40.dp).background(MtColors.Control, CircleShape), contentAlignment = Alignment.Center) {
            Icon(Symbols.History, tint = MtColors.BrandLabel, size = 20.dp)
        }
        Spacer(Modifier.width(MtSpacing.sm))
        Column(Modifier.weight(1f)) {
            SectionCaption("Last Event", modifier = Modifier.padding(start = 0.dp))
            val date = episode?.startedAt?.toLocalDateTime(timeZone)?.date
            MtText(date?.let(DateText::monthDay) ?: "None yet", MtTheme.type.headline, color = MtColors.TextPrimary)
        }
        if (episode != null) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                val symptom = episode.symptoms.minByOrNull { it.ordinal }
                if (symptom != null) EventChip(symptom.shortTitle, symptom.icon)
                EventChip("Intensity ${episode.intensity.value}/10", null)
            }
        }
    }
}

@Composable
private fun EventChip(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector?) {
    Row(
        Modifier.clip(RoundedCornerShape(MtRadius.sm)).background(MtColors.Control).padding(horizontal = 10.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Icon(icon, tint = MtColors.Cyan, size = 16.dp)
            Spacer(Modifier.width(4.dp))
        }
        MtText(text, MtTheme.type.subhead.copy(fontWeight = FontWeight.SemiBold), color = MtColors.TextBody, maxLines = 1)
    }
}
