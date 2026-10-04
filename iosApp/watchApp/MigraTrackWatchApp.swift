import SwiftUI

@main
struct MigraTrackWatchApp: App {
    @State private var model = WatchModel()
    @Environment(\.scenePhase) private var scenePhase

    init() {
        PhoneConnection.shared.start()
    }

    var body: some Scene {
        WindowGroup {
            HomeView()
                .environment(model)
                .onOpenURL { url in
                    // Complications on the watch face can only open the app, so the URL says what to do.
                    if url.host == "quicklog" {
                        Task { await model.quickLog() }
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
                model.sheet = nil
            default:
                break
            }
        }
    }
}
