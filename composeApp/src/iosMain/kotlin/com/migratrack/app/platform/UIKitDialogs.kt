package com.migratrack.app.platform

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleCancel
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertActionStyleDestructive
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleActionSheet
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UITextAutocapitalizationType
import platform.UIKit.UITextFieldViewMode
import kotlin.coroutines.resume

/** Alerts, prompts, and action sheets with UIAlertController, so they match the system exactly. */
class UIKitDialogs : SystemDialogs {

    override suspend fun confirm(title: String, message: String?, confirmTitle: String, destructive: Boolean): Boolean =
        present { resume ->
            UIAlertController.alertControllerWithTitle(title, message, UIAlertControllerStyleAlert).apply {
                val confirmAction = UIAlertAction.actionWithTitle(
                    confirmTitle,
                    if (destructive) UIAlertActionStyleDestructive else UIAlertActionStyleDefault,
                ) { resume(true) }
                if (destructive || confirmTitle != "OK") {
                    addAction(UIAlertAction.actionWithTitle("Cancel", UIAlertActionStyleCancel) { resume(false) })
                }
                addAction(confirmAction)
                if (!destructive) preferredAction = confirmAction
            }
        } ?: false

    override suspend fun prompt(
        title: String,
        message: String?,
        placeholder: String,
        initialValue: String,
        confirmTitle: String,
    ): String? = present { resume ->
        UIAlertController.alertControllerWithTitle(title, message, UIAlertControllerStyleAlert).apply {
            addTextFieldWithConfigurationHandler { field ->
                field?.placeholder = placeholder
                field?.text = initialValue
                field?.autocapitalizationType = UITextAutocapitalizationType.UITextAutocapitalizationTypeSentences
                field?.clearButtonMode = UITextFieldViewMode.UITextFieldViewModeWhileEditing
            }
            addAction(UIAlertAction.actionWithTitle("Cancel", UIAlertActionStyleCancel) { resume(null) })
            val save = UIAlertAction.actionWithTitle(confirmTitle, UIAlertActionStyleDefault) {
                val field = textFields?.firstOrNull() as? platform.UIKit.UITextField
                resume(field?.text.orEmpty())
            }
            addAction(save)
            preferredAction = save
        }
    }

    override suspend fun chooseAction(title: String?, message: String?, actions: List<SheetAction>): Int? = present { resume ->
        UIAlertController.alertControllerWithTitle(title, message, UIAlertControllerStyleActionSheet).apply {
            actions.forEachIndexed { index, action ->
                val style = if (action.destructive) UIAlertActionStyleDestructive else UIAlertActionStyleDefault
                addAction(UIAlertAction.actionWithTitle(action.title, style) { resume(index) })
            }
            addAction(UIAlertAction.actionWithTitle("Cancel", UIAlertActionStyleCancel) { resume(null) })
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
