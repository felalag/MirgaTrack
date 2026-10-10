package com.migratrack.domain.analytics

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minus

/** English date labels shared by the UI and the exported reports. */
object DateText {
    private val MONTHS = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    private val MONTH_NAMES = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December",
    )
    private val WEEKDAYS = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    private val WEEKDAY_NAMES = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    fun shortMonth(month: Month): String = MONTHS[month.ordinal]

    fun shortWeekday(day: DayOfWeek): String = WEEKDAYS[day.ordinal]

    /** "Oct 24" */
    fun monthDay(date: LocalDate): String = "${shortMonth(date.month)} ${date.day}"

    /** "Oct 24, 2026" */
    fun fullDate(date: LocalDate): String = "${monthDay(date)}, ${date.year}"

    /** "Friday, Oct 24" */
    fun weekdayMonthDay(date: LocalDate): String = "${WEEKDAY_NAMES[date.dayOfWeek.ordinal]}, ${monthDay(date)}"

    /** "October 2026" */
    fun monthYear(month: YearMonth): String = "${MONTH_NAMES[month.month.ordinal]} ${month.year}"

    /** "Oct 2026" */
    fun shortMonthYear(month: YearMonth): String = "${shortMonth(month.month)} ${month.year}"

    /** "Aug 1 – Oct 24", with years only when the range crosses one. */
    fun range(from: LocalDate, to: LocalDate): String =
        if (from.year == to.year) "${monthDay(from)} – ${monthDay(to)}"
        else "${fullDate(from)} – ${fullDate(to)}"

    /** "09:05" */
    fun time(dateTime: LocalDateTime): String =
        "${dateTime.hour.toString().padStart(2, '0')}:${dateTime.minute.toString().padStart(2, '0')}"

    /** "Today, 09:05", "Yesterday, 22:40", "Oct 16, 09:05", or "Dec 30, 2025, 09:05" in another year. */
    fun dayAndTime(dateTime: LocalDateTime, today: LocalDate): String {
        val date = dateTime.date
        val day = when {
            date == today -> "Today"
            date == today.minus(DatePeriod(days = 1)) -> "Yesterday"
            date.year == today.year -> monthDay(date)
            else -> fullDate(date)
        }
        return "$day, ${time(dateTime)}"
    }

    /** "2026-10-24" */
    fun iso(date: LocalDate): String = date.toString()
}
