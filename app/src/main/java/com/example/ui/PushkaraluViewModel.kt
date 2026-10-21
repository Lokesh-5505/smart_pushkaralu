package com.example.ui

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.PushkaraluRepository
import com.example.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Locale

enum class ScreenTab {
    HOME,
    PLANNER,
    ROUTES,
    GHAT_DETAIL,
    STAYS,
    PARKING,
    GROUP,
    SAFETY,
    SETTINGS
}

data class UiState(
    val currentTab: ScreenTab = ScreenTab.HOME,
    val language: AppLanguage = AppLanguage.TELUGU,
    val selectedCategory: PilgrimCategory = PilgrimCategory.GENERAL_CITIZEN,
    val selectedGhatId: String = "ghat_pushkar",
    val selectedDistrictFilter: District? = null,
    val staysVegOnlyFilter: Boolean = false,
    val staysStepFreeFilter: Boolean = false,
    val staysParkingFilter: Boolean = false,
    val staysPriceBandFilter: PriceBand? = null,
    val isTtsSpeaking: Boolean = false,
    val isSosActive: Boolean = false,
    val sosCountdown: Int = 3,
    val sosDispatchedOfficer: String? = null,
    val isEClinicModalOpen: Boolean = false,
    val isMissingPersonModalOpen: Boolean = false,
    val isSwachhataModalOpen: Boolean = false,
    val tripPlanResult: TripPlanItinerary? = null,
    val userRegisteredVehicleNo: String = "AP39 AB 1234",
    val isHighContrastEnabled: Boolean = false,
    val isLargeTextEnabled: Boolean = false
)

class PushkaraluViewModel(application: Application) : AndroidViewModel(application), TextToSpeech.OnInitListener {

    private val repository = PushkaraluRepository

    val ghats: StateFlow<List<GhatInfo>> = repository.ghatsState
    val parkingLots: StateFlow<List<ParkingLot>> = repository.parkingState
    val familyMembers: StateFlow<List<FamilyMember>> = repository.familyMembers
    val alerts: StateFlow<List<EmergencyAlert>> = repository.alerts
    val swachhataTickets: StateFlow<List<SwachhataTicket>> = repository.swachhataTickets

    val isSurgeSimulated: StateFlow<Boolean> = repository.isSurgeSimulated
    val isParkingFullSimulated: StateFlow<Boolean> = repository.isParkingFullSimulated
    val isAmbulanceCorridorSimulated: StateFlow<Boolean> = repository.isAmbulanceCorridorSimulated

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    private var sosJob: Job? = null

