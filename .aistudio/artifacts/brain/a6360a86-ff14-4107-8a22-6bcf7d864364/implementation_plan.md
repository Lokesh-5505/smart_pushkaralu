# Smart Pushkaralu 2027: Pilgrim Mobile & Web App (Gov of AP)

A mission-critical public safety and crowd management Android application designed for the Government of Andhra Pradesh to prevent stampedes, manage multi-modal traffic, and guide millions of pilgrims across 6 delta districts and 440+ bathing ghats during Godavari Pushkaralu 2027.

## User Review & Critical Decisions

> [!IMPORTANT]
> The following critical product and UX decisions have been aligned based on clarifying interview answers:

- **Confirmed Decision 1 (Gov Showcase Simulation)**: Equipped with a persistent Floating Simulation Bar that allows reviewers to trigger 1-tap scenarios: *Pushkar Ghat Crowd Surge*, *Parking Zone Overflow*, and *Active Ambulance Corridor*, demonstrating real-time dynamic rerouting and safe ghat redirection.
- **Confirmed Decision 2 (Initial Startup Experience)**: Opens directly into the Pilgrim Home Dashboard pre-loaded with a Rajamahendravaram (Central Hub) family pilgrim profile (`G. Satyanarayana`, General Citizen with Elderly & Child dependents) for immediate, realistic review.
- **Confirmed Decision 3 (Trilingual Voice Prompts)**: Fully integrated with Android's native `TextToSpeech` engine supporting Telugu, English, and Hindi with high-contrast audio triggers designed for elderly and visually impaired pilgrims.

---

## 1. Overview & Core Concept

- **What It Does**: Connects pilgrims across the 6 Godavari delta districts (East Godavari, West Godavari, Konaseema, Kakinada, Eluru, Polavaram) directly to the AP State Command & Control Center. Prevents surges and bottlenecks via a 5-layer anti-stampede mechanism: pre-trip crowd distribution, hard routing guardrails, upstream parking interception, unidirectional category zoning, and instant emergency de-escalation.
- **Target Audience / Persona**: Millions of pilgrims including elderly devotees, families with young children, differently-abled persons, and ritual performers, as well as field duty officers and district administrators.
- **Key Value**: Zero stampedes, clear non-conflicting ingress/egress routes, transparent verified accommodation without price gouging, automated parking allocation via ANPR, and sub-second 1-tap SOS response.

---

## 2. User Experience & Visual Design

### 2.1 The 9 Core Application Screens

1. **Home Screen**:
   - Trilingual header with instant 1-tap switch (`తెలుగు` | `English` | `हिंदी`) and TTS voice prompt button.
   - Live Ghat Density Ticker highlighting Rajamahendravaram, Kovvur, Antarvedi, and Narasapuram.
   - High-visibility Official Weather/River-Level Alert Banner.
   - 4 Primary Action Cards (*Plan Trip*, *Navigate & Routes*, *Smart Parking*, *Verified Stay*) + prominent Red Emergency SOS Button.
   - Floating Gov Showcase Simulator bar.
2. **Trip Planner (Module D)**:
   - Budget slider (Economy, Standard, Comfort, Premium), date selector, group age breakdown (elderly/child counts), and Temple Circuit toggle (*Pithapuram, Annavaram, Dwaraka Tirumala, Antarvedi*).
   - Generates day-wise itinerary cards recommending low-density ghats and assigned off-peak bathing time slots to proactively disperse crowds.
3. **Map & Navigation (Module A)**:
   - Live visual routing engine offering 3 distinct options: **Fastest**, **Least Crowded**, and **Easiest** (step-free, minimal turns for elderly/disabled).
   - Strict Anti-Stampede Guardrails: completely blocks routing into Closed or High-density choke points and automatically diverts to the nearest safe tier-1/tier-2 ghat.
   - Smart Emergency Corridor overlay that clears pilgrim routes when ambulances are active.
