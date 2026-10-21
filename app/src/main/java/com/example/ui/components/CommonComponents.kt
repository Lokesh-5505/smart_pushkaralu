package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.model.CrowdDensity
import com.example.ui.theme.*
import com.example.util.Localization

@Composable
fun AppHeader(
    currentLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    isSpeaking: Boolean,
    onTtsClick: () -> Unit,
    onSosClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("app_header"),
        color = NavyPrimary,
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            // Government Branding Strip
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(GoldAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "AP Gov Emblem",
                            tint = NavyDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = Localization.govtBadge(currentLanguage),
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldLight,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                        Text(
                            text = Localization.appTitle(currentLanguage),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Voice Prompt TTS Button
                IconButton(
                    onClick = onTtsClick,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isSpeaking) GoldAccent else Color.White.copy(alpha = 0.15f))
                        .testTag("tts_voice_button")
                ) {
                    Icon(
                        imageVector = if (isSpeaking) Icons.Default.VolumeUp else Icons.Default.RecordVoiceOver,
                        contentDescription = "Voice Guidance TTS",
                        tint = if (isSpeaking) NavyDark else Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Language Selector Pills + SOS Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Trilingual Switcher (Telugu, English, Hindi)
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                        .padding(2.dp)
                        .testTag("language_selector")
                ) {
                    AppLanguage.values().forEach { lang ->
                        val isSelected = lang == currentLanguage
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) GoldAccent else Color.Transparent)
                                .clickable { onLanguageSelected(lang) }
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = lang.nativeName,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) NavyDark else Color.White
                            )
                        }
                    }
                }

                // 1-Tap SOS Button
                Button(
                    onClick = onSosClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SosEmergencyRed,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    modifier = Modifier
                        .height(34.dp)
                        .testTag("top_bar_sos_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Emergency SOS",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "SOS",
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CrowdBadge(
    density: CrowdDensity,
    modifier: Modifier = Modifier
) {
    val (bg, fg, label) = when (density) {
        CrowdDensity.LOW -> Triple(CrowdLowBg, CrowdLowGreen, density.labelEn)
        CrowdDensity.MEDIUM -> Triple(CrowdMedBg, CrowdMedAmber, density.labelEn)
        CrowdDensity.HIGH -> Triple(CrowdHighBg, CrowdHighRed, density.labelEn)
        CrowdDensity.CRITICAL -> Triple(CrowdHighBg, CrowdHighRed, density.labelEn)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .testTag("crowd_badge_${density.name.lowercase()}"),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(fg)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                color = fg,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
        }
    }
}
