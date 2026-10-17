package com.migratrack.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.migratrack.data.db.Custom_medication
import com.migratrack.data.db.Custom_trigger
import com.migratrack.data.db.MigraTrackDatabase
import com.migratrack.domain.model.Medication
import com.migratrack.domain.model.MedicationCatalog
import com.migratrack.domain.model.MedicationRole
import com.migratrack.domain.model.Trigger
import com.migratrack.domain.model.TriggerCatalog
import com.migratrack.domain.model.TriggerCategory
import com.migratrack.domain.repository.CatalogRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.time.Clock

internal class SqlCatalogRepository(
    database: MigraTrackDatabase,
    private val dispatcher: CoroutineDispatcher,
    private val clock: Clock,
    private val newId: () -> String,
) : CatalogRepository {
    private val queries = database.profileQueries

    override fun observeTriggers(): Flow<List<Trigger>> =
        queries.selectCustomTriggers().asFlow().mapToList(dispatcher).map { TriggerCatalog.defaults + it.map(Custom_trigger::toDomain) }

    override suspend fun triggers(): List<Trigger> = withContext(dispatcher) {
        TriggerCatalog.defaults + queries.selectCustomTriggers().executeAsList().map(Custom_trigger::toDomain)
    }

    override fun observeMedications(): Flow<List<Medication>> =
        queries.selectCustomMedications().asFlow().mapToList(dispatcher)
            .map { MedicationCatalog.defaults + it.map(Custom_medication::toDomain) }

    override suspend fun medications(): List<Medication> = withContext(dispatcher) {
        MedicationCatalog.defaults + queries.selectCustomMedications().executeAsList().map(Custom_medication::toDomain)
    }

    override suspend fun addCustomTrigger(name: String): Trigger = withContext(dispatcher) {
        val row = Custom_trigger("custom-trigger-${newId()}", name.trim(), clock.now().toEpochMilliseconds())
        queries.insertCustomTrigger(row.id, row.name, row.created_at)
        row.toDomain()
    }

    override suspend fun addCustomMedication(name: String, dose: String, role: MedicationRole): Medication =
        withContext(dispatcher) {
            val row = Custom_medication("custom-medication-${newId()}", name.trim(), dose.trim(), role.name, clock.now().toEpochMilliseconds())
            queries.insertCustomMedication(row.id, row.name, row.dose, row.role, row.created_at)
            row.toDomain()
        }
}

private fun Custom_trigger.toDomain() =
    Trigger(id = id, name = name, category = TriggerCategory.Custom, isCustom = true)

private fun Custom_medication.toDomain() = Medication(
    id = id,
    name = name,
    dose = dose,
    role = enumOrNull<MedicationRole>(role) ?: MedicationRole.Acute,
    isCustom = true,
)
