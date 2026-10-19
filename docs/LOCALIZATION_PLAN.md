# Localization plan

Status: plan only, October 17, 2026. No app code changed. Version 1.0 ships in English only (README, "Known limits").

## 1. Summary

Prepare the code first and translate later. Stage 1 moves every string into resource files and keeps English as the only language. The app looks the same after it, except for dates and times. They follow the region of the device, and this closes D10 in [REVIEW_READINESS.md](REVIEW_READINESS.md).

The target is every primary language of the App Store countries that iOS supports, and Belarusian: 47 languages next to English (section 1.1).

The recommended technology needs no new dependency:

- **iPhone UI (Compose):** Compose Multiplatform resources. `compose-components-resources` is already in `composeApp`.
- **Swift (watch app, complications, App Intents, Info.plist):** String Catalogs (`.xcstrings`) in Xcode.
- **`shared/domain` and `shared/export`:** no strings. They give enums, IDs and numbers. The UI layer gives the text through small interfaces.
- **Dates and numbers:** the system formatters of iOS (`NSDateFormatter`, `NSNumberFormatter`), behind one interface.
- **Language:** the language of the iPhone. The app has no language picker.

The work has about 450 keys and 2,400 words for each language, together with the in-app legal texts and the App Store metadata. For 47 languages this is about 110,000 words. Stage 1 has seven pull requests, and the translations come in eight batches (section 8).

Three findings change the order of the work:

- **The font covers only the Latin script.** Plus Jakarta Sans has all letters of the 23 Latin-script languages, but no Cyrillic, Greek, Arabic, Hebrew, Indic, Thai, Chinese, Japanese or Korean letters. These scripts use the system font, as the apps of Apple do (section 4.7).
- **Three languages read from right to left:** Arabic, Hebrew and Urdu. The layouts, the charts, the PDF and the XLSX need a right-to-left mode (section 4.8).
- **iOS does not see the Compose resources.** iOS gets the language list of the app from the `.lproj` folders of the bundle. The App Store "Languages" field, the per-app language setting, and the system dialogs (Quick Look, share sheet) use this list (section 4.9). Each new language therefore also needs an `.lproj` folder, for example from `InfoPlist.xcstrings`.

The project owner made these decisions on October 17, 2026:

| Question | Decision |
|---|---|
| Languages | Every primary language of the App Store countries, and Belarusian (section 1.1) |
| Format of the string files | XML (Compose resources). No YAML. |
| Who translates | Claude. The project owner reads Russian, Belarusian and English. |
| Medical terms | Claude checks each term against the medical sources of a country of the language (section 6.1). |
| Terms of Service and Privacy Policy | Translate them in the app. |
| Website (GitHub Pages) | Stays English only. |
| Built-in triggers and medications | Translate their names and descriptions, and show them by ID (section 3). |
| Medications without a registration in a country | Stay in the catalog of that language. |
| Font for non-Latin scripts | The system font (section 4.7). |
| Language picker in the app | None for now. The app uses the language of the iPhone. |
| First day of the week | Follows the region of the device (section 4.5). |
| Language of the report | The language of the app. |

No question is open now (section 9).

### 1.1 Target languages

The rule has two parts. The language is the default or an additional language of at least one App Store country. iOS also has it as a system language. Two sources give the lists:

