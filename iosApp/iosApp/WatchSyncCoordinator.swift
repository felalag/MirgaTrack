import ComposeApp
import WatchConnectivity

/// Sends the preset and the recent episodes to the watch, and takes in the episodes logged there.
/// The sync rules live in Kotlin (PhoneSync); this class only moves the JSON strings.
final class WatchSyncCoordinator: NSObject, WCSessionDelegate {
    static let shared = WatchSyncCoordinator()

    private var latestContext: String?

    func start() {
        guard WCSession.isSupported() else { return }
        WCSession.default.delegate = self
        WCSession.default.activate()
        PhoneWatchBridge.shared.observeContext { [weak self] context in
            self?.latestContext = context
            self?.pushContext()
        }
    }

    /// The system keeps only the newest application context and delivers it when the watch wakes.
    private func pushContext() {
        let session = WCSession.default
        guard session.activationState == .activated, session.isPaired, session.isWatchAppInstalled,
              let context = latestContext else { return }
        try? session.updateApplicationContext(["context": context])
    }

    func session(_ session: WCSession, activationDidCompleteWith activationState: WCSessionActivationState, error: Error?) {
        DispatchQueue.main.async { self.pushContext() }
    }

    func sessionWatchStateDidChange(_ session: WCSession) {
        DispatchQueue.main.async { self.pushContext() }
    }

    func sessionDidBecomeInactive(_ session: WCSession) {}

    func sessionDidDeactivate(_ session: WCSession) {
        // The user switched to another watch; activate again to talk to it.
        session.activate()
    }

    /// The watch sends a batch this way while the phone is reachable; the reply confirms it at once.
    func session(_ session: WCSession, didReceiveMessage message: [String: Any], replyHandler: @escaping ([String: Any]) -> Void) {
        guard let batch = message["batch"] as? String else { return replyHandler([:]) }
        Task { @MainActor in
            let context = try? await PhoneWatchBridge.shared.receive(batch: batch)
            replyHandler(context.map { ["context": $0] } ?? [:])
        }
    }

    /// The queued path, for batches sent while the phone was out of reach.
    func session(_ session: WCSession, didReceiveUserInfo userInfo: [String: Any] = [:]) {
        guard let batch = userInfo["batch"] as? String else { return }
        Task { @MainActor in
            _ = try? await PhoneWatchBridge.shared.receive(batch: batch)
        }
    }
}
