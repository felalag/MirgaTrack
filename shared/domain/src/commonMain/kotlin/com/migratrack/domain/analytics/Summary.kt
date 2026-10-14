package com.migratrack.domain.analytics

import com.migratrack.domain.model.Episode
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus

/** One day of the Summary strip. A migraine day is always tracked. */
data class SummaryDay(val date: LocalDate, val isTracked: Boolean, val isMigraineDay: Boolean) {
    val isPainFree: Boolean get() = isTracked && !isMigraineDay
}

/** The numbers behind the Summary tab: the pain-free days of the last 30 days, each day of them, and the last event. */
data class SummarySnapshot(
    val today: LocalDate,
    val painFreeDays: Int,
    /** Tracked days of the 30-day window. Fewer than 30 while the user tracks for less than 30 days. */
    val trackedDays: Int,
    /** The 30 days of the window, oldest first; the last one is [today]. */
    val days: List<SummaryDay>,
    val lastEpisode: Episode?,
)

object SummaryCalculator {
    const val WINDOW_DAYS: Int = 30

    /** @param trackedSince the first tracked day; a later date than [today] counts as today. */
    fun calculate(
        episodes: List<Episode>,
        today: LocalDate,
        timeZone: TimeZone,
        trackedSince: LocalDate,
    ): SummarySnapshot {
        val window = today.minus(DatePeriod(days = WINDOW_DAYS - 1))..today
        val days = MigraineDays(episodes, timeZone, minOf(trackedSince, today))
        return SummarySnapshot(
            today = today,
            painFreeDays = days.painFreeDaysIn(window),
            trackedDays = days.trackedDaysIn(window),
            days = window.days().map { SummaryDay(it, days.isTracked(it), days.isMigraineDay(it)) }.toList(),
            lastEpisode = episodes.maxByOrNull { it.startedAt },
        )
    }
}
