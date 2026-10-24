# Localization glossary

Status: English source terms, October 17, 2026. Russian and Belarusian, October 18, 2026. The Western European and the Central and Northern European batches, October 18, 2026. Ukrainian, Bulgarian, Kazakh and Greek, October 19, 2026. Vietnamese, Indonesian, Malay and Thai, October 19, 2026. Chinese (Simplified), Chinese (Traditional), Japanese and Korean, October 20, 2026. Arabic, Hebrew and Urdu, October 23, 2026. The ten languages of India, October 24, 2026. Each translation batch adds a column for each of its languages and a source for each medical term ([LOCALIZATION_PLAN.md](LOCALIZATION_PLAN.md), sections 6 and 6.1).

Every string of the app uses the terms of this file. One term names one thing. A new string that needs a new term adds it here first.

## Terms

| Term | Definition | In the app | Do not use |
|---|---|---|---|
| episode | One migraine that the user records: start time, intensity, and optional region, symptoms, medications and possible triggers. | "Log Migraine Episode", "Save Episode", "Edit Episode", "Delete Episode", "Episodes recorded", "Last Episode" | attack, event, log (as a noun), entry, record |
| attack | The migraine as the user lives it. The app records each attack as an episode. | Only in the App Store text and keywords, because people search for "migraine attack" | In the app, use episode |
| log (verb) | To record an episode in the app, on the watch, or through Siri. | "Log Migraine Episode", "Log Episode", "logged" | log as a noun ("Episode Log", "Logs"); use episode |
| intensity | The pain intensity of an episode, a whole number from 1 to 10. The user sets it. | "Intensity 6/10", "Pain Intensity", "Peak Intensity", "Typical intensity" | pain level, severity, score, VAS |
| scale 1–10 | The range of intensity. Write it with an en dash and no spaces: "1–10". | "Pain Intensity (1–10)", "Pain Intensity Timeline (1–10)" | "0–10", "1-10", "VAS 0–10" |
| peak intensity | The highest intensity of the episodes of one day. A pain-free day has 0 in the XLSX report. | Insights day card, XLSX sheet Daily Pain | peak pain |
| migraine day | A day with one or more episodes. Two episodes on one day make one migraine day. | "3 Migraine Days", "Migraine days", legend "Migraine" | attack day |
| tracked day | A day from the first day of tracking to today. A migraine day before that day is also a tracked day. | "24 of 30 tracked days" | |
| pain-free day | A tracked day without an episode. | "Pain-free days", "without a logged episode" | |
| not tracked | A day before the first day of tracking, without an episode. It is not a pain-free day. | Legend "Not tracked", XLSX "Not Tracked" | |
| trigger | A factor in the catalog that can come before a migraine, for example "Poor / Irregular Sleep". | Settings and onboarding: known triggers | cause |
| possible trigger | A trigger that the user notes with one episode. The count shows how often the user noted it, not a cause. | "Possible Triggers", "noted in 2 of 5 episodes" | cause, reason |
| known trigger | A trigger in the baseline profile. | "Known triggers" | |
| aura | Visual or sensory symptoms that come before or with the pain. | "Migraine with aura", "Aura & Sensory Symptoms" | |
| symptom | One item of the symptom list of an episode, for example "Photophobia (Light)". | "Aura & Sensory Symptoms" | |
| acute medication | A medication that the user takes during an episode. It pre-fills each new episode. | "Acute / Abortive", "During an episode (acute)" | rescue medication |
| preventive medication | A medication or supplement that the user takes every day. | "Preventive & Supplements", "Every day (preventive)" | |
| medication intake | One medication that the user notes with one episode, with its dose. | "Medication Intakes", "No medication intakes logged in this range." | medication log |
| treatment blueprint | The list of acute and preventive medications in the baseline profile. | "Treatment Blueprint" | |
| baseline profile | The data from onboarding: diagnoses, frequency, typical duration, typical intensity, known triggers and treatment blueprint. | "Baseline Profile", "Set up your baseline profile." | headache profile |
| report | The PDF or XLSX file that Export makes for a clinician. | "MigraTrack report", "Export" | |

## Writing rules for the English source

- Write numbers as digits: "1 episode", "3 migraine days".
- Write the intensity of one episode as "6/10" and the scale as "1–10".
- Write "possible trigger" or "noted", never "cause". Each count of triggers says that it does not show a cause.
- Keep the Title Case of buttons and titles in English. Translations use the case rules of their language.

## Translations

### Russian and Belarusian

Russian (`ru`) and Belarusian (`be`, the official orthography of 2008). The address is polite and in lower case ("вы"), as in iOS. Russian writes "ё". The project owner approved the sample of these terms on October 18, 2026.

| Term | Russian | Belarusian | Note |
|---|---|---|---|
| episode | приступ | прыступ | One word for episode and attack, as patients and the national sources say it. "Эпизод" is not used. |
| attack | приступ | прыступ | App Store text only |
| log (verb) | записать | запісаць | Tab "Записать" / "Запісаць". On the watch and the complication: "Новый приступ" / "Новы прыступ" (max 14 characters). |
| intensity | интенсивность (боли) | інтэнсіўнасць (болю) | With a score: "Боль 6/10" / "Боль 6/10" (max 16 characters) |
| scale 1–10 | 1–10 | 1–10 | |
| peak intensity | максимальная интенсивность; "Макс. интенсивность" in the day card | максімальная інтэнсіўнасць; "Макс. інтэнсіўнасць" | |
| migraine day | день с мигренью | дзень з мігрэнню | The calendar pill in Belarusian: "дзень мігрэні", because "21 дзень з мігрэнню" is longer than 18 characters. |
| tracked day | день наблюдения | дзень назірання | |
| pain-free day | день без боли | дзень без болю | |
| not tracked | без наблюдения | без назірання | |
| trigger | триггер | трыгер | |
| possible trigger | возможный триггер | магчымы трыгер | |
| known trigger | известный триггер | вядомы трыгер | |
| aura | аура | аўра | |
| symptom | симптом | сімптом | |
| acute medication | лекарство для купирования приступа | лякарства для купіравання прыступу | |
| preventive medication | профилактика (лекарство каждый день) | прафілактыка (лякарства кожны дзень) | |
| medication intake | приём лекарства | прыём лякарства | |
| treatment blueprint | план лечения | план лячэння | |
| baseline profile | исходный профиль | зыходны профіль | |
| report | отчёт | справаздача | |
| Apple Health | приложение «Здоровье» | Apple Health | iOS has no Belarusian, so the Belarusian text keeps the product name. |

#### Medical terms and their sources

The sources, checked on October 18, 2026. A page number is the page of the PDF file.

