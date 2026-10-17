package com.migratrack.app.ui

import com.migratrack.domain.model.MedicationCatalog
import com.migratrack.domain.model.TriggerCatalog
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CatalogTextTest {
    @Test
    fun everyBuiltInTriggerHasANameResource() {
        assertEquals(TriggerCatalog.defaults.map { it.id }.toSet(), TriggerNames.keys)
    }

    @Test
    fun everyBuiltInMedicationHasANameResource() {
        assertEquals(MedicationCatalog.defaults.map { it.id }.toSet(), MedicationNames.keys)
    }

    @Test
    fun theCatalogDoseIsShownInTheLanguageOfTheApp() {
        val text = assertIs<UiText.Resource>(medicationText("ibuprofen-400", "Ibuprofen", "400mg"))
        assertEquals(listOf("400"), assertIs<UiText.Resource>(text.args[1]).args.toList())
    }

    @Test
    fun aDoseThatDiffersFromTheCatalogStaysAsStored() {
        val text = assertIs<UiText.Resource>(medicationText("ibuprofen-400", "Ibuprofen", "200mg"))
        assertEquals("200mg", text.args[1])
    }

    @Test
    fun aCustomMedicationKeepsItsOwnWords() {
        val text = assertIs<UiText.Verbatim>(medicationText("custom-medication-1", "Eletriptan 40mg", ""))
        assertEquals("Eletriptan 40mg", text.text)
    }
}
