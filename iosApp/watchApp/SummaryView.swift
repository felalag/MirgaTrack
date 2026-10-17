import MigraCore
import SwiftUI

/// The first screen: the pain-free days of the last 30 days and the way into the logger.
struct SummaryView: View {
    @Environment(WatchModel.self) private var model

    var body: some View {
        @Bindable var model = model
        NavigationStack {
            VStack(spacing: 4) {
                PainFreeDays(status: model.status)
                if let status = model.status, !status.isLinked {
                    Text("Open MigraTrack on your iPhone to sync.")
                        .font(.footnote)
                        .foregroundStyle(.secondary)
                        .multilineTextAlignment(.center)
                        .fixedSize(horizontal: false, vertical: true)
                }
            }
            .scenePadding(.horizontal)
            .bottomAction {
                Button("Log Migraine") {
                    model.showLogger()
                }
                .buttonStyle(.borderedProminent)
                .tint(.migraPink)
                .disabled(model.status?.isLinked == false)
            }
            .sheet(isPresented: $model.isLoggerShown) {
                LoggerSheet()
            }
        }
        .task { await model.refresh() }
    }
}

/// Days without an episode among the tracked days of the window, as a number over a linear gauge.
struct PainFreeDays: View {
    let status: WatchStatus?

    var body: some View {
        let days = status.map { Int($0.painFreeDays) }
        let window = status.map { Int($0.trackedDays) } ?? 30
        VStack(alignment: .leading, spacing: 4) {
            VStack(alignment: .leading, spacing: 2) {
                Text("Pain-free days")
                    .font(.footnote)
                    .foregroundStyle(.secondary)
                HStack(alignment: .firstTextBaseline, spacing: 0) {
                    Text(days.map(String.init) ?? "–")
                        .font(.largeTitle.bold())
                        .contentTransition(.numericText())
                    Text(" / \(window)")
                        .font(.title3)
                        .foregroundStyle(.secondary)
                }
                .monospacedDigit()
                .lineLimit(1)
                .minimumScaleFactor(0.5)
                Gauge(value: Double(days ?? 0), in: 0...Double(max(window, 1))) {}
                    .gaugeStyle(.linearCapacity)
                    .tint(.migraGreen)
            }
            .accessibilityElement(children: .ignore)
            .accessibilityLabel("Pain-free days")
            .accessibilityValue(days.map { String(localized: "\($0) of \(window)") } ?? "")
            if let status {
                Group {
                    if let last = status.lastEpisodeStartedAt {
                        Text("Last episode \(last, format: .dateTime.month(.abbreviated).day())")
                    } else {
                        Text("No episodes logged")
                    }
                }
                .font(.caption)
                .foregroundStyle(.secondary)
                .lineLimit(2)
                .minimumScaleFactor(0.8)
            }
        }
        .animation(.smooth, value: days)
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
    }
}

extension View {
    /// Puts the main action of a screen at the bottom of the safe area, so that the whole button stays clear of the
    /// round corners. The system bottom bar puts it 3 pt from the bottom edge of the 40mm SE display.
    func bottomAction(@ViewBuilder _ action: () -> some View) -> some View {
        safeAreaInset(edge: .bottom, spacing: 4, content: action)
    }
}
