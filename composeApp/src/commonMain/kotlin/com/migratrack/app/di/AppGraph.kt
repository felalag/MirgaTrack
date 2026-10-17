package com.migratrack.app.di

import androidx.compose.runtime.staticCompositionLocalOf
import com.migratrack.app.platform.PlatformServices
import com.migratrack.app.ui.loadCatalogNames
import com.migratrack.domain.repository.CatalogRepository
import com.migratrack.domain.repository.EpisodeRepository
import com.migratrack.domain.repository.ProfileRepository
import com.migratrack.domain.usecase.CompleteOnboarding
import com.migratrack.domain.usecase.GetLoggingPreset
import com.migratrack.domain.usecase.LoadSampleData
import com.migratrack.domain.usecase.LogEpisode
import com.migratrack.domain.usecase.QuickLogEpisode
import com.migratrack.domain.usecase.UpdateEpisode
import com.migratrack.export.ExportReport
import com.migratrack.sync.PhoneSync
import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

/** Hand-wired object graph. One instance lives for the whole process. */
class AppGraph(
    val services: PlatformServices,
    val clock: Clock = Clock.System,
) {
    val timeZone: () -> TimeZone = { TimeZone.currentSystemDefault() }

    val episodes: EpisodeRepository get() = services.store.episodes
    val profiles: ProfileRepository get() = services.store.profiles
    val catalog: CatalogRepository get() = services.store.catalog

    val logEpisode = LogEpisode(episodes, clock)
    val updateEpisode = UpdateEpisode(episodes, clock)
    val loggingPreset = GetLoggingPreset(profiles, catalog)
    val quickLog = QuickLogEpisode(profiles, loggingPreset, logEpisode)
    val completeOnboarding = CompleteOnboarding(profiles, clock, timeZone)
    val loadSampleData = LoadSampleData(episodes, profiles, clock, timeZone)
    val exportReport = ExportReport(episodes, profiles, catalog, clock, timeZone, services.pdfRenderer, names = ::loadCatalogNames)
    val phoneSync = PhoneSync(episodes, profiles, catalog, loggingPreset, services.store.documents, services.store.eraser, clock)

    fun today(): LocalDate = clock.todayIn(timeZone())
}

val LocalAppGraph = staticCompositionLocalOf<AppGraph> { error("AppGraph is not provided") }
