# MigraTrack

MigraTrack is a migraine tracker for iPhone. You log attacks and their possible triggers, see trends in a chart and a month calendar, edit past episodes, and export a clinical report.
The data stays on the device.

The app uses Kotlin Multiplatform. Compose Multiplatform draws the iPhone UI, and Swift and Kotlin/Native call iOS frameworks where the platform needs them.
An Apple Watch app in SwiftUI shows your pain-free days and logs an episode with the Digital Crown. A complication and a control open the logger. The watch app shares the Kotlin core with the iPhone.
Android comes later. [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) gives the seams for it.

## Run the app

You need macOS with Xcode 26.4 or later (tested with Xcode 27.0) and JDK 21.
Gradle finds JDK 21 itself through `gradle/gradle-daemon-jvm.properties`.
The app runs on iOS 18.5 and later, the minimum of the Compose Multiplatform 1.12 graphics library.

1. Open `iosApp/iosApp.xcodeproj` in Xcode.
2. Select an iPhone simulator and press Run.

The first build downloads Gradle, the Kotlin/Native toolchain, and the libraries. It takes a few minutes.

To build from the terminal:

```bash
xcodebuild -project iosApp/iosApp.xcodeproj -scheme iosApp -destination 'platform=iOS Simulator,name=iPhone 18 Pro' -derivedDataPath build/xcode build
```

### Run on an iPhone and an Apple Watch

1. Put your team ID in `TEAM_ID` in `iosApp/Configuration/Config.xcconfig`. A free Personal Team works; HealthKit and App Groups are available to it.
2. Change `APP_BUNDLE_ID` in the same file to an identifier that is yours. The watch targets and the App Group follow it.
3. Run the `iosApp` scheme on the iPhone. The watch app installs with it on the paired watch.

### Try the watch in the simulator

1. Boot a paired iPhone and Apple Watch, for example iPhone 18 Pro Max and Apple Watch Series 12 (46mm). The pairs are listed by `xcrun simctl list pairs`.
2. Run the `iosApp` scheme on the iPhone, then the `MigraTrackWatch` scheme on the watch.
3. On the watch, touch and hold the face, add a face with complication slots (for example Infograph), and choose **MigraTrack › Log Migraine**.

In the simulator a batch the watch queues with `transferUserInfo` does not reach the iPhone app. The watch sends a direct message when the iPhone is reachable, which works in the simulator too.

### See filled charts

A Debug build has **Settings › Developer › Load Sample Data**. It adds a year of generated episodes.

### Log from the watch

The watch app has three screens:

1. **Summary.** The pain-free days of the last 30 as a number and a bar, the date of the last attack, and a **Log Migraine** button.
2. **Pain Intensity.** Turn the Digital Crown to set 1–10, then tap **Save Episode**. The logger starts at your typical intensity.
3. **Episode logged.** A checkmark confirms the save. **Done** or the close button returns to the summary.

The episode gets the acute medications of your treatment blueprint, and it goes to the iPhone at once. It has no triggers. To add them, edit the episode on the iPhone.

| Where | What a tap does |
|---|---|
| Watch app: **Log Migraine** | Opens Pain Intensity. |
| Complication "Log Migraine", on the watch face or in the Smart Stack | Opens the watch app on Pain Intensity. The rectangular style also shows your pain-free days, or "–" if it cannot read them. |
| Control "Log Migraine" (watchOS 26 and later) | Opens Pain Intensity from Control Center, the Smart Stack, or the Action button of Apple Watch Ultra. |
| iPhone: Spotlight, Siri, Shortcuts, Action button | Saves on the iPhone at once with your typical intensity: "Log a migraine in MigraTrack". Before onboarding, Siri says "Finish setup in MigraTrack first." and offers to open the app. |

## Test

```bash
./gradlew check
```

The command runs the shared tests on the JVM, the iOS simulator, and the watchOS simulator. It also checks the database migrations.

## Project layout

| Path | Contents |
|---|---|
| `shared/domain` | Models, use cases, statistics, repository and platform interfaces. Pure Kotlin; it also compiles for watchOS. |
| `shared/data` | SQLDelight store on the system SQLite. Compiles for iOS and watchOS. |
| `shared/export` | Report model, XLSX writer, PDF layout; the PDF is drawn with UIKit on iOS. |
| `shared/sync` | Phone ↔ watch sync rules and JSON payloads. No platform code. |
| `shared/watchcore` | `MigraCore.framework` for the watch app and its complications: a Swift facade over the core, without Compose. |
| `composeApp` | Compose Multiplatform UI, view models, and the iOS platform services. Produces `ComposeApp.framework`. |
| `iosApp/iosApp` | iPhone app: SwiftUI entry point, App Intent, WatchConnectivity, Info.plist, HealthKit entitlement. |
| `iosApp/watchApp` | watchOS app in SwiftUI: the summary of pain-free days, the logger with the Digital Crown, the confirmation, and WatchConnectivity. |
| `iosApp/watchComplications` | WidgetKit extension: the Log Migraine complication and the watchOS 26 control. |
| `docs` | The GitHub Pages site: Support, Privacy Policy, and Terms. `docs/legal/files` holds the legal texts that the app also bundles. [docs/APP_STORE.md](docs/APP_STORE.md) has the App Store metadata and review notes. |

