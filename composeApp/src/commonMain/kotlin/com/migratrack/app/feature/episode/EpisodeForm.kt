package com.migratrack.app.feature.episode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.migratrack.app.ui.components.AddRow
import com.migratrack.app.ui.components.Card
import com.migratrack.app.ui.components.MedicationCard
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.PainScale
import com.migratrack.app.ui.components.RegionOption
import com.migratrack.app.ui.components.SymptomCapsule
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
import com.migratrack.domain.usecase.EpisodeDraft
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/** The cards of the Log Episode and Edit Episode screens, without the buttons under them. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColumnScope.EpisodeFormFields(
    draft: EpisodeDraft,
    viewModel: EpisodeFormViewModel,
    today: LocalDate,
    timeZone: TimeZone,
) {
    Card(bordered = true, contentPadding = PaddingValues(MtSpacing.screen)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Symbols.E911Emergency, tint = MtColors.BrandLabel, size = 20.dp)
            Spacer(Modifier.width(MtSpacing.sm))
            MtText("PAIN INTENSITY INDEX", MtTheme.type.subhead.copy(fontWeight = FontWeight.SemiBold), color = MtColors.Caption)
        }
        Spacer(Modifier.height(MtSpacing.md))
        PainScale(draft.intensity, viewModel::setIntensity)
    }

    FormSection("Cranial Epicenter", Symbols.CenterFocusStrong, MtColors.Amber, onClear = viewModel::clearRegion) {
        CranialRegion.entries.chunked(2).forEach { pair ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
                pair.forEach { region ->
                    RegionOption(region.title, draft.region == region, { viewModel.selectRegion(region) }, Modifier.weight(1f))
                }
            }
        }
    }

    FormSection("Aura & Sensory Phenotypes", Symbols.Sensors, MtColors.Cyan, onClear = viewModel::clearSymptoms) {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(MtSpacing.sm), verticalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
            Symptom.entries.forEach { symptom ->
                SymptomCapsule(symptom.title, symptom.icon, symptom in draft.symptoms, { viewModel.toggleSymptom(symptom) })
            }
        }
    }

    FormSection("Acute Abortive Therapy", Symbols.Pill, MtColors.Green, onClear = viewModel::clearMedications) {
        draft.medications.forEach { medication ->
            MedicationCard(medication.displayName, medication.drugClass, onRemove = { viewModel.removeMedication(medication) })
        }
        AddRow("Add Medication / Hydration Electrolytes", MtColors.Green, onClick = viewModel::addMedication)
    }

    val start = draft.startedAt?.let { DateText.dayAndTime(it.toLocalDateTime(timeZone), today) } ?: "Now"
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
            TextAction("Clear", accent, onClear)
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
            MtText("Episode Start", MtTheme.type.bodyStrong.copy(fontWeight = FontWeight.Bold), modifier = Modifier.weight(1f))
            MtText(value, MtTheme.type.subhead, color = MtColors.TextSecondary, maxLines = 1)
            Icon(Symbols.ChevronRight, tint = MtColors.TextSecondary.copy(alpha = 0.6f), size = 22.dp)
        }
    }
}
