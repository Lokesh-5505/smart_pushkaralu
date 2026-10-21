package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.model.PilgrimCategory
import com.example.model.SwachhataTicket
import com.example.ui.theme.*

@Composable
fun SettingsScreen(
    currentLanguage: AppLanguage,
    onSelectLanguage: (AppLanguage) -> Unit,
    currentCategory: PilgrimCategory,
    onSelectCategory: (PilgrimCategory) -> Unit,
    isLargeText: Boolean,
    onToggleLargeText: () -> Unit,
    isHighContrast: Boolean,
    onToggleHighContrast: () -> Unit,
    swachhataTickets: List<SwachhataTicket>,
    onOpenSwachhata: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pushEnabled by remember { mutableStateOf(true) }
    var smsEnabled by remember { mutableStateOf(true) }
    var whatsAppEnabled by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("settings_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 120.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Language Selection Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "1. Official Language Selection (పూర్తి త్రిభాషా మద్దతు)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = NavyPrimary
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    AppLanguage.values().forEach { lang ->
                        val isSelected = lang == currentLanguage
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) Color(0xFFFEF3C7) else Color.Transparent)
                                .clickable { onSelectLanguage(lang) }
                                .padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = isSelected, onClick = { onSelectLanguage(lang) })
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "${lang.nativeName} (${lang.label})",
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 13.sp,
                                    color = NavyPrimary
                                )
                                Text(
                                    text = "Includes native voice prompts & emergency notices",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }

        // Pilgrim Category Zoning Switcher
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "2. Pilgrim Category (Strict Zoning & Flow)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = NavyPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    PilgrimCategory.values().forEach { cat ->
                        val isSelected = cat == currentCategory
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) Color(0xFFEFF6FF) else Color.Transparent)
                                .clickable { onSelectCategory(cat) }
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = isSelected, onClick = { onSelectCategory(cat) })
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "${cat.nameEn} • ${cat.nameTe}",
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = NavyPrimary
                                )
                                Text(
                                    text = "Assigned Entry: ${cat.assignedGate}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TempleSaffron
                                )
                            }
                        }
                    }
                }
            }
        }

        // Accessibility & Assistive Modes
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "3. Accessibility & Elderly Assistance",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = NavyPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Large Typography Mode", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyPrimary)
                            Text(text = "Scales all text for effortless reading in sunlight", fontSize = 10.sp, color = Color.Gray)
                        }
                        Switch(checked = isLargeText, onCheckedChange = { onToggleLargeText() })
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "High-Contrast Visual Mode", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyPrimary)
                            Text(text = "Sharp dark backgrounds with vivid alerts", fontSize = 10.sp, color = Color.Gray)
                        }
                        Switch(checked = isHighContrast, onCheckedChange = { onToggleHighContrast() })
                    }
                }
            }
        }

        // Multi-Channel Alerts Settings
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "4. Multi-Channel Alert Delivery",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = NavyPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Guarantees offline reach even during riverbank cell congestion.",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("App Push Notifications", fontSize = 12.sp, color = Color.DarkGray)
                        Switch(checked = pushEnabled, onCheckedChange = { pushEnabled = it })
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Emergency SMS Broadcasts", fontSize = 12.sp, color = Color.DarkGray)
                        Switch(checked = smsEnabled, onCheckedChange = { smsEnabled = it })
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("WhatsApp Safety Advisories", fontSize = 12.sp, color = Color.DarkGray)
                        Switch(checked = whatsAppEnabled, onCheckedChange = { whatsAppEnabled = it })
                    }
                }
            }
        }

        // Swachhata Cleanliness Tickets
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
                        Text(
                            text = "Tracked Swachhata Tickets",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = NavyPrimary
                        )
                        IconButton(onClick = onOpenSwachhata) {
                            Icon(Icons.Default.Add, contentDescription = "New Ticket", tint = CrowdLowGreen)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    swachhataTickets.forEach { ticket ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = ticket.id, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NavySecondary)
                                    Text(text = ticket.reportedAt, fontSize = 10.sp, color = Color.Gray)
                                }
                                Text(text = "${ticket.ghatName} • ${ticket.locationDetail}", fontSize = 11.sp, color = Color.DarkGray)
                                Text(text = ticket.issueType, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = NavyPrimary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Status: ${ticket.status}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CrowdLowGreen)
                            }
                        }
                    }
                }
            }
        }
    }
}
