package com.migratrack.watchcore

import com.migratrack.data.AppleDatabase
import com.migratrack.data.LocalStore
import com.migratrack.domain.analytics.SummaryCalculator
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.usecase.LogEpisode
import com.migratrack.sync.WatchSync
import kotlinx.coroutines.flow.first
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toNSDate
import kotlinx.datetime.todayIn
import platform.Foundation.NSBundle
import platform.Foundation.NSDate
import kotlin.time.Clock

/** What the watch app and the complication show. */
data class WatchStatus(
    val painFreeDays: Int,
    /** Tracked days of the 30-day window, fewer than 30 in the first month of tracking. */
    val trackedDays: Int,
    /** The intensity the logger starts at: the typical intensity from the phone. */
    val presetIntensity: Int,
    /** When the last episode started; null when none is logged. */
    val lastEpisodeStartedAt: NSDate?,
    /** False until a context arrives from a phone that finished onboarding; the watch does not log until then. */
    val isLinked: Boolean,
)

/** An episode the watch just saved, for the Episode logged screen. */
data class WatchEpisode(
    val intensity: Int,
    /** The acute medications from the phone's preset, for example "Ibuprofen 400mg, Sumatriptan 50mg"; empty when none. */
    val medications: String,
)

/**
 * Swift entry point on the watch. The app and the complication extension both open
 * the database in the App Group container, so a log in the app shows in the complication.
 * The group id comes from the MigraTrackAppGroup key of Info.plist, which Config.xcconfig fills in.
 */
object WatchCore {
    private val clock = Clock.System
    private val store by lazy {
        val group = NSBundle.mainBundle.objectForInfoDictionaryKey("MigraTrackAppGroup") as? String
        LocalStore(AppleDatabase.driver(group?.let(AppleDatabase::appGroupDirectory)))
    }
    private val sync by lazy { WatchSync(store.episodes, store.outbox, store.documents) }
    private val logEpisode by lazy { LogEpisode(store.episodes, clock) }

    @Throws(Exception::class)
    suspend fun status(): WatchStatus {
        val zone = TimeZone.currentSystemDefault()
        val all = store.episodes.observeAll().first()
        val today = clock.todayIn(zone)
        val summary = SummaryCalculator.calculate(all, today, zone, trackedSince = sync.trackedSince() ?: today)
        return WatchStatus(
            painFreeDays = summary.painFreeDays,
            trackedDays = summary.trackedDays,
            lastEpisodeStartedAt = summary.lastEpisode?.startedAt?.toNSDate(),
            presetIntensity = sync.preset().intensity.value,
            isLinked = sync.isLinked(),
        )
    }

    /** Saves the phone's preset with the intensity the user chose on the Digital Crown, and queues it for the phone. */
    @Throws(Exception::class)
    suspend fun log(intensity: Int): WatchEpisode {
        val episode = logEpisode(sync.draft(intensity), LogSource.App)
        sync.queue(episode)
        return episode.toWatch()
    }

    @Throws(Exception::class)
    suspend fun applyPhoneContext(json: String) = sync.applyContext(json)

    /** JSON to send over WatchConnectivity, or null when nothing waits. */
    @Throws(Exception::class)
    suspend fun pendingBatch(): String? = sync.pendingBatch()

    @Throws(Exception::class)
    suspend fun markDelivered(batch: String) = sync.markDelivered(batch)

    private fun Episode.toWatch() = WatchEpisode(intensity.value, medications.joinToString(", ") { it.displayName })
}
