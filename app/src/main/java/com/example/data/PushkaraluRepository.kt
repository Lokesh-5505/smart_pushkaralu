package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object PushkaraluRepository {

    // 1. Initial Ghats across 6 Districts
    private val initialGhats = listOf(
        GhatInfo(
            id = "ghat_pushkar",
            nameEn = "Pushkar Ghat (Central Hub)",
            nameTe = "పుష్కర ఘాట్ (ప్రధాన కేంద్రం)",
            district = District.EAST_GODAVARI,
            tier = GhatTier.TIER_1_AI_CCTV,
            density = CrowdDensity.MEDIUM,
            currentCrowd = 5820,
            safeCapacity = 11000,
            entryGate = "Gate 1 (North Arch)",
            exitGate = "Gate 4 (South Bund Exit Only)",
            distanceKmFromHub = 0.8,
            isEntryOpen = true,
            isDiverting = false,
            stepFreeRampAvailable = true,
            bioToiletsCount = 36,
            drinkingWaterPoints = 28,
            medicalPostActive = true,
            eClinicStationActive = true,
            riverWaterLevelMeters = 14.1,
            isRiverFlowSafe = true
        ),
        GhatInfo(
            id = "ghat_kotilingala",
            nameEn = "Kotilingala Revu Ghat",
            nameTe = "కోటిలింగాల రేవ ఘాట్",
            district = District.EAST_GODAVARI,
            tier = GhatTier.TIER_1_AI_CCTV,
            density = CrowdDensity.LOW,
            currentCrowd = 2100,
            safeCapacity = 8500,
            entryGate = "Temple East Gate",
            exitGate = "Bund North Exit",
            distanceKmFromHub = 2.1,
            stepFreeRampAvailable = true,
            bioToiletsCount = 20,
            drinkingWaterPoints = 16,
            medicalPostActive = true,
            eClinicStationActive = true
        ),
        GhatInfo(
            id = "ghat_kovvur",
            nameEn = "Kovvur Goshpada Kshetram Ghat",
            nameTe = "కొవ్వూరు గోష్పాద క్షేత్రం ఘాట్",
            district = District.EAST_GODAVARI,
            tier = GhatTier.TIER_1_AI_CCTV,
            density = CrowdDensity.LOW,
            currentCrowd = 1450,
            safeCapacity = 7500,
            entryGate = "Goshpada Arch Entry",
            exitGate = "Bridge Road Exit",
            distanceKmFromHub = 4.5,
            stepFreeRampAvailable = true,
            bioToiletsCount = 30,
            drinkingWaterPoints = 22,
            medicalPostActive = true,
            eClinicStationActive = true
        ),
        GhatInfo(
            id = "ghat_dowleswaram",
            nameEn = "Dowleswaram Barrage Ghat",
            nameTe = "ధవళేశ్వరం బ్యారేజ్ ఘాట్",
            district = District.EAST_GODAVARI,
            tier = GhatTier.TIER_1_AI_CCTV,
            density = CrowdDensity.LOW,
            currentCrowd = 1200,
            safeCapacity = 6000,
            entryGate = "Cotton Museum Gate",
            exitGate = "Anicut South Walkway",
            distanceKmFromHub = 7.2,
            stepFreeRampAvailable = true
        ),
        GhatInfo(
            id = "ghat_antarvedi",
            nameEn = "Antarvedi Sagara Sangamam Ghat",
            nameTe = "అంతర్వేది సాగర సంగమం ఘాట్",
            district = District.KONASEEMA,
            tier = GhatTier.TIER_1_AI_CCTV,
            density = CrowdDensity.MEDIUM,
            currentCrowd = 3400,
            safeCapacity = 9000,
            entryGate = "Lighthouse Beach Road Gate",
            exitGate = "Temple Circle One-Way Exit",
            distanceKmFromHub = 84.0,
            stepFreeRampAvailable = true
        ),
        GhatInfo(
            id = "ghat_balusutippa",
            nameEn = "Balusutippa Terminal River Ghat",
            nameTe = "బలుసుతిప్ప టెర్మినల్ నదీ ఘాట్",
            district = District.KONASEEMA,
            tier = GhatTier.TIER_2_VILLAGE_NETWORK,
            density = CrowdDensity.LOW,
            currentCrowd = 850,
            safeCapacity = 4500,
            entryGate = "Boat Jetty Entrance",
            exitGate = "Mangrove Loop Exit",
            distanceKmFromHub = 98.0,
            stepFreeRampAvailable = false
        ),
        GhatInfo(
            id = "ghat_goalanka",
            nameEn = "Goalanka Terminal Ghat",
            nameTe = "గోలంక టెర్మినల్ ఘాట్",
            district = District.KAKINADA,
            tier = GhatTier.TIER_1_AI_CCTV,
            density = CrowdDensity.LOW,
            currentCrowd = 920,
            safeCapacity = 5000,
            entryGate = "Canal Road Gate A",
            exitGate = "Highway Return Gate",
            distanceKmFromHub = 55.0,
            stepFreeRampAvailable = true
        ),
        GhatInfo(
            id = "ghat_narasapuram",
            nameEn = "Narasapuram Terminal Ghat",
            nameTe = "నరసాపురం టెర్మినల్ ఘాట్",
            district = District.WEST_GODAVARI,
            tier = GhatTier.TIER_1_AI_CCTV,
            density = CrowdDensity.LOW,
            currentCrowd = 1100,
            safeCapacity = 6000,
            entryGate = "Ferry Road North Gate",
            exitGate = "Valandhar Loop Exit",
            distanceKmFromHub = 78.0,
            stepFreeRampAvailable = true
        ),
        GhatInfo(
            id = "ghat_pattiseema",
            nameEn = "Pattiseema Akhanda Godavari Ghat",
            nameTe = "పట్టిసీమ అఖండ గోదావరి ఘాట్",
            district = District.POLAVARAM,
            tier = GhatTier.TIER_1_AI_CCTV,
            density = CrowdDensity.LOW,
            currentCrowd = 620,
            safeCapacity = 4000,
            entryGate = "Hill Shrine Foot Gate",
            exitGate = "Spillway Bypass Road",
            distanceKmFromHub = 38.0,
            stepFreeRampAvailable = true
        ),
        GhatInfo(
            id = "ghat_eluru_inland",
            nameEn = "Tammileru Confluence Ghat",
            nameTe = "తమ్మిలేరు సంగమం ఘాట్",
            district = District.ELURU,
            tier = GhatTier.TIER_2_VILLAGE_NETWORK,
            density = CrowdDensity.LOW,
            currentCrowd = 450,
            safeCapacity = 3500,
            entryGate = "District Canal Gate",
            exitGate = "Ring Road Exit",
            distanceKmFromHub = 62.0,
            stepFreeRampAvailable = true
        )
    )

    // 2. Pre-Loaded Verified Stays (Official East Godavari List + Regional Stays)
    private val verifiedStays = listOf(
        // Rajamahendravaram Official List
        StayProperty(
            id = "stay_river_bay",
            name = "River Bay Resort",
            district = District.EAST_GODAVARI,
            hubCity = "Rajamahendravaram",
            category = "Resort / Hotel",
            starRating = 4.4,
            priceBand = PriceBand.COMFORT,
            fixedRatePerNight = 3200,
            hasStepFreeAccess = true,
            isPureVeg = false,
            hasParkingOnSite = true,
            contactPhone = "+91 883 244 5566",
            availableRooms = 14,
            distanceToNearestGhatKm = 1.2
        ),
        StayProperty(
            id = "stay_anand_regency",
            name = "Anand Regency Hotel",
            district = District.EAST_GODAVARI,
            hubCity = "Rajamahendravaram",
            category = "Hotel",
            starRating = 4.3,
            priceBand = PriceBand.COMFORT,
            fixedRatePerNight = 2800,
            hasStepFreeAccess = true,
            isPureVeg = false,
            hasParkingOnSite = true,
            contactPhone = "+91 883 246 1201",
            availableRooms = 8,
            distanceToNearestGhatKm = 1.8
        ),
        StayProperty(
            id = "stay_manjeera",
            name = "Manjeera International",
            district = District.EAST_GODAVARI,
            hubCity = "Rajamahendravaram",
            category = "Hotel",
            starRating = 4.1,
            priceBand = PriceBand.STANDARD,
            fixedRatePerNight = 1800,
            hasStepFreeAccess = true,
            isPureVeg = true,
            hasParkingOnSite = true,
            contactPhone = "+91 883 247 3344",
            availableRooms = 19,
            distanceToNearestGhatKm = 2.4
        ),
        StayProperty(
            id = "stay_sitara",
            name = "Sitara Residency & Grand",
            district = District.EAST_GODAVARI,
            hubCity = "Rajamahendravaram",
            category = "Hotel",
            starRating = 4.0,
            priceBand = PriceBand.STANDARD,
            fixedRatePerNight = 1500,
            hasStepFreeAccess = true,
            isPureVeg = true,
            hasParkingOnSite = true,
            contactPhone = "+91 883 245 8899",
            availableRooms = 12,
            distanceToNearestGhatKm = 1.5
        ),
        StayProperty(
            id = "stay_jetty_grand",
            name = "Jetty Grand Hotel",
            district = District.EAST_GODAVARI,
            hubCity = "Rajamahendravaram",
            category = "Hotel",
            starRating = 4.2,
            priceBand = PriceBand.STANDARD,
            fixedRatePerNight = 1900,
            hasStepFreeAccess = true,
            isPureVeg = false,
            hasParkingOnSite = true,
            contactPhone = "+91 883 248 9900",
            availableRooms = 6,
            distanceToNearestGhatKm = 2.0
        ),
        StayProperty(
            id = "stay_la_hospin",
            name = "La Hospin Hotel",
            district = District.EAST_GODAVARI,
            hubCity = "Rajamahendravaram",
            category = "Hotel",
            starRating = 4.3,
            priceBand = PriceBand.COMFORT,
            fixedRatePerNight = 3100,
            hasStepFreeAccess = true,
            isPureVeg = false,
            hasParkingOnSite = true,
            contactPhone = "+91 883 666 4444",
            availableRooms = 11,
            distanceToNearestGhatKm = 1.9
        ),
        StayProperty(
            id = "stay_shelton",
            name = "Shelton Hotel",
            district = District.EAST_GODAVARI,
            hubCity = "Rajamahendravaram",
            category = "Hotel",
            starRating = 4.4,
            priceBand = PriceBand.PREMIUM,
            fixedRatePerNight = 4800,
            hasStepFreeAccess = true,
            isPureVeg = false,
            hasParkingOnSite = true,
            contactPhone = "+91 883 249 7788",
            availableRooms = 5,
            distanceToNearestGhatKm = 3.1
        ),
        StayProperty(
            id = "stay_bommana",
            name = "Hotel Bommana Residency",
            district = District.EAST_GODAVARI,
            hubCity = "Rajamahendravaram",
            category = "Lodge",
            starRating = 3.7,
            priceBand = PriceBand.STANDARD,
            fixedRatePerNight = 1200,
            hasStepFreeAccess = false,
            isPureVeg = true,
            hasParkingOnSite = true,
            contactPhone = "+91 883 242 1100",
            availableRooms = 22,
            distanceToNearestGhatKm = 0.9
        ),
        StayProperty(
            id = "stay_aptdc_kovvur",
            name = "APTDC Haritha Hotel Kovvur",
            district = District.EAST_GODAVARI,
            hubCity = "Kovvur",
            category = "Govt Guest House",
            starRating = 4.2,
            priceBand = PriceBand.STANDARD,
            fixedRatePerNight = 1400,
            hasStepFreeAccess = true,
            isPureVeg = true,
            hasParkingOnSite = true,
            contactPhone = "+91 8813 231 222",
            availableRooms = 25,
            distanceToNearestGhatKm = 0.4
        ),
        StayProperty(
            id = "stay_kotla_kovvur",
            name = "Kotla v3 Grand Kovvur",
            district = District.EAST_GODAVARI,
            hubCity = "Kovvur",
            category = "Hotel",
            starRating = 4.8,
            priceBand = PriceBand.STANDARD,
            fixedRatePerNight = 1750,
            hasStepFreeAccess = true,
            isPureVeg = true,
            hasParkingOnSite = true,
            contactPhone = "+91 8813 234 567",
            availableRooms = 15,
            distanceToNearestGhatKm = 0.7
        ),
        StayProperty(
            id = "stay_ttd_choultry",
            name = "TTD Pilgrim Choultry Rajahmundry",
            district = District.EAST_GODAVARI,
            hubCity = "Rajamahendravaram",
            category = "Dharmashala / Choultry",
            starRating = 4.6,
            priceBand = PriceBand.ECONOMY,
            fixedRatePerNight = 250,
            hasStepFreeAccess = true,
            isPureVeg = true,
            hasParkingOnSite = true,
            contactPhone = "+91 883 241 0001",
            availableRooms = 60,
            distanceToNearestGhatKm = 1.1
        ),
        StayProperty(
            id = "stay_pushkar_tent_city",
            name = "Govt Pushkar Tent City Dowleswaram",
            district = District.EAST_GODAVARI,
            hubCity = "Dowleswaram",
            category = "Pilgrim Tent City",
            starRating = 4.0,
            priceBand = PriceBand.ECONOMY,
            fixedRatePerNight = 0, // Free Community Shelter
            hasStepFreeAccess = true,
            isPureVeg = true,
            hasParkingOnSite = true,
            contactPhone = "1800-425-2027",
            availableRooms = 350,
            distanceToNearestGhatKm = 0.5
        ),

        // Konaseema & Delta Regional Stays
        StayProperty(
            id = "stay_rio_vista",
            name = "Rio Vista Resorts Sompalli",
            district = District.KONASEEMA,
            hubCity = "Sompalli",
            category = "Resort",
            starRating = 4.5,
            priceBand = PriceBand.COMFORT,
            fixedRatePerNight = 3600,
            hasStepFreeAccess = true,
            isPureVeg = false,
            hasParkingOnSite = true,
            contactPhone = "+91 8862 255 111",
            availableRooms = 8,
            distanceToNearestGhatKm = 3.5
        ),
        StayProperty(
            id = "stay_palavelli_dindi",
            name = "Sterling Palavelli Dindi",
            district = District.KONASEEMA,
            hubCity = "Dindi",
            category = "Resort",
            starRating = 4.1,
            priceBand = PriceBand.PREMIUM,
            fixedRatePerNight = 4900,
            hasStepFreeAccess = true,
            isPureVeg = false,
            hasParkingOnSite = true,
            contactPhone = "+91 8862 259 800",
            availableRooms = 7,
            distanceToNearestGhatKm = 2.8
        ),
        StayProperty(
            id = "stay_somisetty_tatipaka",
            name = "Hotel Somisetty Landmark Tatipaka",
            district = District.KONASEEMA,
            hubCity = "Tatipaka",
            category = "Hotel",
            starRating = 4.4,
            priceBand = PriceBand.STANDARD,
            fixedRatePerNight = 1600,
            hasStepFreeAccess = true,
            isPureVeg = true,
            hasParkingOnSite = true,
            contactPhone = "+91 8862 222 345",
            availableRooms = 14,
            distanceToNearestGhatKm = 4.0
        ),
        StayProperty(
            id = "stay_amalapuram_grand",
            name = "Monday Hotels Amalapuram",
            district = District.KONASEEMA,
            hubCity = "Amalapuram",
            category = "Hotel",
            starRating = 4.7,
            priceBand = PriceBand.COMFORT,
            fixedRatePerNight = 2400,
            hasStepFreeAccess = true,
            isPureVeg = false,
            hasParkingOnSite = true,
            contactPhone = "+91 8856 231 999",
            availableRooms = 18,
            distanceToNearestGhatKm = 8.5
        ),
        StayProperty(
            id = "stay_anand_vihar_ambajipeta",
            name = "Anand Vihar Ambajipeta",
            district = District.KONASEEMA,
            hubCity = "Ambajipeta",
            category = "Lodge",
            starRating = 4.9,
            priceBand = PriceBand.STANDARD,
            fixedRatePerNight = 1350,
            hasStepFreeAccess = true,
            isPureVeg = true,
            hasParkingOnSite = true,
            contactPhone = "+91 8856 244 555",
            availableRooms = 12,
            distanceToNearestGhatKm = 6.2
        ),

        // West Godavari & Bhimavaram Hub
        StayProperty(
            id = "stay_surya_narsapur",
            name = "Surya Residency Narsapur",
            district = District.WEST_GODAVARI,
            hubCity = "Narasapuram",
            category = "Hotel",
            starRating = 4.9,
            priceBand = PriceBand.STANDARD,
            fixedRatePerNight = 1500,
            hasStepFreeAccess = true,
            isPureVeg = true,
            hasParkingOnSite = true,
            contactPhone = "+91 8814 278 123",
            availableRooms = 16,
            distanceToNearestGhatKm = 0.8
        ),
        StayProperty(
            id = "stay_pr_royal_bhimavaram",
            name = "Hotel PR Royal Bhimavaram",
            district = District.WEST_GODAVARI,
            hubCity = "Bhimavaram",
            category = "Hotel",
            starRating = 4.8,
            priceBand = PriceBand.COMFORT,
            fixedRatePerNight = 2600,
            hasStepFreeAccess = true,
            isPureVeg = false,
            hasParkingOnSite = true,
            contactPhone = "+91 8816 233 444",
            availableRooms = 20,
            distanceToNearestGhatKm = 24.0 // Town fallback to Narasapuram
        ),
        StayProperty(
            id = "stay_brk_perupalem",
            name = "BRK Guest House Perupalem",
            district = District.WEST_GODAVARI,
            hubCity = "Perupalem",
            category = "Homestay",
            starRating = 4.7,
            priceBand = PriceBand.STANDARD,
            fixedRatePerNight = 1400,
            hasStepFreeAccess = false,
            isPureVeg = true,
            hasParkingOnSite = true,
            contactPhone = "+91 8814 290 888",
            availableRooms = 10,
            distanceToNearestGhatKm = 12.0
        ),

        // Kakinada Hub
        StayProperty(
            id = "stay_grt_kakinada",
            name = "Grand Kakinada by GRT Hotels",
            district = District.KAKINADA,
            hubCity = "Kakinada",
            category = "Hotel",
            starRating = 4.5,
            priceBand = PriceBand.PREMIUM,
            fixedRatePerNight = 5200,
            hasStepFreeAccess = true,
            isPureVeg = false,
            hasParkingOnSite = true,
            contactPhone = "+91 884 233 3333",
            availableRooms = 9,
            distanceToNearestGhatKm = 18.0
        ),
        StayProperty(
            id = "stay_paradigm_kakinada",
            name = "Paradigm Sarovar Portico Kakinada",
            district = District.KAKINADA,
            hubCity = "Kakinada",
            category = "Hotel",
            starRating = 4.4,
            priceBand = PriceBand.COMFORT,
            fixedRatePerNight = 3400,
            hasStepFreeAccess = true,
            isPureVeg = false,
            hasParkingOnSite = true,
            contactPhone = "+91 884 669 9999",
            availableRooms = 14,
            distanceToNearestGhatKm = 19.2
        ),

        // Polavaram & Pattiseema
        StayProperty(
            id = "stay_river_inn_pattiseema",
            name = "River Inn Pattiseema (NH 365B)",
            district = District.POLAVARAM,
            hubCity = "Pattiseema",
            category = "Lodge",
            starRating = 3.9,
            priceBand = PriceBand.STANDARD,
            fixedRatePerNight = 1100,
            hasStepFreeAccess = true,
            isPureVeg = true,
            hasParkingOnSite = true,
            contactPhone = "+91 8811 254 001",
            availableRooms = 16,
            distanceToNearestGhatKm = 1.1
        ),
        StayProperty(
            id = "stay_aaradhya_polavaram",
            name = "Aaradhya Residency Polavaram",
            district = District.POLAVARAM,
            hubCity = "Polavaram",
            category = "Lodge",
            starRating = 4.4,
            priceBand = PriceBand.STANDARD,
            fixedRatePerNight = 1300,
            hasStepFreeAccess = true,
            isPureVeg = true,
            hasParkingOnSite = true,
            contactPhone = "+91 8811 252 890",
            availableRooms = 15,
            distanceToNearestGhatKm = 2.4
        )
    )

    // 3. ANPR Parking Lots
    private val initialParkingLots = listOf(
        ParkingLot(
            id = "park_pushkar_a",
            name = "Pushkar Circle Parking Zone A",
            district = District.EAST_GODAVARI,
            associatedGhatName = "Pushkar Ghat (Central Hub)",
            vehicleTypeSupported = "Cars & 2-Wheelers",
            totalSlots = 850,
            occupiedSlots = 590,
            status = ParkingStatus.FILLING,
            shuttleFrequencyMinutes = 5,
            hasAccessibleBays = true,
            walkingTimeMinutesToGate = 6,
            overflowRedirectName = "Arts College Remote Ground Zone C"
        ),
        ParkingLot(
            id = "park_arts_college_c",
            name = "Govt Arts College Remote Hub (Zone C)",
            district = District.EAST_GODAVARI,
            associatedGhatName = "Pushkar Ghat & Kotilingala",
            vehicleTypeSupported = "Heavy Buses, Travellers & Cars",
            totalSlots = 3500,
            occupiedSlots = 820,
            status = ParkingStatus.FREE,
            shuttleFrequencyMinutes = 3,
            hasAccessibleBays = true,
            walkingTimeMinutesToGate = 18,
            overflowRedirectName = null
        ),
        ParkingLot(
            id = "park_kovvur_goshpada",
            name = "Kovvur Flood Bank Ground",
            district = District.EAST_GODAVARI,
            associatedGhatName = "Kovvur Goshpada Kshetram Ghat",
            vehicleTypeSupported = "Cars, 2-Wheelers & Buses",
            totalSlots = 1800,
            occupiedSlots = 340,
            status = ParkingStatus.FREE,
            shuttleFrequencyMinutes = 6,
            hasAccessibleBays = true,
            walkingTimeMinutesToGate = 4,
            overflowRedirectName = null
        ),
        ParkingLot(
            id = "park_dowleswaram_cotton",
            name = "Dowleswaram Barrage East Ground",
            district = District.EAST_GODAVARI,
            associatedGhatName = "Dowleswaram Barrage Ghat",
            vehicleTypeSupported = "Cars & 2-Wheelers",
            totalSlots = 1200,
            occupiedSlots = 410,
            status = ParkingStatus.FREE,
            shuttleFrequencyMinutes = 8,
            hasAccessibleBays = true,
            walkingTimeMinutesToGate = 5,
            overflowRedirectName = null
        ),
        ParkingLot(
            id = "park_antarvedi_beach",
            name = "Antarvedi Temple Perimeter Parking",
            district = District.KONASEEMA,
            associatedGhatName = "Antarvedi Sagara Sangamam Ghat",
            vehicleTypeSupported = "All Vehicles",
            totalSlots = 2200,
            occupiedSlots = 1100,
            status = ParkingStatus.FREE,
            shuttleFrequencyMinutes = 10,
            hasAccessibleBays = true,
            walkingTimeMinutesToGate = 7
        )
    )

    // Dynamic State Holders
    private val _ghatsState = MutableStateFlow(initialGhats)
    val ghatsState: StateFlow<List<GhatInfo>> = _ghatsState.asStateFlow()

    private val _parkingState = MutableStateFlow(initialParkingLots)
    val parkingState: StateFlow<List<ParkingLot>> = _parkingState.asStateFlow()

    private val _familyMembers = MutableStateFlow(
        listOf(
            FamilyMember(
                id = "fm_1",
                name = "G. Satyanarayana",
                relation = "Self (Group Head)",
                age = 48,
                category = PilgrimCategory.GENERAL_CITIZEN,
                rfidWristbandId = "AP-PK-9910",
                wristbandStatus = "Active & Verified",
                distanceMetersFromHead = 0
            ),
            FamilyMember(
                id = "fm_2",
                name = "G. Lakshmi",
                relation = "Wife",
                age = 44,
                category = PilgrimCategory.RITUAL_PERFORMER,
                rfidWristbandId = "AP-PK-9911",
                wristbandStatus = "Active & Safe",
                distanceMetersFromHead = 1
            ),
            FamilyMember(
                id = "fm_3",
                name = "G. Subbamma",
                relation = "Mother (Senior Citizen)",
                age = 76,
                category = PilgrimCategory.PERSON_WITH_DISABILITY,
                rfidWristbandId = "AP-PK-9912",
                wristbandStatus = "Step-Free Ramp Assigned",
                distanceMetersFromHead = 2
            ),
            FamilyMember(
                id = "fm_4",
                name = "G. Ananya",
                relation = "Daughter (Child)",
                age = 8,
                category = PilgrimCategory.CHILD_WITH_GUARDIAN,
                rfidWristbandId = "AP-PK-8821",
                wristbandStatus = "Child Safety Tag Active",
                distanceMetersFromHead = 3
            )
        )
    )
    val familyMembers: StateFlow<List<FamilyMember>> = _familyMembers.asStateFlow()

    private val _alerts = MutableStateFlow(
        listOf(
            EmergencyAlert(
                id = "alt_1",
                titleEn = "Godavari River Flow Alert",
                titleTe = "గోదావరి నదీ ప్రవాహ హెచ్చరిక",
                messageEn = "River water level normal at 14.1m. Bathing safe inside marked floating safety nets at all designated ghats.",
                messageTe = "నదీ నీటి మట్టం 14.1 మీటర్ల వద్ద సాధారణంగా ఉంది. భద్రతా వలల లోపల మాత్రమే స్నానం ఆమోదించబడింది.",
                severity = AlertSeverity.INFO,
                timestamp = "Just Now"
            ),
            EmergencyAlert(
                id = "alt_2",
                titleEn = "Hydration & ORS Desks Active",
                titleTe = "ఉచిత మంచినీరు & ORS కేంద్రాలు",
                messageEn = "APSWAN & Health Dept have 120 cold RO water and glucose stations open across Rajamahendravaram.",
                messageTe = "రాజమండ్రి అంతటా 120 శుద్ధి చేసిన తాగునీటి మరియు ఓఆర్ఎస్ కేంద్రాలు అందుబాటులో ఉన్నాయి.",
                severity = AlertSeverity.INFO,
                timestamp = "15m ago"
            )
        )
    )
    val alerts: StateFlow<List<EmergencyAlert>> = _alerts.asStateFlow()

    private val _swachhataTickets = MutableStateFlow(
        listOf(
            SwachhataTicket(
                id = "SW-2027-104",
                ghatName = "Pushkar Ghat",
                locationDetail = "Near Gate 2 Changing Enclosure",
                issueType = "Sanitization & Water Refill Requested",
                status = "Resolved (Sanitized at 09:15 AM)",
                reportedAt = "08:45 AM"
            )
        )
    )
    val swachhataTickets: StateFlow<List<SwachhataTicket>> = _swachhataTickets.asStateFlow()

    // Simulation States
    private val _isSurgeSimulated = MutableStateFlow(false)
    val isSurgeSimulated: StateFlow<Boolean> = _isSurgeSimulated.asStateFlow()

    private val _isParkingFullSimulated = MutableStateFlow(false)
    val isParkingFullSimulated: StateFlow<Boolean> = _isParkingFullSimulated.asStateFlow()

    private val _isAmbulanceCorridorSimulated = MutableStateFlow(false)
    val isAmbulanceCorridorSimulated: StateFlow<Boolean> = _isAmbulanceCorridorSimulated.asStateFlow()

    fun getAllStays(): List<StayProperty> = verifiedStays

    // Simulation Toggles for Government Showcase Prototype
    fun togglePushkarGhatSurge(activate: Boolean) {
        _isSurgeSimulated.value = activate
        _ghatsState.update { list ->
            list.map { ghat ->
                if (ghat.id == "ghat_pushkar") {
                    if (activate) {
                        ghat.copy(
                            density = CrowdDensity.CRITICAL,
                            currentCrowd = 10850,
                            isEntryOpen = false,
                            isDiverting = true,
                            safeAlternativeGhatName = "Kovvur Goshpada Kshetram Ghat (6.5 min via Rail-cum-Road Bridge)"
                        )
                    } else {
                        ghat.copy(
                            density = CrowdDensity.MEDIUM,
                            currentCrowd = 5820,
                            isEntryOpen = true,
                            isDiverting = false,
                            safeAlternativeGhatName = null
                        )
                    }
                } else if (ghat.id == "ghat_kovvur") {
                    if (activate) {
                        ghat.copy(density = CrowdDensity.LOW, currentCrowd = 2100)
                    } else {
                        ghat.copy(density = CrowdDensity.LOW, currentCrowd = 1450)
                    }
                } else {
                    ghat
                }
            }
        }

        // Add or remove surge alert
        if (activate) {
            val surgeAlert = EmergencyAlert(
                id = "surge_alert_pushkar",
                titleEn = "ANTI-STAMPEDE DIVERSION: Pushkar Ghat Throttled",
                titleTe = "తొక్కిసలాట నివారణ మళ్లింపు: పుష్కర ఘాట్ నియంత్రణ",
                messageEn = "Pushkar Ghat has reached 98% safe threshold. Ingress gates paused. Divert to Kovvur Goshpada or Dowleswaram Ghat.",
                messageTe = "పుష్కర ఘాట్ 98% సామర్థ్యానికి చేరుకుంది. తొక్కిసలాట జరగకుండా ప్రవేశం తాత్కాలికంగా నిలిపివేయబడింది. కొవ్వూరు లేదా ధవళేశ్వరం వైపు వెళ్ళండి.",
                severity = AlertSeverity.EMERGENCY,
                timestamp = "LIVE DEMO SURGE TRIGGERED"
            )
            _alerts.update { listOf(surgeAlert) + it.filter { a -> a.id != "surge_alert_pushkar" } }
        } else {
            _alerts.update { it.filter { a -> a.id != "surge_alert_pushkar" } }
        }
    }

    fun toggleParkingOverflow(activate: Boolean) {
        _isParkingFullSimulated.value = activate
        _parkingState.update { list ->
            list.map { park ->
                if (park.id == "park_pushkar_a") {
                    if (activate) {
                        park.copy(
                            occupiedSlots = park.totalSlots,
                            status = ParkingStatus.FULL
                        )
                    } else {
                        park.copy(
                            occupiedSlots = 590,
                            status = ParkingStatus.FILLING
                        )
                    }
                } else {
                    park
                }
            }
        }
    }

    fun toggleAmbulanceCorridor(activate: Boolean) {
        _isAmbulanceCorridorSimulated.value = activate
        if (activate) {
            val corridorAlert = EmergencyAlert(
                id = "corridor_active",
                titleEn = "SMART AMBULANCE CORRIDOR ACTIVE",
                titleTe = "అంబులెన్స్ అత్యవసర మార్గం యాక్టివ్",
                messageEn = "Godavari Bund Road reserved exclusively for emergency medical transport to GSL Medical Hospital. Pilgrim routing shifted to bypass.",
                messageTe = "గోదావరి బండ్ రహదారి అంబులెన్స్ కొరకు కేటాయించబడింది. భక్తులు రింగ్ రోడ్డు బైపాస్ ఉపయోగించండి.",
                severity = AlertSeverity.WARNING,
                timestamp = "CORRIDOR ACTIVE",
                isAmbulanceCorridorActive = true
            )
            _alerts.update { listOf(corridorAlert) + it.filter { a -> a.id != "corridor_active" } }
        } else {
            _alerts.update { it.filter { a -> a.id != "corridor_active" } }
        }
    }

    fun addSwachhataComplaint(ghatName: String, detail: String, type: String) {
        val newTicket = SwachhataTicket(
            id = "SW-2027-${(100..999).random()}",
            ghatName = ghatName,
            locationDetail = detail,
            issueType = type,
            status = "Registered • Ground Staff Alerted",
            reportedAt = "Just now"
        )
        _swachhataTickets.update { listOf(newTicket) + it }
    }

    fun addFamilyMember(member: FamilyMember) {
        _familyMembers.update { it + member }
    }

    // Dynamic Route Engine (Module A)
    fun calculateRoutes(
        destinationGhatId: String,
        isElderlyOrPwd: Boolean
    ): List<RouteOption> {
        val destinationGhat = _ghatsState.value.find { it.id == destinationGhatId } 
            ?: _ghatsState.value.first()
        val isSurge = _isSurgeSimulated.value && destinationGhat.id == "ghat_pushkar"
        val isCorridor = _isAmbulanceCorridorSimulated.value

        if (isSurge) {
            // Anti-Stampede Hard Guardrail: NEVER route into a throttled/critical gate
            return listOf(
                RouteOption(
                    id = "route_safe_diversion",
                    type = RouteType.LEAST_CROWDED,
                    titleEn = "RECOMMENDED SAFE DIVERSION -> Kovvur Goshpada Ghat",
                    titleTe = "సురక్షిత మళ్లింపు మార్గం -> కొవ్వూరు గోష్పాద ఘాట్",
                    durationMinutes = 18,
                    distanceKm = 6.8,
                    walkingMinutes = 4,
                    crowdRiskScore = 2,
                    isStepFree = true,
                    summaryEn = "Crosses Rail-cum-Road bridge. Free remote parking with 0 waiting queue.",
                    summaryTe = "రైల్-కం-రోడ్డు వంతెన మీదుగా వెళ్తుంది. రద్దీ లేదు, ఉచిత పార్కింగ్ అందుబాటులో ఉంది.",
                    isSafeUnderAntiStampedeRule = true,
                    diversionNotice = "PROTECTION ACTIVE: Pushkar Ghat entry closed to prevent stampede hazard."
                ),
                RouteOption(
                    id = "route_dowleswaram",
                    type = RouteType.EASIEST_STEP_FREE,
                    titleEn = "ALTERNATIVE -> Dowleswaram Barrage Ghat",
                    titleTe = "ప్రత్యామ్నాయం -> ధవళేశ్వరం బ్యారేజ్ ఘాట్",
                    durationMinutes = 24,
                    distanceKm = 7.4,
                    walkingMinutes = 5,
                    crowdRiskScore = 3,
                    isStepFree = true,
                    summaryEn = "Wide riverside embankment with dedicated step-free bathing ramps.",
                    summaryTe = "విశాలమైన నదీ తీరం మరియు వీల్ చైర్ ర్యాంపులు అందుబాటులో ఉన్నాయి.",
                    isSafeUnderAntiStampedeRule = true
                )
            )
        }

        // Standard 3 Routes
        val fastestDuration = if (isCorridor) 28 else 19
        val corridorNote = if (isCorridor) " (Ambulance Corridor bypass in effect)" else ""

        return listOf(
            RouteOption(
                id = "route_fastest",
                type = RouteType.FASTEST,
                titleEn = "Fastest Route (Via Main Arterial Road)",
                titleTe = "అతి వేగవంతమైన మార్గం (ప్రధాన రహదారి)$corridorNote",
                durationMinutes = fastestDuration,
                distanceKm = 4.2,
                walkingMinutes = 8,
                crowdRiskScore = 5,
                isStepFree = false,
                summaryEn = "Shortest drive time. Standard parking at Zone A with 8-min walk to Gate C.",
                summaryTe = "తక్కువ ప్రయాణ సమయం. జోన్ A వద్ద పార్కింగ్, అక్కడి నుండి గేట్ C కి 8 నిమిషాల నడక.",
                isSafeUnderAntiStampedeRule = true
            ),
            RouteOption(
                id = "route_least_crowded",
                type = RouteType.LEAST_CROWDED,
                titleEn = "Least Crowded (Upstream Shuttle Link)",
                titleTe = "తక్కువ రద్దీ గల మార్గం (షటిల్ బస్సు ద్వారా)",
                durationMinutes = 22,
                distanceKm = 5.8,
                walkingMinutes = 3,
                crowdRiskScore = 2,
                isStepFree = true,
                summaryEn = "Park at Arts College Remote Hub; take free AC e-shuttle bus straight to Gate.",
                summaryTe = "ఆర్ట్స్ కళాశాల గ్రౌండ్స్ వద్ద వాహనం నిలపండి; ఉచిత ఈ-షటిల్ బస్సులో గేట్ వద్దకు వెళ్ళండి.",
                isSafeUnderAntiStampedeRule = true
            ),
            RouteOption(
                id = "route_easiest",
                type = RouteType.EASIEST_STEP_FREE,
                titleEn = "Easiest & Step-Free (Senior & Wheelchair Friendly)",
                titleTe = "సులభమైన వీల్ చైర్ & వృద్ధుల మార్గం",
                durationMinutes = 25,
                distanceKm = 5.1,
                walkingMinutes = 2,
                crowdRiskScore = 2,
                isStepFree = true,
                summaryEn = "Zero stairs. Battery cart feeder to Gate D, dedicated disability ramp to water line.",
                summaryTe = "మెట్లు లేవు. గేట్ D వరకు బ్యాటరీ వాహనం, నీటి వరకు సురక్షిత ర్యాంప్ మార్గం.",
                isSafeUnderAntiStampedeRule = true
            )
        )
    }

    // Module D: Budget-Based Trip Planner Engine
    fun generateTripPlan(
        budgetTier: PriceBand,
        groupSize: Int,
        elderlyCount: Int,
        childCount: Int,
        includeTempleCircuit: Boolean,
        travelMode: String
    ): TripPlanItinerary {
        val totalDays = if (includeTempleCircuit) 3 else 2
        val perPersonBudget = when (budgetTier) {
            PriceBand.ECONOMY -> 750
            PriceBand.STANDARD -> 1800
            PriceBand.COMFORT -> 3500
            PriceBand.PREMIUM -> 6000
        }
        val totalEstimate = perPersonBudget * groupSize * totalDays

        val assignedSlot = when {
            elderlyCount > 0 -> "05:30 AM – 07:00 AM (Cool morning, lowest crowd density)"
            childCount > 0 -> "06:30 AM – 08:00 AM (Family safe window with wristband helpdesk)"
            else -> "06:00 AM – 07:30 AM (Early auspicious low-traffic slot)"
        }

        val recommendedGhat = when {
            elderlyCount > 0 -> "Kovvur Goshpada Kshetram Ghat (Direct step-free ramps)"
            budgetTier == PriceBand.ECONOMY -> "Dowleswaram Barrage Ghat (Free Tent City & Shuttles)"
            else -> "Pushkar Ghat or Kotilingala Ghat (Central Hub)"
        }

        val staySuggestion = when (budgetTier) {
            PriceBand.ECONOMY -> "TTD Choultry or Govt Pushkar Tent City Dowleswaram (₹0 - ₹250/night)"
            PriceBand.STANDARD -> "APTDC Haritha Hotel Kovvur or Sitara Residency (₹1,400 - ₹1,700/night)"
            PriceBand.COMFORT -> "River Bay Resort or Anand Regency (₹2,800 - ₹3,200/night)"
            PriceBand.PREMIUM -> "Shelton Hotel or Grand Kakinada by GRT (₹4,800+/night)"
        }

        val temples = if (includeTempleCircuit) {
            listOf(
                "Pithapuram (Pada Gaya Kshetram)",
                "Annavaram (Sri Veera Venkata Satyanarayana Swamy)",
                "Dwaraka Tirumala (Chinna Tirupati)",
                "Antarvedi (Sri Lakshmi Narasimha Swamy)"
            )
        } else {
            listOf("Kotilingeswara Swamy Temple", "Markandeya Temple Rajahmundry")
        }

        return TripPlanItinerary(
            totalDays = totalDays,
            assignedLowDensitySlot = assignedSlot,
            recommendedGhat = recommendedGhat,
            staySuggestion = staySuggestion,
            travelModeSummary = travelMode,
            estimatedBudgetInr = totalEstimate,
            templeCircuitCovered = temples,
            crowdRiskAdvice = "Pre-assigned time slot avoids peak 9:00 AM - 12:00 PM delta rush."
        )
    }
}
