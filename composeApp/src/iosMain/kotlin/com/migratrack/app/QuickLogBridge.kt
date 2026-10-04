package com.migratrack.app

import kotlin.coroutines.cancellation.CancellationException

/**
 * Swift entry point for logging without the UI. App Intents use it now;
 * the watch complication and Control Center control will call the same use case.
 */
object QuickLogBridge {
    /** Logs an episode from the baseline preset and returns a short summary for Siri to speak. */
    @Throws(IllegalStateException::class, CancellationException::class)
    suspend fun logEpisode(): String {
        val episode = appGraph.quickLog()
        val medications = episode.medications.joinToString(", ") { it.displayName }
        return buildString {
            append("intensity ${episode.intensity.value} of 10")
            if (medications.isNotEmpty()) append(", with $medications")
        }
    }
}
