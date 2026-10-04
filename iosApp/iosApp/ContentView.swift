import ComposeApp
import SwiftUI
import UIKit

/// Hosts the Compose Multiplatform UI. Everything above this view is native SwiftUI.
struct ContentView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
