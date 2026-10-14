# Architecture

This document records why the project uses each tool and where Android and the Apple Watch plug in.
Research date: 2026-10-04.

## Stack

| Part | Choice | Why |
|---|---|---|
| Language and targets | Kotlin 2.4.20 Multiplatform | Latest stable release. The core modules compile for iOS, watchOS and the JVM. |
| UI | Compose Multiplatform 1.12.1 (foundation only, no Material) | One UI for iOS and, later, Android. Material components would look like Android on an iPhone, so the components are drawn to iOS metrics. |
| Navigation | JetBrains navigation-compose 2.9.2 | On iOS it gives the push transition and the edge swipe back. |
| State | JetBrains lifecycle-viewmodel 2.11.0, StateFlow | The official KMP view model. |
| Storage | SQLDelight 2.4.0 on the system SQLite | No code generation through KSP. It ships every watchOS target, and the watch keeps the file in an App Group container for its complications. Migrations are verified at build time. |
| Phone ↔ watch payloads | kotlinx-serialization-json 1.11.0 | The official JetBrains library. New fields with defaults keep older apps on the other device reading new payloads. |
| Dependency injection | A hand-wired `AppGraph` | Fewer than twenty objects. A container library adds a dependency and buys nothing here. |
| Dates | kotlinx-datetime 0.8.0 and `kotlin.time.Instant` | `kotlin.time.Instant` and `Clock` are stable since Kotlin 2.3. |
| Build | Gradle 9.7.1, daemon on JDK 21 | Kotlin 2.4.20 lists Gradle up to 9.7 as tested. |

The project adds no third-party libraries beyond JetBrains, Google and Cash App artifacts. XLSX, ZIP, PDF layout and icons are project code.
The watch side uses only Apple frameworks: SwiftUI, WidgetKit, App Intents and WatchConnectivity.

### Xcode 27

Kotlin 2.4.20 lists Xcode 26.4 as tested. Xcode 27 support is planned for Kotlin 2.5.
With Xcode 27.0 the framework compiles, links and passes the simulator tests. `kotlin.apple.xcodeCompatibility.nowarn=true` in `gradle.properties` hides the version warning.
Remove the flag when you move to Kotlin 2.5.

## Modules

```
shared/domain     ← pure Kotlin: models, use cases, statistics, interfaces      (iOS, watchOS, JVM)
shared/data       ← SQLDelight store, implements the repository interfaces      (iOS, watchOS, JVM)
shared/export     ← report model, XLSX, PDF layout; iosMain draws with UIKit     (iOS, JVM)
shared/sync       ← phone ↔ watch sync rules and JSON payloads                   (iOS, watchOS, JVM)
shared/watchcore  ← MigraCore.framework: Swift facade for the watch             (watchOS)
composeApp        ← Compose UI, view models, iosMain platform services         (iOS) → ComposeApp.framework
iosApp/iosApp             ← SwiftUI host, App Intent, WatchConnectivity
iosApp/watchApp           ← watchOS app in SwiftUI
iosApp/watchComplications ← WidgetKit: the Log Migraine complication and a control
```

Nothing in `shared/domain`, `shared/data` or `shared/sync` depends on Compose or UIKit, so the watch links them without the UI.
The watch app and its extension link the same `MigraCore.framework`. `Brand.swift` and `OpenLoggerIntent.swift` in `watchApp` compile into both targets.

## Seams

| Interface | Where | iOS implementation | Later |
|---|---|---|---|
| `EpisodeRepository`, `ProfileRepository`, `CatalogRepository` | domain | `LocalStore` (SQLDelight) | The same code runs on Android and watchOS. |
| `SleepDataSource` | domain | `HealthKitSleepDataSource` | Health Connect on Android. |
| `PdfRenderer` / `ReportCanvas` | export | `UIKitPdfRenderer` (UIGraphicsPDFRenderer) | `android.graphics.pdf.PdfDocument`. The layout code stays shared. |
| `SystemDialogs` | composeApp | `UIKitDialogs`: UIAlertController, and a UIDatePicker in a sheet | Compose or platform dialogs. |
| `FileSharing` | composeApp | Quick Look and UIActivityViewController | Intent chooser with FileProvider. |
| `QuickLogEpisode` | domain | iPhone: `QuickLogBridge`. The watch logs through `WatchCore.log(intensity)`. | Android: a Quick Settings tile or a widget. |
| `PresetProvider` | domain | Phone: `GetLoggingPreset`. Watch: `WatchSync`, from the phone context | — |
| `SyncOutbox`, `DocumentStore` | domain | SQLDelight tables `sync_outbox` and `document` | Wear OS uses the same sync rules with the Data Layer API. |

`GetLoggingPreset` turns the baseline profile into the default form: the typical intensity and the acute medications of the treatment blueprint.
The Log Episode form starts from it, and quick logging saves it unchanged. The watch has no baseline of its own; it receives the preset from the phone. The watch logger starts at the preset intensity and keeps the preset medications (`WatchSync.draft`).

## Episodes and the calendar

- **One form, three uses.** `EpisodeFormViewModel` drives the Log tab, Edit Episode, and Log Episode for a day picked in the calendar. `EpisodeTarget` tells them apart.
- **Start time.** `EpisodeDraft.startedAt` is null for "now", so a new episode still starts when the user saves it. `LogEpisode` and `UpdateEpisode` move a future start back to now.
- **Edit.** `UpdateEpisode` keeps the id and the source. `EpisodeRepository.add` replaces the stored episode with the same id, and the phone context carries the new version to the watch.
- **Insights.** `InsightsCalculator` takes the browsed month. `CalendarBuilder` builds the month grid, which starts on Monday, and the preview of one day.

