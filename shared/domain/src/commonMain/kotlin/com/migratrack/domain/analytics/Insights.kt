package com.migratrack.domain.analytics

import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.PainIntensity
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlin.math.roundToInt

enum class InsightsPeriod { Week, Month, SixMonths, Year }

/** One bar of the Insights chart: a day for the week view, a week or a month otherwise. */
data class InsightsBar(
    val label: String,
    /** Days of this bucket up to today. Zero means the bucket is in the future. */
    val elapsedDays: Int,
    val migraineDays: Int,
    val peakIntensity: PainIntensity?,
    val isCurrent: Boolean,
) {
    val painFreeDays: Int get() = elapsedDays - migraineDays
}

data class Insights(
    val period: InsightsPeriod,
    val today: LocalDate,
    val attackCount: Int,
    /**
     * Change in attacks over the last 7, 30, 182 or 365 days against the same span before it.
     * Negative means fewer attacks; null when the earlier span has none.
     */
    val trendPercent: Int?,
    val bars: List<InsightsBar>,
)

object InsightsCalculator {

    fun calculate(
        episodes: List<Episode>,
        period: InsightsPeriod,
        today: LocalDate,
        timeZone: TimeZone,
    ): Insights {
        val days = MigraineDays(episodes, timeZone)
        val attackCount = days.attacksIn(period.range(today).start..today)

        // Rolling spans, not calendar periods: early in a month a calendar comparison sets
        // a few days against a whole month and swings by hundreds of percent.
        val span = period.trendSpanDays
        val recent = days.attacksIn(today.minus(DatePeriod(days = span - 1))..today)
        val earlier = days.attacksIn(today.minus(DatePeriod(days = 2 * span - 1))..today.minus(DatePeriod(days = span)))
        val trend = if (earlier == 0) null else ((recent - earlier) * 100.0 / earlier).roundToInt()

        val bars = period.buckets(today).map { (label, bucket) ->
            val elapsedPart = bucket.start..minOf(bucket.endInclusive, today)
            InsightsBar(
                label = label,
                elapsedDays = elapsedPart.length(),
                migraineDays = days.migraineDaysIn(elapsedPart),
                peakIntensity = days.peakIn(elapsedPart),
                isCurrent = today in bucket,
            )
        }
        return Insights(period, today, attackCount, trend, bars)
    }
}

private fun InsightsPeriod.range(today: LocalDate): ClosedRange<LocalDate> = when (this) {
    InsightsPeriod.Week -> today.startOfWeek().let { it..it.plus(DatePeriod(days = 6)) }
    InsightsPeriod.Month -> today.startOfMonth().let { it..it.endOfMonth() }
    InsightsPeriod.SixMonths -> today.startOfMonth().minus(DatePeriod(months = 5))..today.startOfMonth().endOfMonth()
    InsightsPeriod.Year -> LocalDate(today.year, Month.JANUARY, 1)..LocalDate(today.year, Month.DECEMBER, 31)
}

private val InsightsPeriod.trendSpanDays: Int
    get() = when (this) {
        InsightsPeriod.Week -> 7
        InsightsPeriod.Month -> 30
        InsightsPeriod.SixMonths -> 182
        InsightsPeriod.Year -> 365
    }

private fun InsightsPeriod.buckets(today: LocalDate): List<Pair<String, ClosedRange<LocalDate>>> = when (this) {
    InsightsPeriod.Week -> {
        val monday = today.startOfWeek()
        DayOfWeek.entries.mapIndexed { index, day ->
            val date = monday.plus(DatePeriod(days = index))
            DateText.shortWeekday(day) to date..date
        }
    }
    InsightsPeriod.Month -> {
        // The last bucket absorbs days 29–31, so the chart always has the four bars of the design.
        val first = today.startOfMonth()
        val last = first.endOfMonth()
        (0 until 4).map { week ->
            val start = first.plus(DatePeriod(days = week * 7))
            val end = if (week == 3) last else start.plus(DatePeriod(days = 6))
            "W${week + 1}" to start..end
        }
    }
    InsightsPeriod.SixMonths -> (5 downTo 0).map { back ->
        val start = today.startOfMonth().minus(DatePeriod(months = back))
        DateText.shortMonth(start.month) to start..start.endOfMonth()
    }
    InsightsPeriod.Year -> Month.entries.map { month ->
        val start = LocalDate(today.year, month, 1)
        DateText.shortMonth(month).take(1) to start..start.endOfMonth()
    }
}

internal fun LocalDate.startOfWeek(): LocalDate =
    minus(DatePeriod(days = dayOfWeek.isoDayNumber - DayOfWeek.MONDAY.isoDayNumber))

internal fun LocalDate.startOfMonth(): LocalDate = LocalDate(year, month.number, 1)

internal fun LocalDate.endOfMonth(): LocalDate =
    startOfMonth().plus(DatePeriod(months = 1)).minus(DatePeriod(days = 1))
