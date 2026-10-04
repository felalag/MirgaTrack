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
iosApp/watchComplications ← WidgetKit: complications and a control
```

Nothing in `shared/domain`, `shared/data` or `shared/sync` depends on Compose or UIKit, so the watch links them without the UI.
The watch app and its extension link the same `MigraCore.framework`. `LogMigraineIntent.swift` in `watchApp` compiles into both targets.

## Seams

| Interface | Where | iOS implementation | Later |
|---|---|---|---|
| `EpisodeRepository`, `ProfileRepository`, `CatalogRepository` | domain | `LocalStore` (SQLDelight) | The same code runs on Android and watchOS. |
| `SleepDataSource` | domain | `HealthKitSleepDataSource` | Health Connect on Android. |
| `PdfRenderer` / `ReportCanvas` | export | `UIKitPdfRenderer` (UIGraphicsPDFRenderer) | `android.graphics.pdf.PdfDocument`. The layout code stays shared. |
| `SystemDialogs` | composeApp | `UIKitDialogs` (UIAlertController) | Compose or platform dialogs. |
| `FileSharing` | composeApp | Quick Look and UIActivityViewController | Intent chooser with FileProvider. |
| `QuickLogEpisode` | domain | iPhone: `QuickLogBridge`. Watch: `WatchCore.quickLog()` | Android: a Quick Settings tile or a widget. |
| `PresetProvider` | domain | Phone: `GetLoggingPreset`. Watch: `WatchSync`, from the phone context | — |
| `SyncOutbox`, `DocumentStore` | domain | SQLDelight tables `sync_outbox` and `document` | Wear OS uses the same sync rules with the Data Layer API. |

`GetLoggingPreset` turns the baseline profile into the default form: the typical intensity and the acute medications of the treatment blueprint.
The Log Episode form starts from it, and quick logging saves it unchanged. The watch has no baseline of its own; it receives the preset from the phone.

## Native feel

- **Navigation:** the iOS push transition, the edge swipe back, and a tab bar that stays under pushed screens.
- **System UI:** UIAlertController for alerts, prompts and action sheets. Quick Look for the PDF preview, and the system share sheet.
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
- A log from the Smart Stack or a control runs in the WidgetKit extension, which cannot use WatchConnectivity. That episode reaches the phone the next time the watch app runs.

## Apple Watch

### Complications and quick logging

Watch complications are WidgetKit accessory widgets in four families: circular, corner, rectangular and inline. What a tap can do depends on where the widget is:

| Surface | Tap result | Implementation |
|---|---|---|
| Watch face | The system opens the app. `Button(intent:)` does not run there. | `widgetURL(migratrack://quicklog)`. The app saves the preset at once and shows Undo. |
| Smart Stack (watchOS 11+) | The intent runs without opening the app. | `Button(intent: LogMigraineIntent())` in the same view. |
| Control Center, Smart Stack, Action button (watchOS 26+) | The intent runs without opening the app. | `LogMigraineControl`, a `ControlWidget`. |

Sources: WWDC24 "What's new in watchOS 11" (interactive widgets), WWDC25 session 334 (controls on watchOS 26), and Apple developer forum thread 734855 (on the watch face, buttons open the app).

### Sync

The rules live in `shared/sync`; Swift moves JSON strings over WatchConnectivity.

- **Phone → watch:** after every change the phone sends a `PhoneContext` with `updateApplicationContext`. It holds the preset and the episodes of the last 60 days. The system keeps only the newest one.
- **Watch → phone:** an episode logged on the watch goes into `sync_outbox`.
  - If the phone is reachable, the watch uses `sendMessage`. The phone replies with its new context, which confirms the episode at once.
  - Otherwise the watch queues the batch with `transferUserInfo`.
- **Confirmation:** an episode leaves the outbox only when a phone context lists it. A finished transfer is not proof, because the phone app may not have stored the batch yet. Until then the watch keeps the episode and sends it again, and the phone ignores ids it already has.
- **Undo:** the watch deletes the episode and sends a delete request, even if the episode was never confirmed. Its batch may already be on the way, and the phone ignores unknown ids.
- **Database:** the watch app and the complication extension share one database in the App Group container, which `MigraTrackAppGroup` in Info.plist names.

`SyncTest` covers these cases with two in-memory databases.
