import SwiftUI

/// Confirms the save with a checkmark the size of the ring and the gauge. Done and the close button return to the
/// summary.
struct EpisodeLoggedView: View {
    @Environment(WatchModel.self) private var model
    @State private var hasAppeared = false

    var body: some View {
        GeometryReader { geometry in
            let side = RingMetrics.side(in: geometry.size)
            Image(systemName: "checkmark.circle.fill")
                .resizable()
                .scaledToFit()
                .fontWeight(.semibold)
                .symbolRenderingMode(.palette)
                .foregroundStyle(.black, Color.migraGreen)
                .shadow(color: .migraGreen.opacity(0.45), radius: RingMetrics.line(side: side) * 0.6)
                .symbolEffect(.bounce, value: hasAppeared)
                .frame(width: side, height: side)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .accessibilityLabel("Episode logged")
        }
        .bottomAction {
            Button("Done") {
                model.logger = nil
            }
            .buttonStyle(.borderedProminent)
            .tint(.migraPink)
        }
        .presentationBackground { Glow(color: .migraPink) }
        .onAppear { hasAppeared = true }
    }
}
