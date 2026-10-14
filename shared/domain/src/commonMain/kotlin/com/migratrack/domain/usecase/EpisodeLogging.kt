package com.migratrack.domain.usecase

import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.Medication
import com.migratrack.domain.model.MedicationIntake
import com.migratrack.domain.model.MedicationRole
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.repository.CatalogRepository
import com.migratrack.domain.repository.EpisodeRepository
import com.migratrack.domain.repository.ProfileRepository
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.Uuid

/** The editable state of the Log Episode and Edit Episode forms. */
data class EpisodeDraft(
    val intensity: PainIntensity,
    val region: CranialRegion? = null,
    val symptoms: Set<Symptom> = emptySet(),
    val medications: List<Medication> = emptyList(),
    /** Null means "now": a new episode starts at the moment the user saves it. */
    val startedAt: Instant? = null,
)

/** Saves a new episode. A start in the future becomes now; medications count as taken at the start. */
class LogEpisode(
    private val episodes: EpisodeRepository,
    private val clock: Clock,
    private val newId: () -> String = { Uuid.random().toString() },
) {
    suspend operator fun invoke(draft: EpisodeDraft, source: LogSource = LogSource.App): Episode {
        val now = clock.now()
        val startedAt = minOf(draft.startedAt ?: now, now)
        val episode = Episode(
            id = EpisodeId(newId()),
            startedAt = startedAt,
            intensity = draft.intensity,
            region = draft.region,
            symptoms = draft.symptoms,
            medications = draft.medications.map { MedicationIntake(it.id, it.name, it.dose, startedAt) },
            source = source,
        )
        episodes.add(episode)
        return episode
    }
}

/**
 * Saves the edited form over an episode. The id and the source stay, so the watch copy and the export follow the edit.
 * Kept medications move with the start time; added ones count as taken at the start.
 */
class UpdateEpisode(
    private val episodes: EpisodeRepository,
    private val clock: Clock,
) {
    suspend operator fun invoke(original: Episode, draft: EpisodeDraft): Episode {
        val now = clock.now()
        val startedAt = minOf(draft.startedAt ?: original.startedAt, now)
        val shift = startedAt - original.startedAt
        val kept = original.medications.associateBy { it.medicationId }
        val updated = original.copy(
            startedAt = startedAt,
            intensity = draft.intensity,
            region = draft.region,
            symptoms = draft.symptoms,
            medications = draft.medications.map { medication ->
                kept[medication.id]?.let { it.copy(takenAt = minOf(it.takenAt + shift, now)) }
                    ?: MedicationIntake(medication.id, medication.name, medication.dose, startedAt)
            },
        )
        episodes.add(updated)
        return updated
    }
}

/**
 * The form state for editing this episode. Medications keep the name and dose they were logged with;
 * the catalog adds the role and the drug class when it still has the entry.
 */
fun Episode.toDraft(catalog: List<Medication>): EpisodeDraft {
    val byId = catalog.associateBy { it.id }
    return EpisodeDraft(
        intensity = intensity,
        region = region,
        symptoms = symptoms,
        medications = medications.map { intake ->
            val known = byId[intake.medicationId]
            Medication(
                id = intake.medicationId,
                name = intake.name,
                dose = intake.dose,
                role = known?.role ?: MedicationRole.Acute,
                drugClass = known?.drugClass.orEmpty(),
                isCustom = known?.isCustom ?: false,
            )
        },
        startedAt = startedAt,
    )
}

/** The draft that quick logging saves as is. The phone builds it; the watch receives a copy. */
fun interface PresetProvider {
    suspend fun preset(): EpisodeDraft
}

/**
 * Builds the draft that pre-fills the Log Episode form and that quick logging saves as is:
 * the typical intensity and the acute medications of the treatment blueprint.
 */
class GetLoggingPreset(
    private val profiles: ProfileRepository,
    private val catalog: CatalogRepository,
) : PresetProvider {
    suspend operator fun invoke(): EpisodeDraft = preset()

    override suspend fun preset(): EpisodeDraft {
        val baseline = profiles.baseline() ?: return EpisodeDraft(PainIntensity.Default)
        val acute = catalog.medications().filter {
            it.role == MedicationRole.Acute && it.id in baseline.medicationIds
        }
        return EpisodeDraft(intensity = baseline.typicalIntensity, medications = acute)
    }
}

/** One-tap logging for the iPhone App Shortcut. */
class QuickLogEpisode(
    private val profiles: ProfileRepository,
    private val preset: PresetProvider,
    private val logEpisode: LogEpisode,
) {
    /** @return null before onboarding, when there is no preset to log and the app still shows the welcome screen. */
    suspend operator fun invoke(): Episode? {
        if (profiles.baseline() == null) return null
        return logEpisode(preset.preset(), LogSource.QuickLog)
    }
}
