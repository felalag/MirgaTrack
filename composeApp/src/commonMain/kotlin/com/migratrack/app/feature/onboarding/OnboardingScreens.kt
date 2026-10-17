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
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.common_back
import com.migratrack.app.generated.resources.common_medical_disclaimer
import com.migratrack.app.generated.resources.common_treatment_blueprint
import com.migratrack.app.generated.resources.onboarding_add_custom_medication
import com.migratrack.app.generated.resources.onboarding_add_custom_trigger
import com.migratrack.app.generated.resources.onboarding_back_to_diagnoses
import com.migratrack.app.generated.resources.onboarding_back_to_triggers
import com.migratrack.app.generated.resources.onboarding_continue_to_medications
import com.migratrack.app.generated.resources.onboarding_continue_to_triggers
import com.migratrack.app.generated.resources.onboarding_diagnoses_subtitle
import com.migratrack.app.generated.resources.onboarding_diagnoses_title
import com.migratrack.app.generated.resources.onboarding_episode_duration
import com.migratrack.app.generated.resources.onboarding_episode_frequency
import com.migratrack.app.generated.resources.onboarding_finish_setup
import com.migratrack.app.generated.resources.onboarding_intensity_baseline
import com.migratrack.app.generated.resources.onboarding_primary_diagnosis
import com.migratrack.app.generated.resources.onboarding_save_changes
import com.migratrack.app.generated.resources.onboarding_step
import com.migratrack.app.generated.resources.onboarding_treatment_subtitle
import com.migratrack.app.generated.resources.onboarding_triggers_subtitle
import com.migratrack.app.generated.resources.onboarding_triggers_title
import com.migratrack.app.generated.resources.onboarding_update_later
import com.migratrack.app.navigation.navigateFrom
import com.migratrack.app.navigation.popFrom
import com.migratrack.app.ui.Numbers
import com.migratrack.app.ui.accent
import com.migratrack.app.ui.classText
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
import com.migratrack.app.ui.descriptionText
import com.migratrack.app.ui.icon
import com.migratrack.app.ui.icons.Icon
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.nameText
import com.migratrack.app.ui.resolve
import com.migratrack.app.ui.subtitle
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import com.migratrack.app.ui.title
import com.migratrack.app.ui.uppercaseInAppLanguage
import com.migratrack.domain.model.Diagnosis
import com.migratrack.domain.model.EpisodeFrequency
import com.migratrack.domain.model.MedicationRole
import com.migratrack.domain.model.TriggerCategory
import com.migratrack.domain.model.TypicalDuration
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource

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
        composable<WelcomeStep> { entry ->
            WelcomeScreen(
                onGetStarted = { navController.navigateFrom(entry, DiagnosesStep) },
                onOpenDocument = { navController.navigateFrom(entry, DocumentStep(it.name)) },
            )
        }
        composable<DocumentStep> { entry ->
            LegalScreen(LegalDocument.valueOf(entry.toRoute<DocumentStep>().document), onBack = { navController.popFrom(entry) })
        }
        composable<DiagnosesStep> { entry ->
            DiagnosesStepScreen(viewModel, step = 1, onBack = { navController.popFrom(entry) }, primaryLabel = stringResource(Res.string.onboarding_continue_to_triggers)) {
                navController.navigateFrom(entry, TriggersStep)
            }
        }
        composable<TriggersStep> { entry ->
            TriggersStepScreen(
                viewModel,
                step = 2,
                onBack = { navController.popFrom(entry) },
                primaryLabel = stringResource(Res.string.onboarding_continue_to_medications),
                backLabel = stringResource(Res.string.onboarding_back_to_diagnoses),
            ) { navController.navigateFrom(entry, TreatmentStep) }
        }
        composable<TreatmentStep> { entry ->
            TreatmentStepScreen(
                viewModel,
                step = 3,
                onBack = { navController.popFrom(entry) },
                primaryLabel = stringResource(Res.string.onboarding_finish_setup),
                backLabel = stringResource(Res.string.onboarding_back_to_triggers),
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
    val saveLabel = stringResource(Res.string.onboarding_save_changes)
    when (step) {
        1 -> DiagnosesStepScreen(viewModel, step = null, onBack = onClose, primaryLabel = saveLabel, onPrimary = save)
        2 -> TriggersStepScreen(viewModel, step = null, onBack = onClose, primaryLabel = saveLabel, backLabel = null, onPrimary = save)
        else -> TreatmentStepScreen(viewModel, step = null, onBack = onClose, primaryLabel = saveLabel, backLabel = null, onPrimary = save)
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
        title = stringResource(Res.string.onboarding_diagnoses_title),
        subtitle = stringResource(Res.string.onboarding_diagnoses_subtitle),
        onBack = onBack,
    ) {
        StepSection(stringResource(Res.string.onboarding_primary_diagnosis)) {
            Diagnosis.entries.forEach { diagnosis ->
                SelectionCard(
                    title = stringResource(diagnosis.title),
                    subtitle = stringResource(diagnosis.subtitle),
                    icon = diagnosis.icon,
                    selected = diagnosis in draft.diagnoses,
                    accent = MtColors.Pink,
                    style = SelectionStyle.Diagnosis,
                    onClick = { viewModel.toggleDiagnosis(diagnosis) },
                )
            }
        }
        StepSection(stringResource(Res.string.onboarding_episode_frequency)) {
            val labels = EpisodeFrequency.entries.associateWith { stringResource(it.title) }
            ChoiceChips(EpisodeFrequency.entries, draft.frequency, { labels.getValue(it) }, viewModel::setFrequency, grouped = true)
        }
        StepSection(stringResource(Res.string.onboarding_episode_duration)) {
            val labels = TypicalDuration.entries.associateWith { stringResource(it.title) }
            ChoiceChips(TypicalDuration.entries, draft.typicalDuration, { labels.getValue(it) }, viewModel::setDuration)
        }
        StepSection(stringResource(Res.string.onboarding_intensity_baseline)) {
            Box(Modifier.fillMaxWidth().background(MtColors.CardRaised, androidx.compose.foundation.shape.RoundedCornerShape(MtRadius.lg)).padding(12.dp)) {
                PainScale(draft.typicalIntensity, viewModel::setIntensity)
            }
        }
        StepActions(
            primaryLabel = primaryLabel,
            primaryEnabled = state.canContinueFromDiagnoses && !state.saving,
            onPrimary = onPrimary,
            footnote = if (step != null) stringResource(Res.string.onboarding_update_later) else null,
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
        title = stringResource(Res.string.onboarding_triggers_title),
        subtitle = stringResource(Res.string.onboarding_triggers_subtitle),
        onBack = onBack,
    ) {
        state.triggers.groupBy { it.category }.entries.sortedBy { it.key.ordinal }.forEach { (category, triggers) ->
            StepSection(stringResource(category.title)) {
                triggers.forEach { trigger ->
                    SelectionCard(
                        title = trigger.nameText.resolve(),
                        subtitle = trigger.descriptionText.resolve(),
                        icon = trigger.icon,
                        selected = trigger.id in state.draft.triggerIds,
                        accent = category.accent,
                        onClick = { viewModel.toggleTrigger(trigger.id) },
                    )
                }
            }
        }
        AddRow(stringResource(Res.string.onboarding_add_custom_trigger), accent = TriggerCategory.Custom.accent, onClick = viewModel::addCustomTrigger)
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
        title = stringResource(Res.string.common_treatment_blueprint),
        subtitle = stringResource(Res.string.onboarding_treatment_subtitle),
        onBack = onBack,
    ) {
        MedicationRole.entries.forEach { role ->
            StepSection(stringResource(role.title)) {
                state.medications.filter { it.role == role }.forEach { medication ->
                    SelectionCard(
                        title = medication.nameText.resolve(),
                        subtitle = medication.classText.resolve(),
                        icon = medication.icon,
                        selected = medication.id in state.draft.medicationIds,
                        accent = MtColors.Green,
                        onClick = { viewModel.toggleMedication(medication.id) },
                    )
                }
            }
        }
        AddRow(stringResource(Res.string.onboarding_add_custom_medication), accent = MtColors.Green, onClick = viewModel::addCustomMedication)
        StepActions(
            primaryLabel,
            primaryEnabled = !state.saving,
            onPrimary = onPrimary,
            footnote = if (step != null) stringResource(Res.string.common_medical_disclaimer) else null,
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
    val backLabel = stringResource(Res.string.common_back)
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
                    Modifier.size(MtSpacing.minTouch).semantics { contentDescription = backLabel }.pressable(onClick = onBack),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Symbols.ArrowBackIosNew, tint = MtColors.BrandLabel, size = 24.dp)
                }
            }
            Spacer(Modifier.weight(1f))
            if (step != null) {
                MtText(stringResource(Res.string.onboarding_step, Numbers.integer(step), Numbers.integer(STEP_COUNT)).uppercaseInAppLanguage(), MtTheme.type.caption, color = MtColors.Pink, modifier = Modifier.padding(end = 8.dp))
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
