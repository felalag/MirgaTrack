import MigraCore
import SwiftUI

/// The sheet behind Log Migraine: Pain Intensity, then Episode logged. watchOS draws the close button.
struct LoggerSheet: View {
    @Environment(WatchModel.self) private var model

    var body: some View {
        NavigationStack {
            if model.logger == .logged {
                EpisodeLoggedView()
                    .transition(.blurReplace)
            } else if model.status?.isLinked == false {
                // The complication opens the logger also before onboarding on the iPhone, when the watch cannot log.
                Text("Open MigraTrack on your iPhone to sync.")
                    .multilineTextAlignment(.center)
            } else {
                LogEpisodeView()
                    .transition(.blurReplace)
            }
        }
        .animation(.smooth, value: model.logger)
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
        VStack(spacing: 4) {
            IntensityGauge(level: level)
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
            Text("Pain Intensity")
                .font(.footnote)
                .foregroundStyle(.secondary)
                .multilineTextAlignment(.center)
                // The gauge already reads as "Pain intensity" to VoiceOver.
                .accessibilityHidden(true)
        }
        .bottomAction {
            Button("Save Episode") {
                Task { await model.save(intensity: level) }
            }
            .buttonStyle(.borderedProminent)
            .tint(.migraPink)
            .disabled(model.isSaving)
        }
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

/// The dark backdrop of the logger with a soft glow in the middle. An opaque backdrop keeps the summary from
/// showing through the sheet.
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
            let side = DialMetrics.side(in: geometry.size)
            let line = DialMetrics.line(side: side)
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
                Text("\(level)")
                    .font(.largeTitle.bold())
                    .monospacedDigit()
                    .foregroundStyle(color)
                    .contentTransition(.numericText(value: Double(level)))
                    .lineLimit(1)
                .minimumScaleFactor(0.5)
                .frame(width: DialMetrics.textSide(side: side), height: DialMetrics.textSide(side: side))
            }
            .padding(line / 2)
            .frame(width: side, height: side)
            .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
    }
}

/// Sizes that the intensity gauge and the checkmark share, so that the two screens match.
enum DialMetrics {
    /// A share of the screen width. 0.33 is the most that the gauge gets on the 49mm watch, between the close button
    /// and its label.
    static func side(in size: CGSize) -> CGFloat {
        min(size.width * 0.33, size.height)
    }

    static func line(side: CGFloat) -> CGFloat {
        side * 0.06
    }

    /// The largest square inside the gauge, where large Dynamic Type sizes shrink the text to fit.
    static func textSide(side: CGFloat) -> CGFloat {
        (side - 2 * line(side: side)) * 0.7
    }
}