4. **Ghat Detail & Category Zoning**:
   - Dynamic crowd density badge (**LOW**, **MEDIUM**, **HIGH**).
   - Unidirectional gate guidance: distinct Entry Gates and Exit Gates so opposing crowds never collide.
   - Dedicated Category Zones (*Ritual*, *Child with guardian*, *Person with disability*, *General citizen*).
   - Nearby drinking water, mobile bio-toilets, medical camps, and quick links to stays and parking.
5. **Stay Directory (Module B)**:
   - Directory pre-loaded with official East Godavari district hotels (*River Bay Resort, Anand Regency, Manjeera International, Sitara Residency, Jetty Grand, La Hospin, Shelton, Kovvur Haritha*) and 37+ verified regional properties across all 6 districts.
   - District filter, price-band caps to prevent festival gouging, step-free/lift filters, pure vegetarian filters, and direct 1-tap call/booking links.
6. **Smart Parking (Module C)**:
   - ANPR-integrated parking pass displaying assigned zone, spot status (**Free**, **Filling**, **Full**), vehicle category (2-wheeler, car, bus), and real-time feeder shuttle bus schedules.
   - Upstream overflow interception: triggers advance rerouting before vehicles enter crowded ghat perimeters.
7. **My Family Group & Child Wristbands**:
   - Group Head profile linking family dependents with active RFID/QR child wristband status and lost-prevention beacons.
   - 1-tap live GPS family location sharing.
8. **Safety & Medical (Layer 5)**:
   - Large 1-tap SOS trigger with instant GPS dispatch to the nearest sector police officer and medical camp.
   - E-Clinic tele-consultation portal connecting field medical volunteers with specialized hospital doctors.
   - Officer-verified Missing Person reporting uploader.
9. **Profile, Settings & Accessibility**:
   - Trilingual selection, Pilgrim Category switcher, High-Contrast mode, Large Typography toggle, and SMS/WhatsApp alert dispatch switches.

### 2.2 Visual Identity & Styling
- **Palette**: Divine Godavari Navy (`#0C2D48`), Temple Gold (`#E07A5F` / `#F4A261`), Emerald Green for Low Density (`#15803D`), Warm Amber for Medium (`#D97706`), and Crimson Red for Critical/SOS/High (`#DC2626`).
- **Typography & Accessibility**: Clean, high-legibility sans-serif with accessible scale factors (minimum 48dp touch targets on all interactive controls).
- **Tone**: Authoritative, calm, dependable, government-grade utility—free from confusing chatbots or distracting novelty widgets.

---

## 3. Key Product Decisions & Trade-Offs

- **Decision 1: Deterministic Multi-Layer Routing over Black-Box AI**:
  - *Chosen Approach*: Rule-based cost formula `Ttraffic + Twalk + Pcrowded_roads + Pone_way_conflicts + Tparking_shuttle_wait` with hard barriers against high-density entrances.
  - *Why*: Critical safety infrastructure must guarantee predictability; routing must never send people into a stampede risk zone.
- **Decision 2: Fully Localized In-Memory & Offline-Cached State**:
  - *Chosen Approach*: Self-contained repository pre-seeded with all 6 district hubs, 440+ representative ghat points, official district hotels, and ANPR parking bays, backed by stateful reactive flows.
  - *Why*: Riverbank cellular networks during 10-million-pilgrim festivals experience severe packet loss and network towers become overloaded; app works seamlessly offline.
- **Decision 3: Floating Showcase Scenario Trigger**:
  - *Chosen Approach*: Persistent demo control panel allowing evaluators to simulate surges and watch the app adapt in real time.
  - *Why*: Enables government stakeholders to inspect both normal and peak-crisis modes during approval presentations.

---

## 4. Technical Architecture & Data Strategy

