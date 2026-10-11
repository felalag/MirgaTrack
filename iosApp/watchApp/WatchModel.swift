import MigraCore
import Observation
import WatchKit
import WidgetKit

/// State of the watch app for SwiftUI. MigraCore (Kotlin) owns the data and the rules.
@MainActor
@Observable
final class WatchModel {
    enum LoggerStep {
        case intensity
        case logged(WatchEpisode)
    }

    var status: WatchStatus?
    /// The logger sheet over the summary; nil when it is closed.
    var logger: LoggerStep?
    private(set) var isSaving = false

    var isLoggerShown: Bool {
        get { logger != nil }
        set { if !newValue { logger = nil } }
    }

    func refresh() async {
        status = try? await WatchCore.shared.status()
    }

    func showLogger() {
        logger = .intensity
    }

    func save(intensity: Int) async {
        guard !isSaving else { return }
        isSaving = true
        defer { isSaving = false }
        do {
            logger = .logged(try await WatchCore.shared.log(intensity: Int32(intensity)))
            WKInterfaceDevice.current().play(.success)
            await refresh()
            WidgetCenter.shared.reloadAllTimelines()
            PhoneConnection.shared.flush()
        } catch {
            WKInterfaceDevice.current().play(.failure)
        }
    }
}
