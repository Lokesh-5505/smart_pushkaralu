package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.model.District
import com.example.model.PriceBand
import com.example.model.StayProperty
import com.example.ui.theme.*

@Composable
fun StayDirectoryScreen(
    language: AppLanguage,
    stays: List<StayProperty>,
    selectedDistrict: District?,
    onSelectDistrict: (District?) -> Unit,
    vegFilter: Boolean,
    onToggleVeg: () -> Unit,
    stepFreeFilter: Boolean,
    onToggleStepFree: () -> Unit,
    parkingFilter: Boolean,
    onToggleParking: () -> Unit,
    priceBandFilter: PriceBand?,
    onSelectPriceBand: (PriceBand?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("stay_directory_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 120.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Government Anti-Overcharging Verification Header
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavyDark),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Verified, contentDescription = null, tint = GoldLight)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ANTI-OVERCHARGING VERIFIED DIRECTORY (MODULE B)",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Official AP Tourism & Municipal Fixed Rate Bands",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Pre-loaded with official East Godavari district list + 37 regional hotels, dharmashalas, and free tent shelters. Fixed tariffs prevent price gouging.",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        lineHeight = 15.sp
                    )
                }
            }
        }

        // District Chips Filter
        item {
            Column {
                Text(
                    text = "FILTER BY DELTA DISTRICT:",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = selectedDistrict == null,
                            onClick = { onSelectDistrict(null) },
                            label = { Text("All 6 Districts", fontSize = 11.sp) }
                        )
                    }
                    items(District.values()) { dist ->
                        FilterChip(
                            selected = selectedDistrict == dist,
                            onClick = { onSelectDistrict(dist) },
                            label = { Text(dist.nameEn.split(" ").first(), fontSize = 11.sp) }
                        )
                    }
                }
            }
        }

        // Feature Filter Toggles (Veg, Step-free, Parking)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = vegFilter,
                    onClick = onToggleVeg,
                    leadingIcon = { Icon(Icons.Default.Eco, contentDescription = null, modifier = Modifier.size(14.dp)) },
                    label = { Text("Pure Veg", fontSize = 11.sp) }
                )
                FilterChip(
                    selected = stepFreeFilter,
                    onClick = onToggleStepFree,
                    leadingIcon = { Icon(Icons.Default.Accessible, contentDescription = null, modifier = Modifier.size(14.dp)) },
                    label = { Text("Step-Free/Lift", fontSize = 11.sp) }
                )
                FilterChip(
                    selected = parkingFilter,
                    onClick = onToggleParking,
                    leadingIcon = { Icon(Icons.Default.LocalParking, contentDescription = null, modifier = Modifier.size(14.dp)) },
                    label = { Text("Parking", fontSize = 11.sp) }
                )
            }
        }

        // Result Count Banner
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${stays.size} Verified Properties Available",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
                Text(
                    text = "Sorted by Distance to Ghat",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }

        // Stays List
        items(stays) { stay ->
            StayItemCard(stay = stay)
        }
    }
}

@Composable
fun StayItemCard(stay: StayProperty) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("stay_card_${stay.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stay.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = NavyPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Govt Verified",
                            tint = CrowdLowGreen,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = "${stay.hubCity} • ${stay.category} • ${stay.distanceToNearestGhatKm} km to Ghat",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFFEF3C7))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "${stay.starRating}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = CrowdMedAmber)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Badges row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (stay.hasStepFreeAccess) {
                    BadgePill(label = "Step-Free / Lift", bg = Color(0xFFEFF6FF), fg = RiverCyan)
                }
                if (stay.isPureVeg) {
                    BadgePill(label = "Pure Veg", bg = Color(0xFFDCFCE7), fg = CrowdLowGreen)
                }
                if (stay.hasParkingOnSite) {
                    BadgePill(label = "Free Parking", bg = Color(0xFFF1F5F9), fg = NavyPrimary)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(10.dp))

            // Pricing & Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (stay.fixedRatePerNight == 0) "FREE COMMUNITY SHELTER" else "₹${stay.fixedRatePerNight} / night",
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        color = if (stay.fixedRatePerNight == 0) CrowdLowGreen else NavyDark
                    )
                    Text(
                        text = "${stay.availableRooms} rooms left (Govt Cap Applied)",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }

                Row {
                    OutlinedButton(
                        onClick = { /* Simulated Phone Call */ },
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Call", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Button(
                        onClick = { /* Simulated AP Tourism Booking */ },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("Book Pass", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun BadgePill(label: String, bg: Color, fg: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = fg)
    }
}
