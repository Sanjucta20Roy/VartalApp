package com.example.vartalapp.feature.settings

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vartalapp.core.model.DisplayScript
import com.example.vartalapp.core.model.UserPreferences
import com.example.vartalapp.feature.language.LanguageSelectionDialog
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    userPreferences: UserPreferences,
    onBackClick: () -> Unit,
    onDisplayScriptChanged: (DisplayScript) -> Unit,
    onDailyGoalChanged: (Int) -> Unit,
    onAudioSpeedChanged: (Float) -> Unit,
    onThemeChanged: (String) -> Unit,
    onNotificationsToggled: (Boolean) -> Unit,
    onPlayAudio: (String) -> Unit = {}
) {
    val context = LocalContext.current
    var showLanguageDialog by remember { mutableStateOf(false) }

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            selectedLanguageId = "kannada",
            onDismiss = { showLanguageDialog = false },
            onLanguageSelected = { showLanguageDialog = false },
            onPlayAudio = onPlayAudio
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Learning Language Selector Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showLanguageDialog = true },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Language, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("Learning Language", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                Text(userPreferences.learningLanguage, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            Text("38 Languages ▾", fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }
                }
            }

            // Display Script Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Translate, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Display Script Preference", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Choose how Kannada text is shown throughout the app:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChip(
                                selected = userPreferences.displayScript == DisplayScript.BOTH,
                                onClick = { onDisplayScriptChanged(DisplayScript.BOTH) },
                                label = { Text("Both Scripts") }
                            )
                            FilterChip(
                                selected = userPreferences.displayScript == DisplayScript.NATIVE,
                                onClick = { onDisplayScriptChanged(DisplayScript.NATIVE) },
                                label = { Text("Kannada Script") }
                            )
                            FilterChip(
                                selected = userPreferences.displayScript == DisplayScript.TRANSLITERATION,
                                onClick = { onDisplayScriptChanged(DisplayScript.TRANSLITERATION) },
                                label = { Text("English Transliteration") }
                            )
                        }
                    }
                }
            }

            // Daily Goal & Audio Speed Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Speed, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Learning & Audio Speed", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Daily Learning Goal:", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf(3, 5, 10, 15).forEach { goal ->
                                FilterChip(
                                    selected = userPreferences.dailyGoalPhrases == goal,
                                    onClick = { onDailyGoalChanged(goal) },
                                    label = { Text("$goal phrases/day") }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Audio Speech Speed:", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf(0.75f, 1.0f, 1.25f).forEach { speed ->
                                FilterChip(
                                    selected = userPreferences.audioSpeed == speed,
                                    onClick = { onAudioSpeedChanged(speed) },
                                    label = { Text("${speed}x") }
                                )
                            }
                        }
                    }
                }
            }

            // Theme Mode Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Palette, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("App Theme", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("SYSTEM", "LIGHT", "DARK").forEach { mode ->
                                FilterChip(
                                    selected = userPreferences.themeMode == mode,
                                    onClick = { onThemeChanged(mode) },
                                    label = {
                                        val titleText = mode.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                                        Text(titleText)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // ColdText Keyboard Companion Card (Optional Feature Separation)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Keyboard, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("ColdText Keyboard Companion", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "ColdText is an optional Android IME companion that brings Kannada suggestions into WhatsApp and Messages. VartalApp works completely independently without requiring the keyboard.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    context.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Enable Keyboard", fontSize = 12.sp)
                            }

                            OutlinedButton(
                                onClick = {
                                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                                    imm?.showInputMethodPicker()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Switch Keyboard", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // About VartalApp Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("About VartalApp", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("VartalApp v1.0 — Practical Indian & Global Languages", style = MaterialTheme.typography.bodySmall)
                        Text("Targeted for Bengaluru Kannada learners & global movers.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                    }
                }
            }
        }
    }
}
