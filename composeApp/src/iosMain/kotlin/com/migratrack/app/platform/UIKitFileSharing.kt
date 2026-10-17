package com.migratrack.app.platform

import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.export_write_failed
import com.migratrack.export.ExportFile
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.getString
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.create
import platform.Foundation.writeToURL
import platform.QuickLook.QLPreviewController
import platform.QuickLook.QLPreviewControllerDataSourceProtocol
import platform.QuickLook.QLPreviewItemProtocol
import platform.UIKit.UIActivityViewController
import platform.darwin.NSInteger
import platform.darwin.NSObject

/** Writes exports to the temporary folder, then hands them to Quick Look or the share sheet. */
class UIKitFileSharing : FileSharing {
    // QLPreviewController holds its data source weakly; this keeps it alive while the preview is open.
    private var previewSource: PreviewSource? = null

    override suspend fun preview(file: ExportFile) {
        val url = write(file)
        withContext(Dispatchers.Main) {
            val host = topViewController() ?: return@withContext
            val source = PreviewSource(url)
            previewSource = source
            val preview = QLPreviewController()
            preview.dataSource = source
            host.presentViewController(preview, animated = true, completion = null)
        }
    }

    override suspend fun share(files: List<ExportFile>, text: String?) {
        val urls = files.map { write(it) }
        withContext(Dispatchers.Main) {
            val host = topViewController() ?: return@withContext
            val items: List<Any> = listOfNotNull(text) + urls
            val sheet = UIActivityViewController(activityItems = items, applicationActivities = null)
            sheet.anchorPopover(host)
            host.presentViewController(sheet, animated = true, completion = null)
        }
    }

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    private suspend fun write(file: ExportFile): NSURL = withContext(Dispatchers.IO) {
        val folder = NSTemporaryDirectory() + "exports"
        NSFileManager.defaultManager.createDirectoryAtPath(folder, withIntermediateDirectories = true, attributes = null, error = null)
        val url = NSURL.fileURLWithPath("$folder/${file.fileName}")
        val data = file.bytes.usePinned { pinned -> NSData.create(bytes = pinned.addressOf(0), length = file.bytes.size.toULong()) }
        check(data.writeToURL(url, atomically = true)) { getString(Res.string.export_write_failed, file.fileName) }
        url
    }

    private class PreviewSource(url: NSURL) : NSObject(), QLPreviewControllerDataSourceProtocol {
        // NSURL adopts QLPreviewItem only through a QuickLook category, which Kotlin/Native cannot cast to.
        private val item = PreviewItem(url)

        override fun numberOfPreviewItemsInPreviewController(controller: QLPreviewController): NSInteger = 1

        override fun previewController(controller: QLPreviewController, previewItemAtIndex: NSInteger): QLPreviewItemProtocol = item
    }

    private class PreviewItem(private val url: NSURL) : NSObject(), QLPreviewItemProtocol {
        override fun previewItemURL(): NSURL = url
    }
}
