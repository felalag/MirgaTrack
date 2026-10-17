package com.migratrack.app.feature.episode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.common_add_trigger
import com.migratrack.app.generated.resources.common_clear
import com.migratrack.app.generated.resources.common_possible_triggers
import com.migratrack.app.generated.resources.episode_acute_therapy
import com.migratrack.app.generated.resources.episode_add_medication_row
import com.migratrack.app.generated.resources.episode_aura_symptoms
import com.migratrack.app.generated.resources.episode_cranial_epicenter
import com.migratrack.app.generated.resources.episode_pain_intensity_index
import com.migratrack.app.generated.resources.episode_start
import com.migratrack.app.generated.resources.episode_start_now
import com.migratrack.app.ui.accent
import com.migratrack.app.ui.components.AddRow
import com.migratrack.app.ui.components.Card
import com.migratrack.app.ui.components.MedicationCard
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.PainScale
import com.migratrack.app.ui.components.RegionOption
import com.migratrack.app.ui.components.SelectableCapsule
import com.migratrack.app.ui.components.TextAction
import com.migratrack.app.ui.components.glow
import com.migratrack.app.ui.components.pressable
import com.migratrack.app.ui.icon
import com.migratrack.app.ui.icons.Icon
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import com.migratrack.domain.analytics.DateText
import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.model.Trigger
import com.migratrack.domain.usecase.EpisodeDraft
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource

/** The cards of the Log Episode and Edit Episode screens, without the buttons under them. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColumnScope.EpisodeFormFields(
    draft: EpisodeDraft,
    baselineTriggers: List<Trigger>,
    viewModel: EpisodeFormViewModel,
    today: LocalDate,
    timeZone: TimeZone,
) {
    Card(bordered = true, contentPadding = PaddingValues(MtSpacing.screen)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Symbols.E911Emergency, tint = MtColors.BrandLabel, size = 20.dp)
            Spacer(Modifier.width(MtSpacing.sm))
            MtText(stringResource(Res.string.episode_pain_intensity_index).uppercase(), MtTheme.type.subhead.copy(fontWeight = FontWeight.SemiBold), color = MtColors.Caption)
        }
        Spacer(Modifier.height(MtSpacing.md))
        PainScale(draft.intensity, viewModel::setIntensity)
    }

    FormSection(stringResource(Res.string.episode_cranial_epicenter), Symbols.CenterFocusStrong, MtColors.Amber, onClear = viewModel::clearRegion) {
        CranialRegion.entries.chunked(2).forEach { pair ->
            // Large text wraps a title to two lines; both tiles of a pair keep the same height.
            Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
                pair.forEach { region ->
                    RegionOption(region.title, draft.region == region, { viewModel.selectRegion(region) }, Modifier.weight(1f).fillMaxHeight())
                }
            }
        }
    }

    FormSection(stringResource(Res.string.episode_aura_symptoms), Symbols.Sensors, MtColors.Cyan, onClear = viewModel::clearSymptoms) {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(MtSpacing.sm), verticalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
            Symptom.entries.forEach { symptom ->
                SelectableCapsule(symptom.title, symptom.icon, symptom in draft.symptoms, { viewModel.toggleSymptom(symptom) })
            }
        }
    }

    // Violet is the specification's accent for trigger categorization; a chosen capsule takes its category color.
    FormSection(stringResource(Res.string.common_possible_triggers), Symbols.Bolt, MtColors.Purple, onClear = viewModel::clearTriggers) {
        val noted = draft.triggers.map { it.id }.toSet()
        val shown = baselineTriggers + draft.triggers.filterNot { trigger -> baselineTriggers.any { it.id == trigger.id } }
        if (shown.isNotEmpty()) {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(MtSpacing.sm), verticalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
                shown.forEach { trigger ->
                    SelectableCapsule(trigger.name, trigger.icon, trigger.id in noted, { viewModel.toggleTrigger(trigger) }, accent = trigger.category.accent)
                }
            }
        }
        AddRow(stringResource(Res.string.common_add_trigger), MtColors.Purple, onClick = viewModel::addTrigger)
    }

    FormSection(stringResource(Res.string.episode_acute_therapy), Symbols.Pill, MtColors.Green, onClear = viewModel::clearMedications) {
        draft.medications.forEach { medication ->
            MedicationCard(medication.displayName, medication.drugClass, onRemove = { viewModel.removeMedication(medication) })
        }
        AddRow(stringResource(Res.string.episode_add_medication_row), MtColors.Green, onClick = viewModel::addMedication)
    }

    val start = draft.startedAt?.let { DateText.dayAndTime(it.toLocalDateTime(timeZone), today) } ?: stringResource(Res.string.episode_start_now)
    StartRow(start, onClick = viewModel::pickStart)
}

@Composable
private fun FormSection(
    title: String,
    icon: ImageVector,
    accent: Color,
    onClear: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(contentPadding = PaddingValues(MtSpacing.screen)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, tint = accent, size = 20.dp, modifier = Modifier.glow(accent, 8.dp, alpha = 0.5f))
            Spacer(Modifier.width(MtSpacing.sm))
            MtText(title, MtTheme.type.bodyStrong.copy(fontWeight = FontWeight.Bold), modifier = Modifier.weight(1f).semantics { heading() })
            TextAction(stringResource(Res.string.common_clear), accent, onClear)
        }
        Spacer(Modifier.height(MtSpacing.sm))
        Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.sm), content = content)
    }
}

/**
 * When the episode started. The screens have no such field; the calendar implies it,
 * because it logs on past days and edits old episodes. Violet is the design system's accent for circadian data.
 */
@Composable
private fun StartRow(value: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().semantics(mergeDescendants = true) {}.pressable(pressedScale = 0.98f, onClick = onClick),
        contentPadding = PaddingValues(horizontal = MtSpacing.screen, vertical = MtSpacing.md),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Symbols.Schedule, tint = MtColors.Purple, size = 20.dp, modifier = Modifier.glow(MtColors.Purple, 8.dp, alpha = 0.5f))
            Spacer(Modifier.width(MtSpacing.sm))
            MtText(stringResource(Res.string.episode_start), MtTheme.type.bodyStrong.copy(fontWeight = FontWeight.Bold), modifier = Modifier.weight(1f))
            MtText(value, MtTheme.type.subhead, color = MtColors.TextSecondary, maxLines = 1)
            Icon(Symbols.ChevronRight, tint = MtColors.TextSecondary.copy(alpha = 0.6f), size = 22.dp)
        }
    }
}
