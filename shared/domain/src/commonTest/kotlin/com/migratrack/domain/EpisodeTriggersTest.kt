package com.migratrack.domain

import com.migratrack.domain.analytics.InsightsCalculator
import com.migratrack.domain.analytics.InsightsPeriod
import com.migratrack.domain.analytics.TriggerCount
import com.migratrack.domain.analytics.TriggerFrequency
import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.NotedTrigger
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.Trigger
import com.migratrack.domain.model.TriggerCatalog
import com.migratrack.domain.model.TriggerCategory
import com.migratrack.domain.usecase.EpisodeDraft
import com.migratrack.domain.usecase.GetLoggingPreset
import com.migratrack.domain.usecase.LogEpisode
import com.migratrack.domain.usecase.UpdateEpisode
import com.migratrack.domain.usecase.toDraft
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant

class EpisodeTriggersTest {
    private val sleep = TriggerCatalog.defaults.first { it.id == "poor-sleep" }
    private val light = TriggerCatalog.defaults.first { it.id == "bright-light" }
    private val caffeine = TriggerCatalog.defaults.first { it.id == "caffeine" }
    private val clock = FixedClock(Instant.parse("2026-10-14T12:00:00Z"))

    @Test
    fun loggedEpisodeCopiesTheIdAndNameOfEachTrigger() = runTest {
        val episodes = InMemoryEpisodes()
        val logged = LogEpisode(episodes, clock, newId = { "e1" })(EpisodeDraft(PainIntensity(6), triggers = listOf(sleep, light)))

        assertEquals(listOf(NotedTrigger("poor-sleep", sleep.name), NotedTrigger("bright-light", light.name)), logged.triggers)
        assertEquals(logged, episodes.find(logged.id))
    }

    @Test
    fun editReplacesTheTriggersAndKeepsTheNameTheEpisodeWasLoggedWith() = runTest {
        val episodes = InMemoryEpisodes()
        val custom = Trigger("custom-trigger-1", "Perfume", "Custom trigger", TriggerCategory.Custom, isCustom = true)
        val original = LogEpisode(episodes, clock)(EpisodeDraft(PainIntensity(6), triggers = listOf(sleep, custom)))
        // A later catalog renames one trigger and no longer has the custom one.
        val catalog = TriggerCatalog.defaults.map { if (it.id == sleep.id) it.copy(name = "Poor Sleep") else it }

        val draft = original.toDraft(catalog = emptyList(), triggerCatalog = catalog)
        assertEquals(listOf(sleep.name, "Perfume"), draft.triggers.map { it.name })
        assertEquals(listOf(TriggerCategory.SleepPhysical, TriggerCategory.Custom), draft.triggers.map { it.category })

        val updated = UpdateEpisode(episodes, clock)(original, draft.copy(triggers = draft.triggers.take(1) + caffeine))
        assertEquals(listOf(NotedTrigger(sleep.id, sleep.name), NotedTrigger(caffeine.id, caffeine.name)), updated.triggers)
        assertEquals(updated, episodes.find(original.id))
    }

    @Test
    fun presetLeavesTriggersForTheUserToNote() = runTest {
        val profiles = InMemoryProfiles(BaselineProfile.Initial.copy(triggerIds = setOf("poor-sleep", "caffeine")))
        assertEquals(emptyList(), GetLoggingPreset(profiles, InMemoryCatalog())().triggers)
    }

    @Test
    fun frequencyCountsEpisodesPerTriggerMostOftenFirst() {
        val day = LocalDate(2026, 10, 5)
        val episodes = listOf(
            episodeOn(day, hour = 8).withTriggers(sleep, light),
            episodeOn(day, hour = 20).withTriggers(sleep),
            episodeOn(day.plusDays(1)).withTriggers(caffeine, sleep),
            episodeOn(day.plusDays(2)),
        )

        val frequency = TriggerFrequency.of(episodes)

        assertEquals(4, frequency.episodes)
        assertEquals(1, frequency.withoutTriggers)
        assertEquals(
            listOf(TriggerCount("poor-sleep", sleep.name, 3), TriggerCount("bright-light", light.name, 1), TriggerCount("caffeine", caffeine.name, 1)),
            frequency.counts,
        )
    }

    @Test
    fun frequencyCountsATriggerOncePerEpisodeAndTakesTheNewestName() {
        val older = episodeOn(LocalDate(2026, 10, 1)).copy(triggers = listOf(NotedTrigger("custom-1", "Perfum"), NotedTrigger("custom-1", "Perfum")))
        val newer = episodeOn(LocalDate(2026, 10, 2)).copy(triggers = listOf(NotedTrigger("custom-1", "Perfume")))

        assertEquals(listOf(TriggerCount("custom-1", "Perfume", 2)), TriggerFrequency.of(listOf(newer, older)).counts)
    }

    @Test
    fun insightsCountTriggersOnlyInTheShownRangeUpToToday() {
        val today = LocalDate(2026, 10, 22)
        val episodes = listOf(
            episodeOn(LocalDate(2026, 10, 3)).withTriggers(sleep),
            episodeOn(LocalDate(2026, 10, 20)).withTriggers(sleep, light),
            episodeOn(LocalDate(2026, 9, 28)).withTriggers(caffeine),
        )

        val month = InsightsCalculator.calculate(episodes, InsightsPeriod.Month, today, UTC).triggers
        assertEquals(2, month.episodes)
        assertEquals(listOf("poor-sleep" to 2, "bright-light" to 1), month.counts.map { it.triggerId to it.episodes })

        val week = InsightsCalculator.calculate(episodes, InsightsPeriod.Week, today, UTC).triggers
        assertEquals(1, week.episodes)
    }

    private fun com.migratrack.domain.model.Episode.withTriggers(vararg triggers: Trigger) =
        copy(triggers = triggers.map { NotedTrigger(it.id, it.name) })

    private fun LocalDate.plusDays(days: Int) = LocalDate.fromEpochDays(toEpochDays() + days)
}