## Design decisions

The screens in the Stitch export and the design specification disagree in some places. The rules were:

- **Tokens come from the specification.** Colors, radii, the type scale, and the font (Plus Jakarta Sans).
- **Layout comes from the most frequent variant in the screens.** Examples: the 20 pt side margin, the header with the brain mark, the settings gear on every tab.
- **Native behavior wins where the screens are silent.** Settings is pushed under the tab bar with a back button and the edge swipe.

The third Stitch export adds the Edit Episode screen and a calendar in Insights. Three of its images are broken files (`analytics_insights`, `analytics_insights_week_w`, `analytics_insights_year_y`), so their HTML was the reference.

- **The month pill next to W / M / 6M / Y has one look on every period.** The HTML draws it two ways, three files each. The look of the calendar screens won, because two of the three valid images show it.
- **The pill month drives the whole tab.** The chart shows the period that ends in that month: the month itself, six months, its year, or the week of its last day. The trend compares spans that end on the same day.
- **Buttons in the calendar have the full 56 pt height.** "Edit Episode Details" is smaller in one screen, but "Log Migraine Episode" fills the same place at full height.
- **The day preview follows `analytics_insights_calendar_expanded`.** The hidden drawer in `analytics_insights` has an older preview with an episode duration, which the app does not record.
- **The tokens stay with the specification.** The `DESIGN.md` of the third export is an older palette with a lime accent on a black canvas.

The fourth Stitch export redraws the Apple Watch app in three screens: `apple_watch_summary_ring`, `apple_watch_log_episode` and `apple_watch_episode_logged`. The watch app now has only what they show. Their web layout became native watchOS controls:

