package com.migratrack.domain.model

import kotlin.jvm.JvmInline
import kotlin.time.Instant

@JvmInline
value class EpisodeId(val value: String)

/** One logged migraine attack. */
data class Episode(
    val id: EpisodeId,
    val startedAt: Instant,
    val intensity: PainIntensity,
    val region: CranialRegion?,
    val symptoms: Set<Symptom>,
    val medications: List<MedicationIntake>,
    /** Possible triggers the user noted. Quick logs have none until the user edits the episode. */
    val triggers: List<NotedTrigger>,
    val source: LogSource,
)

/** Where the log came from. Quick logs use the baseline preset and skip the form. */
enum class LogSource { App, QuickLog }

/**
 * A medication taken during an episode.
 * Name and dose are copied from the catalog, so history stays readable after the user edits the catalog.
 * The UI shows a built-in medication by its ID instead; the copy is the fallback.
 */
data class MedicationIntake(
    val medicationId: String,
    val name: String,
    val dose: String,
    val takenAt: Instant,
) {
    val displayName: String get() = medicationDisplayName(name, dose)
}

/** A trigger noted for an episode. The name is a copy, like the name of a [MedicationIntake]. */
data class NotedTrigger(val triggerId: String, val name: String)

enum class CranialRegion { LeftTemple, RightTemple, FrontalBrow, RetroOrbital, OccipitalNeck }

enum class Symptom { ScintillatingScotoma, NeckStiffness, Photophobia, Nausea, Phonophobia, Aphasia, Paresthesia }
