package com.migratrack.domain.usecase

import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.Episode
import com.migratrack.domain.model.EpisodeId
import com.migratrack.domain.model.LogSource
import com.migratrack.domain.model.MedicationCatalog
import com.migratrack.domain.model.MedicationIntake
import com.migratrack.domain.model.PainIntensity
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.repository.EpisodeRepository
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

/**
 * Fills the store with a year of plausible episodes, so a debug build can show
 * the charts as they appear in the design. Release builds do not expose it.
 */
class LoadSampleData(
    private val episodes: EpisodeRepository,
    private val clock: Clock,
) {
    suspend operator fun invoke(seed: Int = 24) {
        val random = Random(seed)
        val now = clock.now()
        val ibuprofen = MedicationCatalog.defaults.first { it.id == "ibuprofen-400" }
        val sumatriptan = MedicationCatalog.defaults.first { it.id == "sumatriptan-50" }
        var daysAgo = 2 + random.nextInt(3)
        while (daysAgo < 365) {
            val startedAt = now - daysAgo.days - random.nextInt(0, 14).hours - random.nextInt(0, 59).minutes
            val intensity = PainIntensity(random.nextInt(2, 10))
            val medications = buildList {
                if (random.nextInt(4) > 0) add(ibuprofen)
                if (intensity.value >= 7 && random.nextBoolean()) add(sumatriptan)
            }
            episodes.add(
                Episode(
                    id = EpisodeId("sample-$daysAgo"),
                    startedAt = startedAt,
                    intensity = intensity,
                    region = CranialRegion.entries.random(random),
                    symptoms = Symptom.entries.shuffled(random).take(random.nextInt(0, 4)).toSet(),
                    medications = medications.map {
                        MedicationIntake(it.id, it.name, it.dose, startedAt + random.nextInt(10, 90).minutes)
                    },
                    source = LogSource.App,
                ),
            )
            daysAgo += random.nextInt(5, 19)
        }
    }
}
