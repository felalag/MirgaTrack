package com.migratrack.app.feature.onboarding

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.migratrack.app.di.LocalAppGraph
import com.migratrack.app.feature.legal.LegalDocument
import com.migratrack.app.feature.legal.LegalScreen
import com.migratrack.app.feature.welcome.WelcomeScreen
import com.migratrack.app.ui.accent
import com.migratrack.app.ui.components.AddRow
import com.migratrack.app.ui.components.ChoiceChips
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.PainScale
import com.migratrack.app.ui.components.PrimaryButton
import com.migratrack.app.ui.components.SectionCaption
import com.migratrack.app.ui.components.SelectionCard
import com.migratrack.app.ui.components.SelectionStyle
import com.migratrack.app.ui.components.contentColumn
import com.migratrack.app.ui.components.glow
import com.migratrack.app.ui.components.pressable
import com.migratrack.app.ui.icon
import com.migratrack.app.ui.icons.Icon
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import com.migratrack.domain.model.Diagnosis
import com.migratrack.domain.model.EpisodeFrequency
import com.migratrack.domain.model.MedicationRole
import com.migratrack.domain.model.TriggerCategory
import com.migratrack.domain.model.TypicalDuration
import kotlinx.serialization.Serializable

@Serializable private data object WelcomeStep
@Serializable private data class DocumentStep(val document: String)
@Serializable private data object DiagnosesStep
@Serializable private data object TriggersStep
@Serializable private data object TreatmentStep

private const val STEP_COUNT = 3

/** First-launch flow: welcome, diagnoses, triggers, treatment. Each step pushes with the iOS slide and swipe back. */
@Composable
fun OnboardingFlow(session: Int) {
    val graph = LocalAppGraph.current
    val viewModel = viewModel(key = "onboarding-$session") { BaselineFormViewModel(graph, editing = false) }
    val navController = rememberNavController()
    NavHost(navController, startDestination = WelcomeStep) {
        composable<WelcomeStep> {
            WelcomeScreen(
                onGetStarted = { navController.navigate(DiagnosesStep) },
                onOpenDocument = { navController.navigate(DocumentStep(it.name)) },
            )
        }
        composable<DocumentStep> { entry ->
            LegalScreen(LegalDocument.valueOf(entry.toRoute<DocumentStep>().document), onBack = { navController.popBackStack() })
        }
        composable<DiagnosesStep> {
            DiagnosesStepScreen(viewModel, step = 1, onBack = { navController.popBackStack() }, primaryLabel = "Continue to Triggers") {
                navController.navigate(TriggersStep)
            }
        }
        composable<TriggersStep> {
            TriggersStepScreen(
                viewModel,
                step = 2,
                onBack = { navController.popBackStack() },
                primaryLabel = "Continue to Medications",
                backLabel = "Back to Diagnoses",
            ) { navController.navigate(TreatmentStep) }
        }
        composable<TreatmentStep> {
            TreatmentStepScreen(
                viewModel,
                step = 3,
                onBack = { navController.popBackStack() },
                primaryLabel = "Finish Setup",
                backLabel = "Back to Triggers",
            ) { viewModel.finishOnboarding() }
        }
    }
}

/** One onboarding step opened from Settings; the button saves and closes it. */
@Composable
fun BaselineEditor(step: Int, onClose: () -> Unit) {
    val graph = LocalAppGraph.current
    val viewModel = viewModel { BaselineFormViewModel(graph, editing = true) }
    val save: () -> Unit = { viewModel.saveEdits(onClose) }
    when (step) {
        1 -> DiagnosesStepScreen(viewModel, step = null, onBack = onClose, primaryLabel = "Save Changes", onPrimary = save)
        2 -> TriggersStepScreen(viewModel, step = null, onBack = onClose, primaryLabel = "Save Changes", backLabel = null, onPrimary = save)
        else -> TreatmentStepScreen(viewModel, step = null, onBack = onClose, primaryLabel = "Save Changes", backLabel = null, onPrimary = save)
    }
}

