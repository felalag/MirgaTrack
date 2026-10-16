package com.migratrack.domain.model

import kotlinx.datetime.LocalDate

/**
 * The headache profile from onboarding. Its typical intensity and acute medications
 * become the preset for quick logging.
 */
data class BaselineProfile(
    val diagnoses: Set<Diagnosis>,
    val frequency: EpisodeFrequency,
    val typicalDuration: TypicalDuration,
    val typicalIntensity: PainIntensity,
    val triggerIds: Set<String>,
    val medicationIds: Set<String>,
) {
    companion object {
        val Initial = BaselineProfile(
            diagnoses = emptySet(),
            frequency = EpisodeFrequency.OneToFour,
            typicalDuration = TypicalDuration.TwelveToTwentyFour,
            typicalIntensity = PainIntensity.Default,
            triggerIds = emptySet(),
            medicationIds = emptySet(),
        )
    }
}

enum class Diagnosis(val title: String, val subtitle: String) {
    MigraineWithoutAura("Migraine without aura", "Common migraine pattern"),
    MigraineWithAura("Migraine with aura", "Visual or sensory focal aura"),
    ChronicMigraine("Chronic Migraine", "15 or more headache days a month"),
    TensionType("Tension-type Headache", "Constricting, band-like tension"),
    Cluster("Cluster Headache", "Severe unilateral orbital episodes"),
    NotDiagnosed("Not diagnosed / Exploring", "Observing symptom patterns"),
}

enum class EpisodeFrequency(val title: String) {
    LessThanOne("< 1 / mo"),
    OneToFour("1–4 / mo"),
    FiveToEight("5–7 / mo"),
    EightPlus("8+ / mo"),
}

enum class TypicalDuration(val title: String) {
    TwoToFourHours("2–4h"),
    FourToTwelveHours("4–12h"),
    TwelveToTwentyFour("12–24h"),
    OneToThreeDays("24–72h"),
}

data class UserProfile(
    val name: String,
    /** The day the user first finished onboarding. Pain-free days count from this day on. */
    val memberSince: LocalDate,
)

/** The first tracked day. Without a profile, for example during onboarding, it is [today]. */
fun UserProfile?.trackedSince(today: LocalDate): LocalDate = this?.memberSince ?: today
