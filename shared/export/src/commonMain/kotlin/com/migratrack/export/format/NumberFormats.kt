package com.migratrack.export.format

/**
 * Numbers as the region of the device writes them: its digits, separators and percent sign.
 * The examples are US English.
 */
interface NumberFormats {
    /** "1,234", or "1234" without [grouping] */
    fun integer(value: Int, grouping: Boolean = true): String

    /** "6.5", always with one decimal place */
    fun decimal(value: Double): String

    /** "25%" for 25 */
    fun percent(value: Int): String
}
