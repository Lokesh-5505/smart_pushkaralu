package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*

@Composable
fun SosEmergencyDialog(
    countdown: Int,
    dispatchedOfficer: String?,
    onCancel: () -> Unit
) {
    Dialog(onDismissRequest = onCancel) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("sos_emergency_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(CrowdHighBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Emergency,
                        contentDescription = "SOS Alert",
                        tint = SosEmergencyRed,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "EMERGENCY SOS ACTIVE",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    color = SosEmergencyRed
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (countdown > 0) {
                    Text(
                        text = "Broadcasting live GPS in $countdown seconds...",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LinearProgressIndicator(
                        progress = { (3 - countdown) / 3f },
                        modifier = Modifier.fillMaxWidth(),
                        color = SosEmergencyRed,
                        trackColor = CrowdHighBg
                    )
                } else {
                    Text(
                        text = "LIVE GPS DISPATCHED TO COMMAND CENTER",
                        fontWeight = FontWeight.Bold,
                        color = CrowdLowGreen,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = BackgroundLight),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Assigned Field Responder:",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = dispatchedOfficer ?: "Sector 4 Duty Officer",
                                fontWeight = FontWeight.Bold,
                                color = NavyPrimary,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Emergency Medical Team & 108 Boat alerted",
                                fontSize = 11.sp,
                                color = CrowdLowGreen,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("cancel_sos_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray)
                ) {
                    Text("CANCEL SOS (FALSE ALARM)", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun EClinicDialog(
    onDismiss: () -> Unit
) {
    var isConnecting by remember { mutableStateOf(false) }
    var connectedDoctor by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .testTag("eclinic_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0F2FE)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MedicalServices,
                            contentDescription = null,
                            tint = RiverCyan
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "AP E-Clinic Tele-Consultation",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = NavyPrimary
                        )
                        Text(
                            text = "Connected to GSL & RMC Government Hospitals",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (connectedDoctor == null) {
                    Text(
                        text = "Instant video/audio consultation for pilgrims feeling dehydrated, exhausted, or needing urgent medication guidance.",
                        fontSize = 13.sp,
                        color = Color.DarkGray,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Available on duty right now:", fontSize = 11.sp, color = Color.Gray)
                            Text(text = "• 8 General Physicians (Sunstroke & Dehydration)", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            Text(text = "• 4 Pediatricians (Child Care)", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            Text(text = "• 3 Cardiologists & Emergency Medics", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            isConnecting = true
                            connectedDoctor = "Dr. K. Srinivas, MD (Rajamahendravaram Govt General Hospital)"
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("connect_doctor_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = RiverCyan),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.VideoCall, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("CONNECT DOCTOR NOW (FREE)", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NavyDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Active Call With Doctor", color = GoldLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(connectedDoctor ?: "", color = Color.White, fontSize = 11.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Prescription and nearest free medicine desk location will be sent via SMS.",
                        fontSize = 11.sp,
                        color = CrowdLowGreen,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Close", color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun MissingPersonDialog(
    onDismiss: () -> Unit
) {
    var personName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var clothesDescription by remember { mutableStateOf("") }
    var lastSeenLocation by remember { mutableStateOf("Pushkar Ghat Circle") }
    var isSubmitted by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .testTag("missing_person_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(CrowdMedBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.PersonSearch, contentDescription = null, tint = CrowdMedAmber)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Report Missing Person",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = NavyPrimary
                        )
                        Text(
                            text = "Broadcasts instantly to 440 Ghat Officer Booths",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (!isSubmitted) {
                    OutlinedTextField(
                        value = personName,
                        onValueChange = { personName = it },
                        label = { Text("Person / Child Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = age,
                            onValueChange = { age = it },
                            label = { Text("Age") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = lastSeenLocation,
                            onValueChange = { lastSeenLocation = it },
                            label = { Text("Last Seen Ghat / Area") },
                            modifier = Modifier.weight(2f),
                            singleLine = true
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = clothesDescription,
                        onValueChange = { clothesDescription = it },
                        label = { Text("Clothes Color & Identification Marks") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { isSubmitted = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_missing_person_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("BROADCAST TO FIELD POLICE & PA SYSTEMS", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                } else {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CrowdLowBg),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = CrowdLowGreen)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Alert Dispatched Successfully", fontWeight = FontWeight.Bold, color = CrowdLowGreen)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Photo match and details sent to Sector 4 Command Desk, RFID Wristband scanners, and PA announcements.",
                                fontSize = 12.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Close", color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun SwachhataDialog(
    onDismiss: () -> Unit,
    onSubmit: (ghat: String, location: String, issue: String) -> Unit
) {
    var selectedGhat by remember { mutableStateOf("Pushkar Ghat") }
    var locationDetail by remember { mutableStateOf("") }
    var issueType by remember { mutableStateOf("Drinking Water Refill Needed") }

    val issues = listOf(
        "Drinking Water Refill Needed",
        "Bio-Toilet Sanitization Required",
        "Changing Room Maintenance",
        "Garbage Bin Overflowing",
        "Step-Free Ramp Obstacle"
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .testTag("swachhata_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFDCFCE7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CleaningServices, contentDescription = null, tint = CrowdLowGreen)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Swachhata Cleanliness Desk",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = NavyPrimary
                        )
                        Text(
                            text = "Direct alert to Municipal Field Sanitation Squad",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = selectedGhat,
                    onValueChange = { selectedGhat = it },
                    label = { Text("Bathing Ghat") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = locationDetail,
                    onValueChange = { locationDetail = it },
                    label = { Text("Specific Location (e.g., Gate 2 changing room)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Select Cleanliness Issue:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(6.dp))

                issues.forEach { issue ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = issueType == issue,
                            onClick = { issueType = issue }
                        )
                        Text(text = issue, fontSize = 12.sp, color = Color.DarkGray)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onSubmit(selectedGhat, locationDetail.ifBlank { "Main Ghat Area" }, issueType)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_swachhata_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = CrowdLowGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("SUBMIT FOR IMMEDIATE CLEANING", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        }
    }
}