- **The logger and the confirmation are a sheet.** watchOS draws the close button and the time, as the screens show them.
- **The intensity gauge is a 270° arc that is open at the bottom.** watchOS draws its own circular gauges this way. The HTML has the gap at the upper left.
- **The watch uses the system fonts of watchOS.** The app uses SF Compact in the system text styles; the numbers in the summary and the gauge are Large Title in its bold weight. WidgetKit sets SF Compact Rounded in the complications. The HTML loads Plus Jakarta Sans.
- **The backgrounds are dark, with a soft glow of the severity color.** The sheet does not show the summary through it.
- **Log Migraine, Save Episode and Done are at the bottom of the safe area.** The whole button stays clear of the round corners. The system bottom bar puts it 3 pt from the edge of the 40mm SE display. Each screen fits without scrolling, from the 40mm watch up.
- **Each screen shows only its main parts.** Log Migraine has no plus icon. VoiceOver reads the summary as "Pain-free days, 25 of 30".
- **"Pain Intensity" is a gray footnote under the gauge, in place of the "Turn Digital Crown" hint.** The HTML has a caption above the gauge and the hint below it.
- **The gauge shows only the number.** The HTML has "out of 10" under it. VoiceOver still reads "6 out of 10".
- **Episode logged shows only a checkmark and Done.** The checkmark has the size of the gauge, so the two screens match. The HTML also shows the title "Episode logged", the intensity and the medications. VoiceOver reads the checkmark as "Episode logged".
- **The summary is a number over a linear gauge, not the ring of the HTML.** Apple does not allow apps to copy the Activity rings (see [No Activity rings](#no-activity-rings)). The caption "Pain-free days", the number with " / 30", a green `linearCapacity` gauge and "Last attack Oct 12" fit without scrolling from the 40mm watch up, also at the largest accessibility text size.
- **The gauge takes 33% of the screen width**, the most that it gets on the 49mm watch.
- **Text uses the system text styles and follows Dynamic Type.**

Removed from the watch, because the screens do not have them: the This month and Last attack rows, the − and + buttons, the medications on the logger, Undo, the separate Pain-Free Days complication, and the Siri command on the watch. The iPhone keeps its App Shortcut.

The screens do not show complications. The project owner chose the rest:

- **A complication tap opens the logger and does not save at once.** An accidental tap saves nothing, so Undo is not necessary.
- **One complication, "Log Migraine".** Its rectangular style also shows the pain-free days, as the summary screen does. The other styles show only the action.
- **The control stays and opens the logger.**
- **Before the first sync, a caption under the summary asks you to open MigraTrack on the iPhone.**

### No Activity rings

The first designs showed the pain-free days in rings like the Apple Activity rings: in the app icon, on the welcome screen, on the Summary and on the watch. App Review guideline 5.2.5 and the [Human Interface Guidelines](https://developer.apple.com/design/human-interface-guidelines/activity-rings) do not allow this. The project owner chose these replacements:

- **The app icon is a diary of 12 day dots.** Three pink dots are attack days. The iPhone and the watch use the same icon.
- **The Summary shows a "Last 30 days" card.** It shows the pain-free days as "6 of 13 tracked days" and a strip of 30 day dots, oldest first, with today at the end. Pink is an attack day and green is a pain-free day, as in Insights. Gray is a day before you started tracking. The card also shows last night's sleep.
- **No combined score.** The "Biometric balance" score mixed the pain-free share with a sleep score that had no stated method, and showed 100 on the first day. The card now shows only counts.
- **A doctor reminder under Log Migraine Episode and under the button of the last onboarding step**: "MigraTrack is a diary, not medical advice." It follows guideline 1.4.1. Onboarding also does not say "calibrate": step 2 is "Your Triggers".
- **The Summary tab has a calendar icon.**

The welcome screen comes from a design of the project owner. It follows `sign_in_registration` of the first Stitch export, but has one **Get Started** button in place of "Continue with Apple" and "Continue with Google". The app has no accounts for now; [the auth and sync proposal](https://github.com/felalag/MirgaTrack/pull/5) gives the reasons.

- **Get Started is the pink primary button.** The design draws it white, but every other main action in the app uses the pink pill of the specification.
- **The card is an example of the Summary card,** with the same 30-day strip and an "Example" label. The headline says "Bring the facts to your doctor." and promises no prediction.
- **The links open the Terms of Service and the Privacy Policy in the app.** The texts ship with the app, so they open offline. Settings has a Legal section with the same two pages. The texts are drafts: they need a legal review and a contact address before a release.
- **Get Started pushes step 1 of onboarding.** Step 1 now has a back button, and the edge swipe returns to the welcome screen.

Additions that the screens imply but do not show:

- **Step 3 of onboarding, "Treatment Blueprint".** Step 2 ends with "Continue to Medications", and Settings has a Treatment Blueprint row. The step follows the style of step 2.
- **Edit and delete an episode.** Open the calendar in Insights and tap a day, or tap the Last Event card on Summary. A day with two episodes asks which one.
- **Log a past episode.** Tap an empty day in the calendar, or change **Episode Start** at the bottom of the Log Episode form. The row opens the system date picker. The screens have no time field, but the calendar logs on past days and edits old episodes, so the form needs one.
- **Move between months.** Swipe the calendar grid sideways, or tap the month title for month and year wheels.
- **Edit your name.** Tap the name on the Settings profile card. The avatar shows initials, not a photo.
- **Delete all data.** No account server exists, so Delete All Data in Settings erases the data on this iPhone after a confirmation. The confirmation names the copy on the watch and the device backups. The welcome screen then shows again. The watch deletes its copies with the next context and does not log until onboarding is complete again.
- **Possible triggers of an episode.** The welcome screen promises help with triggers, but onboarding records only the known triggers of the profile. Each Insights screen of the Stitch exports has an empty "Top Identified Triggers Breakdown" slot. The `DESIGN.md` of the third export describes trigger badges: capsules that take the category color when chosen, with violet as the accent of trigger categories. The project owner approved this design:
  - The Log Episode and Edit Episode forms have a **Possible Triggers** card after the symptoms. It shows the known triggers of the profile as capsules, but does not select them. If it selected them, every known trigger would be in almost every episode, and the frequency would have no meaning. **Add Trigger** opens the rest of the catalog and a custom entry.
  - Insights has a **Possible Triggers** card below the chart. For the shown period it lists the five triggers that you noted most often, as "5 of 9" with a bar. The card shows how often you noted a trigger, not what causes an attack, and it says so. A last line counts the episodes with no triggers noted.
  - The PDF has a **Possible Triggers Noted** section with the share of episodes and the same note. The episode log of the PDF and the Episodes sheet of the XLSX have a column for the triggers. The **Possible Triggers** toggle in Export controls all three.
  - The watch and the iPhone App Shortcut log with one tap during an attack, so their episodes have no triggers. You add them later in Edit Episode. Sync sends the triggers to the watch with the episode, and older builds ignore the field.
  - An episode keeps a copy of the trigger name, as it does for a medication name, so the history stays readable.
- **Sleep from Apple Health.** The Last 30 days card shows how long you slept last night, from HealthKit. The app does not relate sleep to attacks, so the Health permission text, the Connect card and the App Store description promise only last night's sleep on the Summary. The simulator has no sleep data unless you add some in the Health app.

## Known limits

- The UI text is English only.
- An episode has a start time but no end, so the app shows no attack duration.
- A medication has no time of its own. The app sets it to the start of the attack, so the PDF and the XLSX show no intake time.
- The project has no linter configured. The build has no compiler warnings.
