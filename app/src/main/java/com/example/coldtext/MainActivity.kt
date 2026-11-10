package com.example.coldtext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import com.example.coldtext.ui.theme.ColdTextTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ColdTextTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("coldtext_prefs", Context.MODE_PRIVATE) }

    var testText by remember { mutableStateOf("") }
    var keyboardSize by remember { mutableStateOf(prefs.getString("keyboard_size", "MEDIUM") ?: "MEDIUM") }
    var letterSize by remember { mutableStateOf(prefs.getString("letter_size", "MEDIUM") ?: "MEDIUM") }
    var toneLevel by remember { mutableStateOf(prefs.getString("tone_level", "NEUTRAL") ?: "NEUTRAL") }
    var hapticsEnabled by remember { mutableStateOf(prefs.getBoolean("is_haptics_enabled", true)) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title Header
        Text(
            text = "ColdText Keyboard",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Quick phrase insertion keyboard with adjustable tone, compact size, and smart suggestions.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Step 1 & Step 2 Card: Setup Buttons
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Keyboard Setup Steps",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Button(
                    onClick = {
                        val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("1. Enable ColdText in Settings")
                }

                Button(
                    onClick = {
                        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                        imm?.showInputMethodPicker()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("2. Select ColdText Keyboard")
                }
            }
        }

        // Step 3 Card: Interactive Test Area
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "3. Interactive Keyboard Test Area",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = testText,
                    onValueChange = { testText = it },
                    label = { Text("Tap here to open ColdText & test phrases...") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                if (testText.isNotEmpty()) {
                    OutlinedButton(
                        onClick = { testText = "" },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Clear Text")
                    }
                }
            }
        }

        // Customization Settings Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Keyboard Options & Size",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                // Keyboard Max Height Option
                Text("Keyboard Height:", fontWeight = FontWeight.Medium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("COMPACT" to "Compact (35%)", "MEDIUM" to "Med (40%)", "TALL" to "Tall (45%)").forEach { (key, label) ->
                        FilterChip(
                            selected = keyboardSize == key,
                            onClick = {
                                keyboardSize = key
                                prefs.edit().putString("keyboard_size", key).apply()
                            },
                            label = { Text(label, fontSize = 12.sp) }
                        )
                    }
                }

                Divider()

                // Letter Size Option
                Text("Letter/Font Size:", fontWeight = FontWeight.Medium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("SMALL" to "Small", "MEDIUM" to "Medium", "LARGE" to "Large").forEach { (key, label) ->
                        FilterChip(
                            selected = letterSize == key,
                            onClick = {
                                letterSize = key
                                prefs.edit().putString("letter_size", key).apply()
                            },
                            label = { Text(label) }
                        )
                    }
                }

                Divider()

                // Tone Level Option
                Text("Default Tone/Warmth:", fontWeight = FontWeight.Medium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf(
                        "COLDEST" to "❄️ Cold",
                        "NEUTRAL" to "😐 Neutral",
                        "WARM" to "😊 Warm",
                        "FRIENDLIEST" to "❤️ Friendly"
                    ).forEach { (key, label) ->
                        FilterChip(
                            selected = toneLevel == key,
                            onClick = {
                                toneLevel = key
                                prefs.edit().putString("tone_level", key).apply()
                            },
                            label = { Text(label, fontSize = 11.sp) }
                        )
                    }
                }

                Divider()

                // Haptic Feedback Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Haptic Feedback (Key Vibration):", fontWeight = FontWeight.Medium)
                    Switch(
                        checked = hapticsEnabled,
                        onCheckedChange = {
                            hapticsEnabled = it
                            prefs.edit().putBoolean("is_haptics_enabled", it).apply()
                        }
                    )
                }
            }
        }
    }
}