- App Store Connect, [App Store localizations](https://developer.apple.com/help/app-store-connect/reference/app-store-localizations): 50 localizations and a table of 173 countries with their default and additional languages. Each of the 50 localizations is in use in at least one country.
- The system languages of iOS 27.1: the `.lproj` folders of the Settings app in the iOS 27.1 simulator runtime. They contain all 50 localizations, and also Bulgarian, Kazakh and Lithuanian.

Bulgaria, Kazakhstan and Lithuania are App Store countries, but the App Store shows only English (U.K.) there. These three languages therefore get a translation of the app, but no App Store metadata.

**Belarusian is an addition to the rule.** The project owner reads it. The App Store in Belarus shows only English (U.K.), and iOS has no Belarusian system language. A user can still add Belarusian in Settings › General › Language & Region › Other Languages; iOS then gives it to the apps that have it. Belarusian therefore also gets a translation of the app, but no App Store metadata. In 68 other countries the App Store also shows only English. Many of them speak English, or a language that is a target already (Russian in Belarus, Romanian in Moldova, Portuguese in Angola). The other languages there are not iOS system languages, for example Albanian, Armenian, Georgian, Latvian, Estonian, Uzbek and Swahili. They are not targets.

| Group | Languages (folder code) | Script and font | Direction |
|---|---|---|---|
| English (source) | English, US (`values`). U.K., Australia and Canada get App Store metadata only. | Latin, Plus Jakarta Sans | Left to right |
| Western Europe | German (`de`), French (`fr`), French Canada (`fr-rCA`, changes only), Spanish Latin America (`es`), Spanish Spain (`es-rES`, changes only), Portuguese Brazil (`pt`), Portuguese Portugal (`pt-rPT`, changes only), Italian (`it`), Dutch (`nl`), Catalan (`ca`) | Latin, Plus Jakarta Sans | Left to right |
| Central and Northern Europe | Polish (`pl`), Czech (`cs`), Slovak (`sk`), Croatian (`hr`), Slovenian (`sl`), Romanian (`ro`), Hungarian (`hu`), Lithuanian (`lt`), Danish (`da`), Norwegian Bokmål (`nb`), Swedish (`sv`), Finnish (`fi`), Turkish (`tr`) | Latin, Plus Jakarta Sans | Left to right |
| Cyrillic and Greek | Russian (`ru`), Belarusian (`be`), Ukrainian (`uk`), Bulgarian (`bg`), Kazakh (`kk`), Greek (`el`) | System font | Left to right |
| Southeast Asia | Vietnamese (`vi`), Indonesian (`id`), Malay (`ms`), Thai (`th`) | Latin with Plus Jakarta Sans; Thai with the system font | Left to right |
| East Asia | Chinese Simplified (`b+zh+Hans`), Chinese Traditional (`b+zh+Hant`), Japanese (`ja`), Korean (`ko`) | System font | Left to right |
| India | Hindi (`hi`), Marathi (`mr`), Bangla (`bn`), Gujarati (`gu`), Punjabi (`pa`), Odia (`or`), Kannada (`kn`), Malayalam (`ml`), Tamil (`ta`), Telugu (`te`) | System font | Left to right |
| Right to left | Arabic (`ar`), Hebrew (`he`), Urdu (`ur`) | System font | Right to left |

That is 47 full translations and 3 small sets of regional changes. A regional folder holds only the keys that differ; Compose resources take the other keys from the language folder. Spanish uses the Latin American text as its base, because 19 countries show Spanish (Mexico) and only Spain shows Spanish (Spain). Stage 1 checks the folder codes on iOS, in particular `nb` for Norwegian and the script folders for Chinese, as the JetBrains documentation describes them. Batch 3 checked the regional codes (October 18, 2026): Compose resources 1.12.1 read the region of the first preferred language, so `fr-CA` selects `values-fr-rCA` and `es-MX` or `es-419` selects `values-es`. `Bundle.preferredLocalizations` gives `["pt-PT", "pt"]` for `pt-PT` and `["es"]` for `es-MX`, so the String Catalogs and `knownRegions` use `fr-CA`, `es-ES` and `pt-PT` next to `fr`, `es` and `pt`. Batch 4 checked Norwegian (October 18, 2026): the language list of iOS 27.1 (`RenderableUILanguages.plist`) names Bokmål `nb`, so Settings writes `nb-NO`. Compose resources then select `values-nb`, and the bundle selects `nb.lproj` from the String Catalogs, although the system apps of Apple keep a `no.lproj` folder. The screenshots with `-AppleLanguages "(nb-NO)"` show the Norwegian text on the iPhone and the watch.

## 2. String inventory

The counts come from a scan of the string literals on `origin/main` at `bcc64bd`. "Strings" counts literals with letters. Text fragments in `if` expressions count as separate literals. IDs, XML, file paths and log messages are not in the counts. A "word" is a run of letters; placeholders do not count.

| Area | Where | Strings | Words | Notes |
|---|---|---:|---:|---|
| iPhone screens | `composeApp/src/commonMain/.../feature/*` | 242 | 843 | Insights 66, onboarding 35, Settings 34, Summary 29, episode form 39, Export 22, welcome 12, legal 3, log 2 |
| Shared UI parts | `ui/components`, `ui/Formatting.kt` | 17 | 24 | Tab titles, Back, On/Off, pain scale labels, "7 h 20 min" |
| Native dialogs and sharing | `composeApp/src/iosMain/.../platform` | 7 | 11 | OK, Cancel, Save, "Could not write …" |
| Siri summary from Kotlin | `QuickLogBridge.kt` | 2 | 5 | "intensity 6 of 10, with …" goes into a Swift dialog |
| Domain enums and catalogs | `shared/domain/.../model/*.kt` | 119 | 242 | Diagnosis 12, Symptom 14, CranialRegion 5, TriggerCategory 6, EpisodeFrequency 4, TypicalDuration 4, ExportRange 4, MedicationRole 2, 15 triggers with descriptions, 14 medications with drug classes |
| Date names | `DateText.kt` | 40 | 40 | Month and weekday names, Today, Yesterday. The system formatter replaces them. |
| Domain text | `TriggerFrequency.kt`, `Insights.kt` | 5 | 12 | "1 of 3 episodes has no triggers noted", "W1" |
| Data layer | `SqlCatalogRepository.kt` | 2 | 4 | "Custom trigger", "Custom medication" |
| PDF report | `shared/export/.../pdf/ReportPdf.kt` | 54 | 170 | Titles, column headers, legend, notes |
| XLSX report | `shared/export/.../xlsx/ReportWorkbook.kt` | 37 | 58 | Sheet names, column headers, profile fields |
| Watch app | `iosApp/watchApp/*.swift` | 15 | 37 | 40 mm screen; short text is necessary |
| Complications and control | `iosApp/watchComplications/Complications.swift` | 13 | 38 | "LOG" in the circular style is the tightest text |
| App Intents and App Shortcuts | `iosApp/iosApp/LogMigraineIntent.swift`, `iosApp/watchApp/OpenLoggerIntent.swift` | 8 | 37 | Two Siri phrases, title, description, dialogs |
| Info.plist | `iosApp/iosApp/Info.plist` | 1 | 20 | `NSHealthShareUsageDescription`. `CFBundleDisplayName` stays "MigraTrack". |
| **App total** | | **≈ 560** | **≈ 1,540** | About 450 keys after the fragments join into full sentences and plurals |
| Legal texts | `docs/legal/files/*.md` | 2 files | 483 | Translated in the app only |
| Website | `docs/index.md`, `docs/support.md` | 2 files | 263 | Not translated (decision of October 17, 2026) |
| App Store metadata | `docs/APP_STORE.md`, "App information" and "Version information" | 6 fields | ≈ 370 | Subtitle, promotional text, description, keywords, What's New |
| **Total for each language** | | | **≈ 2,400** | Without the website |
| **Total for 47 languages** | | | **≈ 110,000** | Belarusian, Bulgarian, Kazakh and Lithuanian have no App Store metadata; the regional sets are small |

### 2.1 Problems in the English source

These places cannot be translated as they are. Stage 1 changes them.

| Problem | Example | Change |
|---|---|---|
| Plurals with `if` | [InsightsScreen.kt:212](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/insights/InsightsScreen.kt#L212), [SettingsScreen.kt:79](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/settings/SettingsScreen.kt#L79), [SummaryScreen.kt:94](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/summary/SummaryScreen.kt#L94), [ReportPdf.kt:107](../shared/export/src/commonMain/kotlin/com/migratrack/export/pdf/ReportPdf.kt#L107) | `<plurals>` resources. The languages need from one form (Chinese, Japanese) up to six forms (Arabic); see section 4.6. |
| Sentences from fragments | "By continuing, you agree to " + link + " and " + link ([WelcomeScreen.kt:166](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/welcome/WelcomeScreen.kt#L166)); "$noun this Week" ([InsightsScreen.kt:214](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/insights/InsightsScreen.kt#L214)); "fewer"/"more" in [InsightsScreen.kt:285](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/insights/InsightsScreen.kt#L285) | One string for each full sentence, with numbered placeholders (`%1$s`). Translators can then change the word order. Links get placeholders in one string. |
| Logic that compares display text | `column.title == "Pain"` in [ReportPdf.kt:289](../shared/export/src/commonMain/kotlin/com/migratrack/export/pdf/ReportPdf.kt#L289) | Compare a column ID. |
| Text in the domain | `withoutTriggersText` ([TriggerFrequency.kt:19](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/analytics/TriggerFrequency.kt#L19)); "W1" bar labels ([Insights.kt:132](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/analytics/Insights.kt#L132)); "Custom trigger" in the data layer ([SqlCatalogRepository.kt:59](../shared/data/src/commonMain/kotlin/com/migratrack/data/SqlCatalogRepository.kt#L59)) | The domain gives counts and date ranges. The UI and the report make the text. |
| Fixed calendar layout | Weekday letters "M T W T F S S" and a Monday start ([InsightsCalendar.kt:74](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/insights/InsightsCalendar.kt#L74)) | Letters from `veryShortStandaloneWeekdaySymbols`. The first weekday follows the region (section 4.5). |
| Number formats | "6.5" for the average pain ([ReportPdf.kt:93](../shared/export/src/commonMain/kotlin/com/migratrack/export/pdf/ReportPdf.kt#L93)); "25%"; "v1.0 (Build 3)"; "STEP 1/3" | `NSNumberFormatter`. Many languages use a decimal comma, and some put a space before "%". |
| Digits from `toString()` | The big numbers on Summary, Insights, Export and the welcome screen, the calendar days, the pain scale ([SummaryScreen.kt:100](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/summary/SummaryScreen.kt#L100), [PainScale.kt:127](../composeApp/src/commonMain/kotlin/com/migratrack/app/ui/components/PainScale.kt#L127), [InsightsCalendar.kt:266](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/insights/InsightsCalendar.kt#L266)) | `NSNumberFormatter` for every number on the screen. Arabic can use Eastern Arabic digits, depending on the region (Apple HIG, Right to left). |
| Different terms for one thing | "Episode", "Attack", "Event", "Log"; "VAS 0–10", "Peak Pain (0-10)" and "Pain Intensity (1-10)" for a scale of 1 to 10 | Fix the English first: one term for one thing. Then write the glossary (section 6). |
| Title Case | "Log Migraine Episode", "Delete Episode Log" | Most languages use sentence case, and many scripts have no letter case. The style guide tells the translation not to copy Title Case. |
| Upper case in code | `text.uppercase()` in [Basics.kt:66](../composeApp/src/commonMain/kotlin/com/migratrack/app/ui/components/Basics.kt#L66) and in the PDF column headers | Wrong for Turkish: "i" must become "İ". Use the locale-aware upper case of Compose (`androidx.compose.ui.text.toUpperCase`). Scripts without case (Arabic, Hebrew, Indic, Thai, CJK) stay as they are. |

## 3. User data and stored names

The data layer stores enums by name and keeps the catalogs of triggers and medications in code ([Trigger.kt](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/model/Trigger.kt), [Medication.kt](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/model/Medication.kt)). Diagnoses, symptoms, regions, frequency and duration are therefore not a problem. Only these values are English text in the database:

| Table | Column | Content |
|---|---|---|
| `episode_trigger` | `name` | A copy of the trigger name at the time of the log |
| `medication_intake` | `name`, `dose` | A copy of the medication name and dose, for example "Ibuprofen", "400mg" |
| `custom_trigger`, `custom_medication` | `name`, `dose` | Text that the user typed |

The sync payloads to the watch also carry these copies (`SyncIntake`, `SyncTrigger`, `SyncMedication` in [SyncModels.kt](../shared/sync/src/commonMain/kotlin/com/migratrack/sync/SyncModels.kt)).

**Recommendation: show a built-in item by its ID, not by its stored copy.**

- If `trigger_id` or `medication_id` belongs to the built-in catalog, the app shows the name in the current language. A language change then also changes all old episodes, the Insights card and the reports.
- The stored copy stays the fallback for custom items and for IDs that a later version removes. The reason for the copy ("history stays readable after the user edits the catalog") stays true, because the user cannot edit a built-in item.
- The text that the user typed stays as it is. The app does not translate it.
- The dose of a built-in medication becomes an amount and a unit in the catalog, for example 400 and mg. The app shows "400 mg" or "400 мг". A stored dose that differs from the catalog stays as stored.
- No database migration is necessary. The watch needs no change, because it shows no names.

Two details follow from this:

- `TriggerFrequency` sorts by name ([TriggerFrequency.kt:32](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/analytics/TriggerFrequency.kt#L32)). Equal counts must sort by the shown name, with a locale-aware comparison (`localizedCompare` on iOS). The UI layer does this sort.
- The built-in names and descriptions get a translation (decision of October 17, 2026). Medication names use the local name of the active substance (section 6.1).

## 4. Technology options

### 4.1 iPhone UI: Compose Multiplatform resources

**Recommended.** Strings go into `composeApp/src/commonMain/composeResources/values/strings.xml`, and each language gets `values-ru/`, `values-pl/` and so on. The build generates `Res.string.*` and `Res.plurals.*` accessors.

| For | Against |
|---|---|
| Already a dependency (fonts use it). No new library. | iOS does not see these files as localizations (section 4.9). |
| `stringResource`, `pluralStringResource` in composables; `getString`, `getPluralString` (suspend) in view models and in `iosMain` | Placeholders are only `%1$s` and `%1$d`. Numbers must be formatted before they go in. |
| CLDR plural rules: zero, one, two, few, many, other | No pseudo-locale and no translation states, unlike String Catalogs |
| The same XML as Android `strings.xml`. Every translation service reads it, and Android later uses the same files. | A missing key falls back to the English text of that key, without a warning. A test must find missing keys (section 5.2). |
| On iOS the library walks the whole list `NSLocale.preferredLanguages` and takes the first language that has a match ([source](https://github.com/JetBrains/compose-multiplatform/blob/master/components/resources/library/src/iosMain/kotlin/org/jetbrains/compose/resources/ResourceEnvironment.ios.kt)). This is the same rule that iOS uses for bundles. | The `files/` folder has no qualifiers. The legal texts need their own language rule (section 4.2). |

The view models build strings now (for example `SettingsViewModel`, `EpisodeFormViewModel`). Two ways are possible:

- The view model keeps plain state (counts, enums) and the composable makes the text. This is the default for new code.
- For dialog titles and messages, the view model calls `getString`. The dialogs are suspend calls already.

`UIKitDialogs.kt` gets "OK" and "Cancel" from the caller. Quick Look and the share sheet are system UI. iOS translates them when the bundle has the language (section 4.9).

### 4.2 Modules without Compose: `shared/domain`, `shared/export`, legal texts

`shared/domain` compiles for watchOS, and Compose Multiplatform has no watchOS target. `compose-components-resources` also depends on Compose `foundation` and `runtime` (Gradle module metadata of 1.12.1). This rules out Compose resources in `domain`, and makes them a poor fit for `export`, which has JVM tests.

| Option | For | Against |
|---|---|---|
| **A. Keys in the domain, text in the UI layer (recommended)** | The domain stays pure Kotlin. An exhaustive `when` over each enum in `composeApp` maps it to a `StringResource`, so a new enum entry does not compile without a string. [CatalogVisuals.kt](../composeApp/src/commonMain/kotlin/com/migratrack/app/ui/CatalogVisuals.kt) already maps catalog IDs to icons in the same way. | About 130 mapping lines |
| B. Compose resources in `shared/export` | One mechanism | Brings Compose into a module that is now free of it; the JVM tests need the desktop variant |
| C. A strings interface for each module | Explicit and testable | More code than A for the domain; correct for `export` |

**For `shared/export`: option C.** The report gets a `ReportStrings` interface (titles, column headers, plurals as functions) and a `DateFormats` interface (section 4.5). `composeApp` implements both with `getString` and `NSDateFormatter`. The JVM tests use an English implementation, so the current tests keep their text. The report also needs the language of the UI for its catalog names, so it gets the resolved names, not the IDs.

**Legal texts.** The English files stay in `docs/legal/files/`. Each translation goes into a folder for its language, for example `docs/legal/files/pl/privacy-policy.md`. `LegalScreen` reads the folder name from the string `legal_folder`, so the legal text and the UI strings always have the same language, and it falls back to English when the file is missing. The website stays English only, so `docs/_config.yml` excludes the language folders from GitHub Pages. The Privacy Policy URL in App Store Connect points to the English page for each language. Each translation starts with one sentence: the English text is the binding text, and the translation is for convenience.

### 4.3 Swift: String Catalogs

**Recommended** for the targets `iosApp`, `MigraTrackWatch` and `watchComplications`. The build setting `SWIFT_EMIT_LOC_STRINGS = YES` is already on. SwiftUI `Text("…")`, `Button("…")` and `LocalizedStringResource` are already localizable APIs, so the catalog fills itself at the next build.

| File | Contents |
|---|---|
| `Localizable.xcstrings` in each target | SwiftUI text, accessibility labels, complication and control names, intent title, description and dialogs |
| `InfoPlist.xcstrings` (iPhone) | `NSHealthShareUsageDescription`. It also creates the `.lproj` folders that iOS needs (section 4.9). |
| `AppShortcuts.xcstrings` (iPhone) | The Siri phrases. Each phrase must keep `${applicationName}`. |

| For | Against |
|---|---|
| Plural variants and device variants; translation states (New, Needs Review, Stale); XLIFF and `.xcloc` export | A second file format next to the Compose XML |
| Xcode pseudolanguages for Swift text (section 5.1) | Developer forums report problems with phrase variants in `AppShortcuts.xcstrings` ([thread 797784](https://developer.apple.com/forums/thread/797784)). Test each phrase with Product › App Shortcuts Preview. |
| Xcode can generate comments for translators and suggest translations (Apple documentation, read on October 17, 2026) | |

Specific changes:

- `LogMigraineIntent` puts a Kotlin string into its dialog. `QuickLogBridge` must return the intensity and the medication names as data. Swift makes the sentence with a `LocalizedStringResource`.
- The watch formats "Last attack Oct 12" with `format: .dateTime`, which is already localized. "\(days) of \(entry.trackedDays) days pain-free" needs a plural variant.
- Siri: Apple's iOS feature availability page did not list Polish or Ukrainian for Siri on October 17, 2026. Without Siri, the App Shortcut still shows its translated short title in Spotlight and in Shortcuts. Translate the phrases only for Siri languages.
- Each new language goes into `knownRegions` of the Xcode project.

### 4.4 One source for Kotlin and Swift?

The watch and the iPhone share some terms ("Log Migraine", "Pain-free days", "Pain Intensity"). The watch does not show any text from the Kotlin core. A shared source is therefore not necessary now.

| Option | Verdict |
|---|---|
| **Two sources and one glossary, plus a test that compares the shared terms (recommended)** | Small. The test reads `strings.xml` and the `.xcstrings` JSON and compares a list of key pairs for each language. |
| A generator: one master file writes the Compose XML and the String Catalogs | Own tooling to maintain. Useful only if many strings become shared. |
| moko-resources: one source that also makes `.lproj` folders and Swift access through the framework | New dependency. It overlaps with Compose resources, which the fonts use. Not recommended. |

### 4.5 Dates, times and numbers

kotlinx-datetime puts locale-specific month and day names out of its scope ([README](https://github.com/Kotlin/kotlinx-datetime#readme)). The system formatters must do it.

- A `DateFormats` interface replaces `DateText`. It has the same functions: `monthDay`, `fullDate`, `weekdayMonthDay`, `monthYear`, `range`, `time`, `dayAndTime`. The domain gets no formatting code; the current English code moves into the JVM test implementation.
- The iOS implementation uses `NSDateFormatter.dateFormatFromTemplate` with templates such as `MMMd`, `yMMMd`, `EEEEMMMd`, `LLLLy` and `j:mm`. The template `j` gives 12-hour or 24-hour time from the region of the device. `NSDateIntervalFormatter` makes the ranges. Relative days ("Today", "Yesterday") come from `doesRelativeDateFormatting`.
- `NSNumberFormatter` makes decimals and percentages. `NSDateComponentsFormatter` makes "7 h 20 min".
- The XLSX keeps ISO dates (`yyyy-mm-dd hh:mm`) and the file name keeps `MigraTrack-Report-2026-10-24`. Both are neutral for every language.
- The UI and the report take the format region from the device, also when the language is English. This fixes D10 and can ship in 1.0 without any translation.
- **The first day of the week follows the region** (decision of October 17, 2026). It is Monday in Poland, Russia and Germany, and Sunday in the US. Now three places use Monday:
  - the month grid ([MonthCalendar.kt:27](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/analytics/MonthCalendar.kt#L27));
  - the week period of Insights ([Insights.kt:119](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/analytics/Insights.kt#L119) and `startOfWeek` at line 146);
  - the weekday letters ([InsightsCalendar.kt:74](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/insights/InsightsCalendar.kt#L74)).

  The domain gets the first day as a `DayOfWeek` parameter. The iOS layer reads it from `NSCalendar.currentCalendar.firstWeekday`. The domain tests run with Monday and with Sunday.

### 4.6 Plurals, word order and text length

The plural forms that each language needs for whole numbers, after the CLDR rules. Both resource formats take the rule from CLDR; the translation only fills in the forms.

| Forms | Languages |
|---|---|
| other | Chinese, Japanese, Korean, Thai, Vietnamese, Indonesian, Malay |
| one, other | English, German, Dutch, Danish, Norwegian, Swedish, Finnish, Hungarian, Greek, Bulgarian, Kazakh, Turkish, Urdu, the 10 Indian languages |
| one, other (and many for millions) | French, Spanish, Portuguese, Italian, Catalan |
| one, few, other | Croatian, Romanian |
| one, few, many, other | Polish, Russian, Belarusian, Ukrainian, Czech, Slovak, Lithuanian |
| one, two, other | Hebrew |
| one, two, few, other | Slovenian |
| zero, one, two, few, many, other | Arabic |

- The completeness test (section 5.2) takes the forms from `PLURAL_CATEGORIES` in `buildSrc/src/main/kotlin/l10n/Languages.kt`, which has the CLDR categories of this table. Compose resources keep their CLDR rules in internal code that a Gradle task cannot read. A new language needs a row there.
- Polish "few" is 2–4, 22–24 and so on, but not 12–14. Arabic uses "two" for 2 and "zero" for 0.
- Many languages change the form of a noun after a number or a preposition, and many put the verb at the end. A placeholder for a noun ("$noun this Week") does not work. Each sentence is one complete string with numbered placeholders.
- German, Finnish and Hungarian text is often 30–40% longer than English. CJK text is shorter but taller. The tight places are:
  - one-line controls: the page title ([Chrome.kt:115](../composeApp/src/commonMain/kotlin/com/migratrack/app/ui/components/Chrome.kt#L115)), the main buttons and pills ([Controls.kt:76](../composeApp/src/commonMain/kotlin/com/migratrack/app/ui/components/Controls.kt#L76), lines 110 and 127), the medication card ([Selection.kt:218](../composeApp/src/commonMain/kotlin/com/migratrack/app/ui/components/Selection.kt#L218));
  - the tab bar and the header, which do not scale with Dynamic Type;
  - the W / M / 6M / Y switch, the calendar weekday letters, "STEP 1/3";
  - the watch: "Save Episode", "Log Migraine" and "Pain-free days" on the 40 mm screen, and "LOG" in the circular complication.
- Strings with a hard limit get a maximum length in the comment for the translation. The completeness test checks it.
- **Tall scripts.** Thai, the Indian scripts, Arabic and Urdu need more line height than Latin text, because their marks go above and below the line. The type scale has fixed line heights, for example 15/20 sp for `body` ([Theme.kt](../composeApp/src/commonMain/kotlin/com/migratrack/app/ui/theme/Theme.kt)). With the system font, the theme uses the line height of the font (`TextUnit.Unspecified`). Fixed-height chips and bars must grow with the text.

### 4.7 Fonts

**Recommendation: each language whose script Plus Jakarta Sans does not have uses the system font. The 23 Latin-script languages keep Plus Jakarta Sans.** The apps of Apple use the system font for every script, and the app gets it without a font file or a license. On iOS the system font is SF Pro for Latin, Cyrillic and Greek, and SF Arabic and SF Hebrew for those scripts. For the other scripts iOS adds its own fonts, for example PingFang for Chinese, Hiragino for Japanese, Apple SD Gothic Neo for Korean and Kohinoor for Devanagari.

**The glyph check.** Core Text checked the letters of each language in the font files on October 17, 2026. SF Pro and SF Compact are the copies in `/System/Library/Fonts` of this Mac.

| Script | Languages | Plus Jakarta Sans | SF Pro, SF Compact |
|---|---|---|---|
| Latin with diacritics | All 23 Latin-script targets, also Vietnamese, Romanian (ș ț), Hungarian (ő ű), Catalan (ŀ) | All letters present | All letters present |
| Cyrillic | Russian, Belarusian (ў, і), Ukrainian, Bulgarian, Kazakh | No letters | All letters present |
| Greek | Greek | No letters | All letters present |
| Arabic, Hebrew | Arabic, Hebrew (Urdu: see the next row) | No letters | SF Arabic and SF Hebrew, members of the SF family (HIG) |
| Urdu (Nastaliq), Indian scripts, Thai, Chinese, Japanese, Korean | Urdu, the 10 Indian languages, Thai, CJK | No letters | Not in SF; iOS draws them with its own system fonts for these scripts |

Plus Jakarta Sans does not get Cyrillic later. Google Fonts gives it a "cyrillic-ext" subset, but that subset covers U+0460–052F (historic and minority letters), not the Russian alphabet (U+0410–044F). The last release of the font is 2.7.1 of May 26, 2023.

**What Apple uses and recommends.**

- The apps of Apple use the system font in every language: SF Pro on iPhone and SF Compact on the watch. A Russian interface uses the same SF Pro as an English one. Apple does not change the font for a script.
- SF Pro "supports over 150 languages across Latin, Greek, and Cyrillic scripts" ([Apple Fonts](https://developer.apple.com/fonts/)). The serif New York also has Cyrillic; Apple uses it in reading apps.
- The Human Interface Guidelines ([Typography](https://developer.apple.com/design/human-interface-guidelines/typography), changed December 16, 2025) say:
  - SF Pro is the system font of iOS. System fonts have the best legibility and support Dynamic Type.
  - Keep the number of typefaces small. Too many typefaces make the hierarchy unclear.
  - Do not embed the system fonts in the app; get them through the system API. The license of the SF fonts allows only mock-ups, not embedding.
  - A custom font must be legible and must support the same accessibility behavior as the system font.
- Apple has no separate recommendation for Cyrillic. Its recommendation for every script is the system font.

**How Compose gets SF Pro.** On iOS, `FontFamily.Default` and `FontFamily.SansSerif` map to the alias `.AppleSystemUIFont`. The source calls this alias "the only legal way to get default SF and NY fonts" ([PlatformFont.nonAndroid.kt](https://github.com/JetBrains/compose-multiplatform-core/blob/jb-main/compose/ui/ui-skiko/src/nonAndroidMain/kotlin/androidx/compose/ui/text/platform/PlatformFont.nonAndroid.kt), branch `jb-main`). The app then uses the font of the system and embeds nothing.

**License.** The app may use SF Pro in this way:

- The license on [Apple Fonts](https://developer.apple.com/fonts/) applies to the font files that a developer downloads there. It allows mock-ups only and forbids embedding these files in an app. The app does not bundle an SF file.
- The SF fonts on the device are part of iOS. The [iOS 26 license](https://www.apple.com/legal/sla/docs/iOS26_iPadOS26.pdf) names fonts as part of the Apple Software. Apps draw text with them through the system API, and the HIG tells apps to do this and not to embed the fonts.
- `.AppleSystemUIFont` is the family name that the system font API itself reports. On macOS 26, Core Text resolves it to SF without a warning; a private name such as `.SFUI-Regular` gets Times New Roman and a warning. On the iOS simulator, `FontFamily.Default` draws in SF, and the other scripts come from the system fallback fonts (stage 1g).
- The PDF already uses `UIFont.systemFontOfSize`, and the PDF context embeds subsets of the glyphs. The SF font files allow this: their OS/2 `fsType` is 0 (installable embedding).
- Not allowed: an SF file in `composeResources/font`, and SF on the website.

**Options.**

| Option | For | Against | Verdict |
|---|---|---|---|
| 1. No change: Skia draws missing letters in a fallback font | No work | The letters come from one font, the digits, the punctuation and the Latin brand name from another. This is the opposite of "keep the number of typefaces small". | No |
| **2. The system font for the languages without Latin script** | Same as the apps of Apple. No font file, no license. Good hinting at small sizes. The Latin-script languages keep the brand font. | Latin and non-Latin screens look a little different. The letter spacing of the type scale (for example −0.02 em in `hero`) is tuned for Plus Jakarta Sans. | **Yes** |
| 3. One new font family for all languages (for example the Noto family, under the SIL Open Font License) | One look in every language | A new design for the whole app; many large font files for Arabic, Indic and CJK scripts | No |
| 4. SF Pro for all languages | Fully native; one look | The brand font goes; a new design for the whole app | Only as a design decision, not for localization |

**How to build option 2.** A resource selects the font, so the font and the language always agree, also with the per-app language of iOS:

```xml
<!-- values/strings.xml -->
<string name="font_family">brand</string>
<!-- values-ru, values-el, values-ar, values-hi, values-ja and every other non-Latin language -->
<string name="font_family">system</string>
```

`MigraTrackTheme` reads `font_family` and uses `FontFamily.Default` for `system`. With `system`, the theme also drops the letter spacing and uses the line height of the font (section 4.6). The letter spacing goes completely, also the positive spacing of the captions, because spacing splits the joined letters of Arabic. The completeness test (section 5.2) checks that this key has the correct value for the script of each language.

Stage 1g checked this on the iOS 27 simulator with the pseudo-locales `ru-XC` and `th-XT`. With `system`, the Latin and Cyrillic letters and the digits come from SF, and the Thai letters from the Thai system font. The tabular digits (`tnum`) stay. The lines get taller. The chips, pills and buttons have a minimum height in place of a fixed height (`heightIn`), so a taller line makes them grow.

The PDF ([UIKitPdfRenderer.kt:37](../shared/export/src/iosMain/kotlin/com/migratrack/export/pdf/UIKitPdfRenderer.kt#L37)), the watch app and the complications use the system font already. They need no change.

### 4.8 Right-to-left languages

Arabic, Hebrew and Urdu are targets, so the app needs a right-to-left (RTL) mode. Compose Multiplatform mirrors layouts automatically for an RTL locale: paddings, `Row` order, `start` and `end` alignment, and horizontal scrolling ([RTL page](https://kotlinlang.org/docs/multiplatform/compose-rtl.html), updated February 20, 2026). JetBrains lists open caret and `TextDirection.Content` issues. SwiftUI mirrors the watch app and the complications.

The Apple HIG ([Right to left](https://developer.apple.com/design/human-interface-guidelines/right-to-left)) gives the rules. For this app they mean:

| Element | RTL behavior | Work |
|---|---|---|
| Back chevron, "Previous Month" and "Next Month" | Flip | The custom `ImageVector` icons in [MaterialSymbols.kt](../composeApp/src/commonMain/kotlin/com/migratrack/app/ui/icons/MaterialSymbols.kt) get `autoMirror = true` for direction icons only |
| 30-day strip on Summary and the welcome screen, chart bars, month calendar | Time runs from right to left, so the order flips | Check that each one uses a mirrored `Row` and not fixed x coordinates; `Canvas` drawings mirror by hand |
| Pain scale 1–10 and the slider | Flip, and the numbers reverse their order; each number keeps its digits | Check the drag direction too |
| Digits in a number ("25", "6.5") | Never flip | `NSNumberFormatter` (section 4.5) |
| Brand mark, app icon, checkmark | Never flip | Keep them in an `Ltr` scope if a parent mirrors them |
| Calendar swipe between months | Swipe direction follows the reading direction | Check the gesture |
| Paragraphs of 3 or more lines | Align by the language of the text | The legal texts and the user's own trigger names |
| Upper-case Latin captions next to Arabic or Hebrew | Arabic and Hebrew have no upper case and look small | The HIG suggests about 2 points more for the RTL text |
| PDF report | The layout in [ReportPdf.kt](../shared/export/src/commonMain/kotlin/com/migratrack/export/pdf/ReportPdf.kt) uses absolute x coordinates | A mirror of the x axis in the layout, right-aligned text, and the table columns from right to left |
| XLSX report | Excel shows the sheet from left to right | `rightToLeft="1"` in the `sheetView` of each sheet |
| Watch and complications | SwiftUI mirrors them | Check the gauge and "Last attack" |

Compose takes the direction from UIKit, and UIKit takes it from the localizations of the bundle. Before an Arabic translation exists, a run with `-AppleLanguages "(ar)"` therefore stays left to right. The launch argument `-NSForceRightToLeftWritingDirection YES` mirrors the app with the English text; `scripts/l10n-screenshots.sh` adds it for Arabic, Hebrew, Urdu and Persian. The report takes the same direction as the app (`appReadsRightToLeft`).

### 4.9 Language choice and the default language

How iOS selects the language ([QA1828](https://developer.apple.com/library/archive/qa/qa1828/_index.html)):

- iOS compares the preferred languages of the user, in order, with the `.lproj` folders of the bundle. If none matches, it uses `CFBundleDevelopmentRegion` (here `en`).
- An app that keeps its translations outside `.lproj` folders must list its languages in `CFBundleLocalizations`.
- The App Store "Languages" field also comes from the `.lproj` folders.
- The per-app language setting (Settings › MigraTrack › Language) exists since iOS 13. It shows only when the app has more than one language and the device has more than one preferred language ([forum thread 123413](https://developer.apple.com/forums/thread/123413)).

The Compose resources are not in `.lproj` folders. Without a String Catalog, iOS thinks that the app is English only, and the system dialogs stay English. Each language therefore needs `InfoPlist.xcstrings` (or `CFBundleLocalizations`) in the same pull request as its Compose translation. A test checks that both lists agree.

**Decision (October 17, 2026): the app uses the language of the iPhone and has no language picker.**

- **Default:** the first language of the device that the app has. English is the fallback.
- The per-app language of iOS (Settings › MigraTrack › Language) comes with the `.lproj` folders automatically. It is part of iOS, and it needs no code: iOS writes the choice into `NSLocale.preferredLanguages`, and Compose resources read it.
- An in-app picker through `AppleLanguages` is possible later. It works only after a restart of the app, and it does not change the watch.
- The watch app and the complications follow the language of the watch. It is set in the Watch app on the iPhone, separately from the per-app language on the iPhone. Stage 2 checks this on a real pair.
- The report uses the language of the app (decision of October 17, 2026).

### 4.10 Possible dependencies (proposals only, nothing added)

| Proposal | What it gives | Recommendation |
|---|---|---|
| A translation service: Crowdin, Lokalise, Weblate, POEditor | Shared translation memory, glossary, review states, reviewers without Xcode; reads Android XML, XLIFF and `.xcstrings` | Not now. Claude translates in the files in Git, and the tests of section 5.2 do the checks. A service becomes useful if outside reviewers join. No code dependency. |
| moko-resources | One source for Kotlin and Swift, `.lproj` generation | No (section 4.4) |
| Lyricist | Strings as Kotlin code, type-safe | No. Compose resources do the same and are already present. |
| A screenshot-test library (for example Roborazzi or Paparazzi) | Automatic screenshots in each language | No for iOS-only Compose today; the simulator script in section 5.3 is enough. |

## 5. Quality and checks

### 5.1 Pseudolocalization and long text

- **Swift:** Xcode schemes have pseudolanguages under Run › Options › App Language: double length, accented, bounded string, right to left. They change only the text from String Catalogs.
- **Compose:** Compose resources have no pseudo-locale. The Gradle task `pseudoLocalize` writes `values-en-rXA/strings.xml` from `values/strings.xml`: accented letters, 40% longer text, and brackets at both ends, for example `[Ļöĝ Ṁîĝŕåîñé Éþîšöðé ~~~~~~~~]`. It also writes `values-ru-rXC` (Cyrillic letters) and `values-th-rXT` (Thai letters with marks). The task runs only with `-Pmigratrack.pseudo=true` or the environment variable `MIGRATRACK_PSEUDO=true` (Xcode starts Gradle itself), so a normal or Release build never contains it. Run the app with `-AppleLanguages "(en-XA)"`.
- Three runs come before any translation exists. `-AppleLanguages "(ar)" -NSForceRightToLeftWritingDirection YES` shows the mirrored layout (section 4.8). A pseudo-locale with Cyrillic letters and one with Thai marks show the system font and the line heights (sections 4.6 and 4.7).
- Check each screen at the default text size and at XXXL, which is the largest size that the app supports ([Theme.kt:76](../composeApp/src/commonMain/kotlin/com/migratrack/app/ui/theme/Theme.kt#L76)).
- Watch: check the 40 mm SE and the 49 mm Ultra, also at the largest accessibility size. The watch simulator takes the text size from a launch argument: `-UIPreferredContentSizeCategoryName UICTContentSizeCategoryAccessibilityXXXL`.

### 5.2 Completeness tests

One Gradle task `verifyTranslations`, part of `./gradlew check`. It lives in `buildSrc/src/main/kotlin/l10n` and uses only the JVM XML parser and the Groovy JSON parser of Gradle, so it needs no new dependency. `verifyTranslationsSelfTest` runs it on broken files in `buildSrc/src/fixtures/l10n` and expects exactly their problems. It fails when:

- a key of `values/` is missing in a language, or a language has a key that `values/` does not have;
- the placeholders differ (`%1$s` in English, `%1$d` or nothing in the translation);
- a plural lacks a form that the language needs after CLDR (section 4.6);
- `font_family` does not match the script of the language (section 4.7);
- a string in an `.xcstrings` file is not in the state "translated" for each language;
- a string is longer than the maximum in its comment;
- the languages of the Compose resources, of `InfoPlist.xcstrings`, of `knownRegions` in the Xcode project and of the legal files differ;
- a legal folder lacks a document, `docs/_config.yml` does not exclude it, or `legal_folder` does not name the folder of its language;
- a term pair from the glossary has different translations on the iPhone and on the watch (section 4.4).

A unit test also checks that each built-in trigger and medication ID has a name resource. The exhaustive `when` (section 4.2) already guards the enums.

### 5.3 Screenshots in each language

The screenshots in `docs/user-journey` and in the notes come from the simulator. `scripts/l10n-screenshots.sh` repeats them for each language. It launches a Debug build with `-AppleLanguages`, `-AppleLocale` and `-MigraTrackScreen <tab or Settings>`, which only Debug builds read, and saves Summary, Log, Insights, Export, Settings and, with `WATCH=<udid>`, the watch summary:

```bash
WATCH=<watch-udid> scripts/l10n-screenshots.sh <udid> build/l10n-screenshots pl:pl_PL en-XA
```

Use the sample data of the Debug build (Settings › Developer › Load Sample Data), so the charts have data.

[LOCALIZATION_CHECKLIST.md](LOCALIZATION_CHECKLIST.md) lists the files, the back-translation and the checks of each batch.

For 48 languages with English, 5 iPhone screens and 1 watch screen, the script makes about 290 images. They do not go into Git. Claude reads them for cut, overlapping or untranslated text before each batch merges, and the project owner looks at Russian, Belarusian and English. The App Store images go to App Store Connect.

### 5.4 App Store

- App Store Connect has 50 localizations, and all 50 are targets: the 47 languages of section 1.1 without Belarusian, Bulgarian, Kazakh and Lithuanian, plus English (U.K.), (Australia), (Canada) and French (Canada), Spanish (Spain) and Portuguese (Portugal) as their own localizations.
- Each localization has its own name, subtitle, description, keywords, promotional text, What's New and Privacy Policy URL. The Privacy Policy URL is the English page for each localization (section 4.2).
- A new language takes the screenshots of the primary language until you upload others ([Localize app information](https://developer.apple.com/help/app-store-connect/manage-app-information/localize-app-information)). Localized screenshots are optional, but they help on the store.
- The keywords field has a limit of 100 bytes. In UTF-8 a Cyrillic, Greek, Arabic or Hebrew letter takes 2 bytes, and a Chinese, Japanese, Korean, Thai or Indian character takes 3 bytes. Chinese keywords therefore get about 33 characters.
- Siri phrases go only into Siri languages (section 4.3).
- Keep English as the primary language.
- Put the metadata of each localization into one file for each language, for example `docs/app-store/pl.md`, with the character counts of the English section. `docs/APP_STORE.md` keeps the English text and links to them. `docs/_config.yml` excludes the folder from GitHub Pages.
- In China mainland, the MIIT requires an ICP filing number for some apps ([App information](https://developer.apple.com/help/app-store-connect/reference/app-information/)). A developer outside China usually needs a local partner for it. This is not a translation task, but it decides if the app and its Chinese (Simplified) text reach China mainland. Check it before the batch with Chinese.

## 6. Translation process

- **Who:** Claude translates every language (decision of October 17, 2026). The project owner reads Russian, Belarusian and the English source. No native speaker reads the other 45 languages before a release; section 7 gives the actions for this risk.
- **Format:** the files in Git. Compose XML for the iPhone, the String Catalogs for Swift, Markdown for the legal texts and the App Store metadata. One pull request for each batch (section 8).
- **Context:** each key has a comment: where the text shows, the maximum length, and the meaning of each placeholder. The translation uses this comment and the screenshots of section 5.3.
- **Glossary:** one file `docs/LOCALIZATION_GLOSSARY.md` with each term in each language: episode, attack, migraine day, pain-free day, trigger, possible trigger, aura, acute medication, preventive medication, treatment blueprint, baseline profile, intensity. Every key uses the glossary term. `docs/_config.yml` excludes the file from GitHub Pages.
- **Back-translation:** for each language, a second pass translates the medical strings, the medical notes and the legal texts back into English, without the English source. Each difference in meaning gets a fix before the merge. The pull request lists the differences that it found.
- **Style:** sentence case where the language uses it; the polite form of address where the language has one (for example "Sie" in German, "Вы" in Russian); impersonal forms where the polite form is long (Polish); no slang; the same short style as the English text.
- **New strings later:** each feature pull request that adds an English string also adds its 47 translations. The completeness test fails without them, so no screen falls back to English by accident.

### 6.1 Check of the medical terms

Claude checks each medical term against the medical sources of a reference country of the language (decision of October 17, 2026). The reference country is the App Store country with the most people among the countries that show this language. A regional set uses its own country: Spain for `es-rES`, Portugal for `pt-rPT`, Canada for `fr-rCA`. The check covers:

- diagnoses, symptoms (photophobia, phonophobia, scintillating scotoma, aphasia, paresthesia), cranial regions;
- drug names, drug classes and dose units of the built-in catalog;
- the medical notes: "MigraTrack is a diary, not medical advice", "This does not show a cause", the PDF disclaimer.

The sources, in this order:

1. The official translation of the headache classification ICHD-3 for that language, and the national edition of ICD-10 or ICD-11.
2. The current national clinical guideline for migraine.
3. The national register of medicinal products and the product information (SmPC). They give the local name of the active substance, the drug class and the unit ("mg", "мг").
4. Articles in national peer-reviewed journals, for terms that 1–3 do not contain.

Rules:

- The glossary gives the source of each term: link, section or page, and the date of the check. A clinician can check every term later without a new search.
- The app speaks to patients. Some guidelines have a clinical term and a common term for one thing. The app then uses both, as the English text does: "Photophobia (Light)".
- Medication names use the local name of the active substance, not a brand. Acetaminophen is "paracetamol" in Polish and "парацетамол" in Russian.
- A built-in medication without a registration in the reference country stays in the catalog of that language (decision of October 17, 2026). It gets the international name (INN) in the script of the language, and the glossary notes that it has no registration there.

Sources found for the pilot languages and Polish (checked on October 17, 2026):

| Language | Classification | Guideline | Drug register |
|---|---|---|---|
| Polish | No official Polish ICHD-3 translation found; the Polish ICD-10 gives the diagnosis names | Stępień A. et al., migraine treatment guideline of the Polish Headache Society, the Polish Neurological Society and the Polish Pain Society, [Neurol Neurochir Pol 2021;55(1):33–51](https://journals.viamedica.pl/neurologia_neurochirurgia_polska/article/view/71354), with the 2023 expert update | [Rejestr Produktów Leczniczych](https://rejestrymedyczne.ezdrowie.gov.pl/rpl/search/public) |
| Russian | [ICHD-3 in Russian](https://ihs-headache.org/wp-content/uploads/2022/09/ICHD-3-Russian.pdf), adaptation of the Russian Society for the Study of Headache, on the IHS website | Clinical guideline "Migraine", Ministry of Health of the Russian Federation, [ID 295_4, 2024](https://cr.minzdrav.gov.ru/view-cr/295_4) | [State Register of Medicinal Products (GRLS)](https://grls.minzdrav.gov.ru/) |
| German | [ICHD-3 in German](https://ihs-headache.org/wp-content/uploads/2020/05/3385_ichd-3-german.pdf) | S1 guideline of DGN and DMKG, [AWMF 030-057, 2025](https://register.awmf.org/assets/guidelines/030-057l_S1_Therapie-der-Migraeneattacke-Prophylaxe-der-Migraene_2025-11.pdf) | AMIce (sign-in, not searched); EMA product information |
| French | [ICHD-3 in French](https://ihs-headache.org/wp-content/uploads/2020/05/3628_ichd-3-french.pdf) | SFEMC recommendations, Rev Neurol 2012 | [Base de données publique des médicaments](https://base-donnees-publique.medicaments.gouv.fr/) |
| French (Canada) | French ICHD-3 | INESSS, "Traitement de la migraine chez l'adulte", 2025 | Health Canada [Drug Product Database](https://www.canada.ca/en/health-canada/services/drugs-health-products/drug-products/drug-product-database.html) |
| Spanish (Mexico) | [ICHD-3 in Spanish](https://ihs-headache.org/wp-content/uploads/2020/05/3559_ichd-3-spanish.pdf) | IMSS guideline IMSS-047-08 | COFEPRIS (did not load, not searched) |
| Spanish (Spain), Catalan | Spanish ICHD-3; Canal Salut for Catalan | SEN, Manual de Práctica Clínica en Cefaleas 2020 | [CIMA](https://cima.aemps.es/) |
| Portuguese (Brazil) | [ICHD-3 in Brazilian Portuguese](https://ihs-headache.org/wp-content/uploads/2021/03/ICHD-3-Brazilian-Portuguese.pdf); CID-10 of DATASUS | — | [ANVISA open data](https://dados.anvisa.gov.br/) |
| Portuguese (Portugal) | [ICHD-3 in Portuguese](https://ihs-headache.org/wp-content/uploads/2020/05/3391_ichd-3-portuguese.pdf) | Sociedade Portuguesa de Cefaleias, Recomendações 2021 | INFARMED Prontuário Terapêutico (Infomed answered 503) |
| Italian | [ICHD-3 in Italian](https://ihs-headache.org/wp-content/uploads/2021/11/ICHD-3-Italian.pdf) | No Italian-language guideline found | [AIFA open data](https://www.aifa.gov.it/liste-dei-farmaci) |
| Dutch | No Dutch ICHD-3 found | [NHG-Standaard Hoofdpijn (M19)](https://richtlijnen.nhg.org/standaarden/hoofdpijn) | [CBG Geneesmiddeleninformatiebank](https://www.geneesmiddeleninformatiebank.nl/) |
| Belarusian | No Belarusian ICHD-3 translation found (pilot, October 18, 2026). Medical documents in Belarus are mostly in Russian, so the Russian ICHD-3 gives the meaning, and the project owner checks the Belarusian term. | Clinical protocol "Диагностика и лечение пациентов с заболеваниями нервной системы (взрослое население)", resolution No. 8 of January 18, 2018, as amended in 2026, section G43 ([standards](https://minzdrav.gov.by/ru/dlya-spetsialistov/standarty-obsledovaniya-i-lecheniya/), Neurology). It is in Russian. | [State register of medicinal products, RCETH](https://www.rceth.by/Refbank/) |
| Polish (batch 4) | No Polish ICHD-3 found; Medycyna Praktyczna patient text and the EMA product information in Polish | The guideline above is in English; it gives no Polish terms | Rejestr Produktów Leczniczych: not searched in batch 4; a sumatriptan SmPC in Polish |
| Czech | [ICHD-3 in Czech](https://ihs-headache.org/wp-content/uploads/2020/05/3922_ichd-3-czech.pdf), basic and pocket versions | No Czech guideline found | SÚKL: not searched |
| Slovak | No Slovak ICHD-3 found | No national guideline found; reviews in Neurológia pre prax and Solen | ŠÚKL: not searched |
| Croatian | No Croatian ICHD-3 found | Acta Medica Croatica recommendations not found as free text; theses behind a CAPTCHA, not read | HALMED: the Imigran SmPC; database not searched |
| Slovenian | No Slovenian ICHD-3 found | No current guideline found; a Ljubljana teaching text | CBZ: not searched |
| Romanian | [ICHD-3 in Romanian](https://ihs-headache.org/wp-content/uploads/2022/08/ICHD-3-Romanian-rv.pdf) | No Romanian guideline found | ANMDMR: not searched |
| Hungarian | No Hungarian ICHD-3 found | The 2010 protocol answered "not found"; a Semmelweis University lecture | OGYÉI: not searched |
| Lithuanian | No Lithuanian ICHD-3 found | No national guideline found; Neurologijos seminarai 2023 review | VVKT: not searched |
| Danish | No Danish ICHD-3 found | sundhed.dk Lægehåndbogen, "Migræne uden aura" and "Migræne med aura", August 2025 | Lægemiddelstyrelsen: not searched |
| Norwegian | No Norwegian ICHD-3 found | Norsk Elektronisk Legehåndbok, "Migrene", February 2024 (public summary only) | DMP: not searched |
| Swedish | No Swedish ICHD-3 found | NPO Nervsystemets sjukdomar, "Riktlinje för migrän", October 2022 | Läkemedelsverket: not searched |
| Finnish | No Finnish ICHD-3 found | [Käypä hoito "Migreeni"](https://www.kaypahoito.fi/hoi36050), March 2024 | Fimea: not searched |
| Turkish | [ICHD-3 in Turkish](https://ihs-headache.org/wp-content/uploads/2021/12/Bas%CC%A7ag%CC%86risi-Bozukluklarinin-Uluslararasi-Siniflamasi.pdf) | Ministry of Health, "Migren Klinik Protokolü", revision of August 2025 | TİTCK: not searched |
| Ukrainian | No Ukrainian ICHD-3 found | Ministry of Health order No. 487 of August 17, 2007, migraine protocol; the register of current guidelines (guidelines.moz.gov.ua) asked for a Cloudflare check and was not read | [State Register of Medicinal Products](http://www.drlz.com.ua/), full CSV export searched |
| Bulgarian | [ICHD-3 in Bulgarian](https://ihs-headache.org/wp-content/uploads/2020/05/3675_ichd-3-bulgarian.pdf) | No Bulgarian guideline found; EMA product information in Bulgarian | Bulgarian Drug Agency: not searched |
| Kazakh | No Kazakh ICHD-3 found | Clinical protocol "Мигрень у взрослых", Ministry of Health of Kazakhstan, 2023, protocol No. 188 (in Russian; no Kazakh text found) | NDDA register: not searched (script page) |
| Greek | No Greek ICHD-3 found | No national guideline found; EMA product information in Greek, Eginition Hospital leaflets (2026), headaches.gr | EOF: not searched |
| Vietnamese | [ICHD-3 in Vietnamese](https://ihs-headache.org/wp-content/uploads/2025/04/ICHD-3-Vietnamese.pdf) (2025) | No guideline of the Ministry of Health found | DAV and the National Drug Formulary: not searched |
| Indonesian | No Indonesian ICHD-3 found | PERDOSSI, Acuan Panduan Praktik Klinis Neurologi, 2016, chapter "Migren" (partly scanned) | BPOM: not searched |
| Malay | No Malay ICHD-3 found | The Malaysian guidelines are in English; Cochrane plain language summaries in Malay | NPRA: not searched |
| Thai | No Thai ICHD-3 found | Neurological Society of Thailand, [migraine guideline 2022](https://neurothai.org/media/news_file/495-CPG-migraine-2565-20230104074752.pdf) (drug names in English) | Thai FDA: not searched |

The pilot could not search GRLS: its search asks for a CAPTCHA, which Claude does not solve. [LOCALIZATION_GLOSSARY.md](LOCALIZATION_GLOSSARY.md) marks these checks as open, with the page of the Russian guideline that names each medication.

For each other language, its batch (section 8) finds the same kinds of source first and adds them to this table. If a country has no official ICHD-3 translation, the national ICD edition and the national guideline give the terms.

## 7. Risks

| Risk | Effect | Action |
|---|---|---|
| No native speaker reads 45 of the 47 languages | Wrong or unnatural text stays in the app | Glossary from national sources, back-translation of medical and legal text, screenshot check by Claude (6, 5.3). In App Store Connect, a language can wait for a later version if its check is not complete. |
| Wrong medical term | A clinician reads a wrong symptom or diagnosis | Glossary with a source for each term (6.1) |
| No human clinician in the medical check | A wrong term is not found | A source link for each term in the glossary, so a clinician can check it later (6.1) |
| Machine quality is lower for some languages | Odia, Punjabi, Kannada, Malayalam, Marathi, Gujarati, Telugu, Tamil, Bangla, Urdu and Kazakh have less training text than the large languages | These languages come in the last batches, after the method has proved itself on Russian and Belarusian (8) |
| Mixed languages on one screen | A missing Compose key falls back to English without a warning | Completeness test (5.2) |
| iOS does not know the language | No per-app language setting, system dialogs in English, wrong App Store "Languages" | `InfoPlist.xcstrings` in the same pull request (4.9) |
| Missing letters | Empty boxes or mixed fonts | System font for non-Latin scripts; test of `font_family` (4.7) |
| Clipped tall scripts | Thai, Indian and Arabic marks cut off at the top or the bottom | Line height of the font with the system font; screenshots (4.6) |
| RTL bugs | Charts, the day strip or the PDF read in the wrong direction | RTL table of 4.8; Arabic run before the translation; Arabic, Hebrew and Urdu in their own batch |
| Legal text in two languages that differ | Unclear which text applies | The English text is the binding one, and each translation says so (4.2). A lawyer of the country can still check the translation before the release. |
| Old episodes with English names | Mixed names in history and reports | Names by ID (3) |
| Text too long on the watch or in one-line controls | Text cut off on 40 mm | Length limits in comments, pseudo-locale, screenshots on 40 mm |
| Siri phrases in a language without Siri | No effect for the user, but wasted work | Translate phrases only for Siri languages (4.3) |
| Cost of each new string | Each new English string needs 47 translations | Claude adds them in the same pull request; the test enforces it (6) |
| Distribution rules outside translation | The Chinese text does not reach China mainland without an ICP filing | Check before the East Asia batch (5.4) |
| Regressions in Stage 1 | The English UI changes by accident | Screenshot comparison before and after Stage 1; the existing tests |

## 8. Plan

Each stage is one pull request that can merge alone. Stage 1 changes nothing for the user, except the dates and times (1c). The days are working days of one developer together with Claude.

### 8.1 Stage 1: preparation, English only

| # | Stage | Contents | Size | Main risk |
|---|---|---|---|---|
| 0 | English source | Fix the terms of section 2.1 in the English text. Write the glossary in English. The project owner reads the English text. | 1 day | The later stages need it |
| 1a | Compose strings | `values/strings.xml` with about 300 keys; plurals; full sentences; dialogs and share text; comments with length limits | 2–3 days | Accidental UI changes |
| 1b | Domain without text | Remove `title`, `subtitle`, `description` and `drugClass` from the domain; mappings in `composeApp`; names by ID (section 3); catalog doses as amount and unit; sort by shown name | 1–2 days | Missed use of a title |
| 1c | Dates and numbers | `DateFormats`, `NSDateFormatter`, `NSNumberFormatter` for every number; first day of the week from the region; calendar weekday letters; closes D10 | 1.5–2 days | The 12-hour time in US English changes layouts. Can ship in 1.0. |
| 1d | Report | `ReportStrings` and `DateFormats` in `shared/export`; column IDs; JVM tests with the English implementation | 1 day | PDF layout with longer text |
| 1e | Swift | String Catalogs for the three targets, `InfoPlist.xcstrings`, `AppShortcuts.xcstrings`; data in place of text from `QuickLogBridge` | 0.5–1 day | Siri phrase validation |
| 1f | Tools | `verifyTranslations`, pseudo-locales, screenshot script, legal file rule, back-translation check list | 1–2 days | iOS ignores the pseudo-locale |
| 1g | Scripts and direction | `font_family` and the system font; line heights for tall scripts; RTL: icons, day strip, charts, pain scale, calendar swipe, PDF mirror, XLSX `rightToLeft`; locale-aware upper case | 3–4 days | RTL in the custom `Canvas` drawings and in the PDF |

Stage 1 takes about 11–16 days in total. Stages 1a–1g have few dependencies:

- 1a, 1b and 1e can run in parallel, because they touch different files.
- 1c and 1d need 1b, because the domain text moves.
- 1f needs 1a and 1e, because it checks their files.
- 1g needs 1a and 1f, because it uses the resources and the pseudo-locales.

### 8.2 Stages 2–9: translations in batches

A batch holds languages with the same script or direction, so one layout check covers the batch. Each batch contains the Compose strings, the String Catalogs, the legal texts, the glossary rows with their sources, the back-translation report, the App Store metadata and the screenshot check.

| # | Batch | Languages | Size | Why in this place |
|---|---|---|---|---|
| 2 | Pilot | Russian, Belarusian | 3–4 days | The project owner reads both. The batch tests the method, the glossary and the system font. |
| 3 | Western Europe | German, French, French (Canada), Spanish, Spanish (Spain), Portuguese, Portuguese (Portugal), Italian, Dutch, Catalan | 3–4 days | Large markets; long German words test the layouts |
| 4 | Central and Northern Europe | Polish, Czech, Slovak, Croatian, Slovenian, Romanian, Hungarian, Lithuanian, Danish, Norwegian, Swedish, Finnish, Turkish | 3–4 days | Latin script; the Turkish upper case |
| 5 | Cyrillic and Greek | Ukrainian, Bulgarian, Kazakh, Greek | 2 days | System font, checked in the pilot |
| 6 | Southeast Asia | Vietnamese, Indonesian, Malay, Thai | 2–3 days | Thai is the first tall script |
| 7 | East Asia | Chinese (Simplified), Chinese (Traditional), Japanese, Korean | 2–3 days | CJK line breaks; the ICP question (5.4) |
| 8 | Right to left | Arabic, Hebrew, Urdu | 3–4 days | Needs 1g; six Arabic plural forms; Eastern Arabic digits |
| 9 | India | Hindi, Marathi, Bangla, Gujarati, Punjabi, Odia, Kannada, Malayalam, Tamil, Telugu | 3–4 days | Tall scripts; lowest machine quality, so it comes last |

The batches take about 21–28 days in total. Batches 3–9 can come in any order after the pilot, with one rule: batch 8 needs stage 1g. A release can ship with the batches that are complete; the other languages wait for a later version.

## 9. Decisions for the project owner

All questions of the first version of this plan have an answer (section 1). New questions can come from the pilot batch:

- if the system font looks too different from Plus Jakarta Sans on Cyrillic screens;
- if a language of batches 3–9 must wait for a native reader before its release.

## 10. Sources

Read on October 17, 2026. The dates are the update dates on the pages.

- JetBrains, [Localizing strings](https://kotlinlang.org/docs/multiplatform/compose-localize-strings.html), September 2, 2026.
- JetBrains, [Strings, plurals and string arrays](https://kotlinlang.org/docs/multiplatform/compose-multiplatform-resources-usage.html), October 10, 2026.
- JetBrains, [Manage local resource environment](https://kotlinlang.org/docs/multiplatform/compose-resource-environment.html) (`LocalAppLocale`, `AppleLanguages`), October 2, 2026.
- JetBrains, [Setup and configuration for multiplatform resources](https://kotlinlang.org/docs/multiplatform/compose-multiplatform-resources-setup.html) (no qualifiers for `files/`, `customDirectory`), September 2, 2026.
- JetBrains, [Right-to-left languages](https://kotlinlang.org/docs/multiplatform/compose-rtl.html), February 20, 2026.
- JetBrains, [ResourceEnvironment.ios.kt](https://github.com/JetBrains/compose-multiplatform/blob/master/components/resources/library/src/iosMain/kotlin/org/jetbrains/compose/resources/ResourceEnvironment.ios.kt) and [ResourceEnvironment.kt](https://github.com/JetBrains/compose-multiplatform/blob/master/components/resources/library/src/commonMain/kotlin/org/jetbrains/compose/resources/ResourceEnvironment.kt), branch `master`.
- Kotlin, [kotlinx-datetime README](https://github.com/Kotlin/kotlinx-datetime#readme): locale-specific names are out of scope.
- Apple, [Localizing and varying text with a string catalog](https://developer.apple.com/documentation/xcode/localizing-and-varying-text-with-a-string-catalog), no date on the page.
- Apple, [Testing localizations when running your app](https://developer.apple.com/documentation/xcode/testing-localizations-when-running-your-app), no date on the page.
- Apple, [Localizing strings that contain plurals](https://developer.apple.com/documentation/xcode/localizing-strings-that-contain-plurals), no date on the page.
- Apple, [CFBundleLocalizations](https://developer.apple.com/documentation/bundleresources/information-property-list/cfbundlelocalizations), no date on the page.
- Apple, [QA1828: How iOS determines the language for your app](https://developer.apple.com/library/archive/qa/qa1828/_index.html), March 23, 2016.
- Apple, [Localize app information](https://developer.apple.com/help/app-store-connect/manage-app-information/localize-app-information) (App Store Connect), 2026.
- Apple, [iOS feature availability](https://www.apple.com/ios/feature-availability/), read on October 17, 2026.
- Apple Developer Forums: [per-app language not shown (123413)](https://developer.apple.com/forums/thread/123413), [AppShortcuts.xcstrings and phrase variants (797784)](https://developer.apple.com/forums/thread/797784), [localizing `AppShortcutPhrase` (803490)](https://developer.apple.com/forums/thread/803490).
- Apple, [Typography](https://developer.apple.com/design/human-interface-guidelines/typography), Human Interface Guidelines, changed December 16, 2025.
- Apple, [Fonts](https://developer.apple.com/fonts/) (SF Pro, SF Compact, New York; font licenses), no date on the page.
- JetBrains, [PlatformFont.nonAndroid.kt](https://github.com/JetBrains/compose-multiplatform-core/blob/jb-main/compose/ui/ui-skiko/src/nonAndroidMain/kotlin/androidx/compose/ui/text/platform/PlatformFont.nonAndroid.kt) (`.AppleSystemUIFont`), branch `jb-main`.
- Google Fonts, [Plus Jakarta Sans](https://fonts.google.com/specimen/Plus+Jakarta+Sans) subsets; Tokotype, [Plus Jakarta Sans releases](https://github.com/tokotype/PlusJakartaSans/releases) (2.7.1, May 26, 2023).
- International Headache Society, [ICHD translations](https://ihs-headache.org/en/resources/ichd/) and the [Russian ICHD-3](https://ihs-headache.org/wp-content/uploads/2022/09/ICHD-3-Russian.pdf).
- Stępień A. et al., [migraine treatment guideline](https://journals.viamedica.pl/neurologia_neurochirurgia_polska/article/view/71354), Neurol Neurochir Pol 2021;55(1):33–51.
- Ministry of Health of the Russian Federation, [clinical guideline "Migraine", ID 295_4](https://cr.minzdrav.gov.ru/view-cr/295_4), 2024.
- [Rejestr Produktów Leczniczych](https://rejestrymedyczne.ezdrowie.gov.pl/rpl/search/public) (Poland) and [GRLS](https://grls.minzdrav.gov.ru/) (Russia), drug registers.
- Apple, [App Store localizations](https://developer.apple.com/help/app-store-connect/reference/app-store-localizations) (50 localizations, 173 countries with default and additional languages), App Store Connect Help, 2026.
- Apple, [App information](https://developer.apple.com/help/app-store-connect/reference/app-information/) (ICP filing number for China mainland), App Store Connect Help, 2026.
- Apple, [Right to left](https://developer.apple.com/design/human-interface-guidelines/right-to-left), Human Interface Guidelines, no date on the page.
- iOS 27.1 simulator runtime (24A94425): the `.lproj` folders of `Preferences.app`, read on October 17, 2026, as the list of iOS system languages.
- Apple, WWDC19 session 403, [Creating Great Localized Experiences with Xcode 11](https://developer.apple.com/videos/play/wwdc2019/403/) (per-app language, iOS 13).
