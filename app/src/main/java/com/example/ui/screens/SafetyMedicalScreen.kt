package com.example.ui.screens

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
import com.example.model.EmergencyAlert
import com.example.ui.theme.*
import com.example.util.Localization

@Composable
fun SafetyMedicalScreen(
    language: AppLanguage,
    alerts: List<EmergencyAlert>,
    onTriggerSos: () -> Unit,
    onOpenEClinic: () -> Unit,
    onOpenMissingPerson: () -> Unit,
    onOpenSwachhata: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("safety_medical_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 120.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Giant 1-Tap SOS Button Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SosEmergencyRed),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTriggerSos() }
                    .testTag("big_sos_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Emergency,
                            contentDescription = "SOS",
                            tint = Color.White,
                            modifier = Modifier.size(44.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = Localization.sosButton(language),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Instantly sends your exact GPS coordinates to Sector 4 Command Desk, 108 River Ambulance Boat & Ghat Medical Camp.",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 15.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        // Dedicated Field Emergency Corridors & Medical Posts
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "RAPID MEDICAL SERVICES & E-CLINIC",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // E-Clinic Button
                    Button(
                        onClick = onOpenEClinic,
                        colors = ButtonDefaults.buttonColors(containerColor = RiverCyan),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("eclinic_trigger_button")
                    ) {
                        Icon(Icons.Default.VideoCall, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = Localization.eClinicTitle(language),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Missing Person Button
                    OutlinedButton(
                        onClick = onOpenMissingPerson,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("missing_person_trigger_button")
                    ) {
                        Icon(Icons.Default.PersonSearch, contentDescription = null, tint = GoldAccent)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = Localization.missingPersonTitle(language),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = NavyPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Swachhata Cleanliness
                    OutlinedButton(
                        onClick = onOpenSwachhata,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("swachhata_trigger_button")
                    ) {
                        Icon(Icons.Default.CleaningServices, contentDescription = null, tint = CrowdLowGreen)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = Localization.swachhataTitle(language),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = NavyPrimary
                        )
                    }
                }
            }
        }

        // Official Godavari River & Weather Safety Bulletin
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Waves, contentDescription = null, tint = RiverCyan)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "GODAVARI RIVER & WEATHER TELEMETRY",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = NavyPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TelemetryPill(label = "River Level", value = "14.1m", status = "Normal Safe")
                        TelemetryPill(label = "Current Flow", value = "1.8 m/s", status = "Inside Nets")
                        TelemetryPill(label = "Temperature", value = "31°C", status = "ORS Desks Open")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "120 Deep-Water Rescue Boats & 450 Trained Life Guards active with AP Disaster Response Force (APDRF).",
                        fontSize = 11.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }

        // Active Emergency Bulletins List
        item {
            Text(
                text = "OFFICIAL GOVERNMENT ADVISORIES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }

        items(alerts) { alert ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (alert.severity == com.example.model.AlertSeverity.EMERGENCY) CrowdHighBg else Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (language == AppLanguage.TELUGU) alert.titleTe else alert.titleEn,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = if (alert.severity == com.example.model.AlertSeverity.EMERGENCY) SosEmergencyRed else NavyPrimary
                        )
                        Text(
                            text = alert.timestamp,
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (language == AppLanguage.TELUGU) alert.messageTe else alert.messageEn,
                        fontSize = 11.sp,
                        color = TextPrimaryLight
                    )
                }
            }
        }
    }
}

@Composable
fun TelemetryPill(label: String, value: String, status: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 10.sp, color = Color.Gray)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Black, color = NavyPrimary)
        Text(text = status, fontSize = 9.sp, color = CrowdLowGreen, fontWeight = FontWeight.Bold)
    }
}
