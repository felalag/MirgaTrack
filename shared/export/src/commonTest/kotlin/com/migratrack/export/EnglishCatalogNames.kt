package com.migratrack.export

import com.migratrack.domain.model.CranialRegion
import com.migratrack.domain.model.Diagnosis
import com.migratrack.domain.model.EpisodeFrequency
import com.migratrack.domain.model.ExportRange
import com.migratrack.domain.model.MedicationCatalog
import com.migratrack.domain.model.Symptom
import com.migratrack.domain.model.TriggerCatalog
import com.migratrack.domain.model.TypicalDuration
import com.migratrack.export.report.CatalogNames

/** The English names of version 1.0, so the report tests keep their text. */
object EnglishCatalogNames : CatalogNames {
    override fun diagnosis(diagnosis: Diagnosis) = when (diagnosis) {
        Diagnosis.MigraineWithoutAura -> "Migraine without aura"
        Diagnosis.MigraineWithAura -> "Migraine with aura"
        Diagnosis.ChronicMigraine -> "Chronic Migraine"
        Diagnosis.TensionType -> "Tension-type Headache"
        Diagnosis.Cluster -> "Cluster Headache"
        Diagnosis.NotDiagnosed -> "Not diagnosed / Exploring"
    }

    override fun frequency(frequency: EpisodeFrequency) = when (frequency) {
        EpisodeFrequency.LessThanOne -> "< 1 / mo"
        EpisodeFrequency.OneToFour -> "1–4 / mo"
        EpisodeFrequency.FiveToEight -> "5–7 / mo"
        EpisodeFrequency.EightPlus -> "8+ / mo"
    }

    override fun duration(duration: TypicalDuration) = when (duration) {
        TypicalDuration.TwoToFourHours -> "2–4h"
        TypicalDuration.FourToTwelveHours -> "4–12h"
        TypicalDuration.TwelveToTwentyFour -> "12–24h"
        TypicalDuration.OneToThreeDays -> "24–72h"
    }

    override fun region(region: CranialRegion) = when (region) {
        CranialRegion.LeftTemple -> "Left Temple"
        CranialRegion.RightTemple -> "Right Temple"
        CranialRegion.FrontalBrow -> "Frontal / Brow"
        CranialRegion.RetroOrbital -> "Retro-Orbital"
        CranialRegion.OccipitalNeck -> "Occipital & Cervical Neck"
    }

    override fun symptom(symptom: Symptom) = when (symptom) {
        Symptom.ScintillatingScotoma -> "Scintillating Scotoma (Visual)"
        Symptom.NeckStiffness -> "Neck Stiffness"
        Symptom.Photophobia -> "Photophobia (Light)"
        Symptom.Nausea -> "Nausea"
        Symptom.Phonophobia -> "Phonophobia (Sound)"
        Symptom.Aphasia -> "Aphasia / Brain Fog"
        Symptom.Paresthesia -> "Tingling / Paresthesia"
    }

    override fun range(range: ExportRange) = when (range) {
        ExportRange.Last30Days -> "Last 30 Days"
        ExportRange.Last90Days -> "Last 90 Days"
        ExportRange.Last180Days -> "Last 6 Months"
        ExportRange.Last365Days -> "Last 12 Months"
    }

    override fun trigger(id: String, stored: String) = TriggerCatalog.builtIn(id)?.name ?: stored

    override fun medicationName(id: String, stored: String) = MedicationCatalog.builtIn(id)?.name ?: stored

    override fun medicationDose(id: String, stored: String) = stored

    override fun compare(a: String, b: String) = a.compareTo(b)
}
