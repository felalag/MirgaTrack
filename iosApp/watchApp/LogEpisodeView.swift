import MigraCore
import SwiftUI

/// The sheet behind Log Migraine: Pain Intensity, then Episode logged. watchOS draws the close button.
struct LoggerSheet: View {
    @Environment(WatchModel.self) private var model

    var body: some View {
        NavigationStack {
            if case .logged(let episode) = model.logger {
                EpisodeLoggedView(episode: episode)
                    .transition(.blurReplace)
            } else {
                LogEpisodeView()
                    .transition(.blurReplace)
            }
        }
        .animation(.smooth, value: isLogged)
    }

    private var isLogged: Bool {
        if case .logged = model.logger { true } else { false }
    }
}

/// Pain intensity on the Digital Crown, then Save Episode. It starts at the typical intensity from the iPhone.
struct LogEpisodeView: View {
    @Environment(WatchModel.self) private var model
    @State private var crown = 7.0
    @State private var hasPreset = false
    @FocusState private var isGaugeFocused: Bool

    private var level: Int { Int(crown.rounded()) }

    var body: some View {
        VStack(spacing: 6) {
            Text("Pain Intensity")
                .textCase(.uppercase)
                .font(.caption2.weight(.semibold))
                .tracking(1.5)
                .foregroundStyle(.secondary)
            IntensityGauge(level: level)
                .frame(maxHeight: .infinity)
                .focusable()
                .focusEffectDisabled()
                .focused($isGaugeFocused)
                .digitalCrownRotation(
                    $crown, from: 1, through: 10, by: 1,
                    sensitivity: .low, isContinuous: false, isHapticFeedbackEnabled: true
                )
                .accessibilityElement(children: .ignore)
                .accessibilityLabel("Pain intensity")
                .accessibilityValue("\(level) out of 10")
                .accessibilityAdjustableAction { direction in
                    switch direction {
                    case .increment: crown = min(10, crown.rounded() + 1)
                    case .decrement: crown = max(1, crown.rounded() - 1)
                    @unknown default: break
                    }
                }
            Label {
                Text("Turn Digital Crown").foregroundStyle(.secondary)
            } icon: {
                Image(systemName: "digitalcrown.arrow.clockwise").foregroundStyle(Color.severity(level))
            }
            .font(.caption2)
            .imageScale(.small)
            .padding(.horizontal, 8)
            .padding(.vertical, 2)
            .background(.white.opacity(0.08), in: Capsule())
            .accessibilityHidden(true)
            Button("Save Episode") {
                Task { await model.save(intensity: level) }
            }
            .buttonStyle(.borderedProminent)
            .tint(.migraPink)
            .disabled(model.isSaving)
        }
        .ignoresSafeArea(edges: .bottom)
        .dynamicTypeSize(...DynamicTypeSize.xxxLarge)
        .presentationBackground { Glow(color: .severity(level)) }
        .animation(.smooth, value: level)
        .onAppear {
            isGaugeFocused = true
            startAtPreset()
        }
        // A tap on the complication can open the logger before the status has loaded.
        .onChange(of: model.status == nil) { startAtPreset() }
    }

    private func startAtPreset() {
        guard !hasPreset, let status = model.status else { return }
        crown = Double(status.presetIntensity)
        hasPreset = true
    }
}

/// The dark backdrop of the logger with a soft glow in the middle. An opaque backdrop keeps the summary's ring
/// and button from showing through the sheet.
struct Glow: View {
    let color: Color

    var body: some View {
        RadialGradient(colors: [color.opacity(0.28), .black], center: .center, startRadius: 0, endRadius: 130)
    }
}

/// A 270° arc open at the bottom, the shape watchOS uses for its own circular gauges.
private struct IntensityGauge: View {
    let level: Int

    var body: some View {
        let color = Color.severity(level)
        GeometryReader { geometry in
            let side = min(geometry.size.width, geometry.size.height)
            let line = side * 0.06
            ZStack {
                Circle()
                    .trim(from: 0, to: 0.75)
                    .stroke(.white.opacity(0.14), style: StrokeStyle(lineWidth: line, lineCap: .round))
                Circle()
                    .trim(from: 0, to: 0.75 * Double(level) / 10)
                    .stroke(color, style: StrokeStyle(lineWidth: line, lineCap: .round))
                    .shadow(color: color.opacity(0.5), radius: line)
            }
            .rotationEffect(.degrees(135))
            .overlay {
                VStack(spacing: 0) {
                    Text("\(level)")
                        .font(.system(size: side * 0.44, weight: .heavy, design: .rounded))
                        .foregroundStyle(color)
                        .contentTransition(.numericText(value: Double(level)))
                    Text("Out of 10")
                        .textCase(.uppercase)
                        .font(.system(size: side * 0.085, weight: .semibold))
                        .foregroundStyle(.secondary)
                }
            }
            .padding(line / 2)
            .frame(width: side, height: side)
            .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
    }
}
