package com.migratrack.app.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.migratrack.app.ui.icons.Symbols
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.domain.model.Diagnosis
import com.migratrack.domain.model.Medication
import com.migratrack.domain.model.MedicationRole
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.model.Trigger
import com.migratrack.domain.model.TriggerCategory

/** Icons and accent colors for catalog entries; the domain stays free of UI types. */
val Diagnosis.icon: ImageVector
    get() = when (this) {
        Diagnosis.MigraineWithoutAura -> Symbols.Adjust
        Diagnosis.MigraineWithAura -> Symbols.BlurOn
        Diagnosis.ChronicMigraine -> Symbols.Schedule
        Diagnosis.TensionType -> Symbols.Compress
        Diagnosis.Cluster -> Symbols.FlashOn
        Diagnosis.NotDiagnosed -> Symbols.Help
    }

val Symptom.icon: ImageVector
    get() = when (this) {
        Symptom.ScintillatingScotoma -> Symbols.Visibility
        Symptom.NeckStiffness -> Symbols.Straighten
        Symptom.Photophobia -> Symbols.LightMode
        Symptom.Nausea -> Symbols.SentimentVeryDissatisfied
        Symptom.Phonophobia -> Symbols.Hearing
        Symptom.Aphasia -> Symbols.RecordVoiceOver
        Symptom.Paresthesia -> Symbols.ElectricBolt
    }

/** Category colors follow the specification: sensory cyan, recovery green, stress pink, hormonal purple, diet orange. */
val TriggerCategory.accent: Color
    get() = when (this) {
        TriggerCategory.WeatherEnvironment -> MtColors.Cyan
        TriggerCategory.SleepPhysical -> MtColors.Green
        TriggerCategory.StressEmotions -> MtColors.Pink
        TriggerCategory.Hormonal -> MtColors.Purple
        TriggerCategory.DietConsumption -> MtColors.Orange
        TriggerCategory.Custom -> MtColors.Cyan
    }

val Trigger.icon: ImageVector
    get() = when (id) {
        "barometric-pressure" -> Symbols.Air
        "bright-light" -> Symbols.LightMode
        "heat-humidity" -> Symbols.Thermostat
        "loud-noise" -> Symbols.VolumeUp
        "poor-sleep" -> Symbols.Bed
        "dehydration" -> Symbols.WaterDrop
        "skipped-meals" -> Symbols.Restaurant
        "overexertion" -> Symbols.FitnessCenter
        "emotional-stress" -> Symbols.Psychology
        "screen-fatigue" -> Symbols.Devices
        "hormonal-cycle" -> Symbols.CalendarMonth
        "caffeine" -> Symbols.Coffee
        "aged-cheese" -> Symbols.LunchDining
        "alcohol" -> Symbols.WineBar
        "sweeteners" -> Symbols.Cookie
        else -> Symbols.Tune
    }

val Medication.icon: ImageVector
    get() = when {
        id == "electrolytes" -> Symbols.WaterDrop
        role == MedicationRole.Acute -> Symbols.Pill
        else -> Symbols.Medication
    }
