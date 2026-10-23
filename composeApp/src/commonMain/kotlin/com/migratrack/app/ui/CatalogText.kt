package com.migratrack.app.ui

import androidx.compose.runtime.Composable
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.diagnosis_chronic_migraine
import com.migratrack.app.generated.resources.diagnosis_chronic_migraine_subtitle
import com.migratrack.app.generated.resources.diagnosis_cluster
import com.migratrack.app.generated.resources.diagnosis_cluster_subtitle
import com.migratrack.app.generated.resources.diagnosis_migraine_with_aura
import com.migratrack.app.generated.resources.diagnosis_migraine_with_aura_subtitle
import com.migratrack.app.generated.resources.diagnosis_migraine_without_aura
import com.migratrack.app.generated.resources.diagnosis_migraine_without_aura_subtitle
import com.migratrack.app.generated.resources.diagnosis_not_diagnosed
import com.migratrack.app.generated.resources.diagnosis_not_diagnosed_subtitle
import com.migratrack.app.generated.resources.diagnosis_tension_type
import com.migratrack.app.generated.resources.diagnosis_tension_type_subtitle
import com.migratrack.app.generated.resources.duration_four_to_twelve
import com.migratrack.app.generated.resources.duration_one_to_three_days
import com.migratrack.app.generated.resources.duration_twelve_to_twenty_four
import com.migratrack.app.generated.resources.duration_two_to_four
import com.migratrack.app.generated.resources.export_range_last_12_months
import com.migratrack.app.generated.resources.export_range_last_30_days
import com.migratrack.app.generated.resources.export_range_last_6_months
import com.migratrack.app.generated.resources.export_range_last_90_days
import com.migratrack.app.generated.resources.frequency_eight_plus
import com.migratrack.app.generated.resources.frequency_five_to_eight
import com.migratrack.app.generated.resources.frequency_less_than_one
import com.migratrack.app.generated.resources.frequency_one_to_four
import com.migratrack.app.generated.resources.medication_acetaminophen_1000
import com.migratrack.app.generated.resources.medication_acetaminophen_1000_class
import com.migratrack.app.generated.resources.medication_amitriptyline_10
import com.migratrack.app.generated.resources.medication_amitriptyline_10_class
import com.migratrack.app.generated.resources.medication_custom_class
import com.migratrack.app.generated.resources.medication_dose_milligrams
import com.migratrack.app.generated.resources.medication_electrolytes
import com.migratrack.app.generated.resources.medication_electrolytes_class
import com.migratrack.app.generated.resources.medication_erenumab_70
import com.migratrack.app.generated.resources.medication_erenumab_70_class
import com.migratrack.app.generated.resources.medication_ibuprofen_400
import com.migratrack.app.generated.resources.medication_ibuprofen_400_class
import com.migratrack.app.generated.resources.medication_magnesium_400
import com.migratrack.app.generated.resources.medication_magnesium_400_class
import com.migratrack.app.generated.resources.medication_metoclopramide_10
import com.migratrack.app.generated.resources.medication_metoclopramide_10_class
import com.migratrack.app.generated.resources.medication_name_and_dose
import com.migratrack.app.generated.resources.medication_naproxen_500
import com.migratrack.app.generated.resources.medication_naproxen_500_class
import com.migratrack.app.generated.resources.medication_propranolol_40
import com.migratrack.app.generated.resources.medication_propranolol_40_class
import com.migratrack.app.generated.resources.medication_riboflavin_400
import com.migratrack.app.generated.resources.medication_riboflavin_400_class
import com.migratrack.app.generated.resources.medication_rizatriptan_10
import com.migratrack.app.generated.resources.medication_rizatriptan_10_class
import com.migratrack.app.generated.resources.medication_role_acute
import com.migratrack.app.generated.resources.medication_role_preventive
import com.migratrack.app.generated.resources.medication_sumatriptan_50
import com.migratrack.app.generated.resources.medication_sumatriptan_50_class
import com.migratrack.app.generated.resources.medication_topiramate_50
import com.migratrack.app.generated.resources.medication_topiramate_50_class
import com.migratrack.app.generated.resources.medication_ubrogepant_100
import com.migratrack.app.generated.resources.medication_ubrogepant_100_class
import com.migratrack.app.generated.resources.region_frontal_brow
import com.migratrack.app.generated.resources.region_left_temple
import com.migratrack.app.generated.resources.region_occipital_neck
import com.migratrack.app.generated.resources.region_retro_orbital
import com.migratrack.app.generated.resources.region_right_temple
import com.migratrack.app.generated.resources.symptom_aphasia
import com.migratrack.app.generated.resources.symptom_aphasia_short
import com.migratrack.app.generated.resources.symptom_nausea
import com.migratrack.app.generated.resources.symptom_nausea_short
import com.migratrack.app.generated.resources.symptom_neck_stiffness
import com.migratrack.app.generated.resources.symptom_neck_stiffness_short
import com.migratrack.app.generated.resources.symptom_paresthesia
import com.migratrack.app.generated.resources.symptom_paresthesia_short
import com.migratrack.app.generated.resources.symptom_phonophobia
import com.migratrack.app.generated.resources.symptom_phonophobia_short
import com.migratrack.app.generated.resources.symptom_photophobia
import com.migratrack.app.generated.resources.symptom_photophobia_short
import com.migratrack.app.generated.resources.symptom_scintillating_scotoma
import com.migratrack.app.generated.resources.symptom_scintillating_scotoma_short
import com.migratrack.app.generated.resources.trigger_aged_cheese
import com.migratrack.app.generated.resources.trigger_aged_cheese_description
import com.migratrack.app.generated.resources.trigger_alcohol
import com.migratrack.app.generated.resources.trigger_alcohol_description
import com.migratrack.app.generated.resources.trigger_barometric_pressure
import com.migratrack.app.generated.resources.trigger_barometric_pressure_description
import com.migratrack.app.generated.resources.trigger_bright_light
import com.migratrack.app.generated.resources.trigger_bright_light_description
import com.migratrack.app.generated.resources.trigger_caffeine
import com.migratrack.app.generated.resources.trigger_caffeine_description
import com.migratrack.app.generated.resources.trigger_category_custom
import com.migratrack.app.generated.resources.trigger_category_diet_consumption
import com.migratrack.app.generated.resources.trigger_category_hormonal
import com.migratrack.app.generated.resources.trigger_category_sleep_physical
import com.migratrack.app.generated.resources.trigger_category_stress_emotions
import com.migratrack.app.generated.resources.trigger_category_weather_environment
import com.migratrack.app.generated.resources.trigger_custom_description
import com.migratrack.app.generated.resources.trigger_dehydration
import com.migratrack.app.generated.resources.trigger_dehydration_description
import com.migratrack.app.generated.resources.trigger_emotional_stress
import com.migratrack.app.generated.resources.trigger_emotional_stress_description
import com.migratrack.app.generated.resources.trigger_heat_humidity
import com.migratrack.app.generated.resources.trigger_heat_humidity_description
import com.migratrack.app.generated.resources.trigger_hormonal_cycle
import com.migratrack.app.generated.resources.trigger_hormonal_cycle_description
import com.migratrack.app.generated.resources.trigger_loud_noise
import com.migratrack.app.generated.resources.trigger_loud_noise_description
import com.migratrack.app.generated.resources.trigger_overexertion
import com.migratrack.app.generated.resources.trigger_overexertion_description
import com.migratrack.app.generated.resources.trigger_poor_sleep
import com.migratrack.app.generated.resources.trigger_poor_sleep_description
import com.migratrack.app.generated.resources.trigger_screen_fatigue
import com.migratrack.app.generated.resources.trigger_screen_fatigue_description
import com.migratrack.app.generated.resources.trigger_skipped_meals
import com.migratrack.app.generated.resources.trigger_skipped_meals_description
import com.migratrack.app.generated.resources.trigger_sweeteners
import com.migratrack.app.generated.resources.trigger_sweeteners_description
import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.Diagnosis
import com.migratrack.domain.model.Dose
import com.migratrack.domain.model.DoseUnit
import com.migratrack.domain.model.EpisodeFrequency
import com.migratrack.domain.model.ExportRange
import com.migratrack.domain.model.Medication
import com.migratrack.domain.model.MedicationCatalog
import com.migratrack.domain.model.MedicationIntake
import com.migratrack.domain.model.MedicationRole
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.model.Trigger
import com.migratrack.domain.model.TriggerCatalog
import com.migratrack.domain.model.TriggerCategory
import com.migratrack.domain.model.TypicalDuration
import com.migratrack.export.report.CatalogNames
import org.jetbrains.compose.resources.StringResource

