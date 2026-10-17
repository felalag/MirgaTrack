package com.migratrack.app.feature.export

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.migratrack.app.di.AppGraph
import com.migratrack.app.di.LocalAppGraph
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.common_possible_triggers
import com.migratrack.app.generated.resources.export_episodes_recorded
import com.migratrack.app.generated.resources.export_episodes_unit
import com.migratrack.app.generated.resources.export_excel
import com.migratrack.app.generated.resources.export_failed_message
import com.migratrack.app.generated.resources.export_failed_title
import com.migratrack.app.generated.resources.export_include
import com.migratrack.app.generated.resources.export_medications
import com.migratrack.app.generated.resources.export_medications_subtitle
import com.migratrack.app.generated.resources.export_pain_charts
import com.migratrack.app.generated.resources.export_pain_charts_subtitle
import com.migratrack.app.generated.resources.export_pdf
import com.migratrack.app.generated.resources.export_preparing_excel
import com.migratrack.app.generated.resources.export_preparing_pdf
import com.migratrack.app.generated.resources.export_preparing_report
import com.migratrack.app.generated.resources.export_range_title
import com.migratrack.app.generated.resources.export_share
import com.migratrack.app.generated.resources.export_share_text
import com.migratrack.app.generated.resources.export_symptoms
import com.migratrack.app.generated.resources.export_symptoms_subtitle
import com.migratrack.app.generated.resources.export_triggers_subtitle
import com.migratrack.app.generated.resources.tab_export
import com.migratrack.app.platform.SheetAction
import com.migratrack.app.ui.components.GroupedSection
import com.migratrack.app.ui.components.ListDivider
import com.migratrack.app.ui.components.ListRow
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.Page
import com.migratrack.app.ui.components.PrimaryButton
import com.migratrack.app.ui.components.RowTrailing
import com.migratrack.app.ui.components.SecondaryButton
import com.migratrack.app.ui.components.pressable
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import com.migratrack.domain.analytics.DateText
import com.migratrack.domain.model.ExportPreferences
import com.migratrack.domain.model.ExportRange
import com.migratrack.export.ExportFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getPluralString
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

enum class ExportJob(val progressLabel: StringResource) {
    Pdf(Res.string.export_preparing_pdf),
    Excel(Res.string.export_preparing_excel),
    Share(Res.string.export_preparing_report),
}

data class ExportUiState(
    val episodeCount: Int,
    val dateRange: String,
    val preferences: ExportPreferences,
    val running: ExportJob?,
)

class ExportViewModel(private val graph: AppGraph) : ViewModel() {
    private val running = MutableStateFlow<ExportJob?>(null)

