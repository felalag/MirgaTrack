package com.migratrack.domain.analytics

import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.PainIntensity
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.yearMonth
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
    /** The month the user browses; the chart shows the period that ends in it. */
    val month: YearMonth,
    /** The days the chart covers. Days after today count as not yet elapsed. */
    val range: ClosedRange<LocalDate>,
    val attackCount: Int,
    /**
     * Change in attacks over 7, 30, 182 or 365 days up to the end of the range (today at most)
     * against the same span before it. Negative means fewer attacks; null when the earlier span has none.
     */
    val trendPercent: Int?,
    val bars: List<InsightsBar>,
) {
    val includesToday: Boolean get() = today in range
}

object InsightsCalculator {

    /**
     * @param month the browsed month; a later month than today's is clamped to today's.
     *   For a past month the week view shows the week of its last day.
     */
    fun calculate(
        episodes: List<Episode>,
        period: InsightsPeriod,
        today: LocalDate,
        timeZone: TimeZone,
        month: YearMonth = today.yearMonth,
    ): Insights {
        val shown = minOf(month, today.yearMonth)
        val anchor = if (shown == today.yearMonth) today else shown.lastDay
        val range = period.range(anchor)
        val end = minOf(range.endInclusive, today)
        val days = MigraineDays(episodes, timeZone)
        val attackCount = days.attacksIn(range.start..end)

        // Rolling spans, not calendar periods: early in a month a calendar comparison sets
        // a few days against a whole month and swings by hundreds of percent.
        val span = period.trendSpanDays
        val recent = days.attacksIn(end.minus(DatePeriod(days = span - 1))..end)
        val earlier = days.attacksIn(end.minus(DatePeriod(days = 2 * span - 1))..end.minus(DatePeriod(days = span)))
        val trend = if (earlier == 0) null else ((recent - earlier) * 100.0 / earlier).roundToInt()

        val bars = period.buckets(anchor).map { (label, bucket) ->
            val elapsedPart = bucket.start..minOf(bucket.endInclusive, today)
            InsightsBar(
                label = label,
                elapsedDays = elapsedPart.length(),
                migraineDays = days.migraineDaysIn(elapsedPart),
                peakIntensity = days.peakIn(elapsedPart),
                isCurrent = today in bucket,
            )
        }
        return Insights(period, today, shown, range, attackCount, trend, bars)
    }
}

private fun InsightsPeriod.range(anchor: LocalDate): ClosedRange<LocalDate> = when (this) {
    InsightsPeriod.Week -> anchor.startOfWeek().let { it..it.plus(DatePeriod(days = 6)) }
    InsightsPeriod.Month -> anchor.startOfMonth().let { it..it.endOfMonth() }
    InsightsPeriod.SixMonths -> anchor.startOfMonth().minus(DatePeriod(months = 5))..anchor.startOfMonth().endOfMonth()
    InsightsPeriod.Year -> LocalDate(anchor.year, Month.JANUARY, 1)..LocalDate(anchor.year, Month.DECEMBER, 31)
}

private val InsightsPeriod.trendSpanDays: Int
    get() = when (this) {
        InsightsPeriod.Week -> 7
        InsightsPeriod.Month -> 30
        InsightsPeriod.SixMonths -> 182
        InsightsPeriod.Year -> 365
    }

private fun InsightsPeriod.buckets(anchor: LocalDate): List<Pair<String, ClosedRange<LocalDate>>> = when (this) {
    InsightsPeriod.Week -> {
        val monday = anchor.startOfWeek()
        DayOfWeek.entries.mapIndexed { index, day ->
            val date = monday.plus(DatePeriod(days = index))
            DateText.shortWeekday(day) to date..date
        }
    }
    InsightsPeriod.Month -> {
        // The last bucket absorbs days 29–31, so the chart always has the four bars of the design.
        val first = anchor.startOfMonth()
        val last = first.endOfMonth()
        (0 until 4).map { week ->
            val start = first.plus(DatePeriod(days = week * 7))
            val end = if (week == 3) last else start.plus(DatePeriod(days = 6))
            "W${week + 1}" to start..end
        }
    }
    InsightsPeriod.SixMonths -> (5 downTo 0).map { back ->
        val start = anchor.startOfMonth().minus(DatePeriod(months = back))
        DateText.shortMonth(start.month) to start..start.endOfMonth()
    }
    InsightsPeriod.Year -> Month.entries.map { month ->
        val start = LocalDate(anchor.year, month, 1)
        DateText.shortMonth(month).take(1) to start..start.endOfMonth()
    }
}

internal fun LocalDate.startOfWeek(): LocalDate =
    minus(DatePeriod(days = dayOfWeek.isoDayNumber - DayOfWeek.MONDAY.isoDayNumber))

internal fun LocalDate.startOfMonth(): LocalDate = LocalDate(year, month.number, 1)

internal fun LocalDate.endOfMonth(): LocalDate =
    startOfMonth().plus(DatePeriod(months = 1)).minus(DatePeriod(days = 1))
