package com.migratrack.app.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getPluralString as composeGetPluralString
import org.jetbrains.compose.resources.getString as composeGetString
import org.jetbrains.compose.resources.pluralStringResource as composePluralStringResource
import org.jetbrains.compose.resources.stringResource as composeStringResource

// The app reads its strings through these functions, not the ones of Compose: a translation writes "30" or "/10"
// in its text, and only here do these digits follow the region like the numbers that Numbers fills in.

@Composable
fun stringResource(resource: StringResource): String = composeStringResource(resource).localizeDigits()

@Composable
fun stringResource(resource: StringResource, vararg formatArgs: Any): String =
    fillPlaceholders(stringResource(resource), formatArgs.toStrings())

@Composable
fun pluralStringResource(resource: PluralStringResource, quantity: Int): String =
    composePluralStringResource(resource, quantity).localizeDigits()

@Composable
fun pluralStringResource(resource: PluralStringResource, quantity: Int, vararg formatArgs: Any): String =
    fillPlaceholders(pluralStringResource(resource, quantity), formatArgs.toStrings())

suspend fun getString(resource: StringResource): String = composeGetString(resource).localizeDigits()

suspend fun getString(resource: StringResource, vararg formatArgs: Any): String =
    fillPlaceholders(getString(resource), formatArgs.toStrings())

suspend fun getPluralString(resource: PluralStringResource, quantity: Int): String =
    composeGetPluralString(resource, quantity).localizeDigits()

suspend fun getPluralString(resource: PluralStringResource, quantity: Int, vararg formatArgs: Any): String =
    fillPlaceholders(getPluralString(resource, quantity), formatArgs.toStrings())

private fun Array<out Any>.toStrings(): Array<String> = Array(size) { this[it].toString() }

/**
 * Writes the digits of a translated text with [digits], the region's forms of 0 to 9: "آخر 30 يومًا" becomes
 * "آخر ٣٠ يومًا" in Saudi Arabia. Placeholders and digits after a Latin letter, as in "B2", keep their form.
 */
internal fun String.localizeDigits(digits: List<String> = RegionDigits): String {
    if (digits == AsciiDigits) return this
    return DigitPattern.replace(this) { match -> match.value.singleOrNull()?.let { digits[it - '0'] } ?: match.value }
}

private val AsciiDigits = List(10) { it.toString() }

private val RegionDigits: List<String> by lazy { List(10) { Numbers.integer(it, grouping = false) } }

private val DigitPattern = Regex("%\\d+\\\$s|[A-Za-z][0-9]+|[0-9]")
