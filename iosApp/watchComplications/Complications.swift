import MigraCore
import SwiftUI
import WidgetKit

struct StatusEntry: TimelineEntry {
    let date: Date
    /// Nil when the shared database cannot be read.
    let painFreeDays: Int?
    let trackedDays: Int
    let isLinked: Bool

    /// Sample data for the widget gallery only. A real watch face must not show days that the user never logged.
    static let placeholder = StatusEntry(date: .now, painFreeDays: 26, trackedDays: 30, isLinked: true)
    static let unavailable = StatusEntry(date: .now, painFreeDays: nil, trackedDays: 30, isLinked: true)
}

/// Reads the shared database through MigraCore. Pain-free days change at midnight, so the timeline ends there.
struct StatusProvider: TimelineProvider {
    func placeholder(in context: Context) -> StatusEntry { .placeholder }

    func getSnapshot(in context: Context, completion: @escaping (StatusEntry) -> Void) {
        if context.isPreview { return completion(.placeholder) }
        Task { @MainActor in completion(await Self.load()) }
    }

    func getTimeline(in context: Context, completion: @escaping (Timeline<StatusEntry>) -> Void) {
        Task { @MainActor in
            let entry = await Self.load()
            let tomorrow = Calendar.current.startOfDay(for: .now).addingTimeInterval(24 * 60 * 60 + 60)
            let retry = Date.now.addingTimeInterval(15 * 60)
            completion(Timeline(entries: [entry], policy: .after(entry.painFreeDays == nil ? retry : tomorrow)))
        }
    }

    @MainActor
    private static func load() async -> StatusEntry {
        guard let status = try? await WatchCore.shared.status() else { return .unavailable }
        return StatusEntry(
            date: .now,
            painFreeDays: Int(status.painFreeDays),
            trackedDays: Int(status.trackedDays),
            isLinked: status.isLinked
        )
    }
}

// MARK: - Log Migraine

/// A tap opens the logger, on the watch face and in the Smart Stack alike.
/// The watch face cannot run a button's intent, and a log through the app reaches the iPhone at once.
struct LogMigraineComplication: Widget {
    var body: some WidgetConfiguration {
        StaticConfiguration(kind: "LogMigraine", provider: StatusProvider()) { entry in
            LogMigraineView(entry: entry)
                .containerBackground(for: .widget) { Color.clear }
                .widgetURL(.logger)
        }
        .configurationDisplayName("Log Migraine")
        .description("Opens the logger. The rectangular style also shows your pain-free days.")
        .supportedFamilies([.accessoryCircular, .accessoryCorner, .accessoryRectangular, .accessoryInline])
    }
}

private struct LogMigraineView: View {
    @Environment(\.widgetFamily) private var family
    let entry: StatusEntry

    var body: some View {
        switch family {
        case .accessoryCircular:
            ZStack {
                AccessoryWidgetBackground()
                VStack(spacing: 0) {
                    Image(systemName: "plus").font(.title3.weight(.semibold))
                    Text("LOG")
                }
                .foregroundStyle(Color.migraPink)
                .widgetAccentable()
            }
            .accessibilityElement(children: .ignore)
            .accessibilityLabel("Log Migraine")
        case .accessoryCorner:
            Image(systemName: "plus.circle.fill")
                .font(.title.weight(.semibold))
                .foregroundStyle(Color.migraPink)
                .widgetAccentable()
                .widgetLabel("Log Migraine")
        case .accessoryRectangular:
            VStack(alignment: .leading, spacing: 2) {
                Label {
                    Text("Log Migraine")
                } icon: {
                    Image(systemName: "plus.circle.fill").foregroundStyle(Color.migraPink)
                }
                .font(.headline)
                .widgetAccentable()
                if entry.isLinked, let days = entry.painFreeDays {
                    Gauge(value: Double(days), in: 0...Double(max(entry.trackedDays, 1))) {}
                        .gaugeStyle(.accessoryLinearCapacity)
                        .tint(Color.migraGreen)
                    Text("\(days) of \(entry.trackedDays) days pain-free")
                        .font(.caption)
                        .foregroundStyle(.secondary)
                } else if entry.isLinked {
                    Text("Pain-free days –")
                        .font(.caption)
                        .foregroundStyle(.secondary)
                } else {
                    Text("Open MigraTrack on iPhone")
                        .font(.caption)
                        .foregroundStyle(.secondary)
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
        default:
            Label("Log Migraine", systemImage: "plus.circle")
        }
    }
}

// MARK: - Control

/// Control Center, the Smart Stack and the Action button of Apple Watch Ultra: a tap opens the logger.
@available(watchOS 26.0, *)
struct LogMigraineControl: ControlWidget {
    var body: some ControlWidgetConfiguration {
        StaticControlConfiguration(kind: "LogMigraineControl") {
            ControlWidgetButton(action: OpenLoggerIntent()) {
                Label("Log Migraine", systemImage: "brain.head.profile")
            }
        }
        .displayName("Log Migraine")
        .description("Opens the logger.")
    }
}

@main
struct MigraTrackComplications: WidgetBundle {
    var body: some Widget {
        LogMigraineComplication()
        if #available(watchOS 26.0, *) {
            LogMigraineControl()
        }
    }
}
