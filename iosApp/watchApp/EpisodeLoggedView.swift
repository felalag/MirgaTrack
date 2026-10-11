import MigraCore
import SwiftUI

/// Confirms the save with what the episode holds. Done and the close button return to the summary.
struct EpisodeLoggedView: View {
    @Environment(WatchModel.self) private var model
    @State private var hasAppeared = false
    let episode: WatchEpisode

    var body: some View {
        let intensity = Int(episode.intensity)
        ScrollView {
            VStack(spacing: 8) {
                Image(systemName: "checkmark.circle.fill")
                    .symbolRenderingMode(.palette)
                    .foregroundStyle(.black, Color.migraGreen)
                    .font(.system(size: 44, weight: .semibold))
                    .shadow(color: .migraGreen.opacity(0.45), radius: 10)
                    .symbolEffect(.bounce, value: hasAppeared)
                    .accessibilityHidden(true)
                Text("Episode logged")
                    .font(.title3.weight(.bold))
                details(intensity: intensity)
                    .font(.footnote)
                    .foregroundStyle(.secondary)
                    .multilineTextAlignment(.center)
                Button("Done") {
                    model.logger = nil
                }
                .buttonStyle(.borderedProminent)
                .tint(.migraPink)
                .padding(.top, 4)
            }
            .frame(maxWidth: .infinity)
        }
        .presentationBackground { Glow(color: .migraPink) }
        .onAppear { hasAppeared = true }
    }

    private func details(intensity: Int) -> Text {
        let level = Text("Intensity \(intensity)/10")
            .fontWeight(.semibold)
            .foregroundStyle(Color.severity(intensity))
        return episode.medications.isEmpty ? level : Text("\(level) • \(episode.medications)")
    }
}
