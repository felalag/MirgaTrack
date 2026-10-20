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

## Batch 4: Central and Northern Europe (October 18, 2026)

Languages: Polish, Czech, Slovak, Croatian, Slovenian, Romanian, Hungarian, Lithuanian, Danish, Norwegian Bokmål, Swedish, Finnish and Turkish. Scope: the keys of the checklist, section 3, the Siri and watch strings, and `docs/legal/files/{pl,cs,sk,hr,sl,ro,hu,lt,da,nb,sv,fi,tr}/*.md`.

### Fixed

| Language | Key | Back-translation before the fix | English source | Fix |
|---|---|---|---|---|
| ro | Every key with "episode" (first draft, approved as a sample) | "crisis" ("criză") | Episode | "atac": the Romanian ICHD-3 (RO1, p. 11) says "atac de migrenă" and keeps "criză" for epileptic seizures (p. 17). The watch says "Atac nou", the complication "NOU". |
| fi | `diagnosis_migraine_without_aura` | "Migraine without aura" ("Migreeni ilman auraa") | Migraine without aura | "Auraton migreeni", the name in Käypä hoito (FI1, table 1) |
| cs, sk | `report_disclaimer` | "supplements the clinical examination" | supports a clinical assessment | "klinické posouzení" / "klinické posúdenie" (assessment). "Klinické hodnocení" is not used: in Czech it also means a clinical trial. |
| da, nb, sv | `medication_metoclopramide_10_class` | "Agent against nausea" | Antiemetic | "Antiemetikum": an antiemetic also acts on vomiting, as in batch 3 for German |
| All | `export_range_*` | "Last 90 / days" on two lines at the largest text size | Last 90 Days | A no-break space between the number and the unit, as in the dose |

### Kept, with the reason

