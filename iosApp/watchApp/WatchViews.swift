import MigraCore
import SwiftUI

extension Color {
    static let migraPink = Color(red: 1, green: 45 / 255, blue: 85 / 255)
    static let migraGreen = Color(red: 48 / 255, green: 209 / 255, blue: 88 / 255)
    static let migraOrange = Color(red: 1, green: 159 / 255, blue: 10 / 255)

    /// The severity colors of the specification: 1–3 green, 4–7 orange, 8–10 pink.
    static func severity(_ intensity: Int) -> Color {
        switch intensity {
        case ...3: .migraGreen
        case 4...7: .migraOrange
        default: .migraPink
        }
    }
}

struct HomeView: View {
    @Environment(WatchModel.self) private var model

    var body: some View {
        @Bindable var model = model
        NavigationStack {
            ScrollView {
                VStack(spacing: 10) {
                    PainFreeRing(status: model.status)
                        .frame(width: 92, height: 92)
                    if let status = model.status {
                        Text("\(status.painFreeDays) of \(status.windowDays) days pain-free")
                            .font(.footnote)
                            .foregroundStyle(.secondary)
                    }
                    Button {
                        model.sheet = .logger
                    } label: {
                        Label("Log Migraine", systemImage: "plus.circle.fill")
                            .frame(maxWidth: .infinity)
                    }
                    .buttonStyle(.borderedProminent)
                    .tint(.migraPink)
                    if let status = model.status {
                        StatusRows(status: status)
                    }
                }
            }
            .navigationTitle("MigraTrack")
            .sheet(item: $model.sheet) { sheet in
                switch sheet {
                case .logger: LogView()
                case .logged(let episode): LoggedView(episode: episode)
                }
            }
        }
        .task { await model.refresh() }
    }
}

private struct StatusRows: View {
    let status: WatchStatus

    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            row("This month", value: "\(status.attacksThisMonth) \(status.attacksThisMonth == 1 ? "attack" : "attacks")")
            row("Last attack", value: status.lastEpisodeDate ?? "None")
            if !status.isLinked {
                Text("Open MigraTrack on your iPhone to bring over your typical intensity and medications.")
                    .font(.footnote)
                    .foregroundStyle(.secondary)
                    .padding(.top, 4)
            }
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }

    private func row(_ title: String, value: String) -> some View {
        HStack {
            Text(title).foregroundStyle(.secondary)
            Spacer()
            Text(value).fontWeight(.semibold)
        }
        .font(.footnote)
    }
}

/// The 30-day ring of the Summary tab: the share of days without an episode.
struct PainFreeRing: View {
    let status: WatchStatus?

    var body: some View {
        let progress = status.map { Double($0.painFreeDays) / Double(max($0.windowDays, 1)) } ?? 0
        ZStack {
            Circle().stroke(Color.migraGreen.opacity(0.2), lineWidth: 10)
            Circle()
                .trim(from: 0, to: progress)
                .stroke(Color.migraGreen, style: StrokeStyle(lineWidth: 10, lineCap: .round))
                .rotationEffect(.degrees(-90))
                .shadow(color: .migraGreen.opacity(0.5), radius: 4)
            VStack(spacing: 0) {
                Text(status.map { "\($0.painFreeDays)" } ?? "–")
                    .font(.system(size: 30, weight: .bold, design: .rounded))
                Text("/\(status?.windowDays ?? 30)d")
                    .font(.caption2)
                    .foregroundStyle(.secondary)
            }
        }
        .accessibilityElement(children: .ignore)
        .accessibilityLabel(status.map { "\($0.painFreeDays) of \($0.windowDays) days pain-free" } ?? "Loading")
    }
}

/// Pick the intensity with the Digital Crown or the buttons, then save.
struct LogView: View {
    @Environment(WatchModel.self) private var model
    @State private var intensity: Double = 7

    private var level: Int { Int(intensity.rounded()) }

    var body: some View {
        ScrollView {
            VStack(spacing: 8) {
                Text("PAIN INTENSITY")
                    .font(.caption2.weight(.semibold))
                    .foregroundStyle(.secondary)
                HStack {
                    Button {
                        intensity = max(1, intensity - 1)
                    } label: {
                        Image(systemName: "minus")
                    }
                    .accessibilityLabel("Less")
                    Text("\(level)")
                        .font(.system(size: 46, weight: .bold, design: .rounded))
                        .foregroundStyle(Color.severity(level))
                        .frame(minWidth: 60)
                        .focusable()
                        .digitalCrownRotation($intensity, from: 1, through: 10, by: 1, sensitivity: .low, isContinuous: false, isHapticFeedbackEnabled: true)
                        .accessibilityLabel("Intensity \(level) of 10")
                    Button {
                        intensity = min(10, intensity + 1)
                    } label: {
                        Image(systemName: "plus")
                    }
                    .accessibilityLabel("More")
                }
                .buttonStyle(.bordered)
                if let medications = model.status?.presetMedications, !medications.isEmpty {
                    Label(medications, systemImage: "pills.fill")
                        .font(.footnote)
                        .foregroundStyle(Color.migraGreen)
                }
                Button("Save Episode") {
                    Task { await model.log(intensity: level) }
                }
                .buttonStyle(.borderedProminent)
                .tint(.migraPink)
            }
        }
        .onAppear { intensity = Double(model.status?.presetIntensity ?? 7) }
    }
}

/// Shown after every log, from the app or from the complication, with a way to take it back.
struct LoggedView: View {
    @Environment(WatchModel.self) private var model
    @Environment(\.dismiss) private var dismiss
    let episode: WatchEpisode

    var body: some View {
        ScrollView {
            VStack(spacing: 8) {
                Image(systemName: "checkmark.circle.fill")
                    .font(.system(size: 40))
                    .foregroundStyle(Color.migraGreen)
                Text("Episode logged").font(.headline)
                Text(episode.summary)
                    .font(.footnote)
                    .foregroundStyle(.secondary)
                    .multilineTextAlignment(.center)
                Button("Done") { dismiss() }
                    .buttonStyle(.borderedProminent)
                    .tint(.migraPink)
                Button("Undo", role: .destructive) {
                    Task { await model.undo(episode) }
                }
            }
        }
    }
}
