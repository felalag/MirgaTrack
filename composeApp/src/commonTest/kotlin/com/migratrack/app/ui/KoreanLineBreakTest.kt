package com.migratrack.app.ui

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class KoreanLineBreakTest {
    private val joiner = "\u2060"

    @Test
    fun aKoreanWordHasNoBreakBetweenItsSyllables() {
        assertEquals("상${joiner}담${joiner}하${joiner}십${joiner}시${joiner}오", "상담하십시오".keepKoreanWords())
    }

    @Test
    fun aSpaceStaysTheOnlyBreakBetweenKoreanWords() {
        assertEquals("의${joiner}사${joiner}와 상${joiner}담", "의사와 상담".keepKoreanWords())
    }

    @Test
    fun digitsAndLatinLettersJoinTheKoreanWordThatTheyAreIn() {
        assertEquals("10${joiner}월", "10월".keepKoreanWords())
        assertEquals("MigraTrack${joiner}은", "MigraTrack은".keepKoreanWords())
    }

    @Test
    fun punctuationKeepsItsBreak() {
        assertEquals("구${joiner}역(메${joiner}스${joiner}꺼${joiner}움)", "구역(메스꺼움)".keepKoreanWords())
    }

    @Test
    fun textWithoutHangulIsUnchanged() {
        val text = "Talk to your doctor 今天"
        assertSame(text, text.keepKoreanWords())
    }
}