| Language | Key | Back-translation | English source | Reason |
|---|---|---|---|---|
| pl | `pain_scale_high` | "10 Unbearable" | 10 Disabling | 14 characters. "Obezwładniający" and "Paraliżujący" are longer. |
| cs, sk, hu, da, nb, sv, sl | `pain_scale_high` | "10 Paralysing" | 10 Disabling | The common word for a disabling headache, as German and Dutch in batch 3 |
| hr | `pain_scale_high` | "10 Devastating" | 10 Disabling | 14 characters; "Onesposobljavajuća" has 18 |
| lt | `pain_scale_high` | "10 Exhausting" | 10 Disabling | 14 characters; "Nepakeliamas" (unbearable) has 15 with the number |
| ro, lt | `common_intensity_of_ten`, `insights_episode_option`, `episode_delete_message` | "Pain 6/10" | Intensity 6/10 | 16 characters ("Intensitate 6/10" has 17), as Portuguese and Dutch in batch 3 |
| pl | `report_legend_moderate` | "Medium 4–7" | Moderate 4–7 | 16 characters; "Umiarkowany 4–7" has 17 |
| fi | `report_legend_moderate` | "Mod. 4–7" ("Keskiv.") | Moderate 4–7 | 16 characters; "Keskivaikea 4–7" has 17 |
| pl, cs, sk, hr, sl, ro, hu, lt, da, nb, sv, fi, tr | `medication_topiramate_50_class` | "Antiepileptic" | Anticonvulsant | The usual class name in these languages (SK1, p. 3; PL-MP); same drug class |
| fi | `medication_acetaminophen_1000_class`, `medication_metoclopramide_10_class` | "Pain medicine", "Nausea medicine" | Analgesic, Antiemetic | The words of Finnish patient texts and of the 2024 Käypä hoito presentation ("Pahoinvointilääke: Metoklopramidi") |
| pl, cs, sk, hr, sl, ro, lt, da, nb, sv, fi, hu, tr | `settings_member` | "Diary since", "Tracked since", "First month", "Since", "Start" | Member | No account exists; the value is the first tracked month (as in batches 2 and 3) |
| cs, sk, hr, sl, ro, lt, sv | `settings_episodes_logged` | "35 in total" | 35 Logged | 14 characters or the plural forms; the caption above says "episodes" |
| sl, ro, lt | `calendar_migraine_days` | "Migraine: 3 days", "With migraine: 3", "Migraine: 3 d." | 3 Migraine Days | 18 characters with the plural forms: "2 dneva z migreno", "21 de zile cu migrenă", "1 diena su migrena" are longer |
| cs, sk, ro, lt | `export_range_*` | "Over 30 days", "30 days" | Last 30 Days | 16 characters; the report header shows the dates |
| pl | `export_range_last_12_months` | "Last year" | Last 12 Months | 16 characters; same period |
| sl, hu, lt, fi | `report_stat_pain_free_days`, watch "Pain-free days" | "Pain-free" | Pain-free days | 16 characters on the 40 mm watch; the term pair makes the report tile equal (as German, French, Italian in batch 3) |
| cs, sk, ro, lt, da, nb, sv, sl | `report_column_pain` | "Int." | Pain | 5 characters in the PDF column |
| hu | `calendar_edit_episode_details` | "Edit details" | Edit Episode Details | 28 characters; the button is in the card of one episode |
| lt | `calendar_edit_episode_details` | "Edit the data" | Edit Episode Details | 28 characters; same reason |
| da, nb, sv | `strip_legend_not_tracked` | "No data" | Not tracked | 14 characters; the longer legends of Insights keep "not registered" |
| pl | `episode_aura_symptoms`, `report_header_symptoms` | "Aura and accompanying symptoms" | Aura & Sensory Phenotypes / Symptoms | Nausea in the list is not a sensory symptom; "objawy towarzyszące" is the usual Polish clinical phrase |
| cs, sk, hr, sl, ro | `diagnosis_cluster` | "Cluster headache" (English word in cs, hr, ro) | Cluster Headache | The Czech and Romanian ICHD-3 keep "cluster" (CS1, p. 3; RO1, p. 6) |
| tr | `duration_*` | "2–4 h" with the symbol "h" | 2–4h | 7 characters: "12–24 sa" has 8 |
| hu, fi, tr | `summary_of_tracked_days`, `insights_trigger_row_count` | "26 / 30 tracked days" | 26 of 30 tracked days | A slash avoids an article or a case ending that depends on the number |
| lt | Watch and complication "Log Migraine", App Shortcut title | "Record" ("Įrašyti") | Log Migraine | 14 characters on a 40 mm watch: "Naujas priepuolis" has 17. The owner approved it. |
| lt | Complication "LOG" | "MARK" ("ŽYMĖ") | LOG | 4 characters under a plus sign; no Lithuanian verb fits. The owner approved it. |
| All but lt | Watch and complication "Log Migraine" | "New attack" | Log Migraine | 14 characters on a 40 mm watch, as in batches 2 and 3. The owner approved it. |
| All | Complication "LOG" | "NEW" | LOG | 4 characters under a plus sign. The owner approved it. |
| ro, fi | Rectangular complication | "Pain-free: 25/30" | 25 of 30 days pain-free | 24 characters with the Romanian "de" and the Finnish cases; the complication sits next to the app name |

### No difference in meaning

