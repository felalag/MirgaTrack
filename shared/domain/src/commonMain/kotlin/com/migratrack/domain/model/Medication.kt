package com.migratrack.domain.model

/** A medication the user can put in the treatment blueprint and log during an episode. */
data class Medication(
    val id: String,
    val name: String,
    val dose: String,
    val role: MedicationRole,
    val drugClass: String,
    val isCustom: Boolean = false,
) {
    val displayName: String get() = medicationDisplayName(name, dose)
}

enum class MedicationRole(val title: String) {
    /** Taken during an attack. The quick-log preset pre-fills these. */
    Acute("Acute / Abortive"),
    Preventive("Preventive & Supplements"),
}

internal fun medicationDisplayName(name: String, dose: String): String =
    if (dose.isBlank()) name else "$name $dose"

object MedicationCatalog {
    val defaults: List<Medication> = listOf(
        Medication("ibuprofen-400", "Ibuprofen", "400mg", MedicationRole.Acute, "Anti-inflammatory Adjuvant"),
        Medication("sumatriptan-50", "Sumatriptan", "50mg", MedicationRole.Acute, "Triptan, serotonin agonist"),
        Medication("rizatriptan-10", "Rizatriptan", "10mg", MedicationRole.Acute, "Triptan, orally dissolving"),
        Medication("naproxen-500", "Naproxen", "500mg", MedicationRole.Acute, "Long-acting NSAID"),
        Medication("acetaminophen-1000", "Acetaminophen", "1000mg", MedicationRole.Acute, "Analgesic"),
        Medication("ubrogepant-100", "Ubrogepant", "100mg", MedicationRole.Acute, "Gepant, CGRP antagonist"),
        Medication("metoclopramide-10", "Metoclopramide", "10mg", MedicationRole.Acute, "Antiemetic"),
        Medication("electrolytes", "Hydration Electrolytes", "", MedicationRole.Acute, "Oral rehydration"),
        Medication("riboflavin-400", "Riboflavin", "400mg", MedicationRole.Preventive, "Vitamin B2"),
        Medication("magnesium-400", "Magnesium", "400mg", MedicationRole.Preventive, "Mineral supplement"),
        Medication("propranolol-40", "Propranolol", "40mg", MedicationRole.Preventive, "Beta blocker"),
        Medication("topiramate-50", "Topiramate", "50mg", MedicationRole.Preventive, "Anticonvulsant"),
        Medication("amitriptyline-10", "Amitriptyline", "10mg", MedicationRole.Preventive, "Tricyclic antidepressant"),
        Medication("erenumab-70", "Erenumab", "70mg", MedicationRole.Preventive, "CGRP antibody, monthly"),
    )
}
