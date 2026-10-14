package com.migratrack.domain

import com.migratrack.domain.analytics.CalendarBuilder
import com.migratrack.domain.analytics.DateText
import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.MedicationCatalog
import com.migratrack.domain.model.TriggerCatalog
import com.migratrack.domain.model.MedicationIntake
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.usecase.EpisodeDraft
import com.migratrack.domain.usecase.LogEpisode
import com.migratrack.domain.usecase.UpdateEpisode
import com.migratrack.domain.usecase.toDraft
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.YearMonth
import kotlinx.datetime.toInstant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class CalendarAndEditingTest {
    private val today = LocalDate(2026, 10, 22)
    private val now = LocalDateTime(2026, 10, 22, 14, 5).toInstant(UTC)
    private val ibuprofen = MedicationCatalog.defaults.first { it.id == "ibuprofen-400" }
    private val sumatriptan = MedicationCatalog.defaults.first { it.id == "sumatriptan-50" }

    @Test
    fun calendarStartsOnMondayWithTheTailOfThePreviousMonth() {
        // Oct 1, 2026 is a Thursday.
        val calendar = CalendarBuilder.month(emptyList(), YearMonth(2026, 10), today, UTC)

        assertEquals(LocalDate(2026, 9, 28), calendar.days.first().date)
        assertEquals(3, calendar.days.count { !it.inMonth })
        assertEquals(31, calendar.days.count { it.inMonth })
        assertTrue(calendar.days.single { it.date == today }.isToday)
        assertEquals(9, calendar.days.count { it.isFuture })
    }

    @Test
    fun calendarCountsAttackDaysOfTheMonthOnly() {
        val episodes = listOf(
            episodeOn(LocalDate(2026, 9, 29), intensity = 4),
            episodeOn(LocalDate(2026, 10, 4), intensity = 6),
            episodeOn(LocalDate(2026, 10, 18), intensity = 3, hour = 8),
            episodeOn(LocalDate(2026, 10, 18), intensity = 8, hour = 19),
        )
        val calendar = CalendarBuilder.month(episodes, YearMonth(2026, 10), today, UTC)

        assertEquals(2, calendar.attackDays)
        val eighteenth = calendar.days.single { it.date == LocalDate(2026, 10, 18) }
        assertEquals(2, eighteenth.attacks)
        assertEquals(PainIntensity(8), eighteenth.peak)
        // A leading day keeps its data, but the month count ignores it.
        assertTrue(calendar.days.first { it.date == LocalDate(2026, 9, 29) }.isMigraineDay)
    }

    @Test
    fun calendarOfAMonthStartingOnMondayHasNoLeadingDays() {
        val calendar = CalendarBuilder.month(emptyList(), YearMonth(2026, 6), today, UTC)
        assertEquals(LocalDate(2026, 6, 1), calendar.days.first().date)
        assertTrue(calendar.days.all { it.inMonth && !it.isFuture })
    }

    @Test
    fun dayPreviewTakesThePeakAndEachMedicationOnce() {
        val day = LocalDate(2026, 10, 18)
        val morning = episodeOn(day, intensity = 5, hour = 8).copy(
            medications = listOf(MedicationIntake(ibuprofen.id, ibuprofen.name, ibuprofen.dose, at(day, 8, 30))),
        )
        val evening = episodeOn(day, intensity = 7, hour = 19).copy(
            medications = listOf(
                MedicationIntake(sumatriptan.id, sumatriptan.name, sumatriptan.dose, at(day, 19, 10)),
                MedicationIntake(ibuprofen.id, ibuprofen.name, ibuprofen.dose, at(day, 19, 20)),
            ),
        )
        val preview = CalendarBuilder.day(listOf(evening, episodeOn(LocalDate(2026, 10, 19)), morning), day, UTC)

        assertEquals(listOf(morning, evening), preview.episodes)
        assertEquals(PainIntensity(7), preview.peak)
        assertEquals(listOf("Ibuprofen 400mg", "Sumatriptan 50mg"), preview.medications)
    }

    @Test
    fun logEpisodeUsesThePickedStartAndClampsTheFuture() = runTest {
        val episodes = InMemoryEpisodes()
        val log = LogEpisode(episodes, FixedClock(now)) { "id-${episodes.state.value.size}" }
        val lastWeek = now - (7 * 24).hours

        val past = log(EpisodeDraft(PainIntensity(6), medications = listOf(ibuprofen), startedAt = lastWeek))
        val future = log(EpisodeDraft(PainIntensity(6), startedAt = now + 2.hours))

        assertEquals(lastWeek, past.startedAt)
        assertEquals(lastWeek, past.medications.single().takenAt)
        assertEquals(now, future.startedAt)
    }

    @Test
    fun updateKeepsIdentityAndMovesKeptMedicationsWithTheStart() = runTest {
        val episodes = InMemoryEpisodes()
        val day = LocalDate(2026, 10, 18)
        val original = episodeOn(day, intensity = 5, hour = 8).copy(
            source = LogSource.QuickLog,
            medications = listOf(MedicationIntake(ibuprofen.id, "Ibuprofen", "400mg", at(day, 8, 40))),
        )
        episodes.add(original)
        val draft = original.toDraft(MedicationCatalog.defaults, TriggerCatalog.defaults).copy(
            intensity = PainIntensity(8),
            region = CranialRegion.RightTemple,
            symptoms = setOf(Symptom.Nausea),
            medications = original.toDraft(MedicationCatalog.defaults, TriggerCatalog.defaults).medications + sumatriptan,
            startedAt = at(day, 6, 0),
        )

        val updated = UpdateEpisode(episodes, FixedClock(now))(original, draft)

        assertEquals(original.id, updated.id)
        assertEquals(LogSource.QuickLog, updated.source)
        assertEquals(at(day, 6, 0), updated.startedAt)
        assertEquals(PainIntensity(8), updated.intensity)
        assertEquals(CranialRegion.RightTemple, updated.region)
        assertEquals(listOf(at(day, 6, 40), at(day, 6, 0)), updated.medications.map { it.takenAt })
        assertEquals(listOf(updated), episodes.state.value)
    }

    @Test
    fun draftOfAnEpisodeKeepsTheLoggedNameAndAddsTheCatalogClass() {
        val episode = episodeOn(LocalDate(2026, 10, 18)).copy(
            medications = listOf(
                MedicationIntake(ibuprofen.id, "Ibuprofen", "200mg", now),
                MedicationIntake("custom-medication-gone", "Eletriptan 40mg", "", now),
            ),
        )
        val draft = episode.toDraft(MedicationCatalog.defaults, TriggerCatalog.defaults)

        assertEquals(episode.startedAt, draft.startedAt)
        assertEquals(listOf("Ibuprofen 200mg", "Eletriptan 40mg"), draft.medications.map { it.displayName })
        assertEquals(listOf(ibuprofen.drugClass, ""), draft.medications.map { it.drugClass })
        assertFalse(draft.medications.first().isCustom)
    }

    @Test
    fun dayAndTimeNamesRecentDaysAndAddsTheYearOnlyWhenItDiffers() {
        assertEquals("Today, 09:05", DateText.dayAndTime(LocalDateTime(2026, 10, 22, 9, 5), today))
        assertEquals("Yesterday, 22:40", DateText.dayAndTime(LocalDateTime(2026, 10, 21, 22, 40), today))
        assertEquals("Oct 16, 17:55", DateText.dayAndTime(LocalDateTime(2026, 10, 16, 17, 55), today))
        assertEquals("Dec 30, 2025, 08:00", DateText.dayAndTime(LocalDateTime(2025, 12, 30, 8, 0), today))
        assertEquals("Thursday, Oct 22", DateText.weekdayMonthDay(today))
        assertEquals("October 2026", DateText.monthYear(YearMonth(2026, 10)))
    }

    private fun at(date: LocalDate, hour: Int, minute: Int) =
        LocalDateTime(date.year, date.month, date.day, hour, 0).toInstant(UTC) + minute.minutes
}
