package com.migratrack.domain.repository

import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.ExportPreferences
import com.migratrack.domain.model.Medication
import com.migratrack.domain.model.MedicationRole
import com.migratrack.domain.model.Trigger
import com.migratrack.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

interface EpisodeRepository {
    /** All episodes, newest first. */
    fun observeAll(): Flow<List<Episode>>

    /** Episodes that started in [from, until), oldest first. */
    suspend fun inRange(from: Instant, until: Instant): List<Episode>

    suspend fun find(id: EpisodeId): Episode?

    /** Inserts the episode, or replaces the stored one with the same id. */
    suspend fun add(episode: Episode)

    suspend fun delete(id: EpisodeId)
}

interface ProfileRepository {
    /** Emits null until the user completes onboarding. */
    fun observeBaseline(): Flow<BaselineProfile?>

    suspend fun baseline(): BaselineProfile?

    suspend fun saveBaseline(profile: BaselineProfile)

    fun observeUser(): Flow<UserProfile?>

    suspend fun user(): UserProfile?

    suspend fun saveUser(profile: UserProfile)

    fun observeExportPreferences(): Flow<ExportPreferences>

    suspend fun saveExportPreferences(preferences: ExportPreferences)
}

/** Built-in triggers and medications merged with the ones the user adds. */
interface CatalogRepository {
    fun observeTriggers(): Flow<List<Trigger>>

    suspend fun triggers(): List<Trigger>

    fun observeMedications(): Flow<List<Medication>>

    suspend fun medications(): List<Medication>

    suspend fun addCustomTrigger(name: String): Trigger

    suspend fun addCustomMedication(name: String, dose: String, role: MedicationRole): Medication
}

/** Deletes every record on this device. Sign out uses it because the app has no account server. */
fun interface LocalDataEraser {
    suspend fun eraseAll()
}

/** Episodes logged on this device that the paired device has not confirmed yet. */
interface SyncOutbox {
    suspend fun enqueue(id: EpisodeId)

    /** Oldest first. */
    suspend fun pending(): List<EpisodeId>

    suspend fun remove(ids: Collection<EpisodeId>)
}

/** Small named text documents, such as the preset the watch receives from the phone. */
interface DocumentStore {
    suspend fun read(name: String): String?

    suspend fun write(name: String, body: String)
}
