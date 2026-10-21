package com.example.model

enum class AppLanguage(val code: String, val label: String, val nativeName: String) {
    TELUGU("te", "Telugu", "తెలుగు"),
    ENGLISH("en", "English", "English"),
    HINDI("hi", "Hindi", "हिंदी")
}

enum class PilgrimCategory(
    val id: String,
    val nameEn: String,
    val nameTe: String,
    val nameHi: String,
    val assignedGate: String,
    val gateDescriptionEn: String,
    val isStepFreeRequired: Boolean = false
) {
    GENERAL_CITIZEN(
        "general",
        "General Pilgrim",
        "సాధారణ భక్తులు",
        "सामान्य तीर्थयात्री",
        "Gate C (Main Flow)",
        "Spacious unidirectional entry with barrier holding bays."
    ),
    RITUAL_PERFORMER(
        "ritual",
        "Ritual Performer (Pinda Pradanam)",
        "పిండ ప్రదాన పూజా భక్తులు",
        "पिंड दान / अनुष्ठान भक्त",
        "Gate A (Ritual Zone)",
        "Direct passage to priests, holy materials, and river staging."
    ),
    CHILD_WITH_GUARDIAN(
        "child",
        "Family with Young Children",
        "పిల్లలతో కుటుంబాలు",
        "बच्चों के साथ परिवार",
        "Gate B (RFID Wristband Desk)",
        "Mandatory fast QR/RFID child safety band linking and pram access."
    ),
    PERSON_WITH_DISABILITY(
        "pwd_elderly",
        "Differently-Abled & Senior Citizens",
        "దివ్యాంగులు & వృద్ధులు",
        "दिव्यांग एवं वरिष्ठ नागरिक",
        "Gate D (Accessible Ramp & Buggy)",
        "100% step-free ramp, wheelchairs, and electric cart feeder to water edge.",
        isStepFreeRequired = true
    )
}

enum class District(val id: String, val nameEn: String, val nameTe: String, val totalGhats: Int) {
    EAST_GODAVARI("eg", "East Godavari (Rajahmundry)", "తూర్పు గోదావరి (రాజమహేంద్రవరం)", 82),
    KONASEEMA("kona", "Dr. B.R. Ambedkar Konaseema", "డా. బి.ఆర్. అంబేద్కర్ కోనసీమ", 175),
    KAKINADA("kkn", "Kakinada", "కాకినాడ", 88),
    WEST_GODAVARI("wg", "West Godavari (Narasapuram)", "పశ్చిమ గోదావరి (నరసాపురం)", 40),
    ELURU("elr", "Eluru", "ఏలూరు", 44),
    POLAVARAM("plv", "Polavaram & Pattiseema", "పోలవరం & పట్టిసీమ", 11)
}

enum class CrowdDensity(val labelEn: String, val labelTe: String, val labelHi: String) {
    LOW("LOW DENSITY", "తక్కువ రద్దీ", "कम भीड़"),
    MEDIUM("MODERATE", "మధ్యస్థ రద్దీ", "मध्यम भीड़"),
    HIGH("HIGH DENSITY", "అధిక రద్దీ", "अधिक भीड़"),
    CRITICAL("CRITICAL / THROTTLED", "తీవ్ర రద్దీ / నియంత్రణ", "अत्यधिक भीड़ / बंद")
}

enum class GhatTier {
    TIER_1_AI_CCTV,
    TIER_2_VILLAGE_NETWORK
}

data class GhatInfo(
    val id: String,
    val nameEn: String,
    val nameTe: String,
    val district: District,
    val tier: GhatTier,
    val density: CrowdDensity,
    val currentCrowd: Int,
    val safeCapacity: Int,
    val entryGate: String,
    val exitGate: String,
    val distanceKmFromHub: Double,
    val isEntryOpen: Boolean = true,
    val isDiverting: Boolean = false,
    val safeAlternativeGhatName: String? = null,
    val stepFreeRampAvailable: Boolean = true,
    val bioToiletsCount: Int = 24,
    val drinkingWaterPoints: Int = 18,
    val medicalPostActive: Boolean = true,
    val eClinicStationActive: Boolean = true,
    val riverWaterLevelMeters: Double = 14.2,
    val isRiverFlowSafe: Boolean = true
)

