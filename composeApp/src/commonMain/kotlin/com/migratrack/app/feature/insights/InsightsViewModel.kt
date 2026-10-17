package com.migratrack.app.feature.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migratrack.app.di.AppGraph
import com.migratrack.app.feature.episode.confirmDelete
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.common_delete_episode
import com.migratrack.app.generated.resources.common_edit_episode
import com.migratrack.app.generated.resources.insights_choose_episode_message
import com.migratrack.app.generated.resources.insights_episode_option
import com.migratrack.app.generated.resources.insights_month_picker_title
import com.migratrack.app.platform.DatePickerMode
import com.migratrack.app.platform.SheetAction
import com.migratrack.app.ui.Dates
import com.migratrack.app.ui.Numbers
import com.migratrack.domain.analytics.CalendarBuilder
import com.migratrack.domain.analytics.DayEpisodes
import com.migratrack.domain.analytics.Insights
import com.migratrack.domain.analytics.InsightsCalculator
import com.migratrack.domain.analytics.InsightsPeriod
import com.migratrack.domain.analytics.MonthCalendar
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.Trigger
import com.migratrack.domain.model.trackedSince
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.YearMonth
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.yearMonth
import org.jetbrains.compose.resources.getPluralString
import org.jetbrains.compose.resources.getString

data class InsightsUiState(
    val period: InsightsPeriod,
    val insights: Insights,
    val calendarOpen: Boolean,
    val calendar: MonthCalendar,
    /** The picked day of the shown month, or null. */
    val selected: LocalDate?,
    val day: DayEpisodes?,
    /** The trigger catalog by id, for the icons and colors of the Possible Triggers card. */
    val triggerCatalog: Map<String, Trigger>,
) {
    val canShowNextMonth: Boolean get() = calendar.month < insights.today.yearMonth
}

/** The browsed month drives the chart, the month pill and the calendar together. */
class InsightsViewModel(private val graph: AppGraph) : ViewModel() {
    // The base Insights screen of the design opens on the month view with the calendar closed.
    private val period = MutableStateFlow(InsightsPeriod.Month)
    private val month = MutableStateFlow(graph.today().yearMonth)
    private val calendarOpen = MutableStateFlow(false)
    private val selected = MutableStateFlow<LocalDate?>(graph.today())

    private val calendarState = combine(calendarOpen, selected, ::Pair)
    private val triggerCatalog = graph.catalog.observeTriggers().map { triggers -> triggers.associateBy { it.id } }
    private val data = combine(graph.episodes.observeAll(), graph.profiles.observeUser(), triggerCatalog, ::Triple)

    val state: StateFlow<InsightsUiState?> =
        combine(data, period, month, calendarState) { (episodes, user, triggerCatalog), period, month, (open, day) ->
            val today = graph.today()
            val zone = graph.timeZone()
            val trackedSince = user.trackedSince(today)
            val insights = InsightsCalculator.calculate(episodes, period, today, zone, trackedSince, Dates.firstDayOfWeek, month)
            val shownDay = day?.takeIf { it.yearMonth == insights.month && it <= today }
            InsightsUiState(
                period = period,
                insights = insights,
                calendarOpen = open,
                calendar = CalendarBuilder.month(episodes, insights.month, today, zone, trackedSince, Dates.firstDayOfWeek),
                selected = shownDay,
                day = shownDay?.let { CalendarBuilder.day(episodes, it, zone) },
                triggerCatalog = triggerCatalog,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun selectPeriod(value: InsightsPeriod) {
        period.value = value
    }

    fun toggleCalendar() = calendarOpen.update { !it }

    fun selectDay(date: LocalDate) {
        selected.value = date
    }

    /** Shows [target], or the current month for a later one. Today stays picked in the current month. */
    fun showMonth(target: YearMonth) {
        val today = graph.today()
        val shown = minOf(target, today.yearMonth)
        month.value = shown
        selected.value = if (shown == today.yearMonth) today else null
    }

    fun pickMonth() = viewModelScope.launch {
        val zone = graph.timeZone()
        val picked = graph.services.dialogs.pickDate(
            title = getString(Res.string.insights_month_picker_title),
            initial = LocalDateTime(month.value.firstDay, LocalTime(12, 0)).toInstant(zone),
            latest = graph.clock.now(),
            mode = DatePickerMode.MonthAndYear,
        ) ?: return@launch
        showMonth(picked.toLocalDateTime(zone).date.yearMonth)
    }

    fun editEpisode(day: DayEpisodes, open: (EpisodeId) -> Unit) = viewModelScope.launch {
        chooseEpisode(day, getString(Res.string.common_edit_episode), destructive = false)?.let { open(it.id) }
    }

    fun deleteEpisode(day: DayEpisodes) = viewModelScope.launch {
        val episode = if (day.episodes.size == 1) {
            day.episodes.single().takeIf { confirmDelete(graph, it) }
        } else {
            chooseEpisode(day, getString(Res.string.common_delete_episode), destructive = true)
        }
        episode?.let { graph.episodes.delete(it.id) }
    }

    /** The only episode of the day, or the one the user picks from an action sheet. */
    private suspend fun chooseEpisode(day: DayEpisodes, title: String, destructive: Boolean): Episode? {
        if (day.episodes.size == 1) return day.episodes.single()
        val zone = graph.timeZone()
        val index = graph.services.dialogs.chooseAction(
            title = title,
            message = getPluralString(
                Res.plurals.insights_choose_episode_message,
                day.episodes.size,
                Dates.weekdayMonthDay(day.date),
                Numbers.integer(day.episodes.size),
            ),
            actions = day.episodes.map {
                val time = Dates.time(it.startedAt.toLocalDateTime(zone))
                SheetAction(getString(Res.string.insights_episode_option, time, Numbers.integer(it.intensity.value)), destructive)
            },
        ) ?: return null
        return day.episodes[index]
    }
}
