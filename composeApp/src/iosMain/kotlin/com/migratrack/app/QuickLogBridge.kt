package com.migratrack.app

import kotlin.coroutines.cancellation.CancellationException

/**
 * Swift entry point for logging without the UI. App Intents use it now;
 * the watch complication and Control Center control will call the same use case.
 */
object QuickLogBridge {
    /** Logs an episode from the baseline preset and returns what Siri reports, or null before onboarding. */
    @Throws(IllegalStateException::class, CancellationException::class)
    suspend fun logEpisode(): QuickLogSummary? {
        val episode = appGraph.quickLog() ?: return null
        return QuickLogSummary(episode.intensity.value, episode.medications.map { it.displayName })
    }
}

/** The facts of a quick log. Swift makes the sentence, so the String Catalog holds its translations. */
class QuickLogSummary(val intensity: Int, val medications: List<String>)
