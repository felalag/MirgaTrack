package com.migratrack.app.ui

import com.migratrack.export.format.DateFormats
import com.migratrack.export.format.NumberFormats
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.YearMonth
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.number
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarIdentifierGregorian
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDateFormatterMediumStyle
import platform.Foundation.NSDateFormatterShortStyle
import platform.Foundation.NSDateIntervalFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.NSNumberFormatterPercentStyle
import platform.Foundation.NSTimeZone
import platform.Foundation.currentLocale
import platform.Foundation.systemTimeZone

actual val Dates: DateFormats = SystemDateFormats()

actual val Numbers: NumberFormats = SystemNumberFormats()

/**
 * The system date formats of [locale]. The [calendar] gives the first day of the week.
 * The formatters keep the region of the app start; a region change applies at the next start.
 */
internal class SystemDateFormats(
    private val locale: NSLocale = NSLocale.currentLocale,
    calendar: NSCalendar = NSCalendar.currentCalendar,
    private val timeZone: NSTimeZone = NSTimeZone.systemTimeZone,
) : DateFormats {
    // The grid and the periods of the app are Gregorian, so a region with another calendar still gets Gregorian months.
    private val gregorian = NSCalendar(NSCalendarIdentifierGregorian).also {
        it.locale = locale
        it.timeZone = timeZone
    }

    override val firstDayOfWeek: DayOfWeek = DayOfWeek(calendar.firstWeekday.toInt().let { (it + 5) % 7 + 1 })

    private val monthDay = template("MMMd")
    private val fullDate = template("yMMMd")
    private val weekdayMonthDay = template("EEEEMMMd")
    private val monthYear = template("LLLLy")
    private val shortMonthYear = template("MMMy")
    private val year = template("y")
    private val time = template("jmm")
    private val monthDayTime = template("MMMdjmm")
    private val fullDateTime = template("yMMMdjmm")
    private val relativeDayTime = formatter().also {
        it.dateStyle = NSDateFormatterMediumStyle
        it.timeStyle = NSDateFormatterShortStyle
        it.doesRelativeDateFormatting = true
    }
    private val symbols = formatter()
    private val rangeInYear = interval("MMMd")
    private val rangeAcrossYears = interval("yMMMd")

    override fun monthDay(date: LocalDate): String = monthDay.format(date)

    override fun fullDate(date: LocalDate): String = fullDate.format(date)

    override fun weekdayMonthDay(date: LocalDate): String = weekdayMonthDay.format(date)

    override fun monthYear(month: YearMonth): String = monthYear.format(month.firstDay)

    override fun shortMonthYear(month: YearMonth): String = shortMonthYear.format(month.firstDay)

    override fun year(year: Int): String = this.year.format(LocalDate(year, 1, 1))

    override fun shortMonth(month: Month): String = symbols.shortStandaloneMonthSymbols[month.ordinal] as String

    override fun narrowMonth(month: Month): String = symbols.veryShortStandaloneMonthSymbols[month.ordinal] as String

    override fun shortWeekday(day: DayOfWeek): String = symbols.shortStandaloneWeekdaySymbols[day.symbolIndex] as String

    override fun narrowWeekday(day: DayOfWeek): String = symbols.veryShortStandaloneWeekdaySymbols[day.symbolIndex] as String

    override fun range(from: LocalDate, to: LocalDate): String =
        (if (from.year == to.year) rangeInYear else rangeAcrossYears).stringFromDate(nsDate(from), toDate = nsDate(to))

    override fun time(dateTime: LocalDateTime): String = time.stringFromDate(nsDate(dateTime))

    override fun dayAndTime(dateTime: LocalDateTime, today: LocalDate): String {
        val date = dateTime.date
        val formatter = when {
            date == today || date == today.minus(DatePeriod(days = 1)) -> relativeDayTime
            date.year == today.year -> monthDayTime
            else -> fullDateTime
        }
        return formatter.stringFromDate(nsDate(dateTime))
    }

    private fun formatter() = NSDateFormatter().also {
        it.locale = locale
        it.calendar = gregorian
        it.timeZone = timeZone
    }

    private fun template(template: String) = formatter().also {
        it.setLocalizedDateFormatFromTemplate(template)
    }

    private fun interval(template: String) = NSDateIntervalFormatter().also {
        it.locale = locale
        it.calendar = gregorian
        it.timeZone = timeZone
        it.dateTemplate = template
    }

    private fun NSDateFormatter.format(date: LocalDate): String = stringFromDate(nsDate(date))

    // Noon, because in some time zones midnight does not exist on the day the clocks change.
    private fun nsDate(date: LocalDate): NSDate = nsDate(LocalDateTime(date, LocalTime(12, 0)))

    private fun nsDate(dateTime: LocalDateTime): NSDate {
        val components = NSDateComponents().also {
            it.year = dateTime.year.toLong()
            it.month = dateTime.month.number.toLong()
            it.day = dateTime.day.toLong()
            it.hour = dateTime.hour.toLong()
            it.minute = dateTime.minute.toLong()
        }
        return requireNotNull(gregorian.dateFromComponents(components))
    }

    /** The weekday symbols of Foundation start on Sunday. */
    private val DayOfWeek.symbolIndex: Int get() = isoDayNumber % 7
}

/** The system number formats of [locale]. */
internal class SystemNumberFormats(locale: NSLocale = NSLocale.currentLocale) : NumberFormats {
    private val integer = NSNumberFormatter().also {
        it.locale = locale
        it.numberStyle = NSNumberFormatterDecimalStyle
        it.maximumFractionDigits = 0u
    }
    private val ungrouped = NSNumberFormatter().also {
        it.locale = locale
        it.numberStyle = NSNumberFormatterDecimalStyle
        it.maximumFractionDigits = 0u
        it.usesGroupingSeparator = false
    }
    private val decimal = NSNumberFormatter().also {
        it.locale = locale
        it.numberStyle = NSNumberFormatterDecimalStyle
        it.minimumFractionDigits = 1u
        it.maximumFractionDigits = 1u
    }
    private val percent = NSNumberFormatter().also {
        it.locale = locale
        it.numberStyle = NSNumberFormatterPercentStyle
    }

    override fun integer(value: Int, grouping: Boolean): String =
        requireNotNull((if (grouping) integer else ungrouped).stringFromNumber(NSNumber(int = value)))

    override fun decimal(value: Double): String = requireNotNull(decimal.stringFromNumber(NSNumber(double = value)))

    override fun percent(value: Int): String = requireNotNull(percent.stringFromNumber(NSNumber(double = value / 100.0)))
}
