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
import com.migratrack.app.ui.components.DestructiveButton
import com.migratrack.app.ui.components.Page
import com.migratrack.app.ui.components.PrimaryButton
import com.migratrack.app.ui.icons.Symbols

/**
 * Edit Episode, or Log Episode for a day picked in the calendar. Pushed under the tab bar with a back button,
 * like Settings. [onDone] gets the confirmation for the banner.
 */
@Composable
fun EpisodeEditorScreen(target: EpisodeTarget, onBack: () -> Unit, onDone: (message: String) -> Unit) {
    val graph = LocalAppGraph.current
    val viewModel = viewModel(key = target.toString()) { EpisodeFormViewModel(graph, target) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val haptics = LocalHapticFeedback.current
    val editing = target is EpisodeTarget.Edit

    LaunchedEffect(state.missing) {
        if (state.missing) onBack()
    }

    Page(title = if (editing) "Edit Episode" else "Log Episode", onBack = onBack) {
        val draft = state.draft ?: return@Page
        EpisodeFormFields(draft, state.baselineTriggers, viewModel, graph.today(), graph.timeZone())
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            PrimaryButton(
                text = if (editing) "Update Episode" else "Save Episode",
                icon = Symbols.Save,
                enabled = !state.saving,
                height = 56.dp,
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.Confirm)
                    viewModel.save { onDone(if (editing) "Episode updated." else SAVED_MESSAGE) }
                },
            )
            if (editing) {
                DestructiveButton("Delete Episode", enabled = !state.saving, onClick = {
                    viewModel.delete { onDone("Episode deleted.") }
                })
            }
        }
    }
}

const val SAVED_MESSAGE = "Episode saved. Take it easy and stay in low light."
