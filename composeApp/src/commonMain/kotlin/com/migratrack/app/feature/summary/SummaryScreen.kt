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
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.common_intensity_of_ten
import com.migratrack.app.generated.resources.common_last_30_days
import com.migratrack.app.generated.resources.common_log_migraine_episode
import com.migratrack.app.generated.resources.common_medical_disclaimer
import com.migratrack.app.generated.resources.common_today
import com.migratrack.app.generated.resources.summary_last_episode
import com.migratrack.app.generated.resources.summary_last_episode_none
import com.migratrack.app.generated.resources.summary_of_tracked_days
import com.migratrack.app.generated.resources.summary_pain_free_a11y
import com.migratrack.app.generated.resources.summary_sleep_caption
import com.migratrack.app.generated.resources.summary_sleep_connect_body
import com.migratrack.app.generated.resources.summary_sleep_connect_button
import com.migratrack.app.generated.resources.summary_sleep_connect_title
import com.migratrack.app.generated.resources.summary_sleep_hint
import com.migratrack.app.generated.resources.summary_sleep_last_night
import com.migratrack.app.generated.resources.summary_sleep_not_recorded
import com.migratrack.app.generated.resources.summary_today_date
import com.migratrack.app.generated.resources.summary_tracking_start_note
import com.migratrack.app.generated.resources.summary_without_logged_episode
import com.migratrack.app.generated.resources.tab_summary
import com.migratrack.app.ui.Dates
import com.migratrack.app.ui.Numbers
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
import com.migratrack.app.ui.pluralStringResource
import com.migratrack.app.ui.shortTitle
import com.migratrack.app.ui.stringResource
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
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

    Page(title = stringResource(Res.string.tab_summary), onSettings = onOpenSettings) {
        val current = state ?: return@Page
        val snapshot = current.snapshot
        SectionCaption(stringResource(Res.string.summary_today_date, Dates.monthDay(snapshot.today)))
        LastThirtyDaysCard(snapshot, current.sleep)
        Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
            PrimaryButton(stringResource(Res.string.common_log_migraine_episode), onClick = onLogEpisode, icon = Symbols.AddCircle)
            MtText(
                stringResource(Res.string.common_medical_disclaimer),
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
        val summary = pluralStringResource(
            Res.plurals.summary_pain_free_a11y,
            snapshot.trackedDays,
            Numbers.integer(snapshot.painFreeDays),
            Numbers.integer(snapshot.trackedDays),
        )
        Column(Modifier.clearAndSetSemantics { contentDescription = summary }) {
            SectionCaption(stringResource(Res.string.common_last_30_days), modifier = Modifier.padding(start = 0.dp))
            Spacer(Modifier.height(MtSpacing.xs))
            Row(verticalAlignment = Alignment.Bottom) {
                MtText(Numbers.integer(snapshot.painFreeDays), MtTheme.type.hero, color = MtColors.TextPrimary)
                MtText(
                    " " + pluralStringResource(Res.plurals.summary_of_tracked_days, snapshot.trackedDays, Numbers.integer(snapshot.trackedDays)),
                    MtTheme.type.headline,
                    color = MtColors.TextBody,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }
            MtText(stringResource(Res.string.summary_without_logged_episode), MtTheme.type.subhead, color = MtColors.TextSecondary)
            Spacer(Modifier.height(MtSpacing.md))
            DayStrip(snapshot.days.map { it.toStripDay() })
            Spacer(Modifier.height(6.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                MtText(Dates.monthDay(snapshot.days.first().date), MtTheme.type.subhead, color = MtColors.TextSecondary)
                MtText(stringResource(Res.string.common_today), MtTheme.type.subhead, color = MtColors.TextSecondary)
            }
            Spacer(Modifier.height(MtSpacing.sm))
            DayStripLegend()
            if (snapshot.trackedDays < SummaryCalculator.WINDOW_DAYS) {
                Spacer(Modifier.height(MtSpacing.sm))
                MtText(
                    stringResource(Res.string.summary_tracking_start_note),
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
            MtText(stringResource(Res.string.summary_sleep_last_night), MtTheme.type.body, color = MtColors.TextBody, modifier = Modifier.weight(1f))
            Spacer(Modifier.width(MtSpacing.sm))
            MtText(asleep?.let { formatHoursMinutes(it) } ?: stringResource(Res.string.summary_sleep_not_recorded), MtTheme.type.bodyStrong, color = MtColors.TextPrimary)
        }
        if (asleep == null) {
            Spacer(Modifier.height(4.dp))
            MtText(
                stringResource(Res.string.summary_sleep_hint),
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
        SectionCaption(stringResource(Res.string.summary_sleep_caption))
    }
}

@Composable
private fun SleepConnectCard(onConnect: () -> Unit) {
    Card(color = MtColors.CardRaised) {
        SleepHeader()
        Spacer(Modifier.height(MtSpacing.sm))
        MtText(stringResource(Res.string.summary_sleep_connect_title), MtTheme.type.headline)
        Spacer(Modifier.height(4.dp))
        MtText(
            stringResource(Res.string.summary_sleep_connect_body),
            MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal),
            color = MtColors.TextSecondary,
        )
        Spacer(Modifier.height(MtSpacing.md))
        SecondaryButton(stringResource(Res.string.summary_sleep_connect_button), onClick = onConnect, icon = Symbols.BedtimeFilled, iconTint = MtColors.Green)
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
            SectionCaption(stringResource(Res.string.summary_last_episode), modifier = Modifier.padding(start = 0.dp))
            val date = episode?.startedAt?.toLocalDateTime(timeZone)?.date
            MtText(date?.let(Dates::monthDay) ?: stringResource(Res.string.summary_last_episode_none), MtTheme.type.headline, color = MtColors.TextPrimary)
        }
        if (episode != null) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                val symptom = episode.symptoms.minByOrNull { it.ordinal }
                if (symptom != null) EventChip(stringResource(symptom.shortTitle), symptom.icon)
                EventChip(stringResource(Res.string.common_intensity_of_ten, Numbers.integer(episode.intensity.value)), null)
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
