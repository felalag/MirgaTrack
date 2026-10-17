package com.migratrack.export.report

import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.Diagnosis
import com.migratrack.domain.model.EpisodeFrequency
import com.migratrack.domain.model.ExportRange
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.model.TypicalDuration

/**
 * The names that the report shows for the values of the domain, in the language of the app.
 * The app loads them from its resources before each export; the tests use English names.
 */
interface CatalogNames {
    fun diagnosis(diagnosis: Diagnosis): String

    fun frequency(frequency: EpisodeFrequency): String

    fun duration(duration: TypicalDuration): String

    fun region(region: CranialRegion): String

    fun symptom(symptom: Symptom): String

    fun range(range: ExportRange): String

    /** The name of the built-in trigger [id], or [stored] for a custom or removed one. */
    fun trigger(id: String, stored: String): String

    /** The name of the built-in medication [id], or [stored] for a custom or removed one. */
    fun medicationName(id: String, stored: String): String

    /** The catalog dose of [id] when [stored] is that dose, else [stored] as the user saved it. */
    fun medicationDose(id: String, stored: String): String

    /** Orders names as the language sorts them. */
    fun compare(a: String, b: String): Int
}