enum class PriceBand(val label: String) {
    ECONOMY("Economy (₹0 - ₹800)"),
    STANDARD("Standard (₹800 - ₹2,000)"),
    COMFORT("Comfort (₹2,000 - ₹4,500)"),
    PREMIUM("Premium (₹4,500+)")
}

data class StayProperty(
    val id: String,
    val name: String,
    val district: District,
    val hubCity: String,
    val category: String, // Hotel, Resort, Dharmashala, Tent City, Choultry
    val starRating: Double,
    val priceBand: PriceBand,
    val fixedRatePerNight: Int,
    val hasStepFreeAccess: Boolean,
    val isPureVeg: Boolean,
    val hasParkingOnSite: Boolean,
    val contactPhone: String,
    val availableRooms: Int,
    val isGovtVerified: Boolean = true,
    val distanceToNearestGhatKm: Double
)

enum class ParkingStatus(val labelEn: String, val labelTe: String) {
    FREE("FREE SLOTS", "ఖాళీ స్థలాలు"),
    FILLING("FILLING FAST", "త్వరగా నిండుతోంది"),
    FULL("FULL - OVERFLOW ACTIVE", "నిండిపోయింది - మళ్లింపు")
}

data class ParkingLot(
    val id: String,
    val name: String,
    val district: District,
    val associatedGhatName: String,
    val vehicleTypeSupported: String, // "Two-Wheelers & Cars", "Heavy Buses & Travellers"
    val totalSlots: Int,
    val occupiedSlots: Int,
    val status: ParkingStatus,
    val shuttleFrequencyMinutes: Int,
    val hasAccessibleBays: Boolean,
    val walkingTimeMinutesToGate: Int,
    val overflowRedirectName: String? = null
)

enum class RouteType {
    FASTEST,
    LEAST_CROWDED,
    EASIEST_STEP_FREE
}

data class RouteOption(
    val id: String,
    val type: RouteType,
    val titleEn: String,
    val titleTe: String,
    val durationMinutes: Int,
    val distanceKm: Double,
    val walkingMinutes: Int,
    val crowdRiskScore: Int, // 1 (best) to 10 (choked)
    val isStepFree: Boolean,
    val summaryEn: String,
    val summaryTe: String,
    val isSafeUnderAntiStampedeRule: Boolean = true,
    val diversionNotice: String? = null
)

data class FamilyMember(
    val id: String,
    val name: String,
    val relation: String,
    val age: Int,
    val category: PilgrimCategory,
    val rfidWristbandId: String? = null,
    val wristbandStatus: String = "Active & Safe",
    val distanceMetersFromHead: Int = 3
)

enum class AlertSeverity {
    INFO,
    WARNING,
    EMERGENCY
}

data class EmergencyAlert(
    val id: String,
    val titleEn: String,
    val titleTe: String,
    val messageEn: String,
    val messageTe: String,
    val severity: AlertSeverity,
    val timestamp: String,
    val isAmbulanceCorridorActive: Boolean = false
)

data class TripPlanItinerary(
    val totalDays: Int,
    val assignedLowDensitySlot: String,
    val recommendedGhat: String,
    val staySuggestion: String,
    val travelModeSummary: String,
    val estimatedBudgetInr: Int,
    val templeCircuitCovered: List<String>,
    val crowdRiskAdvice: String
)

data class SwachhataTicket(
    val id: String,
    val ghatName: String,
    val locationDetail: String,
    val issueType: String,
    val status: String = "In Progress (Assigned to Sector 3 Team)",
    val reportedAt: String
)
