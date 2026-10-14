package com.migratrack.domain.analytics

import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.PainIntensity
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

/** One cell of the Insights calendar. */
data class CalendarDay(
    val date: LocalDate,
    /** False for the days of the previous month that fill the first week. */
    val inMonth: Boolean,
    val attacks: Int,
    val peak: PainIntensity?,
    val isToday: Boolean,
    val isFuture: Boolean,
    /** False before the tracking start, unless the user logged an episode on that day. */
    val isTracked: Boolean,
) {
    val isMigraineDay: Boolean get() = attacks > 0
}

/** A month grid that starts on Monday, as the Insights calendar draws it. */
data class MonthCalendar(
    val month: YearMonth,
    /** Days of the previous month that fill the first week, then every day of [month]. */
    val days: List<CalendarDay>,
) {
    val attackDays: Int get() = days.count { it.inMonth && it.isMigraineDay }
}

/** The episodes of one day, oldest first, for the preview under the calendar. */
data class DayEpisodes(val date: LocalDate, val episodes: List<Episode>) {
    val peak: PainIntensity? get() = episodes.maxByOrNull { it.intensity.value }?.intensity

    /** Each medication once, in the order the user took them. */
    val medications: List<String>
        get() = episodes.flatMap { it.medications }.sortedBy { it.takenAt }.map { it.displayName }.distinct()
}

object CalendarBuilder {

    fun month(episodes: List<Episode>, month: YearMonth, today: LocalDate, timeZone: TimeZone, trackedSince: LocalDate): MonthCalendar {
        val days = MigraineDays(episodes, timeZone, trackedSince)
        val first = month.firstDay
        val leading = (first.startOfWeek()..first.minus(DatePeriod(days = 1))).days()
        val cells = (leading + (first..month.lastDay).days()).map { date ->
            CalendarDay(
                date = date,
                inMonth = date >= first,
                attacks = days.attacksIn(date..date),
                peak = days.peak(date),
                isToday = date == today,
                isFuture = date > today,
                isTracked = days.isTracked(date),
            )
        }
        return MonthCalendar(month, cells.toList())
    }

    fun day(episodes: List<Episode>, date: LocalDate, timeZone: TimeZone): DayEpisodes =
        DayEpisodes(date, episodes.filter { it.startedAt.toLocalDateTime(timeZone).date == date }.sortedBy { it.startedAt })
}
