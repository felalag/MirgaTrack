import AppIntents
import ComposeApp

/// One-tap logging from Siri, Spotlight, the Shortcuts app, and the Action button.
/// It calls the same Kotlin use case that the Apple Watch complication will call.
struct LogMigraineIntent: AppIntent {
    static let title: LocalizedStringResource = "Log Migraine Episode"
    static let description = IntentDescription(
        "Logs an episode now with your typical pain intensity and acute medications from MigraTrack."
    )
    static let openAppWhenRun = false

    func perform() async throws -> some IntentResult & ProvidesDialog {
        let summary = try await QuickLogBridge.shared.logEpisode()
        return .result(dialog: "Logged a migraine episode: \(summary).")
    }
}

struct MigraTrackShortcuts: AppShortcutsProvider {
    static var appShortcuts: [AppShortcut] {
        AppShortcut(
            intent: LogMigraineIntent(),
            phrases: [
                "Log a migraine in \(.applicationName)",
                "Log migraine with \(.applicationName)",
            ],
            shortTitle: "Log Migraine",
            systemImageName: "brain.head.profile"
        )
    }
}
