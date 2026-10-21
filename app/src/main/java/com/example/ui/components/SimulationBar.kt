package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.ui.theme.*

@Composable
fun SimulationControlBar(
    isSurgeActive: Boolean,
    isParkingFullActive: Boolean,
    isCorridorActive: Boolean,
    onToggleSurge: () -> Unit,
    onToggleParking: () -> Unit,
    onToggleCorridor: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag("simulation_control_bar"),
        colors = CardDefaults.cardColors(
            containerColor = NavyDark
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Header Row / Tap to Expand
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(GoldAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Science,
                            contentDescription = "Simulation",
                            tint = NavyDark,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "GOV SHOWCASE: CROWD & CRISIS SIMULATION",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = GoldLight,
                        fontSize = 11.sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val activeCount = listOf(isSurgeActive, isParkingFullActive, isCorridorActive).count { it }
                    if (activeCount > 0) {
                        Text(
                            text = "$activeCount ACTIVE",
                            color = SosEmergencyRed,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(CrowdHighBg)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Expand simulation bar",
                        tint = Color.White
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = "Tap any button below to test real-time anti-stampede rerouting & ANPR interception live in the app:",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        lineHeight = 15.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Simulation Trigger 1: Crowd Surge at Pushkar Ghat
                    FilledTonalButton(
                        onClick = onToggleSurge,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = if (isSurgeActive) SosEmergencyRed else Color.White.copy(alpha = 0.15f),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("sim_toggle_surge")
                    ) {
                        Icon(
                            imageVector = if (isSurgeActive) Icons.Default.CheckCircle else Icons.Default.WarningAmber,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isSurgeActive) "RESET: Clear Pushkar Ghat Surge" else "SIMULATE: Crowd Surge at Pushkar Ghat",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Simulation Trigger 2: Parking Zone Full (ANPR Interception)
                    FilledTonalButton(
                        onClick = onToggleParking,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = if (isParkingFullActive) GoldAccent else Color.White.copy(alpha = 0.15f),
                            contentColor = if (isParkingFullActive) NavyDark else Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("sim_toggle_parking")
                    ) {
                        Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isParkingFullActive) "RESET: Clear Pushkar Parking" else "SIMULATE: Pushkar Parking FULL (Upstream Divert)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Simulation Trigger 3: Active Ambulance Corridor
                    FilledTonalButton(
                        onClick = onToggleCorridor,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = if (isCorridorActive) RiverCyan else Color.White.copy(alpha = 0.15f),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("sim_toggle_corridor")
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalHospital,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isCorridorActive) "RESET: Clear Ambulance Corridor" else "SIMULATE: Smart Ambulance Corridor (Clear Bund Rd)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