- Diagnoses, symptoms, regions, frequency and duration chips, the 15 triggers and their descriptions, the 14 medications and their classes, and the dose unit "mg" with a no-break space in all thirteen sets.
- `common_medical_disclaimer`, for example Polish: "MigraTrack is a diary, not medical advice. Before a change of treatment, one should talk to a doctor."
- `insights_triggers_hint`, for example Czech: "How often you noted each trigger with an attack. This does not show the cause."
- `report_triggers_note`, for example Swedish: "The numbers show how often the patient noted a trigger with an attack. They do not show a cause."
- `report_disclaimer`, for example Turkish: "Data that the patient entered in MigraTrack. This report supports the clinical assessment and does not replace it."
- The other `report_*` keys, the Siri dialogs (da, nb, sv, fi, tr) and the watch strings.
- Privacy Policy, all thirteen texts: no account, no server, no analytics; sleep read only; WatchConnectivity; backups; sharing; Delete All Data; contact; changes. "Logging preset" comes back as "default values for logging", as in batches 2 and 3.
- Terms of Service, all thirteen texts: not a medical device (the legal term of each language: "wyrób medyczny", "zdravotnický prostředek", "zdravotnícka pomôcka", "medicinski proizvod", "medicinski pripomoček", "dispozitiv medical", "orvostechnikai eszköz", "medicinos priemonė", "medicinsk udstyr", "medisinsk utstyr", "medicinteknisk produkt", "lääkinnällinen laite", "tıbbi cihaz"), talk to your clinician, the emergency sentence, backups, personal non-commercial use, "as is", the limitation of liability.
- Each legal translation starts with the sentence that the English text is legally binding.

## Batch 5: Ukrainian, Bulgarian, Kazakh, Greek (October 19, 2026)

Languages: Ukrainian, Bulgarian, Kazakh and Greek. Scope: the keys of the checklist, section 3, the Siri dialogs and watch strings, and `docs/legal/files/{uk,bg,kk,el}/*.md`. None of the four has Siri phrases.

### Fixed

| Language | Key | Back-translation before the fix | English source | Fix |
|---|---|---|---|---|
| el | `common_clear` | "Delete" ("Διαγραφή") | Clear | "Απαλοιφή", the word of Greek iOS. The button removes the choices of one card, not data. |
| el | `diagnosis_tension_type` | "Headache of the tension type" ("τάσης") | Tension-type Headache | Same meaning; the form "τάσεως" of the Eginition leaflets (EL2) |
| kk | `medication_naproxen_500_class` | "Long-acting ҚҚСП" (abbreviation not found in a source) | Long-acting NSAID | Written out: "стероидты емес қабынуға қарсы дәрі" |
| bg, el | `report_column_pain` | "PAI / N" on two lines in the PDF ("БОЛК/А", "ΠΟΝΟ/Σ") | Pain | "Инт.", "Έντ." (intensity), as cs, sk, ro, lt, da, nb, sv, sl in batch 4. Five capital letters do not fit the column. |
| kk | `report_column_date_time` | "DATE AND / TIME" on two lines over the first row of the PDF table | Date & Time | "Күн, уақыт" (date, time) |
| kk | `export_pain_charts` | "Charts of pain inten…" cut at the largest text size | Pain Intensity Charts | "Ауырсыну графиктері" (pain charts); the subtitle names the intensity |
| kk, el | Watch "Pain-free days", `report_stat_pain_free_days` | "Pain-free…" cut on the 40 mm watch at the largest text size | Pain-free days | "Ауырсынусыз", "Χωρίς πόνο" (pain-free); see "Kept" |
| el | `report_stat_episodes` | Two lines in the PDF tile | Episodes recorded | "Σύνολο κρίσεων" (total episodes); the tile has one line |

### Kept, with the reason

