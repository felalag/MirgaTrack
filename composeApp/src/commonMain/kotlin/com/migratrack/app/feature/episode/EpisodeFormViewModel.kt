package com.migratrack.app.feature.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migratrack.app.di.AppGraph
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.common_add
import com.migratrack.app.generated.resources.common_add_medication
import com.migratrack.app.generated.resources.common_add_trigger
import com.migratrack.app.generated.resources.common_date_at_time
import com.migratrack.app.generated.resources.common_delete_episode
import com.migratrack.app.generated.resources.common_medication_name_and_dose
import com.migratrack.app.generated.resources.common_medication_placeholder
import com.migratrack.app.generated.resources.episode_add_medication_message
import com.migratrack.app.generated.resources.episode_add_trigger_message
import com.migratrack.app.generated.resources.episode_delete_message
import com.migratrack.app.generated.resources.episode_missing_message
import com.migratrack.app.generated.resources.episode_missing_title
import com.migratrack.app.generated.resources.episode_other_medication_action
import com.migratrack.app.generated.resources.episode_other_medication_title
import com.migratrack.app.generated.resources.episode_other_trigger_action
import com.migratrack.app.generated.resources.episode_other_trigger_message
import com.migratrack.app.generated.resources.episode_other_trigger_placeholder
import com.migratrack.app.generated.resources.episode_other_trigger_title
import com.migratrack.app.generated.resources.episode_start
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
import com.migratrack.domain.model.Trigger
import com.migratrack.domain.usecase.EpisodeDraft
import com.migratrack.domain.usecase.toDraft
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.getString

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
    /** The known triggers of the baseline profile. The form offers them but does not select them. */
    val baselineTriggers: List<Trigger> = emptyList(),
    /** The stored episode when the form edits one. */
    val original: Episode? = null,
    val saving: Boolean = false,
    /** The stored episode no longer exists, so the screen closes. */
    val missing: Boolean = false,
)

/** The form starts from the logging preset, so a typical attack needs only "Save". */
class EpisodeFormViewModel(private val graph: AppGraph, private val target: EpisodeTarget) : ViewModel() {
    private val _state = MutableStateFlow(EpisodeFormState())

    // Observed, so that the Log tab follows edits to Known Triggers in Settings.
    private val baselineTriggers: StateFlow<List<Trigger>> =
        combine(graph.profiles.observeBaseline(), graph.catalog.observeTriggers()) { baseline, triggers ->
            triggers.filter { it.id in baseline?.triggerIds.orEmpty() }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val state: StateFlow<EpisodeFormState> = combine(_state, baselineTriggers) { state, known -> state.copy(baselineTriggers = known) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, EpisodeFormState())

    init {
        viewModelScope.launch { _state.value = initialState() }
    }

    private suspend fun initialState(): EpisodeFormState = when (target) {
        EpisodeTarget.LogTab -> EpisodeFormState(draft = graph.loggingPreset())
        is EpisodeTarget.NewOn -> EpisodeFormState(draft = graph.loggingPreset().copy(startedAt = startOn(target.date)))
        is EpisodeTarget.Edit -> graph.episodes.find(target.id)
            ?.let { EpisodeFormState(draft = it.toDraft(graph.catalog.medications(), graph.catalog.triggers()), original = it) }
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

    fun toggleTrigger(trigger: Trigger) = edit { draft ->
        val noted = draft.triggers.any { it.id == trigger.id }
        draft.copy(triggers = if (noted) draft.triggers.filterNot { it.id == trigger.id } else draft.triggers + trigger)
    }

    fun clearTriggers() = edit { it.copy(triggers = emptyList()) }

    /** Native action sheet: the catalog triggers the card does not show yet, then a custom entry. */
    fun addTrigger() = viewModelScope.launch {
        val draft = _state.value.draft ?: return@launch
        val shown = (baselineTriggers.value + draft.triggers).map { it.id }.toSet()
        val candidates = graph.catalog.triggers().filter { it.id !in shown }
        val dialogs = graph.services.dialogs
        val choice = dialogs.chooseAction(
            title = getString(Res.string.common_add_trigger),
            message = getString(Res.string.episode_add_trigger_message),
            actions = candidates.map { SheetAction(it.name) } + SheetAction(getString(Res.string.episode_other_trigger_action)),
        ) ?: return@launch
        val trigger = if (choice < candidates.size) {
            candidates[choice]
        } else {
            val name = dialogs.prompt(
                title = getString(Res.string.episode_other_trigger_title),
                message = getString(Res.string.episode_other_trigger_message),
                placeholder = getString(Res.string.episode_other_trigger_placeholder),
                confirmTitle = getString(Res.string.common_add),
            )?.trim()
            if (name.isNullOrEmpty()) return@launch
            graph.catalog.addCustomTrigger(name)
        }
        edit { it.copy(triggers = it.triggers + trigger) }
    }

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
            title = getString(Res.string.common_add_medication),
            message = getString(Res.string.episode_add_medication_message),
            actions = candidates.map { SheetAction(it.displayName) } + SheetAction(getString(Res.string.episode_other_medication_action)),
        ) ?: return@launch
        val medication = if (choice < candidates.size) {
            candidates[choice]
        } else {
            val name = dialogs.prompt(
                title = getString(Res.string.episode_other_medication_title),
                message = getString(Res.string.common_medication_name_and_dose),
                placeholder = getString(Res.string.common_medication_placeholder),
                confirmTitle = getString(Res.string.common_add),
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
            title = getString(Res.string.episode_start),
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
                graph.services.dialogs.alert(
                    title = getString(Res.string.episode_missing_title),
                    message = getString(Res.string.episode_missing_message),
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
        title = getString(Res.string.common_date_at_time, DateText.fullDate(dateTime.date), DateText.time(dateTime)),
        message = getString(Res.string.episode_delete_message, episode.intensity.value),
        actions = listOf(SheetAction(getString(Res.string.common_delete_episode), destructive = true)),
    )
    return choice == 0
}
