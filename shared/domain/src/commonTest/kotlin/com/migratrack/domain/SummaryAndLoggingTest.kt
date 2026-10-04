package com.migratrack.domain

import com.migratrack.domain.analytics.RestScore
import com.migratrack.domain.analytics.SummaryCalculator
import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.SleepSummary
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
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

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
        val summary = SummaryCalculator.calculate(episodes, sleep = null, today = today, timeZone = UTC)

        assertEquals(28, summary.painFreeDays)
        assertEquals(93, summary.balanceScore)
        assertEquals(today.toString() + "-18", summary.lastEpisode?.id?.value)
    }

    @Test
    fun balanceAveragesPainFreeShareAndRestWhenSleepIsKnown() {
        val sleep = SleepSummary(deep = 110.minutes, core = 252.minutes, rem = 103.minutes)
        val summary = SummaryCalculator.calculate(emptyList(), sleep, today, UTC)

        assertEquals(RestScore.of(sleep), summary.restScore)
        assertEquals((100 + RestScore.of(sleep)) / 2, summary.balanceScore)
    }

    @Test
    fun restScoreRewardsDurationAndRestorativeSleep() {
        assertEquals(100, RestScore.of(SleepSummary(deep = 2.hours, core = 4.hours, rem = 2.hours)))
        assertEquals(35, RestScore.of(SleepSummary(deep = 0.hours, core = 4.hours, rem = 0.hours)))
    }

    @Test
    fun quickLogUsesTypicalIntensityAndAcuteBlueprintMedications() = runTest {
        val baseline = BaselineProfile.Initial.copy(
            typicalIntensity = PainIntensity(6),
            medicationIds = setOf("ibuprofen-400", "riboflavin-400"),
        )
        val episodes = InMemoryEpisodes()
        val clock = FixedClock(LocalDateTime(2026, 10, 24, 14, 5).toInstant(UTC))
        val quickLog = QuickLogEpisode(
            GetLoggingPreset(InMemoryProfiles(baseline), InMemoryCatalog()),
            LogEpisode(episodes, clock) { "fixed-id" },
        )

        val episode = quickLog()

        assertEquals(PainIntensity(6), episode.intensity)
        assertEquals(listOf("ibuprofen-400"), episode.medications.map { it.medicationId })
        assertEquals(LogSource.QuickLog, episode.source)
        assertEquals(clock.instant, episode.medications.single().takenAt)
        assertEquals(listOf(episode), episodes.state.value)
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
}