| Language | Key | Back-translation | English source | Reason |
|---|---|---|---|---|
| uk, bg, kk | `pain_scale_high` | "10 Exhausting" | 10 Disabling | 14 characters, as lt in batch 4 |
| el | `pain_scale_high` | "10 Unbearable" | 10 Disabling | 14 characters: "Εξουθενωτικός" has 16 with the number |
| uk, bg, kk, el | `common_intensity_of_ten`, `insights_episode_option`, `episode_delete_message` | "Pain 6/10" | Intensity 6/10 | 16 characters, as ru and be in batch 2 |
| uk | `duration_*` | "2–4 h" with "г" | 2–4h | 7 characters: "год" does not fit. "г" is the narrow unit of CLDR for Ukrainian (the iOS formatter writes "12 г"). |
| kk | `duration_*`, `onboarding_episode_duration`, `report_baseline_duration`, `report_profile_duration` | "4–12" in the chip; "Typical duration of the episode, h" in the caption | 4–12h | 7 characters: "сағ" with a range has 8 or 9. Kazakh has no shorter unit, so the unit moves to the caption and the report label. |
| kk | `summary_of_tracked_days`, `insights_trigger_row_count`, `insights_without_triggers`, `report_share_of_episodes` | "26 / 30 tracked days", "of 11 episodes 3" | 26 of 30 tracked days | No case suffix after a placeholder; the slash or the genitive of the whole carries the meaning |
| el | `calendar_migraine_days` | "3 d. of migraine" ("ημ.") | 3 Migraine Days | 18 characters: "ημέρες ημικρανίας" with a number has 20 |
| el | `export_range_*` | "Last 30 days" with "Τελ." | Last 30 Days | 16 characters: "Τελευταίες 30 ημέρες" has 20 |
| kk, el | Watch "Pain-free days", `report_stat_pain_free_days` | "Pain-free" | Pain-free days | 40 mm watch at the largest text size; the term pair makes the report tile equal, as de, fr, fi, lt in batches 3 and 4 |
| uk, bg, kk, el | `strip_legend_not_tracked`, `insights_legend_not_tracked`, `report_legend_not_tracked`, `report_day_not_tracked` | "No data" | Not tracked | 14 characters for the strip ("без спостереження" has 17); the same words in every legend. The calendar and VoiceOver keep "not tracked". |
| uk, bg, kk, el | `settings_member` | "Diary since", "Start", "Member since" | Member | No account exists; the value is the first tracked month (as in batches 2–4) |
| kk, el | `settings_episodes_logged` | "Logged: 35", "Total: 35" | 35 Logged | 14 characters; the caption above says "episodes" |
| el | Every UI text with "trigger" where space is short | "factor" ("παράγοντας") | trigger | "εκλυτικός παράγοντας" is long; titles and the report keep the full term |
| el | `docs/legal/files/el/privacy-policy.md` | "Creator of MigraTrack: Ira Babayed" | MigraTrack is developed by Ira Babayed | Greek verbs and articles show a gender; this form needs none |
| uk | Complication "LOG" | "INPUT" ("ВВІД") | LOG | 4 characters under a plus sign, as the Russian "ВВОД". The owner approved it. |
| bg, kk, el | Complication "LOG" | "NEW" | LOG | 4 characters under a plus sign. The owner approved it. |
| uk, bg, kk, el | Watch and complication "Log Migraine" | "New attack" | Log Migraine | 14 characters on a 40 mm watch, as in batches 2–4. The owner approved it. |
| kk | Rectangular complication | "25/30 days pain-free" | 25 of 30 days pain-free | 24 characters; a slash avoids the case ending |

### No difference in meaning

- Diagnoses, symptoms, regions, frequency chips, the 15 triggers and their descriptions, the 14 medications and their classes, and the dose unit ("мг" in uk and kk, "mg" in bg and el, as their product information writes it) with a no-break space.
- `common_medical_disclaimer`, for example Kazakh: "MigraTrack is a diary, not medical advice. Talk to your doctor before you change the treatment."
- `insights_triggers_hint`, for example Bulgarian: "How often you noted each trigger together with an attack. This does not show a cause."
- `report_triggers_note`, for example Greek: "The numbers show how often the patient noted a factor together with an attack. They do not show a cause."
- `report_disclaimer`, for example Ukrainian: "Data entered by the patient in MigraTrack. The report supplements the clinical assessment and does not replace it."
- The other `report_*` keys, the Siri dialogs and the watch strings.
- Privacy Policy, all four texts: no account, no server, no analytics; sleep read only; WatchConnectivity; backups; sharing; Delete All Data; contact; changes. "Logging preset" comes back as "default values for logging", as in batches 2–4.
- Terms of Service, all four texts: not a medical device ("медичний виріб", "медицинско изделие", "медициналық бұйым", "ιατροτεχνολογικό προϊόν"), talk to your clinician, the emergency sentence, backups, personal non-commercial use, "as is", the limitation of liability.
- Each legal translation starts with the sentence that the English text is legally binding.

