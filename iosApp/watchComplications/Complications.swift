import MigraCore
import SwiftUI
import WidgetKit

struct StatusEntry: TimelineEntry {
    let date: Date
    let painFreeDays: Int
    let windowDays: Int
    let isLinked: Bool

    static let placeholder = StatusEntry(date: .now, painFreeDays: 26, windowDays: 30, isLinked: true)
}

/// Reads the shared database through MigraCore. Pain-free days change at midnight, so the timeline ends there.
struct StatusProvider: TimelineProvider {
    func placeholder(in context: Context) -> StatusEntry { .placeholder }

    func getSnapshot(in context: Context, completion: @escaping (StatusEntry) -> Void) {
        Task { @MainActor in completion(await Self.load()) }
    }

    func getTimeline(in context: Context, completion: @escaping (Timeline<StatusEntry>) -> Void) {
        Task { @MainActor in
            let entry = await Self.load()
            let tomorrow = Calendar.current.startOfDay(for: .now).addingTimeInterval(24 * 60 * 60 + 60)
            completion(Timeline(entries: [entry], policy: .after(tomorrow)))
        }
    }

    @MainActor
    private static func load() async -> StatusEntry {
        guard let status = try? await WatchCore.shared.status() else { return .placeholder }
        return StatusEntry(
            date: .now,
            painFreeDays: Int(status.painFreeDays),
            windowDays: Int(status.windowDays),
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
                if entry.isLinked {
                    Gauge(value: Double(entry.painFreeDays), in: 0...Double(max(entry.windowDays, 1))) {}
                        .gaugeStyle(.accessoryLinearCapacity)
                        .tint(Color.migraGreen)
                    Text("\(entry.painFreeDays) of \(entry.windowDays) days pain-free")
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
