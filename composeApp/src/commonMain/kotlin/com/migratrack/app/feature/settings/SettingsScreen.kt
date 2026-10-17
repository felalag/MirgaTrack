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

data class SettingsUiState(
    val name: String,
    val memberSince: String,
    val logCount: Int,
    val diagnoses: String,
    val triggers: String,
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
            diagnoses = baseline?.diagnoses?.sortedBy { it.ordinal }?.joinToString(", ") { it.title }.orEmpty().ifEmpty { "Not set" },
            triggers = when (triggerCount) {
                0 -> "No triggers selected"
                1 -> "1 known trigger"
                else -> "$triggerCount known triggers"
            },
            treatments = medications.filter { it.id in baseline?.medicationIds.orEmpty() }
                .joinToString(", ") { it.displayName }.ifEmpty { "No medications selected" },
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun editName() = viewModelScope.launch {
        val user = graph.profiles.user() ?: UserProfile("", graph.today())
        val name = graph.services.dialogs.prompt(
            title = "Your Name",
            message = "It appears on the reports you export.",
            placeholder = "Name",
            initialValue = user.name,
        ) ?: return@launch
        graph.profiles.saveUser(user.copy(name = name.trim()))
    }

    /** Erases the data on this device and restarts onboarding. The watch drops its copy at the next sync. */
    fun deleteAllData() = viewModelScope.launch {
        val confirmed = graph.services.dialogs.confirm(
            title = "Delete All Data?",
            message = "MigraTrack keeps your data on this iPhone and a copy on your Apple Watch. This permanently deletes every episode and your baseline profile; the watch deletes its copy at the next sync. Device backups keep their copy. Export a report first if you need it.",
            confirmTitle = "Delete All Data",
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

    Page(title = "Settings", onBack = onBack) {
        val current = state ?: return@Page
        ProfileCard(current, onEditName = viewModel::editName)

        GroupedSection("Clinical Baseline & Protocol") {
            ListRow("Baseline Diagnoses", Symbols.Psychology, MtColors.Cyan, subtitle = current.diagnoses, trailing = RowTrailing.Chevron) { onEditBaseline(1) }
            ListDivider()
            ListRow("Known Triggers", Symbols.Bolt, MtColors.Pink, subtitle = current.triggers, trailing = RowTrailing.Chevron) { onEditBaseline(2) }
            ListDivider()
            ListRow("Treatment Blueprint", Symbols.Medication, MtColors.Green, subtitle = current.treatments, trailing = RowTrailing.Chevron) { onEditBaseline(3) }
        }

        GroupedSection("Preferences") {
            ListRow("Appearance", Symbols.DarkMode, MtColors.Caption, trailing = RowTrailing.Value("Dark (Locked)"))
            ListDivider()
            val info = viewModel.appInfo
            ListRow("Version", Symbols.Info, MtColors.Caption, trailing = RowTrailing.Value("v${info.version} (Build ${info.build})"))
        }

        GroupedSection("Legal") {
            ListRow("Privacy Policy", AppIcons.Shield, MtColors.Cyan, trailing = RowTrailing.Chevron) { onOpenDocument(LegalDocument.PrivacyPolicy) }
            ListDivider()
            ListRow("Terms of Service", AppIcons.Document, MtColors.Caption, trailing = RowTrailing.Chevron) { onOpenDocument(LegalDocument.TermsOfService) }
        }

        if (viewModel.appInfo.isDebug) {
            GroupedSection("Developer") {
                ListRow(
                    "Load Sample Data",
                    Symbols.Science,
                    MtColors.Orange,
                    subtitle = "Adds a year of generated episodes to preview the charts",
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
            MtText("Delete All Data", MtTheme.type.bodyStrong, color = MtColors.Pink)
        }
    }
}

@Composable
private fun ProfileCard(state: SettingsUiState, onEditName: () -> Unit) {
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
                .semantics { contentDescription = "Edit name" }
                .pressable(onClick = onEditName),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MtText(
                state.name.ifBlank { "Add Your Name" },
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
            ProfileStat("Member", state.memberSince, MtColors.TextPrimary, Modifier.weight(1f))
            ProfileStat("Episodes", "${state.logCount} Logged", MtColors.BrandLabel, Modifier.weight(1f))
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
