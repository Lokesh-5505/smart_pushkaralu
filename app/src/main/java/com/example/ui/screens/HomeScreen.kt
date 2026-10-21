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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.ScreenTab
import com.example.ui.components.CrowdBadge
import com.example.ui.theme.*
import com.example.util.Localization

@Composable
fun HomeScreen(
    language: AppLanguage,
    category: PilgrimCategory,
    ghats: List<GhatInfo>,
    alerts: List<EmergencyAlert>,
    isSurgeSimulated: Boolean,
    isAmbulanceCorridorSimulated: Boolean,
    onNavigateTab: (ScreenTab) -> Unit,
    onSelectGhat: (String) -> Unit,
    onOpenEClinic: () -> Unit,
    onOpenMissingPerson: () -> Unit,
    onOpenSwachhata: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        // 1. Category Zoning Guidance Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp)
                    .testTag("pilgrim_category_banner"),
                colors = CardDefaults.cardColors(containerColor = NavyDark),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(GoldAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (category) {
                                PilgrimCategory.GENERAL_CITIZEN -> Icons.Default.Groups
                                PilgrimCategory.RITUAL_PERFORMER -> Icons.Default.SelfImprovement
                                PilgrimCategory.CHILD_WITH_GUARDIAN -> Icons.Default.ChildCare
                                PilgrimCategory.PERSON_WITH_DISABILITY -> Icons.Default.Accessible
                            },
                            contentDescription = null,
                            tint = NavyDark
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "CATEGORY ZONING",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldLight
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "• UNIDIRECTIONAL FLOW",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                        Text(
                            text = when (language) {
                                AppLanguage.TELUGU -> category.nameTe
                                AppLanguage.HINDI -> category.nameHi
                                AppLanguage.ENGLISH -> category.nameEn
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Assigned Entry: ${category.assignedGate}",
                            fontSize = 12.sp,
                            color = GoldLight,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    IconButton(onClick = { onNavigateTab(ScreenTab.SETTINGS) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Change Category", tint = Color.White)
                    }
                }
            }
        }

        // 2. High Priority Alert / Surge Banner
        if (alerts.isNotEmpty()) {
            item {
                val topAlert = alerts.first()
                val isUrgent = topAlert.severity == AlertSeverity.EMERGENCY || topAlert.isAmbulanceCorridorActive
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                        .testTag("active_alert_banner"),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isUrgent) CrowdHighBg else Color(0xFFEFF6FF)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isUrgent) Icons.Default.Warning else Icons.Default.Info,
                            contentDescription = null,
                            tint = if (isUrgent) SosEmergencyRed else RiverCyan,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (language == AppLanguage.TELUGU) topAlert.titleTe else topAlert.titleEn,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = if (isUrgent) CrowdHighRed else NavyPrimary
                            )
                            Text(
                                text = if (language == AppLanguage.TELUGU) topAlert.messageTe else topAlert.messageEn,
                                fontSize = 11.sp,
                                color = TextPrimaryLight,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }

        // 3. Live Ghat Crowd Density Carousel
        item {
            Column(modifier = Modifier.padding(top = 12.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Localization.liveDensity(language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                    Text(
                        text = "440 GHATS LIVE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = CrowdLowGreen
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(ghats) { ghat ->
                        GhatDensityCard(
                            ghat = ghat,
                            language = language,
                            onClick = { onSelectGhat(ghat.id) }
                        )
                    }
                }
            }
        }

        // 4. Primary 4 Modules Grid
        item {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "CORE PILGRIM SAFETY SERVICES",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    ModuleActionCard(
                        title = Localization.planTripCardTitle(language),
                        subtitle = "Disperse crowds via assigned low-density slots",
                        icon = Icons.Default.CalendarMonth,
                        accentColor = RiverCyan,
                        onClick = { onNavigateTab(ScreenTab.PLANNER) },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    ModuleActionCard(
                        title = Localization.navigateCardTitle(language),
                        subtitle = "3 live choices: Fastest, Least Crowded, Easiest",
                        icon = Icons.Default.AltRoute,
                        accentColor = if (isSurgeSimulated) SosEmergencyRed else GoldAccent,
                        badge = if (isSurgeSimulated) "DIVERSION" else null,
                        onClick = { onNavigateTab(ScreenTab.ROUTES) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    ModuleActionCard(
                        title = Localization.parkingCardTitle(language),
                        subtitle = "ANPR plate recognition & feeder shuttle tracking",
                        icon = Icons.Default.LocalParking,
                        accentColor = NavySecondary,
                        onClick = { onNavigateTab(ScreenTab.PARKING) },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    ModuleActionCard(
                        title = Localization.stayCardTitle(language),
                        subtitle = "37+ verified properties with fixed festival rate bands",
                        icon = Icons.Default.Hotel,
                        accentColor = TempleSaffron,
                        onClick = { onNavigateTab(ScreenTab.STAYS) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 5. Quick Field Services Row (E-Clinic, Missing Persons, Swachhata)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .testTag("quick_field_services"),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "RAPID FIELD SUPPORT & ASSISTANCE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionItem(
                            title = "E-Clinic",
                            subtitle = "Online Doctor",
                            icon = Icons.Default.MedicalServices,
                            iconTint = RiverCyan,
                            onClick = onOpenEClinic
                        )
                        QuickActionItem(
                            title = "Missing Person",
                            subtitle = "RFID / Photo",
                            icon = Icons.Default.PersonSearch,
                            iconTint = GoldAccent,
                            onClick = onOpenMissingPerson
                        )
                        QuickActionItem(
                            title = "Swachhata",
                            subtitle = "Water & Toilets",
                            icon = Icons.Default.CleaningServices,
                            iconTint = CrowdLowGreen,
                            onClick = onOpenSwachhata
                        )
                    }
                }
            }
        }

        // 6. Anti-Stampede 5-Layer Guarantee Footer Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = CrowdLowGreen)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "5-LAYER ANTI-STAMPEDE PROTOCOL ACTIVE",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = NavyPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "1. Pre-trip slot distribution • 2. Hard routing guardrails • 3. Upstream ANPR parking intercept • 4. Separate Entry & Exit gates • 5. Smart Emergency Corridors.",
                        fontSize = 11.sp,
                        color = TextSecondaryLight,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun GhatDensityCard(
    ghat: GhatInfo,
    language: AppLanguage,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .clickable { onClick() }
            .testTag("ghat_card_${ghat.id}"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CrowdBadge(density = ghat.density)
                Text(
                    text = "${ghat.distanceKmFromHub} km",
                    fontSize = 11.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (language == AppLanguage.TELUGU) ghat.nameTe else ghat.nameEn,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = NavyPrimary,
                maxLines = 1
            )
            Text(
                text = ghat.district.nameEn,
                fontSize = 11.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Capacity Bar
            val ratio = (ghat.currentCrowd.toFloat() / ghat.safeCapacity.toFloat()).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { ratio },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = when (ghat.density) {
                    CrowdDensity.LOW -> CrowdLowGreen
                    CrowdDensity.MEDIUM -> CrowdMedAmber
                    CrowdDensity.HIGH -> CrowdHighRed
                    CrowdDensity.CRITICAL -> SosEmergencyRed
                },
                trackColor = Color(0xFFE2E8F0)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${ghat.currentCrowd} people",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryLight
                )
                Text(
                    text = "Cap: ${ghat.safeCapacity}",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ModuleActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badge: String? = null
) {
    Card(
        modifier = modifier
            .height(130.dp)
            .clickable { onClick() }
            .testTag("module_card_${title.take(10).replace(" ", "_").lowercase()}"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(20.dp)
                    )
                }

                if (badge != null) {
                    Text(
                        text = badge,
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(SosEmergencyRed)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = NavyPrimary,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    lineHeight = 13.sp,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun QuickActionItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(iconTint.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = iconTint, modifier = Modifier.size(22.dp))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NavyPrimary)
        Text(text = subtitle, fontSize = 10.sp, color = Color.Gray)
    }
}
