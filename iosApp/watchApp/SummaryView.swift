import MigraCore
import SwiftUI

/// The first screen: the 30-day pain-free ring and the way into the logger.
struct SummaryView: View {
    @Environment(WatchModel.self) private var model

    var body: some View {
        @Bindable var model = model
        NavigationStack {
            VStack(spacing: 8) {
                PainFreeRing(status: model.status)
                    .frame(maxHeight: .infinity)
                caption
                Button {
                    model.showLogger()
                } label: {
                    Label {
                        Text("Log Migraine")
                    } icon: {
                        Image(systemName: "plus.circle.fill")
                            .symbolRenderingMode(.palette)
                            .foregroundStyle(Color.migraPink, .white)
                    }
                    .frame(maxWidth: .infinity)
                }
                .buttonStyle(.borderedProminent)
                .tint(.migraPink)
            }
            .ignoresSafeArea(edges: .bottom)
            .dynamicTypeSize(...DynamicTypeSize.accessibility2)
            .sheet(isPresented: $model.isLoggerShown) {
                LoggerSheet()
            }
        }
        .task { await model.refresh() }
    }

    private var caption: some View {
        Group {
            if let status = model.status, !status.isLinked {
                Text("Open MigraTrack on your iPhone to sync.")
            } else if let status = model.status {
                Text("\(status.painFreeDays) of \(status.windowDays) days pain-free")
            }
        }
        .font(.footnote)
        .foregroundStyle(.secondary)
        .multilineTextAlignment(.center)
        .fixedSize(horizontal: false, vertical: true)
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
            let side = min(geometry.size.width, geometry.size.height)
            let line = side * 0.1
            ZStack {
                Circle()
                    .stroke(Color.migraGreen.opacity(0.22), lineWidth: line)
                Circle()
                    .trim(from: 0, to: progress)
                    .stroke(Color.migraGreen, style: StrokeStyle(lineWidth: line, lineCap: .round))
                    .rotationEffect(.degrees(-90))
                    .shadow(color: .migraGreen.opacity(0.45), radius: line * 0.6)
                VStack(spacing: 0) {
                    Text(days.map(String.init) ?? "–")
                        .font(.system(size: side * 0.3, weight: .heavy, design: .rounded))
                        .contentTransition(.numericText())
                    Text("/\(window)d")
                        .font(.system(size: side * 0.11, weight: .medium, design: .rounded))
                        .foregroundStyle(.secondary)
                }
            }
            .padding(line / 2)
            .frame(width: side, height: side)
            .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
        .animation(.smooth, value: days)
        // The caption under the ring says the same in words.
        .accessibilityHidden(true)
    }
}