@Composable
private fun DiagnosesStepScreen(
    viewModel: BaselineFormViewModel,
    step: Int?,
    onBack: (() -> Unit)?,
    primaryLabel: String,
    onPrimary: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val draft = state.draft
    StepPage(
        step = step,
        title = "Diagnoses & History",
        subtitle = "Set up your headache profile. Your logs and reports start from it.",
        onBack = onBack,
    ) {
        StepSection("Primary Diagnosis") {
            Diagnosis.entries.forEach { diagnosis ->
                SelectionCard(
                    title = diagnosis.title,
                    subtitle = diagnosis.subtitle,
                    icon = diagnosis.icon,
                    selected = diagnosis in draft.diagnoses,
                    accent = MtColors.Pink,
                    style = SelectionStyle.Diagnosis,
                    onClick = { viewModel.toggleDiagnosis(diagnosis) },
                )
            }
        }
        StepSection("Monthly Episode Frequency") {
            ChoiceChips(EpisodeFrequency.entries, draft.frequency, { it.title }, viewModel::setFrequency, grouped = true)
        }
        StepSection("Typical Episode Duration") {
            ChoiceChips(TypicalDuration.entries, draft.typicalDuration, { it.title }, viewModel::setDuration)
        }
        StepSection("Typical Pain Intensity Baseline") {
            Box(Modifier.fillMaxWidth().background(MtColors.CardRaised, androidx.compose.foundation.shape.RoundedCornerShape(MtRadius.lg)).padding(12.dp)) {
                PainScale(draft.typicalIntensity, viewModel::setIntensity)
            }
        }
        StepActions(
            primaryLabel = primaryLabel,
            primaryEnabled = state.canContinueFromDiagnoses && !state.saving,
            onPrimary = onPrimary,
            footnote = if (step != null) "You can update your baseline profile anytime in Settings." else null,
            forwardArrow = step != null,
        )
    }
}

@Composable
private fun TriggersStepScreen(
    viewModel: BaselineFormViewModel,
    step: Int?,
    onBack: () -> Unit,
    primaryLabel: String,
    backLabel: String?,
    onPrimary: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    StepPage(
        step = step,
        title = "Your Triggers",
        subtitle = "Select your known or suspected triggers. Your report lists them for your clinician.",
        onBack = onBack,
    ) {
        state.triggers.groupBy { it.category }.entries.sortedBy { it.key.ordinal }.forEach { (category, triggers) ->
            StepSection(category.title) {
                triggers.forEach { trigger ->
                    SelectionCard(
                        title = trigger.name,
                        subtitle = trigger.description,
                        icon = trigger.icon,
                        selected = trigger.id in state.draft.triggerIds,
                        accent = category.accent,
                        onClick = { viewModel.toggleTrigger(trigger.id) },
                    )
                }
            }
        }
        AddRow("Add custom trigger", accent = TriggerCategory.Custom.accent, onClick = viewModel::addCustomTrigger)
        StepActions(primaryLabel, primaryEnabled = !state.saving, onPrimary = onPrimary, backLabel = backLabel, onBack = onBack)
    }
}

@Composable
private fun TreatmentStepScreen(
    viewModel: BaselineFormViewModel,
    step: Int?,
    onBack: () -> Unit,
    primaryLabel: String,
    backLabel: String?,
    onPrimary: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    StepPage(
        step = step,
        title = "Treatment Blueprint",
        subtitle = "Choose what you take. Acute medications pre-fill every new episode log, so logging takes one tap.",
        onBack = onBack,
    ) {
        MedicationRole.entries.forEach { role ->
            StepSection(role.title) {
                state.medications.filter { it.role == role }.forEach { medication ->
                    SelectionCard(
                        title = medication.displayName,
                        subtitle = medication.drugClass,
                        icon = medication.icon,
                        selected = medication.id in state.draft.medicationIds,
                        accent = MtColors.Green,
                        onClick = { viewModel.toggleMedication(medication.id) },
                    )
                }
            }
        }
        AddRow("Add custom medication", accent = MtColors.Green, onClick = viewModel::addCustomMedication)
        StepActions(
            primaryLabel,
            primaryEnabled = !state.saving,
            onPrimary = onPrimary,
            footnote = if (step != null) "MigraTrack is a diary, not medical advice. Talk to your doctor before you change your treatment." else null,
            backLabel = backLabel,
            onBack = onBack,
        )
    }
}

