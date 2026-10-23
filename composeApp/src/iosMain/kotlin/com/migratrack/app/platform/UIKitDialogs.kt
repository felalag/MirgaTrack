package com.migratrack.app.platform

import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.common_cancel
import com.migratrack.app.generated.resources.common_ok
import com.migratrack.app.generated.resources.common_save
import com.migratrack.app.ui.getString
import kotlin.coroutines.resume
import kotlin.time.Instant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import platform.Foundation.NSDate
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIAction
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleCancel
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertActionStyleDestructive
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleActionSheet
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIBarButtonItem
import platform.UIKit.UIBarButtonSystemItem
import platform.UIKit.UIColor
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode
import platform.UIKit.UIDatePickerStyle
import platform.UIKit.UINavigationController
import platform.UIKit.UISheetPresentationControllerDetent
import platform.UIKit.UITextAutocapitalizationType
import platform.UIKit.UITextFieldViewMode
import platform.UIKit.UIViewController
import platform.UIKit.modalInPresentation
import platform.UIKit.navigationItem
import platform.UIKit.sheetPresentationController
import platform.UIKit.systemGroupedBackgroundColor

/** Alerts, prompts, and action sheets with UIAlertController, so they match the system exactly. */
class UIKitDialogs : SystemDialogs {

    override suspend fun confirm(title: String, message: String?, confirmTitle: String, destructive: Boolean): Boolean {
        val cancel = getString(Res.string.common_cancel)
        return present { resume ->
            UIAlertController.alertControllerWithTitle(title, message, UIAlertControllerStyleAlert).apply {
                val confirmAction = UIAlertAction.actionWithTitle(
                    confirmTitle,
                    if (destructive) UIAlertActionStyleDestructive else UIAlertActionStyleDefault,
                ) { resume(true) }
                addAction(UIAlertAction.actionWithTitle(cancel, UIAlertActionStyleCancel) { resume(false) })
                addAction(confirmAction)
                if (!destructive) preferredAction = confirmAction
            }
        } ?: false
    }

    override suspend fun alert(title: String, message: String?) {
        val ok = getString(Res.string.common_ok)
        present<Unit> { resume ->
            UIAlertController.alertControllerWithTitle(title, message, UIAlertControllerStyleAlert).apply {
                val okAction = UIAlertAction.actionWithTitle(ok, UIAlertActionStyleDefault) { resume(Unit) }
                addAction(okAction)
                preferredAction = okAction
            }
        }
    }

    override suspend fun prompt(
        title: String,
        message: String?,
        placeholder: String,
        initialValue: String,
        confirmTitle: String?,
    ): String? {
        val cancel = getString(Res.string.common_cancel)
        val confirm = confirmTitle ?: getString(Res.string.common_save)
        return present { resume ->
            UIAlertController.alertControllerWithTitle(title, message, UIAlertControllerStyleAlert).apply {
                addTextFieldWithConfigurationHandler { field ->
                    field?.placeholder = placeholder
                    field?.text = initialValue
                    field?.autocapitalizationType = UITextAutocapitalizationType.UITextAutocapitalizationTypeSentences
                    field?.clearButtonMode = UITextFieldViewMode.UITextFieldViewModeWhileEditing
                }
                addAction(UIAlertAction.actionWithTitle(cancel, UIAlertActionStyleCancel) { resume(null) })
                val save = UIAlertAction.actionWithTitle(confirm, UIAlertActionStyleDefault) {
                    val field = textFields?.firstOrNull() as? platform.UIKit.UITextField
                    resume(field?.text.orEmpty())
                }
                addAction(save)
                preferredAction = save
            }
        }
    }

    override suspend fun chooseAction(title: String?, message: String?, actions: List<SheetAction>): Int? {
        val cancel = getString(Res.string.common_cancel)
        return present { resume ->
            UIAlertController.alertControllerWithTitle(title, message, UIAlertControllerStyleActionSheet).apply {
                actions.forEachIndexed { index, action ->
                    val style = if (action.destructive) UIAlertActionStyleDestructive else UIAlertActionStyleDefault
                    addAction(UIAlertAction.actionWithTitle(action.title, style) { resume(index) })
                }
                addAction(UIAlertAction.actionWithTitle(cancel, UIAlertActionStyleCancel) { resume(null) })
            }
        }
    }

