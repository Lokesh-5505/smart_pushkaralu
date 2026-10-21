package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.GhatInfo
import com.example.model.RouteOption
import com.example.model.RouteType
import com.example.ui.theme.*

@Composable
fun NavigationScreen(
    language: AppLanguage,
    ghats: List<GhatInfo>,
    selectedGhatId: String,
    onSelectGhat: (String) -> Unit,
    routes: List<RouteOption>,
    isSurgeActive: Boolean,
    isCorridorActive: Boolean,
    modifier: Modifier = Modifier
) {
    var selectedRouteId by remember(routes) { mutableStateOf(routes.firstOrNull()?.id ?: "") }
    var originHub by remember { mutableStateOf("Rajamahendravaram Railway Station") }

    val currentGhat = ghats.find { it.id == selectedGhatId } ?: ghats.first()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("navigation_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 120.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Origin & Destination Header Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "TRAFFIC-AWARE ROUTE ENGINE (MODULE A)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Origin Input Row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(RiverCyan)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Origin Point:", fontSize = 10.sp, color = Color.Gray)
                            Text(
                                text = originHub,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = NavyPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(color = Color(0xFFE2E8F0), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(10.dp))

                    // Destination Ghat Dropdown
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(if (isSurgeActive) SosEmergencyRed else CrowdLowGreen)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Destination Ghat:", fontSize = 10.sp, color = Color.Gray)
                            Text(
                                text = if (language == AppLanguage.TELUGU) currentGhat.nameTe else currentGhat.nameEn,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = NavyPrimary
                            )
                        }
                    }
                }
            }
        }

        // Anti-Stampede Guardrail Alert (if surge or corridor active)
        if (isSurgeActive) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CrowdHighBg),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.GppBad,
                            contentDescription = null,
                            tint = SosEmergencyRed,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "STAMPEDE PREVENTION GUARDRAIL TRIGGERED",
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                color = CrowdHighRed
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Pushkar Ghat entry throttled. The router strictly blocks routes to choked gates and has auto-diverted you to safe sister ghats across the river.",
                                fontSize = 11.sp,
                                color = Color.DarkGray,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }

        if (isCorridorActive) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2FE)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalHospital,
                            contentDescription = null,
                            tint = RiverCyan,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "SMART EMERGENCY CORRIDOR ACTIVE",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = RiverCyan
                            )
                            Text(
                                text = "Godavari Bund Road cleared for hospital ambulances. Routes adjusted via bypass.",
                                fontSize = 11.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }

        // Spatial GIS Map Canvas Mockup
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavyDark),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .testTag("spatial_gis_map_canvas")
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height

                        // Draw Godavari River flow (curved blue stream)
                        val riverPath = Path().apply {
                            moveTo(0f, h * 0.45f)
                            cubicTo(w * 0.3f, h * 0.35f, w * 0.6f, h * 0.65f, w, h * 0.55f)
                            lineTo(w, h * 0.85f)
                            cubicTo(w * 0.6f, h * 0.95f, w * 0.3f, h * 0.65f, 0f, h * 0.75f)
                            close()
                        }
                        drawPath(path = riverPath, color = Color(0xFF0369A1).copy(alpha = 0.5f))

                        // Draw Rail-cum-Road Bridge
                        drawLine(
                            color = Color(0xFFF4A261),
                            start = Offset(w * 0.25f, h * 0.3f),
                            end = Offset(w * 0.35f, h * 0.75f),
                            strokeWidth = 6f
                        )

                        // Draw Ambulance Corridor Highlight (if active)
                        if (isCorridorActive) {
                            drawLine(
                                color = Color(0xFFDC2626),
                                start = Offset(w * 0.35f, h * 0.32f),
                                end = Offset(w * 0.85f, h * 0.45f),
                                strokeWidth = 8f
                            )
                        }

                        // Draw Recommended Pilgrim Path
                        val pilgrimPath = Path().apply {
                            moveTo(w * 0.15f, h * 0.2f)
                            lineTo(w * 0.25f, h * 0.3f)
                            lineTo(w * 0.35f, h * 0.75f)
                            lineTo(w * 0.55f, h * 0.78f)
                        }
                        drawPath(
                            path = pilgrimPath,
                            color = if (isSurgeActive) Color(0xFF16A34A) else Color(0xFFFBBF24),
                            style = Stroke(width = 6f)
                        )

                        // Draw Pins (Origin, Pushkar Ghat, Kovvur Ghat)
                        drawCircle(color = Color(0xFF38BDF8), radius = 10f, center = Offset(w * 0.15f, h * 0.2f))
                        drawCircle(
                            color = if (isSurgeActive) Color(0xFFDC2626) else Color(0xFF16A34A),
                            radius = 12f,
                            center = Offset(w * 0.7f, h * 0.45f)
                        )
                        drawCircle(color = Color(0xFF16A34A), radius = 12f, center = Offset(w * 0.55f, h * 0.78f))
                    }

                    // Map Overlay Indicators
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "RUDA GIS • GODAVARI DELTA SATELLITE",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = if (isSurgeActive) "DIVERSION OVERLAY: Pushkar -> Kovvur Link" else "LIVE TRAFFIC & SHUTTLE SENSORS ACTIVE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSurgeActive) GoldLight else Color.White
                        )
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(CrowdLowGreen)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "OFFLINE CACHED",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // 3 Route Choice Cards (Fastest, Least Crowded, Easiest)
        item {
            Text(
                text = "SELECT ROUTE OPTION (3 CHOICES)",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }

        items(routes) { route ->
            val isSelected = route.id == selectedRouteId
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color(0xFFEFF6FF) else Color.White
                ),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedRouteId = route.id }
                    .testTag("route_option_${route.id}")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedRouteId = route.id }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (language == AppLanguage.TELUGU) route.titleTe else route.titleEn,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = NavyPrimary
                            )
                        }

                        if (route.type == RouteType.LEAST_CROWDED) {
                            Text(
                                text = "RECOMMENDED",
                                color = CrowdLowGreen,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(CrowdLowBg)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Schedule, contentDescription = null, tint = NavySecondary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "${route.durationMinutes} mins", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "${route.distanceKm} km", fontSize = 12.sp, color = Color.Gray)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DirectionsWalk, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "${route.walkingMinutes}m walk", fontSize = 12.sp, color = Color.Gray)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (language == AppLanguage.TELUGU) route.summaryTe else route.summaryEn,
                        fontSize = 11.sp,
                        color = Color.DarkGray
                    )

                    if (route.diversionNotice != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = route.diversionNotice,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SosEmergencyRed
                        )
                    }
                }
            }
        }
    }
}
