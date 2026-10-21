package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.model.AppLanguage
import com.example.model.ParkingLot
import com.example.model.ParkingStatus
import com.example.ui.theme.*

@Composable
fun SmartParkingScreen(
    language: AppLanguage,
    parkingLots: List<ParkingLot>,
    userVehicleNo: String,
    isParkingFullSimulated: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("smart_parking_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 120.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // ANPR Vehicle Pass Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavyDark),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CameraAlt, contentDescription = null, tint = GoldLight)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ANPR AUTOMATIC PLATE RECOGNITION (MODULE C)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldLight
                            )
                        }
                        Text(
                            text = "PASS VERIFIED",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = CrowdLowGreen,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(CrowdLowBg)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Registered Vehicle:", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
                            Text(
                                text = userVehicleNo,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.QrCode, contentDescription = "ANPR QR", tint = NavyDark, modifier = Modifier.size(36.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Gate cameras will scan your plate automatically. Boom barrier opens seamlessly without physical tickets.",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Upstream Interception Warning (if parking full simulated)
        if (isParkingFullSimulated) {
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
                            imageVector = Icons.Default.AltRoute,
                            contentDescription = null,
                            tint = SosEmergencyRed,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "UPSTREAM INTERCEPTION & REDIRECTION ACTIVE",
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                color = CrowdHighRed
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Pushkar Circle Zone A is at 100% capacity. Incoming vehicles are being automatically intercepted before the river road bottleneck and rerouted to Arts College Remote Hub.",
                                fontSize = 11.sp,
                                color = Color.DarkGray,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }

        // Feeder Shuttle Bus Tracker Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFEFF6FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.DirectionsBus, contentDescription = null, tint = RiverCyan, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Free Ghat Feeder E-Shuttle Bus Network",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = NavyPrimary
                            )
                            Text(
                                text = "Connects all remote parking hubs directly to category gates",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ShuttleInfoItem(label = "Next Bus", value = "2 Mins", sub = "Shuttle #14")
                        ShuttleInfoItem(label = "Frequency", value = "Every 3m", sub = "AC Electric")
                        ShuttleInfoItem(label = "Fare", value = "100% Free", sub = "Govt Funded")
                    }
                }
            }
        }

        // Live Parking Lots
        item {
            Text(
                text = "GHAT-WISE SMART PARKING ZONES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }

        items(parkingLots) { lot ->
            ParkingLotCard(lot = lot)
        }
    }
}

@Composable
fun ParkingLotCard(lot: ParkingLot) {
    val isFull = lot.status == ParkingStatus.FULL
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("parking_card_${lot.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = lot.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = NavyPrimary
                    )
                    Text(
                        text = "Serves: ${lot.associatedGhatName}",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Supported: ${lot.vehicleTypeSupported}",
                        fontSize = 10.sp,
                        color = NavySecondary,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Status Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            when (lot.status) {
                                ParkingStatus.FREE -> CrowdLowBg
                                ParkingStatus.FILLING -> CrowdMedBg
                                ParkingStatus.FULL -> CrowdHighBg
                            }
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = lot.status.labelEn,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = when (lot.status) {
                            ParkingStatus.FREE -> CrowdLowGreen
                            ParkingStatus.FILLING -> CrowdMedAmber
                            ParkingStatus.FULL -> SosEmergencyRed
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Capacity Bar
            val ratio = (lot.occupiedSlots.toFloat() / lot.totalSlots.toFloat()).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { ratio },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = if (isFull) SosEmergencyRed else if (ratio > 0.7f) CrowdMedAmber else CrowdLowGreen,
                trackColor = Color(0xFFE2E8F0)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${lot.occupiedSlots} / ${lot.totalSlots} Slots Taken",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Text(
                    text = "${lot.totalSlots - lot.occupiedSlots} available",
                    fontSize = 11.sp,
                    color = if (isFull) SosEmergencyRed else CrowdLowGreen,
                    fontWeight = FontWeight.Bold
                )
            }

            if (isFull && lot.overflowRedirectName != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.TurnRight, contentDescription = null, tint = CrowdMedAmber, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Auto Redirect to: ${lot.overflowRedirectName}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CrowdMedAmber
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShuttleInfoItem(label: String, value: String, sub: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 10.sp, color = Color.Gray)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Black, color = NavyPrimary)
        Text(text = sub, fontSize = 9.sp, color = CrowdLowGreen, fontWeight = FontWeight.Bold)
    }
}
