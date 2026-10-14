package com.migratrack.domain

import com.migratrack.domain.analytics.InsightsCalculator
import com.migratrack.domain.analytics.InsightsPeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class InsightsCalculatorTest {
    // A Thursday, so the week view has three future days.
    private val today = LocalDate(2026, 10, 22)

    @Test
    fun weekHasSevenDaysFromMondayAndMarksFutureDays() {
        val episodes = listOf(episodeOn(LocalDate(2026, 10, 20), intensity = 6))
        val insights = InsightsCalculator.calculate(episodes, InsightsPeriod.Week, today, UTC, LONG_AGO)

        assertEquals(listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"), insights.bars.map { it.label })
        assertEquals(1, insights.attackCount)
        assertEquals(6, insights.bars[1].peakIntensity?.value)
        assertTrue(insights.bars[3].isCurrent)
        assertEquals(listOf(1, 1, 1, 1, 0, 0, 0), insights.bars.map { it.elapsedDays })
    }

    @Test
    fun monthFoldsDaysAfterTheTwentyEighthIntoTheFourthWeek() {
        val octoberEnd = LocalDate(2026, 10, 31)
        val episodes = listOf(episodeOn(LocalDate(2026, 10, 30)), episodeOn(LocalDate(2026, 10, 3)))
        val insights = InsightsCalculator.calculate(episodes, InsightsPeriod.Month, octoberEnd, UTC, LONG_AGO)

        assertEquals(listOf("W1", "W2", "W3", "W4"), insights.bars.map { it.label })
        assertEquals(10, insights.bars[3].elapsedDays)
        assertEquals(1, insights.bars[3].migraineDays)
        assertEquals(1, insights.bars[0].migraineDays)
        assertEquals(2, insights.attackCount)
    }

    @Test
    fun twoAttacksOnOneDayCountAsTwoAttacksButOneMigraineDay() {
        val day = LocalDate(2026, 10, 5)
        val episodes = listOf(episodeOn(day, hour = 8), episodeOn(day, hour = 20, intensity = 9))
        val insights = InsightsCalculator.calculate(episodes, InsightsPeriod.Month, today, UTC, LONG_AGO)

        assertEquals(2, insights.attackCount)
        assertEquals(1, insights.bars[0].migraineDays)
        assertEquals(9, insights.bars[0].peakIntensity?.value)
    }

    @Test
    fun trendComparesTheLastThirtyDaysWithTheThirtyBefore() {
        // Aug 17 – Sep 15 has 4 attacks, Sep 16 – Oct 15 has 2.
        val earlier = listOf(LocalDate(2026, 8, 20), LocalDate(2026, 8, 25), LocalDate(2026, 9, 1), LocalDate(2026, 9, 10))
        val recent = listOf(LocalDate(2026, 9, 20), LocalDate(2026, 10, 2))
        val insights = InsightsCalculator.calculate((earlier + recent).map { episodeOn(it) }, InsightsPeriod.Month, LocalDate(2026, 10, 15), UTC, LONG_AGO)

        assertEquals(-50, insights.trendPercent)
    }

    @Test
    fun trendIsNullWhenThePreviousPeriodStartsBeforeTheTrackingStart() {
        val earlier = listOf(LocalDate(2026, 9, 5), LocalDate(2026, 9, 10))
        val recent = listOf(LocalDate(2026, 9, 20), LocalDate(2026, 10, 2), LocalDate(2026, 10, 9))
        val insights = InsightsCalculator.calculate(
            (earlier + recent).map { episodeOn(it) }, InsightsPeriod.Month, LocalDate(2026, 10, 15), UTC, trackedSince = LocalDate(2026, 9, 1),
        )

        // Aug 17 – Aug 31 is not tracked, so "+50%" would compare 30 days against 15.
        assertNull(insights.trendPercent)
    }

    @Test
    fun barsBeforeTheTrackingStartAreNotTracked() {
        // Tracking started on Tuesday, Oct 20; today is Thursday, Oct 22.
        val trackedSince = LocalDate(2026, 10, 20)
        val month = InsightsCalculator.calculate(listOf(episodeOn(LocalDate(2026, 10, 21))), InsightsPeriod.Month, today, UTC, trackedSince)

        assertEquals(listOf(7, 7, 7, 1), month.bars.map { it.elapsedDays })
        assertEquals(listOf(0, 0, 2, 1), month.bars.map { it.trackedDays })
        assertEquals(listOf(0, 0, 1, 1), month.bars.map { it.painFreeDays })

        val week = InsightsCalculator.calculate(emptyList(), InsightsPeriod.Week, today, UTC, trackedSince)
        assertEquals(listOf(0, 1, 1, 1, 0, 0, 0), week.bars.map { it.trackedDays })

        val sixMonths = InsightsCalculator.calculate(emptyList(), InsightsPeriod.SixMonths, today, UTC, trackedSince)
        assertEquals(listOf(0, 0, 0, 0, 0, 3), sixMonths.bars.map { it.painFreeDays })
    }

    @Test
    fun trendStaysFlatEarlyInAMonthWhenTheAttackRateIsSteady() {
        // One attack every ten days. On Oct 4 the month holds 1 attack in 4 days, which a calendar comparison inflates.
        val episodes = listOf(
            LocalDate(2026, 8, 15), LocalDate(2026, 8, 25), LocalDate(2026, 9, 4),
            LocalDate(2026, 9, 14), LocalDate(2026, 9, 24), LocalDate(2026, 10, 2),
        ).map { episodeOn(it) }
        val insights = InsightsCalculator.calculate(episodes, InsightsPeriod.Month, LocalDate(2026, 10, 4), UTC, LONG_AGO)

        assertEquals(0, insights.trendPercent)
    }

    @Test
    fun trendIsNullWithoutAttacksInThePreviousPeriod() {
        val insights = InsightsCalculator.calculate(listOf(episodeOn(today)), InsightsPeriod.Year, today, UTC, LONG_AGO)
        assertNull(insights.trendPercent)
    }

    @Test
    fun sixMonthsEndsWithTheCurrentMonth() {
        val insights = InsightsCalculator.calculate(emptyList(), InsightsPeriod.SixMonths, today, UTC, LONG_AGO)
        assertEquals(listOf("May", "Jun", "Jul", "Aug", "Sep", "Oct"), insights.bars.map { it.label })
        assertTrue(insights.bars.last().isCurrent)
    }

    @Test
    fun yearLabelsMonthsWithTheirInitials() {
        val insights = InsightsCalculator.calculate(emptyList(), InsightsPeriod.Year, today, UTC, LONG_AGO)
        assertEquals("JFMAMJJASOND", insights.bars.joinToString("") { it.label })
        assertEquals(0, insights.bars[11].elapsedDays)
    }

    @Test
    fun pastMonthShowsItsOwnWeeksCountAndTrend() {
        // Sep 2026: attacks on Sep 3 and Sep 20; Aug 1–31 has three.
        val episodes = listOf(
            LocalDate(2026, 8, 5), LocalDate(2026, 8, 12), LocalDate(2026, 8, 30),
            LocalDate(2026, 9, 3), LocalDate(2026, 9, 20), LocalDate(2026, 10, 2),
        ).map { episodeOn(it) }
        val insights = InsightsCalculator.calculate(episodes, InsightsPeriod.Month, today, UTC, LONG_AGO, YearMonth(2026, 9))

        assertEquals(YearMonth(2026, 9), insights.month)
        assertEquals(LocalDate(2026, 9, 1)..LocalDate(2026, 9, 30), insights.range)
        assertEquals(2, insights.attackCount)
        // Sep 1–30 against Aug 2–31.
        assertEquals(-33, insights.trendPercent)
        assertEquals(listOf(7, 7, 7, 9), insights.bars.map { it.elapsedDays })
        assertTrue(insights.bars.none { it.isCurrent })
        assertFalse(insights.includesToday)
    }

    @Test
    fun pastMonthAnchorsTheOtherPeriodsAtItsLastDay() {
        val september = YearMonth(2026, 9)
        val week = InsightsCalculator.calculate(emptyList(), InsightsPeriod.Week, today, UTC, LONG_AGO, september)
        val sixMonths = InsightsCalculator.calculate(emptyList(), InsightsPeriod.SixMonths, today, UTC, LONG_AGO, september)
        val lastYear = InsightsCalculator.calculate(emptyList(), InsightsPeriod.Year, today, UTC, LONG_AGO, YearMonth(2025, 3))

        // Sep 30, 2026 is a Wednesday; its week runs into October.
        assertEquals(LocalDate(2026, 9, 28)..LocalDate(2026, 10, 4), week.range)
        assertEquals(listOf("Apr", "May", "Jun", "Jul", "Aug", "Sep"), sixMonths.bars.map { it.label })
        assertEquals(LocalDate(2025, 1, 1)..LocalDate(2025, 12, 31), lastYear.range)
        assertEquals(31, lastYear.bars[11].elapsedDays)
    }

    @Test
    fun futureMonthFallsBackToTheCurrentOne() {
        val insights = InsightsCalculator.calculate(emptyList(), InsightsPeriod.Month, today, UTC, LONG_AGO, YearMonth(2027, 1))
        assertEquals(YearMonth(2026, 10), insights.month)
        assertTrue(insights.includesToday)
    }
}
