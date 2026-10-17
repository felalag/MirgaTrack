# App Store submission

This file holds the App Store Connect metadata, the privacy and age rating answers, the notes for App Review, and the screenshot plan for MigraTrack 1.0.
The requirements were checked against the Apple sources in [Sources](#sources) on October 14, 2026.

## Before you submit

- [x] Put the contact email address in `docs/support.md` and in the legal texts in `docs/legal/files/`.
- [x] Turn on GitHub Pages: Settings › Pages › Deploy from a branch › `main` › `/docs`. Pages from a private repository needs GitHub Pro, or make the repository public.
- [ ] Open the three URLs below in a browser and make sure that they load.
- [ ] Set `TEAM_ID` and `APP_BUNDLE_ID` in `iosApp/Configuration/Config.xcconfig`, archive, and open **Product › Archive › Generate Privacy Report** to see the three privacy manifests.
- [ ] Complete the declaration in App Information › App Store Regulations & Permits › Declare Regulated Medical Device. The answer is **No**.

## URLs

| Field | URL |
|---|---|
| Privacy Policy URL (required) | https://felalag.github.io/MirgaTrack/privacy/ |
| Support URL (required) | https://felalag.github.io/MirgaTrack/support/ |
| Marketing URL (optional) | https://felalag.github.io/MirgaTrack/ |

The app and the website show the same legal text. The Markdown files in `docs/legal/files/` are the only copy: the app bundles them as Compose resources, and GitHub Pages publishes them.
The Support URL must lead to contact information, so the support page shows an email address.

## App information

| Field | Value | Limit |
|---|---|---|
| Name | MigraTrack | 30 characters |
| Subtitle | Migraine Diary & Reports | 30 characters |
| Primary category | Health & Fitness | |
| Secondary category | Medical | |
| Price | Free | |
| Copyright | 2026 Ira Babayed | |

## Version information

### Promotional text (170 characters)

Log migraine attacks on iPhone or Apple Watch, see your patterns, and share a report with your doctor.

### Description (4000 characters)

```text
MigraTrack is a diary for your migraine attacks. Log an attack in seconds, see your patterns over time, and take a clear report to your next appointment.

LOG AN ATTACK
- Record the start time, the pain intensity from 1 to 10, the aura, the symptoms, the triggers, and the medications.
- Your typical intensity and your usual acute medications fill in each new log, so one tap is often enough.
- Log an attack on a past day from the calendar, or edit an episode later.

SEE YOUR PATTERNS
- The Summary shows your pain-free days and your last attacks.
- Insights shows attacks per week, month, six months, and year, with a month calendar.

APPLE HEALTH
- With your permission, MigraTrack reads your sleep from the Health app and shows last night's sleep on the Summary.
- MigraTrack does not write to the Health app.

APPLE WATCH
- See your pain-free days on your wrist.
- Turn the Digital Crown to set the intensity and log an attack. The episode goes to your iPhone.
- Add the Log Migraine complication to your watch face, or use the control in Control Center or the Smart Stack.

SIRI AND SHORTCUTS
- Say "Log a migraine in MigraTrack" to log an attack with your typical intensity.

REPORTS FOR YOUR DOCTOR
- Export a PDF report or an Excel file for the period that you choose.
- Select what the report includes: medications, aura and warning symptoms, and pain charts.

PRIVATE BY DESIGN
- No account, no server, no analytics, no advertising.
- Your data stays on your iPhone and your Apple Watch.
- Settings › Delete All Data deletes everything at any time.

MigraTrack is not a medical device and does not give medical advice. Speak with your doctor before you change your treatment.
```

### Keywords (100 bytes)

```text
headache,tracker,log,aura,pain,trigger,sleep,medication,attack,journal,calendar,doctor,pdf,watch
```

96 bytes. The name and the subtitle are searchable already, so the list does not repeat "migraine", "diary", or "reports".

### What's New

Not shown for the first version.

## App privacy

Answer **No, we do not collect data from this app**. App Store Connect then shows **Data Not Collected**.

Apple defines "collect" as sending data off the device so that the developer or a partner can access it for longer than the request takes. Data that the app processes only on the device is not collected. MigraTrack has no server, no analytics, and no advertising, and these facts make the answer correct:

| Data | Where it goes | Collected? |
|---|---|---|
| Episodes, baseline profile, name | The app database on the iPhone | No |
| Sleep from HealthKit | Read on the iPhone, never sent | No |
| Episodes between iPhone and Apple Watch | WatchConnectivity, directly between the user's own devices | No |
| PDF and Excel reports | Leave the device only when the user shares them | No |

The privacy manifests agree: `NSPrivacyTracking` is false and `NSPrivacyCollectedDataTypes` is empty in all three targets.

## Age rating

The expected result is **4+**.

| Section | Question | Answer |
|---|---|---|
| In-App Controls | Parental Controls | No |
| In-App Controls | Age Assurance | No |
| Capabilities | Unrestricted Web Access | No |
| Capabilities | User-Generated Content | No |
| Capabilities | Social Media | No |
| Capabilities | Messaging and Chat | No |
| Capabilities | Advertising | No |
| Mature Themes | Profanity or Crude Humor | None |
| Mature Themes | Horror/Fear Themes | None |
| Mature Themes | Alcohol, Tobacco, or Drug Use or References | None |
| Medical or Wellness | Medical or Treatment Information | None (see the note below) |
| Medical or Wellness | Health or Wellness Topics | No |
| Sexuality or Nudity | All questions | None |
| Violence | All questions | None |
| Chance-Based Activities | Gambling, Simulated Gambling, Contests, Loot Boxes | None / No |

**Medical or Treatment Information** means content that gives diagnoses or guidance for the management of medical conditions, for example medication guidance or treatment information. MigraTrack records what the user enters. It gives no diagnosis, no dosage advice, and no treatment advice, so the answer is **None**. Onboarding offers a list of common medications with a dose, such as "Propranolol 40mg", so that the user can select what they already take. If you think that this list is medication information, answer **Infrequent**, and the rating becomes 13+. **Infrequent** gives 13+, and **Frequent** gives 16+ and makes the medical device declaration necessary.

**Health or Wellness Topics** means self-care or lifestyle recommendations, for example calorie tracking or exercise advice. MigraTrack makes no recommendations, so the answer is **No**. If a later version adds tips, the answer becomes **Yes** and the rating becomes 9+.

## Regulated medical device

Apps in the EU/EEA, UK, or US with the Health & Fitness or Medical category must declare whether they are a regulated medical device. MigraTrack has no clearance and no CE or UKCA mark, and the Terms of Service say that it is not a medical device. Answer **No**.

## App Review information

| Field | Value |
|---|---|
| Sign-in required | No. The app has no accounts. |
| Contact | Your name, email, and phone number in international format (+country code) |
| Notes | The text below. The limit is 4000 bytes. |

```text
MigraTrack is a migraine diary. It has no account, no server, no analytics, and no advertising. All data stays on the device: a local SQLite database on the iPhone and the App Group container on the Apple Watch. The iPhone and the watch exchange episodes directly through WatchConnectivity.

ONBOARDING (iPhone)
1. Tap Get Started. The Terms of Service and the Privacy Policy links are on this screen.
2. Step 1: select a diagnosis and set the typical pain intensity. Tap Continue to Triggers.
3. Step 2: select some triggers. Tap Continue to Medications.
4. Step 3: select an acute medication. Tap Finish Setup.

LOG AN EPISODE
On the Summary, tap Log Migraine Episode (or open the Log tab), change any field, and tap Save Episode. The Summary and Insights update at once. In Insights, tap the month button (for example "Oct 2026") to open the calendar, then tap a day to edit its episode.

HEALTHKIT
On the Summary, tap Connect Apple Health and allow Sleep. The app only reads sleep analysis. It writes nothing to HealthKit. The sleep card shows last night's sleep. In the simulator, add a sleep sample in the Health app first.

APPLE WATCH
Install the app on a paired watch. Open the iPhone app once so that it sends the logging preset. On the watch, tap Log Migraine, turn the Digital Crown to set the intensity, and tap Save Episode. The episode shows on the iPhone.

COMPLICATION AND CONTROL
Touch and hold the watch face, tap Edit, and add MigraTrack > Log Migraine to a complication slot. A tap opens the watch app on the intensity screen. On watchOS 26 and later, the same Log Migraine control is available in Control Center and the Smart Stack.

APP SHORTCUT
Say "Log a migraine in MigraTrack" to Siri, or run Log Migraine from Spotlight or the Shortcuts app. The shortcut saves an episode with the typical intensity and confirms it in a dialog.

DELETE DATA
Settings > Delete All Data deletes all data on the iPhone.

The app is not a medical device and gives no medical advice. The Terms of Service in the app say so.
```

## Screenshots

Upload PNG or JPEG files without transparency, 1 to 10 for each size. Screenshots must show the app in use.

| Size class | Simulator | Pixels (portrait) | Status |
|---|---|---|---|
| iPhone with Dynamic Island, medium display | iPhone 18 Pro | 1206 × 2622 | Required |
| iPhone with Dynamic Island, large display | iPhone 18 Pro Max | 1320 × 2868 | Recommended: without it, App Store Connect requires the 6.5-inch Face ID set (1284 × 2778 or 1242 × 2688) |
| iPad 13-inch | — | — | Not needed: the app is for iPhone only |
| Apple Watch | Apple Watch Series 12 (46mm) | 416 × 496 | Required for a watch app; use this size in all localizations |
| iPhone Duo | — | — | Required from April 2027 for builds with the iOS 27.1 SDK or later |

The Apple Watch Series 12 (42mm) simulator makes 374 × 446 pixels, which App Store Connect does not accept. Use the 46mm watch, or the Ultra 4 at 422 × 514.

Use the same set of screens on each iPhone size. Load Settings › Developer › Load Sample Data in a Debug build first, so the charts have data.

| # | iPhone screen | What it shows |
|---|---|---|
| 1 | Summary | The Last 30 days card with the day strip and last night's sleep |
| 2 | Log Episode | Intensity, aura, symptoms, triggers, and medications |
| 3 | Insights, Month | The attack chart and the month calendar |
| 4 | Insights calendar day | An episode on a day, with Edit Episode |
| 5 | Export | The report options and the PDF button |
| 6 | Siri dialog | "Logged a migraine episode" after the App Shortcut |

| # | Apple Watch screen | What it shows |
|---|---|---|
| 1 | Summary | The pain-free days, the bar, and the Log Migraine button |
| 2 | Pain Intensity | The Digital Crown value and Save Episode |
| 3 | Episode logged | The checkmark |

To take a screenshot from the simulator:

```bash
xcrun simctl io booted screenshot --type=png summary.png
```

## Review risks found on October 14, 2026

These items do not block the submission, but App Review or a user can ask about them.

- **Guideline 5.1.3(ii)** says that apps "may not store personal health information in iCloud". MigraTrack does not use iCloud storage, but an iCloud backup of the iPhone includes the database. The Privacy Policy says this openly. To remove the doubt, exclude the database from backup (`isExcludedFromBackup`); then a new iPhone starts without the data.
- **Guideline 1.4.1** asks medical apps to remind users to check with a doctor. The reminder is in the Terms of Service, in the App Store description, in a short line under Log Migraine Episode on the Summary, and under the button of the last onboarding step.

## Checks on the simulator

- **iPad.** The app is for iPhone only (`TARGETED_DEVICE_FAMILY = 1`), so App Review can run it on an iPad in compatibility mode. On iPad Pro 13-inch (iPadOS 27) it opens in a window with the iPhone shape. The window controls stay in the status bar area and cover no content.
- **Dynamic Type.** Text follows the iOS text size up to the largest standard size (XXXL). The accessibility sizes above it show the XXXL size, because larger text overflows the fixed chips, bars, and day strips. The page header and the tab bar keep their design size, as UIKit bars do. Titles of rows and chips wrap to two lines instead of being cut.
- **Empty state.** With zero episodes, the Summary, Insights, Export, and the PDF report show zero values and "None yet" or "No episodes recorded" messages. Nothing breaks.

## Release checklist

- [ ] `./gradlew check` passes.
- [ ] The Release archive builds with all three targets and a real team.
- [ ] TestFlight build installs on an iPhone and on a paired watch.
- [ ] Privacy Policy and Support URLs load.
- [ ] Metadata, privacy answers, age rating, and medical device declaration are complete.
- [ ] Screenshots are uploaded for the required sizes.

## Sources

All pages were read on October 14, 2026.

- [App Review Guidelines](https://developer.apple.com/app-store/review/guidelines/), last updated June 8, 2026: 1.4.1, 2.1, 2.3.3, 2.4.1, 2.5.1, 5.1.1, 5.1.3.
- [Screenshot specifications](https://developer.apple.com/help/app-store-connect/reference/app-information/screenshot-specifications), App Store Connect Help.
- [Platform version information](https://developer.apple.com/help/app-store-connect/reference/app-information/platform-version-information): description, keywords, Support URL, review notes.
- [App information](https://developer.apple.com/help/app-store-connect/reference/app-information/app-information): name, subtitle, Privacy Policy URL, medical device declaration.
- [Age ratings values and definitions](https://developer.apple.com/help/app-store-connect/reference/app-information/age-ratings-values-and-definitions).
- [Declare regulated medical device status](https://developer.apple.com/help/app-store-connect/manage-app-information/declare-regulated-medical-device-status/).
- [App privacy details on the App Store](https://developer.apple.com/app-store/app-privacy-details/).
- [Describing use of required reason API](https://developer.apple.com/documentation/bundleresources/describing-use-of-required-reason-api) and [privacy manifest files](https://developer.apple.com/documentation/bundleresources/privacy-manifest-files).
- [Privacy manifest for iOS apps](https://kotlinlang.org/docs/multiplatform/multiplatform-privacy-manifest.html), Kotlin Multiplatform docs, May 16, 2025.
- [What is GitHub Pages?](https://docs.github.com/en/pages/getting-started-with-github-pages/what-is-github-pages): Pages for private repositories needs GitHub Pro, Team, or Enterprise.
