import AppIntents

/// The action of the Log Migraine control. Both targets compile this file: the control in the extension
/// names the intent, and the foreground mode makes the system run it in the app.
@available(watchOS 26.0, *)
struct OpenLoggerIntent: AppIntent {
    static let title: LocalizedStringResource = "Log Migraine"
    static let isDiscoverable = false
    static let supportedModes: IntentModes = .foreground

    @MainActor
    func perform() async throws -> some IntentResult {
        LoggerLauncher.open()
        return .result()
    }
}

/// The app sets this at launch. The extension never sets it, because it never runs the intent.
@MainActor
enum LoggerLauncher {
    static var open: () -> Void = {}
}
