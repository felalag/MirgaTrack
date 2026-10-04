package com.migratrack.domain.model

import kotlin.time.Duration

/** Last night's sleep as the platform health store reports it. */
data class SleepSummary(
    val deep: Duration,
    val core: Duration,
    val rem: Duration,
) {
    val asleep: Duration get() = deep + core + rem
}