/**
 * A text that is either a resource with its arguments or the user's own words.
 * Composables and view models format it the same way; an argument can itself be a [UiText].
 */
sealed interface UiText {
    class Resource(val id: StringResource, vararg val args: Any) : UiText

    class Verbatim(val text: String) : UiText
}

@Composable
fun UiText.resolve(): String = when (this) {
    is UiText.Verbatim -> text
    is UiText.Resource -> stringResource(id, *args.map { if (it is UiText) it.resolve() else it }.toTypedArray())
}

suspend fun UiText.load(): String = when (this) {
    is UiText.Verbatim -> text
    is UiText.Resource -> getString(id, *args.map { if (it is UiText) it.load() else it }.toTypedArray())
}

val Diagnosis.title: StringResource
    get() = when (this) {
        Diagnosis.MigraineWithoutAura -> Res.string.diagnosis_migraine_without_aura
        Diagnosis.MigraineWithAura -> Res.string.diagnosis_migraine_with_aura
        Diagnosis.ChronicMigraine -> Res.string.diagnosis_chronic_migraine
        Diagnosis.TensionType -> Res.string.diagnosis_tension_type
        Diagnosis.Cluster -> Res.string.diagnosis_cluster
        Diagnosis.NotDiagnosed -> Res.string.diagnosis_not_diagnosed
    }

