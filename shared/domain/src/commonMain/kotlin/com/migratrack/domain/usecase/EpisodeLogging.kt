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
import kotlin.uuid.Uuid

/** The editable state of the Log Episode form. */
data class EpisodeDraft(
    val intensity: PainIntensity,
    val region: CranialRegion? = null,
    val symptoms: Set<Symptom> = emptySet(),
    val medications: List<Medication> = emptyList(),
)

class LogEpisode(
    private val episodes: EpisodeRepository,
    private val clock: Clock,
    private val newId: () -> String = { Uuid.random().toString() },
) {
    suspend operator fun invoke(draft: EpisodeDraft, source: LogSource = LogSource.App): Episode {
        val now = clock.now()
        val episode = Episode(
            id = EpisodeId(newId()),
            startedAt = now,
            intensity = draft.intensity,
            region = draft.region,
            symptoms = draft.symptoms,
            medications = draft.medications.map { MedicationIntake(it.id, it.name, it.dose, now) },
            source = source,
        )
        episodes.add(episode)
        return episode
    }
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

/** One-tap logging for the App Shortcut and the watch complication. */
class QuickLogEpisode(
    private val preset: PresetProvider,
    private val logEpisode: LogEpisode,
) {
    suspend operator fun invoke(): Episode = logEpisode(preset.preset(), LogSource.QuickLog)
}
