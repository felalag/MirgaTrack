package com.migratrack.app.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migratrack.app.di.AppGraph
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.common_add
import com.migratrack.app.generated.resources.common_add_medication
import com.migratrack.app.generated.resources.common_medication_name_and_dose
import com.migratrack.app.generated.resources.common_medication_placeholder
import com.migratrack.app.generated.resources.common_next
import com.migratrack.app.generated.resources.onboarding_custom_trigger_message
import com.migratrack.app.generated.resources.onboarding_custom_trigger_placeholder
import com.migratrack.app.generated.resources.onboarding_custom_trigger_title
import com.migratrack.app.generated.resources.onboarding_medication_role_acute
import com.migratrack.app.generated.resources.onboarding_medication_role_message
import com.migratrack.app.generated.resources.onboarding_medication_role_preventive
import com.migratrack.app.platform.SheetAction
import com.migratrack.domain.model.BaselineProfile
import com.migratrack.domain.model.Diagnosis
import com.migratrack.domain.model.EpisodeFrequency
import com.migratrack.domain.model.Medication
import com.migratrack.domain.model.MedicationRole
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.Trigger
import com.migratrack.domain.model.TypicalDuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

data class BaselineFormState(
    val draft: BaselineProfile,
    val triggers: List<Trigger>,
    val medications: List<Medication>,
    val saving: Boolean,
) {
    val canContinueFromDiagnoses: Boolean get() = draft.diagnoses.isNotEmpty()
}

/** Holds the baseline draft for the three onboarding steps, or for one step edited from Settings. */
class BaselineFormViewModel(private val graph: AppGraph, private val editing: Boolean) : ViewModel() {
    private val draft = MutableStateFlow(BaselineProfile.Initial)
    private val saving = MutableStateFlow(false)

    val state: StateFlow<BaselineFormState> = combine(
        draft,
        graph.catalog.observeTriggers(),
        graph.catalog.observeMedications(),
        saving,
    ) { draft, triggers, medications, saving -> BaselineFormState(draft, triggers, medications, saving) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), BaselineFormState(BaselineProfile.Initial, emptyList(), emptyList(), false))

    init {
        if (editing) {
            viewModelScope.launch { graph.profiles.baseline()?.let { draft.value = it } }
        }
    }

    /** "Not diagnosed" excludes the diagnoses, and a diagnosis excludes "Not diagnosed". */
    fun toggleDiagnosis(diagnosis: Diagnosis) = draft.update { current ->
        val chosen = current.diagnoses
        val next = when {
            diagnosis in chosen -> chosen - diagnosis
            diagnosis == Diagnosis.NotDiagnosed -> setOf(Diagnosis.NotDiagnosed)
            else -> chosen - Diagnosis.NotDiagnosed + diagnosis
        }
        current.copy(diagnoses = next)
    }

    fun setFrequency(frequency: EpisodeFrequency) = draft.update { it.copy(frequency = frequency) }

    fun setDuration(duration: TypicalDuration) = draft.update { it.copy(typicalDuration = duration) }

    fun setIntensity(intensity: PainIntensity) = draft.update { it.copy(typicalIntensity = intensity) }

    fun toggleTrigger(id: String) = draft.update { it.copy(triggerIds = it.triggerIds.toggle(id)) }

    fun toggleMedication(id: String) = draft.update { it.copy(medicationIds = it.medicationIds.toggle(id)) }

    fun addCustomTrigger() = viewModelScope.launch {
        val name = graph.services.dialogs.prompt(
            title = getString(Res.string.onboarding_custom_trigger_title),
            message = getString(Res.string.onboarding_custom_trigger_message),
            placeholder = getString(Res.string.onboarding_custom_trigger_placeholder),
            confirmTitle = getString(Res.string.common_add),
        )?.trim()
        if (name.isNullOrEmpty()) return@launch
        val trigger = graph.catalog.addCustomTrigger(name)
        draft.update { it.copy(triggerIds = it.triggerIds + trigger.id) }
    }

    fun addCustomMedication() = viewModelScope.launch {
        val dialogs = graph.services.dialogs
        val name = dialogs.prompt(
            title = getString(Res.string.common_add_medication),
            message = getString(Res.string.common_medication_name_and_dose),
            placeholder = getString(Res.string.common_medication_placeholder),
            confirmTitle = getString(Res.string.common_next),
        )?.trim()
        if (name.isNullOrEmpty()) return@launch
        val roleIndex = dialogs.chooseAction(
            title = name,
            message = getString(Res.string.onboarding_medication_role_message),
            actions = listOf(
                SheetAction(getString(Res.string.onboarding_medication_role_acute)),
                SheetAction(getString(Res.string.onboarding_medication_role_preventive)),
            ),
        ) ?: return@launch
        val role = if (roleIndex == 0) MedicationRole.Acute else MedicationRole.Preventive
        val medication = graph.catalog.addCustomMedication(name, dose = "", role = role)
        draft.update { it.copy(medicationIds = it.medicationIds + medication.id) }
    }

    /** Ends onboarding. The root screen switches to the tabs when the baseline appears. */
    fun finishOnboarding() = viewModelScope.launch {
        saving.value = true
        graph.completeOnboarding(draft.value)
    }

    fun saveEdits(onSaved: () -> Unit) = viewModelScope.launch {
        saving.value = true
        graph.profiles.saveBaseline(draft.value)
        saving.value = false
        onSaved()
    }

    private fun Set<String>.toggle(id: String) = if (id in this) this - id else this + id
}
