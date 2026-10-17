package com.migratrack.app.ui

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.todayIn
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarIdentifierGregorian
import platform.Foundation.NSLocale

class SystemFormatsTest {
    private val saturday = LocalDate(2026, 10, 24)
    private val morning = LocalDateTime(2026, 10, 16, 9, 5)

    @Test
    fun usEnglishWritesMonthFirstWithA12HourClockAndStartsTheWeekOnSunday() {
        val dates = dates("en_US")

        assertEquals("Oct 24", dates.monthDay(saturday))
        assertEquals("Oct 24, 2026", dates.fullDate(saturday))
        assertEquals("Saturday, Oct 24", dates.weekdayMonthDay(saturday))
        assertEquals("October 2026", dates.monthYear(YearMonth(2026, 10)))
        assertEquals("Oct 2026", dates.shortMonthYear(YearMonth(2026, 10)))
        assertEquals("2026", dates.year(2026))
        assertEquals("9:05 AM", dates.time(morning).plain())
        assertEquals("Oct 16 at 9:05 AM", dates.dayAndTime(morning, saturday).plain())
        assertEquals("Dec 30, 2025 at 9:05 AM", dates.dayAndTime(LocalDateTime(LocalDate(2025, 12, 30), LocalTime(9, 5)), saturday).plain())
        assertEquals("Aug 1 – Oct 24", dates.range(LocalDate(2026, 8, 1), saturday).plain())
        assertEquals("Dec 30, 2025 – Jan 5, 2026", dates.range(LocalDate(2025, 12, 30), LocalDate(2026, 1, 5)).plain())
        assertEquals(DayOfWeek.SUNDAY, dates.firstDayOfWeek)
        assertEquals("Fri", dates.shortWeekday(DayOfWeek.FRIDAY))
        assertEquals("S", dates.narrowWeekday(DayOfWeek.SUNDAY))
        assertEquals("Oct", dates.shortMonth(Month.OCTOBER))
        assertEquals("O", dates.narrowMonth(Month.OCTOBER))
    }

    @Test
    fun britishEnglishWritesTheDayFirstWithA24HourClockAndStartsTheWeekOnMonday() {
        val dates = dates("en_GB")

        assertEquals("24 Oct", dates.monthDay(saturday))
        assertEquals("24 Oct 2026", dates.fullDate(saturday))
        assertEquals("09:05", dates.time(morning))
        assertEquals(DayOfWeek.MONDAY, dates.firstDayOfWeek)
    }

    @Test
    fun todayAndYesterdayGetTheirNames() {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val dates = dates("en_US")

        assertTrue(dates.dayAndTime(LocalDateTime(today, LocalTime(9, 5)), today).plain().startsWith("Today"))
        assertTrue(dates.dayAndTime(LocalDateTime(LocalDate.fromEpochDays(today.toEpochDays() - 1), LocalTime(9, 5)), today).plain().startsWith("Yesterday"))
    }

    @Test
    fun numbersFollowTheRegion() {
        val us = SystemNumberFormats(NSLocale("en_US"))
        assertEquals("1,234", us.integer(1234))
        assertEquals("6.0", us.decimal(6.0))
        assertEquals("25%", us.percent(25))

        val german = SystemNumberFormats(NSLocale("de_DE"))
        assertEquals("1.234", german.integer(1234))
        assertEquals("1000", german.integer(1000, grouping = false))
        assertEquals("6,5", german.decimal(6.5))
        assertEquals("25 %", german.percent(25).plain())
    }

    private fun dates(identifier: String): SystemDateFormats {
        val locale = NSLocale(identifier)
        val calendar = NSCalendar(NSCalendarIdentifierGregorian).also { it.locale = locale }
        return SystemDateFormats(locale, calendar)
    }

    /** The system puts narrow and thin no-break spaces before "AM", around "–" and before "%". */
    private fun String.plain(): String = replace(' ', ' ').replace(' ', ' ').replace(' ', ' ')
}
