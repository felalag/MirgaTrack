package com.migratrack.app.feature.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migratrack.app.di.AppGraph
import com.migratrack.app.platform.DatePickerMode
import com.migratrack.app.platform.SheetAction
import com.migratrack.domain.analytics.DateText
import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.Medication
import com.migratrack.domain.model.MedicationRole
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.usecase.EpisodeDraft
import com.migratrack.domain.usecase.toDraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant

/** What the episode form works on. */
sealed interface EpisodeTarget {
    /** The Log tab. After each save the form starts from the preset again. */
    data object LogTab : EpisodeTarget

    /** A new episode on a day the user picked in the Insights calendar. */
    data class NewOn(val date: LocalDate) : EpisodeTarget

    data class Edit(val id: EpisodeId) : EpisodeTarget
}

data class EpisodeFormState(
    val draft: EpisodeDraft? = null,
    /** The stored episode when the form edits one. */
    val original: Episode? = null,
    val saving: Boolean = false,
    /** The stored episode no longer exists, so the screen closes. */
    val missing: Boolean = false,
)

/** The form starts from the logging preset, so a typical attack needs only "Save". */
class EpisodeFormViewModel(private val graph: AppGraph, private val target: EpisodeTarget) : ViewModel() {
    private val _state = MutableStateFlow(EpisodeFormState())
    val state: StateFlow<EpisodeFormState> = _state.asStateFlow()

    init {
        viewModelScope.launch { _state.value = initialState() }
    }

    private suspend fun initialState(): EpisodeFormState = when (target) {
        EpisodeTarget.LogTab -> EpisodeFormState(draft = graph.loggingPreset())
        is EpisodeTarget.NewOn -> EpisodeFormState(draft = graph.loggingPreset().copy(startedAt = startOn(target.date)))
        is EpisodeTarget.Edit -> graph.episodes.find(target.id)
            ?.let { EpisodeFormState(draft = it.toDraft(graph.catalog.medications()), original = it) }
            ?: EpisodeFormState(missing = true)
    }

    /** The current time of day on a past [date]; null, which means now, for today. */
    private fun startOn(date: LocalDate): Instant? {
        val zone = graph.timeZone()
        val now = graph.clock.now().toLocalDateTime(zone)
        return if (date >= now.date) null else LocalDateTime(date, now.time).toInstant(zone)
    }

    private fun edit(change: (EpisodeDraft) -> EpisodeDraft) =
        _state.update { current -> current.copy(draft = current.draft?.let(change)) }

    fun setIntensity(intensity: PainIntensity) = edit { it.copy(intensity = intensity) }

    fun selectRegion(region: CranialRegion) = edit { it.copy(region = if (it.region == region) null else region) }

    fun clearRegion() = edit { it.copy(region = null) }

    fun toggleSymptom(symptom: Symptom) =
        edit { it.copy(symptoms = if (symptom in it.symptoms) it.symptoms - symptom else it.symptoms + symptom) }

    fun clearSymptoms() = edit { it.copy(symptoms = emptySet()) }

    fun removeMedication(medication: Medication) =
        edit { draft -> draft.copy(medications = draft.medications.filterNot { it.id == medication.id }) }

    fun clearMedications() = edit { it.copy(medications = emptyList()) }

    /** Native action sheet: blueprint medications first, then the rest of the catalog, then a custom entry. */
    fun addMedication() = viewModelScope.launch {
        val draft = _state.value.draft ?: return@launch
        val blueprint = graph.profiles.baseline()?.medicationIds.orEmpty()
        val taken = draft.medications.map { it.id }.toSet()
        val candidates = graph.catalog.medications()
            .filter { it.id !in taken }
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

    fun pickStart() = viewModelScope.launch {
        val draft = _state.value.draft ?: return@launch
        val now = graph.clock.now()
        val picked = graph.services.dialogs.pickDate(
            title = "Episode Start",
            initial = draft.startedAt ?: now,
            latest = now,
            mode = DatePickerMode.DateAndTime,
        ) ?: return@launch
        // On the Log tab a time within the current minute stays "Now", so the episode starts when it is saved.
        val keepNow = target == EpisodeTarget.LogTab && now - picked < 1.minutes
        edit { it.copy(startedAt = if (keepNow) null else picked) }
    }

    fun save(onSaved: () -> Unit) = viewModelScope.launch {
        val current = _state.value
        val draft = current.draft ?: return@launch
        if (current.saving) return@launch
        _state.update { it.copy(saving = true) }
        val original = current.original
        when {
            original == null -> graph.logEpisode(draft)
            graph.episodes.find(original.id) == null -> {
                graph.services.dialogs.confirm(
                    title = "Episode Deleted",
                    message = "This episode was deleted on another device, so the changes cannot be saved.",
                    confirmTitle = "OK",
                )
                _state.update { it.copy(missing = true) }
                return@launch
            }
            else -> graph.updateEpisode(original, draft)
        }
        if (target == EpisodeTarget.LogTab) _state.value = EpisodeFormState(draft = graph.loggingPreset())
        onSaved()
    }

    fun delete(onDeleted: () -> Unit) = viewModelScope.launch {
        val original = _state.value.original ?: return@launch
        if (confirmDelete(graph, original)) {
            graph.episodes.delete(original.id)
            onDeleted()
        }
    }
}

/** The system confirmation sheet before an episode is deleted. */
suspend fun confirmDelete(graph: AppGraph, episode: Episode): Boolean {
    val dateTime = episode.startedAt.toLocalDateTime(graph.timeZone())
    val choice = graph.services.dialogs.chooseAction(
        title = "${DateText.fullDate(dateTime.date)} at ${DateText.time(dateTime)}",
        message = "Intensity ${episode.intensity.value}/10. The episode leaves your history and reports.",
        actions = listOf(SheetAction("Delete Episode Log", destructive = true)),
    )
    return choice == 0
}
