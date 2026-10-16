# App icon

`icon.svg` is the source design: a 5 × 5 grid of day dots (#FF8093) on #0f0f0f, with one attack day (#FF2D55) that has a glow.

- `android/` holds launcher icons for a future Android app. The app does not exist yet, so no build uses these files.
- The iPhone and watch icons are in `iosApp/*/Assets.xcassets/AppIcon.appiconset`. The main 1024 icon is square and opaque, because the App Store refuses an alpha channel and iOS rounds the corners itself.
- The iPhone dark and tinted icons have a transparent background, because iOS draws its own background behind them. The tinted icon is grayscale: iOS tints it by luminance, so the attack day is white.
- The website icons are in `docs/assets`.
