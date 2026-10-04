package com.migratrack.app.feature.log

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.migratrack.app.di.LocalAppGraph
import com.migratrack.app.ui.components.AddRow
import com.migratrack.app.ui.components.Card
import com.migratrack.app.ui.components.MedicationCard
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.Page
import com.migratrack.app.ui.components.PainScale
import com.migratrack.app.ui.components.PrimaryButton
import com.migratrack.app.ui.components.RegionOption
import com.migratrack.app.ui.components.SymptomCapsule
import com.migratrack.app.ui.components.TextAction
import com.migratrack.app.ui.components.glow
import com.migratrack.app.ui.icon
import com.migratrack.app.ui.icons.Icon
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.Symptom

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LogEpisodeScreen(onOpenSettings: () -> Unit, onSaved: () -> Unit) {
    val graph = LocalAppGraph.current
    val viewModel = viewModel { LogEpisodeViewModel(graph) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val haptics = LocalHapticFeedback.current

    Page(title = "Log Episode", onSettings = onOpenSettings) {
        val draft = state.draft ?: return@Page
        Card(bordered = true, contentPadding = PaddingValues(MtSpacing.screen)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Symbols.E911Emergency, tint = MtColors.BrandLabel, size = 20.dp)
                Spacer(Modifier.width(MtSpacing.sm))
                MtText("PAIN INTENSITY INDEX", MtTheme.type.subhead.copy(fontWeight = FontWeight.SemiBold), color = MtColors.Caption)
            }
            Spacer(Modifier.height(MtSpacing.md))
            PainScale(draft.intensity, viewModel::setIntensity)
        }

        LogSection("Cranial Epicenter", Symbols.CenterFocusStrong, MtColors.Amber, onClear = viewModel::clearRegion) {
            CranialRegion.entries.chunked(2).forEach { pair ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
                    pair.forEach { region ->
                        RegionOption(region.title, draft.region == region, { viewModel.selectRegion(region) }, Modifier.weight(1f))
                    }
                }
            }
        }

        LogSection("Aura & Sensory Phenotypes", Symbols.Sensors, MtColors.Cyan, onClear = viewModel::clearSymptoms) {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(MtSpacing.sm), verticalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
                Symptom.entries.forEach { symptom ->
                    SymptomCapsule(symptom.title, symptom.icon, symptom in draft.symptoms, { viewModel.toggleSymptom(symptom) })
                }
            }
        }

        LogSection("Acute Abortive Therapy", Symbols.Pill, MtColors.Green, onClear = viewModel::clearMedications) {
            draft.medications.forEach { medication ->
                MedicationCard(medication.displayName, medication.drugClass, onRemove = { viewModel.removeMedication(medication) })
            }
            AddRow("Add Medication / Hydration Electrolytes", MtColors.Green, onClick = viewModel::addMedication)
        }

        PrimaryButton(
            text = "Save Episode Log",
            icon = Symbols.Save,
            enabled = !state.saving,
            height = 56.dp,
            onClick = {
                haptics.performHapticFeedback(HapticFeedbackType.Confirm)
                viewModel.save(onSaved)
            },
        )
    }
}

@Composable
private fun LogSection(
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
        androidx.compose.foundation.layout.Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.sm), content = content)
    }
}
