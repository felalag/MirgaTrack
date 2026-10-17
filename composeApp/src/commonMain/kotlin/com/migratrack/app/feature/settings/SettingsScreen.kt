package com.migratrack.app.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.migratrack.app.di.AppGraph
import com.migratrack.app.di.LocalAppGraph
import com.migratrack.app.feature.legal.LegalDocument
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.common_not_set
import com.migratrack.app.generated.resources.common_settings
import com.migratrack.app.generated.resources.common_treatment_blueprint
import com.migratrack.app.generated.resources.legal_privacy_policy
import com.migratrack.app.generated.resources.legal_terms_of_service
import com.migratrack.app.generated.resources.settings_add_name
import com.migratrack.app.generated.resources.settings_appearance
import com.migratrack.app.generated.resources.settings_appearance_value
import com.migratrack.app.generated.resources.settings_baseline_diagnoses
import com.migratrack.app.generated.resources.settings_delete_all
import com.migratrack.app.generated.resources.settings_delete_message
import com.migratrack.app.generated.resources.settings_delete_title
import com.migratrack.app.generated.resources.settings_edit_name_a11y
import com.migratrack.app.generated.resources.settings_episodes
import com.migratrack.app.generated.resources.settings_episodes_logged
import com.migratrack.app.generated.resources.settings_known_triggers
import com.migratrack.app.generated.resources.settings_known_triggers_count
import com.migratrack.app.generated.resources.settings_load_sample_data
import com.migratrack.app.generated.resources.settings_load_sample_data_subtitle
import com.migratrack.app.generated.resources.settings_member
import com.migratrack.app.generated.resources.settings_name_message
import com.migratrack.app.generated.resources.settings_name_placeholder
import com.migratrack.app.generated.resources.settings_name_title
import com.migratrack.app.generated.resources.settings_no_medications
import com.migratrack.app.generated.resources.settings_no_triggers
import com.migratrack.app.generated.resources.settings_section_baseline
import com.migratrack.app.generated.resources.settings_section_developer
import com.migratrack.app.generated.resources.settings_section_legal
import com.migratrack.app.generated.resources.settings_section_preferences
import com.migratrack.app.generated.resources.settings_version
import com.migratrack.app.generated.resources.settings_version_value
import com.migratrack.app.ui.components.GroupedSection
import com.migratrack.app.ui.components.ListDivider
import com.migratrack.app.ui.components.ListRow
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.Page
import com.migratrack.app.ui.components.RowTrailing
import com.migratrack.app.ui.components.pressable
import com.migratrack.app.ui.icons.AppIcons
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import com.migratrack.domain.analytics.DateText
import com.migratrack.domain.model.UserProfile
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

/** [diagnoses] and [treatments] are empty when nothing is selected; the screen then shows a hint. */
data class SettingsUiState(
    val name: String,
    val memberSince: String,
    val logCount: Int,
    val diagnoses: String,
    val triggerCount: Int,
    val treatments: String,
)

class SettingsViewModel(private val graph: AppGraph) : ViewModel() {
    val appInfo = graph.services.appInfo

