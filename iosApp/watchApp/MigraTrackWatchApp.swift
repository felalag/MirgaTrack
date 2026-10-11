import SwiftUI

@main
struct MigraTrackWatchApp: App {
    @State private var model: WatchModel
    @Environment(\.scenePhase) private var scenePhase

    init() {
        let model = WatchModel()
        _model = State(initialValue: model)
        LoggerLauncher.open = { model.showLogger() }
        PhoneConnection.shared.start()
    }

    var body: some Scene {
        WindowGroup {
            SummaryView()
                .environment(model)
                .onOpenURL { url in
                    if url.host == URL.logger.host {
                        model.showLogger()
                    }
                }
                .task {
                    PhoneConnection.shared.onContextApplied = { [model] in
                        Task { await model.refresh() }
                    }
                }
        }
        .onChange(of: scenePhase) { _, phase in
            switch phase {
            case .active:
                Task { await model.refresh() }
                PhoneConnection.shared.flush()
            case .background:
                // The next visit starts on the summary, as Apple's own watch apps do.
                model.logger = nil
            default:
                break
            }
        }
    }
}
