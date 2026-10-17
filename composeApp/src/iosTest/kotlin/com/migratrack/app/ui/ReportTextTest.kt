package com.migratrack.app.ui

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import platform.Foundation.NSLocale

class ReportTextTest {

    @Test
    fun reportTextsComeFromTheResourcesWithTheirPluralForms() = runTest {
        val strings = loadReportStrings(SystemNumberFormats(NSLocale("en_US")))

        assertEquals("Clinical Migraine Report", strings.title)
        assertEquals("MigraTrack · Page 2", strings.page(2))
        assertEquals("Mild 1–3", strings.legendMild(1, 3))
        assertEquals("3 of 11 episodes (27%)", strings.shareOfEpisodes(3, 11, 27))
        assertEquals("1 of 1 episode (100%)", strings.shareOfEpisodes(1, 1, 100))
        assertEquals("1 of 3 episodes has no triggers noted.", strings.withoutTriggers(1, 3))
        assertEquals("2 of 3 episodes have no triggers noted.", strings.withoutTriggers(2, 3))
        assertEquals("1 of 1 episode has no triggers noted.", strings.withoutTriggers(1, 1))
        assertEquals(
            "Tracking started on Oct 20, 2026. 1 earlier day of this range has no records and does not count as a pain-free day.",
            strings.untrackedNote("Oct 20, 2026", 1),
        )
    }

    @Test
    fun countsAboveTheLoadedSamplesKeepTheirPluralForm() = runTest {
        val strings = loadReportStrings(SystemNumberFormats(NSLocale("en_US")))

        assertEquals("1 intake", strings.intakes(1))
        assertEquals("1,201 intakes", strings.intakes(1201))
        assertEquals("1,001 intakes", strings.intakes(1001))
    }
}
