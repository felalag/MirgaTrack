package com.migratrack.app.platform

import com.migratrack.data.LocalStore
import com.migratrack.domain.platform.SleepDataSource
import com.migratrack.export.ExportFile
import com.migratrack.export.pdf.PdfRenderer
import kotlin.time.Instant

/**
 * Everything the shared app needs from the operating system.
 * iOS implements it in iosMain with UIKit and HealthKit; Android will add its own implementation.
 */
class PlatformServices(
    val store: LocalStore,
    val dialogs: SystemDialogs,
    val files: FileSharing,
    val sleep: SleepDataSource,
    val pdfRenderer: PdfRenderer,
    val appInfo: AppInfo,
)

/** Native alerts and action sheets, so prompts look like the platform and not like Material. */
interface SystemDialogs {
    /** Asks with Cancel and [confirmTitle]; returns true for [confirmTitle]. */
    suspend fun confirm(title: String, message: String?, confirmTitle: String, destructive: Boolean = false): Boolean

    /** Reports something with an OK button only. */
    suspend fun alert(title: String, message: String?)

    /** Returns the entered text, or null when the user cancels. A null [confirmTitle] shows "Save". */
    suspend fun prompt(
        title: String,
        message: String? = null,
        placeholder: String = "",
        initialValue: String = "",
        confirmTitle: String? = null,
    ): String?

    /** Shows an action sheet and returns the index of the chosen action, or null on cancel. */
    suspend fun chooseAction(title: String?, message: String? = null, actions: List<SheetAction>): Int?

    /** Shows the system date picker in a sheet and returns the picked moment, or null on cancel. */
    suspend fun pickDate(title: String, initial: Instant, latest: Instant, mode: DatePickerMode): Instant?
}

data class SheetAction(val title: String, val destructive: Boolean = false)

enum class DatePickerMode {
    /** A calendar with a time row, for the start of an episode. */
    DateAndTime,

    /** Month and year wheels, for the Insights calendar. */
    MonthAndYear,
}

interface FileSharing {
    /** Opens the platform document preview, which has its own share button. */
    suspend fun preview(file: ExportFile)

    suspend fun share(files: List<ExportFile>, text: String? = null)
}

data class AppInfo(
    val version: String,
    val build: String,
    val isDebug: Boolean,
    /** A tab name or "Settings" to open first, for the screenshot script; only Debug builds read it. */
    val startScreen: String? = null,
)
