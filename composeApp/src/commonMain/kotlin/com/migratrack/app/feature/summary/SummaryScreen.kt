package com.migratrack.app.feature.summary

import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.migratrack.app.di.LocalAppGraph
import com.migratrack.app.ui.components.Card
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
import com.migratrack.domain.analytics.SummaryCalculator
import com.migratrack.domain.analytics.SummarySnapshot
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
        LastThirtyDaysCard(snapshot, current.sleep)
        Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
            PrimaryButton("Log Migraine Episode", onClick = onLogEpisode, icon = Symbols.AddCircle)
            MtText(
                "MigraTrack is a diary, not medical advice. Talk to your doctor before you change your treatment.",
                MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal),
                Modifier.fillMaxWidth().padding(horizontal = MtSpacing.sm),
                color = MtColors.TextSecondary,
                textAlign = TextAlign.Center,
            )
        }
        if (current.sleep == SleepCard.NeedsAccess) SleepConnectCard(onConnect = viewModel::connectHealth)
        LastEventCard(snapshot.lastEpisode, graph.timeZone(), onClick = { viewModel.manageEpisode(it, onEditEpisode) })
    }
}

@Composable
private fun LastThirtyDaysCard(snapshot: SummarySnapshot, sleep: SleepCard) {
    Card(color = MtColors.CardRaised, radius = MtRadius.xl, contentPadding = PaddingValues(MtSpacing.md)) {
        val trackedDays = "${snapshot.trackedDays} tracked ${if (snapshot.trackedDays == 1) "day" else "days"}"
        val summary = "${snapshot.painFreeDays} of $trackedDays without a logged episode in the last 30 days"
        Column(Modifier.clearAndSetSemantics { contentDescription = summary }) {
            SectionCaption("Last 30 days", modifier = Modifier.padding(start = 0.dp))
            Spacer(Modifier.height(MtSpacing.xs))
            Row(verticalAlignment = Alignment.Bottom) {
                MtText(snapshot.painFreeDays.toString(), MtTheme.type.hero, color = MtColors.TextPrimary)
                MtText(
                    " of $trackedDays",
                    MtTheme.type.headline,
                    color = MtColors.TextBody,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }
            MtText("without a logged episode", MtTheme.type.subhead, color = MtColors.TextSecondary)
            Spacer(Modifier.height(MtSpacing.md))
            DayStrip(snapshot.days.map { it.toStripDay() })
            Spacer(Modifier.height(6.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                MtText(DateText.monthDay(snapshot.days.first().date), MtTheme.type.subhead, color = MtColors.TextSecondary)
                MtText("Today", MtTheme.type.subhead, color = MtColors.TextSecondary)
            }
            Spacer(Modifier.height(MtSpacing.sm))
            DayStripLegend()
            if (snapshot.trackedDays < SummaryCalculator.WINDOW_DAYS) {
                Spacer(Modifier.height(MtSpacing.sm))
                MtText(
                    "Counted from the day you started tracking. Days before it are not tracked.",
                    MtTheme.type.subhead,
                    color = MtColors.Caption,
                )
            }
        }
        if (sleep is SleepCard.Loaded) {
            Spacer(Modifier.height(MtSpacing.md))
            Box(Modifier.fillMaxWidth().height(0.5.dp).background(MtColors.BorderSubtle))
            Spacer(Modifier.height(12.dp))
            SleepRow(sleep.sleep)
        }
    }
}

@Composable
private fun SleepRow(sleep: SleepSummary?) {
    val asleep = sleep?.asleep?.takeIf { it.inWholeMinutes > 0 }
    Column(Modifier.semantics(mergeDescendants = true) {}) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Symbols.BedtimeFilled, tint = MtColors.Green, size = 18.dp)
            Spacer(Modifier.width(MtSpacing.sm))
            MtText("Sleep last night", MtTheme.type.body, color = MtColors.TextBody, modifier = Modifier.weight(1f))
            Spacer(Modifier.width(MtSpacing.sm))
            MtText(asleep?.let(::formatHoursMinutes) ?: "Not recorded", MtTheme.type.bodyStrong, color = MtColors.TextPrimary)
        }
        if (asleep == null) {
            Spacer(Modifier.height(4.dp))
            MtText(
                "Wear Apple Watch to bed or track sleep in the Health app. If you turned off access, enable it in Settings › Health.",
                MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal),
                color = MtColors.TextSecondary,
            )
        }
    }
}

@Composable
private fun SleepHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Symbols.BedtimeFilled, tint = MtColors.Green, size = 20.dp)
        Spacer(Modifier.width(MtSpacing.xs))
        SectionCaption("Sleep")
    }
}

@Composable
private fun SleepConnectCard(onConnect: () -> Unit) {
    Card(color = MtColors.CardRaised) {
        SleepHeader()
        Spacer(Modifier.height(MtSpacing.sm))
        MtText("See last night's sleep on the Summary", MtTheme.type.headline)
        Spacer(Modifier.height(4.dp))
        MtText(
            "MigraTrack reads last night's sleep from Apple Health. The data stays on this iPhone.",
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
            SectionCaption("Last Episode", modifier = Modifier.padding(start = 0.dp))
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
