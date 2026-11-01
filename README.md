# Smart Pushkaralu

Cross-platform Flutter app for Godavari Pushkaralu 2027 public safety and crowd management. The app supports Android, iOS, tablets, and desktop-sized layouts from one responsive codebase.

## Included features

- Telugu, English, and Hindi language selection
- Live ghat crowd-density dashboard with safe-capacity indicators
- Crowd-surge, parking-interception, and ambulance-corridor simulations
- Trip planner with budget and group-demographic guidance
- Anti-stampede route options with step-free navigation
- Verified stay directory and smart parking / ANPR guidance
- Family group and RFID wristband status
- SOS countdown, medical posts, e-clinic, missing-person reporting, and river telemetry
- Large-text and high-contrast accessibility settings

## Run locally

Install Flutter 3.22 or newer, then run:

```bash
flutter pub get
flutter analyze
flutter test
flutter run
```

For release builds:

```bash
flutter build apk --release
flutter build ipa --release
```

The repository intentionally contains no third-party runtime packages; platform integrations can be added behind Flutter services without changing the responsive UI.
