package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.model.AppLanguage
import com.example.model.PriceBand
import com.example.model.TripPlanItinerary
import com.example.ui.theme.*

@Composable
fun TripPlannerScreen(
    language: AppLanguage,
    currentPlan: TripPlanItinerary?,
    onGeneratePlan: (
        budgetTier: PriceBand,
        groupSize: Int,
        elderlyCount: Int,
        childCount: Int,
        includeTempleCircuit: Boolean,
        travelMode: String
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTier by remember { mutableStateOf(PriceBand.STANDARD) }
    var groupSize by remember { mutableIntStateOf(4) }
    var elderlyCount by remember { mutableIntStateOf(1) }
    var childCount by remember { mutableIntStateOf(1) }
    var includeTempleCircuit by remember { mutableStateOf(true) }
    var travelMode by remember { mutableStateOf("Train + Free Pushkar Bus Shuttle") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("trip_planner_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 120.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavyDark),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = GoldLight)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "PRE-TRIP CROWD DISPERSAL (MODULE D)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Intelligent Bathing Slot & Budget Planner",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Spreads pilgrims away from peak hours (9 AM–12 PM) and assigns safe, off-peak ghats based on your family demographics.",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        lineHeight = 15.sp
                    )
                }
            }
        }

        // Budget Tier Selection
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "1. Select Budget Preference Tier",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = NavyPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    PriceBand.values().forEach { tier ->
                        val isSelected = selectedTier == tier
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) Color(0xFFFEF3C7) else Color.Transparent)
                                .clickable { selectedTier = tier }
                                .padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedTier = tier }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = tier.label,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = if (isSelected) NavyPrimary else Color.DarkGray
                                )
                                Text(
                                    text = when (tier) {
                                        PriceBand.ECONOMY -> "Free pilgrim tent city / dharmashala + public bus"
                                        PriceBand.STANDARD -> "Verified budget lodge / hotel + own car or shared cab"
                                        PriceBand.COMFORT -> "3-Star hotel with family rooms + step-free access"
                                        PriceBand.PREMIUM -> "Resort / Premium hotel closest to ghat"
                                    },
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }

        // Group Size & Demographics Steppers
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "2. Pilgrim Group Demographics",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = NavyPrimary
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    StepperRow(
                        label = "Total Group Size",
                        subtext = "Total members traveling together",
                        count = groupSize,
                        onDecrement = { if (groupSize > 1) groupSize-- },
                        onIncrement = { if (groupSize < 20) groupSize++ }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    StepperRow(
                        label = "Elderly Devotees (65+ yrs)",
                        subtext = "Enforces cool morning slots & step-free ramps",
                        count = elderlyCount,
                        onDecrement = { if (elderlyCount > 0) elderlyCount-- },
                        onIncrement = { if (elderlyCount < groupSize) elderlyCount++ }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    StepperRow(
                        label = "Young Children (<12 yrs)",
                        subtext = "Assigns wristband issuance desks & safety nets",
                        count = childCount,
                        onDecrement = { if (childCount > 0) childCount-- },
                        onIncrement = { if (childCount < groupSize) childCount++ }
                    )
                }
            }
        }

        // Temple Circuit & Travel Mode
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "3. Travel Mode & Optional Temple Circuit",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = NavyPrimary
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Temple Circuit Checkbox
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFF8FAFC))
                            .clickable { includeTempleCircuit = !includeTempleCircuit }
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = includeTempleCircuit,
                            onCheckedChange = { includeTempleCircuit = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Include Godavari Temple Circuit",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = NavyPrimary
                            )
                            Text(
                                text = "Links Pithapuram, Annavaram, Dwaraka Tirumala & Antarvedi",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            onGeneratePlan(
                                selectedTier,
                                groupSize,
                                elderlyCount,
                                childCount,
                                includeTempleCircuit,
                                travelMode
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("generate_plan_button")
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = GoldLight)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "CALCULATE ITINERARY & ASSIGN SLOT",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Generated Itinerary Result Card
        if (currentPlan != null) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("itinerary_result_card")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "OFFICIAL ITINERARY PASS",
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                color = CrowdMedAmber
                            )
                            Text(
                                text = "${currentPlan.totalDays} DAYS TOUR",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = NavyPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Assigned Low Density Slot
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(CrowdLowBg),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.AccessTime, contentDescription = null, tint = CrowdLowGreen)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Assigned Low-Density Bathing Slot:",
                                        fontSize = 10.sp,
                                        color = Color.Gray,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = currentPlan.assignedLowDensitySlot,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CrowdLowGreen
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Recommended Ghat: ${currentPlan.recommendedGhat}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = NavyPrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Stay Recommendation: ${currentPlan.staySuggestion}",
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Estimated Budget: ₹${currentPlan.estimatedBudgetInr} for $groupSize people (${currentPlan.totalDays} Days)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = NavyDark
                        )

                        if (currentPlan.templeCircuitCovered.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Covered Temples:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                            currentPlan.templeCircuitCovered.forEach { temple ->
                                Text(
                                    text = "• $temple",
                                    fontSize = 11.sp,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StepperRow(
    label: String,
    subtext: String,
    count: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = NavyPrimary)
            Text(text = subtext, fontSize = 10.sp, color = Color.Gray)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onDecrement,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE2E8F0))
            ) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
            }
            Text(
                text = "$count",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            IconButton(
                onClick = onIncrement,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE2E8F0))
            ) {
                Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(16.dp))
            }
        }
    }
}
