package com.migratrack.export.format

import kotlin.test.Test
import kotlin.test.assertEquals

class ListSeparatorTest {

    @Test
    fun arabicScriptLanguagesUseTheArabicComma() {
        assertEquals("، ", listSeparatorFor("ar"))
        assertEquals("، ", listSeparatorFor("ur"))
    }

    @Test
    fun chineseAndJapaneseUseTheIdeographicComma() {
        assertEquals("、", listSeparatorFor("ja"))
        assertEquals("、", listSeparatorFor("zh"))
        assertEquals("、", listSeparatorFor("zh-Hant"))
        assertEquals("、", listSeparatorFor("zh_Hans"))
    }

    @Test
    fun otherLanguagesUseTheLatinComma() {
        listOf("en", "ko", "he", "hi", "th", "ru", "el").forEach { assertEquals(", ", listSeparatorFor(it), it) }
    }
}
