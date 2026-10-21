package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.ScreenTab
import com.example.ui.components.CrowdBadge
import com.example.ui.theme.*

@Composable
fun GhatDetailScreen(
    language: AppLanguage,
    ghat: GhatInfo,
    onNavigateTab: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("ghat_detail_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 120.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Main Ghat Header Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CrowdBadge(density = ghat.density)
                        Text(
                            text = if (ghat.tier == GhatTier.TIER_1_AI_CCTV) "TIER 1 • CCTV AI COUNT" else "TIER 2 • FIELD UPDATED",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavySecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (language == AppLanguage.TELUGU) ghat.nameTe else ghat.nameEn,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                    Text(
                        text = "${ghat.district.nameEn} • ${ghat.distanceKmFromHub} km from Central Hub",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Capacity & Headcount
                    val ratio = (ghat.currentCrowd.toFloat() / ghat.safeCapacity.toFloat()).coerceIn(0f, 1f)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Text(text = "Live People in Ghat Area:", fontSize = 11.sp, color = Color.Gray)
                            Text(
                                text = "${ghat.currentCrowd} Pilgrims",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = NavyDark
                            )
                        }
                        Text(
                            text = "Safe Cap: ${ghat.safeCapacity}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { ratio },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = when (ghat.density) {
                            CrowdDensity.LOW -> CrowdLowGreen
                            CrowdDensity.MEDIUM -> CrowdMedAmber
                            CrowdDensity.HIGH -> CrowdHighRed
                            CrowdDensity.CRITICAL -> SosEmergencyRed
                        },
                        trackColor = Color(0xFFE2E8F0)
                    )
                }
            }
        }

        // Unidirectional Ingress & Egress Architecture (Anti-Stampede Layer 4)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavyDark),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.SyncAlt, contentDescription = null, tint = GoldLight)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "UNIDIRECTIONAL CROWD ARCHITECTURE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Separate Ingress & Egress Gates",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Incoming pilgrims and returning bathers never cross paths, eliminating sudden choke points.",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        // Entry Gate
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.12f)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Login, contentDescription = null, tint = CrowdLowGreen, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "ENTRY ONLY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CrowdLowGreen)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = ghat.entryGate, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(text = "One-way into staging bay", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        // Exit Gate
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.12f)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Logout, contentDescription = null, tint = GoldLight, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "EXIT ONLY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GoldLight)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = ghat.exitGate, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(text = "Leads directly to shuttles", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                            }
                        }
                    }
                }
            }
        }

        // Category Gate Allocations
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "DESIGNATED CATEGORY ENTRY LANES",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    PilgrimCategory.values().forEach { cat ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF1F5F9)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = when (cat) {
                                        PilgrimCategory.GENERAL_CITIZEN -> Icons.Default.Groups
                                        PilgrimCategory.RITUAL_PERFORMER -> Icons.Default.SelfImprovement
                                        PilgrimCategory.CHILD_WITH_GUARDIAN -> Icons.Default.ChildCare
                                        PilgrimCategory.PERSON_WITH_DISABILITY -> Icons.Default.Accessible
                                    },
                                    contentDescription = null,
                                    tint = NavyPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = cat.nameEn,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = NavyPrimary
                                )
                                Text(
                                    text = cat.assignedGate,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TempleSaffron
                                )
                            }
                        }
                    }
                }
            }
        }

        // Amenities & River Safety Status
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "GHAT AMENITIES & RIVER SAFETY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AmenityPill(
                            icon = Icons.Default.Wc,
                            label = "${ghat.bioToiletsCount} Bio-Toilets",
                            sublabel = "Sanitized"
                        )
                        AmenityPill(
                            icon = Icons.Default.WaterDrop,
                            label = "${ghat.drinkingWaterPoints} RO Stations",
                            sublabel = "Cold & Free"
                        )
                        AmenityPill(
                            icon = Icons.Default.Accessible,
                            label = "Step-Free Ramp",
                            sublabel = "Wheelchair Ok"
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AmenityPill(
                            icon = Icons.Default.LocalHospital,
                            label = "Field Medical Camp",
                            sublabel = "24x7 Doctors"
                        )
                        AmenityPill(
                            icon = Icons.Default.VideoCall,
                            label = "E-Clinic Booth",
                            sublabel = "Specialist Link"
                        )
                        AmenityPill(
                            icon = Icons.Default.PhonelinkRing,
                            label = "RFID Wristband",
                            sublabel = "Lost Child Desk"
                        )
                    }
                }
            }
        }

        // Quick Jump to Stay & Parking
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onNavigateTab(ScreenTab.STAYS) },
                    colors = ButtonDefaults.buttonColors(containerColor = NavySecondary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Hotel, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Nearby Stays", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = { onNavigateTab(ScreenTab.PARKING) },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.LocalParking, contentDescription = null, tint = NavyDark, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Ghat Parking", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NavyDark)
                }
            }
        }
    }
}

@Composable
fun AmenityPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    sublabel: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFFF1F5F9)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 10.sp, color = NavyPrimary)
        Text(text = sublabel, fontSize = 9.sp, color = CrowdLowGreen)
    }
}
