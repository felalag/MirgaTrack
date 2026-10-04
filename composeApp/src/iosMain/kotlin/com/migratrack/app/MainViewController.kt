package com.migratrack.app

import androidx.compose.ui.window.ComposeUIViewController
import com.migratrack.app.di.AppGraph
import com.migratrack.app.platform.AppInfo
import com.migratrack.app.platform.HealthKitSleepDataSource
import com.migratrack.app.platform.PlatformServices
import com.migratrack.app.platform.UIKitDialogs
import com.migratrack.app.platform.UIKitFileSharing
import com.migratrack.data.AppleDatabase
import com.migratrack.data.LocalStore
import com.migratrack.export.pdf.UIKitPdfRenderer
import platform.Foundation.NSBundle
import platform.UIKit.UIViewController
import kotlin.experimental.ExperimentalNativeApi

/** One graph per process: the UI and the App Intents share it. */
internal val appGraph: AppGraph by lazy {
    AppGraph(
        PlatformServices(
            store = LocalStore(AppleDatabase.driver()),
            dialogs = UIKitDialogs(),
            files = UIKitFileSharing(),
            sleep = HealthKitSleepDataSource(),
            pdfRenderer = UIKitPdfRenderer(),
            appInfo = bundleAppInfo(),
        ),
    )
}

/** Entry point for SwiftUI's ContentView. */
fun MainViewController(): UIViewController = ComposeUIViewController { App(appGraph) }

@OptIn(ExperimentalNativeApi::class)
private fun bundleAppInfo(): AppInfo {
    val info = NSBundle.mainBundle.infoDictionary.orEmpty()
    return AppInfo(
        version = info["CFBundleShortVersionString"] as? String ?: "1.0",
        build = info["CFBundleVersion"] as? String ?: "1",
        isDebug = Platform.isDebugBinary,
    )
}
