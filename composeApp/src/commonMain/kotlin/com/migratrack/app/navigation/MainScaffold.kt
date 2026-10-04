package com.migratrack.app.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.migratrack.app.feature.export.ExportScreen
import com.migratrack.app.feature.insights.InsightsScreen
import com.migratrack.app.feature.log.LogEpisodeScreen
import com.migratrack.app.feature.onboarding.BaselineEditor
import com.migratrack.app.feature.settings.SettingsScreen
import com.migratrack.app.feature.summary.SummaryScreen
import com.migratrack.app.ui.components.Banner
import com.migratrack.app.ui.components.LocalBottomBarInset
import com.migratrack.app.ui.components.Tab
import com.migratrack.app.ui.components.TabBar
import com.migratrack.app.ui.components.TabBarHeight
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

@Serializable
data object SettingsRoute

@Serializable
data class EditBaselineRoute(val step: Int)

/**
 * Tabs with a shared push stack, as a UITabBarController hosting navigation controllers.
 * The tab bar floats over the NavHost, so a pushed screen slides in under it and the
 * system edge swipe pops it. Baseline editors hide the bar, like hidesBottomBarWhenPushed.
 */
@Composable
fun MainScaffold() {
    val navController = rememberNavController()
    var selectedTab by rememberSaveable { mutableStateOf(Tab.Summary) }
    var banner by remember { mutableStateOf<String?>(null) }
    val entry by navController.currentBackStackEntryAsState()
    val tabBarVisible = entry?.destination?.hasRoute<EditBaselineRoute>() != true
    val bottomInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    LaunchedEffect(banner) {
        if (banner != null) {
            delay(2_500)
            banner = null
        }
    }

    Box(Modifier.fillMaxSize()) {
        CompositionLocalProvider(LocalBottomBarInset provides if (tabBarVisible) TabBarHeight + bottomInset else 0.dp) {
            NavHost(navController, startDestination = MainRoute) {
                composable<MainRoute> {
                    TabContent(
                        tab = selectedTab,
                        openSettings = { navController.navigate(SettingsRoute) },
                        openLog = { selectedTab = Tab.Log },
                        onEpisodeSaved = {
                            selectedTab = Tab.Summary
                            banner = "Episode saved. Take it easy and stay in low light."
                        },
                    )
                }
                composable<SettingsRoute> {
                    SettingsScreen(
                        onBack = { navController.popBackStack() },
                        onEditBaseline = { step -> navController.navigate(EditBaselineRoute(step)) },
                    )
                }
                composable<EditBaselineRoute> { backStackEntry ->
                    BaselineEditor(
                        step = backStackEntry.toRoute<EditBaselineRoute>().step,
                        onClose = { navController.popBackStack() },
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = tabBarVisible,
            enter = slideInVertically { it },
            exit = slideOutVertically { it },
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            TabBar(
                selected = selectedTab,
                onSelect = { tab ->
                    selectedTab = tab
                    navController.popBackStack(MainRoute, inclusive = false)
                },
            )
        }

        AnimatedVisibility(
            visible = banner != null,
            enter = slideInVertically { -it } + fadeIn(),
            exit = slideOutVertically { -it } + fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 8.dp),
        ) {
            Banner(banner.orEmpty(), Symbols.CheckCircle, MtColors.Cyan)
        }
    }
}

/** Keeps each tab's scroll position while another tab is shown. */
@Composable
private fun TabContent(tab: Tab, openSettings: () -> Unit, openLog: () -> Unit, onEpisodeSaved: () -> Unit) {
    val stateHolder = rememberSaveableStateHolder()
    stateHolder.SaveableStateProvider(tab.name) {
        when (tab) {
            Tab.Summary -> SummaryScreen(onOpenSettings = openSettings, onLogEpisode = openLog)
            Tab.Log -> LogEpisodeScreen(onOpenSettings = openSettings, onSaved = onEpisodeSaved)
            Tab.Insights -> InsightsScreen(onOpenSettings = openSettings)
            Tab.Export -> ExportScreen(onOpenSettings = openSettings)
        }
    }
}