## Batch 6: Vietnamese, Indonesian, Malay, Thai (October 19, 2026)

Languages: Vietnamese, Indonesian, Malay and Thai. Scope: the keys of the checklist, section 3, the Siri dialogs and watch strings, and `docs/legal/files/{vi,id,ms,th}/*.md`. Vietnamese, Malay and Thai have Siri phrases; Indonesian has none.

### Fixed

| Language | Key | Back-translation before the fix | English source | Fix |
|---|---|---|---|---|
| vi | `episode_update` | "Update" | Update Episode | "Cập nhật cơn migraine" |
| vi, id | `duration_*` | "4–12 hours" with "giờ", "jam": 8 or 9 characters | 4–12h | "h" and "j", the short units of the two languages; the limit is 7 |
| id | `export_range_last_12_months` | "Last 12 months": 17 characters | Last 12 Months | "1 tahun terakhir" (last 1 year); the limit is 16 |
| th | `pain_scale_middle` | "5 Clearly felt": 15 characters | 5 Noticeable | "5 รู้สึกชัด" (felt clearly) |
| vi | Watch "Last episode %@" | "Latest episode: 12 Oct": 23 characters | Last episode Oct 12 | "Gần nhất: %@" (latest); the limit is 22 |
| th | `summary_sleep_hint`, `insights_without_triggers`, `common_medical_disclaimer`, the PDF trigger and symptom lists | "Settings" split as "การตั้ง / ค่า"; "trigger" split as "สิ่ง / กระตุ้น"; "medical" split as "ทางการ / แพทย์" ("official / doctor") at the largest text size and in the PDF | — | A word joiner inside "สิ่งกระตุ้น", "การตั้งค่า", "ทางการแพทย์", "อดอาหาร", "เหน็บชา", "ผิดปกติ" and "เป็นเวลา" |

### Kept, with the reason

| Language | Key | Back-translation | English source | Reason |
|---|---|---|---|---|
| vi, th | `pain_scale_high` | "10 Very severe" | 10 Disabling | 14 characters, as uk, bg, kk and el in batch 5 |
| vi | `common_clear` | "Deselect" ("Bỏ chọn") | Clear | "Xóa" also means delete; the button removes the choices of one card, not data (as el in batch 5) |
| vi | aura in the UI | "warning sign" ("thoáng báo") | aura | The Vietnamese ICHD-3 says "cơn thoáng báo"; "cơn" is the episode word of the app, so the UI keeps only "thoáng báo". The diagnoses keep the ICHD-3 form. |
| vi | `symptom_photophobia`, `symptom_phonophobia` | "Fear of light", "Fear of sound" | Photophobia (Light), Phonophobia (Sound) | The Vietnamese ICHD-3 term is already the common word, so no word in brackets |
| vi | `insights_period_*`, `insights_bar_week` | "T", "Th", "6Th", "N"; "T1" | W, M, 6M, Y; W1 | 3 characters. "T" (tuần) can also read as a weekday ("T2" = Monday); the bars are under the month chart, which shows weeks only |
| th | `duration_*`, `onboarding_episode_duration`, `report_baseline_duration`, `report_profile_duration` | "4–12" in the chip; "Typical duration of the pain episode (hours)" in the caption | 4–12h | 7 characters: "4–12 ชม." has 8. The unit moves to the caption and the report label, as kk in batch 5 |
| th | `symptom_aphasia` | "Difficulty speaking / brain fog" | Aphasia / Brain Fog | The patient words; the clinical term "ภาวะเสียการสื่อความ" is long and not in TH1 |
| th | `symptom_paresthesia` | "Pins and needles / abnormal sensation" | Tingling / Paresthesia | The second part explains paresthesia in common words |
| vi, id, ms, th | `settings_member` | "Since" | Member | No account exists; the value is the first tracked month (as in batches 2–5) |
| id, ms, th | `report_column_pain` | "Int.", "Int." ("Kmt."), "Level" ("ระดับ") | Pain | 5 characters, as batches 4 and 5 |
| th | `common_save_episode`, watch "Save Episode" | "Save" ("บันทึก") | Save Episode | 14 characters on the watch; the term pair makes the iPhone button equal. "บันทึก" means both save and record, as in Thai iOS |
| vi, id, ms, th | Watch and complication "Log Migraine" | "Record new episode", "New attack", "New attack", "Record episode" | Log Migraine | 14 characters on a 40 mm watch. The owner approved them |
| vi, id, ms, th | Complication "LOG" | "RECORD" ("GHI"), "NEW", "NEW", "NEW" | LOG | 4 characters under a plus sign. The owner approved them |
| id, ms | `pain_scale_high` | "10 Disabling" on two lines at the largest text size | 10 Disabling | The line breaks between the number and the word, nothing is cut |

