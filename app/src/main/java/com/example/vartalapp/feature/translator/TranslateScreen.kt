package com.example.vartalapp.feature.translator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vartalapp.core.model.DisplayScript

val exampleSuggestions = listOf(
    "Please wait for me, I am coming.",
    "Indiranagar-ge barteera?",
    "Keep the parcel near the gate.",
    "How much to Koramangala?",
    "Where is the bus stop?",
    "OTP is 4921",
    "Don't wait for me",
    "Kannada gothilla"
)

val situationOptions = listOf(
    "All Situations",
    "Apartment & Security Guard",
    "Transport & Auto Drivers",
    "Delivery & Courier",
    "Shopping & Payments",
    "Restaurants & Food"
)

@Composable
fun TranslateScreen(
    uiState: TranslatorUiState,
    displayScript: DisplayScript,
    onInputChanged: (String) -> Unit,
    onClearInput: () -> Unit,
    onSelectSituation: (String?) -> Unit,
    onPlayAudio: (String) -> Unit
) {
    val context = LocalContext.current
    var expandedDropdown by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title
        item {
            Column {
                Text(
                    text = "Conversational Translator",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Translate English to natural, real-life Bengaluru Kannada",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Situation Filter Dropdown
        item {
            Box {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedDropdown = true },
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Situation: ${uiState.selectedSituation ?: "All Situations"}",
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text("▼", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                    }
                }

                DropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false }
                ) {
                    situationOptions.forEach { situation ->
                        DropdownMenuItem(
                            text = { Text(situation) },
                            onClick = {
                                onSelectSituation(if (situation == "All Situations") null else situation)
                                expandedDropdown = false
                            }
                        )
                    }
                }
            }
        }

        // Input Field Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "English Text",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Row {
                            IconButton(onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val item = clipboard.primaryClip?.getItemAt(0)
                                item?.text?.let { onInputChanged(it.toString()) }
                            }) {
                                Icon(Icons.Default.ContentPaste, contentDescription = "Paste", tint = MaterialTheme.colorScheme.outline)
                            }
                            if (uiState.inputText.isNotEmpty()) {
                                IconButton(onClick = onClearInput) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear", tint = MaterialTheme.colorScheme.outline)
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = uiState.inputText,
                        onValueChange = onInputChanged,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp),
                        placeholder = { Text("Type English sentence (e.g. Please wait for one minute, I am coming...)") },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }

        // Quick Example Chips
        item {
            Column {
                Text(
                    text = "Try Example Sentences:",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(exampleSuggestions) { suggestion ->
                        Surface(
                            modifier = Modifier.clickable { onInputChanged(suggestion) },
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = suggestion,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Translation Result Card
        item {
            uiState.result?.let { res ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Confidence Label Badge
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = when (res.confidence) {
                                    "Exact phrase match" -> Color(0xFF4CAF50)
                                    "Suggested conversational expression" -> MaterialTheme.colorScheme.primary
                                    else -> Color(0xFFFF9800)
                                }
                            ) {
                                Text(
                                    text = res.confidence,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Row {
                                if (res.kannadaScript.isNotBlank()) {
                                    IconButton(onClick = { onPlayAudio(res.kannadaScript) }) {
                                        Icon(Icons.Default.PlayArrow, contentDescription = "Play Audio", tint = MaterialTheme.colorScheme.primary)
                                    }
                                    IconButton(onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("Kannada Translation", "${res.kannadaScript}\n${res.transliteration}")
                                        clipboard.setPrimaryClip(clip)
                                        Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
                                    }) {
                                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = MaterialTheme.colorScheme.outline)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (res.kannadaScript.isNotBlank() && (displayScript == DisplayScript.BOTH || displayScript == DisplayScript.NATIVE)) {
                            Text(
                                text = res.kannadaScript,
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }

                        if (res.transliteration.isNotBlank() && (displayScript == DisplayScript.BOTH || displayScript == DisplayScript.TRANSLITERATION)) {
                            Text(
                                text = res.transliteration,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Meaning: ${res.englishMeaning}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "💡 Context: ${res.context}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                        )

                        res.alternative?.let { alt ->
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "🔄 Alternative: $alt",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                }
            }
        }
    }
}
