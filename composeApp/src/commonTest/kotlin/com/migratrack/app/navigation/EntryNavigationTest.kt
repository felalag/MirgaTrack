package com.migratrack.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertTrue

class EntryNavigationTest {
    @Serializable private data object Root
    @Serializable private data object First
    @Serializable private data object Second

    private fun navController() = NavController().apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        graph = createGraph(startDestination = Root) {
            composable<Root> {}
            composable<First> {}
            composable<Second> {}
        }
    }

    @Test
    fun secondBackFromTheLeavingScreenKeepsTheRoot() {
        val nav = navController()
        nav.navigate(First)
        val first = nav.currentBackStackEntry!!

        nav.popFrom(first)
        nav.popFrom(first)

        assertTrue(nav.currentDestination?.hasRoute<Root>() == true)
    }

    @Test
    fun secondBackFromTheLeavingScreenKeepsTheScreenBelow() {
        val nav = navController()
        nav.navigate(First)
        nav.navigate(Second)
        val second = nav.currentBackStackEntry!!

        nav.popFrom(second)
        nav.popFrom(second)

        assertTrue(nav.currentDestination?.hasRoute<First>() == true)
    }

    @Test
    fun backOnTheStartDestinationDoesNothing() {
        val nav = navController()

        nav.popFrom(nav.currentBackStackEntry!!)

        assertTrue(nav.currentDestination?.hasRoute<Root>() == true)
    }

    @Test
    fun secondTapOnTheLeavingScreenDoesNotPushTwice() {
        val nav = navController()
        val root = nav.currentBackStackEntry!!

        nav.navigateFrom(root, First)
        nav.navigateFrom(root, First)
        nav.popFrom(nav.currentBackStackEntry!!)

        assertTrue(nav.currentDestination?.hasRoute<Root>() == true)
    }

    @Test
    fun tapOnTheTopScreenPushes() {
        val nav = navController()

        nav.navigateFrom(nav.currentBackStackEntry!!, First)
        nav.navigateFrom(nav.currentBackStackEntry!!, Second)

        assertTrue(nav.currentDestination?.hasRoute<Second>() == true)
    }
}
