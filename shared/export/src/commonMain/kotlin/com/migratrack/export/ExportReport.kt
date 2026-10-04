package com.migratrack.export

import com.migratrack.domain.analytics.DateText
import com.migratrack.domain.repository.CatalogRepository
import com.migratrack.domain.repository.EpisodeRepository
import com.migratrack.domain.repository.ProfileRepository
import com.migratrack.export.pdf.PdfRenderer
import com.migratrack.export.pdf.ReportPdf
import com.migratrack.export.report.ClinicalReport
import com.migratrack.export.report.ClinicalReportBuilder
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
        )
    }

    suspend fun pdf(): ExportFile = withContext(dispatcher) {
        val report = report()
        ExportFile(fileName(report, "pdf"), "application/pdf", ReportPdf.render(report, pdfRenderer))
    }

    suspend fun xlsx(): ExportFile = withContext(dispatcher) {
        val report = report()
        ExportFile(
            fileName(report, "xlsx"),
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            ReportWorkbook.write(report),
        )
    }

    private fun fileName(report: ClinicalReport, extension: String) =
        "MigraTrack-Report-${DateText.iso(report.to)}.$extension"
}
