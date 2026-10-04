package com.migratrack.app.feature.log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migratrack.app.di.AppGraph
import com.migratrack.app.platform.SheetAction
import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.Medication
import com.migratrack.domain.model.MedicationRole
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.usecase.EpisodeDraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LogUiState(val draft: EpisodeDraft? = null, val saving: Boolean = false)

/** The form starts from the logging preset, so a typical attack needs only "Save". */
class LogEpisodeViewModel(private val graph: AppGraph) : ViewModel() {
    private val _state = MutableStateFlow(LogUiState())
    val state: StateFlow<LogUiState> = _state.asStateFlow()

    init {
        resetToPreset()
    }

    private fun resetToPreset() = viewModelScope.launch {
        _state.value = LogUiState(draft = graph.loggingPreset())
    }

    private fun edit(change: (EpisodeDraft) -> EpisodeDraft) =
        _state.update { current -> current.copy(draft = current.draft?.let(change)) }

    fun setIntensity(intensity: PainIntensity) = edit { it.copy(intensity = intensity) }

    fun selectRegion(region: CranialRegion) = edit { it.copy(region = if (it.region == region) null else region) }

    fun clearRegion() = edit { it.copy(region = null) }

    fun toggleSymptom(symptom: Symptom) =
        edit { it.copy(symptoms = if (symptom in it.symptoms) it.symptoms - symptom else it.symptoms + symptom) }

    fun clearSymptoms() = edit { it.copy(symptoms = emptySet()) }

    fun removeMedication(medication: Medication) = edit { draft -> draft.copy(medications = draft.medications - medication) }

    fun clearMedications() = edit { it.copy(medications = emptyList()) }

    /** Native action sheet: blueprint medications first, then the rest of the catalog, then a custom entry. */
    fun addMedication() = viewModelScope.launch {
        val draft = _state.value.draft ?: return@launch
        val blueprint = graph.profiles.baseline()?.medicationIds.orEmpty()
        val candidates = graph.catalog.medications()
            .filter { it !in draft.medications }
            .sortedWith(compareBy({ it.id !in blueprint }, { it.role != MedicationRole.Acute }))
        val dialogs = graph.services.dialogs
        val choice = dialogs.chooseAction(
            title = "Add Medication",
            message = "What did you take for this episode?",
            actions = candidates.map { SheetAction(it.displayName) } + SheetAction("Other Medication…"),
        ) ?: return@launch
        val medication = if (choice < candidates.size) {
            candidates[choice]
        } else {
            val name = dialogs.prompt(
                title = "Other Medication",
                message = "Enter the name and dose.",
                placeholder = "For example, Eletriptan 40mg",
                confirmTitle = "Add",
            )?.trim()
            if (name.isNullOrEmpty()) return@launch
            graph.catalog.addCustomMedication(name, dose = "", role = MedicationRole.Acute)
        }
        edit { it.copy(medications = it.medications + medication) }
    }

    fun save(onSaved: () -> Unit) = viewModelScope.launch {
        val draft = _state.value.draft ?: return@launch
        _state.update { it.copy(saving = true) }
        graph.logEpisode(draft)
        _state.value = LogUiState(draft = graph.loggingPreset())
        onSaved()
    }
}