    init {
        tts = TextToSpeech(application, this)
        // Pre-generate default itinerary for quick review
        generatePlan(
            budgetTier = PriceBand.STANDARD,
            groupSize = 4,
            elderlyCount = 1,
            childCount = 1,
            includeTempleCircuit = true,
            travelMode = "Train to Rajahmundry + Feeder Bus"
        )
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTtsReady = true
            updateTtsLocale(_uiState.value.language)
        }
    }

    private fun updateTtsLocale(language: AppLanguage) {
        tts?.let { engine ->
            val locale = when (language) {
                AppLanguage.TELUGU -> Locale("te", "IN")
                AppLanguage.HINDI -> Locale("hi", "IN")
                AppLanguage.ENGLISH -> Locale("en", "IN")
            }
            val res = engine.setLanguage(locale)
            if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                engine.language = Locale.ENGLISH
            }
        }
    }

    fun speak(text: String) {
        if (!isTtsReady || text.isBlank()) return
        tts?.stop()
        _uiState.update { it.copy(isTtsSpeaking = true) }
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "puskaralu_tts_id")
        viewModelScope.launch {
            delay(4000)
            _uiState.update { it.copy(isTtsSpeaking = false) }
        }
    }

    fun stopSpeaking() {
        tts?.stop()
        _uiState.update { it.copy(isTtsSpeaking = false) }
    }

    fun setTab(tab: ScreenTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun setLanguage(lang: AppLanguage) {
        _uiState.update { it.copy(language = lang) }
        updateTtsLocale(lang)
        val prompt = when (lang) {
            AppLanguage.TELUGU -> "భాష తెలుగుగా మార్చబడింది. గోదావరి పుష్కరాలు 2027 అధికారిక భద్రతా యాప్‌కు స్వాగతం."
            AppLanguage.HINDI -> "भाषा हिंदी में बदली गई। गोदावरी पुष्करालु आधिकारिक सुरक्षा ऐप में आपका स्वागत है।"
            AppLanguage.ENGLISH -> "Language changed to English. Welcome to Smart Pushkaralu 2027 official public safety app."
        }
        speak(prompt)
    }

    fun setCategory(category: PilgrimCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
        val prompt = when (_uiState.value.language) {
            AppLanguage.TELUGU -> "మీ వర్గం: ${category.nameTe}. కేటాయించిన మార్గం: ${category.assignedGate}."
            AppLanguage.HINDI -> "आपकी श्रेणी: ${category.nameHi}. निर्धारित द्वार: ${category.assignedGate}."
            AppLanguage.ENGLISH -> "Selected Category: ${category.nameEn}. Dedicated gate: ${category.assignedGate}."
        }
        speak(prompt)
    }

    fun selectGhat(ghatId: String) {
        _uiState.update { it.copy(selectedGhatId = ghatId, currentTab = ScreenTab.GHAT_DETAIL) }
    }

    // Filters for Stay Directory
    fun setDistrictFilter(district: District?) {
        _uiState.update { it.copy(selectedDistrictFilter = district) }
    }

    fun toggleVegFilter() {
        _uiState.update { it.copy(staysVegOnlyFilter = !it.staysVegOnlyFilter) }
    }

    fun toggleStepFreeFilter() {
        _uiState.update { it.copy(staysStepFreeFilter = !it.staysStepFreeFilter) }
    }

    fun toggleParkingFilter() {
        _uiState.update { it.copy(staysParkingFilter = !it.staysParkingFilter) }
    }

    fun setPriceBandFilter(band: PriceBand?) {
        _uiState.update { it.copy(staysPriceBandFilter = band) }
    }

    fun toggleHighContrast() {
        _uiState.update { it.copy(isHighContrastEnabled = !it.isHighContrastEnabled) }
    }

    fun toggleLargeText() {
        _uiState.update { it.copy(isLargeTextEnabled = !it.isLargeTextEnabled) }
    }

    // Modal Toggles
    fun setEClinicModal(open: Boolean) {
        _uiState.update { it.copy(isEClinicModalOpen = open) }
    }

    fun setMissingPersonModal(open: Boolean) {
        _uiState.update { it.copy(isMissingPersonModalOpen = open) }
    }

    fun setSwachhataModal(open: Boolean) {
        _uiState.update { it.copy(isSwachhataModalOpen = open) }
    }

    // 1-Tap SOS Trigger
    fun triggerSos() {
        sosJob?.cancel()
        _uiState.update { it.copy(isSosActive = true, sosCountdown = 3, sosDispatchedOfficer = null) }
        val prompt = when (_uiState.value.language) {
            AppLanguage.TELUGU -> "అత్యవసర రక్షణ అలర్ట్ ప్రారంభమైంది. మీ ప్రత్యక్ష జీపీఎస్ లొకేషన్ సెక్టార్ 4 కమాండ్ ఆఫీసర్‌కు పంపబడుతోంది."
            AppLanguage.HINDI -> "आपातकालीन एसओएस सक्रिय। आपका लाइव जीपीएस सेक्टर 4 पुलिस और मेडिकल कैंप को भेजा जा रहा है।"
            AppLanguage.ENGLISH -> "Emergency SOS activated. Live GPS coordinates sent to Sector 4 Command Officer and Medical Post."
        }
        speak(prompt)

        sosJob = viewModelScope.launch {
            for (i in 3 downTo 1) {
                _uiState.update { it.copy(sosCountdown = i) }
                delay(1000)
            }
            _uiState.update {
                it.copy(
                    sosCountdown = 0,
                    sosDispatchedOfficer = "Inspector K. R. Rao (Sector 4 Ghat Duty) • ETA 2.4 Mins"
                )
            }
        }
    }

    fun cancelSos() {
        sosJob?.cancel()
        _uiState.update { it.copy(isSosActive = false, sosCountdown = 3, sosDispatchedOfficer = null) }
        speak("SOS cancelled. Stay safe.")
    }

    // Simulation Trigger Handlers
    fun toggleSurgeSimulation() {
        val next = !isSurgeSimulated.value
        repository.togglePushkarGhatSurge(next)
        if (next) {
            val alertSpeech = when (_uiState.value.language) {
                AppLanguage.TELUGU -> "హెచ్చరిక: పుష్కర ఘాట్ వద్ద అధిక రద్దీ గుర్తించబడింది. తొక్కిసలాట నివారణ కోసం ప్రవేశం నిలిపివేయబడింది. దయచేసి కొవ్వూరు లేదా ధవళేశ్వరం ఘాట్‌కు వెళ్లండి."
                AppLanguage.HINDI -> "चेतावनी: पुष्कर घाट पर भारी भीड़। भगदड़ रोकने के लिए प्रवेश बंद। कृपया कोव्वूर घाट की ओर जाएं।"
                AppLanguage.ENGLISH -> "Warning: Heavy surge at Pushkar Ghat. Ingress halted under anti-stampede protocol. Divert to Kovvur Ghat."
            }
            speak(alertSpeech)
        }
    }

    fun toggleParkingOverflowSimulation() {
        val next = !isParkingFullSimulated.value
        repository.toggleParkingOverflow(next)
        if (next) {
            val alertSpeech = when (_uiState.value.language) {
                AppLanguage.TELUGU -> "పుష్కర సర్కిల్ పార్కింగ్ జోన్ నిండిపోయింది. వాహనాలు ఆర్ట్స్ కళాశాల గ్రౌండ్ వైపు మళ్లించబడుతున్నాయి."
                AppLanguage.HINDI -> "पुष्कर पार्किंग फुल। वाहन आर्ट्स कॉलेज ग्राउंड की ओर मोड़ दिए गए हैं।"
                AppLanguage.ENGLISH -> "Pushkar Circle Parking full. Upstream interception routing to Arts College Remote Hub with free shuttle."
            }
            speak(alertSpeech)
        }
    }

    fun toggleAmbulanceCorridorSimulation() {
        val next = !isAmbulanceCorridorSimulated.value
        repository.toggleAmbulanceCorridor(next)
        if (next) {
            val alertSpeech = when (_uiState.value.language) {
                AppLanguage.TELUGU -> "స్మార్ట్ అంబులెన్స్ కారిడార్ అమలులో ఉంది. గోదావరి బండ్ రోడ్డు ఖాళీ చేయండి."
                AppLanguage.HINDI -> "स्मार्ट एम्बुलेंस कॉरिडोर सक्रिय। गोदावरी बंड रोड खाली करें।"
                AppLanguage.ENGLISH -> "Smart Emergency Corridor active. Keep Godavari Bund Road clear for medical vehicles."
            }
            speak(alertSpeech)
        }
    }

    fun getFilteredStays(): List<StayProperty> {
        val all = repository.getAllStays()
        val s = _uiState.value
        return all.filter { stay ->
            (s.selectedDistrictFilter == null || stay.district == s.selectedDistrictFilter) &&
            (!s.staysVegOnlyFilter || stay.isPureVeg) &&
            (!s.staysStepFreeFilter || stay.hasStepFreeAccess) &&
            (!s.staysParkingFilter || stay.hasParkingOnSite) &&
            (s.staysPriceBandFilter == null || stay.priceBand == s.staysPriceBandFilter)
        }
    }

    fun getActiveRoutes(): List<RouteOption> {
        return repository.calculateRoutes(
            destinationGhatId = _uiState.value.selectedGhatId,
            isElderlyOrPwd = _uiState.value.selectedCategory.isStepFreeRequired
        )
    }

    fun generatePlan(
        budgetTier: PriceBand,
        groupSize: Int,
        elderlyCount: Int,
        childCount: Int,
        includeTempleCircuit: Boolean,
        travelMode: String
    ) {
        val plan = repository.generateTripPlan(
            budgetTier = budgetTier,
            groupSize = groupSize,
            elderlyCount = elderlyCount,
            childCount = childCount,
            includeTempleCircuit = includeTempleCircuit,
            travelMode = travelMode
        )
        _uiState.update { it.copy(tripPlanResult = plan) }
    }

    fun submitSwachhataComplaint(ghatName: String, location: String, issue: String) {
        repository.addSwachhataComplaint(ghatName, location, issue)
        _uiState.update { it.copy(isSwachhataModalOpen = false) }
        speak("Cleanliness complaint submitted. Sanitation team notified.")
    }

    fun addFamilyMember(member: FamilyMember) {
        repository.addFamilyMember(member)
        speak("New family member added to safety circle.")
    }

    override fun onCleared() {
        super.onCleared()
        tts?.stop()
        tts?.shutdown()
    }
}
