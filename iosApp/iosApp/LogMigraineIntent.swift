import AppIntents
import ComposeApp

/// One-tap logging from Siri, Spotlight, the Shortcuts app, and the Action button.
/// It calls the same Kotlin use case that the Apple Watch complication will call.
struct LogMigraineIntent: ForegroundContinuableIntent {
    static let title: LocalizedStringResource = "Log Migraine Episode"
    static let description = IntentDescription(
        "Logs an episode now with your typical pain intensity and acute medications from MigraTrack."
    )
    static let openAppWhenRun = false

    /// Before onboarding there is no preset to log, so Siri offers to open the app instead.
    func perform() async throws -> some IntentResult & ProvidesDialog {
        guard let summary = try await QuickLogBridge.shared.logEpisode() else {
            throw needsToContinueInForegroundError("Finish setup in MigraTrack first.")
        }
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
