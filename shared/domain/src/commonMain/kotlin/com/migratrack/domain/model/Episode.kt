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
    val source: LogSource,
)

/** Where the log came from. Quick logs use the baseline preset and skip the form. */
enum class LogSource { App, QuickLog }

/**
 * A medication taken during an episode.
 * Name and dose are copied from the catalog, so history stays readable after the user edits the catalog.
 */
data class MedicationIntake(
    val medicationId: String,
    val name: String,
    val dose: String,
    val takenAt: Instant,
) {
    val displayName: String get() = medicationDisplayName(name, dose)
}

enum class CranialRegion(val title: String) {
    LeftTemple("Left Temple"),
    RightTemple("Right Temple"),
    FrontalBrow("Frontal / Brow"),
    RetroOrbital("Retro-Orbital"),
    OccipitalNeck("Occipital & Cervical Neck"),
}

enum class Symptom(val title: String, val shortTitle: String) {
    ScintillatingScotoma("Scintillating Scotoma (Visual)", "Visual Aura"),
    NeckStiffness("Neck Stiffness", "Neck Stiffness"),
    Photophobia("Photophobia (Light)", "Light"),
    Nausea("Nausea", "Nausea"),
    Phonophobia("Phonophobia (Sound)", "Sound"),
    Aphasia("Aphasia / Brain Fog", "Brain Fog"),
    Paresthesia("Tingling / Paresthesia", "Tingling"),
}
