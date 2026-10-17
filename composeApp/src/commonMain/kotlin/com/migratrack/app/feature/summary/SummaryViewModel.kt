package com.migratrack.app.feature.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migratrack.app.di.AppGraph
import com.migratrack.app.platform.SheetAction
import com.migratrack.domain.analytics.DateText
import com.migratrack.domain.analytics.SummaryCalculator
import com.migratrack.domain.analytics.SummarySnapshot
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.SleepSummary
import com.migratrack.domain.model.trackedSince
import com.migratrack.domain.platform.SleepAccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDateTime

sealed interface SleepCard {
    data object Loading : SleepCard
    /** No health store on this device: the card is hidden. */
    data object Hidden : SleepCard
    data object NeedsAccess : SleepCard
    data class Loaded(val sleep: SleepSummary?) : SleepCard
}

data class SummaryUiState(val snapshot: SummarySnapshot, val sleep: SleepCard)

class SummaryViewModel(private val graph: AppGraph) : ViewModel() {
    private val sleepCard = MutableStateFlow<SleepCard>(SleepCard.Loading)

    val state: StateFlow<SummaryUiState?> = combine(graph.episodes.observeAll(), graph.profiles.observeUser(), sleepCard) { episodes, user, sleep ->
        val today = graph.today()
        SummaryUiState(SummaryCalculator.calculate(episodes, today, graph.timeZone(), user.trackedSince(today)), sleep)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    init {
        refreshSleep()
    }

    fun refreshSleep() = viewModelScope.launch {
        val source = graph.services.sleep
        sleepCard.value = when (source.access()) {
            SleepAccess.Unavailable -> SleepCard.Hidden
            SleepAccess.NotRequested -> SleepCard.NeedsAccess
            SleepAccess.Requested -> SleepCard.Loaded(source.lastNight())
        }
    }

    fun connectHealth() = viewModelScope.launch {
        graph.services.sleep.requestAccess()
        refreshSleep()
    }

    /** Offers to edit or delete the last episode. */
    fun manageEpisode(episode: Episode, onEdit: (EpisodeId) -> Unit) = viewModelScope.launch {
        val dateTime = episode.startedAt.toLocalDateTime(graph.timeZone())
        val choice = graph.services.dialogs.chooseAction(
            title = "${DateText.fullDate(dateTime.date)} at ${DateText.time(dateTime)}",
            message = "Intensity ${episode.intensity.value}/10",
            actions = listOf(SheetAction("Edit Episode"), SheetAction("Delete Episode", destructive = true)),
        )
        when (choice) {
            0 -> onEdit(episode.id)
            1 -> graph.episodes.delete(episode.id)
        }
    }
}
