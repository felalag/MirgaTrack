# Localization glossary

Status: English source terms, October 17, 2026. Russian and Belarusian, October 18, 2026. The Western European and the Central and Northern European batches, October 18, 2026. Ukrainian, Bulgarian, Kazakh and Greek, October 19, 2026. Vietnamese, Indonesian, Malay and Thai, October 19, 2026. Each translation batch adds a column for each of its languages and a source for each medical term ([LOCALIZATION_PLAN.md](LOCALIZATION_PLAN.md), sections 6 and 6.1).

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
