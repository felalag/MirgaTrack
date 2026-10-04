package com.migratrack.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.migratrack.data.db.Baseline_profile
import com.migratrack.data.db.Export_preferences
import com.migratrack.data.db.MigraTrackDatabase
import com.migratrack.data.db.User_profile
import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.Diagnosis
import com.migratrack.domain.model.EpisodeFrequency
import com.migratrack.domain.model.ExportPreferences
import com.migratrack.domain.model.ExportRange
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.TypicalDuration
import com.migratrack.domain.model.UserProfile
import com.migratrack.domain.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

internal class SqlProfileRepository(
    database: MigraTrackDatabase,
    private val dispatcher: CoroutineDispatcher,
) : ProfileRepository {
    private val queries = database.profileQueries

    override fun observeBaseline(): Flow<BaselineProfile?> =
        queries.selectBaseline().asFlow().mapToOneOrNull(dispatcher).map { it?.toDomain() }

    override suspend fun baseline(): BaselineProfile? = withContext(dispatcher) {
        queries.selectBaseline().executeAsOneOrNull()?.toDomain()
    }

    override suspend fun saveBaseline(profile: BaselineProfile) {
        withContext(dispatcher) {
            queries.upsertBaseline(
                diagnoses = profile.diagnoses.joinToString(",") { it.name },
                frequency = profile.frequency.name,
                typical_duration = profile.typicalDuration.name,
                typical_intensity = profile.typicalIntensity.value.toLong(),
                trigger_ids = profile.triggerIds.joinToString(","),
                medication_ids = profile.medicationIds.joinToString(","),
            )
        }
    }

    override fun observeUser(): Flow<UserProfile?> =
        queries.selectUser().asFlow().mapToOneOrNull(dispatcher).map { it?.toDomain() }

    override suspend fun user(): UserProfile? = withContext(dispatcher) {
        queries.selectUser().executeAsOneOrNull()?.toDomain()
    }

    override suspend fun saveUser(profile: UserProfile) {
        withContext(dispatcher) {
            queries.upsertUser(name = profile.name, member_since = profile.memberSince.toString())
        }
    }

    override fun observeExportPreferences(): Flow<ExportPreferences> =
        queries.selectExportPreferences().asFlow().mapToOneOrNull(dispatcher).map { it?.toDomain() ?: ExportPreferences() }

    override suspend fun saveExportPreferences(preferences: ExportPreferences) {
        withContext(dispatcher) {
            queries.upsertExportPreferences(
                include_medications = preferences.includeMedications.toLong(),
                include_symptoms = preferences.includeSymptoms.toLong(),
                include_pain_charts = preferences.includePainCharts.toLong(),
                range = preferences.range.name,
            )
        }
    }
}

private fun Boolean.toLong(): Long = if (this) 1L else 0L

private fun Baseline_profile.toDomain() = BaselineProfile(
    diagnoses = diagnoses.splitIds().mapNotNull { enumOrNull<Diagnosis>(it) }.toSet(),
    frequency = enumOrNull<EpisodeFrequency>(frequency) ?: BaselineProfile.Initial.frequency,
    typicalDuration = enumOrNull<TypicalDuration>(typical_duration) ?: BaselineProfile.Initial.typicalDuration,
    typicalIntensity = PainIntensity(typical_intensity.toInt().coerceIn(PainIntensity.RANGE)),
    triggerIds = trigger_ids.splitIds().toSet(),
    medicationIds = medication_ids.splitIds().toSet(),
)

private fun User_profile.toDomain() = UserProfile(name = name, memberSince = LocalDate.parse(member_since))

private fun Export_preferences.toDomain() = ExportPreferences(
    includeMedications = include_medications == 1L,
    includeSymptoms = include_symptoms == 1L,
    includePainCharts = include_pain_charts == 1L,
    range = enumOrNull<ExportRange>(range) ?: ExportRange.Last90Days,
)