    val state: StateFlow<SettingsUiState?> = combine(
        graph.profiles.observeUser(),
        graph.profiles.observeBaseline(),
        graph.episodes.observeAll(),
        graph.catalog.observeTriggers(),
        graph.catalog.observeMedications(),
    ) { user, baseline, episodes, triggers, medications ->
        val triggerCount = triggers.count { it.id in baseline?.triggerIds.orEmpty() }
        SettingsUiState(
            name = user?.name.orEmpty(),
            memberSince = user?.memberSince?.let { "${DateText.shortMonth(it.month)} ${it.year}" } ?: "–",
            logCount = episodes.size,
            diagnoses = baseline?.diagnoses?.sortedBy { it.ordinal }?.joinToString(", ") { it.title }.orEmpty(),
            triggerCount = triggerCount,
            treatments = medications.filter { it.id in baseline?.medicationIds.orEmpty() }.joinToString(", ") { it.displayName },
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun editName() = viewModelScope.launch {
        val user = graph.profiles.user() ?: UserProfile("", graph.today())
        val name = graph.services.dialogs.prompt(
            title = getString(Res.string.settings_name_title),
            message = getString(Res.string.settings_name_message),
            placeholder = getString(Res.string.settings_name_placeholder),
            initialValue = user.name,
        ) ?: return@launch
        graph.profiles.saveUser(user.copy(name = name.trim()))
    }

    /** Erases the data on this device and restarts onboarding. The watch drops its copy at the next sync. */
    fun deleteAllData() = viewModelScope.launch {
        val confirmed = graph.services.dialogs.confirm(
            title = getString(Res.string.settings_delete_title),
            message = getString(Res.string.settings_delete_message),
            confirmTitle = getString(Res.string.settings_delete_all),
            destructive = true,
        )
        if (confirmed) graph.services.store.eraser.eraseAll()
    }

    fun loadSampleData() = viewModelScope.launch { graph.loadSampleData() }
}

@Composable
fun SettingsScreen(onBack: () -> Unit, onEditBaseline: (step: Int) -> Unit, onOpenDocument: (LegalDocument) -> Unit) {
    val graph = LocalAppGraph.current
    val viewModel = viewModel { SettingsViewModel(graph) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Page(title = stringResource(Res.string.common_settings), onBack = onBack) {
        val current = state ?: return@Page
        ProfileCard(current, onEditName = viewModel::editName)

        val diagnoses = current.diagnoses.ifEmpty { stringResource(Res.string.common_not_set) }
        val triggers = if (current.triggerCount == 0) {
            stringResource(Res.string.settings_no_triggers)
        } else {
            pluralStringResource(Res.plurals.settings_known_triggers_count, current.triggerCount, current.triggerCount)
        }
        val treatments = current.treatments.ifEmpty { stringResource(Res.string.settings_no_medications) }
        GroupedSection(stringResource(Res.string.settings_section_baseline)) {
            ListRow(stringResource(Res.string.settings_baseline_diagnoses), Symbols.Psychology, MtColors.Cyan, subtitle = diagnoses, trailing = RowTrailing.Chevron) { onEditBaseline(1) }
            ListDivider()
            ListRow(stringResource(Res.string.settings_known_triggers), Symbols.Bolt, MtColors.Pink, subtitle = triggers, trailing = RowTrailing.Chevron) { onEditBaseline(2) }
            ListDivider()
            ListRow(stringResource(Res.string.common_treatment_blueprint), Symbols.Medication, MtColors.Green, subtitle = treatments, trailing = RowTrailing.Chevron) { onEditBaseline(3) }
        }

        GroupedSection(stringResource(Res.string.settings_section_preferences)) {
            ListRow(
                stringResource(Res.string.settings_appearance),
                Symbols.DarkMode,
                MtColors.Caption,
                trailing = RowTrailing.Value(stringResource(Res.string.settings_appearance_value)),
            )
            ListDivider()
            val info = viewModel.appInfo
            ListRow(
                stringResource(Res.string.settings_version),
                Symbols.Info,
                MtColors.Caption,
                trailing = RowTrailing.Value(stringResource(Res.string.settings_version_value, info.version, info.build)),
            )
        }

        GroupedSection(stringResource(Res.string.settings_section_legal)) {
            ListRow(stringResource(Res.string.legal_privacy_policy), AppIcons.Shield, MtColors.Cyan, trailing = RowTrailing.Chevron) { onOpenDocument(LegalDocument.PrivacyPolicy) }
            ListDivider()
            ListRow(stringResource(Res.string.legal_terms_of_service), AppIcons.Document, MtColors.Caption, trailing = RowTrailing.Chevron) { onOpenDocument(LegalDocument.TermsOfService) }
        }

        if (viewModel.appInfo.isDebug) {
            GroupedSection(stringResource(Res.string.settings_section_developer)) {
                ListRow(
                    stringResource(Res.string.settings_load_sample_data),
                    Symbols.Science,
                    MtColors.Orange,
                    subtitle = stringResource(Res.string.settings_load_sample_data_subtitle),
                    onClick = viewModel::loadSampleData,
                )
            }
        }

        Box(
            Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .clip(RoundedCornerShape(MtRadius.lg))
                .background(MtColors.Card)
                .pressable(pressedScale = 1f, onClick = viewModel::deleteAllData),
            contentAlignment = Alignment.Center,
        ) {
            MtText(stringResource(Res.string.settings_delete_all), MtTheme.type.bodyStrong, color = MtColors.Pink)
        }
    }
}

@Composable
private fun ProfileCard(state: SettingsUiState, onEditName: () -> Unit) {
    val editNameLabel = stringResource(Res.string.settings_edit_name_a11y)
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MtRadius.lg))
            .background(MtColors.Card)
            .padding(MtSpacing.md),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MtSpacing.sm),
    ) {
        Box(
            Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MtColors.ControlHigh)
                .border(2.dp, MtColors.Pink.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            val initials = state.name.split(' ').filter { it.isNotBlank() }.take(2).joinToString("") { it.first().uppercase() }
            if (initials.isNotEmpty()) {
                MtText(initials, MtTheme.type.title1, color = MtColors.TextPrimary)
            } else {
                com.migratrack.app.ui.icons.Icon(Symbols.Person, tint = MtColors.Caption, size = 40.dp)
            }
        }
        Row(
            Modifier
                .heightIn(min = MtSpacing.minTouch)
                .semantics { contentDescription = editNameLabel }
                .pressable(onClick = onEditName),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MtText(
                state.name.ifBlank { stringResource(Res.string.settings_add_name) },
                MtTheme.type.title2.copy(fontWeight = FontWeight.Bold),
                color = if (state.name.isBlank()) MtColors.TextSecondary else MtColors.TextPrimary,
            )
            com.migratrack.app.ui.icons.Icon(Symbols.Edit, tint = MtColors.TextSecondary, size = 18.dp, modifier = Modifier.padding(start = 6.dp))
        }
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(MtRadius.md))
                .background(MtColors.CardRaised)
                .padding(MtSpacing.sm),
        ) {
            ProfileStat(stringResource(Res.string.settings_member), state.memberSince, MtColors.TextPrimary, Modifier.weight(1f))
            ProfileStat(
                stringResource(Res.string.settings_episodes),
                pluralStringResource(Res.plurals.settings_episodes_logged, state.logCount, state.logCount),
                MtColors.BrandLabel,
                Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun ProfileStat(label: String, value: String, valueColor: androidx.compose.ui.graphics.Color, modifier: Modifier) {
    Column(modifier.semantics(mergeDescendants = true) {}, horizontalAlignment = Alignment.CenterHorizontally) {
        MtText(label.uppercase(), MtTheme.type.caption, color = MtColors.Caption)
        MtText(value, MtTheme.type.bodyStrong, color = valueColor)
    }
}
