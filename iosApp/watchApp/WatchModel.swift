import MigraCore
import Observation
import WatchKit
import WidgetKit

/// State of the watch app for SwiftUI. MigraCore (Kotlin) owns the data and the rules.
@MainActor
@Observable
final class WatchModel {
    enum Sheet: Identifiable {
        case logger
        case logged(WatchEpisode)

        var id: String {
            switch self {
            case .logger: "logger"
            case .logged(let episode): episode.id
            }
        }
    }

    var status: WatchStatus?
    var sheet: Sheet?

    func refresh() async {
        status = try? await WatchCore.shared.status()
    }

    /// The Log Migraine complication opens the app with migratrack://quicklog: the episode is saved at once,
    /// and the confirmation offers Undo in case the tap was an accident.
    func quickLog() async {
        do {
            sheet = .logged(try await WatchCore.shared.quickLog())
            WKInterfaceDevice.current().play(.success)
            await dataChanged()
        } catch {
            WKInterfaceDevice.current().play(.failure)
        }
    }

    func log(intensity: Int) async {
        do {
            sheet = .logged(try await WatchCore.shared.log(intensity: Int32(intensity)))
            WKInterfaceDevice.current().play(.success)
            await dataChanged()
        } catch {
            WKInterfaceDevice.current().play(.failure)
        }
    }

    func undo(_ episode: WatchEpisode) async {
        try? await WatchCore.shared.undo(episodeId: episode.id)
        sheet = nil
        WKInterfaceDevice.current().play(.retry)
        await dataChanged()
    }

    private func dataChanged() async {
        await refresh()
        WidgetCenter.shared.reloadAllTimelines()
        PhoneConnection.shared.flush()
    }
}