    override suspend fun pickDate(title: String, initial: Instant, latest: Instant, mode: DatePickerMode): Instant? =
        withContext(Dispatchers.Main) {
            val host = topViewController() ?: return@withContext null
            suspendCancellableCoroutine { continuation ->
                val picker = UIDatePicker().apply {
                    datePickerMode = when (mode) {
                        DatePickerMode.DateAndTime -> UIDatePickerMode.UIDatePickerModeDateAndTime
                        DatePickerMode.MonthAndYear -> UIDatePickerMode.UIDatePickerModeYearAndMonth
                    }
                    preferredDatePickerStyle = when (mode) {
                        DatePickerMode.DateAndTime -> UIDatePickerStyle.UIDatePickerStyleInline
                        DatePickerMode.MonthAndYear -> UIDatePickerStyle.UIDatePickerStyleWheels
                    }
                    maximumDate = latest.toNSDate()
                    date = minOf(initial, latest).toNSDate()
                    translatesAutoresizingMaskIntoConstraints = false
                }
                val content = UIViewController().apply {
                    this.title = title
                    view.backgroundColor = UIColor.systemGroupedBackgroundColor
                    view.addSubview(picker)
                    NSLayoutConstraint.activateConstraints(
                        listOf(
                            picker.topAnchor.constraintEqualToAnchor(view.safeAreaLayoutGuide.topAnchor),
                            picker.leadingAnchor.constraintEqualToAnchor(view.layoutMarginsGuide.leadingAnchor),
                            picker.trailingAnchor.constraintEqualToAnchor(view.layoutMarginsGuide.trailingAnchor),
                        ),
                    )
                }
                val sheet = UINavigationController(rootViewController = content)
                fun finish(value: Instant?) {
                    if (continuation.isActive) continuation.resume(value)
                    sheet.dismissViewControllerAnimated(true, completion = null)
                }
                content.navigationItem.leftBarButtonItem = UIBarButtonItem(
                    barButtonSystemItem = UIBarButtonSystemItem.UIBarButtonSystemItemCancel,
                    primaryAction = UIAction.actionWithHandler { finish(null) },
                )
                content.navigationItem.rightBarButtonItem = UIBarButtonItem(
                    barButtonSystemItem = UIBarButtonSystemItem.UIBarButtonSystemItemDone,
                    primaryAction = UIAction.actionWithHandler { finish(picker.date.toInstant()) },
                )
                // Only Cancel and Done close the sheet, so every way out resumes the caller.
                sheet.modalInPresentation = true
                sheet.sheetPresentationController?.apply {
                    val height = if (mode == DatePickerMode.DateAndTime) 540.0 else 290.0
                    detents = listOf(UISheetPresentationControllerDetent.customDetentWithIdentifier(null) { _ -> height })
                    prefersGrabberVisible = false
                }
                host.presentViewController(sheet, animated = true, completion = null)
                continuation.invokeOnCancellation { sheet.dismissViewControllerAnimated(true, completion = null) }
            }
        }

    /** Presents the alert built by [build] and suspends until one of its actions calls resume. */
    private suspend fun <T> present(build: (resume: (T?) -> Unit) -> UIAlertController): T? = withContext(Dispatchers.Main) {
        val host = topViewController() ?: return@withContext null
        suspendCancellableCoroutine { continuation ->
            val alert = build { value -> if (continuation.isActive) continuation.resume(value) }
            alert.anchorPopover(host)
            host.presentViewController(alert, animated = true, completion = null)
            continuation.invokeOnCancellation { alert.dismissViewControllerAnimated(true, completion = null) }
        }
    }
}

private fun Instant.toNSDate(): NSDate = NSDate.dateWithTimeIntervalSince1970(toEpochMilliseconds() / 1000.0)

private fun NSDate.toInstant(): Instant = Instant.fromEpochMilliseconds((timeIntervalSince1970 * 1000).toLong())
