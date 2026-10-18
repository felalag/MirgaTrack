# Localization glossary

Status: English source terms, October 17, 2026. Russian and Belarusian, October 18, 2026. Each translation batch adds a column for each of its languages and a source for each medical term ([LOCALIZATION_PLAN.md](LOCALIZATION_PLAN.md), sections 6 and 6.1).

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