### No difference in meaning

- Diagnoses, symptoms, regions, frequency chips, the 15 triggers and their descriptions, the 14 medications and their classes, and the dose unit ("mg" in vi, id and ms, "มก." in th) with a no-break space.
- `common_medical_disclaimer`, for example Thai: "MigraTrack is a diary, not medical advice. Please consult a doctor before you change the treatment."
- `insights_triggers_hint`, for example Indonesian: "How often you noted each trigger together with an attack. This does not show a cause."
- `report_triggers_note`, for example Malay: "The numbers show how often the patient noted a trigger together with an attack. These numbers do not show a cause."
- `report_disclaimer`, for example Vietnamese: "Data self-recorded by the patient in MigraTrack. This report supports, and does not replace, the clinical assessment."
- The other `report_*` keys, the Siri dialogs and the watch strings.
- Privacy Policy, all four texts: no account, no server, no analytics; sleep read only; WatchConnectivity; backups; sharing; Delete All Data; contact; changes. "Logging preset" comes back as "default values for logging", as in batches 2–5.
- Terms of Service, all four texts: not a medical device ("thiết bị y tế", "alat kesehatan", "peranti perubatan", "เครื่องมือแพทย์"), talk to your clinician, the emergency sentence, backups, personal non-commercial use, "as is", the limitation of liability.
- Each legal translation starts with the sentence that the English text is legally binding.

## Batch 7: Chinese (Simplified), Chinese (Traditional), Japanese, Korean (October 20, 2026)

Languages: Chinese (Simplified), Chinese (Traditional), Japanese and Korean. Scope: the keys of the checklist, section 3, the Siri dialogs and watch strings, and `docs/legal/files/{zh-Hans,zh-Hant,ja,ko}/*.md`. All four languages have Siri phrases.

### Fixed

| Language | Key | Back-translation before the fix | English source | Fix |
|---|---|---|---|---|
| zh-Hans | `settings_member` | "Time of joining" ("加入时间"): suggests an account | Member | "开始日期" (start date), as zh-Hant and batches 2–6 |
| zh-Hans, zh-Hant, ja | `insights_bar_week` | "Week 1" ("第1周"): 4 characters by the count of the build | W1 | "1周", "1週" (week 1); the limit is 3 |
| zh-Hans | `insights_trend_fewer`, `insights_trend_more` | "%%" after the number: the first draft escaped the percent sign | 25 percent fewer episodes | The word for percent ("百分之"), as in the English source and the other languages |
| ko | Every screen at the default text size, and the PDF table | Words split between two lines: "기록하 / 십시오" (please record), "의 / 사와" (with the doctor), "수 / 마트립탄" (sumatriptan) | — | Korean breaks lines at spaces. The code keeps each Korean word on one line: `keepKoreanWords` in `MtText` and on the welcome screen, and `NSLineBreakStrategyHangulWordPriority` in the PDF |

### Kept, with the reason

