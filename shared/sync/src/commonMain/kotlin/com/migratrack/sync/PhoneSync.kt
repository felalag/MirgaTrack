package com.migratrack.sync

import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.repository.CatalogRepository
import com.migratrack.domain.repository.DocumentStore
import com.migratrack.domain.repository.EpisodeRepository
import com.migratrack.domain.repository.LocalDataEraser
import com.migratrack.domain.repository.ProfileRepository
import com.migratrack.domain.usecase.PresetProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

/** The phone side: builds the context for the watch and applies the batches the watch sends. */
class PhoneSync(
    private val episodes: EpisodeRepository,
    private val profiles: ProfileRepository,
    private val catalog: CatalogRepository,
    private val preset: PresetProvider,
    private val documents: DocumentStore,
    private val eraser: LocalDataEraser,
    private val clock: Clock,
) {
    private val receivedChanges = MutableStateFlow(0)

    /**
     * A new context after every change to the episodes, the preset, the tracking start, or the ids received from the watch.
     * Delete All Data changes the episodes and the preset, so its erase time goes out without a trigger of its own.
     */
    fun contexts(): Flow<String> =
        combine(episodes.observeAll(), profiles.observeBaseline(), profiles.observeUser(), catalog.observeMedications(), receivedChanges) { all, _, _, _, _ -> all }
            .map { all -> encode(all) }
            .distinctUntilChanged()

    suspend fun context(): String = encode(episodes.observeAll().first())

    /**
     * Adds the episodes the phone does not have and deletes the ones an earlier watch build undid.
     * Batches can arrive twice and out of order with a delete, so both steps are idempotent.
     * An id the phone took in once is never added again: the user may have deleted that episode here since.
     * Before onboarding the phone takes in nothing, and the watch keeps its episodes until a later batch.
     * Episodes that started before Delete All Data stay deleted, also from a batch that was on its way.
     * @return the number of episodes added.
     */
    suspend fun receive(batch: String): Int {
        if (profiles.baseline() == null) return 0
        val decoded = SyncJson.decodeFromString<WatchBatch>(batch)
        val received = readReceived()
        val known = episodes.observeAll().first().map { it.id }.toSet()
        val erasedAt = eraser.erasedAt()
        val fresh = decoded.episodes.mapNotNull { it.toDomain() }
            .filter { it.id !in known && it.id.value !in received && it.id.value !in decoded.deletedIds }
            .filter { erasedAt == null || it.startedAt >= erasedAt }
        fresh.forEach { episodes.add(it) }
        decoded.deletedIds.map(::EpisodeId).filter { it in known }.forEach { episodes.delete(it) }
        writeReceived(received + decoded.episodes.map { it.id } + decoded.deletedIds)
        receivedChanges.value++
        return fresh.size
    }

    // The watch shows a 30-day ring and the current month; 60 days covers both with room to spare.
    private suspend fun encode(all: List<Episode>): String {
        val since = clock.now() - CONTEXT_DAYS.days
        val context = PhoneContext(
            preset = preset.preset().toSync(),
            episodes = all.filter { it.startedAt >= since }.map { it.toSync() },
            receivedIds = readReceived(),
            isSetUp = profiles.baseline() != null,
            erasedAt = eraser.erasedAt()?.toEpochMilliseconds(),
            trackedSince = profiles.user()?.memberSince?.toString(),
        )
        return SyncJson.encodeToString(PhoneContext.serializer(), context)
    }

    private suspend fun readReceived(): List<String> =
        documents.read(RECEIVED)?.let { SyncJson.decodeFromString(IdList, it) }.orEmpty()

    // The watch clears its outbox within a few batches, so the newest ids are the only ones that matter.
    private suspend fun writeReceived(ids: List<String>) =
        documents.write(RECEIVED, SyncJson.encodeToString(IdList, ids.distinct().takeLast(MAX_RECEIVED_IDS)))

    companion object {
        const val CONTEXT_DAYS = 60
        const val MAX_RECEIVED_IDS = 200
        private const val RECEIVED = "received-watch-episode-ids"
        private val IdList = ListSerializer(String.serializer())
    }
}
