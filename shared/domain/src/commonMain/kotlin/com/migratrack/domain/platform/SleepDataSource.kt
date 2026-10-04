package com.migratrack.domain.platform

import com.migratrack.domain.model.SleepSummary

/** Reads sleep from the platform health store: HealthKit on iOS, Health Connect on Android later. */
interface SleepDataSource {
    suspend fun access(): SleepAccess

    /** Shows the system permission sheet. Returns false if the store is unavailable. */
    suspend fun requestAccess(): Boolean

    /** Sleep of the night that ended today, or null when the store has no samples. */
    suspend fun lastNight(): SleepSummary?
}

enum class SleepAccess {
    Unavailable,
    NotRequested,

    /**
     * The user saw the permission sheet. Health stores do not reveal whether read access was granted,
     * so a denial looks like a night without data.
     */
    Requested,
}

object NoSleepDataSource : SleepDataSource {
    override suspend fun access(): SleepAccess = SleepAccess.Unavailable
    override suspend fun requestAccess(): Boolean = false
    override suspend fun lastNight(): SleepSummary? = null
}
