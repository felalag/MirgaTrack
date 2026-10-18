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

## Batch 3: Western Europe (October 18, 2026)

Languages: German, French, French (Canada), Spanish, Spanish (Spain), Portuguese, Portuguese (Portugal), Italian, Dutch and Catalan. Scope: the keys of the checklist, section 3, the Siri and watch strings, and `docs/legal/files/{de,fr,es,pt,pt-PT,it,nl,ca}/*.md`. The regional folders were read together with the folder of their language.

### Fixed

| Language | Key | Back-translation before the fix | English source | Fix |
|---|---|---|---|---|
| de | `medication_metoclopramide_10_class` (first draft) | "Agent against nausea" | Antiemetic | "Antiemetikum": an antiemetic also acts on vomiting (DE2, p. 9) |
| fr | `summary_pain_free_a11y`, `insights_trigger_row_a11y`, `report_share_of_episodes` | "1 days of 30 tracked days", "noted for 1 attacks of 5" | One noun after the number that selects the plural | The noun follows the number that selects the plural form: "%1$s sur %2$s jours suivis" |
| fr, es, pt, it, ca | Every `plurals` (first draft of fr) | "1 000 000 of attacks" | — | CLDR "many" covers exact millions only; written in digits, it takes the "other" text |
| fr | `summary_sleep_connect_body`, `summary_sleep_connect_button` | "Apple Health" | Apple Health | "l'app Santé", the name of the app in French iOS, as in the other French strings |
| pt | `diagnosis_tension_type` | "Tension-type headache" (with "tensional") | Tension-type Headache | "Cefaleia do tipo tensão", the term of the Brazilian ICHD-3 (BR1, p. 22) |
| it | `insights_headline_month` | "attacks to Oct" | Episodes in Oct | "attacchi in ott" |
| de, fr, es, pt, it, nl, ca | `medication_dose_milligrams` | "50 / mg" on two lines at the largest text size | 50mg | A no-break space between the number and "mg", as in Russian and Belarusian |
| de, fr, es, pt, it, nl, ca | `export_pain_charts_subtitle` | "1– / 10" on two lines at the largest text size | 1–10 | A word joiner (U+2060) on both sides of the en dash, as in Russian and Belarusian |

### Kept, with the reason

| Language | Key | Back-translation | English source | Reason |
|---|---|---|---|---|
| de | `pain_scale_high` | "10 Paralysing" | 10 Disabling | 14 characters. "Lähmend" is the common German word for a disabling headache; "Unerträglich" has 15. |
| de, fr, it | `report_stat_pain_free_days`, watch "Pain-free days", legends | "Pain-free" | Pain-free days | 16 characters on the 40 mm watch; the term pair makes the report tile equal |
| de, fr, it | `report_column_pain` | "Int." | Pain | 5 characters in the PDF column |
| de, fr, es, pt, it, nl, ca | `settings_member` | "With us since", "Tracked since", "Diary since", "Active since" | Member | No account exists; the value is the first tracked month (as in batch 2) |
| fr | `diagnosis_cluster` | "Vascular pain of the face" | Cluster Headache | "Algie vasculaire de la face" is the name of 3.1 in the French ICHD-3 (FR1, p. 87) |
| fr | `calendar_migraine_days` | "3 d of migraine" | 3 Migraine Days | 18 characters; "j" is the French symbol for day |
| fr | `export_range_*` | "Over 30 days" | Last 30 Days | 16 characters; same period |
| es, es-ES, pt-PT, nl, ca | `strip_legend_not_tracked` | "No data" | Not tracked | 14 characters. The longer legends of Insights keep "not tracked". |
| es, pt | `settings_episodes_logged` | "35 in total" | 35 Logged | 14 characters; the caption above says "episodes" |
| es | `report_baseline_triggers` | "Triggers" | Known triggers | 24 characters; the row is in the baseline profile, which lists known triggers only |
| es-ES, fr, pt-PT, it, nl, ca | `medication_topiramate_50_class` | "Antiepileptic" | Anticonvulsant | The national term of the class (ES2, FR2, PT2, NL1); same drug class |
| pt, nl | `common_intensity_of_ten` | "Pain 6/10" | Intensity 6/10 | 16 characters (as in batch 2) |
| pt | Diagnoses | "Migraine" as "enxaqueca" | Migraine | The Brazilian ICD-10 (BR2) and patients say "enxaqueca"; the Brazilian ICHD-3 says "migrânea" |
| pt | `medication_ubrogepant_100` | "Ubrogepanto" | Ubrogepant | No registration in Brazil; the INN with the Brazilian ending of the DCB |
| pt | `report_intakes` | "8 uses" | 8 intakes | Brazilian usage; pt-PT says "tomas" |
| it | `insights_legend_migraine_day` | "With migraine" | Migraine Day | 16 characters |
| it | `calendar_migraine_days` | "Migraine: 3 dd" | 3 Migraine Days | 18 characters; "gg" is the Italian abbreviation of days |
| nl | `pain_scale_high` | "10 Paralysing" | 10 Disabling | 14 characters; "verlammend" is the common Dutch word for a disabling headache |
| nl | `export_range_*` | "30 days" | Last 30 Days | 16 characters: "Laatste 6 maanden" has 17. The report header shows the dates. |
| ca | `calendar_edit_episode_details` | "Edit the details" | Edit Episode Details | 28 characters; the button is in the card of one episode |
| All | Complication "LOG" | "NEW", "NOTE", "REPORT" | LOG | 4 characters under a plus sign |
| All | Watch and complication "Log Migraine" | "New attack" | Log Migraine | 14 characters on a 40 mm watch. The owner approved it. |

### No difference in meaning

- Diagnoses, symptoms, regions, frequency and duration chips, the 15 triggers and their descriptions, the 14 medications and their classes, and the dose unit "mg" in all ten sets.
- `common_medical_disclaimer`, for example German: "MigraTrack is a diary and not medical advice. Speak with your doctor before you change your treatment."
- `insights_triggers_hint`: "How often you noted each trigger together with an attack. This does not show a cause."
- `report_triggers_note`: "The numbers show how often the patient noted a trigger with an attack. They do not show a cause."
- `report_disclaimer`: "Data entered by the patient in MigraTrack. This report supports a clinical assessment and does not replace it." German says "ärztliche Beurteilung" (assessment by a physician).
- The other `report_*` keys, the Siri dialogs and the watch strings.
- Privacy Policy, all eight texts: no account, no server, no analytics; sleep read only; WatchConnectivity; backups; sharing; Delete All Data; contact; changes.
- Terms of Service, all eight texts: not a medical device (the legal term of each language: "Medizinprodukt", "dispositif médical", "dispositivo médico", "dispositivo medico", "medisch hulpmiddel", "producte sanitari"), talk to your clinician, the emergency sentence, backups, personal non-commercial use, "as is", the limitation of liability.
- Each legal translation starts with the sentence that the English text is legally binding.
