package com.migratrack.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.migratrack.app.di.AppGraph
import com.migratrack.app.di.LocalAppGraph
import com.migratrack.app.feature.onboarding.OnboardingFlow
import com.migratrack.app.navigation.MainScaffold
import com.migratrack.app.ui.theme.MigraTrackTheme
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun App(graph: AppGraph) {
    MigraTrackTheme {
        CompositionLocalProvider(LocalAppGraph provides graph) {
            val root = viewModel { RootViewModel(graph.profiles) }
            val state by root.state.collectAsStateWithLifecycle()
            Box(Modifier.fillMaxSize().background(MtColors.Canvas)) {
                Crossfade(targetState = state) { current ->
                    when (current) {
                        RootState.Loading -> Unit
                        is RootState.Onboarding -> OnboardingFlow(session = current.session)
                        RootState.Ready -> MainScaffold()
                    }
                }
            }
        }
    }
}

sealed interface RootState {
    data object Loading : RootState

    /** [session] changes each time onboarding starts again, for example after sign out. */
    data class Onboarding(val session: Int) : RootState
    data object Ready : RootState
}

class RootViewModel(profiles: ProfileRepository) : ViewModel() {
    private var onboardingSessions = 0

    val state: StateFlow<RootState> = profiles.observeBaseline()
        .map { baseline -> if (baseline == null) RootState.Onboarding(++onboardingSessions) else RootState.Ready }
        .stateIn(viewModelScope, SharingStarted.Eagerly, RootState.Loading)
}
