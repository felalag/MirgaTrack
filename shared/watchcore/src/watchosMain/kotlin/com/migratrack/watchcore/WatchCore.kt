package com.migratrack.watchcore

import com.migratrack.data.AppleDatabase
import com.migratrack.data.LocalStore
import com.migratrack.domain.analytics.DateText
import com.migratrack.domain.analytics.InsightsCalculator
import com.migratrack.domain.analytics.InsightsPeriod
import com.migratrack.domain.analytics.SummaryCalculator
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.usecase.EpisodeDraft
import com.migratrack.domain.usecase.LogEpisode
import com.migratrack.sync.WatchSync
import kotlinx.coroutines.flow.first
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import platform.Foundation.NSBundle
import kotlin.time.Clock

/** What the watch app and the complications show. */
data class WatchStatus(
    val painFreeDays: Int,
    val windowDays: Int,
    val attacksThisMonth: Int,
    /** "Oct 4", or null before the first episode. */
    val lastEpisodeDate: String?,
    val daysSinceLastEpisode: Int?,
    val presetIntensity: Int,
    /** The acute medications that a quick log adds, for example "Ibuprofen 400mg". */
    val presetMedications: String,
    /** False until the phone sent its first context; the preset is a default until then. */
    val isLinked: Boolean,
)

data class WatchEpisode(val id: String, val intensity: Int, val summary: String)

/**
 * Swift entry point on the watch. The app and the complication extension both open
 * the database in the App Group container, so a log from either one shows in both.
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
        val today = clock.todayIn(zone)
        val all = store.episodes.observeAll().first()
        val summary = SummaryCalculator.calculate(all, sleep = null, today = today, timeZone = zone)
        val last = summary.lastEpisode?.startedAt?.toLocalDateTime(zone)?.date
        val preset = sync.preset()
        return WatchStatus(
            painFreeDays = summary.painFreeDays,
            windowDays = summary.windowDays,
            attacksThisMonth = InsightsCalculator.calculate(all, InsightsPeriod.Month, today, zone).attackCount,
            lastEpisodeDate = last?.let(DateText::monthDay),
            daysSinceLastEpisode = last?.daysUntil(today),
            presetIntensity = preset.intensity.value,
            presetMedications = preset.medications.joinToString(", ") { it.displayName },
            isLinked = sync.isLinked(),
        )
    }

    /** One tap from the complication: the preset as it is. */
    @Throws(Exception::class)
    suspend fun quickLog(): WatchEpisode = log(sync.preset(), LogSource.QuickLog)

    /** From the watch app, with the intensity the user chose on the Digital Crown. */
    @Throws(Exception::class)
    suspend fun log(intensity: Int): WatchEpisode =
        log(sync.preset().copy(intensity = PainIntensity(intensity.coerceIn(PainIntensity.RANGE))), LogSource.App)

    @Throws(Exception::class)
    suspend fun undo(episodeId: String) = sync.undo(EpisodeId(episodeId))

    @Throws(Exception::class)
    suspend fun applyPhoneContext(json: String) = sync.applyContext(json)

    /** JSON to send over WatchConnectivity, or null when nothing waits. */
    @Throws(Exception::class)
    suspend fun pendingBatch(): String? = sync.pendingBatch()

    @Throws(Exception::class)
    suspend fun markDelivered(batch: String) = sync.markDelivered(batch)

    private suspend fun log(draft: EpisodeDraft, source: LogSource): WatchEpisode {
        val episode = logEpisode(draft, source)
        sync.queue(episode)
        return episode.toWatch()
    }

    private fun Episode.toWatch(): WatchEpisode {
        val medications = medications.joinToString(", ") { it.displayName }
        val summary = "Intensity ${intensity.value}/10" + if (medications.isEmpty()) "" else " · $medications"
        return WatchEpisode(id.value, intensity.value, summary)
    }
}
