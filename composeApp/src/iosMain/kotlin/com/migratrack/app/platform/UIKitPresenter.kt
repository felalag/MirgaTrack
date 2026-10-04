package com.migratrack.app.platform

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice
import platform.UIKit.UIUserInterfaceIdiomPad
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowScene
import platform.UIKit.popoverPresentationController

/** Finds the view controller that can present right now: the key window's root, then its presented chain. */
internal fun topViewController(): UIViewController? {
    val window = UIApplication.sharedApplication.connectedScenes
        .filterIsInstance<UIWindowScene>()
        .flatMap { scene -> scene.windows.filterIsInstance<UIWindow>() }
        .firstOrNull { it.isKeyWindow() }
    var top = window?.rootViewController
    while (top?.presentedViewController != null) top = top.presentedViewController
    return top
}

/**
 * iPad shows sheets as popovers and raises an exception without an anchor.
 * iPhone gets no anchor: since iOS 26 an anchored action sheet becomes a popover there too, instead of rising from the bottom.
 */
@OptIn(ExperimentalForeignApi::class)
internal fun UIViewController.anchorPopover(host: UIViewController) {
    if (UIDevice.currentDevice.userInterfaceIdiom != UIUserInterfaceIdiomPad) return
    val popover = popoverPresentationController ?: return
    popover.sourceView = host.view
    popover.sourceRect = host.view.bounds.useContents { CGRectMake(size.width / 2, size.height / 2, 0.0, 0.0) }
    popover.permittedArrowDirections = 0uL
}
