package com.migratrack.app.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.migratrack.app.feature.episode.EpisodeEditorScreen
import com.migratrack.app.feature.episode.EpisodeTarget
import com.migratrack.app.feature.export.ExportScreen
import com.migratrack.app.feature.insights.InsightsScreen
import com.migratrack.app.feature.legal.LegalDocument
import com.migratrack.app.feature.legal.LegalScreen
import com.migratrack.app.feature.log.LogEpisodeScreen
import com.migratrack.app.feature.onboarding.BaselineEditor
import com.migratrack.app.feature.settings.SettingsScreen
import com.migratrack.app.feature.summary.SummaryScreen
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.episode_saved_banner
import com.migratrack.app.ui.components.Banner
import com.migratrack.app.ui.components.LocalBottomBarInset
import com.migratrack.app.ui.components.Tab
import com.migratrack.app.ui.components.TabBar
import com.migratrack.app.ui.components.TabBarHeight
import com.migratrack.app.ui.components.TabRail
import com.migratrack.app.ui.components.TabRailWidth
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.domain.model.EpisodeId
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Serializable
data object MainRoute

@Serializable
data object SettingsRoute

@Serializable
data class EditBaselineRoute(val step: Int)

/** [document] is a [LegalDocument] name; navigation on iOS has no built-in argument type for enums. */
@Serializable
data class LegalRoute(val document: String)

/** Edit Episode for [episodeId], or Log Episode on [date] (ISO) from the calendar. */
@Serializable
data class EpisodeRoute(val episodeId: String? = null, val date: String? = null) {
    fun target(): EpisodeTarget =
        episodeId?.let { EpisodeTarget.Edit(EpisodeId(it)) } ?: EpisodeTarget.NewOn(LocalDate.parse(requireNotNull(date)))
}

/**
 * Tabs with a shared push stack, as a UITabBarController hosting navigation controllers.
 * The tab bar floats over the NavHost, so a pushed screen slides in under it and the
 * system edge swipe pops it. Baseline editors hide the bar, like hidesBottomBarWhenPushed.
 * In a window wider than tall the bar is a [TabRail] beside the NavHost instead.
 */
@Composable
fun MainScaffold() {
    val navController = rememberNavController()
    var selectedTab by rememberSaveable { mutableStateOf(Tab.Summary) }
    var banner by remember { mutableStateOf<StringResource?>(null) }
    val entry by navController.currentBackStackEntryAsState()
    val tabBarVisible = entry?.destination?.hasRoute<EditBaselineRoute>() != true
    val bottomInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val endInset = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(LocalLayoutDirection.current)

    LaunchedEffect(banner) {
        if (banner != null) {
            delay(2_500)
            banner = null
        }
    }

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val useRail = maxWidth > maxHeight
        val railSpace = maxOf(TabRailWidth, endInset)
        val railWidth = if (useRail && tabBarVisible) railSpace else 0.dp
        val bottomBarInset = if (!useRail && tabBarVisible) TabBarHeight + bottomInset else 0.dp
        val railInsets = PaddingValues(end = railWidth)
        val selectTab: (Tab) -> Unit = { tab ->
            selectedTab = tab
            navController.popBackStack(MainRoute, inclusive = false)
        }

        CompositionLocalProvider(LocalBottomBarInset provides bottomBarInset) {
            NavHost(
                navController,
                startDestination = MainRoute,
                modifier = Modifier.padding(railInsets).consumeWindowInsets(railInsets),
            ) {
                composable<MainRoute> { backStackEntry ->
                    TabContent(
                        tab = selectedTab,
                        openSettings = { navController.navigateFrom(backStackEntry, SettingsRoute) },
                        openLog = { selectedTab = Tab.Log },
                        openEpisode = { route -> navController.navigateFrom(backStackEntry, route) },
                        onEpisodeSaved = {
                            selectedTab = Tab.Summary
                            banner = Res.string.episode_saved_banner
                        },
                    )
                }
                composable<EpisodeRoute> { backStackEntry ->
                    EpisodeEditorScreen(
                        target = backStackEntry.toRoute<EpisodeRoute>().target(),
                        onBack = { navController.popFrom(backStackEntry) },
                        onDone = { message ->
                            navController.popFrom(backStackEntry)
                            banner = message
                        },
                    )
                }
                composable<SettingsRoute> { backStackEntry ->
                    SettingsScreen(
                        onBack = { navController.popFrom(backStackEntry) },
                        onEditBaseline = { step -> navController.navigateFrom(backStackEntry, EditBaselineRoute(step)) },
                        onOpenDocument = { document -> navController.navigateFrom(backStackEntry, LegalRoute(document.name)) },
                    )
                }
                composable<LegalRoute> { backStackEntry ->
                    LegalScreen(LegalDocument.valueOf(backStackEntry.toRoute<LegalRoute>().document), onBack = { navController.popFrom(backStackEntry) })
                }
                composable<EditBaselineRoute> { backStackEntry ->
                    BaselineEditor(
                        step = backStackEntry.toRoute<EditBaselineRoute>().step,
                        onClose = { navController.popFrom(backStackEntry) },
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = tabBarVisible && !useRail,
            enter = slideInVertically { it },
            exit = slideOutVertically { it },
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            TabBar(selected = selectedTab, onSelect = selectTab)
        }

        AnimatedVisibility(
            visible = tabBarVisible && useRail,
            enter = slideInHorizontally { it },
            exit = slideOutHorizontally { it },
            modifier = Modifier.align(Alignment.CenterEnd),
        ) {
            TabRail(selected = selectedTab, onSelect = selectTab, modifier = Modifier.width(railSpace))
        }

        AnimatedVisibility(
            visible = banner != null,
            enter = slideInVertically { -it } + fadeIn(),
            exit = slideOutVertically { -it } + fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(railInsets)
                .consumeWindowInsets(railInsets)
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 8.dp),
        ) {
            Banner(banner?.let { stringResource(it) }.orEmpty(), Symbols.CheckCircle, MtColors.Cyan)
        }
    }
}

/** Keeps each tab's scroll position while another tab is shown. */
@Composable
private fun TabContent(
    tab: Tab,
    openSettings: () -> Unit,
    openLog: () -> Unit,
    openEpisode: (EpisodeRoute) -> Unit,
    onEpisodeSaved: () -> Unit,
) {
    val stateHolder = rememberSaveableStateHolder()
    val editEpisode = { id: EpisodeId -> openEpisode(EpisodeRoute(episodeId = id.value)) }
    stateHolder.SaveableStateProvider(tab.name) {
        when (tab) {
            Tab.Summary -> SummaryScreen(onOpenSettings = openSettings, onLogEpisode = openLog, onEditEpisode = editEpisode)
            Tab.Log -> LogEpisodeScreen(onOpenSettings = openSettings, onSaved = onEpisodeSaved)
            Tab.Insights -> InsightsScreen(
                onOpenSettings = openSettings,
                onEditEpisode = editEpisode,
                onLogOn = { date -> openEpisode(EpisodeRoute(date = date.toString())) },
            )
            Tab.Export -> ExportScreen(onOpenSettings = openSettings)
        }
    }
}
