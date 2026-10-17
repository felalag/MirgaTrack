package com.migratrack.export.format

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.YearMonth

/**
 * Dates and times as the region of the device writes them, for the UI and the report.
 * The app uses the system formatters; the tests use the English formats of version 1.0.
 * The examples are US English.
 */
interface DateFormats {
    /** The first day of the week in the region: Sunday in the US, Monday in Germany. */
    val firstDayOfWeek: DayOfWeek

    /** "Oct 24" */
    fun monthDay(date: LocalDate): String

    /** "Oct 24, 2026" */
    fun fullDate(date: LocalDate): String

    /** "Friday, Oct 24" */
    fun weekdayMonthDay(date: LocalDate): String

    /** "October 2026" */
    fun monthYear(month: YearMonth): String

    /** "Oct 2026" */
    fun shortMonthYear(month: YearMonth): String

    /** "2026" */
    fun year(year: Int): String

    /** "Oct", as a label on its own */
    fun shortMonth(month: Month): String

    /** "O", as a label on its own */
    fun narrowMonth(month: Month): String

    /** "Fri", as a label on its own */
    fun shortWeekday(day: DayOfWeek): String

    /** "F", as a label on its own */
    fun narrowWeekday(day: DayOfWeek): String

    /** "Aug 1 – Oct 24", with the years only when the range crosses one. */
    fun range(from: LocalDate, to: LocalDate): String

    /** "9:05 AM", or "09:05" where the region uses a 24-hour clock. */
    fun time(dateTime: LocalDateTime): String

    /** "Today at 9:05 AM", "Oct 16 at 9:05 AM", or with the year when it is not the year of [today]. */
    fun dayAndTime(dateTime: LocalDateTime, today: LocalDate): String
}