/** Step header with the back chevron, "Step N/3", the progress bar, and the large title. */
@Composable
private fun StepPage(
    step: Int?,
    title: String,
    subtitle: String,
    onBack: (() -> Unit)?,
    content: @Composable ColumnScope.() -> Unit,
) {
    val top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Box(Modifier.fillMaxSize().background(MtColors.Canvas)) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = top + 56.dp, bottom = bottom + MtSpacing.lg)
                .contentColumn()
                .padding(horizontal = MtSpacing.screen),
            verticalArrangement = Arrangement.spacedBy(MtSpacing.lg),
        ) {
            if (step != null) StepProgress(step)
            Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.xs)) {
                MtText(title, MtTheme.type.largeTitle, modifier = Modifier.semantics { heading() })
                MtText(subtitle, MtTheme.type.body, color = MtColors.TextSecondary)
            }
            content()
        }
        Row(
            Modifier
                .fillMaxWidth()
                .background(MtColors.Canvas)
                .padding(top = top)
                .contentColumn()
                .height(56.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (onBack != null) {
                Box(
                    Modifier.size(MtSpacing.minTouch).semantics { contentDescription = "Back" }.pressable(onClick = onBack),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Symbols.ArrowBackIosNew, tint = MtColors.BrandLabel, size = 24.dp)
                }
            }
            Spacer(Modifier.weight(1f))
            if (step != null) {
                MtText("STEP $step/$STEP_COUNT", MtTheme.type.caption, color = MtColors.Pink, modifier = Modifier.padding(end = 8.dp))
            }
        }
    }
}

@Composable
private fun StepProgress(step: Int) {
    Row(Modifier.fillMaxWidth().height(6.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(STEP_COUNT) { index ->
            val done = index < step
            Box(
                Modifier
                    .weight(1f)
                    .height(6.dp)
                    .then(if (done) Modifier.glow(MtColors.Pink, 10.dp, alpha = 0.6f) else Modifier)
                    .background(if (done) MtColors.Pink else MtColors.Control, CircleShape),
            )
        }
    }
}

@Composable
private fun StepSection(caption: String, content: @Composable ColumnScope.() -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SectionCaption(caption)
        content()
    }
}

@Composable
private fun StepActions(
    primaryLabel: String,
    primaryEnabled: Boolean,
    onPrimary: () -> Unit,
    footnote: String? = null,
    forwardArrow: Boolean = false,
    backLabel: String? = null,
    onBack: (() -> Unit)? = null,
) {
    Column(
        Modifier.fillMaxWidth().padding(top = MtSpacing.sm),
        verticalArrangement = Arrangement.spacedBy(MtSpacing.md),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PrimaryButton(primaryLabel, onClick = onPrimary, enabled = primaryEnabled, trailingIcon = Symbols.ArrowForward.takeIf { forwardArrow })
        if (footnote != null) {
            MtText(footnote, MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal), color = MtColors.TextSecondary, textAlign = TextAlign.Center)
        }
        if (backLabel != null && onBack != null) {
            Row(
                Modifier.height(MtSpacing.minTouch).pressable(onClick = onBack).padding(horizontal = MtSpacing.md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Symbols.ChevronLeft, tint = MtColors.TextSecondary, size = 18.dp)
                MtText(backLabel, MtTheme.type.subhead.copy(fontWeight = FontWeight.SemiBold), color = MtColors.TextSecondary)
            }
        }
    }
}
