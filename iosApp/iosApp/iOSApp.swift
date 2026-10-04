import SwiftUI

@main
struct iOSApp: App {
    init() {
        WatchSyncCoordinator.shared.start()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
                .ignoresSafeArea()
                .preferredColorScheme(.dark)
        }
    }
}
