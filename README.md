# Smart Pushkaralu 2027: Pilgrim Mobile & Web App
### Government of Andhra Pradesh • Real-Time Public Safety & Crowd Management

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20M3-4285F4?logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-24%20(Android%207.0)-blue)](https://developer.android.com/)
[![Target SDK](https://img.shields.io/badge/Target%20SDK-36-green)](https://developer.android.com/)

A mission-critical public safety and anti-stampede crowd management Android application built for the **Government of Andhra Pradesh** for the upcoming **Godavari Pushkaralu (26 June – 7 July 2027)**.

The system connects millions of pilgrims across **6 Godavari delta districts** and **440+ bathing ghats** directly to the AP State Command & Control Center, preventing stampedes through deterministic routing guardrails, upstream ANPR parking interception, unidirectional category zoning, and real-time emergency de-escalation.

---

## Table of Contents
1. [Festival & Geographic Scope](#festival--geographic-scope)
2. [5-Layer Anti-Stampede Architecture](#5-layer-anti-stampede-architecture)
3. [9 Core Application Screens](#9-core-application-screens)
4. [Live Government Showcase Simulation Bar](#live-government-showcase-simulation-bar)
5. [Prerequisites & Dependencies to Install](#prerequisites--dependencies-to-install)
6. [How to View the UI Preview Locally (No Device Needed)](#how-to-view-the-ui-preview-locally-no-device-needed)
7. [How to Run the App Locally on Emulator or Device](#how-to-run-the-app-locally-on-emulator-or-device)
8. [CLI Commands Reference Guide](#cli-commands-reference-guide)
9. [Comprehensive Dependency Catalog](#comprehensive-dependency-catalog)
10. [Project File Tree](#project-file-tree)

---

## Festival & Geographic Scope

- **Festival Dates**: 26 June – 7 July 2027 (Godavari Pushkaralu)
- **Central Transit Hub**: Rajamahendravaram (Rajahmundry) — Railway Station, RTC Complex & Airport
- **6 Delta Districts**:
  1. **East Godavari** (82 Ghats) — Central Hub, Kovvur Goshpada Kshetram, Dowleswaram Barrage
  2. **Dr. B.R. Ambedkar Konaseema** (175 Ghats) — Antarvedi, Balusutippa, Dindi, Amalapuram
  3. **Kakinada** (88 Ghats) — Goalanka Terminal, Kakinada Port City
  4. **West Godavari** (40 Ghats) — Narasapuram Terminal, Bhimavaram Transit Hub
  5. **Eluru** (44 Ghats) — Inland and canal river network
  6. **Polavaram** (11 Ghats) — Pattiseema Akhanda Godavari Ghat, Kukunuru
- **Languages**: Full trilingual support in **Telugu (`తెలుగు`)**, **English**, and **Hindi (`हिंदी`)** with native `TextToSpeech` vocal guidance for elderly pilgrims.

---

## 5-Layer Anti-Stampede Architecture

The core engineering objective of this application is **preventing stampedes, crowd surges, and road bottlenecks** across river delta choke points:

1. **Layer 1 — Pre-Trip Crowd Distribution (Module D)**: Spreads arrival traffic away from peak hours (9:00 AM – 12:00 PM) by assigning off-peak low-density bathing slots and recommending sister ghats based on family demographics.
2. **Layer 2 — Hard Routing Guardrails (Module A)**: Real-time route engine computes dynamic costs (`Ttraffic + Twalk + Pcrowded_roads + Pone_way_conflicts + Tparking_shuttle_wait`). **Never routes into a ghat marked Closed, Throttled, or High Density**; automatically diverts incoming pilgrims to safe alternative ghats (e.g. from Pushkar Ghat to Kovvur Goshpada Kshetram).
3. **Layer 3 — Upstream Parking Interception (Module C)**: ANPR cameras identify license plates and track slot occupancy (**Free**, **Filling**, **Full**). When a perimeter lot fills up, drivers are intercepted and redirected to remote overflow hubs (e.g. Arts College Grounds) linked with free electric feeder shuttles before entering narrow river roads.
4. **Layer 4 — Unidirectional Category Zoning**: Dedicated, non-intersecting **Entry** and **Exit** gates ensure incoming and exiting crowds never collide. 4 distinct category lanes segregate pilgrims into *General Citizens*, *Ritual Performers (Pinda Pradanam)*, *Families with Children*, and *Differently-Abled/Seniors (100% Step-Free)*.
5. **Layer 5 — Rapid Emergency De-escalation**: 1-Tap SOS dispatches live GPS coordinates to Sector 4 field officers, medical stations, and 108 river rescue boats. Includes **Smart Emergency Corridors** (clearing pilgrim traffic off ambulance hospital routes), **E-Clinic** tele-consultations, and child RFID wristband tracking.

---

## 9 Core Application Screens

| Screen | Purpose & Key Features |
|---|---|
| **1. Home Dashboard** | Trilingual top bar, native voice TTS prompt, live 440-ghat crowd density carousel, active alert banners, 4 primary service cards, and 1-tap SOS trigger. |
| **2. Trip Planner (Module D)** | Budget tier selector (Economy, Standard, Comfort, Premium), group demographics steppers, Godavari Temple Circuit toggle (Pithapuram, Annavaram, Dwaraka Tirumala, Antarvedi), and assigned low-density bathing slot pass. |
| **3. Map & Navigation (Module A)** | 3 selectable route options (**Fastest**, **Least Crowded**, **Easiest / Step-Free**), RUDA GIS map canvas, bridge links, and anti-stampede diversion notices. |
| **4. Ghat Detail** | Live crowd density badge, CCTV AI count vs safe capacity, separate Entry/Exit gate cards, 4 category gate lanes, bio-toilets/RO water status, and quick links to stays and parking. |
| **5. Stay Directory (Module B)** | 37+ verified properties (including official East Godavari district hotels: River Bay Resort, Anand Regency, Manjeera, Sitara, Jetty Grand, La Hospin, Shelton, and Kovvur Haritha) with fixed festival rate bands preventing price gouging. |
| **6. Smart Parking (Module C)** | ANPR vehicle pass card (`AP39 AB 1234`), live zone occupancy progress bars, upstream interception warnings, and free feeder shuttle frequency tracker. |
| **7. My Family Group** | Group Head profile, linked dependents, child RFID wristband beacon monitoring (`#AP-PK-8821`), proximity alerts, and 1-tap WhatsApp family location sharing. |
| **8. Safety & Medical** | Giant 1-Tap SOS with 3-second abort countdown, field responder dispatch, E-Clinic telemedicine consultation, missing person report form, and Godavari river flow telemetry. |
| **9. Profile & Settings** | Trilingual selector, Pilgrim Category switcher, Large Typography mode, High-Contrast visual mode, SMS/WhatsApp alert delivery toggles, and tracked Swachhata cleanliness tickets. |

---

## Live Government Showcase Simulation Bar

The application features a floating, expandable **Government Showcase Simulation Toolbar** at the bottom of the screen. Reviewers and evaluators can tap 1-touch scenario triggers to watch the app adapt in real time:

- **Simulate Crowd Surge at Pushkar Ghat**: Pushes Pushkar Ghat to CRITICAL (98% capacity), locks entry gates under anti-stampede protocols, posts emergency alerts, and automatically recalculates navigation to divert pilgrims across the Rail-cum-Road bridge to Kovvur Goshpada Kshetram.
- **Simulate Parking Zone Full (ANPR Intercept)**: Fills Pushkar Circle Parking Zone A to 100%, triggers upstream interception, and redirects incoming vehicles to Arts College Remote Hub with free electric feeder shuttles.
- **Simulate Smart Ambulance Corridor**: Activates an emergency medical corridor on Godavari Bund Road, clearing general traffic and routing pilgrims to the Ring Road bypass.

---

## Prerequisites & Dependencies to Install

Ensure the following tools and runtimes are installed on your local development machine:

### 1. System Software & SDKs
| Dependency | Recommended Version | Download / Installation Link |
|---|---|---|
| **Android Studio** | **Ladybug (2024.2.1+)**, Koala, or Meerkat | [developer.android.com/studio](https://developer.android.com/studio) |
| **Java JDK** | **JDK 17** or **JDK 21** | Bundled inside Android Studio or [Adoptium Temurin](https://adoptium.net/) |
| **Android SDK Platform** | **API Level 36** (Android 15/16 preview) or **API 35** | Installed via Android Studio SDK Manager |
| **Android SDK Build-Tools**| **35.0.0** or **36.0.0** | Installed via Android Studio SDK Manager |
| **Git** | **2.30+** | [git-scm.com](https://git-scm.com/) |

### 2. Verify SDK & Java via Terminal
Run the following commands in your terminal to ensure prerequisites are configured:

```bash
# Check Java version (must be 17 or 21)
java -version

# Check Android SDK Path (optional environment variable)
echo $ANDROID_HOME
# or on Windows:
echo %ANDROID_HOME%
```

---

## How to View the UI Preview Locally (No Device Needed)

You do **NOT** need to launch an Android emulator or connect a physical phone to preview the UI. The application uses **Jetpack Compose Tooling Previews** to render live screens directly inside Android Studio.

### Method 1: Compose Split Preview (Instant Visual Canvas)
1. Open the project in **Android Studio**.
2. In the Project Explorer, navigate to:
   `app/src/main/java/com/example/MainActivity.kt`
3. At the top-right corner of the editor window, click the **Split** (Code + Design) or **Design** view icon.
4. Android Studio will automatically build and render **`PushkaraluAppPreview`** in the side canvas.
5. You will see the complete Home Dashboard with AP Government header, trilingual pills, live crowd carousel, and primary cards rendered in high fidelity.

### Method 2: Interactive Preview Mode
1. In the Compose Preview canvas in `MainActivity.kt`, look at the top header of the preview card.
2. Click the **Interactive Mode (▶)** icon (or press `Ctrl + Shift + F10` / `Cmd + Shift + F10`).
3. The preview becomes directly interactive:
   - Tap the language pills (`తెలుగు`, `English`, `हिंदी`) to test real-time trilingual state changes.
   - Tap cards and bottom navigation tabs to inspect layout transitions without compiling to an APK.

---

## How to Run the App Locally on Emulator or Device

### Option A: Using Android Studio (GUI - Recommended)

1. **Clone & Open Project**:
   ```bash
   git clone https://github.com/<your-username>/<your-repo-name>.git
   cd <your-repo-name>
   cp .env.example .env
   ```
   Open Android Studio, select **File -> Open**, and choose the project directory.

2. **Wait for Gradle Sync**:
   Android Studio will download all Gradle plugins and dependencies automatically. Ensure the sync completes with `BUILD SUCCESSFUL`.

3. **Set Up an Android Emulator (AVD)**:
   - Click on the **Device Manager** icon on the right sidebar (or go to **Tools -> Device Manager**).
   - Click **Create Device**.
   - Select **Phone -> Pixel 8** (or Pixel 9 / Pixel 7).
   - Under System Image, download and select **API 34, 35, or 36** (VanillaIceCream / Tiramisu).
   - Click **Finish** and start the emulator by pressing the **Play (▶)** button.

4. **Launch the Application**:
   - In the top toolbar, ensure **`app`** is selected in the run configurations dropdown.
   - Select your running emulator or connected phone in the device selector.
   - Click the green **Run (▶)** button (or press `Shift + F10`).
   - Android Studio will compile, install the APK, and open the app on your screen.

---

### Option B: Using Command Line (Terminal / CLI)

If you prefer using the command line without opening Android Studio's full GUI:

1. **Ensure Gradle Wrapper is Executable**:
   ```bash
   chmod +x gradlew
   ```

2. **Verify Connected Devices / Emulators**:
   ```bash
   adb devices
   ```
   *Output should show at least one attached device (e.g. `emulator-5554 device`).*

3. **Install and Run Directly**:
   ```bash
   ./gradlew installDebug
   ```

4. **Launch the MainActivity via ADB**:
   ```bash
   adb shell am start -n com.aistudio.smartpushkaralu.gkov/com.example.MainActivity
   ```

5. **View Live Application Logs & Telemetry**:
   ```bash
   adb logcat -s SmartPushkaralu,MainActivity,TextToSpeech
   ```

---

## CLI Commands Reference Guide

All common build, test, and execution commands from the project root:

| Action | Linux / macOS Command | Windows CMD / PowerShell |
|---|---|---|
| **Clean Build Cache** | `./gradlew clean` | `gradlew.bat clean` |
| **Run Unit & Robolectric Tests** | `./gradlew testDebugUnitTest` | `gradlew.bat testDebugUnitTest` |
| **Compile & Verify Project** | `./gradlew compileDebugKotlin` | `gradlew.bat compileDebugKotlin` |
| **Build Debug APK** | `./gradlew assembleDebug` | `gradlew.bat assembleDebug` |
| **Build Release Bundle (AAB)** | `./gradlew bundleRelease` | `gradlew.bat bundleRelease` |
| **Install Debug APK on Device** | `./gradlew installDebug` | `gradlew.bat installDebug` |
| **Uninstall App from Device** | `adb uninstall com.aistudio.smartpushkaralu.gkov` | `adb uninstall com.aistudio.smartpushkaralu.gkov` |
| **Stop Background Gradle Daemons**| `./gradlew --stop` | `gradlew.bat --stop` |

> **Compiled APK Output Location**:  
> Once built, your debug APK is located at:  
> `app/build/outputs/apk/debug/app-debug.apk`

---

## Comprehensive Dependency Catalog

All dependencies are centrally configured in **`gradle/libs.versions.toml`** and resolved automatically by Gradle:

### 1. Core Jetpack & Compose Libraries
- **Compose BOM (`2024.09.00`)**: Manages binary-compatible versions of all Compose components.
- **`androidx.compose.material3:material3`**: Material Design 3 design system, colors, elevation, and typography.
- **`androidx.compose.material:material-icons-extended`**: Complete iconography (shields, hospitals, routes, buses, emergency icons).
- **`androidx.activity:activity-compose (1.10.1)`**: Seamless integration of Compose with Android ComponentActivity & BackHandler.
- **`androidx.lifecycle:lifecycle-runtime-compose (2.8.7)`**: Lifecycle-aware state collection (`collectAsStateWithLifecycle`).
- **`androidx.lifecycle:lifecycle-viewmodel-compose (2.8.7)`**: Android MVVM lifecycle management.
- **`androidx.core:core-ktx (1.18.0)`**: Kotlin extensions for core Android platform APIs.

### 2. Concurrency & Data Flow
- **`org.jetbrains.kotlinx:kotlinx-coroutines-core (1.10.2)`** & **`kotlinx-coroutines-android`**: Asynchronous routing calculations and SOS countdown timers.

### 3. Local Persistence & Networking (Ready for Backend Integration)
- **`androidx.room:room-runtime (2.7.0)`** & **`room-ktx`**: Local SQLite persistence for offline maps and caches.
- **`com.squareup.retrofit2:retrofit (2.12.0)`** & **`converter-moshi`**: REST API client for Command Center feeds.
- **`com.squareup.okhttp3:okhttp (4.10.0)`**: HTTP networking engine.

### 4. Testing Frameworks
- **`junit:junit (4.13.2)`**: Unit test runner.
- **`org.robolectric:robolectric (4.16.1)`**: Fast local JVM testing of Android framework components and resources without requiring an emulator.
- **`androidx.compose.ui:ui-test-junit4`**: Compose UI automated test runner.

---

## Project File Tree

```
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/
│   │   │   │   ├── MainActivity.kt            # Edge-to-edge scaffold, bottom navigation & @Preview
│   │   │   │   ├── data/
│   │   │   │   │   └── PushkaraluRepository.kt# 6-District registry, 37+ stays, ANPR parking, simulation engine
│   │   │   │   ├── model/
│   │   │   │   │   └── Models.kt              # Ghats, Stays, Parking, Routes, Demographics & Alerts
│   │   │   │   ├── ui/
│   │   │   │   │   ├── PushkaraluViewModel.kt # State management, TTS audio prompts & scenario triggers
│   │   │   │   │   ├── components/
│   │   │   │   │   │   ├── CommonComponents.kt# Trilingual header, SOS button, crowd badges
│   │   │   │   │   │   ├── SimulationBar.kt   # Floating Government Reviewer Simulation Bar
│   │   │   │   │   │   └── Modals.kt          # SOS Emergency, E-Clinic, Missing Person & Swachhata
│   │   │   │   │   ├── screens/
│   │   │   │   │   │   ├── HomeScreen.kt          # Live density ticker, alerts, action cards
│   │   │   │   │   │   ├── TripPlannerScreen.kt   # Budget & slot dispersal (Module D)
│   │   │   │   │   │   ├── NavigationScreen.kt    # 3-Choice route engine & safe diversion (Module A)
│   │   │   │   │   │   ├── GhatDetailScreen.kt    # Ingress/egress gates & category zones
│   │   │   │   │   │   ├── StayDirectoryScreen.kt # 37+ verified properties & fixed rate bands (Module B)
│   │   │   │   │   │   ├── SmartParkingScreen.kt  # ANPR pass & upstream interception (Module C)
│   │   │   │   │   │   ├── FamilyGroupScreen.kt   # Child RFID wristband & family safety
│   │   │   │   │   │   ├── SafetyMedicalScreen.kt # 1-Tap SOS, E-Clinic & River telemetry
│   │   │   │   │   │   └── SettingsScreen.kt      # Language, category & accessibility switches
│   │   │   │   │   └── theme/
│   │   │   │   │       ├── Color.kt               # Godavari Navy, Gold, River Cyan, Density tokens
│   │   │   │   │       ├── Theme.kt               # SmartPushkaraluTheme with M3 dynamic colors
│   │   │   │   │       └── Type.kt                # Typography scale
│   │   │   ├── res/
│   │   │   │   ├── drawable/                  # Custom adaptive launcher foreground & background
│   │   │   │   ├── mipmap-*/                  # Density-specific raster icons
│   │   │   │   └── values/                    # App names, colors & base themes
│   │   │   └── AndroidManifest.xml            # Hardware, network & safety permissions
│   │   └── test/java/com/example/
│   │       ├── ExampleUnitTest.kt             # Anti-stampede guardrail, parking overflow & routing tests
│   │       └── ExampleRobolectricTest.kt      # Context & resource verification tests
│   └── build.gradle.kts                       # Module dependencies, KSP, Room & Compose plugins
├── gradle/
│   └── libs.versions.toml                     # Centralized Version Catalog
├── metadata.json                              # Platform identity
├── settings.gradle.kts                        # Root project declaration
└── README.md                                  # Complete documentation & local setup guide
```

---

## License & Ownership

Developed for the **Government of Andhra Pradesh** for the **Godavari Pushkaralu 2027 Public Safety & Crowd Management Initiative**. All rights reserved.
