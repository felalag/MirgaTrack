import AppIntents
import MigraCore
import WidgetKit

/// One-tap logging on the watch. The Smart Stack widget and the control run it in the extension;
/// Siri and the Shortcuts app run it in the watch app. Both targets compile this file.
struct LogMigraineIntent: AppIntent {
    static let title: LocalizedStringResource = "Log Migraine Episode"
    static let description = IntentDescription(
        "Logs an episode now with the typical intensity and acute medications from MigraTrack on your iPhone."
    )
    static let openAppWhenRun = false

    func perform() async throws -> some IntentResult & ProvidesDialog {
        let episode = try await WatchCore.shared.quickLog()
        WidgetCenter.shared.reloadAllTimelines()
        return .result(dialog: "Logged a migraine episode: \(episode.summary).")
    }
}
