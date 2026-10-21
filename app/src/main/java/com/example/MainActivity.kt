package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.PushkaraluViewModel
import com.example.ui.ScreenTab
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.util.Localization

class MainActivity : ComponentActivity() {

    private val viewModel: PushkaraluViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val ghats by viewModel.ghats.collectAsStateWithLifecycle()
            val parkingLots by viewModel.parkingLots.collectAsStateWithLifecycle()
            val familyMembers by viewModel.familyMembers.collectAsStateWithLifecycle()
            val alerts by viewModel.alerts.collectAsStateWithLifecycle()
            val swachhataTickets by viewModel.swachhataTickets.collectAsStateWithLifecycle()

            val isSurgeSimulated by viewModel.isSurgeSimulated.collectAsStateWithLifecycle()
            val isParkingFullSimulated by viewModel.isParkingFullSimulated.collectAsStateWithLifecycle()
            val isAmbulanceCorridorSimulated by viewModel.isAmbulanceCorridorSimulated.collectAsStateWithLifecycle()

            // Handle system back navigation to return smoothly to Home
            BackHandler(enabled = uiState.currentTab != ScreenTab.HOME) {
                viewModel.setTab(ScreenTab.HOME)
            }

            SmartPushkaraluTheme(darkTheme = uiState.isHighContrastEnabled) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("main_scaffold"),
                    topBar = {
                        AppHeader(
                            currentLanguage = uiState.language,
                            onLanguageSelected = { lang -> viewModel.setLanguage(lang) },
                            isSpeaking = uiState.isTtsSpeaking,
                            onTtsClick = {
                                if (uiState.isTtsSpeaking) {
                                    viewModel.stopSpeaking()
                                } else {
                                    val prompt = when (uiState.language) {
                                        com.example.model.AppLanguage.TELUGU ->
                                            "స్మార్ట్ పుష్కరాలు 2027 ప్రజా భద్రత యాప్. మీ కేటగిరీ: ${uiState.selectedCategory.nameTe}. పుష్కర ఘాట్ మరియు ఇతర ఘాట్ల వద్ద సురక్షిత స్నానం కోసం సూచనలను అనుసరించండి."
                                        com.example.model.AppLanguage.HINDI ->
                                            "स्मार्ट पुष्करालु 2027 सार्वजनिक सुरक्षा ऐप। आपकी श्रेणी: ${uiState.selectedCategory.nameHi}. सुरक्षित स्नान के लिए दिशा-निर्देशों का पालन करें।"
                                        com.example.model.AppLanguage.ENGLISH ->
                                            "Smart Pushkaralu 2027 Public Safety App. Category: ${uiState.selectedCategory.nameEn}. Follow anti-stampede unidirectional guidance."
                                    }
                                    viewModel.speak(prompt)
                                }
                            },
                            onSosClick = { viewModel.triggerSos() }
                        )
                    },
                    bottomBar = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .windowInsetsPadding(WindowInsets.navigationBars)
                        ) {
                            // Persistent Floating Simulation Bar for Government Review Showcase
                            SimulationControlBar(
                                isSurgeActive = isSurgeSimulated,
                                isParkingFullActive = isParkingFullSimulated,
                                isCorridorActive = isAmbulanceCorridorSimulated,
                                onToggleSurge = { viewModel.toggleSurgeSimulation() },
                                onToggleParking = { viewModel.toggleParkingOverflowSimulation() },
                                onToggleCorridor = { viewModel.toggleAmbulanceCorridorSimulation() }
                            )

                            // Main Navigation Bar with Accessible Labels
                            NavigationBar(
                                containerColor = NavyPrimary,
                                contentColor = Color.White,
                                tonalElevation = 8.dp,
                                modifier = Modifier.testTag("bottom_nav_bar")
                            ) {
                                NavigationBarItem(
                                    selected = uiState.currentTab == ScreenTab.HOME,
                                    onClick = { viewModel.setTab(ScreenTab.HOME) },
                                    icon = { Icon(Icons.Default.Home, contentDescription = Localization.tabHome(uiState.language)) },
                                    label = { Text(Localization.tabHome(uiState.language), fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = NavyDark,
                                        selectedTextColor = GoldLight,
                                        indicatorColor = GoldAccent,
                                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                                        unselectedTextColor = Color.White.copy(alpha = 0.7f)
                                    ),
                                    modifier = Modifier.testTag("nav_item_home")
                                )

                                NavigationBarItem(
                                    selected = uiState.currentTab == ScreenTab.ROUTES,
                                    onClick = { viewModel.setTab(ScreenTab.ROUTES) },
                                    icon = { Icon(Icons.Default.AltRoute, contentDescription = Localization.tabRoutes(uiState.language)) },
                                    label = { Text(Localization.tabRoutes(uiState.language), fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = NavyDark,
                                        selectedTextColor = GoldLight,
                                        indicatorColor = GoldAccent,
                                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                                        unselectedTextColor = Color.White.copy(alpha = 0.7f)
                                    ),
                                    modifier = Modifier.testTag("nav_item_routes")
                                )

                                NavigationBarItem(
                                    selected = uiState.currentTab == ScreenTab.STAYS,
                                    onClick = { viewModel.setTab(ScreenTab.STAYS) },
                                    icon = { Icon(Icons.Default.Hotel, contentDescription = Localization.tabStays(uiState.language)) },
                                    label = { Text(Localization.tabStays(uiState.language), fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = NavyDark,
                                        selectedTextColor = GoldLight,
                                        indicatorColor = GoldAccent,
                                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                                        unselectedTextColor = Color.White.copy(alpha = 0.7f)
                                    ),
                                    modifier = Modifier.testTag("nav_item_stays")
                                )

                                NavigationBarItem(
                                    selected = uiState.currentTab == ScreenTab.PARKING,
                                    onClick = { viewModel.setTab(ScreenTab.PARKING) },
                                    icon = { Icon(Icons.Default.LocalParking, contentDescription = Localization.tabParking(uiState.language)) },
                                    label = { Text(Localization.tabParking(uiState.language), fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = NavyDark,
                                        selectedTextColor = GoldLight,
                                        indicatorColor = GoldAccent,
                                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                                        unselectedTextColor = Color.White.copy(alpha = 0.7f)
                                    ),
                                    modifier = Modifier.testTag("nav_item_parking")
                                )

                                NavigationBarItem(
                                    selected = uiState.currentTab == ScreenTab.GROUP,
                                    onClick = { viewModel.setTab(ScreenTab.GROUP) },
                                    icon = { Icon(Icons.Default.FamilyRestroom, contentDescription = Localization.tabGroup(uiState.language)) },
                                    label = { Text(Localization.tabGroup(uiState.language), fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = NavyDark,
                                        selectedTextColor = GoldLight,
                                        indicatorColor = GoldAccent,
                                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                                        unselectedTextColor = Color.White.copy(alpha = 0.7f)
                                    ),
                                    modifier = Modifier.testTag("nav_item_group")
                                )

                                NavigationBarItem(
                                    selected = uiState.currentTab == ScreenTab.SAFETY,
                                    onClick = { viewModel.setTab(ScreenTab.SAFETY) },
                                    icon = { Icon(Icons.Default.HealthAndSafety, contentDescription = Localization.tabSafety(uiState.language)) },
                                    label = { Text(Localization.tabSafety(uiState.language), fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = NavyDark,
                                        selectedTextColor = GoldLight,
                                        indicatorColor = GoldAccent,
                                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                                        unselectedTextColor = Color.White.copy(alpha = 0.7f)
                                    ),
                                    modifier = Modifier.testTag("nav_item_safety")
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (uiState.currentTab) {
                            ScreenTab.HOME -> {
                                HomeScreen(
                                    language = uiState.language,
                                    category = uiState.selectedCategory,
                                    ghats = ghats,
                                    alerts = alerts,
                                    isSurgeSimulated = isSurgeSimulated,
                                    isAmbulanceCorridorSimulated = isAmbulanceCorridorSimulated,
                                    onNavigateTab = { tab -> viewModel.setTab(tab) },
                                    onSelectGhat = { ghatId -> viewModel.selectGhat(ghatId) },
                                    onOpenEClinic = { viewModel.setEClinicModal(true) },
                                    onOpenMissingPerson = { viewModel.setMissingPersonModal(true) },
                                    onOpenSwachhata = { viewModel.setSwachhataModal(true) }
                                )
                            }
                            ScreenTab.PLANNER -> {
                                TripPlannerScreen(
                                    language = uiState.language,
                                    currentPlan = uiState.tripPlanResult,
                                    onGeneratePlan = { tier, size, elderly, child, temples, mode ->
                                        viewModel.generatePlan(tier, size, elderly, child, temples, mode)
                                    }
                                )
                            }
                            ScreenTab.ROUTES -> {
                                NavigationScreen(
                                    language = uiState.language,
                                    ghats = ghats,
                                    selectedGhatId = uiState.selectedGhatId,
                                    onSelectGhat = { id -> viewModel.selectGhat(id) },
                                    routes = viewModel.getActiveRoutes(),
                                    isSurgeActive = isSurgeSimulated,
                                    isCorridorActive = isAmbulanceCorridorSimulated
                                )
                            }
                            ScreenTab.GHAT_DETAIL -> {
                                val currentGhat = ghats.find { it.id == uiState.selectedGhatId } ?: ghats.first()
                                GhatDetailScreen(
                                    language = uiState.language,
                                    ghat = currentGhat,
                                    onNavigateTab = { tab -> viewModel.setTab(tab) }
                                )
                            }
                            ScreenTab.STAYS -> {
                                StayDirectoryScreen(
                                    language = uiState.language,
                                    stays = viewModel.getFilteredStays(),
                                    selectedDistrict = uiState.selectedDistrictFilter,
                                    onSelectDistrict = { dist -> viewModel.setDistrictFilter(dist) },
                                    vegFilter = uiState.staysVegOnlyFilter,
                                    onToggleVeg = { viewModel.toggleVegFilter() },
                                    stepFreeFilter = uiState.staysStepFreeFilter,
                                    onToggleStepFree = { viewModel.toggleStepFreeFilter() },
                                    parkingFilter = uiState.staysParkingFilter,
                                    onToggleParking = { viewModel.toggleParkingFilter() },
                                    priceBandFilter = uiState.staysPriceBandFilter,
                                    onSelectPriceBand = { band -> viewModel.setPriceBandFilter(band) }
                                )
                            }
                            ScreenTab.PARKING -> {
                                SmartParkingScreen(
                                    language = uiState.language,
                                    parkingLots = parkingLots,
                                    userVehicleNo = uiState.userRegisteredVehicleNo,
                                    isParkingFullSimulated = isParkingFullSimulated
                                )
                            }
                            ScreenTab.GROUP -> {
                                FamilyGroupScreen(
                                    language = uiState.language,
                                    members = familyMembers,
                                    onAddMember = { member -> viewModel.addFamilyMember(member) }
                                )
                            }
                            ScreenTab.SAFETY -> {
                                SafetyMedicalScreen(
                                    language = uiState.language,
                                    alerts = alerts,
                                    onTriggerSos = { viewModel.triggerSos() },
                                    onOpenEClinic = { viewModel.setEClinicModal(true) },
                                    onOpenMissingPerson = { viewModel.setMissingPersonModal(true) },
                                    onOpenSwachhata = { viewModel.setSwachhataModal(true) }
                                )
                            }
                            ScreenTab.SETTINGS -> {
                                SettingsScreen(
                                    currentLanguage = uiState.language,
                                    onSelectLanguage = { lang -> viewModel.setLanguage(lang) },
                                    currentCategory = uiState.selectedCategory,
                                    onSelectCategory = { cat -> viewModel.setCategory(cat) },
                                    isLargeText = uiState.isLargeTextEnabled,
                                    onToggleLargeText = { viewModel.toggleLargeText() },
                                    isHighContrast = uiState.isHighContrastEnabled,
                                    onToggleHighContrast = { viewModel.toggleHighContrast() },
                                    swachhataTickets = swachhataTickets,
                                    onOpenSwachhata = { viewModel.setSwachhataModal(true) }
                                )
                            }
                        }

                        // Modal Overlays
                        if (uiState.isSosActive) {
                            SosEmergencyDialog(
                                countdown = uiState.sosCountdown,
                                dispatchedOfficer = uiState.sosDispatchedOfficer,
                                onCancel = { viewModel.cancelSos() }
                            )
                        }

                        if (uiState.isEClinicModalOpen) {
                            EClinicDialog(
                                onDismiss = { viewModel.setEClinicModal(false) }
                            )
                        }

                        if (uiState.isMissingPersonModalOpen) {
                            MissingPersonDialog(
                                onDismiss = { viewModel.setMissingPersonModal(false) }
                            )
                        }

                        if (uiState.isSwachhataModalOpen) {
                            SwachhataDialog(
                                onDismiss = { viewModel.setSwachhataModal(false) },
                                onSubmit = { ghat, loc, issue ->
                                    viewModel.submitSwachhataComplaint(ghat, loc, issue)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun PushkaraluAppPreview() {
    SmartPushkaraluTheme {
        HomeScreen(
            language = com.example.model.AppLanguage.TELUGU,
            category = com.example.model.PilgrimCategory.GENERAL_CITIZEN,
            ghats = com.example.data.PushkaraluRepository.ghatsState.value,
            alerts = com.example.data.PushkaraluRepository.alerts.value,
            isSurgeSimulated = false,
            isAmbulanceCorridorSimulated = false,
            onNavigateTab = {},
            onSelectGhat = {},
            onOpenEClinic = {},
            onOpenMissingPerson = {},
            onOpenSwachhata = {}
        )
    }
}

