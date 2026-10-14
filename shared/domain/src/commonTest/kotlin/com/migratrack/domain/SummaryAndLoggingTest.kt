package com.migratrack.domain

import com.migratrack.domain.analytics.SummaryCalculator
import com.migratrack.domain.analytics.SummaryDay
import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.usecase.CompleteOnboarding
import com.migratrack.domain.usecase.GetLoggingPreset
import com.migratrack.domain.usecase.LogEpisode
import com.migratrack.domain.usecase.QuickLogEpisode
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SummaryAndLoggingTest {
    private val today = LocalDate(2026, 10, 24)

    @Test
    fun painFreeDaysCountOnlyTheLastThirtyDays() {
        val episodes = listOf(
            episodeOn(today),
            episodeOn(today, hour = 18),
            episodeOn(LocalDate(2026, 9, 25)), // first day of the window
            episodeOn(LocalDate(2026, 9, 24)), // one day before the window
        )
        val summary = SummaryCalculator.calculate(episodes, today = today, timeZone = UTC, trackedSince = LONG_AGO)

        assertEquals(28, summary.painFreeDays)
        assertEquals(today.toString() + "-18", summary.lastEpisode?.id?.value)
    }

    @Test
    fun painFreeDaysCountOnlyFromTheTrackingStart() {
        // Tracking started on Oct 20: five days up to today, one of them with an episode.
        val episodes = listOf(episodeOn(LocalDate(2026, 10, 22)), episodeOn(LocalDate(2026, 9, 26)))
        val summary = SummaryCalculator.calculate(episodes, today, UTC, trackedSince = LocalDate(2026, 10, 20))

        // The episode on Sep 26 makes that day tracked, but the days around it stay unknown.
        assertEquals(6, summary.trackedDays)
        assertEquals(4, summary.painFreeDays)
    }

    @Test
    fun firstDayOfTrackingIsTheOnlyTrackedDay() {
        val summary = SummaryCalculator.calculate(emptyList(), today, UTC, trackedSince = today)

        assertEquals(1, summary.trackedDays)
        assertEquals(1, summary.painFreeDays)
    }

    @Test
    fun trackingStartAfterTodayCountsAsToday() {
        val summary = SummaryCalculator.calculate(emptyList(), today, UTC, trackedSince = LocalDate(2026, 10, 26))

        assertEquals(1, summary.trackedDays)
        assertEquals(1, summary.painFreeDays)
    }

    @Test
    fun stripDaysRunFromTheOldestDayToToday() {
        val summary = SummaryCalculator.calculate(emptyList(), today, UTC, LONG_AGO)

        assertEquals(SummaryCalculator.WINDOW_DAYS, summary.days.size)
        assertEquals(LocalDate(2026, 9, 25), summary.days.first().date)
        assertEquals(today, summary.days.last().date)
        assertEquals(summary.days.sortedBy { it.date }, summary.days)
        assertTrue(summary.days.all { it.isPainFree })
    }

    @Test
    fun stripDaysBeforeTheTrackingStartAreNotTracked() {
        val trackedSince = LocalDate(2026, 10, 20)
        val episodes = listOf(episodeOn(LocalDate(2026, 10, 1)), episodeOn(trackedSince))
        val days = SummaryCalculator.calculate(episodes, today, UTC, trackedSince).days.associateBy { it.date }

        assertEquals(SummaryDay(LocalDate(2026, 10, 19), isTracked = false, isMigraineDay = false), days[LocalDate(2026, 10, 19)])
        assertEquals(SummaryDay(trackedSince, isTracked = true, isMigraineDay = true), days[trackedSince])
        assertEquals(SummaryDay(LocalDate(2026, 10, 21), isTracked = true, isMigraineDay = false), days[LocalDate(2026, 10, 21)])
        // An episode before the tracking start makes its day tracked, but not the days around it.
        assertEquals(SummaryDay(LocalDate(2026, 10, 1), isTracked = true, isMigraineDay = true), days[LocalDate(2026, 10, 1)])
        assertEquals(6, days.values.count { it.isTracked })
        assertEquals(summaryOf(episodes, trackedSince).painFreeDays, days.values.count { it.isPainFree })
    }

    @Test
    fun aDayWithTwoEpisodesIsOneMigraineDay() {
        val episodes = listOf(episodeOn(today, hour = 8), episodeOn(today, hour = 20))
        val summary = summaryOf(episodes, LONG_AGO)

        assertEquals(1, summary.days.count { it.isMigraineDay })
        assertTrue(summary.days.last().isMigraineDay)
        assertEquals(29, summary.painFreeDays)
    }

    @Test
    fun quickLogUsesTypicalIntensityAndAcuteBlueprintMedications() = runTest {
        val baseline = BaselineProfile.Initial.copy(
            typicalIntensity = PainIntensity(6),
            medicationIds = setOf("ibuprofen-400", "riboflavin-400"),
        )
        val episodes = InMemoryEpisodes()
        val clock = FixedClock(LocalDateTime(2026, 10, 24, 14, 5).toInstant(UTC))
        val profiles = InMemoryProfiles(baseline)
        val quickLog = QuickLogEpisode(
            profiles,
            GetLoggingPreset(profiles, InMemoryCatalog()),
            LogEpisode(episodes, clock) { "fixed-id" },
        )

        val episode = assertNotNull(quickLog())

        assertEquals(PainIntensity(6), episode.intensity)
        assertEquals(listOf("ibuprofen-400"), episode.medications.map { it.medicationId })
        assertEquals(LogSource.QuickLog, episode.source)
        assertEquals(clock.instant, episode.medications.single().takenAt)
        assertEquals(listOf(episode), episodes.state.value)
    }

    @Test
    fun quickLogSavesNothingBeforeOnboarding() = runTest {
        val episodes = InMemoryEpisodes()
        val profiles = InMemoryProfiles()
        val quickLog = QuickLogEpisode(
            profiles,
            GetLoggingPreset(profiles, InMemoryCatalog()),
            LogEpisode(episodes, FixedClock(LocalDateTime(2026, 10, 24, 14, 5).toInstant(UTC))),
        )

        assertNull(quickLog())
        assertEquals(emptyList(), episodes.state.value)
    }

    @Test
    fun presetFallsBackToDefaultsBeforeOnboarding() = runTest {
        val preset = GetLoggingPreset(InMemoryProfiles(), InMemoryCatalog())()
        assertEquals(PainIntensity.Default, preset.intensity)
        assertEquals(emptyList(), preset.medications)
    }

    @Test
    fun onboardingKeepsTheFirstMembershipDate() = runTest {
        val profiles = InMemoryProfiles()
        val clock = FixedClock(LocalDateTime(2026, 1, 3, 9, 0).toInstant(UTC))
        val complete = CompleteOnboarding(profiles, clock) { UTC }

        complete(BaselineProfile.Initial)
        clock.instant = LocalDateTime(2026, 10, 24, 9, 0).toInstant(UTC)
        complete(BaselineProfile.Initial.copy(typicalIntensity = PainIntensity(3)))

        assertEquals(LocalDate(2026, 1, 3), assertNotNull(profiles.user()).memberSince)
        assertEquals(PainIntensity(3), profiles.baseline()?.typicalIntensity)
    }

    private fun summaryOf(episodes: List<Episode>, trackedSince: LocalDate) =
        SummaryCalculator.calculate(episodes, today, UTC, trackedSince)
}
