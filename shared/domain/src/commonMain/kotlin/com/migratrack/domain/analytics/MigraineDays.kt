package com.migratrack.domain.analytics

import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.PainIntensity
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

/**
 * Peak pain per calendar day. A day with at least one episode is a migraine day.
 * Days from [trackedSince] on are tracked. Before it, only a migraine day is tracked:
 * a day without an episode there is unknown, not pain-free.
 */
class MigraineDays(episodes: List<Episode>, timeZone: TimeZone, private val trackedSince: LocalDate) {
    private val peaks: Map<LocalDate, PainIntensity> = episodes
        .groupBy { it.startedAt.toLocalDateTime(timeZone).date }
        .mapValues { (_, dayEpisodes) -> dayEpisodes.maxBy { it.intensity.value }.intensity }

    private val attacksPerDay: Map<LocalDate, Int> = episodes
        .groupingBy { it.startedAt.toLocalDateTime(timeZone).date }
        .eachCount()

    fun isMigraineDay(date: LocalDate): Boolean = date in peaks

    fun peak(date: LocalDate): PainIntensity? = peaks[date]

    fun isTracked(date: LocalDate): Boolean = date >= trackedSince || isMigraineDay(date)

    fun migraineDaysIn(range: ClosedRange<LocalDate>): Int = range.days().count(::isMigraineDay)

    fun trackedDaysIn(range: ClosedRange<LocalDate>): Int = range.days().count(::isTracked)

    fun painFreeDaysIn(range: ClosedRange<LocalDate>): Int = trackedDaysIn(range) - migraineDaysIn(range)

    fun attacksIn(range: ClosedRange<LocalDate>): Int = range.days().sumOf { attacksPerDay[it] ?: 0 }

    fun peakIn(range: ClosedRange<LocalDate>): PainIntensity? =
        range.days().mapNotNull(peaks::get).maxByOrNull { it.value }
}

internal fun ClosedRange<LocalDate>.days(): Sequence<LocalDate> =
    if (start > endInclusive) emptySequence()
    else generateSequence(start) { it.plus(DatePeriod(days = 1)) }.takeWhile { it <= endInclusive }

internal fun ClosedRange<LocalDate>.length(): Int =
    if (start > endInclusive) 0 else start.daysUntil(endInclusive) + 1
