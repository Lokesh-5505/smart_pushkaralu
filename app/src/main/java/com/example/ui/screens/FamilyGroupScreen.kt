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
import com.example.model.FamilyMember
import com.example.model.PilgrimCategory
import com.example.ui.theme.*

@Composable
fun FamilyGroupScreen(
    language: AppLanguage,
    members: List<FamilyMember>,
    onAddMember: (FamilyMember) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("family_group_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 120.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Group Head Profile Card
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
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(GoldAccent),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = NavyDark)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "REGISTERED GROUP HEAD",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldLight
                                )
                                Text(
                                    text = "G. Satyanarayana",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "+91 98765 43210 • OTP Verified",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
                        }

                        IconButton(
                            onClick = { showAddDialog = true },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.15f))
                        ) {
                            Icon(Icons.Default.PersonAdd, contentDescription = "Add Member", tint = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatusMiniPill(label = "Family Size", value = "${members.size} Members")
                        StatusMiniPill(label = "Child Safety Bands", value = "1 Active")
                        StatusMiniPill(label = "Group Status", value = "All Together")
                    }
                }
            }
        }

        // Child Wristband & Anti-Lost Safety Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFDCFCE7)),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PhonelinkRing,
                        contentDescription = null,
                        tint = CrowdLowGreen,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "SMART CHILD WRISTBAND BEACON ACTIVE",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = CrowdLowGreen
                        )
                        Text(
                            text = "RFID wristband #AP-PK-8821 (G. Ananya) linked. If child moves beyond 15m, proximity alert rings on your phone.",
                            fontSize = 11.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }

        // 1-Tap Share Live Location Button
        item {
            Button(
                onClick = { /* Simulated Location Share */ },
                colors = ButtonDefaults.buttonColors(containerColor = NavySecondary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .testTag("share_location_button")
            ) {
                Icon(Icons.Default.ShareLocation, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("SHARE FAMILY LIVE LOCATION VIA WHATSAPP", fontWeight = FontWeight.Bold, fontSize = 11.sp)
            }
        }

        // Member Cards List
        item {
            Text(
                text = "LINKED FAMILY MEMBERS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }

        items(members) { member ->
            FamilyMemberCard(member = member)
        }
    }

    if (showAddDialog) {
        AddFamilyMemberDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { member ->
                onAddMember(member)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun FamilyMemberCard(member: FamilyMember) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("member_card_${member.id}")
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF1F5F9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (member.category) {
                        PilgrimCategory.GENERAL_CITIZEN -> Icons.Default.Person
                        PilgrimCategory.RITUAL_PERFORMER -> Icons.Default.SelfImprovement
                        PilgrimCategory.CHILD_WITH_GUARDIAN -> Icons.Default.ChildCare
                        PilgrimCategory.PERSON_WITH_DISABILITY -> Icons.Default.Accessible
                    },
                    contentDescription = null,
                    tint = NavyPrimary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${member.name} (${member.relation})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = NavyPrimary
                )
                Text(
                    text = "Age: ${member.age} • ${member.category.nameEn}",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                if (member.rfidWristbandId != null) {
                    Text(
                        text = "RFID Band: ${member.rfidWristbandId} • ${member.wristbandStatus}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = CrowdLowGreen
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${member.distanceMetersFromHead}m away",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = CrowdLowGreen
                )
                Text(text = "Safe Zone", fontSize = 9.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun StatusMiniPill(label: String, value: String) {
    Column {
        Text(text = label, fontSize = 9.sp, color = Color.White.copy(alpha = 0.7f))
        Text(text = value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldLight)
    }
}

@Composable
fun AddFamilyMemberDialog(
    onDismiss: () -> Unit,
    onAdd: (FamilyMember) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var relation by remember { mutableStateOf("Relative") }
    var ageStr by remember { mutableStateOf("25") }
    var category by remember { mutableStateOf(PilgrimCategory.GENERAL_CITIZEN) }

    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Add Family Member", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, color = NavyPrimary)
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(value = relation, onValueChange = { relation = it }, label = { Text("Relationship") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(value = ageStr, onValueChange = { ageStr = it }, label = { Text("Age") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val age = ageStr.toIntOrNull() ?: 25
                        val newMember = FamilyMember(
                            id = "fm_${System.currentTimeMillis()}",
                            name = name.ifBlank { "Family Member" },
                            relation = relation,
                            age = age,
                            category = category,
                            rfidWristbandId = "AP-PK-${(1000..9999).random()}",
                            wristbandStatus = "Safety Tag Registered",
                            distanceMetersFromHead = 2
                        )
                        onAdd(newMember)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
                ) {
                    Text("ADD & ISSUE RFID BAND PASS")
                }
            }
        }
    }
}
