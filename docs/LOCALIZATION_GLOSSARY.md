# Localization glossary

Status: English source terms, October 17, 2026. The translation batches add a column for each language and a source for each medical term ([LOCALIZATION_PLAN.md](LOCALIZATION_PLAN.md), sections 6 and 6.1).

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
