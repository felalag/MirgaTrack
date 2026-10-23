package com.migratrack.app.feature.episode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.migratrack.app.di.LocalAppGraph
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.common_delete_episode
import com.migratrack.app.generated.resources.common_edit_episode
import com.migratrack.app.generated.resources.common_save_episode
import com.migratrack.app.generated.resources.episode_deleted_banner
import com.migratrack.app.generated.resources.episode_log_title
import com.migratrack.app.generated.resources.episode_saved_banner
import com.migratrack.app.generated.resources.episode_update
import com.migratrack.app.generated.resources.episode_updated_banner
import com.migratrack.app.ui.components.DestructiveButton
import com.migratrack.app.ui.components.Page
import com.migratrack.app.ui.components.PrimaryButton
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.stringResource
import org.jetbrains.compose.resources.StringResource

/**
 * Edit Episode, or Log Episode for a day picked in the calendar. Pushed under the tab bar with a back button,
 * like Settings. [onDone] gets the confirmation for the banner.
 */
@Composable
fun EpisodeEditorScreen(target: EpisodeTarget, onBack: () -> Unit, onDone: (message: StringResource) -> Unit) {
    val graph = LocalAppGraph.current
    val viewModel = viewModel(key = target.toString()) { EpisodeFormViewModel(graph, target) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val haptics = LocalHapticFeedback.current
    val editing = target is EpisodeTarget.Edit

    LaunchedEffect(state.missing) {
        if (state.missing) onBack()
    }

    Page(title = stringResource(if (editing) Res.string.common_edit_episode else Res.string.episode_log_title), onBack = onBack) {
        val draft = state.draft ?: return@Page
        EpisodeFormFields(draft, state.baselineTriggers, viewModel, graph.today(), graph.timeZone())
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            PrimaryButton(
                text = stringResource(if (editing) Res.string.episode_update else Res.string.common_save_episode),
                icon = Symbols.Save,
                enabled = !state.saving,
                height = 56.dp,
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.Confirm)
                    viewModel.save { onDone(if (editing) Res.string.episode_updated_banner else Res.string.episode_saved_banner) }
                },
            )
            if (editing) {
                DestructiveButton(stringResource(Res.string.common_delete_episode), enabled = !state.saving, onClick = {
                    viewModel.delete { onDone(Res.string.episode_deleted_banner) }
                })
            }
        }
    }
}
