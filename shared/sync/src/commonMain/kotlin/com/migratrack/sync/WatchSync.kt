package com.migratrack.sync

import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.repository.DocumentStore
import com.migratrack.domain.repository.EpisodeRepository
import com.migratrack.domain.repository.SyncOutbox
import com.migratrack.domain.usecase.EpisodeDraft
import com.migratrack.domain.usecase.PresetProvider
import kotlinx.coroutines.flow.first
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

/**
 * The watch side. The watch keeps a mirror of the phone's recent episodes plus its own unconfirmed ones,
 * and it takes the logging preset from the phone.
 */
class WatchSync(
    private val episodes: EpisodeRepository,
    private val outbox: SyncOutbox,
    private val documents: DocumentStore,
) : PresetProvider {

    override suspend fun preset(): EpisodeDraft =
        storedContext()?.preset?.toDraft() ?: EpisodeDraft(PainIntensity.Default)

    /** The preset with the intensity the user chose on the watch, kept within 1–10. */
    suspend fun draft(intensity: Int): EpisodeDraft =
        preset().copy(intensity = PainIntensity(intensity.coerceIn(PainIntensity.RANGE)))

    /** True once a context from the phone arrived. */
    suspend fun isLinked(): Boolean = storedContext() != null

    /**
     * Mirrors the phone. An episode in the context, or its id in the received list, confirms that the phone
     * took it in, so it leaves the outbox. Episodes the context does not list go away, unless they still wait in the outbox.
     */
    suspend fun applyContext(json: String) {
        val context = SyncJson.decodeFromString(PhoneContext.serializer(), json)
        documents.write(CONTEXT, json)
        val incoming = context.episodes.mapNotNull { it.toDomain() }
        val incomingIds = incoming.map { it.id }.toSet()
        val confirmed = incomingIds + context.receivedIds.map(::EpisodeId)
        outbox.remove(outbox.pending().filter { it in confirmed })
        val keep = incomingIds + outbox.pending()
        episodes.observeAll().first().filter { it.id !in keep }.forEach { episodes.delete(it.id) }
        incoming.forEach { episodes.add(it) }
    }

    /** Remembers an episode logged on the watch until a phone context lists it. */
    suspend fun queue(episode: Episode) = outbox.enqueue(episode.id)

    /**
     * Removes the episode here and asks the phone to delete it. The request goes even for an unconfirmed
     * episode, because its batch may already be on the way; the phone ignores ids it does not have.
     */
    suspend fun undo(id: EpisodeId) {
        episodes.delete(id)
        outbox.remove(listOf(id))
        writeDeleted(readDeleted() + id.value)
    }

    /** JSON for WatchConnectivity, or null when nothing waits. */
    suspend fun pendingBatch(): String? {
        val pending = outbox.pending().toSet()
        val waiting = episodes.observeAll().first().filter { it.id in pending }
        // An id without its episode cannot be sent; dropping it keeps the outbox from growing.
        outbox.remove(pending - waiting.map { it.id }.toSet())
        val deleted = readDeleted()
        if (waiting.isEmpty() && deleted.isEmpty()) return null
        return SyncJson.encodeToString(WatchBatch.serializer(), WatchBatch(waiting.map { it.toSync() }, deleted))
    }

    /**
     * Call when WatchConnectivity confirms the transfer of [batch]. It clears the delete requests only:
     * a finished transfer does not prove that the phone app stored the episodes, so they wait for a context.
     */
    suspend fun markDelivered(batch: String) {
        val delivered = SyncJson.decodeFromString(WatchBatch.serializer(), batch)
        writeDeleted(readDeleted() - delivered.deletedIds.toSet())
    }

    private suspend fun storedContext(): PhoneContext? =
        documents.read(CONTEXT)?.let { SyncJson.decodeFromString(PhoneContext.serializer(), it) }

    private suspend fun readDeleted(): List<String> =
        documents.read(DELETED)?.let { SyncJson.decodeFromString(IdList, it) }.orEmpty()

    private suspend fun writeDeleted(ids: List<String>) =
        documents.write(DELETED, SyncJson.encodeToString(IdList, ids.distinct()))

    private companion object {
        const val CONTEXT = "phone-context"
        const val DELETED = "deleted-episode-ids"
        val IdList = ListSerializer(String.serializer())
    }
}
