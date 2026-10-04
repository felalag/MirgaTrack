package com.migratrack.sync

import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.repository.CatalogRepository
import com.migratrack.domain.repository.EpisodeRepository
import com.migratrack.domain.repository.ProfileRepository
import com.migratrack.domain.usecase.PresetProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

/** The phone side: builds the context for the watch and applies the batches the watch sends. */
class PhoneSync(
    private val episodes: EpisodeRepository,
    private val profiles: ProfileRepository,
    private val catalog: CatalogRepository,
    private val preset: PresetProvider,
    private val clock: Clock,
) {
    /** A new context after every change to the episodes or the preset. */
    fun contexts(): Flow<String> =
        combine(episodes.observeAll(), profiles.observeBaseline(), catalog.observeMedications()) { all, _, _ -> all }
            .map { all -> encode(all) }
            .distinctUntilChanged()

    suspend fun context(): String = encode(episodes.observeAll().first())

    /**
     * Adds the episodes the phone does not have and deletes the ones the user undid.
     * Batches can arrive twice and out of order with a delete, so both steps are idempotent.
     * @return the number of episodes added.
     */
    suspend fun receive(batch: String): Int {
        val decoded = SyncJson.decodeFromString<WatchBatch>(batch)
        val known = episodes.observeAll().first().map { it.id }.toSet()
        val fresh = decoded.episodes.mapNotNull { it.toDomain() }.filter { it.id !in known }
        fresh.forEach { episodes.add(it) }
        decoded.deletedIds.map(::EpisodeId).filter { it in known }.forEach { episodes.delete(it) }
        return fresh.size
    }

    // The watch shows a 30-day ring and the current month; 60 days covers both with room to spare.
    private suspend fun encode(all: List<Episode>): String {
        val since = clock.now() - CONTEXT_DAYS.days
        val context = PhoneContext(
            preset = preset.preset().toSync(),
            episodes = all.filter { it.startedAt >= since }.map { it.toSync() },
        )
        return SyncJson.encodeToString(PhoneContext.serializer(), context)
    }

    companion object {
        const val CONTEXT_DAYS = 60
    }
}
