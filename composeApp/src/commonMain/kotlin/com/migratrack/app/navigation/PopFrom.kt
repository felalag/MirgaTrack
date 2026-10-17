package com.migratrack.app.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

/**
 * Pops [entry] only while it is the top screen and not the start destination.
 * A fast second tap on Back comes from the screen that still slides out. A plain popBackStack
 * then removes the screen below it, and at the root it leaves the NavHost empty.
 */
fun NavController.popFrom(entry: NavBackStackEntry) {
    if (currentBackStackEntry == entry && previousBackStackEntry != null) popBackStack()
}
