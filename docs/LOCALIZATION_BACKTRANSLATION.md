# Back-translation reports

Each translation batch adds a section here ([LOCALIZATION_CHECKLIST.md](LOCALIZATION_CHECKLIST.md), section 3). A second pass translates the medical strings, the medical notes and the legal texts back into English from the translation alone. Then it compares each back-translation with the English source. A difference in meaning gets a fix or, when a length limit leaves no exact word, an entry in "Kept".

## Batch 2: Russian and Belarusian (October 18, 2026)

Scope: the keys of the checklist, section 3 (`diagnosis_*`, `symptom_*`, `region_*`, `medication_*`, `trigger_*`, `frequency_*`, `duration_*`, `pain_scale_*`, `common_medical_disclaimer`, `insights_triggers_hint`, `report_*`), the Siri and watch strings, and `docs/legal/files/{ru,be}/*.md`.

### Fixed

| Key | Back-translation before the fix | English source | Fix (ru / be) |
|---|---|---|---|
| `diagnosis_migraine_without_aura_subtitle` | "The most common form of migraine" | Common migraine pattern | "Частая форма мигрени" / "Частая форма мігрэні" |
| `diagnosis_tension_type_subtitle` | "Squeezing pain, like a band" | Constricting, band-like tension | "Сдавливающее напряжение, как обруч" / "Сціскальнае напружанне, як абруч" |
| `diagnosis_not_diagnosed_subtitle` | "Observing my symptoms" | Observing symptom patterns | "Наблюдаю за закономерностями симптомов" / "Назіраю за заканамернасцямі сімптомаў" |
| `pain_scale_high` (ru) | "10 Unbearable" | 10 Disabling | "Изнуряющая" (debilitating) |
| `report_stat_average_intensity` | "Average pain /10" | Average intensity /10 | "Ср. интенсивность /10" / "Сяр. інтэнсіўнасць /10": the glossary term, abbreviated for the 22 characters of the tile |
| `medication_ibuprofen_400_class` (first draft) | "Anti-inflammatory agent" | Anti-inflammatory Adjuvant | "Противовоспалительный адъювант" / "Супрацьзапаленчы ад’ювант" |

### Kept, with the reason

| Key | Back-translation | English source | Reason |
|---|---|---|---|
| `pain_scale_high` (be) | "10 Unbearable" | 10 Disabling | The label has 11 letters at most. No Belarusian adjective for "disabling" of that length was found in a source. Owner check. |
| `medication_topiramate_50_class` | "Antiepileptic agent" | Anticonvulsant | The national term for the class in R2 (p. 42) and B1 (p. 33). Same drug class. |
| `medication_rizatriptan_10_class` | "Triptan, dissolves in the mouth" | Triptan, orally dissolving | Same meaning. The common words, not "диспергируемый в полости рта" of R2. |
| `region_retro_orbital` | "Behind the eye" | Retro-Orbital | Same meaning in common words. |
| `symptom_scintillating_scotoma` | "Scintillating scotoma (visual aura)" | Scintillating Scotoma (Visual) | The bracket names what the user sees; same meaning. |
| `trigger_category_sleep_physical` | "Sleep and daily routine" | Sleep & Physical Routine | Wording only: the category holds sleep, meals, water and exertion. |
| `trigger_emotional_stress` | "Stress / relaxation after stress" | Emotional Stress / Letdown | "Letdown" is the relaxation after stress (R2, p. 21: "расслаблением после стресса"). |
| `duration_*` (be) | "2–4 h" with "г" | 2–4h | "г" is the narrow CLDR hour symbol in Belarusian. It can also mean "year", but the chip is under "Звычайная працягласць прыступу". "гадз" does not fit 7 characters. |
| Watch and complication "Log Migraine" | "New attack" | Log Migraine | 14 characters on a 40 mm watch. The owner approved it in the sample. |
| Complication "LOG" | "ENTRY" ("ВВОД" / "УВОД") | LOG | 4 characters under a plus sign. No Russian or Belarusian verb fits. |
| Complication "Open MigraTrack on iPhone" | "Open on iPhone" | Open MigraTrack on iPhone | 25 characters. The complication belongs to MigraTrack, so the name is clear without the word. |
| `settings_member` | "Diary since" | Member | "Участник" (member) means nothing without an account. The value is the first tracked month. |
| `common_intensity_of_ten` | "Pain 6/10" | Intensity 6/10 | 16 characters. The glossary gives "Боль" as the short form with a score. |

### No difference in meaning

- Diagnoses, the other symptoms, regions, frequency and duration chips, the 15 triggers and their descriptions, the other 13 medications and their classes, the dose unit "мг".
- `common_medical_disclaimer`: "MigraTrack is a diary, not a medical recommendation. Talk to a doctor before you change the treatment."
- `insights_triggers_hint`: "How often you noted each trigger together with an attack. This does not indicate a cause."
- `report_triggers_note`: "The numbers show how often the patient noted a trigger together with an attack. They do not indicate a cause."
- `report_disclaimer`: "The data was entered by the patient in MigraTrack. The report complements a clinical assessment and does not replace it."
- The other `report_*` keys, the Siri dialogs and the watch strings.
- Privacy Policy, both languages: each section keeps its facts (no account, no server, no analytics; sleep read only; WatchConnectivity; backups; sharing; Delete All Data; contact; changes). "Logging preset" comes back as "default values for logging".
- Terms of Service, both languages: "not a medical device", "talk to your clinician", the emergency sentence, the backup responsibility, personal non-commercial use, "as is", the limitation of liability. Russian and Belarusian use the legal term "медицинское изделие" / "медыцынскі выраб" for "medical device".
- Each legal translation starts with: "This is a translation for convenience. The English text is legally binding."
