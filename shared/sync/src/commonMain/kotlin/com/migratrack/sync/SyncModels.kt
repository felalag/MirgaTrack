package com.migratrack.sync

import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.Medication
import com.migratrack.domain.model.MedicationIntake
import com.migratrack.domain.model.MedicationRole
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.usecase.EpisodeDraft
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.time.Instant

// Fields only grow, with defaults, so an older app on the other device can still read new payloads.

@Serializable
data class SyncEpisode(
    val id: String,
    val startedAt: Long,
    val intensity: Int,
    val region: String? = null,
    val symptoms: List<String> = emptyList(),
    val medications: List<SyncIntake> = emptyList(),
    val source: String = LogSource.QuickLog.name,
)

@Serializable
data class SyncIntake(val medicationId: String, val name: String, val dose: String, val takenAt: Long)

@Serializable
data class SyncMedication(val id: String, val name: String, val dose: String)

@Serializable
data class SyncPreset(val intensity: Int, val medications: List<SyncMedication> = emptyList())

/**
 * What the phone sends to the watch. The newest one replaces the one before.
 * [receivedIds] lists the newest ids the phone took in from the watch; it confirms an episode the user deleted on the phone.
 */
@Serializable
data class PhoneContext(
    val preset: SyncPreset,
    val episodes: List<SyncEpisode> = emptyList(),
    val receivedIds: List<String> = emptyList(),
)

/** What the watch sends to the phone: new episodes and the ids the user undid. */
@Serializable
data class WatchBatch(val episodes: List<SyncEpisode> = emptyList(), val deletedIds: List<String> = emptyList())

internal val SyncJson = Json { ignoreUnknownKeys = true }

internal fun Episode.toSync() = SyncEpisode(
    id = id.value,
    startedAt = startedAt.toEpochMilliseconds(),
    intensity = intensity.value,
    region = region?.name,
    symptoms = symptoms.map { it.name },
    medications = medications.map { SyncIntake(it.medicationId, it.name, it.dose, it.takenAt.toEpochMilliseconds()) },
    source = source.name,
)

/** Null when the payload holds an intensity outside 1–10. */
internal fun SyncEpisode.toDomain(): Episode? {
    if (intensity !in PainIntensity.RANGE) return null
    return Episode(
        id = EpisodeId(id),
        startedAt = Instant.fromEpochMilliseconds(startedAt),
        intensity = PainIntensity(intensity),
        region = CranialRegion.entries.firstOrNull { it.name == region },
        symptoms = symptoms.mapNotNull { name -> Symptom.entries.firstOrNull { it.name == name } }.toSet(),
        medications = medications.map { MedicationIntake(it.medicationId, it.name, it.dose, Instant.fromEpochMilliseconds(it.takenAt)) },
        source = LogSource.entries.firstOrNull { it.name == source } ?: LogSource.QuickLog,
    )
}

internal fun EpisodeDraft.toSync() = SyncPreset(
    intensity = intensity.value,
    medications = medications.map { SyncMedication(it.id, it.name, it.dose) },
)

internal fun SyncPreset.toDraft() = EpisodeDraft(
    intensity = PainIntensity(intensity.coerceIn(PainIntensity.RANGE)),
    medications = medications.map { Medication(it.id, it.name, it.dose, MedicationRole.Acute, drugClass = "") },
)
