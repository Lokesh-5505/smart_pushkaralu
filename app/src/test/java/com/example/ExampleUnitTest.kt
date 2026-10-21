package com.example

import com.example.data.PushkaraluRepository
import com.example.model.*
import com.example.util.Localization
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ExampleUnitTest {

    @Before
    fun setUp() {
        // Reset simulations to baseline
        PushkaraluRepository.togglePushkarGhatSurge(false)
        PushkaraluRepository.toggleParkingOverflow(false)
        PushkaraluRepository.toggleAmbulanceCorridor(false)
    }

    @Test
    fun testBaselineRoutes_ProvideThreeOptions() {
        val routes = PushkaraluRepository.calculateRoutes("ghat_pushkar", isElderlyOrPwd = false)
        assertEquals(3, routes.size)
        assertTrue(routes.any { it.type == RouteType.FASTEST })
        assertTrue(routes.any { it.type == RouteType.LEAST_CROWDED })
        assertTrue(routes.any { it.type == RouteType.EASIEST_STEP_FREE })
    }

    @Test
    fun testAntiStampedeGuardrail_WhenPushkarGhatSurges_EnforcesDiversion() {
        // Trigger simulated crowd surge at Pushkar Ghat
        PushkaraluRepository.togglePushkarGhatSurge(true)

        val pushkarGhat = PushkaraluRepository.ghatsState.value.find { it.id == "ghat_pushkar" }
        assertNotNull(pushkarGhat)
        assertEquals(CrowdDensity.CRITICAL, pushkarGhat?.density)
        assertFalse(pushkarGhat?.isEntryOpen ?: true)
        assertTrue(pushkarGhat?.isDiverting ?: false)

        // Route engine MUST NOT route into Pushkar Ghat; must offer safe diversion to Kovvur
        val routes = PushkaraluRepository.calculateRoutes("ghat_pushkar", isElderlyOrPwd = false)
        assertTrue(routes.isNotEmpty())
        val safeRoute = routes.first()
        assertTrue(safeRoute.titleEn.contains("SAFE DIVERSION", ignoreCase = true))
        assertTrue(safeRoute.titleEn.contains("Kovvur", ignoreCase = true))
    }

    @Test
    fun testUpstreamParkingInterception_WhenZoneAFills_RedirectsToArtsCollege() {
        PushkaraluRepository.toggleParkingOverflow(true)

        val zoneA = PushkaraluRepository.parkingState.value.find { it.id == "park_pushkar_a" }
        assertNotNull(zoneA)
        assertEquals(ParkingStatus.FULL, zoneA?.status)
        assertEquals("Arts College Remote Ground Zone C", zoneA?.overflowRedirectName)
    }

    @Test
    fun testTripPlanner_AssignsEarlyMorningSlotForElderly() {
        val plan = PushkaraluRepository.generateTripPlan(
            budgetTier = PriceBand.STANDARD,
            groupSize = 4,
            elderlyCount = 1,
            childCount = 1,
            includeTempleCircuit = true,
            travelMode = "Train + Bus Shuttle"
        )
        assertNotNull(plan)
        assertEquals(3, plan.totalDays)
        assertTrue(plan.assignedLowDensitySlot.contains("05:30 AM", ignoreCase = true))
        assertEquals(4, plan.templeCircuitCovered.size)
    }

    @Test
    fun testTrilingualLocalization() {
        assertEquals("స్మార్ట్ పుష్కరాలు 2027", Localization.appTitle(AppLanguage.TELUGU))
        assertEquals("स्मार्ट पुष्करालु 2027", Localization.appTitle(AppLanguage.HINDI))
        assertEquals("Smart Pushkaralu 2027", Localization.appTitle(AppLanguage.ENGLISH))
    }
}
