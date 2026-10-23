package com.migratrack.app.ui

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class LocalizeDigitsTest {
    private val arabic = listOf("٠", "١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩")

    @Test
    fun digitsOfTheTextTakeTheFormOfTheRegion() {
        assertEquals("آخر ٣٠ يومًا", "آخر 30 يومًا".localizeDigits(arabic))
        assertEquals("شدة الألم (١–١٠)", "شدة الألم (1–10)".localizeDigits(arabic))
    }

    @Test
    fun placeholdersKeepTheirDigits() {
        assertEquals("الشدة %1\$s/١٠", "الشدة %1\$s/10".localizeDigits(arabic))
        assertEquals("%2\$s من %1\$s", "%2\$s من %1\$s".localizeDigits(arabic))
    }

    @Test
    fun digitsAfterALatinLetterKeepTheirForm() {
        assertEquals("فيتامين B2", "فيتامين B2".localizeDigits(arabic))
        assertEquals("١٠ mg", "10 mg".localizeDigits(arabic))
    }

    @Test
    fun asciiDigitsLeaveTheTextUnchanged() {
        val text = "Last 30 days"
        assertSame(text, text.localizeDigits(List(10) { it.toString() }))
    }
}
