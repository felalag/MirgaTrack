import MigraCore
import SwiftUI

/// The first screen: the 30-day pain-free ring and the way into the logger.
struct SummaryView: View {
    @Environment(WatchModel.self) private var model

    var body: some View {
        @Bindable var model = model
        NavigationStack {
            VStack(spacing: 4) {
                PainFreeRing(status: model.status)
                if let status = model.status, !status.isLinked {
                    Text("Open MigraTrack on your iPhone to sync.")
                        .font(.footnote)
                        .foregroundStyle(.secondary)
                        .multilineTextAlignment(.center)
                        .fixedSize(horizontal: false, vertical: true)
                }
            }
            .bottomAction {
                Button("Log Migraine") {
                    model.showLogger()
                }
                .buttonStyle(.borderedProminent)
                .tint(.migraPink)
            }
            .sheet(isPresented: $model.isLoggerShown) {
                LoggerSheet()
            }
        }
        .task { await model.refresh() }
    }
}

/// Days without an episode in the window, drawn like an Activity ring.
struct PainFreeRing: View {
    let status: WatchStatus?

    var body: some View {
        let days = status.map { Int($0.painFreeDays) }
        let window = status.map { Int($0.windowDays) } ?? 30
        let progress = Double(days ?? 0) / Double(max(window, 1))
        GeometryReader { geometry in
            let side = RingMetrics.side(in: geometry.size)
            let line = RingMetrics.line(side: side)
            ZStack {
                Circle()
                    .stroke(Color.migraGreen.opacity(0.22), lineWidth: line)
                Circle()
                    .trim(from: 0, to: progress)
                    .stroke(Color.migraGreen, style: StrokeStyle(lineWidth: line, lineCap: .round))
                    .rotationEffect(.degrees(-90))
                    .shadow(color: .migraGreen.opacity(0.45), radius: line * 0.6)
                Text(days.map(String.init) ?? "–")
                    .font(.largeTitle.bold())
                    .monospacedDigit()
                    .contentTransition(.numericText())
                    .lineLimit(1)
                .minimumScaleFactor(0.5)
                .frame(width: RingMetrics.textSide(side: side), height: RingMetrics.textSide(side: side))
            }
            .padding(line / 2)
            .frame(width: side, height: side)
            .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
        .animation(.smooth, value: days)
        .accessibilityElement(children: .ignore)
        .accessibilityLabel("Pain-free days")
        .accessibilityValue(days.map { "\($0) of \(window)" } ?? "")
    }
}

/// Sizes that the pain-free ring, the intensity gauge and the checkmark share, so that the three screens match.
enum RingMetrics {
    /// The same share of the screen width on both screens. 0.33 is the most that the gauge gets on the 49mm watch,
    /// between the close button and its label.
    static func side(in size: CGSize) -> CGFloat {
        min(size.width * 0.33, size.height)
    }

    static func line(side: CGFloat) -> CGFloat {
        side * 0.06
    }

    /// The largest square inside the ring, where large Dynamic Type sizes shrink the text to fit.
    static func textSide(side: CGFloat) -> CGFloat {
        (side - 2 * line(side: side)) * 0.7
    }
}

extension View {
    /// Puts the main action of a screen at the bottom of the safe area, so that the whole button stays clear of the
    /// round corners. The system bottom bar puts it 3 pt from the bottom edge of the 40mm SE display.
    func bottomAction(@ViewBuilder _ action: () -> some View) -> some View {
        safeAreaInset(edge: .bottom, spacing: 4, content: action)
    }
}
