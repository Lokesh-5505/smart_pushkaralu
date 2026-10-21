package com.example.util

import com.example.model.AppLanguage

object Localization {

    fun appTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "స్మార్ట్ పుష్కరాలు 2027"
        AppLanguage.HINDI -> "स्मार्ट पुष्करालु 2027"
        AppLanguage.ENGLISH -> "Smart Pushkaralu 2027"
    }

    fun govtBadge(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "ఆంధ్రప్రదేశ్ ప్రభుత్వం • ప్రజా భద్రతా వ్యవస్థ"
        AppLanguage.HINDI -> "आंध्र प्रदेश सरकार • वास्तविक समय सार्वजनिक सुरक्षा"
        AppLanguage.ENGLISH -> "GOVT OF ANDHRA PRADESH • PUBLIC SAFETY"
    }

    fun tabHome(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "హోమ్"
        AppLanguage.HINDI -> "होम"
        AppLanguage.ENGLISH -> "Home"
    }

    fun tabPlanner(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "ట్రిప్ ప్లానర్"
        AppLanguage.HINDI -> "यात्रा योजना"
        AppLanguage.ENGLISH -> "Planner"
    }

    fun tabRoutes(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "నావిగేషన్"
        AppLanguage.HINDI -> "नेविगेशन"
        AppLanguage.ENGLISH -> "Routes"
    }

    fun tabStays(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "వసతి గృహాలు"
        AppLanguage.HINDI -> "होटल व निवास"
        AppLanguage.ENGLISH -> "Stays"
    }

    fun tabParking(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "పార్కింగ్"
        AppLanguage.HINDI -> "पार्किंग"
        AppLanguage.ENGLISH -> "Parking"
    }

    fun tabGroup(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "కుటుంబం"
        AppLanguage.HINDI -> "परिवार समूह"
        AppLanguage.ENGLISH -> "My Group"
    }

    fun tabSafety(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "వైద్యం & SOS"
        AppLanguage.HINDI -> "सुरक्षा व SOS"
        AppLanguage.ENGLISH -> "Safety/SOS"
    }

    fun tabSettings(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "సెట్టింగ్స్"
        AppLanguage.HINDI -> "सेटिंग्स"
        AppLanguage.ENGLISH -> "Settings"
    }

    fun sosButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "1-ట్యాప్ అత్యవసర SOS"
        AppLanguage.HINDI -> "1-टैप आपातकालीन SOS"
        AppLanguage.ENGLISH -> "1-TAP EMERGENCY SOS"
    }

    fun liveDensity(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "ఘాట్ ప్రత్యక్ష రద్దీ స్థితి"
        AppLanguage.HINDI -> "घाट की लाइव भीड़ स्थिति"
        AppLanguage.ENGLISH -> "Live Ghat Crowd Density"
    }

    fun planTripCardTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "ట్రిప్ & సమయ స్లాట్ ప్లానర్"
        AppLanguage.HINDI -> "यात्रा व स्नान समय योजना"
        AppLanguage.ENGLISH -> "Plan Trip & Safe Slot"
    }

    fun navigateCardTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "ట్రాఫిక్ & డైవర్షన్ రూట్స్"
        AppLanguage.HINDI -> "ट्रैफिक व डायवर्जन रूट्स"
        AppLanguage.ENGLISH -> "Traffic & Safe Routes"
    }

    fun parkingCardTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "స్మార్ట్ ANPR పార్కింగ్"
        AppLanguage.HINDI -> "स्मार्ट ANPR पार्किंग"
        AppLanguage.ENGLISH -> "Smart ANPR Parking"
    }

    fun stayCardTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "ధ్రువీకరించిన హోటళ్ళు & ఆశ్రమాలు"
        AppLanguage.HINDI -> "सत्यापित होटल व आश्रम"
        AppLanguage.ENGLISH -> "Verified Stays & Shelters"
    }

    fun eClinicTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "ఈ-క్లినిక్ డాక్టర్ కన్సల్టేషన్"
        AppLanguage.HINDI -> "ई-क्लिनिक डॉक्टर परामर्श"
        AppLanguage.ENGLISH -> "E-Clinic Doctor Consult"
    }

    fun missingPersonTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "తప్పిపోయిన వారి రిపోర్ట్"
        AppLanguage.HINDI -> "गुमशुदा व्यक्ति रिपोर्ट"
        AppLanguage.ENGLISH -> "Report Missing Person"
    }

    fun childWristbandTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "పిల్లల భద్రతా రిస్ట్‌బ్యాండ్"
        AppLanguage.HINDI -> "बाल सुरक्षा रिस्टबैंड"
        AppLanguage.ENGLISH -> "Child Safety Wristband"
    }

    fun swachhataTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.TELUGU -> "స్వచ్ఛత & పారిశుధ్య సమస్యల ఫిర్యాదు"
        AppLanguage.HINDI -> "स्वच्छता व जल शिकायत"
        AppLanguage.ENGLISH -> "Swachhata Cleanliness Desk"
    }
}
