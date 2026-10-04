import MigraCore
import WatchConnectivity
import WidgetKit

/// The watch end of WatchConnectivity: applies the phone's context and sends the outbox.
/// The Smart Stack widget and the control log in the extension, which has no WatchConnectivity; their episodes leave with the next app run.
final class PhoneConnection: NSObject, WCSessionDelegate {
    static let shared = PhoneConnection()

    /// Called on the main thread after a context from the phone was applied.
    var onContextApplied: (() -> Void)?

    func start() {
        guard WCSession.isSupported() else { return }
        WCSession.default.delegate = self
        WCSession.default.activate()
    }

    /// Sends the outbox. A reachable phone answers with its new context, which confirms the episodes at once.
    /// Otherwise the batch is queued; episodes stay in the outbox until a context lists them, so a lost batch is sent again.
    func flush() {
        Task { @MainActor in
            let session = WCSession.default
            guard session.activationState == .activated,
                  let batch = try? await WatchCore.shared.pendingBatch() else { return }
            if session.isReachable {
                session.sendMessage(["batch": batch], replyHandler: { reply in
                    self.delivered(batch)
                    if let context = reply["context"] as? String { self.apply(context: context) }
                }, errorHandler: { _ in
                    self.queue(batch)
                })
            } else {
                self.queue(batch)
            }
        }
    }

    /// One queued transfer at a time, so the same batch does not pile up.
    private func queue(_ batch: String) {
        let session = WCSession.default
        guard session.outstandingUserInfoTransfers.isEmpty else { return }
        session.transferUserInfo(["batch": batch])
    }

    private func delivered(_ batch: String) {
        Task { @MainActor in try? await WatchCore.shared.markDelivered(batch: batch) }
    }

    func session(_ session: WCSession, activationDidCompleteWith activationState: WCSessionActivationState, error: Error?) {
        if let context = session.receivedApplicationContext["context"] as? String { apply(context: context) }
        flush()
    }

    func session(_ session: WCSession, didReceiveApplicationContext applicationContext: [String: Any]) {
        if let context = applicationContext["context"] as? String { apply(context: context) }
    }

    func sessionReachabilityDidChange(_ session: WCSession) {
        if session.isReachable { flush() }
    }

    func session(_ session: WCSession, didFinish userInfoTransfer: WCSessionUserInfoTransfer, error: Error?) {
        guard error == nil, let batch = userInfoTransfer.userInfo["batch"] as? String else { return }
        delivered(batch)
    }

    private func apply(context: String) {
        Task { @MainActor in
            try? await WatchCore.shared.applyPhoneContext(json: context)
            WidgetCenter.shared.reloadAllTimelines()
            self.onContextApplied?()
        }
    }
}