val Diagnosis.subtitle: StringResource
    get() = when (this) {
        Diagnosis.MigraineWithoutAura -> Res.string.diagnosis_migraine_without_aura_subtitle
        Diagnosis.MigraineWithAura -> Res.string.diagnosis_migraine_with_aura_subtitle
        Diagnosis.ChronicMigraine -> Res.string.diagnosis_chronic_migraine_subtitle
        Diagnosis.TensionType -> Res.string.diagnosis_tension_type_subtitle
        Diagnosis.Cluster -> Res.string.diagnosis_cluster_subtitle
        Diagnosis.NotDiagnosed -> Res.string.diagnosis_not_diagnosed_subtitle
    }

val EpisodeFrequency.title: StringResource
    get() = when (this) {
        EpisodeFrequency.LessThanOne -> Res.string.frequency_less_than_one
        EpisodeFrequency.OneToFour -> Res.string.frequency_one_to_four
        EpisodeFrequency.FiveToEight -> Res.string.frequency_five_to_eight
        EpisodeFrequency.EightPlus -> Res.string.frequency_eight_plus
    }

val TypicalDuration.title: StringResource
    get() = when (this) {
        TypicalDuration.TwoToFourHours -> Res.string.duration_two_to_four
        TypicalDuration.FourToTwelveHours -> Res.string.duration_four_to_twelve
        TypicalDuration.TwelveToTwentyFour -> Res.string.duration_twelve_to_twenty_four
        TypicalDuration.OneToThreeDays -> Res.string.duration_one_to_three_days
    }

val CranialRegion.title: StringResource
    get() = when (this) {
        CranialRegion.LeftTemple -> Res.string.region_left_temple
        CranialRegion.RightTemple -> Res.string.region_right_temple
        CranialRegion.FrontalBrow -> Res.string.region_frontal_brow
        CranialRegion.RetroOrbital -> Res.string.region_retro_orbital
        CranialRegion.OccipitalNeck -> Res.string.region_occipital_neck
    }

val Symptom.title: StringResource
    get() = when (this) {
        Symptom.ScintillatingScotoma -> Res.string.symptom_scintillating_scotoma
        Symptom.NeckStiffness -> Res.string.symptom_neck_stiffness
        Symptom.Photophobia -> Res.string.symptom_photophobia
        Symptom.Nausea -> Res.string.symptom_nausea
        Symptom.Phonophobia -> Res.string.symptom_phonophobia
        Symptom.Aphasia -> Res.string.symptom_aphasia
        Symptom.Paresthesia -> Res.string.symptom_paresthesia
    }

val Symptom.shortTitle: StringResource
    get() = when (this) {
        Symptom.ScintillatingScotoma -> Res.string.symptom_scintillating_scotoma_short
        Symptom.NeckStiffness -> Res.string.symptom_neck_stiffness_short
        Symptom.Photophobia -> Res.string.symptom_photophobia_short
        Symptom.Nausea -> Res.string.symptom_nausea_short
        Symptom.Phonophobia -> Res.string.symptom_phonophobia_short
        Symptom.Aphasia -> Res.string.symptom_aphasia_short
        Symptom.Paresthesia -> Res.string.symptom_paresthesia_short
    }

