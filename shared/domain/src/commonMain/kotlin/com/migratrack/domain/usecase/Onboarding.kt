package com.migratrack.domain.usecase

import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.UserProfile
import com.migratrack.domain.repository.ProfileRepository
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

/** Saves the baseline and starts the membership on the first completion. */
class CompleteOnboarding(
    private val profiles: ProfileRepository,
    private val clock: Clock,
    private val timeZone: () -> TimeZone,
) {
    suspend operator fun invoke(baseline: BaselineProfile) {
        profiles.saveBaseline(baseline)
        if (profiles.user() == null) {
            profiles.saveUser(UserProfile(name = "", memberSince = clock.todayIn(timeZone())))
        }
    }
}
