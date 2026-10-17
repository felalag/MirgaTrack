package com.migratrack.app.feature.insights

import kotlin.test.Test
import kotlin.test.assertEquals

class AttackBarTest {
    @Test
    fun bucketWithOnlyMigraineDaysIsAllPink() {
        assertEquals(1f, pinkWeight(1f))
    }

    @Test
    fun smallAndLargeSharesKeepBothColorsVisible() {
        assertEquals(0.14f, pinkWeight(1f / 30))
        assertEquals(0.5f, pinkWeight(0.5f))
        assertEquals(0.86f, pinkWeight(29f / 30))
    }
}
