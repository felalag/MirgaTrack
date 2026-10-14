package com.migratrack.app.feature.log

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.migratrack.app.di.LocalAppGraph
import com.migratrack.app.feature.episode.EpisodeFormFields
import com.migratrack.app.feature.episode.EpisodeFormViewModel
import com.migratrack.app.feature.episode.EpisodeTarget
import com.migratrack.app.ui.components.Page
import com.migratrack.app.ui.components.PrimaryButton
import com.migratrack.app.ui.icons.Symbols

@Composable
fun LogEpisodeScreen(onOpenSettings: () -> Unit, onSaved: () -> Unit) {
    val graph = LocalAppGraph.current
    val viewModel = viewModel(key = "log-tab") { EpisodeFormViewModel(graph, EpisodeTarget.LogTab) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val haptics = LocalHapticFeedback.current

    Page(title = "Log Episode", onSettings = onOpenSettings) {
        val draft = state.draft ?: return@Page
        EpisodeFormFields(draft, state.baselineTriggers, viewModel, graph.today(), graph.timeZone())
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
