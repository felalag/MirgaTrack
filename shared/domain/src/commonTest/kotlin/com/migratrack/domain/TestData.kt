package com.migratrack.domain

import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.ExportPreferences
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.Medication
import com.migratrack.domain.model.MedicationCatalog
import com.migratrack.domain.model.MedicationRole
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.Trigger
import com.migratrack.domain.model.TriggerCatalog
import com.migratrack.domain.model.TriggerCategory
import com.migratrack.domain.model.UserProfile
import com.migratrack.domain.repository.CatalogRepository
import com.migratrack.domain.repository.EpisodeRepository
import com.migratrack.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Clock
import kotlin.time.Instant

val UTC: TimeZone = TimeZone.UTC

fun episodeAt(dateTime: LocalDateTime, intensity: Int = 5, id: String = dateTime.toString()) = Episode(
    id = EpisodeId(id),
    startedAt = dateTime.toInstant(UTC),
    intensity = PainIntensity(intensity),
    region = null,
    symptoms = emptySet(),
    medications = emptyList(),
    source = LogSource.App,
)

fun episodeOn(date: LocalDate, intensity: Int = 5, hour: Int = 9) =
    episodeAt(LocalDateTime(date.year, date.month, date.day, hour, 0), intensity, "$date-$hour")

class FixedClock(var instant: Instant) : Clock {
    override fun now(): Instant = instant
}

class InMemoryEpisodes : EpisodeRepository {
    val state = MutableStateFlow<List<Episode>>(emptyList())
    override fun observeAll(): Flow<List<Episode>> = state.map { list -> list.sortedByDescending { it.startedAt } }
    override suspend fun inRange(from: Instant, until: Instant) =
        state.value.filter { it.startedAt >= from && it.startedAt < until }.sortedBy { it.startedAt }
    override suspend fun find(id: EpisodeId) = state.value.firstOrNull { it.id == id }
    override suspend fun add(episode: Episode) { state.value = state.value.filterNot { it.id == episode.id } + episode }
    override suspend fun delete(id: EpisodeId) { state.value = state.value.filterNot { it.id == id } }
}

class InMemoryProfiles(baseline: BaselineProfile? = null) : ProfileRepository {
    private val baselineState = MutableStateFlow(baseline)
    private val userState = MutableStateFlow<UserProfile?>(null)
    private val exportState = MutableStateFlow(ExportPreferences())
    override fun observeBaseline() = baselineState
    override suspend fun baseline() = baselineState.value
    override suspend fun saveBaseline(profile: BaselineProfile) { baselineState.value = profile }
    override fun observeUser() = userState
    override suspend fun user() = userState.value
    override suspend fun saveUser(profile: UserProfile) { userState.value = profile }
    override fun observeExportPreferences() = exportState
    override suspend fun saveExportPreferences(preferences: ExportPreferences) { exportState.value = preferences }
}

class InMemoryCatalog : CatalogRepository {
    private val triggers = MutableStateFlow(TriggerCatalog.defaults)
    private val medications = MutableStateFlow(MedicationCatalog.defaults)
    override fun observeTriggers() = triggers
    override suspend fun triggers() = triggers.value
    override fun observeMedications() = medications
    override suspend fun medications() = medications.value
    override suspend fun addCustomTrigger(name: String) =
        Trigger("custom-$name", name, "Custom trigger", TriggerCategory.Custom, isCustom = true).also { triggers.value += it }
    override suspend fun addCustomMedication(name: String, dose: String, role: MedicationRole) =
        Medication("custom-$name", name, dose, role, "Custom medication", isCustom = true).also { medications.value += it }
}
