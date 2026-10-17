package com.migratrack.export

import com.migratrack.export.format.DateFormats
import com.migratrack.export.format.NumberFormats
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minus
import kotlin.math.roundToInt

/** The English dates of version 1.0, with a 24-hour clock and Monday first, so the report tests keep their text. */
object EnglishDateFormats : DateFormats {
    private val MONTHS = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    private val MONTH_NAMES = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December",
    )
    private val WEEKDAYS = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    private val WEEKDAY_NAMES = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    override val firstDayOfWeek = DayOfWeek.MONDAY

    override fun monthDay(date: LocalDate) = "${shortMonth(date.month)} ${date.day}"

    override fun fullDate(date: LocalDate) = "${monthDay(date)}, ${date.year}"

    override fun weekdayMonthDay(date: LocalDate) = "${WEEKDAY_NAMES[date.dayOfWeek.ordinal]}, ${monthDay(date)}"

    override fun monthYear(month: YearMonth) = "${MONTH_NAMES[month.month.ordinal]} ${month.year}"

    override fun shortMonthYear(month: YearMonth) = "${shortMonth(month.month)} ${month.year}"

    override fun year(year: Int) = year.toString()

    override fun shortMonth(month: Month) = MONTHS[month.ordinal]

    override fun narrowMonth(month: Month) = MONTHS[month.ordinal].take(1)

    override fun shortWeekday(day: DayOfWeek) = WEEKDAYS[day.ordinal]

    override fun narrowWeekday(day: DayOfWeek) = WEEKDAYS[day.ordinal].take(1)

    override fun range(from: LocalDate, to: LocalDate) =
        if (from.year == to.year) "${monthDay(from)} – ${monthDay(to)}" else "${fullDate(from)} – ${fullDate(to)}"

    override fun time(dateTime: LocalDateTime) =
        "${dateTime.hour.toString().padStart(2, '0')}:${dateTime.minute.toString().padStart(2, '0')}"

    override fun dayAndTime(dateTime: LocalDateTime, today: LocalDate): String {
        val date = dateTime.date
        val day = when {
            date == today -> "Today"
            date == today.minus(DatePeriod(days = 1)) -> "Yesterday"
            date.year == today.year -> monthDay(date)
            else -> fullDate(date)
        }
        return "$day, ${time(dateTime)}"
    }
}

/** English numbers without grouping, as version 1.0 wrote them. */
object EnglishNumberFormats : NumberFormats {
    override fun integer(value: Int, grouping: Boolean) = value.toString()

    override fun decimal(value: Double) = ((value * 10).roundToInt() / 10.0).toString()

    override fun percent(value: Int) = "$value%"
}