## Native feel

- **Navigation:** the iOS push transition, the edge swipe back, and a tab bar that stays under pushed screens.
- **System UI:** UIAlertController for alerts, prompts and action sheets. A UIDatePicker sheet for the episode start and for month and year. Quick Look for the PDF preview, and the system share sheet.
- **Scrolling and input:** the iOS overscroll bounce comes from Compose Multiplatform. Taps dim and shrink the control instead of a Material ripple.
- **Haptics:** selection, toggle and confirmation haptics through `LocalHapticFeedback`, which Compose maps to the UIKit feedback generators.
- **Accessibility:** VoiceOver labels and states on the custom controls. The pain scale is adjustable as a slider.
- **App chrome:** dark mode locked through `UIUserInterfaceStyle`, 120 Hz through `CADisableMinimumFrameDurationOnPhone`, and a dark launch screen.

## Export

- **XLSX:** a SpreadsheetML package in a STORED ZIP, written in common Kotlin, because Kotlin/Native has no `java.util.zip`. It uses shared strings, because older Apple Numbers versions show inline strings blank. Dates are real date cells.
- **PDF:** the page layout is common Kotlin and calls a `ReportCanvas`. On iOS the canvas draws with UIKit and the system font. A pure-Kotlin PDF with the standard fonts was the other option, but it cannot show Cyrillic, which users can type into custom triggers and names.

## Roadmap

### Android

1. Add the `androidTarget` through the `com.android.kotlin.multiplatform.library` plugin to `composeApp`. Put the activity in a new `androidApp` module.
2. Add `androidMain` drivers: the SQLDelight Android driver, `PdfDocument` for `PdfRenderer`, Health Connect for `SleepDataSource`, and FileProvider sharing.
3. The domain, data, export layout and the whole Compose UI are reused.

### Further watch work

- An iPhone widget extension with a `ControlWidget` would add Log Migraine to the iPhone Control Center and Lock Screen.
- If the Swift facade grows, SKIE would turn Kotlin `Flow` into `AsyncSequence`. Today the facade only exposes `suspend` functions, which Kotlin already maps to `async`.

## Apple Watch

### Screens

The watch app follows the three watch screens of the fourth Stitch export.

- `SummaryView`: the pain-free ring of the last 30 days and the **Log Migraine** button.
- `LoggerSheet`: a sheet with two steps. `LogEpisodeView` takes the intensity from the Digital Crown. After **Save Episode**, `EpisodeLoggedView` replaces it in the same sheet.
- `WatchModel.logger` holds the step. It is nil while the summary shows, and the app clears it when it goes to the background.

### Complications and the control

Watch complications are WidgetKit accessory widgets in four families: circular, corner, rectangular and inline. The app has one, "Log Migraine". Every surface opens the logger and saves nothing by itself:

| Surface | Implementation |
|---|---|
| Watch face and Smart Stack | `widgetURL(migratrack://log)`. The system opens the app with the URL, and `onOpenURL` shows the logger. |
| Control Center, Smart Stack, Action button (watchOS 26+) | `LogMigraineControl` runs `OpenLoggerIntent`. Its `supportedModes` is `.foreground`, so the system runs it in the app, which shows the logger. |

Why no button logs in place:

- On the watch face a button intent does not run; the system only opens the app.
- An intent that runs in the WidgetKit extension cannot use WatchConnectivity. Its episode would reach the phone only at the next app run. A log in the app goes to the phone at once.
- An accidental tap saves nothing, so the watch needs no Undo.

An `OpenURLIntent` that the extension returns does not open the app on watchOS 27 in the simulator. The intent therefore runs in the app.

Sources: WWDC24 "What's new in watchOS 11" (interactive widgets), WWDC25 session 334 (controls on watchOS 26), and Apple developer forum thread 734855 (on the watch face, buttons open the app).

### Sync

The rules live in `shared/sync`; Swift moves JSON strings over WatchConnectivity.

- **Phone → watch:** after every change the phone sends a `PhoneContext` with `updateApplicationContext`. It holds the preset and the episodes of the last 60 days. The system keeps only the newest one.
- **Watch → phone:** an episode logged on the watch goes into `sync_outbox`.
  - If the phone is reachable, the watch uses `sendMessage`. The phone replies with its new context, which confirms the episode at once.
  - Otherwise the watch queues the batch with `transferUserInfo`.
- **Confirmation:** an episode leaves the outbox only when a phone context lists it. A finished transfer is not proof, because the phone app may not have stored the batch yet. Until then the watch keeps the episode and sends it again, and the phone ignores ids it already has.
- **Delete requests:** builds before the redesign had Undo, which sent delete requests in `WatchBatch.deletedIds`. The phone still applies them, and the watch still sends a request that an earlier build stored. The current watch app does not delete episodes.
- **Delete and edit on the phone:** the phone remembers the last 200 ids it received from the watch and lists them in the context as `receivedIds`. A batch that arrives again cannot bring back an episode the user deleted on the phone, and the watch takes the list as confirmation. An edit reaches the watch with the next context.
- **Delete All Data on the phone:** the erase records its time, and the context sends it as `erasedAt`. The watch deletes its own episodes that started before it, also the ones in the outbox. The phone does not take in such episodes from a batch that was on its way.
- **Onboarding:** the context sends `isSetUp`. Until the phone finishes onboarding, the watch does not log and the phone takes in no batch. The watch keeps a newer episode in the outbox until then.
- **Database:** the watch app and the complication extension share one database in the App Group container, which `MigraTrackAppGroup` in Info.plist names.

`SyncTest` covers these cases with two in-memory databases.