val TriggerCategory.title: StringResource
    get() = when (this) {
        TriggerCategory.WeatherEnvironment -> Res.string.trigger_category_weather_environment
        TriggerCategory.SleepPhysical -> Res.string.trigger_category_sleep_physical
        TriggerCategory.StressEmotions -> Res.string.trigger_category_stress_emotions
        TriggerCategory.Hormonal -> Res.string.trigger_category_hormonal
        TriggerCategory.DietConsumption -> Res.string.trigger_category_diet_consumption
        TriggerCategory.Custom -> Res.string.trigger_category_custom
    }

val MedicationRole.title: StringResource
    get() = when (this) {
        MedicationRole.Acute -> Res.string.medication_role_acute
        MedicationRole.Preventive -> Res.string.medication_role_preventive
    }

val ExportRange.title: StringResource
    get() = when (this) {
        ExportRange.Last30Days -> Res.string.export_range_last_30_days
        ExportRange.Last90Days -> Res.string.export_range_last_90_days
        ExportRange.Last180Days -> Res.string.export_range_last_6_months
        ExportRange.Last365Days -> Res.string.export_range_last_12_months
    }

/** Name and description of each built-in trigger; a test checks that the catalog has no entry without them. */
internal val TriggerNames: Map<String, Pair<StringResource, StringResource>> = mapOf(
    "barometric-pressure" to (Res.string.trigger_barometric_pressure to Res.string.trigger_barometric_pressure_description),
    "bright-light" to (Res.string.trigger_bright_light to Res.string.trigger_bright_light_description),
    "heat-humidity" to (Res.string.trigger_heat_humidity to Res.string.trigger_heat_humidity_description),
    "loud-noise" to (Res.string.trigger_loud_noise to Res.string.trigger_loud_noise_description),
    "poor-sleep" to (Res.string.trigger_poor_sleep to Res.string.trigger_poor_sleep_description),
    "dehydration" to (Res.string.trigger_dehydration to Res.string.trigger_dehydration_description),
    "skipped-meals" to (Res.string.trigger_skipped_meals to Res.string.trigger_skipped_meals_description),
    "overexertion" to (Res.string.trigger_overexertion to Res.string.trigger_overexertion_description),
    "emotional-stress" to (Res.string.trigger_emotional_stress to Res.string.trigger_emotional_stress_description),
    "screen-fatigue" to (Res.string.trigger_screen_fatigue to Res.string.trigger_screen_fatigue_description),
    "hormonal-cycle" to (Res.string.trigger_hormonal_cycle to Res.string.trigger_hormonal_cycle_description),
    "caffeine" to (Res.string.trigger_caffeine to Res.string.trigger_caffeine_description),
    "aged-cheese" to (Res.string.trigger_aged_cheese to Res.string.trigger_aged_cheese_description),
    "alcohol" to (Res.string.trigger_alcohol to Res.string.trigger_alcohol_description),
    "sweeteners" to (Res.string.trigger_sweeteners to Res.string.trigger_sweeteners_description),
)

/** Name and drug class of each built-in medication; a test checks that the catalog has no entry without them. */
internal val MedicationNames: Map<String, Pair<StringResource, StringResource>> = mapOf(
    "ibuprofen-400" to (Res.string.medication_ibuprofen_400 to Res.string.medication_ibuprofen_400_class),
    "sumatriptan-50" to (Res.string.medication_sumatriptan_50 to Res.string.medication_sumatriptan_50_class),
    "rizatriptan-10" to (Res.string.medication_rizatriptan_10 to Res.string.medication_rizatriptan_10_class),
    "naproxen-500" to (Res.string.medication_naproxen_500 to Res.string.medication_naproxen_500_class),
    "acetaminophen-1000" to (Res.string.medication_acetaminophen_1000 to Res.string.medication_acetaminophen_1000_class),
    "ubrogepant-100" to (Res.string.medication_ubrogepant_100 to Res.string.medication_ubrogepant_100_class),
    "metoclopramide-10" to (Res.string.medication_metoclopramide_10 to Res.string.medication_metoclopramide_10_class),
    "electrolytes" to (Res.string.medication_electrolytes to Res.string.medication_electrolytes_class),
    "riboflavin-400" to (Res.string.medication_riboflavin_400 to Res.string.medication_riboflavin_400_class),
    "magnesium-400" to (Res.string.medication_magnesium_400 to Res.string.medication_magnesium_400_class),
    "propranolol-40" to (Res.string.medication_propranolol_40 to Res.string.medication_propranolol_40_class),
    "topiramate-50" to (Res.string.medication_topiramate_50 to Res.string.medication_topiramate_50_class),
    "amitriptyline-10" to (Res.string.medication_amitriptyline_10 to Res.string.medication_amitriptyline_10_class),
    "erenumab-70" to (Res.string.medication_erenumab_70 to Res.string.medication_erenumab_70_class),
)

