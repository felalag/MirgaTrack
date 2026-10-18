package com.migratrack.app

import com.migratrack.app.ui.load
import com.migratrack.app.ui.text
import com.migratrack.domain.model.Episode
import kotlin.coroutines.cancellation.CancellationException

/**
 * Swift entry point for logging without the UI. App Intents use it now;
 * the watch complication and Control Center control will call the same use case.
 */
object QuickLogBridge {
    /** Logs an episode from the baseline preset and returns what Siri reports, or null before onboarding. */
    @Throws(IllegalStateException::class, CancellationException::class)
    suspend fun logEpisode(): QuickLogSummary? = appGraph.quickLog()?.quickLogSummary()
}

/** Names a built-in medication in the language of the app, as the screens do, so Siri does not read the stored English copy. */
internal suspend fun Episode.quickLogSummary(): QuickLogSummary =
    QuickLogSummary(intensity.value, medications.map { it.text.load() })

/** The facts of a quick log. Swift makes the sentence, so the String Catalog holds its translations. */
class QuickLogSummary(val intensity: Int, val medications: List<String>)