- **[R1]** ICHD-3 in Russian, adapted version of the Russian Society for the Study of Headache: [ICHD-3-Russian.pdf](https://ihs-headache.org/wp-content/uploads/2022/09/ICHD-3-Russian.pdf) on the IHS website.
- **[R2]** Clinical guideline "Мигрень", Ministry of Health of the Russian Federation, ID 295_4, 2024: [PDF](https://apicr.minzdrav.gov.ru/api.ashx?op=GetClinrecPdf&id=295_4), [page](https://cr.minzdrav.gov.ru/view-cr/295_4).
- **[R3]** State Register of Medicinal Products of Russia ([GRLS](https://grls.minzdrav.gov.ru/)). Not checked: the search asks for a CAPTCHA, and Claude does not solve CAPTCHAs. A person can check the medications marked "GRLS open".
- **[B1]** Clinical protocol "Диагностика и лечение пациентов с заболеваниями нервной системы (взрослое население)", resolution of the Ministry of Health of the Republic of Belarus of January 18, 2018 No. 8, as amended by resolution No. 1 of January 5, 2026, section "Мигрень (G43)": [PDF](https://minzdrav.gov.by/upload/dadvfiles/CProtokol/КП_Диагностика_и_лечение_пациентов_с_заболеваниями_нервной_системы_(взрослое_население)_пост_2026_1.pdf), pages 33 and 83. The protocol is in Russian.
- **[B2]** State register of medicinal products of Belarus, [RCETH](https://www.rceth.by/Refbank/reestr_lekarstvennih_sredstv), search by international name in Cyrillic and in Latin.
- **[B3]** Belarusian Encyclopedia, volume 10 (2000), article "Мігрэнь", as quoted on [be.wikipedia.org](https://be.wikipedia.org/wiki/Мігрэнь): "мігрэнь", "прыступ", "галаўны боль", "моташнасць", "лобна-скроневая вобласць". A spelling reference, not a medical source.

No Belarusian ICHD-3 and no medical source in Belarusian was found. The Russian sources give the meaning, and the project owner checks each Belarusian term.

| Term | Russian | Source (ru) | Belarusian | Source (be) |
|---|---|---|---|---|
| Migraine without aura | Мигрень без ауры | R1, 1.1, p. 20 | Мігрэнь без аўры | R1 meaning; B3 "мігрэнь"; owner |
| Migraine with aura | Мигрень с аурой | R1, 1.2, p. 22 | Мігрэнь з аўрай | R1 meaning; owner |
| Visual or sensory aura | зрительная или сенсорная аура | R1, 1.2 criterion B, p. 22 | зрокавая або сэнсарная аўра | Owner |
| Chronic migraine | Хроническая мигрень | R1, 1.3, p. 26 | Хранічная мігрэнь | Owner |
| Tension-type headache | Головная боль напряжения | R1, 2, p. 31 | Галаўны боль напружання | B3 "галаўны боль"; owner |
| Cluster headache | Кластерная головная боль | R1, 3.1, p. 35; R2, p. 10, 26 ("кластерной (пучковой)") | Кластарны галаўны боль | Owner |
| attack | приступ | R1, throughout; R2, 3.2 | прыступ | B3 |
| Photophobia | фотофобия (светобоязнь) | R1, 1.1 criterion D, p. 21; glossary "Светобоязнь", p. 170 | фотафобія (святлабоязь) | Owner |
| Phonophobia | фонофобия (звукобоязнь) | R1, 1.1 criterion D, p. 21; glossary "Фонофобия", p. 170. "Звукобоязнь" is the common word; not in R1 or R2. | фанафобія (гукабоязь) | Owner |
| Scintillating scotoma | мерцательная скотома | R1 has "скотома" and "сцинтилляция" (glossary, p. 170); R2 has "скотомы" (p. 13, 28). The combined term "мерцательная скотома" is the common clinical name, but neither R1 nor R2 contains it: **not verified**. | мігатлівая скатома | Owner |
| Nausea | тошнота | R1, 1.1 criterion D, p. 21 | моташнасць | B3 |
| Aphasia | афазия | R1, p. 22; R2, p. 17 | афазія | Owner |
| Paresthesia | парестезии | R1, p. 47; R2, p. 28, 34 | парэстэзіі | Owner |
| Neck stiffness | скованность шеи | Common words; not in R1 or R2. Not "ригидность затылочных мышц", which is a meningeal sign. | скаванасць шыі | Owner |
| Temple | висок | R2, p. 12 ("области виска") | скронь | B3 ("лобна-скроневая") |
| Retro-orbital | за глазом | R1, p. 45 ("ретроорбитальной"); the app uses the common words | за вокам | Owner |
| Occipital | затылок | R2, p. 12 ("области затылка") | патыліца | Owner |
| Trigger | триггер | R2, "Термины и определения", p. 7 ("Триггеры приступа мигрени") | трыгер | Owner |
| Acute treatment | купирование приступа | R2, 3.2, p. 30 | купіраванне прыступу | B1, p. 33 ("Для купирования приступа мигрени") |
| Preventive treatment | профилактика | R2, 3.3, p. 38 | прафілактыка | B1, p. 33 ("Профилактическое лечение") |
| Headache diary | дневник головной боли | R2, p. 19, 26 | дзённік | Owner |
| Dose unit | мг | R2, p. 30–43 | мг | B2 |

| Medication (class) | Russian | Source (ru) | Belarusian | Source (be) |
|---|---|---|---|---|
| Ibuprofen | Ибупрофен | R2, p. 30–32 (400 мг); GRLS open | Ібупрафен | B1, p. 33; B2: 25 records |
| Sumatriptan | Суматриптан | R2, p. 33–34; GRLS open | Суматрыптан | B1, p. 33; B2: 4 records |
| Rizatriptan | Ризатриптан | R2, p. 33–34 (10 мг, "диспергируемые в полости рта"); GRLS open | Рызатрыптан | B2: 1 record, "Капориза", orally dispersible 10 mg. Not in B1. |
| Naproxen | Напроксен | R2, p. 31 (500 мг); GRLS open | Напраксен | B2: 4 records |
| Acetaminophen | Парацетамол | R2, p. 20; GRLS open | Парацэтамол | B1, p. 33; B2: 38 records |
| Ubrogepant | Уброгепант | Not in R2; GRLS open. **No registration found**; the INN in Cyrillic (plan, section 6.1). | Убрагепант | Not in B2 (Cyrillic and Latin). **No registration**; the INN in Cyrillic. |
| Metoclopramide | Метоклопрамид | R2, p. 33; GRLS open | Метаклапрамід | B1, p. 33; B2: 3 records |
| Riboflavin | Рибофлавин | Not in R2; GRLS open | Рыбафлавін | B2: only in combinations. **No single-substance registration found.** |
| Magnesium | Магний | R2 has only magnesium sulfate i.v. (p. 36–37); oral magnesium is not in R2; GRLS open | Магній | B2: magnesium citrate and aspartate |
| Propranolol | Пропранолол | R2, p. 40, 42; GRLS open | Прапранолол | B1, p. 33; B2: "Анаприлин" 40 mg |
| Topiramate | Топирамат | R2, p. 40; GRLS open | Тапірамат | B1, p. 33; B2: 1 record |
| Amitriptyline | Амитриптилин | R2, p. 42–43; GRLS open | Амітрыптылін | B1, p. 33; B2: 3 records under "Amitriptyline" |
| Erenumab | Эренумаб | R2, p. 41, 43; GRLS open | Эрэнумаб | Not in B2 (Cyrillic and Latin). **No registration**; the INN in Cyrillic. |
| Triptan, serotonin agonist | триптан, агонист серотонина | R2, p. 18, 33 ("агонисты серотониновых 5-HT1-рецепторов (триптаны)") | трыптан, аганіст сератаніну | B1, p. 33 |
| NSAID | НПВП | R2, p. 4 | НПВС | B1, p. 33 uses "НПВС". The Belarusian text keeps this Russian abbreviation; owner check. |
| Antiemetic | противорвотное средство | R2, p. 33 | супрацьрвотны сродак | B1 says "прокинетики"; owner |
| Beta blocker | бета-адреноблокатор | R2, p. 40 | бэта-адрэнаблакатар | B1, p. 33 |
| Anticonvulsant | противоэпилептическое средство | R2, p. 42 ("Противоэпилептические") | супрацьэпілептычны сродак | B1, p. 33 ("противоэпилептические ЛП") |
| Tricyclic antidepressant | трициклический антидепрессант | R2, p. 4 | трыцыклічны антыдэпрэсант | Owner |
| CGRP | КГРП | R2, p. 4–6 ("кальцитонин-ген-родственный пептид") | CGRP | No Belarusian abbreviation found; the Latin one stays |
| Gepant | гепант | The INN stem; not in R2 | гепант | Owner |

### Western Europe

German (`de`), French (`fr`, Canada `fr-CA`), Spanish (`es`, Latin American text; Spain `es-ES`), Portuguese (`pt`, Brazilian text; Portugal `pt-PT`), Italian (`it`), Dutch (`nl`) and Catalan (`ca`). The project owner approved the address, the word for episode, the medical disclaimer and the watch texts on October 18, 2026. No native speaker reads these languages before a release ([LOCALIZATION_PLAN.md](LOCALIZATION_PLAN.md), section 7).

| Language | Address | Buttons |
|---|---|---|
| de | "Sie" | Infinitive: "Speichern", "Abbrechen" |
| fr, fr-CA | "vous" | Infinitive: "Enregistrer", "Annuler". France: narrow no-break space before ? ! ;. Quebec (OQLF): no space before ? ! ;. Both: no-break space before : and inside « ». |
| es, es-ES | "usted" | Infinitive: "Guardar", "Cancelar" |
| pt | "você", as Brazilian software does | Infinitive: "Salvar", "Cancelar" |
| pt-PT | Impersonal third person with "o seu" | Infinitive: "Guardar", "Cancelar" |
| it | "Lei", written in lower case ("il suo medico") | The imperative of Italian software: "Salva", "Annulla" |
| nl | "u" | Infinitive: "Opslaan", "Annuleren" |
| ca | "vós", as Softcatalà and Firefox in Catalan do | The imperative of Catalan software: "Desa", "Cancel·la" |

| Term | de | fr | es | pt | it | nl | ca |
|---|---|---|---|---|---|---|---|
| episode | Attacke | crise | crisis | crise | attacco | aanval | crisi |
| attack (App Store only) | Attacke, Anfall | crise | crisis, ataque | crise | attacco | aanval | crisi |
| migraine | Migräne | migraine | migraña | enxaqueca | emicrania | migraine | migranya |
| log (verb) | eintragen | noter | registrar | registrar (pt-PT registar) | annotare | noteren | anotar |
| intensity | Intensität, Schmerzintensität | intensité | intensidad | intensidade; "Dor 6/10" with a score | intensità | intensiteit; "Pijn 6/10" with a score | intensitat |
| migraine day | Migränetag | jour de migraine; pill "3 j de migraine" | día con migraña; pill "Migraña: 3 días" | dia de enxaqueca; pill "Enxaqueca: 3 dias" | giorno con emicrania; pill "Emicrania: 3 gg" | migrainedag | dia amb migranya; pill "Migranya: 3 dies" |
| tracked day | erfasster Tag | jour suivi | día con seguimiento | dia monitorado (pt-PT monitorizado) | giorno monitorato | bijgehouden dag | dia amb seguiment |
| pain-free day | schmerzfreier Tag; short "Schmerzfrei" | jour sans douleur; short "Sans douleur" | día sin dolor | dia sem dor | giorno senza dolore; short "Senza dolore" | pijnvrije dag | dia sense dolor |
| not tracked | nicht erfasst | non suivi | sin seguimiento; strip "Sin datos" | não monitorado (pt-PT não monitorizado; strip "Sem dados") | non monitorato | niet bijgehouden; strip "Geen gegevens" | sense seguiment; strip "Sense dades" |
| trigger | Auslöser | déclencheur | desencadenante | gatilho (pt-PT desencadeante) | fattore scatenante | trigger | desencadenant |
| possible trigger | möglicher Auslöser | déclencheur possible | posible desencadenante | possível gatilho | possibile fattore scatenante | mogelijke trigger | possible desencadenant |
| known trigger | bekannter Auslöser | déclencheur connu | desencadenante conocido | gatilho conhecido | fattore scatenante noto | bekende trigger | desencadenant conegut |
| aura | Aura | aura | aura | aura | aura | aura | aura |
| symptom | Symptom | symptôme | síntoma | sintoma | sintomo | klacht | símptoma |
| acute medication | Akutmedikament, Akutbehandlung | traitement de crise | tratamiento de la crisis | tratamento da crise | terapia dell'attacco | aanvalsmedicatie | tractament de la crisi |
| preventive medication | Prophylaxe | traitement de fond | preventivo | preventivo | profilassi | preventief | preventiu |
| medication intake | Einnahme | prise | toma | uso (pt-PT toma) | assunzione | inname | presa |
| treatment blueprint | Behandlungsplan | plan de traitement | plan de tratamiento | plano de tratamento | piano terapeutico | behandelplan | pla de tractament |
| baseline profile | Ausgangsprofil | profil de référence | perfil inicial | perfil inicial | profilo iniziale | basisprofiel | perfil inicial |
| report | Bericht | rapport | informe | relatório | report | rapport | informe |
| Apple Health | Apple Health, Health-App | app Santé | app Salud | app Saúde | app Salute | Gezondheid-app | app Salut |
| Settings (iOS) | Einstellungen | Réglages | Configuración (es-ES Ajustes) | Ajustes (pt-PT Definições) | Impostazioni | Instellingen | Configuració |

Watch and complication texts (term pairs of the build): "Log Migraine" = "Neue Attacke", "Nouvelle crise", "Nueva crisis", "Nova crise", "Nuovo attacco", "Nieuwe aanval", "Nova crisi". "Save Episode" = "Speichern", "Enregistrer", "Guardar", "Salvar" (pt-PT "Guardar"), "Salva", "Opslaan", "Desa". The round complication "LOG" (4 characters) = "NEU", "NOTE", "NOTA", "NOVA", "NOTA", "MELD", "NOVA".

#### Sources of the Western European terms

Checked on October 18, 2026. A page number is the page of the PDF file. "Records" counts the entries of a register file that name the substance.

- **[DE1]** ICHD-3, German translation of the DMKG: [3385_ichd-3-german.pdf](https://ihs-headache.org/wp-content/uploads/2020/05/3385_ichd-3-german.pdf) on the IHS website.
- **[DE2]** S1 guideline "Therapie der Migräneattacke und Prophylaxe der Migräne", DGN and DMKG, AWMF 030-057, November 2025: [PDF](https://register.awmf.org/assets/guidelines/030-057l_S1_Therapie-der-Migraeneattacke-Prophylaxe-der-Migraene_2025-11.pdf).
- **[DE3]** German drug register AMIce (PharmNet.Bund). **Not searched**: the public search opens through a BfArM sign-in. [EU1] covers erenumab; [DE2] names the other substances.
- **[EU1]** EMA, Aimovig (erenumab) product information in German, French, Italian, Dutch, Spanish and Portuguese: [German PDF](https://www.ema.europa.eu/de/documents/product-information/aimovig-epar-product-information_de.pdf) and the same path for the other languages.
- **[FR1]** ICHD-3, French translation: [3628_ichd-3-french.pdf](https://ihs-headache.org/wp-content/uploads/2020/05/3628_ichd-3-french.pdf).
- **[FR2]** Lantéri-Minet M. et al., SFEMC recommendations for migraine in adults, Rev Neurol 2012 (French): [PDF](https://www.sfetd-douleur.org/wp-content/uploads/2021/02/Lanteri-Minet-et-al-Rev-Neurol-2012-1.pdf). The 2021 update of the SFEMC is in English.
- **[FR3]** Base de données publique des médicaments, file [CIS_COMPO_bdpm.txt](https://base-donnees-publique.medicaments.gouv.fr/download/file/CIS_COMPO_bdpm.txt), active substances.
- **[QC1]** Health Canada, Drug Product Database, extract [allfiles.zip](https://www.canada.ca/content/dam/hc-sc/documents/services/drug-product-database/allfiles.zip) (marketed products, French ingredient names), file of October 5, 2026.
- **[QC2]** INESSS, "Traitement de la migraine chez l'adulte", report in support of the optimal-use guide, September 2025: [BAnQ copy](https://collections.banq.qc.ca/document/s/t/ZGIpXSML16D5RlAgkomDqw).
- **[ES1]** ICHD-3, Spanish translation: [3559_ichd-3-spanish.pdf](https://ihs-headache.org/wp-content/uploads/2020/05/3559_ichd-3-spanish.pdf).
- **[ES2]** Sociedad Española de Neurología, "Manual de Práctica Clínica en Cefaleas", 2020: [copy at areasaludbadajoz.com](https://areasaludbadajoz.com/docencia_investigacion/biblioteca/Te_puede_interesar/ManualCefaleas2020.pdf).
- **[ES3]** AEMPS, CIMA register, REST search by first active substance, marketed products only (`cima.aemps.es/cima/rest/medicamentos?practiv1=…&comerc=1`).
- **[MX1]** IMSS, clinical practice guideline "Diagnóstico y tratamiento de cefalea tensional y migraña en el adulto", IMSS-047-08. imss.gob.mx answered 403, so the pages are those of the [copy at maternoinfantil.gov.co](https://maternoinfantil.gov.co/wp-content/uploads/2022/09/GUIA-DE-PRACTICA-CLINICA-GPC-DIAGNOSTICO-Y-TRATAMIENTO-DE-CEFALEA-TENSIONAL-Y-MIGRANA-PRIMER-Y-SEGUNDO-NIVEL-DE-ATENCION.pdf).
- **[MX2]** COFEPRIS register of sanitary records. **Not searched**: the public search did not load.
- **[PT1]** ICHD-3, Portuguese translation (Portugal): [3391_ichd-3-portuguese.pdf](https://ihs-headache.org/wp-content/uploads/2020/05/3391_ichd-3-portuguese.pdf).
- **[PT2]** Sociedade Portuguesa de Cefaleias, "Recomendações Terapêuticas para Cefaleias 2021", supplement of Sinapse 21: [PDF](https://old.sinapse.pt/edition_download.php?id=67).
- **[PT3]** INFARMED, Prontuário Terapêutico online, name index (`app10.infarmed.pt/prontuario/mostraono.php`). The register Infomed answered 503 and was **not searched**.
- **[BR1]** ICHD-3, Brazilian Portuguese translation: [ICHD-3-Brazilian-Portuguese.pdf](https://ihs-headache.org/wp-content/uploads/2021/03/ICHD-3-Brazilian-Portuguese.pdf). It uses "migrânea"; it lists "enxaqueca clássica" among the former terms (p. 27).
- **[BR2]** DATASUS, CID-10 in Portuguese, [G40–G47](http://www2.datasus.gov.br/cid10/V2008/WebHelp/g40_g47.htm): "G43 Enxaqueca", "G43.0 Enxaqueca sem aura", "G43.1 Enxaqueca com aura".
- **[BR3]** ANVISA, open data of registered medicines, [DADOS_ABERTOS_MEDICAMENTOS.csv](https://dados.anvisa.gov.br/dados/DADOS_ABERTOS_MEDICAMENTOS.csv), registrations with the status "Ativo".
- **[IT1]** ICHD-3, Italian translation: [ICHD-3-Italian.pdf](https://ihs-headache.org/wp-content/uploads/2021/11/ICHD-3-Italian.pdf).
- **[IT2]** AIFA, active substances per package, [PA_confezioni.csv](https://drive.aifa.gov.it/farmaci/PA_confezioni.csv). The search found no Italian-language national migraine guideline.
- **[NL1]** NHG-Standaard Hoofdpijn (M19), published September 2021, last change April 2025: [richtlijnen.nhg.org](https://richtlijnen.nhg.org/standaarden/hoofdpijn). The page has sections, no page numbers. No Dutch ICHD-3 was found.
- **[NL2]** CBG, Geneesmiddeleninformatiebank, file [metadata.csv](https://www.geneesmiddeleninformatiebank.nl/metadata.csv), field WERKZAMESTOFFEN.
- **[CAT1]** Generalitat de Catalunya, Canal Salut, [Migranya](https://canalsalut.gencat.cat/ca/salut-a-z/m/migranya/), updated March 24, 2026.
- **[CAT2]** Catalan Wikipedia, article [Migranya](https://ca.wikipedia.org/wiki/Migranya) and the articles of the substances, as a spelling reference. Not a medical source. TERMCAT and the IEC dictionary load their results with scripts that the check could not read: **not searched**.
- The reference country of Catalan is Spain, so [ES1]–[ES3] give the meaning and the registration.

#### Medical terms: German, French, French (Canada)

| Term | German | Source (de) | French | Source (fr) | fr-CA |
|---|---|---|---|---|---|
| Migraine without aura | Migräne ohne Aura | DE1, 1.1, p. 31, 53 | Migraine sans aura | FR1, 1.1, p. 33, 35 | Same; QC2, p. 14 |
| Migraine with aura | Migräne mit Aura | DE1, 1.2, p. 31 | Migraine avec aura | FR1, 1.2, p. 33 | Same |
| Chronic migraine | Chronische Migräne | DE1, 1.3, p. 31 | Migraine chronique | FR1, 1.3, p. 33 | Same |
| Tension-type headache | Kopfschmerz vom Spannungstyp | DE1, 2, p. 31 | Céphalée de tension | FR1, 2, p. 32 | Same |
| Cluster headache | Clusterkopfschmerz | DE1, 3.1, p. 31 | Algie vasculaire de la face | FR1, 3.1, p. 87 | Same |
| attack | Attacke | DE1, throughout; DE2, title | crise | FR1, p. 36; FR2, p. 4 | QC2, p. 19 |
| Photophobia | Photophobie | DE1, 1.1 D, p. 53 | photophobie | FR1, 1.1, p. 35 | QC2, p. 14 |
| Phonophobia | Phonophobie | DE1, 1.1 D, p. 53 | phonophobie | FR1, 1.1, p. 35 | QC2, p. 14 |
| Scintillating scotoma | Flimmerskotom | DE1, p. 193 | scotome scintillant | FR1, p. 161 | Same |
| Nausea | Übelkeit | DE1, p. 53 | nausée | FR1, p. 35 | QC2, p. 14 |
| Aphasia | Aphasie | DE1, p. 56 | aphasie | FR1, p. 39 | Same |
| Paresthesia | Parästhesien | DE1, p. 56 | paresthésies | FR1, p. 118 | Same |
| Neck stiffness | Nackensteifigkeit | DE1, p. 59 | raideur de la nuque | FR1, p. 35 ("raideur et/ou douleur cervicale") | Same |
| Retro-orbital | hinter dem Auge | DE1, p. 340 ("retroorbital"); the app uses common words | derrière l'œil | FR1, p. 113 ("rétro-orbitaire") | Same |
| Occipital | Hinterkopf | DE1, p. 53 ("okzipital") | arrière de la tête | FR1, p. 36 ("occipitale") | Same |
| Trigger | Auslöser | DE2, p. 43 ("Migränetrigger"), p. 50 ("Auslöser") | déclencheur | FR1, p. 111 | QC2, p. 19 |
| Acute treatment | Akutbehandlung | DE2, p. 6 ("Akuttherapie") | traitement de crise | FR2, p. 4 | QC2 |
| Preventive treatment | Prophylaxe | DE2, title | traitement de fond | FR2, p. 6 | QC2, p. 8 ("prophylactique") |
| Headache diary | Kopfschmerztagebuch | DE1, p. 68; DE2, p. 53 | agenda des crises | FR1, p. 14; FR2, p. 4 | calendrier de céphalées, QC2, p. 9 |
| Dose unit | mg, with a space | DE2, p. 19 | mg, with a space | FR2, p. 5 | QC2, p. 31 |

| Medication (class) | German | Source (de) | French | Source (fr) | fr-CA | Source (fr-CA) |
|---|---|---|---|---|---|---|
| Ibuprofen | Ibuprofen | DE2, p. 19 (400 mg) | Ibuprofène | FR3: 106 records | Ibuprofène | QC1: 67 products |
| Sumatriptan | Sumatriptan | DE2, p. 8 | Sumatriptan | FR3: 10; FR2, p. 5 | Sumatriptan | QC1: 34 |
| Rizatriptan | Rizatriptan | DE2, p. 8 | Rizatriptan | FR3: 11; FR2, p. 4 | Rizatriptan | QC1: 34 |
| Naproxen | Naproxen | DE2, p. 19 (500 mg) | Naproxène | FR3: 12 | Naproxène | QC1: 39 |
| Acetaminophen | Paracetamol | DE2, p. 19 (1000 mg) | Paracétamol | FR3: 221 | **Acétaminophène** | QC1: 241; QC2, p. 34 |
| Ubrogepant | Ubrogepant | DE2, p. 39: FDA approval only, **no EU authorization**; the INN | Ubrogépant | FR3: 0. **No registration**; the French INN | Ubrogépant | QC1: 2 (UBRELVY) |
| Metoclopramide | Metoclopramid | DE2, p. 19 (10 mg) | Métoclopramide | FR3: 7 | Métoclopramide | QC1: 10 |
| Riboflavin | Riboflavin | DE2, p. 61 | Riboflavine | FR3: 4, combinations only | Riboflavine | QC1: 7, multivitamins only |
| Magnesium | Magnesium | DE2, p. 53 | Magnésium | FR3: 139 (salts) | Magnésium | QC1: 157 (salts) |
| Propranolol | Propranolol | DE2, p. 11 | Propranolol | FR3: 10; FR2, p. 6 | Propranolol | QC1: 17 |
| Topiramate | Topiramat | DE2, p. 11 | Topiramate | FR3: 21; FR2, p. 6 | Topiramate | QC1: 42 |
| Amitriptyline | Amitriptylin | DE2, p. 11 | Amitriptyline | FR3: 7; FR2, p. 6 | Amitriptyline | QC1: 15 |
| Erenumab | Erenumab | DE2, p. 11; EU1 | Érénumab | FR3: 2 ("ÉRÉNUMAB"); EU1 writes "erenumab" | Érénumab | QC1: 2 (AIMOVIG) |
| NSAID | NSAR | DE2, p. 10 | AINS | FR2, p. 4 | AINS | QC2, p. 13 |
| Triptan, serotonin agonist | Triptan, Serotoninagonist | DE2, p. 8 | triptan, agoniste de la sérotonine | FR2, p. 4 | Same | |
| Antiemetic | Antiemetikum | DE2, p. 9 | antiémétique | FR2, p. 5 (métoclopramide) | Same | QC2, p. 27 |
| Beta blocker | Betablocker | DE2, p. 11 | bêtabloquant | FR2, p. 6 | Same | QC2, p. 39 |
| Anticonvulsant | Antikonvulsivum | DE2, p. 11 ("Antikonvulsiva") | antiépileptique | FR2, p. 6 | Same | QC2, p. 39 ("anticonvulsivants") |
| Tricyclic antidepressant | Trizyklisches Antidepressivum | DE2, p. 69 | antidépresseur tricyclique | FR2, p. 9 | Same | QC2, p. 41 |
| Gepant, CGRP | Gepant, CGRP | DE2, p. 39 | gépant, CGRP | QC2, p. 6 | Same | QC2 |

#### Medical terms: Spanish, Spanish (Spain), Catalan

| Term | Spanish | Source (es, Mexico) | es-ES | Source (es-ES) | Catalan | Source (ca) |
|---|---|---|---|---|---|---|
| Migraine without aura | Migraña sin aura | ES1, 1.1, p. 21 | Same | ES1 | Migranya sense aura | CAT1 |
| Migraine with aura | Migraña con aura | ES1, 1.2, p. 21 | Same | ES1 | Migranya amb aura | CAT1 |
| Chronic migraine | Migraña crónica | ES1, 1.3, p. 27 | Same | ES1 | Migranya crònica | CAT1 |
| Tension-type headache | Cefalea de tipo tensional | ES1, p. 27; MX1, title ("cefalea tensional") | Same | ES1 | Cefalea tensional | CAT2 |
| Cluster headache | Cefalea en racimos | ES1, 3.1, p. 51 | Same | ES1 | Cefalea en raïms | CAT2 (article title) |
| attack | crisis | MX1, p. 39; ES1, p. 21 | crisis | ES2, p. 34 | crisi | CAT1 ("crisis") |
| Photophobia | fotofobia | MX1, p. 8; ES1, p. 43 | Same | ES2, p. 90 | fotofòbia | CAT2 |
| Phonophobia | fonofobia | MX1, p. 8; ES1, p. 44 | Same | ES2, p. 81 | fonofòbia | CAT2 |
| Scintillating scotoma | escotoma centelleante | ES1, p. 22 | Same | ES1 | escotoma centellejant | CAT2 has "escotoma"; "centellejant" **not verified** |
| Nausea | náuseas | MX1, p. 8; ES1, p. 23 | Same | ES1 | nàusees | CAT2 |
| Aphasia | afasia | ES1, p. 21 | Same | ES2, p. 248 | afàsia | CAT2 |
| Paresthesia | parestesias | ES1, p. 70 | Same | ES2, p. 177 | parestèsies | CAT2 |
| Neck stiffness | rigidez de cuello | ES1, p. 23 ("rigidez cervical") | Same | ES2, p. 179 | rigidesa al coll | CAT2 ("rigidesa") |
| Trigger | desencadenante | ES1, p. 42 | Same | ES2, p. 40 | desencadenant | CAT1 |
| Acute treatment | tratamiento de la crisis | MX1, p. 6 | Same | ES2, p. 85 | tractament de la crisi | CAT1 |
| Preventive treatment | preventivo | MX1, p. 26 | Same | ES2, p. 7 | preventiu | CAT1 |
| Headache diary | diario de cefaleas | ES1, p. 27 | Same | ES1 | diari | CAT2 |

| Medication (class) | Spanish | Source (es) | es-ES | Source (es-ES) | Catalan | Source (ca) |
|---|---|---|---|---|---|---|
| Ibuprofen | Ibuprofeno | MX1, p. 10 (400 mg) | Same | ES3: 157; ES2, p. 87 | Ibuprofèn | CAT1; CAT2 |
| Sumatriptan | Sumatriptán | MX1, p. 20, 23 | Same | ES3: 11; ES2, p. 87 | Sumatriptan | CAT2 |
| Rizatriptan | Rizatriptán | MX1, p. 23 | Same | ES3: 29; ES2, p. 87 | Rizatriptan | ES3; no Catalan source found, the INN |
| Naproxen | Naproxeno | MX1, p. 22 (500 mg) | Same | ES3: 15 | Naproxè | CAT1; CAT2 |
| Acetaminophen | Paracetamol | MX1, p. 10 (1000 mg) | Same | ES3: 264 | Paracetamol | CAT2 |
| Ubrogepant | Ubrogepant | MX2 not searched: **not verified** | Same | ES3: 0. **No registration**; the INN | Ubrogepant | **No registration** (ES3) |
| Metoclopramide | Metoclopramida | MX1, p. 11 (10 mg) | Same | ES3: 8 | Metoclopramida | CAT2 |
| Riboflavin | Riboflavina | MX1, p. 29 | Same | ES3: 5, combinations and infusions only | Riboflavina | CAT2 |
| Magnesium | Magnesio | MX1, p. 29 | Same | ES3: 125 (salts) | Magnesi | CAT2 |
| Propranolol | Propranolol | MX1, p. 15 | Same | ES3: 11; ES2, p. 95 | Propranolol | CAT2 |
| Topiramate | Topiramato | MX1, p. 27 | Same | ES3: 57; ES2, p. 95 | Topiramat | CAT2 |
| Amitriptyline | Amitriptilina | MX1, p. 11 | Same | ES3: 13; ES2, p. 94 | Amitriptilina | CAT2 |
| Erenumab | Erenumab | MX2 not searched: **not verified** | Same | ES3: 2; EU1 | Erenumab | CAT2; ES3 |
| NSAID | AINE | MX1, p. 6 ("AINES") | Same | ES2, p. 85 | AINE | CAT1 |
| Triptan | triptán | MX1, p. 23 | Same | ES2, p. 87 | triptan | CAT1 |
| Antiemetic | antiemético | MX1, p. 6 | Same | ES2, p. 85 | antiemètic | ES2 meaning |
| Beta blocker | betabloqueador | MX1, p. 26 ("Beta Bloqueadores") | **betabloqueante** | ES2, p. 94 | betablocador | ES2 meaning; **not verified** in Catalan |
| Anticonvulsant | anticonvulsivo | MX1, p. 27 | **antiepiléptico** | ES2, p. 94 ("neuromoduladores") | antiepilèptic | ES2 meaning |
| Tricyclic antidepressant | antidepresivo tricíclico | MX1, p. 11 | Same | ES2, p. 128 | antidepressiu tricíclic | ES2 meaning |

#### Medical terms: Portuguese (Brazil), Portuguese (Portugal)

| Term | pt | Source (pt) | pt-PT | Source (pt-PT) |
|---|---|---|---|---|
| Migraine without aura | Enxaqueca sem aura | BR2, G43.0; BR1, 1.1, p. 21 ("Migrânea sem aura") | Enxaqueca sem aura | PT1, p. 17 |
| Migraine with aura | Enxaqueca com aura | BR2, G43.1; BR1, p. 21 | Enxaqueca com aura | PT1, p. 17 |
| Chronic migraine | Enxaqueca crônica | BR1, p. 21 ("Migrânea crônica") | Enxaqueca crónica | PT1, p. 17 |
| Tension-type headache | Cefaleia do tipo tensão | BR1, p. 22 | Cefaleia tipo tensão | PT1, p. 17 |
| Cluster headache | Cefaleia em salvas | BR1, 3.1, p. 46 | Same | PT1, 3.1, p. 38 |
| attack | crise | BR1, p. 26 | crise | PT1; PT2, p. 12 |
| Photophobia | fotofobia | BR1, p. 26 | Same | PT1, p. 21 |
| Phonophobia | fonofobia | BR1, p. 26 | Same | PT1, p. 21 |
| Scintillating scotoma | escotoma cintilante | BR1, p. 27 ("escotoma", "borda cintilante") | Same | PT1, p. 23 |
| Nausea | náusea | BR1, p. 26 | Same | PT1, p. 21 ("náuseas") |
| Aphasia | afasia | BR1, p. 27 | Same | PT1, p. 23 |
| Paresthesia | parestesia | BR1, p. 27 | Same | PT1, p. 48 |
| Neck stiffness | rigidez no pescoço | BR1, p. 25 ("rigidez e/ou dor cervical") | Same | PT1, p. 21 |
| Trigger | gatilho | Common Brazilian word; BR1 has no single term. **Not verified** in a clinical source | desencadeante | PT2, p. 8 |
| Acute treatment | tratamento da crise | BR1 | Same | PT2, p. 12 |
| Preventive treatment | preventivo | BR1 | Same | PT2, p. 5 |
| Headache diary | diário | BR1, p. 28 | Same | PT2, p. 10 ("diário das cefaleias") |

| Medication (class) | pt | Source (pt) | pt-PT | Source (pt-PT) |
|---|---|---|---|---|
| Ibuprofen | Ibuprofeno | BR3: 824 active records | Ibuprofeno | PT3; PT2, p. 12 |
| Sumatriptan | Sumatriptana | BR3: 18 ("succinato de sumatriptana") | Sumatriptano | PT3; PT2, p. 13 |
| Rizatriptan | Rizatriptana | BR3: 26 ("benzoato de rizatriptana") | Rizatriptano | PT3; PT2, p. 13 |
| Naproxen | Naproxeno | BR3: 187 | Naproxeno | PT3; PT2, p. 12 |
| Acetaminophen | Paracetamol | BR3: 1098 | Paracetamol | PT3; PT2, p. 9 |
| Ubrogepant | Ubrogepanto | BR3: 0. **No registration**; the INN with the Brazilian ending, as in "aprepitanto" | Ubrogepant | PT2, p. 15 names it; not in PT3; EU: no authorization (DE2, p. 39). **No registration** |
| Metoclopramide | Metoclopramida | BR3: 58 | Metoclopramida | PT3; PT2, p. 12 (10 mg) |
| Riboflavin | Riboflavina | BR3: 59, combinations only | Riboflavina | PT2, p. 10; not in PT3 |
| Magnesium | Magnésio | BR3: 612 (salts, combinations) | Magnésio | PT3 (magnesium salts); PT2, p. 10 |
| Propranolol | Propranolol | BR3: 137 | Propranolol | PT3; PT2, p. 14 |
| Topiramate | Topiramato | BR3: 260 | Topiramato | PT3; PT2, p. 6 |
| Amitriptyline | Amitriptilina | BR3: 101 | Amitriptilina | PT3; PT2, p. 20 |
| Erenumab | Erenumabe | BR3: one registration (PASURTA), **inactive**; the Brazilian name | Erenumab | PT2, p. 6; EU1 |
| NSAID | AINE | Brazilian usage; BR1 | AINE | PT2, p. 12 |
| Triptan | triptano | BR1 | triptano | PT2, p. 5 |
| Antiemetic | antiemético | BR1 | antiemético | PT2, p. 9 |
| Beta blocker | betabloqueador | BR3 class names | **betabloqueante** | PT2, p. 5 ("beta-bloqueantes") |
| Anticonvulsant | anticonvulsivante | BR3 class names | **antiepilético** | PT2, p. 5 |
| Tricyclic antidepressant | antidepressivo tricíclico | BR1 | Same | PT2, p. 5 |

#### Medical terms: Italian, Dutch

| Term | Italian | Source (it) | Dutch | Source (nl) |
|---|---|---|---|---|
| Migraine without aura | Emicrania senza aura | IT1, p. 19 | Migraine zonder aura | NL1, "Belangrijkste wijzigingen" |
| Migraine with aura | Emicrania con aura | IT1, p. 19 | Migraine met aura | NL1, "Belangrijkste wijzigingen" |
| Chronic migraine | Emicrania cronica | IT1, p. 19 | Chronische migraine | NL1, "Richtlijnen beleid" |
| Tension-type headache | Cefalea di tipo tensivo | IT1, p. 19 | Spanningshoofdpijn | NL1, "Richtlijnen beleid" |
| Cluster headache | Cefalea a grappolo | IT1, p. 20 | Clusterhoofdpijn | NL1, "Richtlijnen beleid" |
| attack | attacco | IT1, p. 24 | aanval | NL1, "Anamnese" ("hoofdpijnaanval") |
| Photophobia | fotofobia | IT1, p. 24 | fotofobie | NL1, diagnostic criteria |
| Phonophobia | fonofobia | IT1, p. 24 | fonofobie | NL1, "Anamnese" |
| Scintillating scotoma | scotoma scintillante | IT1, p. 26 ("scotoma") | flikkerscotoom | NL1, "Achtergronden" |
| Nausea | nausea | IT1, p. 24 | misselijkheid | NL1, "Anamnese" |
| Aphasia | afasia | IT1, p. 26 | afasie | Not in NL1: **not verified** |
| Paresthesia | parestesie | IT1, p. 59 | tintelingen / paresthesie | NL1 ("tintelingen"); "paresthesie" **not verified** |
| Neck stiffness | rigidità del collo | IT1, p. 25 | stijve nek | NL1 ("nekstijfheid") |
| Trigger | fattore scatenante | Common word; IT1 has no single term | trigger | NL1 ("aanvallen uitlokken"); "trigger" is the common word |
| Acute treatment | terapia dell'attacco | IT1 meaning | aanvalsbehandeling | NL1, "Medicamenteus stappenplan aanvalsbehandeling" |
| Preventive treatment | profilassi | IT1 meaning | preventieve behandeling | NL1, "Preventieve behandeling" |
| Headache diary | diario | IT1, p. 26 | hoofdpijndagboek | NL1, "Hoofdpijndagboek" |

| Medication (class) | Italian | Source (it) | Dutch | Source (nl) |
|---|---|---|---|---|
| Ibuprofen | Ibuprofene | IT2: 1404 packages | Ibuprofen | NL2: 180; NL1 |
| Sumatriptan | Sumatriptan | IT2: 413 | Sumatriptan | NL2: 39; NL1 |
| Rizatriptan | Rizatriptan | IT2: 212 | Rizatriptan | NL2: 16; NL1 |
| Naproxen | Naprossene | IT2: 27 | Naproxen | NL2: 48; NL1 |
| Acetaminophen | Paracetamolo | IT2: 1520 | Paracetamol | NL2: 278; NL1 |
| Ubrogepant | Ubrogepant | IT2: 0. **No registration**; the INN | Ubrogepant | NL2: 0. **No registration**; the INN |
| Metoclopramide | Metoclopramide | IT2: 88 | Metoclopramide | NL2: 14; NL1 |
| Riboflavin | Riboflavina | IT2: 38, also single-substance | Riboflavine | NL2: 2, combinations only |
| Magnesium | Magnesio | IT2: 3295 (salts) | Magnesium | NL2: 219 (salts) |
| Propranolol | Propranololo | IT2: 10 | Propranolol | NL2: 14; NL1 |
| Topiramate | Topiramato | IT2: 803 | Topiramaat | NL2: 39; NL1 |
| Amitriptyline | Amitriptilina | IT2: 46 | Amitriptyline | NL2: 23; NL1 |
| Erenumab | Erenumab | IT2: 6; EU1 | Erenumab | NL2: 4; EU1 |
| NSAID | FANS | Italian usage; IT2 | NSAID | NL1 |
| Antiemetic | antiemetico | IT2 meaning | anti-emeticum | NL1 |
| Beta blocker | betabloccante | IT2 meaning | bètablokker | NL1 |
| Anticonvulsant | antiepilettico | IT2 meaning | anti-epilepticum | NL1 ("anti-epileptica") |
| Tricyclic antidepressant | antidepressivo triciclico | IT2 meaning | tricyclisch antidepressivum | NL1 |

### Central and Northern Europe

Polish (`pl`), Czech (`cs`), Slovak (`sk`), Croatian (`hr`), Slovenian (`sl`), Romanian (`ro`), Hungarian (`hu`), Lithuanian (`lt`), Danish (`da`), Norwegian Bokmål (`nb`), Swedish (`sv`), Finnish (`fi`) and Turkish (`tr`). The project owner approved the address, the word for episode, the medical disclaimer and the watch texts on October 18, 2026. The Romanian word for episode changed after the approval from "criză" to "atac": the Romanian ICHD-3 (RO1) uses "atac de migrenă" and keeps "criză" for epileptic seizures. No native speaker reads these languages before a release ([LOCALIZATION_PLAN.md](LOCALIZATION_PLAN.md), section 7).

| Language | Address | Buttons |
|---|---|---|
| pl | Impersonal forms ("należy porozmawiać"), as plan section 6 asks; noun phrases in prompts | The imperative of Polish iOS: "Zapisz", "Anuluj" |
| cs | "vy", in lower case | Infinitive: "Uložit", "Zrušit" |
| sk | "vy", in lower case | Infinitive: "Uložiť", "Zrušiť" |
| hr | "vi", in lower case | The imperative of Croatian iOS: "Spremi", "Odustani" |
| sl | "vi", in lower case | The imperative of Slovenian iOS: "Shrani", "Prekliči" |
| ro | The polite verb forms ("Discutați"), "dvs." where a pronoun is necessary | The imperative of Romanian iOS: "Salvează", "Anulează" |
| hu | The formal verb forms without "Ön" where possible | Nouns, as in Hungarian iOS: "Mentés", "Mégsem" |
| lt | "jūs" | Infinitive: "Išsaugoti", "Atšaukti" |
| da | "du"; the polite "De" is out of use | Infinitive: "Gem", "Annuller" |
| nb | "du"; the polite "De" is out of use | Infinitive: "Lagre", "Avbryt" |
| sv | "du" | Infinitive: "Spara", "Avbryt" |
| fi | The second person singular, as Finnish iOS does | The imperative: "Tallenna", "Kumoa" |
| tr | "siz" ("konuşun") | The imperative of Turkish iOS: "Kaydet", "Vazgeç" |

| Term | pl | cs | sk | hr | sl | ro | hu |
|---|---|---|---|---|---|---|---|
| episode | napad | záchvat | záchvat | napadaj | napad | atac | roham |
| attack (App Store only) | napad, atak | záchvat | záchvat | napadaj | napad | atac | roham |
| migraine | migrena | migréna | migréna | migrena | migrena | migrenă | migrén |
| log (verb) | zapisać | zaznamenat, zapsat | zaznamenať, zapísať | zabilježiti | zabeležiti | nota | rögzíteni |
| intensity | natężenie (bólu) | intenzita | intenzita | jačina (boli) | jakost (bolečine) | intensitate; "Durere 6/10" with a score | erősség |
| migraine day | dzień z migreną | den s migrénou | deň s migrénou | dan s migrenom | dan z migreno; pill "Migrena: 3 dni" | zi cu migrenă; pill "Cu migrenă: 3" | migrénes nap |
| tracked day | dzień obserwacji | sledovaný den | sledovaný deň | praćeni dan | spremljani dan | zi monitorizată | követett nap |
| pain-free day | dzień bez bólu | den bez bolesti | deň bez bolesti | dan bez boli | dan brez bolečin; short "Brez bolečin" | zi fără durere | fájdalommentes nap; short "Fájdalommentes" |
| not tracked | bez obserwacji | nesledováno | nesledované | nije praćeno | ni spremljano | nemonitorizat | nem követett |
| trigger | czynnik wyzwalający | spouštěč | spúšťač | okidač | sprožilec | factor declanșator | kiváltó tényező |
| possible trigger | możliwy czynnik wyzwalający | možný spouštěč | možný spúšťač | mogući okidač | možni sprožilec | factor declanșator posibil | lehetséges kiváltó tényező |
| aura | aura | aura | aura | aura | avra | aură | aura |
| acute medication | lek doraźny | akutní lék | akútny liek | lijek za akutno liječenje | zdravilo za akutno zdravljenje | medicament pentru tratamentul acut | akut gyógyszer |
| preventive medication | profilaktyka | prevence | prevencia | preventivno | preventiva | preventiv | megelőzés |
| treatment blueprint | plan leczenia | léčebný plán | liečebný plán | plan liječenja | načrt zdravljenja | plan de tratament | kezelési terv |
| baseline profile | profil wyjściowy | výchozí profil | východiskový profil | početni profil | izhodiščni profil | profil inițial | kiindulási profil |
| report | raport | zpráva | správa | izvješće | poročilo | raport | jelentés |
| Health app | Zdrowie | Zdraví | Zdravie | Zdravlje | Zdravje | Sănătate | Egészség |
| Settings (iOS) | Ustawienia | Nastavení | Nastavenia | Postavke | Nastavitve | Configurări | Beállítások |

| Term | lt | da | nb | sv | fi | tr |
|---|---|---|---|---|---|---|
| episode | priepuolis | anfald | anfall | anfall | kohtaus | atak |
| attack (App Store only) | priepuolis | anfald | anfall | anfall | kohtaus | atak |
| migraine | migrena | migræne | migrene | migrän | migreeni | migren |
| log (verb) | įrašyti | registrere | registrere | registrera | kirjata | kaydetmek |
| intensity | intensyvumas; "Skausmas 6/10" with a score | intensitet | intensitet | intensitet | voimakkuus | şiddet |
| migraine day | diena su migrena; pill "Migrena: 3 d." | migrænedag | migrenedag | migrändag | migreenipäivä | migren günü |
| tracked day | stebėta diena | registreret dag | registrert dag | registrerad dag | seurattu päivä | takip edilen gün |
| pain-free day | diena be skausmo; short "Be skausmo" | smertefri dag | smertefri dag | smärtfri dag | kivuton päivä; short "Kivuttomat" | ağrısız gün |
| not tracked | nestebėta | ikke registreret; strip "Ingen data" | ikke registrert; strip "Ingen data" | inte registrerad; strip "Inga data" | ei seurattu | takip edilmedi; strip "Takip yok" |
| trigger | provokuojantis veiksnys | udløser | utløser | trigger | laukaisija | tetikleyici |
| possible trigger | galimas provokuojantis veiksnys | mulig udløser | mulig utløser | möjlig trigger | mahdollinen laukaisija | olası tetikleyici |
| aura | aura | aura | aura | aura | aura | aura |
| acute medication | ūminio gydymo vaistas | anfaldsmedicin | anfallsmedisin | anfallsläkemedel | akuuttilääke | akut ilaç |
| preventive medication | profilaktika | forebyggende | forebyggende | förebyggande | estohoito | koruyucu |
| treatment blueprint | gydymo planas | behandlingsplan | behandlingsplan | behandlingsplan | hoitosuunnitelma | tedavi planı |
| baseline profile | pradinis profilis | udgangsprofil | utgangsprofil | utgångsprofil | lähtötilanneprofiili | başlangıç profili |
| report | ataskaita | rapport | rapport | rapport | raportti | rapor |
| Health app | Sveikata | Sundhed | Helse | Hälsa | Terveys | Sağlık |
| Settings (iOS) | Nustatymai | Indstillinger | Innstillinger | Inställningar | Asetukset | Ayarlar |

Watch and complication texts (term pairs of the build): "Log Migraine" = "Nowy napad", "Nový záchvat", "Nový záchvat", "Novi napadaj", "Nov napad", "Atac nou", "Új roham", "Įrašyti", "Nyt anfald", "Nytt anfall", "Nytt anfall", "Uusi kohtaus", "Yeni atak". "Save Episode" = "Zapisz", "Uložit", "Uložiť", "Spremi", "Shrani", "Salvează", "Mentés", "Išsaugoti", "Gem", "Lagre", "Spara", "Tallenna", "Kaydet". The round complication "LOG" (4 characters) = "NOWY", "NOVÝ", "NOVÝ", "NOVI", "NOV", "NOU", "ÚJ", "ŽYMĖ", "NYT", "NYTT", "NYTT", "UUSI", "YENİ".

#### Sources of the Central and Northern European terms

Checked on October 18, 2026. A page number is the page of the PDF file; a web page without page numbers is cited by its heading.

- **[EU-PL], [EU-CS], [EU-SK], [EU-HR], [EU-SL], [EU-RO], [EU-HU], [EU-LT], [EU-DA], [EU-NO], [EU-SV], [EU-FI]** EMA, Aimovig (erenumab) product information in each language: `https://www.ema.europa.eu/<code>/documents/product-information/aimovig-epar-product-information_<code>.pdf`, with `no` for Norwegian. Section 4.1 (p. 2) gives the term for migraine, migraine day and prophylaxis; section 4.4 or 4.5 (p. 4) names sumatriptan or the triptans.
- **[PL-SmPC]** Charakterystyka Produktu Leczniczego, Sumatriptan Medical Valley 50 mg: [copy at receptomat.pl](https://receptomat.pl/wp-content/uploads/2024/11/Sumatriptan-Medical-Valley-charakterystyka-produktu-leczniczego-chpl.pdf).
- **[PL-MP]** Medycyna Praktyczna, [Migrena – objawy, przyczyny, leczenie](https://www.mp.pl/pacjent/neurologia/choroby/150555,migrena), patient text reviewed by a physician, last change February 26, 2024. The Polish guideline of the plan (Stępień 2021) is in English and gives no Polish terms.
- **[CS1]** ICHD-3, Czech basic version: [3922_ichd-3-czech.pdf](https://ihs-headache.org/wp-content/uploads/2020/05/3922_ichd-3-czech.pdf). **[CS2]** ICHD-3, Czech pocket version: [ICHD-3-Czech-pocket-version.pdf](https://ihs-headache.org/wp-content/uploads/2021/03/ICHD-3-Czech-pocket-version.pdf).
- **[SK1]** Hofericová B., "Profylaktická liečba migrény a jej najčastejšie chyby", Neurologie pro praxi 2021;22(3): [PDF](https://www.neurologiepropraxi.cz/pdfs/neu/2021/03/08.pdf). **[SK2]** Špalek P., "Akútna liečba migrény", Solen: [PDF](https://www.solen.sk/storage/file/article/ff3dfdcee4b21f3cc6b6633611344369.pdf). No Slovak national guideline was found.
- **[HR-SmPC]** HALMED, Sažetak opisa svojstava lijeka, Imigran 50 mg: [PDF](https://halmed.hr/upl/lijekovi/SPC/Imigran-SPC.pdf). Croatian theses in the Dabar repositories ask for a CAPTCHA and were **not read**; the Croatian guideline in Acta Medica Croatica was not found as a free text.
- **[SL1]** Strgar Hladnik M., "Obravnava in vodenje migrenskih bolnikov", Medicinska fakulteta Ljubljana: [PDF](https://mf.uni-lj.si/download_file/view/2098/7254). No current Slovenian guideline was found.
- **[RO1]** ICHD-3, Romanian translation: [ICHD-3-Romanian-rv.pdf](https://ihs-headache.org/wp-content/uploads/2022/08/ICHD-3-Romanian-rv.pdf).
- **[HU1]** Bozsik Gy., "Az elsődleges fejfájásokról", Semmelweis University, Department of Neurology, November 8, 2023: [PDF](https://semmelweis.hu/neurologia/files/2023/11/Diasor_Bozsik_Gyorgy.pdf). The 2010 professional protocol of the Hungarian health college answered "not found"; no current Hungarian guideline was found.
- **[LT1]** Kakta B., Mameniškienė R., "NVNU ir triptanai – (ne)nauja migrenos priepuolių gydymo galimybė", Neurologijos seminarai 2023;27(98):240–245, [doi:10.15388/NS.2023.27.98.7](https://doi.org/10.15388/NS.2023.27.98.7). **[LT2]** Visuotinė lietuvių enciklopedija, [Migrena](https://www.vle.lt/straipsnis/migrena/). No Lithuanian national guideline was found.
- **[DA1]** sundhed.dk, Lægehåndbogen, [Migræne uden aura](https://www.sundhed.dk/sundhedsfaglig/laegehaandbogen/neurologi/tilstande-og-sygdomme/hovedpine/migraene-uden-aura/), updated August 27, 2025. **[DA2]** The same, [Migræne med aura](https://www.sundhed.dk/sundhedsfaglig/laegehaandbogen/neurologi/tilstande-og-sygdomme/hovedpine/migraene-med-aura/), updated August 29, 2025.
- **[NO1]** Norsk Elektronisk Legehåndbok, [Migrene](https://legehandboka.no/handboken/kliniske-kapitler/nevrologi/tilstander-og-sykdommer/hodepiner/migrene/), updated February 19, 2024. Only the public summary loaded; the full chapter needs a sign-in.
- **[SE1]** Nationellt system för kunskapsstyrning, NPO Nervsystemets sjukdomar, "Riktlinje för migrän", decided October 25, 2022: [PDF](https://vardpersonal.1177.se/globalassets/nkk/nationell/media/dokument/kunskapsstod/vardriktlinjer/riktlinje-for-migran.pdf).
- **[FI1]** Käypä hoito -suositus "Migreeni", Suomalainen Lääkäriseura Duodecim and Suomen Neurologinen Yhdistys, published March 28, 2024: [kaypahoito.fi/hoi36050](https://www.kaypahoito.fi/hoi36050). The web page has sections, no page numbers.
- **[TR1]** ICHD-3, Turkish translation, "Başağrısı Bozukluklarının Uluslararası Sınıflaması": [PDF](https://ihs-headache.org/wp-content/uploads/2021/12/Bas%CC%A7ag%CC%86risi-Bozukluklarinin-Uluslararasi-Siniflamasi.pdf). **[TR2]** T.C. Sağlık Bakanlığı, "Migren Klinik Protokolü", revision 1, August 2025: [copy at noroloji.org.tr](https://noroloji.org.tr/TNDData/Uploads/files/Migren%20Klinik%20Protokol%c3%bc%20(Revizyon)%20(92%20Sayfa).pdf).
- **National drug registers: not searched in this batch** (Rejestr Produktów Leczniczych, SÚKL, ŠÚKL, HALMED database, JAZMP/CBZ, ANMDMR, OGYÉI, VVKT, Lægemiddelstyrelsen, DMP, Läkemedelsverket, Fimea, TİTCK). The substance names follow the sources above where they name the substance; the other names follow the INN rules of the language and are marked **not verified**. Ubrogepant has no EU marketing authorization (DE2, p. 39, in the Western European sources), so it has **no registration** in the twelve EU and EEA countries; Turkey was not checked.

#### Medical terms: Polish, Czech, Slovak

| Term | Polish | Source (pl) | Czech | Source (cs) | Slovak | Source (sk) |
|---|---|---|---|---|---|---|
| Migraine without aura | Migrena bez aury | EU-PL, p. 8 ("bez aury"); **not verified** as a heading | Migréna bez aury | CS1, 1.1, p. 2 | Migréna bez aury | SK1, p. 1 |
| Migraine with aura | Migrena z aurą | EU-PL, p. 8; PL-MP | Migréna s aurou | CS1, 1.2, p. 2 | Migréna s aurou | SK1, p. 1 |
| Chronic migraine | Migrena przewlekła | EU-PL, p. 7 ("migreny przewlekłej") | Chronická migréna | CS1, 1.3, p. 2 | Chronická migréna | SK1, p. 1; EU-SK, p. 7 |
| Tension-type headache | Ból głowy typu napięciowego | PL-MP | Tenzní typ bolesti hlavy | CS2, 2, p. 13 | Tenzná bolesť hlavy | SK1, p. 2 ("tenzná") |
| Cluster headache | Klasterowy ból głowy | PL-MP | Cluster headache | CS1, 3.1, p. 3 (English name kept) | Klastrová bolesť hlavy | **Not verified**: not in SK1 or SK2 |
| attack | napad | PL-SmPC, 4.1, p. 1 ("napadów migreny") | záchvat | CS2, p. 2 | záchvat | SK1, p. 2; SK2, p. 1 |
| Photophobia | fotofobia (światło) | PL-MP ("światłowstręt", "nadwrażliwość na światło") | fotofobie | CS2, p. 38 | fotofóbia | SK1, p. 1 |
| Phonophobia | fonofobia (dźwięk) | PL-MP ("nadwrażliwość na dźwięki"); "fonofobia" **not verified** | fonofobie | CS2, p. 38 | fonofóbia | SK1, p. 1 |
| Scintillating scotoma | mroczki migoczące | PL-MP ("mroczkami"); PL-SmPC, p. 4 | scintilující skotom | **Not verified**: not in CS1 or CS2 | scintilujúci skotóm | SK2, p. 2 ("skotóm") |
| Nausea | nudności | PL-SmPC, p. 4; PL-MP | nevolnost | CS2, p. 38 | nevoľnosť | SK1, p. 1 |
| Aphasia | afazja | **Not verified** | afázie | **Not verified** | afázia | **Not verified** |
| Paresthesia | parestezje / mrowienie | PL-SmPC, p. 4; PL-MP | parestezie | CS2, p. 3 | parestézie | SK1, p. 2 |
| Neck stiffness | sztywność szyi | PL-SmPC, p. 4 ("sztywność szyi"); not "sztywność karku", a meningeal sign | ztuhlost krku | CS2, p. 63 | stuhnutý krk | Common words; **not verified** |
| Migraine day | dzień z migreną | EU-PL, p. 2 | den s migrénou | EU-CS, p. 2 | deň s migrénou | EU-SK, p. 2 ("dni migrény") |
| Preventive treatment | profilaktyka | EU-PL, p. 2; PL-MP | prevence | CS2, p. 3; EU-CS, p. 2 ("profylaxi") | prevencia | SK1, p. 1; EU-SK, p. 2 ("profylaxiu") |
| Trigger | czynnik wyzwalający | PL-MP says "czynniki prowokujące"; "wyzwalający" **not verified** | spouštěč | **Not verified** | spúšťač | SK2, p. 2 |

| Medication (class) | Polish | Source (pl) | Czech | Source (cs) | Slovak | Source (sk) |
|---|---|---|---|---|---|---|
| Ibuprofen | Ibuprofen | **Not verified** | Ibuprofen | **Not verified** | Ibuprofén | **Not verified** |
| Sumatriptan | Sumatryptan | PL-SmPC, p. 1; EU-PL, p. 4 | Sumatriptan | CS2, p. 3 | Sumatriptán | SK1, p. 2; EU-SK, p. 4 |
| Rizatriptan | Ryzatryptan | **Not verified** | Rizatriptan | **Not verified** | Rizatriptán | SK1, p. 2 |
| Naproxen | Naproksen | **Not verified** | Naproxen | **Not verified** | Naproxén | SK1, p. 2 |
| Acetaminophen | Paracetamol | PL-SmPC, p. 1 | Paracetamol | CS1, p. 6 | Paracetamol | SK1, p. 2 |
| Ubrogepant | Ubrogepant | **No registration** (EU) | Ubrogepant | **No registration** (EU) | Ubrogepant | **No registration** (EU) |
| Metoclopramide | Metoklopramid | PL-MP | Metoklopramid | **Not verified** | Metoklopramid | SK1, p. 2 |
| Riboflavin | Ryboflawina | **Not verified** | Riboflavin | **Not verified** | Riboflavín | **Not verified** |
| Magnesium | Magnez | **Not verified** (PL-SmPC names only magnesium stearate) | Hořčík | **Not verified** | Horčík | **Not verified** |
| Propranolol | Propranolol | PL-SmPC, p. 3 | Propranolol | **Not verified** | Propranolol | SK1, p. 3 |
| Topiramate | Topiramat | EU-PL, p. 14 | Topiramát | EU-CS, p. 13 | Topiramát | SK1, p. 3 |
| Amitriptyline | Amitryptylina | PL-MP | Amitriptylin | **Not verified** | Amitriptylín | SK1, p. 3 |
| Erenumab | Erenumab | EU-PL, p. 2 | Erenumab | EU-CS, p. 2 | Erenumab | EU-SK, p. 2 |
| NSAID | NLPZ | PL-SmPC, p. 1 | NSA | CS2, p. 20 | NSA | **Not verified** |
| Triptan | tryptan | EU-PL, p. 4 | triptan | CS2, p. 2 | triptán | SK1, p. 2 |
| Beta blocker | beta-bloker | **Not verified** | betablokátor | **Not verified** | betablokátor | SK1, p. 3 |
| Anticonvulsant | lek przeciwpadaczkowy | PL-MP | antiepileptikum | **Not verified** | antiepileptikum | SK1, p. 3 |

#### Medical terms: Croatian, Slovenian, Romanian

| Term | Croatian | Source (hr) | Slovenian | Source (sl) | Romanian | Source (ro) |
|---|---|---|---|---|---|---|
| Migraine without aura | Migrena bez aure | HR-SmPC, p. 6 | Migrena brez avre | **Not verified** | Migrenă fără aură | RO1, 1.1, p. 7 |
| Migraine with aura | Migrena s aurom | HR-SmPC, p. 1 | Migrena z avro | SL1, p. 6; EU-SL, p. 8 | Migrenă cu aură | RO1, 1.2, p. 6 |
| Chronic migraine | Kronična migrena | **Not verified** | Kronična migrena | **Not verified** | Migrenă cronică | RO1, 1.3, p. 6 |
| Tension-type headache | Glavobolja tenzijskog tipa | **Not verified** | Tenzijski glavobol | **Not verified** | Cefalee de tip tensional | RO1, 2, p. 5 |
| Cluster headache | Cluster glavobolja | **Not verified** | Glavobol v skupkih | **Not verified** | Cefalee cluster | RO1, 3.1, p. 6 |
| attack | napadaj | HR-SmPC, 4.1, p. 1 | napad | EU-SL, p. 7 | atac | RO1, p. 11 ("atac de migrenă"); "criză" is used for seizures |
| Photophobia | fotofobija | **Not verified** | fotofobija | SL1, p. 2 | fotofobie | RO1, p. 11 |
| Phonophobia | fonofobija | **Not verified** | fonofobija | SL1, p. 2 | fonofobie | RO1, p. 11 |
| Scintillating scotoma | scintilirajući skotom | **Not verified** | migetajoči skotom | **Not verified** | scotom scintilant | RO1, p. 12 |
| Nausea | mučnina | HR-SmPC, p. 5 | slabost | SL1, p. 1 | greață | RO1, p. 11 |
| Aphasia | afazija | **Not verified** | afazija | **Not verified** | afazie | RO1, p. 14 |
| Paresthesia | parestezije / trnci | **Not verified** | parestezije / mravljinčenje | **Not verified** | parestezii / furnicături | RO1, p. 12 |
| Neck stiffness | ukočenost vrata | **Not verified** | otrdel vrat | **Not verified** | rigiditatea gâtului | RO1, p. 13 |
| Migraine day | dan s migrenom | EU-HR, p. 2 ("migrenu najmanje 4 dana") | dan z migreno | EU-SL, p. 2 ("migrenske dni") | zi cu migrenă | EU-RO, p. 2 |
| Preventive treatment | preventivno | EU-HR, p. 2 ("profilaksu") | preventiva | EU-SL, p. 2 ("profilakso") | preventiv | RO1, p. 159; EU-RO, p. 2 ("profilaxia") |
| Trigger | okidač | **Not verified** | sprožilec | SL1, p. 1 ("sprožilni dejavniki") | factor declanșator | RO1, p. 10 ("declanșate") |

| Medication (class) | Croatian | Source (hr) | Slovenian | Source (sl) | Romanian | Source (ro) |
|---|---|---|---|---|---|---|
| Ibuprofen | Ibuprofen | **Not verified** | Ibuprofen | **Not verified** | Ibuprofen | **Not verified** |
| Sumatriptan | Sumatriptan | HR-SmPC, p. 1 | Sumatriptan | SL1, p. 4; EU-SL, p. 4 | Sumatriptan | RO1, p. 105; EU-RO, p. 4 |
| Rizatriptan | Rizatriptan | **Not verified** | Rizatriptan | **Not verified** | Rizatriptan | **Not verified** |
| Naproxen | Naproksen | **Not verified** | Naproksen | **Not verified** | Naproxen | **Not verified** |
| Acetaminophen | Paracetamol | HR-SmPC, p. 1 | Paracetamol | SL1, p. 4 | Paracetamol | **Not verified** |
| Ubrogepant | Ubrogepant | **No registration** (EU) | Ubrogepant | **No registration** (EU) | Ubrogepant | **No registration** (EU) |
| Metoclopramide | Metoklopramid | **Not verified** | Metoklopramid | **Not verified** | Metoclopramidă | **Not verified** |
| Riboflavin | Riboflavin | **Not verified** | Riboflavin | **Not verified** | Riboflavină | **Not verified** |
| Magnesium | Magnezij | **Not verified** | Magnezij | **Not verified** | Magneziu | **Not verified** |
| Propranolol | Propranolol | HR-SmPC, p. 3 | Propranolol | **Not verified** | Propranolol | **Not verified** |
| Topiramate | Topiramat | EU-HR, p. 13 | Topiramat | **Not verified** | Topiramat | **Not verified** |
| Amitriptyline | Amitriptilin | **Not verified** | Amitriptilin | **Not verified** | Amitriptilină | **Not verified** |
| Erenumab | Erenumab | EU-HR, p. 2 | Erenumab | EU-SL, p. 2 | Erenumab | EU-RO, p. 2 |
| Triptan | triptan | HR-SmPC, p. 1 | triptan | SL1, p. 3 | triptan | RO1, p. 105 |

#### Medical terms: Hungarian, Lithuanian, Turkish

| Term | Hungarian | Source (hu) | Lithuanian | Source (lt) | Turkish | Source (tr) |
|---|---|---|---|---|---|---|
| Migraine without aura | Aura nélküli migrén | HU1, p. 3; EU-HU, p. 8 | Migrena be auros | LT2 | Aurasız migren | TR1, 1.1, p. 10 |
| Migraine with aura | Aurával járó migrén | HU1, p. 3 | Migrena su aura | LT2; EU-LT, p. 8 | Auralı migren | TR1, 1.2, p. 8 |
| Chronic migraine | Krónikus migrén | HU1, p. 7 | Lėtinė migrena | EU-LT, p. 7 | Kronik migren | TR1, 1.3, p. 8 |
| Tension-type headache | Tenziós típusú fejfájás | **Not verified** | Įtampos tipo galvos skausmas | **Not verified** | Gerilim-tipi başağrısı | TR1, 2, p. 5 |
| Cluster headache | Cluster fejfájás | HU1, p. 8 | Kekinis galvos skausmas | **Not verified** | Küme başağrısı | TR1, 3.1, p. 8 |
| attack | roham | HU1, p. 3 | priepuolis | LT1, p. 1; EU-LT, p. 7 | atak | TR1, p. 25; TR2, p. 8 |
| Photophobia | fotofóbia | **Not verified** | fotofobija | LT1, p. 2 | fotofobi | TR1, p. 25 |
| Phonophobia | fonofóbia | **Not verified** | fonofobija | LT1, p. 2 | fonofobi | TR1, p. 25 |
| Scintillating scotoma | szcintilláló szkotóma | **Not verified** | mirgančioji skotoma | **Not verified** | parıltılı skotom | TR1, p. 107 |
| Nausea | hányinger | **Not verified** | pykinimas | LT1, p. 2 | bulantı | TR1, p. 25 |
| Aphasia | afázia | **Not verified** | afazija | **Not verified** | afazi | TR1, p. 27 |
| Paresthesia | zsibbadás / paresztézia | **Not verified** | dilgčiojimas / parestezija | **Not verified** | karıncalanma / parestezi | TR1, p. 77 |
| Neck stiffness | merev nyak | **Not verified** | sustingęs kaklas | **Not verified** | boyun sertliği | TR1, p. 26 ("ense sertliği" in the prodrome) |
| Migraine day | migrénes nap | EU-HU, p. 2 | diena su migrena | EU-LT, p. 2 ("dienos su migrenos skausmu") | migren günü | TR2, p. 23 |
| Preventive treatment | megelőzés | HU1, p. 5; EU-HU, p. 2 ("profilaxis") | profilaktika | LT2; EU-LT, p. 2 | koruyucu | TR2, p. 3 |
| Trigger | kiváltó tényező | **Not verified** | provokuojantis veiksnys | **Not verified** | tetikleyici | TR1, p. 12; TR2, p. 14 |

| Medication (class) | Hungarian | Source (hu) | Lithuanian | Source (lt) | Turkish | Source (tr) |
|---|---|---|---|---|---|---|
| Ibuprofen | Ibuprofén | **Not verified** | Ibuprofenas | LT1, p. 4 | İbuprofen | **Not verified** |
| Sumatriptan | Szumatriptán | **Not verified** | Sumatriptanas | LT1, p. 1 | Sumatriptan | TR1, p. 182; TR2, p. 26 |
| Rizatriptan | Rizatriptán | **Not verified** | Rizatriptanas | LT1, p. 5 | Rizatriptan | TR2, p. 32 |
| Naproxen | Naproxén | **Not verified** | Naproksenas | LT1, p. 1 | Naproksen | TR2, p. 30 |
| Acetaminophen | Paracetamol | **Not verified** | Paracetamolis | LT1, p. 3 | Parasetamol | TR1, p. 16; TR2, p. 32 |
| Ubrogepant | Ubrogepant | **No registration** (EU) | Ubrogepantas | **No registration** (EU) | Ubrogepant | TİTCK not searched: **not verified** |
| Metoclopramide | Metoklopramid | **Not verified** | Metoklopramidas | **Not verified** | Metoklopramid | TR2, p. 25 |
| Riboflavin | Riboflavin | **Not verified** | Riboflavinas | **Not verified** | Riboflavin | **Not verified** |
| Magnesium | Magnézium | **Not verified** | Magnis | **Not verified** | Magnezyum | **Not verified** |
| Propranolol | Propranolol | **Not verified** | Propranololis | **Not verified** | Propranolol | TR1, p. 181; TR2, p. 33 |
| Topiramate | Topiramát | **Not verified** | Topiramatas | **Not verified** | Topiramat | TR2, p. 49 |
| Amitriptyline | Amitriptilin | **Not verified** | Amitriptilinas | **Not verified** | Amitriptilin | TR2, p. 51 |
| Erenumab | Erenumab | EU-HU, p. 2 | Erenumabas | EU-LT, p. 2 ("erenumabo") | Erenumab | TR2, p. 9 |
| NSAID | NSAID | **Not verified** | NVNU | LT1, p. 1 | NSAİİ | TR1, p. 16; TR2, p. 10 |
| Triptan | triptán | HU1, p. 6 | triptanas | LT1, p. 1 | triptan | TR1, p. 16 |
| Beta blocker | béta-blokkoló | **Not verified** | beta adrenoblokatorius | **Not verified** | beta bloker | TR2, p. 32 |

#### Medical terms: Danish, Norwegian, Swedish, Finnish

| Term | Danish | Source (da) | Norwegian | Source (nb) | Swedish | Source (sv) | Finnish | Source (fi) |
|---|---|---|---|---|---|---|---|---|
| Migraine without aura | Migræne uden aura | DA1, title | Migrene uten aura | NO1 ("med eller uten aura") | Migrän utan aura | SE1, p. 9 | Auraton migreeni | FI1, table 1 |
| Migraine with aura | Migræne med aura | DA2, title | Migrene med aura | NO1 ("med eller uten aura") | Migrän med aura | SE1, p. 9 | Aurallinen migreeni | FI1, "Määritelmät" |
| Chronic migraine | Kronisk migræne | DA1 | Kronisk migrene | EU-NO, p. 7 | Kronisk migrän | SE1, p. 12 | Krooninen migreeni | FI1 |
| Tension-type headache | Spændingshovedpine | DA1 | Spenningshodepine | **Not verified** | Spänningshuvudvärk | SE1, p. 13 | Jännityspäänsärky | FI1 |
| Cluster headache | Klyngehovedpine | DA1 | Klasehodepine | **Not verified** | Klusterhuvudvärk | SE1, p. 10 | Sarjoittainen päänsärky | **Not verified** |
| attack | anfald | DA1; EU-DA, p. 7 | anfall | NO1; EU-NO, p. 7 | anfall | SE1, p. 7 ("migränanfall") | kohtaus | FI1; EU-FI, p. 8 |
| Photophobia | fotofobi (lys) | DA1 ("lysoverfølsomhed") | fotofobi (lys) | NO1 ("lysømfintlighet") | fotofobi (ljuskänslighet) | SE1, p. 9 ("ljus- och ljudkänslighet") | fotofobia (valonarkuus) | FI1, table 1 |
| Phonophobia | fonofobi (lyd) | DA1 ("lydoverfølsomhed") | fonofobi (lyd) | NO1 ("lydømfintlighet") | fonofobi (ljudkänslighet) | SE1, p. 9 | fonofobia (ääniherkkyys) | FI1, table 1 |
| Scintillating scotoma | flimmerskotom | DA2 ("skotom") | flimmerskotom | **Not verified** | flimmerskotom | SE1, p. 8 | välkkyvä skotooma | **Not verified** |
| Nausea | kvalme | DA1 | kvalme | NO1 | illamående | SE1, p. 9 | pahoinvointi | FI1, table 1 |
| Aphasia | afasi | DA2 | afasi | **Not verified** | afasi | SE1, p. 8 | afasia | **Not verified** |
| Paresthesia | prikken / paræstesier | DA2 | prikking / parestesier | **Not verified** | stickningar / parestesier | SE1, p. 8 | pistely / parestesiat | FI1 ("pistely") |
| Neck stiffness | stiv nakke | Common words; **not verified** | stiv nakke | **Not verified** | stel nacke | **Not verified** | jäykkä niska | Common words; not "niskajäykkyys", a meningeal sign |
| Migraine day | migrænedag | EU-DA, p. 2 | migrenedag | EU-NO, p. 2 | migrändag | SE1, p. 5; EU-SV, p. 2 | migreenipäivä | FI1; EU-FI, p. 2 |
| Preventive treatment | forebyggende | DA1 | forebyggende | NO1 | förebyggande | SE1, p. 18 | estohoito | FI1; EU-FI, p. 2 |
| Trigger | udløser | DA1 ("udløse migræne") | utløser | **Not verified** | trigger | SE1, p. 7 ("triggande faktorer") | laukaisija | FI1 says "laukaisevat tekijät"; the short noun is **not verified** |

| Medication (class) | Danish | Source (da) | Norwegian | Source (nb) | Swedish | Source (sv) | Finnish | Source (fi) |
|---|---|---|---|---|---|---|---|---|
| Ibuprofen | Ibuprofen | **Not verified** | Ibuprofen | **Not verified** | Ibuprofen | SE1, p. 17 | Ibuprofeeni | **Not verified** |
| Sumatriptan | Sumatriptan | DA1 | Sumatriptan | NO1 | Sumatriptan | SE1, p. 17 | Sumatriptaani | FI1 |
| Rizatriptan | Rizatriptan | **Not verified** | Rizatriptan | **Not verified** | Rizatriptan | **Not verified** | Ritsatriptaani | FI1 |
| Naproxen | Naproxen | DA1 | Naproksen | **Not verified** | Naproxen | SE1, p. 17 | Naprokseeni | FI1 |
| Acetaminophen | Paracetamol | DA1 | Paracetamol | NO1 | Paracetamol | SE1, p. 17 | Parasetamoli | FI1 |
| Ubrogepant | Ubrogepant | **No registration** (EU) | Ubrogepant | **No registration** (EEA) | Ubrogepant | **No registration** (EU) | Ubrogepantti | **No registration** (EU) |
| Metoclopramide | Metoclopramid | DA1 | Metoklopramid | **Not verified** | Metoklopramid | SE1, p. 17 | Metoklopramidi | FI1 |
| Riboflavin | Riboflavin | **Not verified** | Riboflavin | **Not verified** | Riboflavin | **Not verified** | Riboflaviini | **Not verified** |
| Magnesium | Magnesium | **Not verified** | Magnesium | **Not verified** | Magnesium | **Not verified** | Magnesium | **Not verified** |
| Propranolol | Propranolol | DA1 | Propranolol | NO1 | Propranolol | SE1, p. 6 | Propranololi | FI1 |
| Topiramate | Topiramat | DA1 | Topiramat | NO1 | Topiramat | SE1, p. 6 | Topiramaatti | FI1 |
| Amitriptyline | Amitriptylin | DA1 | Amitriptylin | NO1 | Amitriptylin | SE1, p. 18 | Amitriptyliini | FI1 |
| Erenumab | Erenumab | EU-DA, p. 2 | Erenumab | NO1; EU-NO, p. 2 | Erenumab | SE1, p. 6 | Erenumabi | FI1; EU-FI, p. 2 |
| NSAID | NSAID | DA1 | NSAID | NO1 | NSAID | SE1, p. 17 | tulehduskipulääke | FI1 |
| Triptan | triptan | DA1 | triptan | NO1 | triptan | SE1, p. 15 | triptaani | FI1 |
| Beta blocker | betablokker | **Not verified** | betablokker | **Not verified** | betablockerare | SE1, p. 6 | beetasalpaaja | FI1 |

### Cyrillic and Greek: Ukrainian, Bulgarian, Kazakh, Greek

Ukrainian (`uk`), Bulgarian (`bg`), Kazakh (`kk`) and Greek (`el`). All four use the system font. The project owner approved the address, the word for episode, the medical disclaimer and the watch texts on October 19, 2026. The owner reads Russian, so the Russian equivalents helped the check of Ukrainian and Kazakh. No native speaker reads these languages before a release ([LOCALIZATION_PLAN.md](LOCALIZATION_PLAN.md), section 7).

| Language | Address | Buttons |
|---|---|---|
| uk | "ви", in lower case, as in Ukrainian iOS | Infinitive: "Зберегти", "Скасувати" |
| bg | The polite plural verb forms ("Говорете"); "вие" in lower case where a pronoun is necessary | The imperative singular of Bulgarian iOS: "Запази", "Отказ" |
| kk | "сіз" and the polite verb forms ("сөйлесіңіз") | Verbal nouns, as in Kazakh iOS: "Сақтау", "Бас тарту" |
| el | The polite plural ("Μιλήστε") | Nouns, as in Greek iOS: "Αποθήκευση", "Ακύρωση" |

Kazakh puts no case suffix after a placeholder, because the suffix follows the vowels of the number. A noun after a number stays singular, so both plural forms are equal.

| Term | uk | bg | kk | el |
|---|---|---|---|---|
| episode | напад | пристъп | ұстама | κρίση |
| attack (App Store only) | напад | пристъп | ұстама | κρίση |
| migraine | мігрень | мигрена | мигрень | ημικρανία |
| log (verb) | записати | запиши, записвам | жазу | καταγραφή, καταγράφω |
| intensity | інтенсивність (болю); "Біль 6/10" with a score | интензитет (на болката); "Болка 6/10" | қарқындылық; "Ауырсыну 6/10" | ένταση (πόνου); "Πόνος 6/10" |
| migraine day | день з мігренню | ден с мигрена | мигрень күні | ημέρα ημικρανίας; pill "3 ημ. ημικρανίας" |
| tracked day | день спостереження | ден на проследяване | бақыланған күн | ημέρα παρακολούθησης |
| pain-free day | день без болю | ден без болка | ауырсынусыз күн; short "Ауырсынусыз" | ημέρα χωρίς πόνο; short "Χωρίς πόνο" |
| not tracked | без спостереження; legend "Немає даних" | без проследяване; legend "Няма данни" | бақыланбаған; legend "Деректер жоқ" | χωρίς παρακολούθηση; legend "Χωρίς δεδομένα" |
| trigger | тригер | тригер | триггер | εκλυτικός παράγοντας; short "παράγοντας" |
| possible trigger | можливий тригер | възможен тригер | ықтимал триггер | πιθανός εκλυτικός παράγοντας |
| aura | аура | аура | аура | αύρα |
| acute medication | ліки для купірування нападу | лекарство за овладяване на пристъпа | ұстаманы басуға арналған дәрі | φάρμακο για την κρίση |
| preventive medication | профілактика | профилактика | алдын алу | προληπτικά |
| treatment blueprint | план лікування | план за лечение | емдеу жоспары | σχέδιο θεραπείας |
| baseline profile | вихідний профіль | изходен профил | бастапқы профиль | αρχικό προφίλ |
| report | звіт | отчет | есеп | αναφορά |
| Health app | «Здоровʼя» | „Здраве“ | «Денсаулық» | «Υγεία» |
| Settings (iOS) | «Параметри» | „Настройки“ | «Параметрлер» | Ρυθμίσεις |

Watch and complication texts (term pairs of the build): "Log Migraine" = "Новий напад", "Нов пристъп", "Жаңа ұстама", "Νέα κρίση". "Save Episode" = "Зберегти", "Запази", "Сақтау", "Αποθήκευση". The round complication "LOG" (4 characters) = "ВВІД" (as the Russian "ВВОД"), "НОВ", "ЖАҢА", "ΝΕΑ" (Greek upper case drops the accent).

#### Sources of the Cyrillic and Greek terms

Checked on October 19, 2026. A page number is the page of the PDF file.

- **[UA1]** Ministry of Health of Ukraine, order No. 487 of August 17, 2007, "Клінічний протокол надання медичної допомоги хворим на мігрень": [copy at mozok.ua](https://mozok.ua/migren/article/2635-klnchnij-protokol-nadannya-medichno-dopomogi-hvorim-na-mgren). Old, but the only national text found. The register of current guidelines, [guidelines.moz.gov.ua](https://guidelines.moz.gov.ua/documents), answered with a Cloudflare check and was **not read**. No Ukrainian ICHD-3 exists on the IHS website.
- **[UA2]** State Register of Medicinal Products of Ukraine, full export [reestr.csv](http://www.drlz.com.ua/ibp/zvity.nsf/all/zvit/$file/reestr.csv) of [drlz.com.ua](http://www.drlz.com.ua/), downloaded and searched by international name.
- **[BG1]** ICHD-3, Bulgarian translation: [3675_ichd-3-bulgarian.pdf](https://ihs-headache.org/wp-content/uploads/2020/05/3675_ichd-3-bulgarian.pdf). **[EU-BG]** EMA, Aimovig product information in Bulgarian (`aimovig-epar-product-information_bg.pdf`, pattern of the Central European sources). No Bulgarian national migraine guideline was found. The Bulgarian Drug Agency register was **not searched**.
- **[KZ1]** Ministry of Health of the Republic of Kazakhstan, clinical protocol "Мигрень у взрослых", approved by the Joint Commission on the Quality of Medical Services on September 7, 2023, protocol No. 188: [MedElement copy](https://diseases.medelement.com/disease/%D0%BC%D0%B8%D0%B3%D1%80%D0%B5%D0%BD%D1%8C-%D1%83-%D0%B2%D0%B7%D1%80%D0%BE%D1%81%D0%BB%D1%8B%D1%85-%D0%BA%D0%BF-%D1%80%D0%BA-2023/17775), sections "Лечение (амбулатория)", tables 5–8. It is in Russian; no Kazakh version was found. **[KZ2]** Kazakh Wikipedia, [Бас сақинасы](https://kk.wikipedia.org/wiki/Бас_сақинасы) (edited November 8, 2020): "мигрень", "бастың ұстамалы ауруы". A spelling reference, not a medical source. No medical source in Kazakh was found, so the Kazakh medical words are **not verified**; KZ1 gives the meaning. The register of the National Center for Expertise of Medicines ([register.ndda.kz](https://register.ndda.kz/)) is a script page and was **not searched**.
- **[EU-EL]** EMA, Aimovig product information in Greek: [aimovig-epar-product-information_el.pdf](https://www.ema.europa.eu/el/documents/product-information/aimovig-epar-product-information_el.pdf). **[EU-EL2]** EMA, Vydura (rimegepant) product information in Greek, `vydura-epar-product-information_el.pdf`: "κρίση ημικρανίας". **[EU-EL3]** EMA, Pedea (ibuprofen) product information in Greek, `pedea-epar-product-information_el.pdf`: "ιβουπροφαίνη", "ΜΣΑΦ".
- **[EL1]** Vikelis M., neurologist, [Θεραπεία της κρίσης ημικρανίας](https://www.headaches.gr/migraine/treatment-of-migraine-attack.html), headaches.gr, © 2024. **[EL2]** Eginition Hospital, National and Kapodistrian University of Athens, patient leaflets [HEADACHE_CLUSTER_NKUA_2026.pdf](https://eginitio.uoa.gr/fileadmin/depts/uoa.gr/eginitio/uploads/documents/HEADACHE/HEADACHE_CLUSTER_NKUA_2026.pdf) and [HEADACHE_TENSION_NKUA_2026.pdf](https://eginitio.uoa.gr/fileadmin/depts/uoa.gr/eginitio/uploads/documents/HEADACHE/HEADACHE_TENSION_NKUA_2026.pdf), as quoted by the search. No Greek ICHD-3 and no Greek national guideline were found. The EOF register was **not searched**.

#### Medical terms: Ukrainian, Bulgarian

| Term | Ukrainian | Source (uk) | Bulgarian | Source (bg) |
|---|---|---|---|---|
| Migraine without aura | Мігрень без аури | UA1 | Мигрена без аура | BG1, 1.1, p. 19 |
| Migraine with aura | Мігрень з аурою | UA1 | Мигрена с аура | BG1, 1.2, p. 22 |
| Chronic migraine | Хронічна мігрень | **Not verified** | Хронична мигрена | BG1, 1.3, p. 19 |
| Tension-type headache | Головний біль напруги | **Not verified** | Тензионен тип главоболие | BG1, 2, p. 55 |
| Cluster headache | Кластерний головний біль | **Not verified** | Кластърно главоболие | BG1, 3.1, p. 9 |
| attack | напад | UA1 ("мігренозного нападу") | пристъп | BG1, throughout; EU-BG, p. 10 |
| Photophobia | фотофобія (світлобоязнь) | **Not verified** | фотофобия | BG1, p. 19 |
| Phonophobia | фонофобія (звукобоязнь) | **Not verified** | фонофобия | BG1, p. 19 |
| Scintillating scotoma | мерехтлива скотома | **Not verified** | сцинтилираща скотома | BG1, p. 23 ("сцинтилации, скотоми") |
| Nausea | нудота | **Not verified** | гадене | BG1, p. 19 |
| Aphasia | афазія | **Not verified** | афазия | BG1, p. 22 |
| Paresthesia | поколювання / парестезії | **Not verified** | изтръпване / парестезии | BG1, p. 91 |
| Neck stiffness | скутість шиї | **Not verified** | скованост на врата | BG1, p. 19 |
| Migraine day | день з мігренню | **Not verified** | ден с мигрена | EU-BG, p. 2 |
| Acute treatment | купірування нападу | UA1 ("купірування мігренозного приступу") | овладяване на пристъпа | **Not verified**; BG1, p. 55 has "остро лечение" |
| Preventive treatment | профілактика | UA1 ("Профілактичне лікування мігрені") | профилактика | EU-BG, p. 2 |
| Trigger | тригер | **Not verified** | тригер | BG1, p. 8 ("тригерирани") |
| Dose unit | мг | UA2 | mg | EU-BG, p. 2 |

| Medication (class) | Ukrainian | Source (uk) | Bulgarian | Source (bg) |
|---|---|---|---|---|
| Ibuprofen | Ібупрофен | UA2: 125 records (400 mg capsules) | Ибупрофен | **Not verified** |
| Sumatriptan | Суматриптан | UA2: 16 records | Суматриптан | EU-BG, p. 4 |
| Rizatriptan | Ризатриптан | UA2: 10 records, orally dispersible 10 mg | Ризатриптан | **Not verified** |
| Naproxen | Напроксен | UA2: 8 records | Напроксен | **Not verified** |
| Acetaminophen | Парацетамол | UA2: 76 records | Парацетамол | **Not verified** |
| Ubrogepant | Уброгепант | Not in UA2: **no registration**; the INN in Cyrillic | Уброгепант | **No registration** (EU) |
| Metoclopramide | Метоклопрамід | UA2: 10 records | Метоклопрамид | **Not verified** |
| Riboflavin | Рибофлавін | UA2: 2 single-substance records | Рибофлавин | **Not verified** |
| Magnesium | Магній | UA2: only in combinations | Магнезий | **Not verified** |
| Propranolol | Пропранолол | UA2: 5 records (40 mg) | Пропранолол | **Not verified** |
| Topiramate | Топірамат | UA2: 14 records | Топирамат | **Not verified** |
| Amitriptyline | Амітриптилін | UA2: 11 records | Амитриптилин | **Not verified** |
| Erenumab | Еренумаб | Not in UA2: **no registration**; the INN in Cyrillic | Еренумаб | EU-BG, p. 2 |
| NSAID | НПЗЗ | UA2 group "Нестероїдні протизапальні … засоби" | НСПВС | **Not verified** |
| Triptan | триптан | UA2 group "Селективні агоністи 5-НТ1-рецепторів" | триптан | **Not verified** |
| Beta blocker | бета-адреноблокатор | UA1 ("β-адреноблокаторів") | бета-блокер | **Not verified** |
| Anticonvulsant | протиепілептичний засіб | UA1 ("антиепілептичних засобів"); UA2 group "Протиепілептичні засоби" | антиепилептично средство | **Not verified** |
| Tricyclic antidepressant | трициклічний антидепресант | UA1 | трицикличен антидепресант | **Not verified** |

#### Medical terms: Kazakh, Greek

| Term | Kazakh | Source (kk) | Greek | Source (el) |
|---|---|---|---|---|
| Migraine without aura | Аурасыз мигрень | KZ1 meaning ("Мигрень без ауры"); **not verified** | Ημικρανία χωρίς αύρα | EL1 |
| Migraine with aura | Аурасы бар мигрень | KZ1 meaning; **not verified** | Ημικρανία με αύρα | EL1 |
| Chronic migraine | Созылмалы мигрень | KZ1 meaning; **not verified** | Χρόνια ημικρανία | **Not verified** |
| Tension-type headache | Шиеленіс түріндегі бас ауруы | **Not verified** | Κεφαλαλγία τύπου τάσεως | EL2 |
| Cluster headache | Кластерлік бас ауруы | **Not verified** | Αθροιστική κεφαλαλγία | EL2 |
| attack | ұстама | KZ2 ("ұстамалы"); KZ1 meaning ("приступ") | κρίση | EU-EL2; EL1 ("κρίση ημικρανίας") |
| Photophobia | фотофобия (жарық) | **Not verified** | φωτοφοβία (φως) | **Not verified**; EL1 has "ευαισθησία σε φως" |
| Phonophobia | фонофобия (дыбыс) | **Not verified** | φωνοφοβία (ήχος) | **Not verified**; EL1 has "ευαισθησία σε ήχους" |
| Scintillating scotoma | жыпылықтайтын скотома | **Not verified** | σπινθηροβόλο σκότωμα | **Not verified** |
| Nausea | жүрек айну | **Not verified** | ναυτία | EL1 |
| Migraine day | мигрень күні | **Not verified** | ημέρα ημικρανίας | EU-EL, p. 2 ("ημέρες ημικρανίας") |
| Acute treatment | ұстаманы басу | KZ1 meaning ("Купирование приступов мигрени") | θεραπεία της κρίσης | EL1 |
| Preventive treatment | алдын алу | KZ1 meaning ("Профилактическое лечение") | προληπτική θεραπεία | EL1; EU-EL, p. 2 ("προφύλαξη") |
| Trigger | триггер | **Not verified** | εκλυτικός παράγοντας | **Not verified** |
| Dose unit | мг | KZ1 ("мг") | mg | EU-EL, p. 2 |

| Medication (class) | Kazakh | Source (kk) | Greek | Source (el) |
|---|---|---|---|---|
| Ibuprofen | Ибупрофен | KZ1, table 5 (400 мг) | Ιβουπροφαίνη | EU-EL3, p. 2 |
| Sumatriptan | Суматриптан | KZ1, table 6 | Σουματριπτάνη | EU-EL, p. 4; EL1 |
| Rizatriptan | Ризатриптан | KZ1, table 6 | Ριζατριπτάνη | EL1 |
| Naproxen | Напроксен | KZ1, table 5 (500 мг) | Ναπροξένη | EL1 |
| Acetaminophen | Парацетамол | KZ1, table 5 (1000 мг) | Παρακεταμόλη | EL1 |
| Ubrogepant | Уброгепант | Not in KZ1; register not searched: **not verified** | Ουμπρογεπάντη | **No registration** (EU); Greek INN spelling **not verified** |
| Metoclopramide | Метоклопрамид | KZ1, table 7 | Μετοκλοπραμίδη | **Not verified** |
| Riboflavin | Рибофлавин | Not in KZ1: **not verified** | Ριβοφλαβίνη | **Not verified** |
| Magnesium | Магний | KZ1, table 10 (magnesium sulfate i.v. only) | Μαγνήσιο | **Not verified** |
| Propranolol | Пропранолол | KZ1, table 8 | Προπρανολόλη | **Not verified** |
| Topiramate | Топирамат | KZ1, table 8 | Τοπιραμάτη | **Not verified** |
| Amitriptyline | Амитриптилин | KZ1, table 8 | Αμιτριπτυλίνη | **Not verified** |
| Erenumab | Эренумаб | Not in KZ1 (it names fremanezumab only): **not verified** | Ερενουμάμπη | EU-EL, p. 2 |
| NSAID | стероидты емес қабынуға қарсы дәрі (written out) | **Not verified** | ΜΣΑΦ | EU-EL3, p. 3 |
| Triptan | триптан | KZ1, table 6 | τριπτάνη | EL1 |
| Beta blocker | бета-адреноблокатор | **Not verified** | β-αναστολέας | **Not verified** |

### Southeast Asia: Vietnamese, Indonesian, Malay, Thai

Vietnamese (`vi`), Indonesian (`id`), Malay (`ms`) and Thai (`th`). Vietnamese, Indonesian and Malay use Plus Jakarta Sans; Thai uses the system font. The project owner approved the address, the word for episode, the medical disclaimer and the watch texts on October 19, 2026. No native speaker reads these languages before a release ([LOCALIZATION_PLAN.md](LOCALIZATION_PLAN.md), section 7). The reference countries are Vietnam, Indonesia, Malaysia and Thailand.

| Language | Address | Buttons |
|---|---|---|
| vi | "bạn", as in Vietnamese iOS | Verbs, as in Vietnamese iOS: "Lưu", "Hủy" |
| id | "Anda", with a capital letter, as in Indonesian iOS | Verbs: "Simpan", "Batalkan" |
| ms | "anda", in lower case, as the Malay of Malaysia writes it | Verbs: "Simpan", "Batal" |
| th | No pronoun; "คุณ" where one is necessary, as in Thai iOS; "โปรด" for a polite request | Verbs: "บันทึก", "ยกเลิก" |

The four languages have one plural form. Thai writes no space between words, so a line can break inside a compound word. A word joiner (`&#8288;`) holds the compounds that broke on the screenshots or in the PDF together: "สิ่ง⁠กระตุ้น", "การ⁠ตั้ง⁠ค่า", "ทาง⁠การ⁠แพทย์", "อด⁠อาหาร", "เหน็บ⁠ชา", "ผิด⁠ปกติ", "เป็น⁠เวลา".

| Term | vi | id | ms | th |
|---|---|---|---|---|
| episode | cơn; "cơn migraine" where the context needs it | serangan | serangan | อาการปวด; counted with "ครั้ง" ("4 ครั้ง") |
| attack (App Store only) | cơn migraine | serangan migren | serangan migrain | อาการปวดไมเกรน |
| migraine | migraine | migren | migrain | ไมเกรน |
| log (verb) | ghi lại | catat | catat | บันทึก |
| intensity | cường độ (đau); "Cường độ 6/10" | intensitas (nyeri); "Intensitas 6/10" | keamatan (sakit); "Keamatan 6/10" | ความรุนแรง; "ความรุนแรง 6/10" |
| migraine day | ngày migraine | hari migren | hari migrain | วันที่มีไมเกรน |
| tracked day | ngày theo dõi | hari terpantau | hari dipantau | วันที่ติดตาม |
| pain-free day | ngày không đau | hari bebas nyeri | hari tanpa sakit | วันที่ไม่ปวด |
| not tracked | chưa theo dõi | belum dipantau | tidak dipantau | ไม่ได้ติดตาม |
| trigger | yếu tố kích thích; short "yếu tố" | pencetus | pencetus | สิ่งกระตุ้น |
| possible trigger | yếu tố kích thích có thể | kemungkinan pencetus | kemungkinan pencetus | สิ่งกระตุ้นที่อาจเกี่ยวข้อง |
| aura | thoáng báo (ICHD-3: "cơn thoáng báo") | aura | aura | ออร่า |
| acute medication | thuốc cắt cơn | obat untuk serangan; "Akut / abortif" | ubat semasa serangan; "Akut / abortif" | ยาบรรเทาอาการปวด |
| preventive medication | thuốc dự phòng | pencegahan | pencegahan | ยาป้องกัน |
| treatment blueprint | kế hoạch điều trị | rencana pengobatan | pelan rawatan | แผนการรักษา |
| baseline profile | hồ sơ ban đầu | profil awal | profil asas | โปรไฟล์พื้นฐาน |
| report | báo cáo | laporan | laporan | รายงาน |
| Health app | Sức khỏe | Kesehatan | Kesihatan | สุขภาพ |
| Settings (iOS) | Cài đặt | Pengaturan | Seting | การตั้งค่า |

Watch and complication texts (term pairs of the build): "Log Migraine" = "Ghi cơn mới", "Serangan baru", "Serangan baru", "บันทึกอาการ". "Save Episode" = "Lưu", "Simpan", "Simpan", "บันทึก". The round complication "LOG" (4 characters) = "GHI" (record), "BARU", "BARU" (new), "ใหม่" (new).

#### Sources of the Southeast Asian terms

Checked on October 19, 2026. A page number is the page of the PDF file.

- **[VN1]** ICHD-3, Vietnamese translation: [ICHD-3-Vietnamese.pdf](https://ihs-headache.org/wp-content/uploads/2025/04/ICHD-3-Vietnamese.pdf) on the IHS website (uploaded April 2025). No Vietnamese national migraine guideline of the Ministry of Health was found. The register of the Drug Administration of Vietnam (dav.gov.vn) and the National Drug Formulary (Dược thư Quốc gia, 2018) were **not searched**: no free copy was found.
- **[ID1]** PERDOSSI (Indonesian Neurological Association), *Acuan Panduan Praktik Klinis Neurologi*, 2016, chapter "Migren" (ICD-10 G43): [copy](https://galihendradita.wordpress.com/wp-content/uploads/2021/06/2016-ppk-neurologi.pdf). Many pages of the copy are scans without text, so some terms could not be searched. The BPOM register (cekbpom.pom.go.id) was **not searched**.
- **[MY1]** Cochrane, Malay plain language summary of review CD009108, [Sumatriptan (semua cara pengambilan) untuk serangan migrain akut](https://www.cochrane.org/ms/CD009108/sumatriptan-semua-cara-pengambilan-untuk-serangan-migrain-akut-pada-orang-dewasa-gambaran), May 28, 2014: "serangan migrain akut", "sakit kepala", "dos oral 50 mg". The Malaysian migraine guidelines are in English, and no Malay clinical text of the Ministry of Health was found. The NPRA register (Quest3+) was **not searched**. Most Malay medical words are therefore **not verified**.
- **[TH1]** Headache Study Group of the Neurological Society of Thailand, *แนวทางเวชปฏิบัติการวินิจฉัยและการรักษาปวดศีรษะไมเกรน* (clinical practice guideline for migraine), 2022 (B.E. 2565): [PDF](https://neurothai.org/media/news_file/495-CPG-migraine-2565-20230104074752.pdf). The text layer of the PDF is damaged, so pages 27 and 59 were read as images. The guideline writes the drug names in English and the dose unit "มก.". No Thai ICHD-3 is on the IHS website.
- **[TH2]** Drug Information Center, Faculty of Pharmacy, Mahidol University, [question 8461](https://pharmacy.mahidol.ac.th/dic/qa_full.php?id=8461), November 24, 2021: "ซูมาทริปแทน". The register of the Thai FDA was **not searched**. The other Thai drug names are transliterations of the INN, **not verified**.

#### Medical terms: Vietnamese, Indonesian

| Term | Vietnamese | Source (vi) | Indonesian | Source (id) |
|---|---|---|---|---|
| Migraine without aura | Migraine không cơn thoáng báo | VN1, 1.1, p. 10 | Migren tanpa aura | ID1, p. 8 |
| Migraine with aura | Migraine có cơn thoáng báo | VN1, 1.2, p. 10 | Migren dengan aura | ID1, p. 7 |
| Chronic migraine | Migraine mạn tính | VN1, 1.3, p. 36 | Migren kronik | ID1, p. 9 |
| Tension-type headache | Đau đầu kiểu căng thẳng | VN1, 2, p. 10 | Nyeri kepala tipe tegang | ID1, p. 12 |
| Cluster headache | Đau đầu cụm | VN1, 3.1, p. 59 | Nyeri kepala klaster | ID1, p. 8 |
| attack | cơn; "cơn migraine" | VN1, p. 29 | serangan | ID1, p. 7 ("serangan migren") |
| Photophobia | sợ ánh sáng | VN1, p. 29 | fotofobia (cahaya) | ID1, p. 7 |
| Phonophobia | sợ âm thanh | VN1, p. 29 | fonofobia (suara) | ID1, p. 7 |
| Scintillating scotoma | ám điểm nhấp nháy | VN1, p. 31 ("nhấp nháy", "ám điểm") | skotoma berkilau | **Not verified** |
| Nausea | buồn nôn | VN1, p. 29 | mual | **Not verified** |
| Aphasia | mất ngôn ngữ | VN1, p. 31 | afasia | **Not verified** |
| Paresthesia | châm chích / dị cảm | VN1, p. 31 ("châm chích"), p. 75 ("dị cảm") | kesemutan / parestesia | ID1, p. 17 ("parestesia") |
| Neck stiffness | cứng cổ | VN1, p. 30 | kaku leher | **Not verified** |
| Migraine day | ngày migraine | **Not verified**; VN1, p. 257 has "ngày đau đầu" | hari migren | **Not verified** |
| Acute treatment | cắt cơn | **Not verified** | obat untuk serangan; "abortif" | ID1, p. 9 ("Terapi abortif") |
| Preventive treatment | dự phòng | VN1, p. 232 ("điều trị dự phòng") | pencegahan, profilaksis | ID1, p. 10 ("profilaksis") |
| Trigger | yếu tố kích thích | VN1, p. 38 ("yếu tố kích thích (trigger)") | pencetus | ID1, p. 7 ("Faktor Pencetus") |
| Dose unit | mg | VN1 | mg | ID1, p. 9 |

| Medication (class) | Vietnamese | Source (vi) | Indonesian | Source (id) |
|---|---|---|---|---|
| Ibuprofen | Ibuprofen | **Not verified** | Ibuprofen | ID1, p. 9 |
| Sumatriptan | Sumatriptan | **Not verified** | Sumatriptan | ID1, p. 9 |
| Rizatriptan | Rizatriptan | **Not verified** | Rizatriptan | ID1, p. 9 |
| Naproxen | Naproxen | **Not verified** | Naproksen | **Not verified**; ID1, p. 13 has "naproxen" |
| Acetaminophen | Paracetamol | **Not verified**; VN1, p. 155 has "acetaminophen" | Parasetamol | ID1, p. 9 |
| Ubrogepant | Ubrogepant | **Not verified**; registration unknown | Ubrogepant | **Not verified**; registration unknown |
| Metoclopramide | Metoclopramid | **Not verified** | Metoklopramid | **Not verified** |
| Riboflavin | Riboflavin | **Not verified** | Riboflavin | **Not verified** |
| Magnesium | Magnesi | **Not verified** | Magnesium | **Not verified** |
| Propranolol | Propranolol | **Not verified** | Propranolol | **Not verified** |
| Topiramate | Topiramat | **Not verified** | Topiramat | ID1, p. 9 |
| Amitriptyline | Amitriptylin | **Not verified** | Amitriptilin | ID1, p. 9 |
| Erenumab | Erenumab | **Not verified**; registration unknown | Erenumab | **Not verified**; registration unknown |
| NSAID | NSAID | VN1, p. 155 ("thuốc chống viêm không steroid (NSAID)") | OAINS | ID1, p. 9 |
| Triptan | triptan | VN1, p. 30 | triptan | ID1, p. 9 |
| Beta blocker | thuốc chẹn beta | **Not verified** | penyekat beta | **Not verified**; ID1, p. 9 has "Beta bloker" |
| Anticonvulsant | thuốc chống động kinh | **Not verified** | antiepilepsi | ID1, p. 9 |
| Tricyclic antidepressant | thuốc chống trầm cảm ba vòng | **Not verified** | antidepresan trisiklik | ID1, p. 14 ("trisiklik") |

#### Medical terms: Malay, Thai

| Term | Malay | Source (ms) | Thai | Source (th) |
|---|---|---|---|---|
| Migraine without aura | Migrain tanpa aura | **Not verified** | ไมเกรนชนิดไม่มีออร่า | TH1, p. 27 ("migraine without aura", in English); Thai form **not verified** |
| Migraine with aura | Migrain dengan aura | **Not verified** | ไมเกรนชนิดมีออร่า | TH1, p. 27; Thai form **not verified** |
| Chronic migraine | Migrain kronik | **Not verified** | ไมเกรนชนิดเรื้อรัง | TH1, p. 59 ("โรคปวดศีรษะไมเกรนชนิดเรื้อรัง") |
| Tension-type headache | Sakit kepala jenis tegang | **Not verified** | ปวดศีรษะชนิดตึงเครียด | **Not verified**; TH1 writes "tension-type" in English |
| Cluster headache | Sakit kepala kelompok | **Not verified** | ปวดศีรษะคลัสเตอร์ | **Not verified** |
| attack | serangan | MY1 ("serangan migrain akut") | อาการปวด, "ครั้ง" | TH1, p. 27 ("อาการปวดศีรษะแต่ละครั้ง") |
| Photophobia | fotofobia (cahaya) | **Not verified**; MY1 has "sensitiviti kepada cahaya" | แพ้แสง | TH1, p. 59 ("แพ้แสงหรือเสียง") |
| Phonophobia | fonofobia (bunyi) | **Not verified**; MY1 has "bunyi" | แพ้เสียง | TH1, p. 59 |
| Scintillating scotoma | skotoma berkelip | **Not verified** | จุดบอดแสงระยิบระยับ | **Not verified** |
| Nausea | loya | **Not verified** | คลื่นไส้ | TH1, p. 27 |
| Aphasia | afasia | **Not verified** | พูดลำบาก | **Not verified** |
| Paresthesia | kesemutan / parestesia | **Not verified** | เหน็บชา | **Not verified** |
| Neck stiffness | leher kaku | **Not verified** | คอแข็งตึง | **Not verified** |
| Migraine day | hari migrain | **Not verified** | วันที่มีไมเกรน | **Not verified** |
| Acute treatment | ubat semasa serangan | **Not verified** | ยาบรรเทาอาการปวด | **Not verified**; TH1 uses "ยาแก้ปวด" |
| Preventive treatment | pencegahan | **Not verified** | ยาป้องกัน | TH1, p. 59 ("ยาป้องกันไมเกรน") |
| Trigger | pencetus | **Not verified** | สิ่งกระตุ้น | TH1, p. 59 |
| Aura | aura | **Not verified** | ออร่า | TH1, p. 36 ("อาการออร่า") |
| Dose unit | mg | MY1 ("50 mg") | มก. | TH1, pp. 38–39 |

| Medication (class) | Malay | Source (ms) | Thai | Source (th) |
|---|---|---|---|---|
| Ibuprofen | Ibuprofen | **Not verified** | ไอบูโพรเฟน | TH1, p. 43 (English name); Thai spelling **not verified** |
| Sumatriptan | Sumatriptan | MY1 | ซูมาทริปแทน | TH2 |
| Rizatriptan | Rizatriptan | **Not verified** | ไรซาทริปแทน | Not in TH1: **not verified** |
| Naproxen | Naproksen | **Not verified** | นาพรอกเซน | TH1, p. 43 (English name); Thai spelling **not verified** |
| Acetaminophen | Parasetamol | **Not verified** | พาราเซตามอล | TH1, p. 37 ("acetaminophen"); Thai spelling **not verified** |
| Ubrogepant | Ubrogepant | **Not verified**; registration unknown | ยูโบรเจแพนท์ | TH1, p. 44 (English name); registration unknown |
| Metoclopramide | Metoklopramid | **Not verified** | เมโทโคลพราไมด์ | TH1, p. 38 (English name); Thai spelling **not verified** |
| Riboflavin | Riboflavin | **Not verified** | ไรโบฟลาวิน | TH1, p. 80 ("วิตามินบี 2 (riboflavin)") |
| Magnesium | Magnesium | **Not verified** | แมกนีเซียม | TH1, p. 38 (English name); Thai spelling **not verified** |
| Propranolol | Propranolol | **Not verified** | โพรพราโนลอล | TH1, p. 63 (English name); Thai spelling **not verified** |
| Topiramate | Topiramat | **Not verified** | โทพิราเมต | TH1, p. 62 (English name); Thai spelling **not verified** |
| Amitriptyline | Amitriptilin | **Not verified** | อะมิทริปไทลีน | TH1, p. 64 (English name); Thai spelling **not verified** |
| Erenumab | Erenumab | **Not verified**; registration unknown | เอเรนูแมบ | TH1, p. 68 (English name); registration unknown |
| NSAID | NSAID | **Not verified** | NSAID | TH1, p. 43 ("NSAIDs") |
| Triptan | triptan | MY1 ("ubat triptan") | ทริปแทน | TH1, p. 43 ("ยากลุ่มทริปแทน") |
| Beta blocker | penyekat beta | **Not verified** | ยาปิดกั้นเบตา | **Not verified**; TH1, p. 63 writes "beta-blocker" |
| Anticonvulsant | antikonvulsan | **Not verified** | ยากันชัก | **Not verified** |
| Tricyclic antidepressant | antidepresan trisiklik | **Not verified** | ยาต้านซึมเศร้าชนิดไตรไซคลิก | **Not verified** |

### East Asia: Chinese (Simplified), Chinese (Traditional), Japanese, Korean

Chinese Simplified (`zh-Hans`), Chinese Traditional (`zh-Hant`), Japanese (`ja`) and Korean (`ko`). All four use the system font: PingFang SC, PingFang TC, Hiragino Sans and Apple SD Gothic Neo. The project owner approved the address, the word for episode, the medical disclaimer and the watch texts on October 20, 2026. No native speaker reads these languages before a release ([LOCALIZATION_PLAN.md](LOCALIZATION_PLAN.md), section 7). The reference countries are China mainland, Taiwan, Japan and South Korea. Plan section 6.1 picks Taiwan for `zh-Hant`: it has more people than Hong Kong and Macao, so users in Hong Kong also read the words of Taiwan (for example 乙醯胺酚, not 撲熱息痛).

| Language | Address | Buttons and punctuation |
|---|---|---|
| zh-Hans | "您" where a pronoun is necessary | Verbs: "保存", "取消". Full-width punctuation; a space between Chinese and Latin letters or digits; quotation marks “” |
| zh-Hant | "您" where a pronoun is necessary | Verbs: "儲存", "取消". Full-width punctuation; quotation marks 「」 |
| ja | Polite desu/masu sentences; no pronoun | Nouns and verbs: "保存", "キャンセル". Full-width punctuation; no space between Japanese and Latin letters or digits |
| ko | Formal polite style (합니다, requests with 하십시오) | Nouns: "저장", "취소". Korean spacing; a counter joins its number ("3회") |

The four languages have one plural form. Chinese and Japanese break a line between any two characters, except before closing punctuation; this is correct for these languages. Korean breaks only at spaces: the app keeps each Korean word on one line in code (`keepKoreanWords` for Compose, `NSLineBreakStrategyHangulWordPriority` for the PDF).

| Term | zh-Hans | zh-Hant | ja | ko |
|---|---|---|---|---|
| episode | 发作; counted with "次" ("3 次发作") | 發作; "3 次發作" | 発作; "発作3回" | 발작; "발작 3회" |
| attack (App Store only) | 偏头痛发作 | 偏頭痛發作 | 片頭痛発作 | 편두통 발작 |
| migraine | 偏头痛 | 偏頭痛 | 片頭痛 | 편두통 |
| log (verb) | 记录 | 記錄 (noun 紀錄) | 記録する | 기록하다 |
| intensity | 强度; "强度 6/10" | 強度; "強度 6/10" | 強さ; "強さ 6/10" | 강도; "강도 6/10" |
| migraine day | 偏头痛日 | 偏頭痛日 | 片頭痛日 | 편두통일 |
| tracked day | 记录日 | 記錄日 | 記録した日 | 기록한 날 ("기록한 30일") |
| pain-free day | 无痛日 | 無痛日 | 痛みのない日 | 통증 없는 날 |
| not tracked | 未记录 | 未記錄 | 記録なし | 기록 안 함 |
| trigger | 诱因 | 誘發因子 | 誘因 | 유발 요인 |
| possible trigger | 可能的诱因 | 可能的誘發因子 | 考えられる誘因 | 가능한 유발 요인 |
| aura | 先兆 | 預兆 | 前兆 | 조짐 |
| acute medication | 急性期药物 | 急性期藥物 | 急性期治療薬 | 급성기 치료제 |
| preventive medication | 预防药物 | 預防藥物 | 予防薬 | 예방 치료제 |
| treatment blueprint | 治疗方案 | 治療計畫 | 治療プラン | 치료 계획 |
| baseline profile | 基线资料 | 基本資料 | 基本プロフィール | 기본 프로필 |
| report | 报告 | 報告 | レポート | 보고서 |
| Health app | “健康” | 「健康」 | ヘルスケア | 건강 앱 |
| Settings (iOS) | 设置 | 設定 | 設定 | 설정 |

Watch and complication texts (term pairs of the build): "Log Migraine" = "记录偏头痛", "記錄偏頭痛", "片頭痛を記録", "편두통 기록". "Save Episode" = "保存", "儲存", "保存", "저장". The round complication "LOG" (4 characters) = "记录", "記錄", "記録", "기록" (record).

#### Sources of the East Asian terms

Checked on October 20, 2026. A page number is the page of the PDF file.

- **[ZH1]** ICHD-3 beta, Chinese (Simplified) translation: [3349_ichd-3-chinese.pdf](https://ihs-headache.org/wp-content/uploads/2020/05/3349_ichd-3-chinese.pdf) on the IHS website. It is the translation of the beta version; the terms of the final ICHD-3 are the same.
- **[ZH2]** 中国偏头痛诊治指南（2022版） (Chinese guideline for the diagnosis and treatment of migraine, 2022), Chinese Medical Doctor Association, neurology branch, and the headache committee of the Chinese Research Hospital Association, Chinese Journal of Pain Medicine 2022;28(12). Read in the reprint on [cn-healthcare.com](https://www.cn-healthcare.com/articlewm/20230128/content-1502901.html) (January 28, 2023), which has no page numbers. The NMPA register was **not searched**.
- **[TW1]** ICHD-3, Traditional Chinese translation of the Taiwan Headache Society: [ICHD-3_Mandarin-Traditional-Chinese.pdf](https://ihs-headache.org/wp-content/uploads/2026/09/ICHD-3_Mandarin-Traditional-Chinese.pdf) on the IHS website (uploaded September 2026).
- **[TW2]** TFDA open data, dataset 36 "全部藥品許可證資料集" (all drug licences), [data.fda.gov.tw](https://data.fda.gov.tw/), file of October 15, 2026, 26,374 licences that are not cancelled. Most product names are brands; the local name of the active substance comes from the generic names in brackets, for example "（乙醯胺酚）". No guideline of the Taiwan Headache Society was found as free text.
- **[JA1]** ICHD-3, Japanese translation of the Japanese Headache Society: [3652_ichd-3-japanese.pdf](https://ihs-headache.org/wp-content/uploads/2020/05/3652_ichd-3-japanese.pdf).
- **[JA2]** 頭痛の診療ガイドライン2021 (clinical practice guideline for headache 2021), Japanese Society of Neurology, Japanese Headache Society and Japanese Society of Neurological Therapeutics: [PDF](https://neurology-jp.org/guidelinem/pdf/headache_medical_2021.pdf). The PMDA register was **not searched**.
- **[KO1]** ICHD-3, Korean translation of the Korean Headache Society: [3334_ichd-3-korean.pdf](https://ihs-headache.org/wp-content/uploads/2020/05/3334_ichd-3-korean.pdf).
- **[KO2]** Korea Pharmaceutical Information Center, 약물백과 "편두통약" (drug encyclopedia, migraine drugs): [PDF](https://common.health.kr/shared/healthkr/pharmreview/%EC%95%BD%EB%AC%BC%EB%B0%B1%EA%B3%BC_%ED%8E%B8%EB%91%90%ED%86%B5%EC%95%BD.pdf). No Korean migraine guideline was found as free text, and the MFDS register (nedrug.mfds.go.kr) was **not searched**.

#### Medical terms: Chinese (Simplified), Chinese (Traditional)

| Term | zh-Hans | Source (zh-Hans) | zh-Hant | Source (zh-Hant) |
|---|---|---|---|---|
| Migraine without aura | 无先兆偏头痛 | ZH1, 1.1, p. 12 | 無預兆偏頭痛 | TW1, 1.1, p. 10 |
| Migraine with aura | 有先兆偏头痛 | ZH1, 1.2, p. 7 | 有預兆偏頭痛 | TW1, 1.2, p. 10 |
| Chronic migraine | 慢性偏头痛 | ZH1, 1.3, p. 7 | 慢性偏頭痛 | TW1, 1.3, p. 10 |
| Tension-type headache | 紧张型头痛 | ZH1, 2, p. 6 | 緊縮型頭痛 | TW1, 2, p. 2 |
| Cluster headache | 丛集性头痛 | ZH1, 3.1, p. 7 | 叢發性頭痛 | TW1, 3.1, p. 3 |
| attack | 发作 ("偏头痛发作") | ZH1, p. 7; 24 times in ZH1 | 發作 ("偏頭痛發作") | TW1, p. 25 |
| Photophobia | 畏光 | ZH1, p. 26 | 畏光 | TW1, p. 25 |
| Phonophobia | 畏声 | ZH1, p. 26 | 怕吵 | TW1, p. 25 ("畏光及怕吵") |
| Scintillating scotoma | 闪光暗点 | ZH1, p. 95 | 閃輝暗點 | TW1, p. 27 |
| Nausea | 恶心 | ZH1, p. 26 | 噁心 | TW1, p. 25 |
| Aphasia | 失语 | ZH1, p. 27 | 失語 | TW1, p. 26 |
| Paresthesia | 麻刺感 / 感觉异常 | ZH1, p. 28 | 麻刺感 / 感覺異常 | TW1, p. 26 ("麻刺感"), p. 64 |
| Neck stiffness | 颈部僵硬 | ZH1, p. 26 ("颈部僵硬感") | 頸部僵硬 | TW1, p. 25 |
| Migraine day | 偏头痛日 | **Not verified** | 偏頭痛日 | **Not verified** |
| Acute treatment | 急性期治疗 | ZH2 ("急性期治疗") | 急性期治療 | **Not verified** |
| Preventive treatment | 预防性治疗 | ZH2 ("预防性治疗") | 預防性治療 | **Not verified** |
| Trigger | 诱因 | ZH2 ("诱因", "诱发因素"); ZH1, p. 67 | 誘發因子 | TW1, p. 156 |
| Aura | 先兆 | ZH1 (374 times) | 預兆 | TW1 (327 times) |
| Dose unit | mg | ZH2 | mg | TW2 ("毫克" in product names; the app keeps "mg", as the dose field of the licences does) |

| Medication (class) | zh-Hans | Source (zh-Hans) | zh-Hant | Source (zh-Hant) |
|---|---|---|---|---|
| Ibuprofen | 布洛芬 | ZH2 | 布洛芬 | TW2 ("布洛芬注射液"; also "異布洛芬") |
| Sumatriptan | 舒马普坦 | ZH2 | 舒馬普坦 | **Not verified**; TW2 lists only the brand 英明格 |
| Rizatriptan | 利扎曲普坦 | ZH2 | 利扎炊坦 | TW2 ("利扎炊坦苯甲酸鹽") |
| Naproxen | 萘普生 | ZH2 | 那普洛先 | TW2 ("（那普洛先）") |
| Acetaminophen | 对乙酰氨基酚 | ZH2 | 乙醯胺酚 | TW2 ("（乙醯胺酚）", 70 licences); TW1 |
| Ubrogepant | 乌布吉泮 | ZH2 | 烏布吉泮 | Not in TW2: **no licence in Taiwan**; INN in Chinese characters after the zh-Hans name |
| Metoclopramide | 甲氧氯普胺 | ZH2 | 美托拉麥 | TW2 ("（美托拉麥）") |
| Riboflavin | 核黄素 | ZH2 | 核黃素 | TW2 ("核黃素－5－磷酸鈉") |
| Magnesium | 镁 | ZH2 ("镁剂") | 鎂 | TW2 ("氧化鎂") |
| Propranolol | 普萘洛尔 | ZH2 | 普潘奈 | TW2 ("（普潘奈）") |
| Topiramate | 托吡酯 | ZH2 | 托必拉美 | TW2 ("托必拉美") |
| Amitriptyline | 阿米替林 | ZH2 | 阿米替林 | **Not verified**; TW2 has only the chemical name "二苯環庚丙胺" |
| Erenumab | 依瑞奈尤单抗 | ZH2 | 依瑞奈尤單抗 | Not in TW2: **no licence found**; zh-Hans name in Traditional characters |
| NSAID | 非甾体抗炎药 | **Not verified** | 非類固醇消炎止痛藥 | **Not verified** |
| Triptan | 曲普坦类 | **Not verified** | 翠普登類 | TW1 ("翠普登 (triptan)") |
| Beta blocker | β 受体阻滞剂 | **Not verified** | 乙型阻斷劑 | **Not verified** |
| Anticonvulsant | 抗癫痫药 | **Not verified** | 抗癲癇藥 | **Not verified** |
| Tricyclic antidepressant | 三环类抗抑郁药 | **Not verified** | 三環抗憂鬱劑 | **Not verified** |

#### Medical terms: Japanese, Korean

| Term | Japanese | Source (ja) | Korean | Source (ko) |
|---|---|---|---|---|
| Migraine without aura | 前兆のない片頭痛 | JA1, 1.1, p. 15 | 무조짐편두통 | KO1, 1.1, p. 6 |
| Migraine with aura | 前兆のある片頭痛 | JA1, 1.2, p. 64 | 조짐편두통 | KO1, 1.2, p. 5 |
| Chronic migraine | 慢性片頭痛 | JA1, 1.3, p. 66 | 만성편두통 | KO1, 1.3, p. 5 |
| Tension-type headache | 緊張型頭痛 | JA1, p. 9 | 긴장형두통 | KO1, 2, p. 6 |
| Cluster headache | 群発頭痛 | JA1, p. 14 | 군발두통 | KO1, 3.1, p. 5 |
| attack | 発作 ("片頭痛発作") | JA1, p. 17; 40 times in JA1 | 발작 ("편두통 발작") | KO1, p. 28; KO2, p. 1 |
| Photophobia | 光過敏 | JA1, p. 23 | 빛공포증 | KO1, p. 28 |
| Phonophobia | 音過敏 | JA1, p. 23 | 소리공포증 | KO1, p. 28 |
| Scintillating scotoma | 閃輝暗点 | JA1, p. 17 | 섬광암점 | KO1, p. 30 |
| Nausea | 悪心 (common word 吐き気) | JA1, p. 23 | 구역 (common word 메스꺼움) | KO1, p. 28 |
| Aphasia | 失語 | JA1, p. 53 | 실어증 | KO1, p. 29 |
| Paresthesia | しびれ / 異常感覚 | JA1, p. 91 ("異常感覚") | 저림 / 감각이상 | KO1, p. 29 |
| Neck stiffness | 首のこわばり | **Not verified** | 목 경직 | KO1, p. 28 ("경부경직"); the app uses the common word for the neck |
| Migraine day | 片頭痛日 | **Not verified** | 편두통일 | **Not verified** |
| Acute treatment | 急性期治療 | JA2 | 급성기 치료 | KO2, p. 1 ("급성 편두통 약물") |
| Preventive treatment | 予防 | JA2 | 예방 치료 | KO2, p. 1 ("편두통 예방 약물") |
| Trigger | 誘因 | JA1, p. 188; JA1, p. 186 has "誘発因子" | 유발 요인 | KO1, p. 86 |
| Aura | 前兆 | JA1, p. 15 | 조짐 | KO1 (356 times) |
| Dose unit | mg | JA2, p. 54 ("400 mg") | mg | KO2 |

| Medication (class) | Japanese | Source (ja) | Korean | Source (ko) |
|---|---|---|---|---|
| Ibuprofen | イブプロフェン | JA2, p. 54 | 이부프로펜 | KO2, p. 3 |
| Sumatriptan | スマトリプタン | JA2, p. 47 | 수마트립탄 | KO2, p. 2 |
| Rizatriptan | リザトリプタン | JA2, p. 151 | 리자트립탄 | **Not verified** |
| Naproxen | ナプロキセン | JA2, p. 152 | 나프록센 | KO2, p. 3 |
| Acetaminophen | アセトアミノフェン | JA2, p. 13 | 아세트아미노펜 | KO2, p. 3 |
| Ubrogepant | ウブロゲパント | Not in JA2: **no approval in Japan known**; INN in katakana | 우브로게판트 | **Not verified**; registration unknown; INN in Hangul |
| Metoclopramide | メトクロプラミド | JA2, p. 151 | 메토클로프라미드 | **Not verified** |
| Riboflavin | リボフラビン | JA2, p. 14 | 리보플라빈 | **Not verified** |
| Magnesium | マグネシウム | JA2, p. 14 | 마그네슘 | **Not verified** |
| Propranolol | プロプラノロール | JA2, p. 13 | 프로프라놀롤 | **Not verified** |
| Topiramate | トピラマート | JA2, p. 136 | 토피라메이트 | KO2, p. 3 |
| Amitriptyline | アミトリプチリン | JA2, p. 14 | 아미트립틸린 | **Not verified** |
| Erenumab | エレヌマブ | JA2, p. 213 | 에레누맙 | **Not verified** |
| NSAID | NSAID (非ステロイド性抗炎症薬) | JA2, p. 13 | NSAID (비스테로이드성 소염진통제) | KO2, p. 3 |
| Triptan | トリプタン系 | JA2, p. 151 | 트립탄계 | KO2, p. 2 ("트립탄제") |
| Beta blocker | β遮断薬 | JA2, p. 13 | 베타 차단제 | **Not verified** |
| Anticonvulsant | 抗てんかん薬 | JA2, p. 136 | 항경련제 | **Not verified** |
| Tricyclic antidepressant | 三環系抗うつ薬 | **Not verified**; JA2, p. 14 has "抗うつ薬" | 삼환계 항우울제 | **Not verified** |

### Right to left: Arabic, Hebrew, Urdu

Arabic (`ar`), Hebrew (`he`) and Urdu (`ur`). All three use the system font: SF Arabic, SF Hebrew, and Noto Nastaliq Urdu, which iOS selects for Urdu by itself. The project owner approved the decisions of batch 8 on October 23, 2026: Modern Standard Arabic, the digits of the region (plan, section 4.5), "אאורה" for aura in Hebrew, "مائیگرین" for migraine in Urdu, and Pakistan as the reference country of Urdu. No native speaker reads these languages before a release ([LOCALIZATION_PLAN.md](LOCALIZATION_PLAN.md), section 7). The reference countries are Egypt, Israel and Pakistan. Plan section 6.1 would pick India for Urdu, because India is the largest country whose App Store shows Urdu; Urdu is the national language of Pakistan, so Pakistan gives the words, as South Korea and not the United States did for Korean.

| Language | Address | Buttons and punctuation |
|---|---|---|
| ar | Masculine singular in instructions ("استشر طبيبك"), as Arabic iOS writes them | Verbal nouns, as Arabic iOS: "حفظ", "إلغاء", "تسجيل نوبة". Arabic comma "،" in sentences; quotation marks «» |
| he | No gender: buttons are nouns and instructions are impersonal ("יש להתייעץ", "אפשר לעדכן"), as Hebrew iOS writes them | Nouns: "שמירה", "ביטול". Names of apps in gershayim (״בריאות״); a maqaf joins a prefix to a Latin word ("ב־iPhone") |
| ur | "آپ" with the polite imperative "کریں"; the verbs do not show the gender of the user | Verbs: "محفوظ کریں", "منسوخ کریں". English loanwords where Urdu iOS uses them ("سیٹنگ", "ایکسپورٹ"); Urdu full stop "۔" |

Plural forms: Arabic has six (zero, one, two, few, many, other). The forms one and two leave out the number ("نوبة واحدة", "نوبتان"); few takes the plural noun ("٣ نوبات"); many and other take the singular ("١١ نوبة", "٣٠ يومًا"). Hebrew has one, two and other; only days take the dual form ("יומיים"). Urdu has one and other.

| Term | ar | he | ur |
|---|---|---|---|
| episode | نوبة (plural نوبات) | התקף (plural התקפים) | دورہ (plural دورے, oblique دوروں) |
| attack (App Store only) | نوبة صداع نصفي | התקף מיגרנה | مائیگرین کا دورہ |
| migraine | الصداع النصفي | מיגרנה | مائیگرین |
| log (verb) | تسجيل, سجّل | תיעוד, לתעד | درج کرنا |
| intensity | الشدة; "الشدة ٦/١٠" | עוצמה; "עוצמה 6/10" | شدت; "شدت 6/10" |
| migraine day | يوم صداع نصفي | יום מיגרנה | مائیگرین کا دن |
| tracked day | يوم متابعة | יום מעקב | ٹریک کیا گیا دن |
| pain-free day | يوم دون ألم | יום ללא כאב | بغیر درد کا دن |
| not tracked | قبل المتابعة (before tracking) | לפני המעקב | ٹریکنگ سے پہلے |
| trigger | محفز (plural محفزات) | גורם מעורר (plural גורמים מעוררים) | محرک (plural محرکات) |
| possible trigger | محفز محتمل | גורם מעורר אפשרי | ممکنہ محرک |
| aura | الأورة | אאורה | اورا |
| acute medication | علاج حاد; "علاج النوبة الحاد" | טיפול חד; "טיפול בהתקף חד" | فوری علاج |
| preventive medication | علاج وقائي | טיפול מונע | روک تھام |
| treatment blueprint | خطة العلاج | תוכנית הטיפול | علاج کا منصوبہ |
| baseline profile | الملف الأساسي | פרופיל בסיס | بنیادی پروفائل |
| report | تقرير | דוח | رپورٹ |
| Health app | صحتي | בריאות | صحت |
| Settings (iOS) | الإعدادات | הגדרות | سیٹنگ (Settings app), سیٹنگز (in the app) |

The names of the Health and Settings apps come from the iOS 27.1 simulator runtime: `HealthUI.framework` (`HEALTH`) and the display name of `Preferences.app` in each `.lproj` folder.

Watch and complication texts (term pairs of the build): "Log Migraine" = "تسجيل نوبة" (record an episode), "תיעוד מיגרנה", "دورہ درج کریں" (record an episode). "Save Episode" = "حفظ النوبة", "שמירת ההתקף", "محفوظ کریں" (save; 14 characters on the watch). The round complication "LOG" (4 characters) = "سجل", "תעד" (masculine imperative, the only form that fits), "درج".

#### Sources of the right-to-left terms

Checked on October 23, 2026. The IHS has no ICHD-3 translation into Arabic, Hebrew or Urdu ([ICHD translations](https://ihs-headache.org/en/resources/ichd/)).

- **[AR1]** Mayo Clinic in Arabic, "الشقيقة (الصداع النصفي) - الأعراض والأسباب", [mayoclinic.org/ar](https://www.mayoclinic.org/ar/diseases-conditions/migraine-headache/symptoms-causes/syc-20360201), read in the browser. It writes "الشقيقة (الصداع النصفي)", "نوبات", "الأورة", "المحفزات", "الحساسية الشديدة للضوء والصوت", "الغثيان", "وخز", "صعوبة الكلام", "تيبّس الرقبة".
- **[AR2]** Mayo Clinic in Arabic, "الصداع النصفي المصحوب بالأورة", [mayoclinic.org/ar](https://www.mayoclinic.org/ar/diseases-conditions/migraine-with-aura/symptoms-causes/syc-20352072): "الصداع النصفي المصحوب بالأورة" and "غير المصحوب بها". The Arabic ICD-11 browser and the WHO EMRO Unified Medical Dictionary did not load without JavaScript; the registers of the Egyptian Drug Authority and the Saudi FDA were **not searched**.
- **[HE1]** Maccabi Healthcare Services, "מיגרנה - מידע רפואי", [maccabi4u.co.il](https://www.maccabi4u.co.il/healthguide/medicalconditions/migraines/), read in the browser. It writes "אאורה (aura)", "התקף מיגרנה", "טיפול בהתקף חד של מיגרנה", "טריגרים", "רגישות מוגברת לאור, לרעש", "בחילה", "נמלולים", "מיגרנה כרונית", "טריפטנים".
- **[HE2]** Clalit, Meir Medical Center, neurology department, patient text on migraine aura ([PDF](https://hospitals.clalit.co.il/meir/he/med/neuro/Documents/%D7%A0%D7%99%D7%A8%D7%99%D7%AA%20%D7%9C%D7%91%20%D7%9E%D7%99%D7%92%D7%A8%D7%A0%D7%95%D7%AA%20%D7%99%D7%93%D7%99%D7%A2%D7%95%D7%AA%20%D7%94%D7%A9%D7%A8%D7%95%D7%9F%20%D7%99%D7%A0%D7%95%D7%90%D7%A8%2023.pdf), January 2023, a scan): "אאורה", "סקוטומה". The register of the Israeli Ministry of Health (israeldrugs.health.gov.il) was **not searched**.
- **[UR1]** Continental Hospitals, Urdu page "درد شقیقہ: وجوہات، خطرے کے عوامل، علامات، علاج", [continentalhospitals.com/ur](https://continentalhospitals.com/ur/diseases/migraine), read in the browser. It writes "مائیگرین" and "درد شقیقہ", "محرکات", "متلی", "روشنی اور آواز کی حساسیت", "دائمی درد شقیقہ", "ٹرپٹانس". The hospital is in India; the words are also those of Pakistani newspapers ([Jang](https://jang.com.pk/news/1314215), found by search, not read in full).
- The DRAP register of Pakistan lists drugs in English only; the Urdu drug names are transliterations of the INN and are **not verified**.

#### Medical terms: Arabic, Hebrew, Urdu

| Term | ar | Source (ar) | he | Source (he) | ur | Source (ur) |
|---|---|---|---|---|---|---|
| Migraine without aura | صداع نصفي غير مصحوب بأورة | AR2 | מיגרנה ללא אאורה | HE1 ("אאורה") | اورا کے بغیر مائیگرین | **Not verified** |
| Migraine with aura | صداع نصفي مصحوب بأورة | AR2 | מיגרנה עם אאורה | HE1 | اورا کے ساتھ مائیگرین | **Not verified** |
| Chronic migraine | صداع نصفي مزمن | **Not verified** | מיגרנה כרונית | HE1 | دائمی مائیگرین | UR1 ("دائمی درد شقیقہ") |
| Tension-type headache | صداع التوتر | **Not verified** | כאב ראש מסוג מתח | **Not verified** | تناؤ والا سر درد | **Not verified** |
| Cluster headache | الصداع العنقودي | **Not verified** | כאב ראש מקבצי | **Not verified** | کلسٹر سر درد | **Not verified** |
| attack | نوبة | AR1 | התקף | HE1 | دورہ | **Not verified**; the common word for an attack of an illness |
| Photophobia | رهاب الضوء | **Not verified**; AR1 has "الحساسية للضوء" | פוטופוביה | **Not verified**; HE1 has "רגישות לאור" | فوٹوفوبیا | **Not verified**; UR1 has "روشنی کی حساسیت" |
| Phonophobia | رهاب الصوت | **Not verified** | פונופוביה | **Not verified** | فونوفوبیا | **Not verified** |
| Scintillating scotoma | العتمة الوامضة | **Not verified**; AR1 has "ومضات ضوئية أو بقع عمياء" | סקוטומה מנצנצת | HE2 ("סקוטומה") | چمکتا اسکوٹوما | **Not verified** |
| Nausea | الغثيان | AR1 | בחילה | HE1 | متلی | UR1 |
| Aphasia | الحُبسة | **Not verified**; AR1 has "صعوبة الكلام" | אפזיה | **Not verified** | افیزیا | **Not verified** |
| Paresthesia | تنمّل; tingling "وخز" | AR1 ("وخز") | נמלול; tingling "עקצוץ" | HE1 ("נמלולים") | پیرستھیزیا; tingling "سنسناہٹ" | **Not verified** |
| Neck stiffness | تيبّس الرقبة | AR1 | נוקשות בצוואר | **Not verified** | گردن کی اکڑن | **Not verified** |
| Migraine day | يوم صداع نصفي | **Not verified** | יום מיגרנה | **Not verified** | مائیگرین کا دن | **Not verified** |
| Acute treatment | علاج حاد | **Not verified** | טיפול בהתקף חד | HE1 | فوری علاج | **Not verified** |
| Preventive treatment | علاج وقائي | **Not verified** | טיפול מונע | **Not verified**; HE1 has "מניעה" | روک تھام | **Not verified** |
| Trigger | محفز | AR1 ("المحفزات") | גורם מעורר | **Not verified**; HE1 has the common word "טריגר" | محرک | UR1 ("محرکات") |
| Aura | الأورة | AR1, AR2 | אאורה | HE1, HE2 | اورا | **Not verified** |
| Dose unit | مجم | **Not verified**; the unit of Egyptian packs | מ״ג | **Not verified** | mg | **Not verified**; Pakistani packs write "mg" |

| Medication (class) | ar | he | ur |
|---|---|---|---|
| Ibuprofen | إيبوبروفين | איבופרופן | آئبوپروفین |
| Sumatriptan | سوماتريبتان | סומטריפטן | سوماٹرپٹان |
| Rizatriptan | ريزاتريبتان | ריזטריפטן | رزاٹرپٹان |
| Naproxen | نابروكسين | נפרוקסן | نیپروکسن |
| Acetaminophen | باراسيتامول (paracetamol, the name in Egypt and Saudi Arabia) | פרצטמול (paracetamol, the name in Israel) | پیراسیٹامول (paracetamol, the name in Pakistan) |
| Ubrogepant | أوبروجيبانت | אוברוגפנט | یوبروجیپینٹ |
| Metoclopramide | ميتوكلوبراميد | מטוקלופרמיד | میٹوکلوپرامائیڈ |
| Riboflavin | ريبوفلافين | ריבופלבין | رائبوفلیون |
| Magnesium | مغنيسيوم | מגנזיום | میگنیشیم |
| Propranolol | بروبرانولول | פרופרנולול | پروپرانولول |
| Topiramate | توبيرامات | טופירמט | ٹوپیرامیٹ |
| Amitriptyline | أميتريبتيلين | אמיטריפטילין | ایمی ٹرپٹیلین |
| Erenumab | إرينوماب | ארנומאב | ایرینومیب |
| NSAID | مضاد التهاب غير ستيرويدي | NSAID | NSAID |
| Triptan | تريبتان | טריפטן (HE1 "טריפטנים") | ٹرپٹان (UR1 "ٹرپٹانس") |
| Beta blocker | حاصر بيتا | חוסם בטא | بیٹا بلاکر |
| Anticonvulsant | مضاد للاختلاج | נוגד פרכוסים | اینٹی کنولسنٹ |
| Tricyclic antidepressant | مضاد اكتئاب ثلاثي الحلقات | נוגד דיכאון טריציקלי | ٹرائی سائیکلک اینٹی ڈپریسنٹ |

The medication names are the INN in the script of each language. No drug register of Egypt, Israel or Pakistan was searched, so each name and its registration are **not verified**; the medications stay in the catalog (decision of October 17, 2026).

### India: Hindi, Marathi, Bangla, Gujarati, Punjabi, Odia, Kannada, Malayalam, Tamil, Telugu

Hindi (`hi`), Marathi (`mr`), Bangla (`bn`), Gujarati (`gu`), Punjabi in the Gurmukhi script (`pa`), Odia (`or`), Kannada (`kn`), Malayalam (`ml`), Tamil (`ta`) and Telugu (`te`). All ten use the system font. The project owner approved the decisions of batch 9 on October 23, 2026: digits 0–9 in the text, as the region of each language writes them by default; the everyday register of iOS with its English loanwords; the loanword for migraine, except Tamil; the word for episode below; and the watch texts. No native speaker reads these languages before a release ([LOCALIZATION_PLAN.md](LOCALIZATION_PLAN.md), section 7). The reference country of all ten is India: the App Store shows them as additional languages of India, and it has no Bangladesh storefront and no Tamil in Sri Lanka.

| Language | Address | Buttons |
|---|---|---|
| hi | "आप" with "-ें" | As Hindi iOS: "सहेजें", "रद्द करें", "डिलीट करें" |
| mr | "तुम्ही" with "-आ" | As Marathi iOS: "जतन करा", "रद्द करा", "हटवा" |
| bn | "আপনি" with "-উন" | As Bangla iOS: "সেভ করুন", "বাতিল", "ডিলিট করুন" |
| gu | "તમે" with "-ો" | As Gujarati iOS: "સેવ કરો", "રદ કરો", "ડિલીટ કરો" |
| pa | "ਤੁਸੀਂ" with "-ੋ" | As Punjabi iOS: "ਸੰਭਾਲੋ", "ਰੱਦ ਕਰੋ", "ਡਿਲੀਟ ਕਰੋ" |
| or | "ଆପଣ" with "-ନ୍ତୁ" | As Odia iOS: "ସେଭ୍ କରନ୍ତୁ", "ବାତିଲ୍ କରନ୍ତୁ", "ଡିଲୀଟ୍ କରନ୍ତୁ" |
| kn | "ನೀವು" with "-ಿ" | As Kannada iOS, short: "ಸೇವ್", "ರದ್ದು", "ಡಿಲೀಟ್ ಮಾಡಿ" |
| ml | "നിങ്ങൾ" with "-ൂ" | As Malayalam iOS: "സേവ് ചെയ്യൂ", "റദ്ദാക്കൂ" |
| ta | "நீங்கள்" with "-ங்கள்" in sentences | As Tamil iOS, native bare verbs: "சேமி", "ரத்துசெய்", "நீக்கு" |
| te | "మీరు" with "-ండి" | As Telugu iOS, short: "సేవ్", "రద్దు", "డిలీట్" |

The button words come from `UIKitCore.framework` of the iOS 27.1 simulator runtime; the names of the Health and Settings apps come from `HealthUI.framework` (`HEALTH`) and from `Preferences.app`.

All ten languages have the CLDR plural forms one and other. In Hindi, Bangla, Gujarati and Kannada "one" also takes 0, and in Punjabi 0 and 1, so each form keeps the number. Bangla and Odia do not change a noun after a number, so both forms are often equal. A length limit of the build counts each vowel sign and virama as a character; the strings fit these counts.

| Term | hi | mr | bn | gu | pa |
|---|---|---|---|---|---|
| episode | दौरा (plural दौरे) | झटका (plural झटके) | অ্যাটাক | હુમલો (plural હુમલા) | ਦੌਰਾ (plural ਦੌਰੇ) |
| migraine | माइग्रेन | मायग्रेन | মাইগ্রেন | માઇગ્રેન | ਮਾਈਗ੍ਰੇਨ |
| log (verb) | दर्ज करना | नोंदवणे | রেকর্ড করা | નોંધવું | ਦਰਜ ਕਰਨਾ |
| intensity | तीव्रता | तीव्रता | তীব্রতা | તીવ્રતા | ਤੀਬਰਤਾ |
| pain | दर्द | वेदना | ব্যথা | પીડા | ਦਰਦ |
| migraine day | माइग्रेन का दिन | मायग्रेनचा दिवस | মাইগ্রেনের দিন | માઇગ્રેનનો દિવસ | ਮਾਈਗ੍ਰੇਨ ਦਾ ਦਿਨ |
| pain-free day | दर्द-मुक्त दिन | वेदनामुक्त दिवस | ব্যথামুক্ত দিন | પીડામુક્ત દિવસ | ਦਰਦ-ਮੁਕਤ ਦਿਨ |
| not tracked | ट्रैक नहीं हुआ | नोंद नाही | ট্র্যাক হয়নি | ટ્રૅક થયું નથી | ਟ੍ਰੈਕ ਨਹੀਂ ਹੋਇਆ |
| trigger | ट्रिगर | ट्रिगर | ট্রিগার | ટ્રિગર | ਟ੍ਰਿਗਰ |
| aura | ऑरा | ऑरा | অরা | ઓરા | ਔਰਾ |
| acute medication | दौरे के समय की दवा | झटक्याच्या वेळचे औषध | অ্যাটাকের সময়ের ওষুধ | હુમલા વખતની દવા | ਦੌਰੇ ਵੇਲੇ ਦੀ ਦਵਾਈ |
| preventive medication | रोकथाम | प्रतिबंधक | প্রতিরোধমূলক | નિવારક | ਰੋਕਥਾਮ |
| treatment blueprint | इलाज की योजना | उपचार योजना | চিকিৎসার পরিকল্পনা | સારવારની યોજના | ਇਲਾਜ ਦੀ ਯੋਜਨਾ |
| baseline profile | शुरुआती प्रोफ़ाइल | प्रारंभिक प्रोफाइल | প্রাথমিক প্রোফাইল | પ્રારંભિક પ્રોફાઇલ | ਸ਼ੁਰੂਆਤੀ ਪ੍ਰੋਫਾਈਲ |
| report | रिपोर्ट | रिपोर्ट | রিপোর্ট | રિપોર્ટ | ਰਿਪੋਰਟ |
| Health app | सेहत | आरोग्य | স্বাস্থ্য | આરોગ્ય | ਸਿਹਤ |
| Settings (iOS) | सेटिंग | सेटिंग | সেটিংস | સેટિંગ્સ | ਸੈਟਿੰਗਾਂ |

| Term | or | kn | ml | ta | te |
|---|---|---|---|---|---|
| episode | ଆଟାକ୍ | ಅಟ್ಯಾಕ್ (plural ಅಟ್ಯಾಕ್‌ಗಳು) | അറ്റാക്ക് (plural അറ്റാക്കുകൾ) | தாக்குதல் (plural தாக்குதல்கள்) | ఎటాక్ (plural ఎటాక్‌లు) |
| migraine | ମାଇଗ୍ରେନ | ಮೈಗ್ರೇನ್ | മൈഗ്രേൻ | ஒற்றைத் தலைவலி | మైగ్రేన్ |
| log (verb) | ରେକର୍ଡ କରିବା | ದಾಖಲಿಸು | രേഖപ്പെടുത്തുക | பதிவுசெய் | నమోదు చేయు |
| intensity | ତୀବ୍ରତା | ತೀವ್ರತೆ | തീവ്രത | தீவிரம் | తీవ్రత |
| pain | ପୀଡ଼ା | ನೋವು | വേദന | வலி | నొప్పి |
| migraine day | ମାଇଗ୍ରେନ ଦିନ | ಮೈಗ್ರೇನ್ ದಿನ | മൈഗ്രേൻ ദിവസം | தாக்குதல் நாள் (attack day) | మైగ్రేన్ రోజు |
| pain-free day | ପୀଡ଼ାମୁକ୍ତ ଦିନ | ನೋವಿಲ್ಲದ ದಿನ | വേദനരഹിത ദിനം | வலியற்ற நாள் | నొప్పిలేని రోజు |
| not tracked | ଟ୍ରାକ୍ ହୋଇନାହିଁ | ಟ್ರ್ಯಾಕ್ ಆಗಿಲ್ಲ | ട്രാക്ക് ചെയ്തിട്ടില്ല | கண்காணிக்கப்படவில்லை | ట్రాక్ కాలేదు |
| trigger | ଟ୍ରିଗର | ಟ್ರಿಗರ್ | ട്രിഗർ | தூண்டி | ట్రిగ్గర్ |
| aura | ଅରା | ಔರಾ | ഓറ | ஆரா | ఆరా |
| acute medication | ଆଟାକ୍ ସମୟର ଔଷଧ | ಅಟ್ಯಾಕ್ ವೇಳೆಯ ಔಷಧ | അറ്റാക്ക് സമയത്തെ മരുന്ന് | தாக்குதலின்போது எடுக்கும் மருந்து | ఎటాక్ సమయంలో మందు |
| preventive medication | ପ୍ରତିରୋଧକ | ತಡೆಗಟ್ಟುವಿಕೆ | പ്രതിരോധം | தடுப்பு | నివారణ |
| treatment blueprint | ଚିକିତ୍ସା ଯୋଜନା | ಚಿಕಿತ್ಸಾ ಯೋಜನೆ | ചികിത്സാ പദ്ധതി | சிகிச்சைத் திட்டம் | చికిత్స ప్రణాళిక |
| baseline profile | ପ୍ରାରମ୍ଭିକ ପ୍ରୋଫାଇଲ୍ | ಆರಂಭಿಕ ಪ್ರೊಫೈಲ್ | പ്രാരംഭ പ്രൊഫൈൽ | தொடக்கச் சுயவிவரம் | ప్రారంభ ప్రొఫైల్ |
| report | ରିପୋର୍ଟ | ವರದಿ | റിപ്പോർട്ട് | அறிக்கை | రిపోర్ట్ |
| Health app | ସ୍ବାସ୍ଥ୍ଯ | ಆರೋಗ್ಯ | ആരോഗ്യം | உடல்நலம் | ఆరోగ్యం |
| Settings (iOS) | ସେଟିଂସ୍ | ಸೆಟ್ಟಿಂಗ್ಸ್ | ക്രമീകരണം | அமைப்புகள் | సెట్టింగ్స్ |

Notes on the terms:

- Tamil writes migraine as "ஒற்றைத் தலைவலி" (14 characters), so the short labels say "தாக்குதல் நாள்" (attack day) for a migraine day and the calendar pill "தாக்குதல்: %1$s நாள்".
- Telugu labels with a limit of 16 characters say "నొప్పిలేని రోజు" in the singular: the plural "నొప్పిలేని రోజులు" has 17. The watch caption and the PDF stat are a term pair, so both use the singular.
- Malayalam "pain-free" is "വേദനരഹിതം". The watch caption and the PDF stat say only "വേദനരഹിതം", as German says "Schmerzfrei": "വേദനരഹിത ദിനങ്ങൾ" was cut on the 40 mm watch at the largest text size.
- The Export tab is "ವರದಿ" (report) in Kannada, "പങ്കിടൂ" (share) in Malayalam and "ఎగుమతి" (export, a native word) in Telugu, because the loanword has more than 10 characters.
- The anticonvulsant class names convulsions or epilepsy ("ਮਿਰਗੀ", "ಸೆಳವು", "അപസ്മാര", "வலிப்பு", "మూర్ఛ") or uses the loanword (hi), never the word for episode: in Hindi and Punjabi "दौरा" / "ਦੌਰਾ" also means a seizure.

Watch and complication texts (term pairs of the build): "Log Migraine" = "नया दौरा", "नवा झटका", "নতুন অ্যাটাক", "નવો હુમલો", "ਨਵਾਂ ਦੌਰਾ", "ନୂଆ ଆଟାକ୍", "ಹೊಸ ಅಟ್ಯಾಕ್" (new episode); "രേഖപ്പെടുത്തൂ", "பதிவு செய்" (record), because "new episode" has more than 14 characters in Malayalam and Tamil; "కొత్త ఎటాక్" (new episode). The round complication "LOG" (4 characters) = "नया", "नवा", "নতুন", "નવો", "ਨਵਾਂ", "ନୂଆ", "ಹೊಸ", "புது" (new); "ലോഗ്" and "లాగ്" (log), because "new" has 5 characters in Malayalam and Telugu.

#### Sources of the Indian terms

Checked on October 23 and 24, 2026. The IHS has no ICHD-3 translation into an Indian language ([ICHD translations](https://ihs-headache.org/en/resources/ichd/), 17 translations). The Indian guidelines are in English.

- **[IN1]** Indian Academy of Neurology, headache guidelines for primary care, [Ann Indian Acad Neurol 2011;14(Suppl 1)](https://pmc.ncbi.nlm.nih.gov/articles/PMC3152170). In English; it names paracetamol, the triptans, propranolol, topiramate, amitriptyline and flunarizine.
- **[IN2]** Translation and validation of the ID-Migraine questionnaire in Hindi and Punjabi, Ann Indian Acad Neurol, January 2023 ([repository record](https://kr.cup.edu.in/handle/32116/4270)). Abstract read; the questionnaire text was **not read**.
- **[IN3]** Duggal et al., validation of a Hindi version of the HARDSHIP questionnaire, J Headache Pain 2024 ([PMC10949646](https://pmc.ncbi.nlm.nih.gov/articles/PMC10949646/)). It cites a validated Kannada version from the Karnataka study. The questionnaire texts were **not read**.
- **[IN4]** Continental Hospitals, Hyderabad, "Migraine: causes, risk factors, symptoms, treatment" in nine Indian languages, for example [Hindi](https://continentalhospitals.com/hi/diseases/migraine) and [Telugu](https://continentalhospitals.com/te/diseases/migraine), read on October 24, 2026. There is no Odia page. The texts look machine-translated: "aura" is "आभा", "ಸೆಳವು" and "പ്രഭാവലയം" (glow), and "minimally invasive" is "ন্যূনতম আক্রমণাত্মক" (least attacking). They confirm the loanword for migraine (and "ஒற்றைத் தலைவலி" in Tamil), "ट्रिगर" and its equivalents, and the words for nausea: "मतली", "मळमळ", "বমি বমি ভাব", "ઉબકા", "ਮਤਲੀ", "ವಾಕರಿಕೆ", "ഓക്കാനം", "குமட்டல்", "వికారం". For attack they write "आक्रमण" words ("আক্রমণ", "ದಾಳಿ", "ആക്രമണം", "దాడి"), Marathi "हल्ला" next to "झटका", and Gujarati "હુમલો". The batch keeps the approved loanword for bn, or, kn, ml and te, because the native words read as an assault and the page is a machine translation.
- **[IN5]** Central Drugs Standard Control Organisation (CDSCO): the Indian register lists drugs under their English names. It was **not searched** for the 14 medications. No approval of erenumab or ubrogepant in India was found; Indian distributors list imported Aimovig. Both stay in the catalog with the note "registration unknown".

#### Medical terms: India

The diagnoses, symptoms and regions follow the English pattern "clinical term (common word)". Except where IN4 confirms a word, each term is **not verified**.

| Term | Verified in IN4 | Not verified |
|---|---|---|
| Migraine | All nine languages with a page | Odia "ମାଇଗ୍ରେନ" |
| Migraine with / without aura | — | All ten; "aura" is the loanword in every language |
| Chronic migraine | "क्रोनिक" (hi), "ક્રોનિક" (gu), "ক্রনিক" (bn) | The other seven |
| Tension-type and cluster headache | "तनाव" (hi, as a cause) | All ten; the names are loanwords "टेंशन-टाइप", "क्लस्टर" and their equivalents, as Indian neurologists say them |
| Nausea | All nine (see IN4) | Odia "ବାନ୍ତି ଭାବ" |
| Photophobia, phonophobia | The words for light and sound (hi, mr, bn, gu, kn, ta) | The loanwords in brackets |
| Paresthesia | "झुनझुनी" (hi), "ਝਰਨਾਹਟ" (pa), "ಜುಮ್ಮೆನಿಸುವಿಕೆ" (kn) | The other seven |
| Trigger | hi, mr, bn, gu, te; pa writes "ਟਰਿੱਗਰ" | kn and ml (IN4 has native "ಪ್ರಚೋದಕ", "പ്രേരക"); Tamil "தூண்டி" (IN4 has the verb "தூண்டு") |
| Triptan | "ट्रिप्टान" (hi), "ಟ್ರಿಪ್ಟಾನ್" (kn), "ട്രിപ്റ്റാൻ" (ml), "டிரிப்டான்" (ta), "ట్రిప్టాన్" (te) | mr, bn, gu, pa, or |
| Dose unit | — | "mg" in Latin letters, as Indian packs and prescriptions write it |

| Medication | Name in the ten languages | Status |
|---|---|---|
| Acetaminophen | Paracetamol, as India names it: "पैरासिटामोल", "पॅरासिटामॉल", "প্যারাসিটামল", "પેરાસિટામોલ", "ਪੈਰਾਸੀਟਾਮੋਲ", "ପାରାସିଟାମୋଲ୍", "ಪ್ಯಾರಸಿಟಮಾಲ್", "പാരസെറ്റമോൾ", "பாராசிட்டமால்", "పారాసిటమాల్" | IN1 (English name); spellings **not verified** |
| Ibuprofen, naproxen, sumatriptan, rizatriptan, metoclopramide, propranolol, topiramate, amitriptyline, riboflavin, magnesium | The INN in the script of each language | IN1 names most of them in English; spellings **not verified** |
| Ubrogepant, erenumab | The INN in the script of each language | Registration in India unknown (IN5) |