```
┌──────────────────────────────────────────────────────────────────────────┐
│                   Smart Pushkaralu 2027 Android App                      │
├──────────────────────────────────────────────────────────────────────────┤
│                                UI LAYER                                  │
│  ┌───────────────┬────────────────────────────┬────────────────────────┐ │
│  │ Home Dashboard│ Map & 3-Route Navigation   │ Ghat & Category Zoning │ │
│  ├───────────────┼────────────────────────────┼────────────────────────┤ │
│  │ Stay Directory│ Smart ANPR Parking & Bus   │ Trip Planner (Mod D)   │ │
│  ├───────────────┼────────────────────────────┼────────────────────────┤ │
│  │ Family Group  │ Safety, SOS & E-Clinic     │ Settings & Trilingual  │ │
│  └───────────────┴────────────────────────────┴────────────────────────┘ │
│                  ▲                                  ▲                    │
│                  │ (StateFlow / Jetpack Compose)    │                    │
│  ┌───────────────┴──────────────────────────────────┴──────────────────┐ │
│  │              PushkaraluViewModel & Simulation Engine                │ │
│  │  - Trilingual Manager (TE / EN / HI) & Android TextToSpeech         │ │
│  │  - Real-time Cost Router (Fastest / Least Crowded / Easiest)        │ │
│  │  - Upstream Parking Overflow & Feeder Shuttle Controller            │ │
│  │  - 1-Tap SOS Coordinator & Emergency Corridor Dispatcher            │ │
│  │  - Demo Simulator (Pushkar Ghat Surge, Kovvur Divert, Parking Full) │ │
│  └─────────────────────────────────────────────────────────────────────┘ │
│                  ▲                                                       │
│                  │                                                       │
│  ┌───────────────┴─────────────────────────────────────────────────────┐ │
│  │             PushkaraluRepository & Statewide Spatial Cache          │ │
│  │  - 6 Districts Registry (Konaseema, Kakinada, E&W Godavari, Eluru)  │ │
│  │  - 37+ Verified Stays (Official East Godavari List + Regional Hubs) │ │
│  │  - ANPR Parking Lots & Shuttle Routes                               │ │
│  │  - Emergency Medical Camps, E-Clinics & Sector Police Booths        │ │
│  └─────────────────────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────────────────────┘
```

### Key Data Entities
- `GhatInfo`: ID, name, district, tier (Tier 1 CCTV AI / Tier 2 Rural), crowdDensity (`LOW`, `MEDIUM`, `HIGH`, `CRITICAL`), entryGate, exitGate, coordinates, categoryZones, amenities.
- `StayProperty`: ID, name, district, hub, category (Hotel, Lodge, Dharmashala, Shelter), starRating, priceBand, stepFreeAccess, vegOnly, contactNumber, bookingStatus.
- `ParkingLot`: ID, name, district, vehicleType, totalCapacity, occupiedCount, status (`FREE`, `FILLING`, `FULL`), feederShuttleMinutes.
- `RouteOption`: ID, type (`FASTEST`, `LEAST_CROWDED`, `EASIEST`), durationMinutes, distanceKm, crowdCostScore, isSafe, stepFree.
- `FamilyMember`: ID, name, relation, ageGroup (`ADULT`, `ELDERLY`, `CHILD`, `PWD`), wristbandId, wristbandStatus.
- `EmergencyAlert`: ID, severity, titleTrilingual, messageTrilingual, timestamp, activeCorridor.

---

## 5. Verification Plan

- Compile and verify via `compile_applet` ensuring zero compilation errors or missing dependencies.
- Verify instant trilingual switching across all 9 views with Telugu, English, and Hindi string maps.
- Test native Android TextToSpeech integration for vocal prompts.
- Test Government Showcase Simulation: verify that toggling "Pushkar Ghat Crowd Surge" immediately switches Pushkar Ghat to HIGH/CRITICAL, triggers a safety alert, and recalculates the navigation route to divert pilgrims to Kovvur or nearby safe ghats.