    val state: StateFlow<ExportUiState?> = combine(
        graph.episodes.observeAll(),
        graph.profiles.observeExportPreferences(),
        running,
    ) { episodes, preferences, job ->
        val today = graph.today()
        val from = today.minus(DatePeriod(days = preferences.range.days - 1))
        val zone = graph.timeZone()
        ExportUiState(
            episodeCount = episodes.count { it.startedAt.toLocalDateTime(zone).date in from..today },
            dateRange = DateText.range(from, today),
            preferences = preferences,
            running = job,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun update(change: (ExportPreferences) -> ExportPreferences) = viewModelScope.launch {
        val current = state.value?.preferences ?: return@launch
        graph.profiles.saveExportPreferences(change(current))
    }

    fun chooseRange() = viewModelScope.launch {
        val ranges = ExportRange.entries
        val index = graph.services.dialogs.chooseAction(
            title = getString(Res.string.export_range_title),
            actions = ranges.map { SheetAction(it.title) },
        ) ?: return@launch
        update { it.copy(range = ranges[index]) }
    }

    fun exportPdf() = run(ExportJob.Pdf) { graph.services.files.preview(graph.exportReport.pdf()) }

    fun exportExcel() = run(ExportJob.Excel) { graph.services.files.share(listOf(graph.exportReport.xlsx())) }

    fun share() = run(ExportJob.Share) {
        val pdf: ExportFile = graph.exportReport.pdf()
        val summary = state.value?.let {
            getPluralString(Res.plurals.export_share_text, it.episodeCount, it.episodeCount, it.dateRange)
        }
        graph.services.files.share(listOf(pdf), summary)
    }

    private fun run(job: ExportJob, block: suspend () -> Unit) = viewModelScope.launch {
        if (running.value != null) return@launch
        running.value = job
        try {
            block()
        } catch (failure: Exception) {
            graph.services.dialogs.alert(
                title = getString(Res.string.export_failed_title),
                message = failure.message ?: getString(Res.string.export_failed_message),
            )
        } finally {
            running.value = null
        }
    }
}

@Composable
fun ExportScreen(onOpenSettings: () -> Unit) {
    val graph = LocalAppGraph.current
    val viewModel = viewModel { ExportViewModel(graph) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Page(title = stringResource(Res.string.tab_export), onSettings = onOpenSettings) {
        val current = state ?: return@Page
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
            StatTile(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.Bottom) {
                    MtText(current.episodeCount.toString(), MtTheme.type.largeTitle)
                    Spacer(Modifier.width(MtSpacing.xs))
                    MtText(pluralStringResource(Res.plurals.export_episodes_unit, current.episodeCount), MtTheme.type.subhead, color = MtColors.Caption, modifier = Modifier.padding(bottom = 5.dp))
                }
                MtText(stringResource(Res.string.export_episodes_recorded), MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal), color = MtColors.TextSecondary)
            }
            StatTile(Modifier.weight(1f).pressable(role = Role.Button, pressedScale = 0.97f, onClick = viewModel::chooseRange)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    MtText(current.preferences.range.title, MtTheme.type.headline.copy(fontWeight = FontWeight.Bold), modifier = Modifier.weight(1f, fill = false))
                    com.migratrack.app.ui.icons.Icon(Symbols.ChevronRight, tint = MtColors.TextSecondary, size = 18.dp)
                }
                MtText(current.dateRange, MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal), color = MtColors.TextSecondary)
            }
        }

        GroupedSection(stringResource(Res.string.export_include), background = MtColors.CardRaised) {
            val preferences = current.preferences
            ListRow(
                title = stringResource(Res.string.export_medications),
                subtitle = stringResource(Res.string.export_medications_subtitle),
                icon = Symbols.Medication,
                iconTint = MtColors.BrandLabel,
                iconBackground = MtColors.Control,
                trailing = RowTrailing.Toggle(preferences.includeMedications) { on -> viewModel.update { it.copy(includeMedications = on) } },
            )
            ListDivider(inset = false)
            ListRow(
                title = stringResource(Res.string.export_symptoms),
                subtitle = stringResource(Res.string.export_symptoms_subtitle),
                icon = Symbols.Visibility,
                iconTint = MtColors.Cyan,
                iconBackground = MtColors.Control,
                trailing = RowTrailing.Toggle(preferences.includeSymptoms) { on -> viewModel.update { it.copy(includeSymptoms = on) } },
            )
            ListDivider(inset = false)
            ListRow(
                title = stringResource(Res.string.common_possible_triggers),
                subtitle = stringResource(Res.string.export_triggers_subtitle),
                icon = Symbols.Bolt,
                iconTint = MtColors.Purple,
                iconBackground = MtColors.Control,
                trailing = RowTrailing.Toggle(preferences.includeTriggers) { on -> viewModel.update { it.copy(includeTriggers = on) } },
            )
            ListDivider(inset = false)
            ListRow(
                title = stringResource(Res.string.export_pain_charts),
                subtitle = stringResource(Res.string.export_pain_charts_subtitle),
                icon = Symbols.StackedLineChart,
                iconTint = MtColors.Pink,
                iconBackground = MtColors.Control,
                trailing = RowTrailing.Toggle(preferences.includePainCharts) { on -> viewModel.update { it.copy(includePainCharts = on) } },
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
            val running = current.running
            PrimaryButton(
                text = stringResource(if (running == ExportJob.Pdf) running.progressLabel else Res.string.export_pdf),
                icon = Symbols.Download,
                enabled = running == null,
                onClick = viewModel::exportPdf,
            )
            SecondaryButton(
                text = stringResource(if (running == ExportJob.Excel) running.progressLabel else Res.string.export_excel),
                icon = Symbols.TableView,
                iconTint = MtColors.Green,
                enabled = running == null,
                onClick = viewModel::exportExcel,
            )
            SecondaryButton(
                text = stringResource(if (running == ExportJob.Share) running.progressLabel else Res.string.export_share),
                icon = Symbols.IosShare,
                iconTint = MtColors.Cyan,
                outlined = true,
                enabled = running == null,
                onClick = viewModel::share,
            )
        }
    }
}

@Composable
private fun StatTile(modifier: Modifier, content: @Composable () -> Unit) {
    Column(
        modifier
            .heightIn(min = 96.dp)
            .clip(RoundedCornerShape(MtRadius.lg))
            .background(MtColors.CardRaised)
            .padding(MtSpacing.md),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
    ) { content() }
}
