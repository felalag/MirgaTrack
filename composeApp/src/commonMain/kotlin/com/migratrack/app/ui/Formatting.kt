package com.migratrack.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.duration_hours_minutes
import com.migratrack.app.generated.resources.duration_minutes
import com.migratrack.export.format.DateFormats
import com.migratrack.export.format.NumberFormats
import kotlin.time.Duration
import org.jetbrains.compose.resources.stringResource

/** Dates in the language and region of the device, for the UI and the report. */
expect val Dates: DateFormats

/** Numbers in the language and region of the device. Every number on the screen goes through it. */
expect val Numbers: NumberFormats

/**
 * The language that the app shows, for its letter case. `Locale.current` follows the language list of the phone,
 * so English text on a Turkish phone would get Turkish case rules.
 */
expect val TextLocale: Locale

/** Whether the app runs right to left, as in Arabic, Hebrew and Urdu. The report uses the same direction. */
expect suspend fun appReadsRightToLeft(): Boolean

/** Upper case by the rules of the app language: Turkish "i" becomes "İ", not "I". */
fun String.uppercaseInAppLanguage(): String = toUpperCase(TextLocale)

/**
 * Korean breaks lines only at spaces, but Compose breaks between any two Hangul syllables, also in "10월" or "MigraTrack은".
 * A word joiner (U+2060) inside each word with Hangul keeps the word on one line, as UIKit does for Korean.
 */
fun String.keepKoreanWords(): String {
    if (none(::isHangul)) return this
    return buildString(length + length / 2) {
        this@keepKoreanWords.forEachIndexed { index, char ->
            val previous = this@keepKoreanWords.getOrNull(index - 1)
            if (previous != null && previous.isLetterOrDigit() && char.isLetterOrDigit() && (isHangul(previous) || isHangul(char))) {
                append('\u2060')
            }
            append(char)
        }
    }
}

private fun isHangul(char: Char): Boolean = char in '\uAC00'..'\uD7A3' || char in '\u3131'..'\u318E'

/** "7 h 20 min", or "45 min" under an hour. */
@Composable
fun formatHoursMinutes(duration: Duration): String {
    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes % 60
    return if (hours == 0L) {
        stringResource(Res.string.duration_minutes, Numbers.integer(minutes.toInt()))
    } else {
        stringResource(Res.string.duration_hours_minutes, Numbers.integer(hours.toInt()), Numbers.integer(minutes.toInt()))
    }
}

/** Appends [template] and lets [placeholder] fill each "%N$s", so a translation can move a link or a styled part. */
fun AnnotatedString.Builder.appendWithPlaceholders(template: String, placeholder: AnnotatedString.Builder.(index: Int) -> Unit) {
    var start = 0
    for (match in PlaceholderPattern.findAll(template)) {
        append(template.substring(start, match.range.first))
        placeholder(match.groupValues[1].toInt())
        start = match.range.last + 1
    }
    append(template.substring(start))
}

/** Fills each "%N$s" of [template] with the Nth of [args], for texts that are loaded before they are needed. */
internal fun fillPlaceholders(template: String, args: Array<out String>): String =
    PlaceholderPattern.replace(template) { args[it.groupValues[1].toInt() - 1] }

private val PlaceholderPattern = Regex("%(\\d+)\\\$s")
