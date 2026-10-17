# App Store review readiness

| | |
|---|---|
| Analysis date | October 14, 2026 |
| Commit | `a6f8d201974b113bf3184f55c0fe3c89d3be3576` (`origin/main`, merge of PR #11) |
| Open pull requests | Only #5, a draft with documentation (the decision on accounts and cloud storage). It changes no code. |
| Toolchain | Xcode 27.0 (27A266a), iOS 27.0 and watchOS 27.0 SDKs |

This report replaces no other document. [APP_STORE.md](APP_STORE.md) keeps the metadata, the privacy answers, and the review notes. This report tells you what is still missing before you submit, and which user journeys are missing.

## Summary

MigraTrack 1.0 is technically ready to upload. The Release archive builds, the privacy manifests are correct, the public URLs load, and all tests pass.

Three things stop the submission now: there is no signing team, there are no store screenshots, and the App Store Connect forms are not complete.

Three things can cause a rejection after the submission:

1. **The app copies the Apple Activity rings.** The app icon, the welcome screen, the Summary, and the watch show red and green concentric rings. Apple tells developers to never use Activity rings for other data or in an app icon (guideline 5.2.5 and the Human Interface Guidelines).
2. **The Health permission promises a feature that the app does not have.** The purpose string says that the app shows "how rest relates to your migraine attacks". The app shows only last night's sleep.
3. **The app shows a health score without a method.** "Biometric balance" shows 100 on the first day, without data. Guideline 1.4.1 asks for the method behind health measurements and for a reminder to see a doctor.

The most important missing user journeys are these: an attack that is still in progress (no end time, no duration), a medication taken later or a second dose, the count of days with acute medication for the doctor, and quick logging during an attack with photophobia.

Do the work in this order:

1. Redesign the icon and the rings (R1). The screenshots depend on it.
2. Correct the Health texts and the score (R2, R3). This is text and one small screen change.
3. Sign, archive, and upload to TestFlight (B1). Then make the screenshots (B2) and complete the forms (B3).
4. After the release, add the end of an attack and the medication times (J1, J2, J3).

## 1. Status of the earlier findings

The earlier analysis of October 14, 2026 listed the gaps below. All four pull requests that it named are merged into `main`.

| Earlier finding | True for `main` now? | Closed by | Evidence |
|---|---|---|---|
| No privacy manifest | No, closed | PR #9 | The archive has `PrivacyInfo.xcprivacy` in the app, the watch app, and the complications. `nm` finds only `stat` and `fstat` in the iPhone binary. The manifest declares them as `C617.1`. The watch binaries use no required-reason API. |
| No public Privacy Policy and Support URL | No, closed | PR #9 | `/privacy/`, `/support/`, `/terms/` and the home page return HTTP 200. The Support page shows the contact email. |
| No notes for App Review, no screenshot plan | No, closed | PR #9 | [APP_STORE.md](APP_STORE.md) has both. The screenshots themselves do not exist yet (B2). |
| "Identify hidden triggers" without triggers on an episode | No, closed | PR #11 | Episodes keep triggers ([Episode.kt:18](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/model/Episode.kt#L18)). The welcome text now says "spot possible triggers". |
| Deleted data comes back from the watch | No, closed | PR #8 | [PhoneSync.kt:61](../shared/sync/src/commonMain/kotlin/com/migratrack/sync/PhoneSync.kt#L61) drops episodes older than the erase time. |
| Siri saves an episode before onboarding | No, closed | PR #8 | [EpisodeLogging.kt:149](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/usecase/EpisodeLogging.kt#L149) returns null without a baseline. |
| Days before tracking count as pain-free | No, closed | PR #10 | [MigraineDays.kt:30](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/analytics/MigraineDays.kt#L30). The PDF explains the untracked days. |
| No end of attack, no duration, no second dose, no medication effect | Yes | — | J1, J2 |
| No reminders | Yes | — | The project does not use `UserNotifications`. J4 |
| No count of days with acute medication | Yes | — | J3 |
| No import or transfer of data | Yes | — | J7 |
| No iPhone widget and no Control Center control | Yes | — | Only the watch has a WidgetKit extension. J5 |
| No menstrual cycle data | Yes | — | HealthKit reads only sleep. J12 |
| The day of an attack uses the current time zone | Yes | — | J6 |

The earlier list missed the Activity ring problem (R1), the Health purpose string (R2), and the score without a method (R3).

## 2. Review readiness

Each finding has an ID, the evidence, and a recommendation. The groups are:

- **Blocks the submission.** You cannot submit, or the app does not reach all users, until you do it.
- **Probable reason for rejection.** You can submit, but App Review can reject the app for it.
- **Recommended.** It is not a review problem, or the risk is low, but it makes the app better or the review easier.

### 2.1 Blocks the submission

#### B1. No team, so no signed archive

- **Evidence.** [Config.xcconfig:3](../iosApp/Configuration/Config.xcconfig#L3) has an empty `TEAM_ID`. The Release archive builds only with `CODE_SIGNING_ALLOWED=NO`. The bundle ID `com.migratrack.app` is not registered yet. On October 14, 2026 the US App Store has no app with the name MigraTrack, but App Store Connect checks the name only when you create the app record.
- **Recommendation.**
  1. Set `TEAM_ID`. Register the App IDs `com.migratrack.app` (with HealthKit), `com.migratrack.app.watchkitapp`, and `com.migratrack.app.watchkitapp.complications` (both with App Groups), and the App Group `group.com.migratrack.app`.
  2. If somebody else has the bundle ID, change `APP_BUNDLE_ID` before the first upload. You cannot change it after the first upload.
  3. Archive, validate, and upload to TestFlight. Open **Generate Privacy Report** on the archive. Install the TestFlight build on an iPhone with a paired watch.

#### B2. No store screenshots

- **Evidence.** App Store Connect requires at least one iPhone screenshot at 1206 × 2622 (Dynamic Island, medium display) and one Apple Watch screenshot, for example 416 × 496 ([Screenshot specifications](https://developer.apple.com/help/app-store-connect/reference/app-information/screenshot-specifications)). The repository has only the plan in [APP_STORE.md](APP_STORE.md#screenshots). The screenshots of other sessions show Debug data and older watch layouts.
- **Recommendation.** Do R1 first, because the screenshots and the icon must not show Activity-style rings. Then take the screenshots from the plan. Use the Apple Watch Series 12 (46 mm) simulator; it gives 416 × 496.

#### B3. App Store Connect forms are not complete

- **Evidence.** These forms are in App Store Connect, not in the repository:
  - The age rating questionnaire. It is mandatory since January 31, 2026 ([Upcoming requirements](https://developer.apple.com/news/upcoming-requirements/)). The answers are in [APP_STORE.md](APP_STORE.md#age-rating).
  - App Privacy ("Data Not Collected") and the regulated medical device declaration ("No"). The answers are in APP_STORE.md.
  - Export compliance. `ITSAppUsesNonExemptEncryption` is `false` in [Info.plist:25](../iosApp/iosApp/Info.plist#L25), so App Store Connect asks nothing.
  - The EU Digital Services Act trader status. Without it, the app is not available in the EU. A trader must show an address, a phone number, and an email on the App Store page.
  - App Accessibility (Accessibility Nutrition Labels). Apple says that this is voluntary for now ([Overview of Accessibility Nutrition Labels](https://developer.apple.com/help/app-store-connect/manage-app-accessibility/overview-of-accessibility-nutrition-labels)). A developer reports that an app stayed in "Processing" for 12 hours until the section was complete ([Developer Forums thread 827270](https://developer.apple.com/forums/thread/827270), May 2026).
- **Recommendation.** Complete all the forms. For App Accessibility, declare only what the app supports: Dark Interface, and VoiceOver after a test on a device (D2). Do not declare Larger Text (D1).

### 2.2 Probable reasons for rejection

#### R1. Rings that copy the Apple Activity rings

- **Evidence.**
  - The app icon ([app-icon-1024.png](../iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/app-icon-1024.png)) shows a red outer ring and a green inner ring on black, with a glow. The watch uses the same icon.
  - The welcome screen ([WelcomeScreen.kt:78](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/welcome/WelcomeScreen.kt#L78)) and the Summary ([SummaryScreen.kt:93](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/summary/SummaryScreen.kt#L93)) draw the same two rings. The component is named `ActivityRings`, and its comment says "as the Activity rings do" ([ActivityRings.kt:33](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/summary/ActivityRings.kt#L33)).
  - The watch summary draws a green ring "like an Activity ring" ([SummaryView.swift:37](../iosApp/watchApp/SummaryView.swift#L37)). On the watch the real Activity rings are a system feature.
  - Screenshots: [welcome-rings.jpg](review-readiness/welcome-rings.jpg), [watch-summary.png](review-readiness/watch-summary.png).
  - Guideline 5.2.5: "Don't create an app that appears confusingly similar to an existing Apple product, interface [...] or app". The [Human Interface Guidelines, Activity rings](https://developer.apple.com/design/human-interface-guidelines/activity-rings) say: "Don't replicate or modify Activity rings for other purposes" and "Never use Activity rings in your app's icon or marketing materials."
- **Recommendation.**
  - Make a new icon without concentric rings. For example, use the brain mark of the app header.
  - On the Summary, show the pain-free days in a different form: one labelled bar, a 30-day strip of day dots, or one ring in a color that is not red or green, with a visible label.
  - On the watch, use a labelled gauge or a number with a caption, not a green ring with a glow.
  - Rename `ActivityRings` so that the next change does not copy the style again.

#### R2. The Health permission and the texts promise a sleep feature that the app does not have

- **Evidence.**
  - The purpose string ([Info.plist:30](../iosApp/iosApp/Info.plist#L30)) says "to show how rest relates to your migraine attacks".
  - The Summary card says "See how sleep shapes your attacks" ([SummaryScreen.kt:206](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/summary/SummaryScreen.kt#L206)).
  - The App Store description says that the app "shows last night's sleep next to your attacks".
  - The app reads only last night's sleep and shows it on the Summary ([HealthKitSleepDataSource.kt:63](../composeApp/src/iosMain/kotlin/com/migratrack/app/platform/HealthKitSleepDataSource.kt#L63)). Episodes, Insights, and the report have no sleep data.
  - Guidelines 2.3 (accurate metadata), 2.5.1 (use HealthKit for its stated purpose), and 5.1.1(iii) (request only data for the core function). A reviewer who taps Connect Apple Health and then looks for the relation finds nothing.
- **Recommendation.** Before 1.0, make the texts agree with the app. For example: "MigraTrack reads last night's sleep to show it on the Summary next to your pain-free days." Change the card title and the description in the same way. Later, store the sleep of the night before each episode and show it in Insights and in the report. Then the original texts become true.

#### R3. A health score without a method, and no doctor reminder in the main screens

- **Evidence.**
  - "Biometric balance" ([SummaryScreen.kt:90](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/summary/SummaryScreen.kt#L90)) is the mean of the pain-free percentage and a "rest score" ([Summary.kt:47](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/analytics/Summary.kt#L47)). The rest score uses targets of 8 hours and 40% deep and REM sleep ([Summary.kt:53-66](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/analytics/Summary.kt#L53)). The app does not explain the score. "Biometric" suggests a measurement from sensors.
  - On the first day, without episodes and without sleep data, the score is 100 ([summary-day-one.jpg](review-readiness/summary-day-one.jpg)). The welcome screen shows a sample score of 88 ([WelcomeScreen.kt:83](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/welcome/WelcomeScreen.kt#L83)).
  - "Stay ahead of pain" ([WelcomeScreen.kt:93](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/welcome/WelcomeScreen.kt#L93)) suggests a prediction. Onboarding says "calibrate your personal baseline and stability rings" ([OnboardingScreens.kt:143](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/onboarding/OnboardingScreens.kt#L143)).
  - The reminder to see a doctor is only in the Terms of Service (two taps from Settings) and in the PDF footer ([ReportPdf.kt:336](../shared/export/src/commonMain/kotlin/com/migratrack/export/pdf/ReportPdf.kt#L336)).
  - Guideline 1.4.1: apps "must clearly disclose data and methodology to support accuracy claims relating to health measurements" and "should remind users to check with a doctor".
- **Recommendation.**
  - Remove "Biometric balance" and the combined score. Show the plain facts: "6 of 13 tracked days without a logged attack" and the hours of sleep.
  - If you keep a sleep score, add an info button that explains the formula, and say that it is not a clinical score.
  - Add one line on the Summary or the Export screen and on the last onboarding step: "MigraTrack is a diary, not medical advice. Talk to your doctor before you change your treatment."
  - Replace "Stay ahead of pain" and "calibrate" with words that do not suggest prediction or measurement.

### 2.3 Recommended

| ID | Finding | Evidence | Recommendation |
|---|---|---|---|
| D1 | Text grows to 135% at most. The header and the tab bar do not grow. The app cannot claim the Larger Text label, which needs 200% or more. Nothing overlaps at the largest size. | [Theme.kt:76](../composeApp/src/commonMain/kotlin/com/migratrack/app/ui/theme/Theme.kt#L76), [summary-ax-xxxl.jpg](review-readiness/summary-ax-xxxl.jpg) | Keep the cap for 1.0 and do not declare Larger Text. Later, let lists and forms grow to 200% and keep the cap only on the rings and the chart. |
| D2 | VoiceOver was not tested on a device. The code gives labels and roles to the pain scale, the calendar days, the switches, the tabs, and the watch ring. | `semantics` in [PainScale.kt](../composeApp/src/commonMain/kotlin/com/migratrack/app/ui/components/PainScale.kt), [InsightsCalendar.kt:246](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/insights/InsightsCalendar.kt#L246), [Controls.kt:158](../composeApp/src/commonMain/kotlin/com/migratrack/app/ui/components/Controls.kt#L158) | Before you declare VoiceOver, do the common tasks with VoiceOver on an iPhone: onboarding, log, edit, delete, export, Settings. Run the Accessibility Inspector audit. |
| D3 | The watch summary shows a number without a unit, for example "6". A sighted user cannot know that it means pain-free days. | [watch-summary.png](review-readiness/watch-summary.png) | Add a short caption, for example "pain-free days", or "6 of 13". Do it together with R1. |
| D4 | The complication shows sample data, "26 of 30 days pain-free", when it cannot read the database. | [Complications.swift:11](../iosApp/watchComplications/Complications.swift#L11), [Complications.swift:32](../iosApp/watchComplications/Complications.swift#L32) | Use the placeholder only in the widget gallery. Show "–" when the read fails. |
| D5 | The report shows a time for each medication, but the app sets every time to the start of the attack. A doctor reads a time that the user never entered. | [EpisodeLogging.kt:49](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/usecase/EpisodeLogging.kt#L49), [ReportPdf.kt:309](../shared/export/src/commonMain/kotlin/com/migratrack/export/pdf/ReportPdf.kt#L309) | Until J2 is done, do not print the medication time in the report. |
| D6 | Clinical texts are not exact. "Chronic Migraine: >15 headache days per month" is not the ICHD-3 definition (15 or more headache days a month for more than 3 months, with 8 or more migraine days). "5–8 / mo" and "8+ / mo" overlap. | [BaselineProfile.kt:32](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/model/BaselineProfile.kt#L32), [BaselineProfile.kt:41-42](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/model/BaselineProfile.kt#L41) | Use "15 or more headache days a month" and the ranges "5–7" and "8+". |
| D7 | Guideline 5.1.1(ix) asks that apps in "highly regulated fields (such as [...] healthcare [...])" come from a legal entity, not an individual. MigraTrack gives no healthcare service; it is a personal diary. The risk is low. | [App Review Guidelines](https://developer.apple.com/app-store/review/guidelines/) 5.1.1(ix) | Keep the review notes clear that the app gives no diagnosis or treatment. If App Review cites 5.1.1(ix), answer with that fact. |
| D8 | The Privacy Policy does not say how long the app keeps the data, and it does not name the developer. Guideline 5.1.1(i) asks for retention. | [privacy-policy.md](legal/files/privacy-policy.md) | Add: "The data stays until you delete it in the app or delete the app." Add the developer name. |
| D9 | The Delete All Data dialog says that the data is "only on this iPhone". The watch also has a copy until the next sync, and old backups keep their copy. | [SettingsScreen.kt:103](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/settings/SettingsScreen.kt#L103) | Name the watch and the backups in the text. |
| D10 | Closed by stage 1c of the [localization plan](LOCALIZATION_PLAN.md). Before, all dates and times were English and in 24-hour format, also for a US user. Now the system formatters write the dates, times and numbers of the UI and the report in the format of the device region, for example 12-hour time in US English. The first day of the week also follows the region. | [SystemFormats.kt](../composeApp/src/iosMain/kotlin/com/migratrack/app/ui/SystemFormats.kt) | Keep English as the only language for 1.0, and select only English in App Store Connect. |
| D11 | The Settings avatar has a green "online" dot. The app has no account and no online state. | [SettingsScreen.kt:197-204](../composeApp/src/commonMain/kotlin/com/migratrack/app/feature/settings/SettingsScreen.kt#L197) | Remove the dot. |

### 2.4 Checked and correct

| Area | Result |
|---|---|
| Build | The Release archive for iOS (`generic/platform=iOS`) and the Release and Debug simulator builds succeed. The watch app and the complications are inside the archive. Minimum versions: iOS 18.5 and watchOS 11.0. SDK 27.0. Apple needs Xcode 26 or later since April 28, 2026. |
| Tests | `./gradlew check`: 164 test runs on the JVM and the iOS and watchOS simulators, 0 failures. SQLDelight checks the migrations 1 → 2 → 3. |
| Privacy manifests | Three manifests in the archive. `NSPrivacyTracking` false, no collected data types. Required-reason APIs agree with `nm` (see section 1). |
| App icons | 1024 × 1024, no alpha channel, for iPhone and watch. (The design is a problem, see R1.) |
| HealthKit | Read-only access to sleep analysis. `NSHealthShareUsageDescription` is present. No `NSHealthUpdateUsageDescription` is necessary. The app works without Health access. (The text is a problem, see R2.) |
| Guideline 5.1.3(ii) | The app stores no health data in iCloud. iCloud device backups can include the database, and the Privacy Policy says so. Note: a later iCloud or CloudKit sync of episodes would break 5.1.3(ii). |
| Legal pages | Privacy, Terms, and Support load from GitHub Pages and from Settings. The welcome screen links Terms and Privacy. |
| Debug features | Release has no Developer section ([release-settings.jpg](review-readiness/release-settings.jpg)). Guideline 2.3.1 is met. |
| Clean install | Welcome, three setup steps, and Summary work on the iPhone 18 Pro simulator with the Release build. |
| iPad | The app is for iPhone only. On iPad Pro 11-inch (iPadOS 27) it opens in an iPhone-shaped window. The window controls cover no content ([ipad-compatibility.jpg](review-readiness/ipad-compatibility.jpg)). |
| Apple Watch | The Release watch app opens on the Series 12 (46 mm) simulator and shows the synced value. The other sessions tested the 40, 42, 46, and 49 mm sizes and AX sizes. |
| Export compliance | `ITSAppUsesNonExemptEncryption` is false. The app uses no encryption of its own. |

## 3. Missing user journeys

The journeys come from two roles: a patient with migraine, and a doctor who reads the patient's report. Priority combines the value for the user and the risk at review.

| ID | Journey | What happens now | Value | Review risk | Priority |
|---|---|---|---|---|---|
| J1 | **The attack is still in progress.** The patient logs the start and wants to add the end later. | An episode has only a start ([Episode.kt:10-20](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/model/Episode.kt#L10)). There is no end, no duration, and no "ongoing" state. An attack from 22:00 to 10:00 counts as one migraine day. The doctor cannot check the 4–72 hour criterion. | High | Low | 1 |
| J2 | **The medication comes later, or a second dose follows.** | Every medication gets the start time ([EpisodeLogging.kt:49](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/usecase/EpisodeLogging.kt#L49)). The edit form keys medications by ID ([EpisodeLogging.kt:70](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/usecase/EpisodeLogging.kt#L70)), so a second dose of the same drug has no place. There is no "did it help after 2 hours" answer. | High | Low (D5 is the review part) | 1 |
| J3 | **The doctor checks medication overuse.** The doctor needs the days with acute medication for each month (10 days for triptans, 15 for simple analgesics). | The report counts intakes for the whole range ([ClinicalReport.kt:108](../shared/export/src/commonMain/kotlin/com/migratrack/export/report/ClinicalReport.kt#L108), [ReportPdf.kt:217-225](../shared/export/src/commonMain/kotlin/com/migratrack/export/pdf/ReportPdf.kt#L217)). It has no month table and no days. | High | Low | 1 |
| J4 | **The patient forgets to log.** | A day without a log counts as pain-free ([MigraineDays.kt:30-36](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/analytics/MigraineDays.kt#L30)). There are no reminders and no daily "Any headache today?" check-in. Quick logs from Siri and the watch have no symptoms or triggers, and nothing asks the patient to complete them later (`LogSource.QuickLog` is not used in the UI). | High | Low | 2 |
| J5 | **Logging during an attack with photophobia.** | Siri and the Action button log with one command. In the app, Save Episode Log is at the end of a long form: three swipes on iPhone 18 Pro Max ([log-form-top.jpg](review-readiness/log-form-top.jpg), [log-form-bottom.jpg](review-readiness/log-form-bottom.jpg)). The iPhone has no Lock Screen widget and no Control Center control. The dark theme helps. | High | Low | 2 |
| J6 | **The doctor reads the report.** | The report has totals, a pain timeline, baseline, medications, symptoms, triggers, and a log. It has no monthly migraine days and headache days, no duration, no treatment response, no disability score (for example MIDAS or HIT-6), and no start dates of preventive treatment. "Pain-free days" means "days without a logged migraine". | High | Low | 2 |
| J7 | **A new phone, or the app is deleted.** | An iCloud or computer backup restores the database. Without a backup, the data is lost. PDF and Excel cannot be imported again. Guideline 5.1.3(ii) blocks an iCloud sync of health data. | Medium | Low | 3 |
| J8 | **Travel across time zones.** | The app stores instants and uses the current time zone for days and times ([AppGraph.kt:26](../composeApp/src/commonMain/kotlin/com/migratrack/app/di/AppGraph.kt#L26), [MigraineDays.kt:19](../shared/domain/src/commonMain/kotlin/com/migratrack/domain/analytics/MigraineDays.kt#L19)). After a flight, an attack can move to another day in the calendar and in the report. | Medium | Low | 3 |
| J9 | **Menstrual migraine.** | The trigger "Hormonal Shifts / Cycle" exists, but the app does not read cycle data from Health. | Medium | Low (a new Health type needs a new purpose string) | 3 |
| J10 | **Apple Watch without the iPhone nearby.** | The watch logs and keeps the episode in an outbox until the iPhone confirms it ([PhoneConnection.swift:21-37](../iosApp/watchApp/PhoneConnection.swift#L21)). The watch cannot set a past time or a medication. The summary number has no label (D3). | Medium | Low | 3 |
| J11 | **Delete data.** | One episode: delete with a confirmation, no undo. All data: confirmation, and the watch follows. The dialog does not offer an export first, and its text misses the watch and the backups (D9). | Low | Low | 4 |
| J12 | **Update with an old database.** | Migrations 1 → 3 are checked at build time. `MigrationTest` checks the data of the 2 → 3 step. The sync payloads only add fields with defaults. | Low | Low | 4 |

### Recommended designs for the top journeys

- **J1 and J2.** Add `endedAt` (null means "in progress") and a list of intakes with their own time and an optional "relief after 2 h" answer. Show an "In progress" card on the Summary with **End Attack** and **Took Medication**. Add an App Intent "End my migraine" for Siri and the watch. Show duration and medication times in the report.
- **J3 and J6.** Add a month table to the PDF and Excel: migraine days, headache days, days with acute medication by class, and the average peak pain. Mark months above the overuse limits without a diagnosis, for example "10 or more days with triptans".
- **J4.** Add an optional daily reminder at a time that the user selects, and a "quick logs to complete" row on the Summary.
- **J5.** Keep **Save Episode Log** fixed above the tab bar. Add an iOS Control (iOS 18) and a Lock Screen widget that run the existing `LogMigraineIntent`.
- **J7.** Add "Export all data" (JSON or CSV in a ZIP) and "Import" through the Files app. Do not use iCloud or CloudKit for the episodes.
- **J8.** Store the UTC offset with each episode and use it for the day and the time.

## 4. Test record

| Check | Device or command | Result |
|---|---|---|
| Release archive, unsigned | `xcodebuild -scheme iosApp -configuration Release -destination 'generic/platform=iOS' archive CODE_SIGNING_ALLOWED=NO` | Succeeded |
| Release and Debug simulator builds, watch build | `xcodebuild` for iOS Simulator and watchOS Simulator | Succeeded |
| Unit tests | `./gradlew check` | 164 runs, 0 failures |
| Required-reason API scan | `nm -u` on the three binaries of the archive | `stat`, `fstat` in the iPhone app only |
| Public URLs | `curl` on `/`, `/privacy/`, `/support/`, `/terms/` | HTTP 200 |
| Clean install | iPhone 18 Pro, Release | Welcome → setup → Summary work |
| Data device | iPhone 18 Pro Max, Release over the existing v3 database (68 episodes) | The data opens. Log form checked. |
| Largest text size | iPhone 18 Pro Max, `accessibility-extra-extra-extra-large` | No overlap. Text stops at 135%. |
| iPad compatibility | iPad Pro 11-inch (M5), iPadOS 27 | Opens in an iPhone-shaped window |
| Apple Watch | Series 12 (46 mm), Release | Summary shows the synced value |
| VoiceOver | — | Not tested on a device. The simulator gives no VoiceOver, and the Accessibility Inspector was not available to this session. |
| Signed upload, TestFlight, privacy report | — | Not possible without a team (B1) |

## Sources

All pages were read on October 14, 2026.

- [App Review Guidelines](https://developer.apple.com/app-store/review/guidelines/), last updated June 8, 2026: 1.4.1, 2.1, 2.3, 2.5.1, 4.2, 4.2.3, 5.1.1, 5.1.3, 5.2.5.
- [Human Interface Guidelines: Activity rings](https://developer.apple.com/design/human-interface-guidelines/activity-rings).
- [Upcoming requirements](https://developer.apple.com/news/upcoming-requirements/): Xcode 26 SDKs from April 28, 2026; age rating answers by January 31, 2026; EU trader status; required-reason APIs from May 1, 2024.
- [Screenshot specifications](https://developer.apple.com/help/app-store-connect/reference/app-information/screenshot-specifications).
- [Overview of Accessibility Nutrition Labels](https://developer.apple.com/help/app-store-connect/manage-app-accessibility/overview-of-accessibility-nutrition-labels).
- [Developer Forums thread 827270](https://developer.apple.com/forums/thread/827270): an app stayed in "Processing" until the App Accessibility section was complete.
- [Describing use of required reason API](https://developer.apple.com/documentation/bundleresources/describing-use-of-required-reason-api).
- The other sources in [APP_STORE.md](APP_STORE.md#sources).
