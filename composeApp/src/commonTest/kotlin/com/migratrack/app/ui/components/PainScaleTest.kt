package com.migratrack.app.ui.components

import kotlin.test.Test
import kotlin.test.assertEquals

class PainScaleTest {
    @Test
    fun leftToRightPutsLevelOneAtTheLeftEdge() {
        assertEquals(1, levelAt(5f, 1000, rtl = false))
        assertEquals(4, levelAt(350f, 1000, rtl = false))
        assertEquals(10, levelAt(995f, 1000, rtl = false))
    }

    @Test
    fun rightToLeftPutsLevelOneAtTheRightEdge() {
        assertEquals(10, levelAt(5f, 1000, rtl = true))
        assertEquals(7, levelAt(350f, 1000, rtl = true))
        assertEquals(1, levelAt(995f, 1000, rtl = true))
    }
}