| Language | Key | Back-translation | English source | Reason |
|---|---|---|---|---|
| zh-Hans, zh-Hant, ja, ko | `common_save_episode`, watch "Save Episode" | "Save" | Save Episode | 14 characters on the watch; the term pair makes the iPhone button equal. The owner approved it |
| zh-Hans, zh-Hant, ja, ko | Watch and complication "Log Migraine" | "Record migraine" | Log Migraine | The owner approved it |
| zh-Hans, zh-Hant, ja, ko | Complication "LOG" | "Record" | LOG | 4 characters under a plus sign. The owner approved it |
| zh-Hans, zh-Hant, ja, ko | `episode_cranial_epicenter`, `report_column_epicenter` | "Pain location" | Cranial Epicenter | The plain words of a patient; the four ICHD-3 editions have no term for "epicenter" |
| zh-Hans, zh-Hant | `pain_scale_high` | "10 Cannot move about" ("无法活动") | 10 Disabling | The meaning of "disabling" for a headache: the pain stops normal activity |
| ja | `pain_scale_high` | "10 Cannot move" ("動けない") | 10 Disabling | The same reason |
| ko | `pain_scale_high` | "10 Daily life impossible" ("일상 불가") | 10 Disabling | The same reason |
| zh-Hant | `symptom_phonophobia` | "Dislike of noise (sound)" ("怕吵") | Phonophobia (Sound) | The term of the Taiwanese ICHD-3 (TW1, p. 25), which is also the common word |
| ja | `export_symptoms`, `report_symptoms_title` | "Aura and prodromal symptoms" ("前兆と予兆症状") | Aura & Warning Symptoms | "予兆" is the Japanese word for a warning sign before a migraine |
| ja | `symptom_nausea` | "Nausea (feeling sick)" ("悪心（吐き気）") | Nausea | The ICHD-3 term and the common word, as the plan section 6.1 asks |
| ko | `symptom_nausea` | "Nausea (queasiness)" ("구역(메스꺼움)") | Nausea | The same reason |
| zh-Hans, zh-Hant, ja, ko | `settings_member` | "Start date", "Start of use" | Member | No account exists; the value is the first tracked month (as in batches 2–6) |
| zh-Hans, zh-Hant | `region_retro_orbital` | "Behind the eye socket" | Retro-Orbital | The anatomic term in plain words |
| ja, ko | Long sentences at the largest text size | Japanese "くだ / さい" on two lines; Chinese "“健 / 康”" | — | Chinese and Japanese break between any two characters; this is correct, and no line starts with closing punctuation |
| zh-Hans, zh-Hant, ja | Lists in the PDF, the XLSX, Settings and the calendar | Items joined with ", " | — | The code joins lists with ", " for every language. The Chinese and Japanese list mark "、" needs a change of the code and of every language; it is a follow-up |

### No difference in meaning

- Diagnoses (the terms of the four ICHD-3 editions), symptoms, regions, frequency chips, the 15 triggers and their descriptions, the 14 medications and their classes, and the dose unit "mg" with a no-break space.
- `common_medical_disclaimer`, for example Korean: "MigraTrack is a diary and not medical advice. Consult a doctor before you change the treatment."
- `insights_triggers_hint`, for example Simplified Chinese: "How many times you noted each trigger at an episode. This does not mean that it is the cause."
- `report_triggers_note`, for example Japanese: "The count is how often the patient recorded that trigger together with an episode. It does not show a cause."
- `report_disclaimer`, for example Traditional Chinese: "Data recorded by the patient in MigraTrack. This report helps, and does not replace, the clinical assessment."
- The other `report_*` keys, the Siri dialogs ("intensity 6 out of 10" comes back as "6 points out of 10" in Chinese and Korean), and the watch strings.
- Privacy Policy, all four texts: no account, no server, no analytics; sleep read only; WatchConnectivity; backups; sharing; Delete All Data; contact; changes. "Logging preset" comes back as "recording preset", as in batches 2–6.
- Terms of Service, all four texts: not a medical device ("医疗器械", "醫療器材", "医療機器", "의료기기"), talk to your clinician, the emergency sentence, backups, personal non-commercial use, "as is", the limitation of liability.
- Each legal translation starts with the sentence that the English text is legally binding.
