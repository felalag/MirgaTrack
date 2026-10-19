# Localization checklist

Use this list for each translation batch of [LOCALIZATION_PLAN.md](LOCALIZATION_PLAN.md) (section 8.2). One batch is one pull request. The terms come from [LOCALIZATION_GLOSSARY.md](LOCALIZATION_GLOSSARY.md).

## 1. Files for each language

Use the folder code of the plan, section 1.1. Examples here use Polish, `pl`.

| File | What to add |
|---|---|
| `composeApp/src/commonMain/composeResources/values-pl/strings.xml` | Every key of `values/strings.xml`. Keep the `%N$s` placeholders; a plural form can leave out the number where the language does. |
| The same file | `legal_folder` = `pl`. In a regional folder (`values-fr-rCA`) leave it out, unless the region has its own legal texts: then `legal_folder` = `pt-PT` and `docs/legal/files/pt-PT/`. |
| A regional folder (`values-fr-rCA`, `values-es-rES`, `values-pt-rPT`) | Only the keys that differ from the folder of the language. Compose takes the region from the first preferred language of iOS (`fr-CA` → `values-fr-rCA`, `es-MX` → `values-es`). |
| The same file, from stage 1g | `font_family` = `brand` for a Latin script, `system` for every other script (plan, section 4.7). |
| `iosApp/**/Localizable.xcstrings`, `InfoPlist.xcstrings` | A `pl` translation in the state "translated" for each string. Xcode: open the catalog, add the language, translate, mark each string as reviewed. A region (`fr-CA`) needs a full set too: iOS offers a region only when the bundle has its `.lproj` folder. |
| `AppShortcuts.xcstrings` | The Siri phrases, only for a Siri language (plan, section 4.3). Each phrase keeps `${applicationName}`. `SIRI_LANGUAGES` in `buildSrc/src/main/kotlin/l10n/Languages.kt` lists them; the build asks for phrases only in these languages. |
| `iosApp/iosApp.xcodeproj/project.pbxproj` | `pl` in `knownRegions`, and each region (`"fr-CA"`). Xcode adds it when you add the language to the project. |
| `docs/legal/files/pl/privacy-policy.md`, `terms-of-service.md` | The translated legal texts, without the front matter. The first paragraph says that the English text is the binding text and the translation is for convenience. |
| `docs/_config.yml` | `- legal/files/pl` under `exclude`, so the website stays English. |
| `docs/LOCALIZATION_GLOSSARY.md` | A column or rows for the language, with the source of each medical term (plan, section 6.1). |
| `docs/app-store/pl.md` | The App Store metadata, when the App Store has the language (plan, section 5.4). |

## 2. Translation rules

- Use the comment of each key: where the text shows, what each placeholder means, and the maximum length.
- A "Max N characters." limit counts the text with its placeholders filled in. The build counts each placeholder as two characters.
- Fill in every plural form that the language needs. The build lists the missing forms.
- Use the glossary term for each concept. The build compares the shared terms of the iPhone and the watch.
- Follow the style of the plan, section 6: sentence case, the polite form of address, no slang.
- Do not translate brand names (MigraTrack) or keys marked "Do not translate".
- Join a number and its unit with a no-break space, written as the entity `&#160;` ("50&#160;mg", "90&#160;dni"), and put a word joiner `&#8288;` on both sides of the dash in "1–10". At the largest text size the lines break there otherwise.
- Use the term of the national sources for the word of the episode before the sample goes to the owner. In batch 4 the Romanian ICHD-3 said "atac", and the sample had "criză".
- A length limit counts characters, not width. Cyrillic and Greek capitals are wide: read the PDF table headers and the 40 mm watch at the largest text size even when the build passes. In batch 5 "БОЛКА", "ΠΟΝΟΣ" (5 characters) and "КҮНІ МЕН УАҚЫТЫ" (15) broke onto two lines.

## 3. Back-translation

A second pass translates the strings below back into English, without the English source. Compare each back-translation with the English text. Fix each difference in meaning before the merge. List the differences, their fixes and the differences that a length limit keeps in a new section of [LOCALIZATION_BACKTRANSLATION.md](LOCALIZATION_BACKTRANSLATION.md), and link it from the pull request.

| Area | Keys and files |
|---|---|
| Diagnoses | `diagnosis_*` |
| Symptoms and aura | `symptom_*` |
| Pain locations | `region_*` |
| Medications | `medication_*`, also the drug classes (`*_class`) and the dose unit |
| Triggers | `trigger_*` and their descriptions |
| Frequency and duration | `frequency_*`, `duration_*` |
| Pain scale | `pain_scale_*` |
| Medical notes | `common_medical_disclaimer`, `insights_triggers_hint`, `report_triggers_note`, `report_disclaimer` |
| Report | `report_*` |
| Legal texts | `docs/legal/files/<language>/*.md` |

## 4. Checks before the merge

1. `./gradlew check` passes. It runs `verifyTranslations` (keys, placeholders, plural forms, lengths, catalogs, language lists, legal folders, glossary terms) and its self-test.
2. Build the app for the simulator with the pseudo-locales, then take the screenshots:

   ```bash
   MIGRATRACK_PSEUDO=true xcodebuild -project iosApp/iosApp.xcodeproj -scheme iosApp -configuration Debug -destination 'platform=iOS Simulator,id=<udid>' build
   ```

   ```bash
   WATCH=<watch-udid> scripts/l10n-screenshots.sh <udid> build/l10n-screenshots pl:pl_PL
   ```
3. Read each screenshot for cut, overlapping or untranslated text. The project owner reads the Russian, Belarusian and English screenshots.
4. Repeat step 2 at the largest text size that the app supports (plan, section 5.1).
5. Export a PDF and an XLSX in the language and read them.
