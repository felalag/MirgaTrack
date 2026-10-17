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

/** The UI and the report name the entries of these enums in the language of the app. */
enum class Diagnosis { MigraineWithoutAura, MigraineWithAura, ChronicMigraine, TensionType, Cluster, NotDiagnosed }

/** Episodes a month: fewer than 1, 1–4, 5–7, 8 or more. */
enum class EpisodeFrequency { LessThanOne, OneToFour, FiveToEight, EightPlus }

/** 2–4 hours, 4–12 hours, 12–24 hours, 24–72 hours. */
enum class TypicalDuration { TwoToFourHours, FourToTwelveHours, TwelveToTwentyFour, OneToThreeDays }

data class UserProfile(
    val name: String,
    /** The day the user first finished onboarding. Pain-free days count from this day on. */
    val memberSince: LocalDate,
)

/** The first tracked day. Without a profile, for example during onboarding, it is [today]. */
fun UserProfile?.trackedSince(today: LocalDate): LocalDate = this?.memberSince ?: today
