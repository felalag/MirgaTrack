import SwiftUI

/// Brand tokens from composeApp's Tokens.kt. The app and the complication extension both compile this file.
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

extension URL {
    /// The complication can only open the app; this URL tells it to show the logger.
    static let logger = URL(string: "migratrack://log")!
}
