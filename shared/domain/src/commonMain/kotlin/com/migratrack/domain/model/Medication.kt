package com.migratrack.domain.model

/**
 * A medication the user can put in the treatment blueprint and log during an episode.
 * [name] and [dose] are the text that episodes store as a copy. A built-in medication also has a [catalogDose],
 * and the UI shows it and the name of the [id] in the language of the app.
 */
data class Medication(
    val id: String,
    val name: String,
    val dose: String,
    val role: MedicationRole,
    val isCustom: Boolean = false,
    val catalogDose: Dose? = null,
) {
    val displayName: String get() = medicationDisplayName(name, dose)
}

enum class MedicationRole {
    /** Taken during an attack. The quick-log preset pre-fills these. */
    Acute,
    Preventive,
}

/** An amount and a unit, so each language can write the dose in its own way. */
data class Dose(val amount: Int, val unit: DoseUnit) {
    /** The dose text that episodes store, as version 1.0 wrote it: "400mg". */
    val stored: String get() = "$amount${unit.symbol}"
}

enum class DoseUnit(val symbol: String) { Milligram("mg") }

internal fun medicationDisplayName(name: String, dose: String): String =
    if (dose.isBlank()) name else "$name $dose"

object MedicationCatalog {
    val defaults: List<Medication> = listOf(
        withDose("ibuprofen-400", "Ibuprofen", 400, MedicationRole.Acute),
        withDose("sumatriptan-50", "Sumatriptan", 50, MedicationRole.Acute),
        withDose("rizatriptan-10", "Rizatriptan", 10, MedicationRole.Acute),
        withDose("naproxen-500", "Naproxen", 500, MedicationRole.Acute),
        withDose("acetaminophen-1000", "Acetaminophen", 1000, MedicationRole.Acute),
        withDose("ubrogepant-100", "Ubrogepant", 100, MedicationRole.Acute),
        withDose("metoclopramide-10", "Metoclopramide", 10, MedicationRole.Acute),
        Medication("electrolytes", "Hydration Electrolytes", "", MedicationRole.Acute),
        withDose("riboflavin-400", "Riboflavin", 400, MedicationRole.Preventive),
        withDose("magnesium-400", "Magnesium", 400, MedicationRole.Preventive),
        withDose("propranolol-40", "Propranolol", 40, MedicationRole.Preventive),
        withDose("topiramate-50", "Topiramate", 50, MedicationRole.Preventive),
        withDose("amitriptyline-10", "Amitriptyline", 10, MedicationRole.Preventive),
        withDose("erenumab-70", "Erenumab", 70, MedicationRole.Preventive),
    )

    private val byId = defaults.associateBy { it.id }

    /** The built-in medication with this ID, or null for a custom or removed one. */
    fun builtIn(id: String): Medication? = byId[id]

    private fun withDose(id: String, name: String, milligrams: Int, role: MedicationRole): Medication {
        val dose = Dose(milligrams, DoseUnit.Milligram)
        return Medication(id, name, dose.stored, role, catalogDose = dose)
    }
}