/** A built-in trigger in the language of the app; a custom or removed one keeps its [stored] name. */
fun triggerName(id: String, stored: String): UiText =
    TriggerNames[id]?.let { UiText.Resource(it.first) } ?: UiText.Verbatim(stored)

val Trigger.nameText: UiText get() = triggerName(id, name)

val Trigger.descriptionText: UiText
    get() = UiText.Resource(TriggerNames[id]?.second ?: Res.string.trigger_custom_description)

/**
 * A medication with its dose. A built-in one shows its name and catalog dose in the language of the app;
 * a dose that differs from the catalog stays as the user saved it.
 */
fun medicationText(id: String, name: String, dose: String): UiText {
    val builtIn = MedicationCatalog.builtIn(id)
    val shownName = MedicationNames[id]?.let { UiText.Resource(it.first) } ?: UiText.Verbatim(name)
    val catalogDose = builtIn?.catalogDose?.takeIf { it.stored == dose }
    return when {
        catalogDose != null -> UiText.Resource(Res.string.medication_name_and_dose, shownName, catalogDose.text)
        dose.isBlank() -> shownName
        else -> UiText.Resource(Res.string.medication_name_and_dose, shownName, dose)
    }
}

val Medication.nameText: UiText get() = medicationText(id, name, dose)

val MedicationIntake.text: UiText get() = medicationText(medicationId, name, dose)

/** The drug class of a built-in medication, a note for a custom one, nothing for a removed one. */
val Medication.classText: UiText
    get() = MedicationNames[id]?.let { UiText.Resource(it.second) }
        ?: if (isCustom) UiText.Resource(Res.string.medication_custom_class) else UiText.Verbatim("")

val Dose.text: UiText
    get() = when (unit) {
        // A grouping separator reads as a decimal point in many regions: "1.000mg" would look like 1 mg.
        DoseUnit.Milligram -> UiText.Resource(Res.string.medication_dose_milligrams, Numbers.integer(amount, grouping = false))
    }

/** Loads every catalog name in the current language, so the report can name things without suspending. */
suspend fun loadCatalogNames(): CatalogNames {
    val diagnoses = Diagnosis.entries.associateWith { getString(it.title) }
    val frequencies = EpisodeFrequency.entries.associateWith { getString(it.title) }
    val durations = TypicalDuration.entries.associateWith { getString(it.title) }
    val regions = CranialRegion.entries.associateWith { getString(it.title) }
    val symptoms = Symptom.entries.associateWith { getString(it.title) }
    val ranges = ExportRange.entries.associateWith { getString(it.title) }
    val triggers = TriggerCatalog.defaults.associate { it.id to it.nameText.load() }
    val medications = MedicationCatalog.defaults.associate { it.id to (MedicationNames[it.id]?.let { names -> getString(names.first) } ?: it.name) }
    val doses = MedicationCatalog.defaults.mapNotNull { medication ->
        medication.catalogDose?.let { medication.id to (it.stored to it.text.load()) }
    }.toMap()
    return object : CatalogNames {
        override fun diagnosis(diagnosis: Diagnosis) = diagnoses.getValue(diagnosis)
        override fun frequency(frequency: EpisodeFrequency) = frequencies.getValue(frequency)
        override fun duration(duration: TypicalDuration) = durations.getValue(duration)
        override fun region(region: CranialRegion) = regions.getValue(region)
        override fun symptom(symptom: Symptom) = symptoms.getValue(symptom)
        override fun range(range: ExportRange) = ranges.getValue(range)
        override fun trigger(id: String, stored: String) = triggers[id] ?: stored
        override fun medicationName(id: String, stored: String) = medications[id] ?: stored
        override fun medicationDose(id: String, stored: String) = doses[id]?.takeIf { it.first == stored }?.second ?: stored
        override fun compare(a: String, b: String) = compareForDisplay(a, b)
    }
}

/** Compares two names as the language of the device sorts them, for the order of equal counts. */
expect fun compareForDisplay(a: String, b: String): Int
