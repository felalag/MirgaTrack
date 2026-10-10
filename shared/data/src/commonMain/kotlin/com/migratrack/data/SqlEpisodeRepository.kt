package com.migratrack.data

import app.cash.sqldelight.coroutines.asFlow
import com.migratrack.data.db.Medication_intake
import com.migratrack.data.db.MigraTrackDatabase
import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.MedicationIntake
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.repository.EpisodeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.time.Instant
import com.migratrack.data.db.Episode as EpisodeRow

internal class SqlEpisodeRepository(
    private val database: MigraTrackDatabase,
    private val dispatcher: CoroutineDispatcher,
) : EpisodeRepository {
    private val queries = database.episodeQueries

    // Intakes change only in the transaction that writes their episode, so the episode query's notification covers them.
    override fun observeAll(): Flow<List<Episode>> =
        queries.selectAll().asFlow()
            .map { query -> attachIntakes(query.executeAsList()) }
            .flowOn(dispatcher)

    override suspend fun inRange(from: Instant, until: Instant): List<Episode> = withContext(dispatcher) {
        attachIntakes(queries.selectInRange(from.toEpochMilliseconds(), until.toEpochMilliseconds()).executeAsList())
    }

    override suspend fun find(id: EpisodeId): Episode? = withContext(dispatcher) {
        queries.selectById(id.value).executeAsOneOrNull()?.toDomain(queries.selectIntakes(id.value).executeAsList())
    }

    override suspend fun add(episode: Episode) = withContext(dispatcher) {
        database.transaction {
            queries.deleteIntakes(episode.id.value)
            queries.insertEpisode(
                id = episode.id.value,
                started_at = episode.startedAt.toEpochMilliseconds(),
                intensity = episode.intensity.value.toLong(),
                region = episode.region?.name,
                symptoms = episode.symptoms.joinToString(",") { it.name },
                source = episode.source.name,
            )
            episode.medications.forEachIndexed { position, intake ->
                queries.insertIntake(
                    episode_id = episode.id.value,
                    position = position.toLong(),
                    medication_id = intake.medicationId,
                    name = intake.name,
                    dose = intake.dose,
                    taken_at = intake.takenAt.toEpochMilliseconds(),
                )
            }
        }
    }

    override suspend fun delete(id: EpisodeId) = withContext(dispatcher) {
        database.transaction {
            queries.deleteIntakes(id.value)
            queries.deleteEpisode(id.value)
        }
    }

    private fun attachIntakes(rows: List<EpisodeRow>): List<Episode> {
        if (rows.isEmpty()) return emptyList()
        val intakes = queries.selectAllIntakes().executeAsList().groupBy { it.episode_id }
        return rows.map { it.toDomain(intakes[it.id].orEmpty()) }
    }
}

private fun EpisodeRow.toDomain(intakes: List<Medication_intake>) = Episode(
    id = EpisodeId(id),
    startedAt = Instant.fromEpochMilliseconds(started_at),
    intensity = PainIntensity(intensity.toInt().coerceIn(PainIntensity.RANGE)),
    region = enumOrNull<CranialRegion>(region),
    symptoms = symptoms.splitIds().mapNotNull { enumOrNull<Symptom>(it) }.toSet(),
    medications = intakes.map {
        MedicationIntake(it.medication_id, it.name, it.dose, Instant.fromEpochMilliseconds(it.taken_at))
    },
    source = enumOrNull<LogSource>(source) ?: LogSource.App,
)
