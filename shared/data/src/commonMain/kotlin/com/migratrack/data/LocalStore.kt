package com.migratrack.data

import app.cash.sqldelight.db.SqlDriver
import com.migratrack.data.db.MigraTrackDatabase
import com.migratrack.domain.repository.CatalogRepository
import com.migratrack.domain.repository.DocumentStore
import com.migratrack.domain.repository.EpisodeRepository
import com.migratrack.domain.repository.LocalDataEraser
import com.migratrack.domain.repository.ProfileRepository
import com.migratrack.domain.repository.SyncOutbox
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.Uuid

/** The on-device SQLite store and the repositories on top of it. */
class LocalStore(
    driver: SqlDriver,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    clock: Clock = Clock.System,
    newId: () -> String = { Uuid.random().toString() },
) {
    private val database = MigraTrackDatabase(driver)

    val episodes: EpisodeRepository = SqlEpisodeRepository(database, dispatcher)
    val profiles: ProfileRepository = SqlProfileRepository(database, dispatcher)
    val catalog: CatalogRepository = SqlCatalogRepository(database, dispatcher, clock, newId)
    val outbox: SyncOutbox = SqlSyncOutbox(database, dispatcher, clock)
    val documents: DocumentStore = SqlDocumentStore(database, dispatcher)

    val eraser: LocalDataEraser = object : LocalDataEraser {
        override suspend fun eraseAll() = withContext(dispatcher) {
            database.transaction {
                with(database.episodeQueries) {
                    deleteAllIntakes()
                    deleteAllTriggers()
                    deleteAllEpisodes()
                }
                with(database.profileQueries) {
                    deleteBaseline()
                    deleteUser()
                    deleteExportPreferences()
                    deleteCustomTriggers()
                    deleteCustomMedications()
                }
                with(database.syncQueries) {
                    clearOutbox()
                    clearDocuments()
                    upsertDocument(ERASED_AT, clock.now().toEpochMilliseconds().toString())
                }
            }
        }

        override suspend fun erasedAt(): Instant? =
            documents.read(ERASED_AT)?.toLongOrNull()?.let(Instant::fromEpochMilliseconds)
    }

    private companion object {
        const val ERASED_AT = "erased-at"
    }
}

internal inline fun <reified E : Enum<E>> enumOrNull(name: String?): E? =
    enumValues<E>().firstOrNull { it.name == name }

internal fun String.splitIds(): List<String> = split(',').filter { it.isNotBlank() }
