package com.migratrack.app.ui

import platform.Foundation.NSString
import platform.Foundation.localizedCompare

// Kotlin strings bridge to NSString on Apple platforms, so the cast succeeds although the compiler cannot prove it.
@Suppress("CAST_NEVER_SUCCEEDS")
actual fun compareForDisplay(a: String, b: String): Int = (a as NSString).localizedCompare(b).toInt()
