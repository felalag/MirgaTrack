package com.migratrack.data

import com.migratrack.data.db.MigraTrackDatabase
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.repository.DocumentStore
import com.migratrack.domain.repository.SyncOutbox
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.time.Clock

internal class SqlSyncOutbox(
    database: MigraTrackDatabase,
    private val dispatcher: CoroutineDispatcher,
    private val clock: Clock,
) : SyncOutbox {
    private val queries = database.syncQueries

    override suspend fun enqueue(id: EpisodeId) {
        withContext(dispatcher) { queries.enqueue(id.value, clock.now().toEpochMilliseconds()) }
    }

    override suspend fun pending(): List<EpisodeId> = withContext(dispatcher) {
        queries.pending().executeAsList().map(::EpisodeId)
    }

    override suspend fun remove(ids: Collection<EpisodeId>) {
        if (ids.isEmpty()) return
        withContext(dispatcher) { queries.dequeue(ids.map { it.value }) }
    }
}

internal class SqlDocumentStore(
    database: MigraTrackDatabase,
    private val dispatcher: CoroutineDispatcher,
) : DocumentStore {
    private val queries = database.syncQueries

    override suspend fun read(name: String): String? = withContext(dispatcher) {
        queries.selectDocument(name).executeAsOneOrNull()
    }

    override suspend fun write(name: String, body: String) {
        withContext(dispatcher) { queries.upsertDocument(name, body) }
    }
}
