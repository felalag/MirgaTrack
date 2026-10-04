# MigraTrack

MigraTrack is a migraine tracker for iPhone. You log attacks, see trends, and export a clinical report.
The data stays on the device.

The app uses Kotlin Multiplatform. Compose Multiplatform draws the iPhone UI, and Swift and Kotlin/Native call iOS frameworks where the platform needs them.
An Apple Watch app in SwiftUI logs an episode from a complication, from the Smart Stack, or from a control. It shares the Kotlin core with the iPhone.
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
3. On the watch, touch and hold the face, add a face with complication slots (for example Infograph), and choose **MigraTrack › Log Migraine** and **Pain-Free Days**.

In the simulator a batch the watch queues with `transferUserInfo` does not reach the iPhone app. The watch sends a direct message when the iPhone is reachable, which works in the simulator too.

### See filled charts

A Debug build has **Settings › Developer › Load Sample Data**. It adds a year of generated episodes.

### Log in one tap

Every quick log saves an episode with your typical intensity and the acute medications of your treatment blueprint.

| Where | What a tap does |
|---|---|
| Watch face complication "Log Migraine" | Opens the watch app, which saves the episode at once and offers Undo. watchOS lets a watch-face complication only open its app. |
| The same complication in the Smart Stack | Saves the episode in place, without opening the app. |
| Control "Log Migraine" (watchOS 26 and later) | Saves in place from Control Center, the Smart Stack, or the Action button of Apple Watch Ultra. |
| iPhone: Spotlight, Siri, Shortcuts, Action button | Saves on the iPhone: "Log a migraine in MigraTrack". |

The "Pain-Free Days" complication shows the 30-day ring of the Summary tab.

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
| `iosApp/watchApp` | watchOS app in SwiftUI: summary ring, logger with the Digital Crown, App Intent, WatchConnectivity. |
| `iosApp/watchComplications` | WidgetKit extension: the two complications and the watchOS 26 control. |

## Design decisions

The screens in the Stitch export and the design specification disagree in some places. The rules were:

- **Tokens come from the specification.** Colors, radii, the type scale, and the font (Plus Jakarta Sans).
- **Layout comes from the most frequent variant in the screens.** Examples: the 20 pt side margin, the header with the brain mark, the settings gear on every tab.
- **Native behavior wins where the screens are silent.** Settings is pushed under the tab bar with a back button and the edge swipe.

Additions that the screens imply but do not show:

- **Step 3 of onboarding, "Treatment Blueprint".** Step 2 ends with "Continue to Medications", and Settings has a Treatment Blueprint row. The step follows the style of step 2.
- **Delete an episode.** Tap the Last Event card on Summary.
- **Edit your name.** Tap the name on the Settings profile card. The avatar shows initials, not a photo.
- **Sign out.** No account server exists, so Sign Out erases the data on this iPhone after a confirmation.
- **Sleep from Apple Health.** The Sleep card reads last night's stages from HealthKit. The simulator has no sleep data unless you add some in the Health app.

## Known limits

- The UI text is English only.
- An episode starts at the moment you save it; you cannot log a past time yet.
- The project has no linter configured. The build has no compiler warnings.
