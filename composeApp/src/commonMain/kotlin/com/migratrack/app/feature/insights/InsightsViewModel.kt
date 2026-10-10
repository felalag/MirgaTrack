package com.migratrack.app.feature.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migratrack.app.di.AppGraph
import com.migratrack.app.feature.episode.confirmDelete
import com.migratrack.app.platform.DatePickerMode
import com.migratrack.app.platform.SheetAction
import com.migratrack.domain.analytics.CalendarBuilder
import com.migratrack.domain.analytics.DateText
import com.migratrack.domain.analytics.DayEpisodes
import com.migratrack.domain.analytics.Insights
import com.migratrack.domain.analytics.InsightsCalculator
import com.migratrack.domain.analytics.InsightsPeriod
import com.migratrack.domain.analytics.MonthCalendar
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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

data class InsightsUiState(
    val period: InsightsPeriod,
    val insights: Insights,
    val calendarOpen: Boolean,
    val calendar: MonthCalendar,
    /** The picked day of the shown month, or null. */
    val selected: LocalDate?,
    val day: DayEpisodes?,
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

    val state: StateFlow<InsightsUiState?> =
        combine(graph.episodes.observeAll(), period, month, calendarOpen, selected) { episodes, period, month, open, day ->
            val today = graph.today()
            val zone = graph.timeZone()
            val insights = InsightsCalculator.calculate(episodes, period, today, zone, month)
            val shownDay = day?.takeIf { it.yearMonth == insights.month && it <= today }
            InsightsUiState(
                period = period,
                insights = insights,
                calendarOpen = open,
                calendar = CalendarBuilder.month(episodes, insights.month, today, zone),
                selected = shownDay,
                day = shownDay?.let { CalendarBuilder.day(episodes, it, zone) },
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
            title = "Month",
            initial = LocalDateTime(month.value.firstDay, LocalTime(12, 0)).toInstant(zone),
            latest = graph.clock.now(),
            mode = DatePickerMode.MonthAndYear,
        ) ?: return@launch
        showMonth(picked.toLocalDateTime(zone).date.yearMonth)
    }

    fun editEpisode(day: DayEpisodes, open: (EpisodeId) -> Unit) = viewModelScope.launch {
        chooseEpisode(day, "Edit Episode", destructive = false)?.let { open(it.id) }
    }

    fun deleteEpisode(day: DayEpisodes) = viewModelScope.launch {
        val episode = if (day.episodes.size == 1) {
            day.episodes.single().takeIf { confirmDelete(graph, it) }
        } else {
            chooseEpisode(day, "Delete Episode Log", destructive = true)
        }
        episode?.let { graph.episodes.delete(it.id) }
    }

    /** The only episode of the day, or the one the user picks from an action sheet. */
    private suspend fun chooseEpisode(day: DayEpisodes, title: String, destructive: Boolean): Episode? {
        if (day.episodes.size == 1) return day.episodes.single()
        val zone = graph.timeZone()
        val index = graph.services.dialogs.chooseAction(
            title = title,
            message = "${DateText.weekdayMonthDay(day.date)} has ${day.episodes.size} episodes.",
            actions = day.episodes.map {
                SheetAction("${DateText.time(it.startedAt.toLocalDateTime(zone))} · Intensity ${it.intensity.value}/10", destructive)
            },
        ) ?: return null
        return day.episodes[index]
    }
}
