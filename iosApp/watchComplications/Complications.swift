import AppIntents
import MigraCore
import SwiftUI
import WidgetKit

private let pink = Color(red: 1, green: 45 / 255, blue: 85 / 255)
private let green = Color(red: 48 / 255, green: 209 / 255, blue: 88 / 255)

struct StatusEntry: TimelineEntry {
    let date: Date
    let painFreeDays: Int
    let windowDays: Int
    let lastEpisodeDate: String?
    let presetIntensity: Int
    let presetMedications: String

    static let placeholder = StatusEntry(
        date: .now, painFreeDays: 26, windowDays: 30, lastEpisodeDate: "Oct 4",
        presetIntensity: 7, presetMedications: "Ibuprofen 400mg"
    )
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
            lastEpisodeDate: status.lastEpisodeDate,
            presetIntensity: Int(status.presetIntensity),
            presetMedications: status.presetMedications
        )
    }
}

// MARK: - Log Migraine

private let quickLogURL = URL(string: "migratrack://quicklog")

/// One tap logs an episode. In the Smart Stack the button runs the intent in place.
/// On the watch face the system only opens the app, so the widget URL tells the app to log at once.
struct QuickLogComplication: Widget {
    var body: some WidgetConfiguration {
        StaticConfiguration(kind: "QuickLog", provider: StatusProvider()) { entry in
            QuickLogView(entry: entry)
                .containerBackground(for: .widget) { Color.clear }
                .widgetURL(quickLogURL)
        }
        .configurationDisplayName("Log Migraine")
        .description("Logs an episode with your typical intensity in one tap.")
        .supportedFamilies([.accessoryCircular, .accessoryCorner, .accessoryRectangular, .accessoryInline])
    }
}

private struct QuickLogView: View {
    @Environment(\.widgetFamily) private var family
    let entry: StatusEntry

    var body: some View {
        switch family {
        case .accessoryCircular:
            Button(intent: LogMigraineIntent()) {
                ZStack {
                    AccessoryWidgetBackground()
                    VStack(spacing: 0) {
                        Image(systemName: "plus").font(.title3.weight(.bold))
                        Text("LOG").font(.system(size: 10, weight: .bold))
                    }
                    .foregroundStyle(pink)
                    .widgetAccentable()
                }
            }
            .buttonStyle(.plain)
            .accessibilityLabel("Log migraine episode")
        case .accessoryCorner:
            Button(intent: LogMigraineIntent()) {
                Image(systemName: "plus.circle.fill")
                    .font(.title.weight(.semibold))
                    .foregroundStyle(pink)
                    .widgetAccentable()
            }
            .buttonStyle(.plain)
            .widgetLabel("Log migraine")
        case .accessoryRectangular:
            Button(intent: LogMigraineIntent()) {
                HStack(spacing: 8) {
                    Image(systemName: "plus.circle.fill")
                        .font(.title2)
                        .foregroundStyle(pink)
                        .widgetAccentable()
                    VStack(alignment: .leading, spacing: 1) {
                        Text("Log Migraine").font(.headline)
                        Text(presetLine).font(.caption).foregroundStyle(.secondary).lineLimit(1)
                    }
                    Spacer(minLength: 0)
                }
            }
            .buttonStyle(.plain)
        default:
            // The inline family cannot hold a button; the widget URL does the work.
            Label("Log migraine", systemImage: "plus.circle")
        }
    }

    private var presetLine: String {
        entry.presetMedications.isEmpty
            ? "Intensity \(entry.presetIntensity)/10"
            : "\(entry.presetIntensity)/10 · \(entry.presetMedications)"
    }
}

// MARK: - Pain-Free Days

/// The 30-day ring of the Summary tab on the watch face. A tap opens the app.
struct PainFreeComplication: Widget {
    var body: some WidgetConfiguration {
        StaticConfiguration(kind: "PainFree", provider: StatusProvider()) { entry in
            PainFreeView(entry: entry)
                .containerBackground(for: .widget) { Color.clear }
                .widgetURL(URL(string: "migratrack://home"))
        }
        .configurationDisplayName("Pain-Free Days")
        .description("Days without a migraine in the last 30 days.")
        .supportedFamilies([.accessoryCircular, .accessoryCorner, .accessoryRectangular, .accessoryInline])
    }
}

private struct PainFreeView: View {
    @Environment(\.widgetFamily) private var family
    let entry: StatusEntry

    var body: some View {
        switch family {
        case .accessoryCircular:
            Gauge(value: Double(entry.painFreeDays), in: 0...Double(entry.windowDays)) {
                Image(systemName: "brain.head.profile")
            } currentValueLabel: {
                Text("\(entry.painFreeDays)")
            }
            .gaugeStyle(.accessoryCircular)
            .tint(green)
        case .accessoryCorner:
            Text("\(entry.painFreeDays)")
                .font(.title2.weight(.semibold))
                .widgetCurvesContent()
                .widgetLabel {
                    Gauge(value: Double(entry.painFreeDays), in: 0...Double(entry.windowDays)) {
                        Text("Pain-free")
                    }
                    .tint(green)
                }
        case .accessoryRectangular:
            VStack(alignment: .leading, spacing: 1) {
                Label("Pain-Free", systemImage: "brain.head.profile")
                    .font(.headline)
                    .foregroundStyle(green)
                    .widgetAccentable()
                Text("\(entry.painFreeDays) of \(entry.windowDays) days")
                Text(entry.lastEpisodeDate.map { "Last attack \($0)" } ?? "No attacks logged")
                    .foregroundStyle(.secondary)
            }
            .font(.caption)
            .frame(maxWidth: .infinity, alignment: .leading)
        default:
            Label("\(entry.painFreeDays)/\(entry.windowDays) pain-free", systemImage: "brain.head.profile")
        }
    }
}

// MARK: - Control

/// Control Center, the Smart Stack and the Action button of Apple Watch Ultra: one tap, no app launch.
@available(watchOS 26.0, *)
struct LogMigraineControl: ControlWidget {
    var body: some ControlWidgetConfiguration {
        StaticControlConfiguration(kind: "LogMigraineControl") {
            ControlWidgetButton(action: LogMigraineIntent()) {
                Label("Log Migraine", systemImage: "brain.head.profile")
            }
        }
        .displayName("Log Migraine")
        .description("Logs an episode with your typical intensity and medications.")
    }
}

@main
struct MigraTrackComplications: WidgetBundle {
    var body: some Widget {
        QuickLogComplication()
        PainFreeComplication()
        if #available(watchOS 26.0, *) {
            LogMigraineControl()
        }
    }
}
