package com.migratrack.export

import com.migratrack.domain.repository.CatalogRepository
import com.migratrack.domain.repository.EpisodeRepository
import com.migratrack.domain.repository.ProfileRepository
import com.migratrack.export.format.DateFormats
import com.migratrack.export.format.NumberFormats
import com.migratrack.export.pdf.PdfRenderer
import com.migratrack.export.pdf.ReportPdf
import com.migratrack.export.report.CatalogNames
import com.migratrack.export.report.ClinicalReport
import com.migratrack.export.report.ClinicalReportBuilder
import com.migratrack.export.report.ReportStrings
import com.migratrack.export.xlsx.ReportWorkbook
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

class ExportFile(val fileName: String, val mimeType: String, val bytes: ByteArray)

/** Builds the export files from the current data and the saved export toggles. */
class ExportReport(
    private val episodes: EpisodeRepository,
    private val profiles: ProfileRepository,
    private val catalog: CatalogRepository,
    private val clock: Clock,
    private val timeZone: () -> TimeZone,
    private val pdfRenderer: PdfRenderer,
    /** Loads the names in the language of the app; it runs before each export, so a language change applies. */
    private val names: suspend () -> CatalogNames,
    /** Loads the report texts in the language of the app, before each export like [names]. */
    private val strings: suspend () -> ReportStrings,
    private val dates: DateFormats,
    private val numbers: NumberFormats,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun report(): ClinicalReport {
        val zone = timeZone()
        return ClinicalReportBuilder.build(
            episodes = episodes.observeAll().first(),
            baseline = profiles.baseline(),
            triggers = catalog.triggers(),
            medications = catalog.medications(),
            user = profiles.user(),
            options = profiles.observeExportPreferences().first(),
            today = clock.todayIn(zone),
            timeZone = zone,
            names = names(),
        )
    }

    suspend fun pdf(): ExportFile = withContext(dispatcher) {
        val report = report()
        ExportFile(fileName(report, "pdf"), "application/pdf", ReportPdf.render(report, pdfRenderer, strings(), dates, numbers))
    }

    suspend fun xlsx(): ExportFile = withContext(dispatcher) {
        val report = report()
        ExportFile(
            fileName(report, "xlsx"),
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            ReportWorkbook.write(report, strings()),
        )
    }

    private fun fileName(report: ClinicalReport, extension: String) =
        "MigraTrack-Report-${report.to}.$extension"
}
