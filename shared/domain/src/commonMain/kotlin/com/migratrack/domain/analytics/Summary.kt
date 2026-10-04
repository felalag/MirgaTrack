package com.migratrack.domain.analytics

import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.SleepSummary
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.hours

/** The numbers behind the Summary tab: the two rings, their centre score, and the last event. */
data class SummarySnapshot(
    val today: LocalDate,
    val painFreeDays: Int,
    val windowDays: Int,
    val restScore: Int?,
    val balanceScore: Int,
    val lastEpisode: Episode?,
) {
    val painFreeFraction: Float get() = painFreeDays.toFloat() / windowDays
}

object SummaryCalculator {
    const val WINDOW_DAYS: Int = 30

    fun calculate(
        episodes: List<Episode>,
        sleep: SleepSummary?,
        today: LocalDate,
        timeZone: TimeZone,
    ): SummarySnapshot {
        val window = today.minus(DatePeriod(days = WINDOW_DAYS - 1))..today
        val painFree = WINDOW_DAYS - MigraineDays(episodes, timeZone).migraineDaysIn(window)
        val painFreePercent = painFree * 100 / WINDOW_DAYS
        val rest = sleep?.let(RestScore::of)
        return SummarySnapshot(
            today = today,
            painFreeDays = painFree,
            windowDays = WINDOW_DAYS,
            restScore = rest,
            balanceScore = if (rest == null) painFreePercent else (painFreePercent + rest) / 2,
            lastEpisode = episodes.maxByOrNull { it.startedAt },
        )
    }
}

/**
 * Sleep quality from 0 to 100: 70% for sleeping 8 hours, 30% for a 40% share of deep and REM sleep.
 * These targets are general sleep-hygiene figures, not a clinical score.
 */
object RestScore {
    private val TARGET_SLEEP = 8.hours
    private const val TARGET_RESTORATIVE_SHARE = 0.40

    fun of(sleep: SleepSummary): Int {
        if (sleep.asleep <= kotlin.time.Duration.ZERO) return 0
        val duration = (sleep.asleep / TARGET_SLEEP).coerceAtMost(1.0)
        val restorative = ((sleep.deep + sleep.rem) / sleep.asleep / TARGET_RESTORATIVE_SHARE).coerceAtMost(1.0)
        return ((0.7 * duration + 0.3 * restorative) * 100).roundToInt()
    }
}
