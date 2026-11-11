package com.example.vartalapp.feature.language

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vartalapp.data.LanguageAvailability
import com.example.vartalapp.data.LanguageCatalog
import com.example.vartalapp.data.LanguageInfo
import com.example.vartalapp.data.OnDemandLanguageManager
import com.example.vartalapp.data.db.VartalDatabase
import kotlinx.coroutines.launch

@Composable
fun LanguageSelectionDialog(
    selectedLanguageId: String = "kannada",
    onDismiss: () -> Unit,
    onLanguageSelected: (LanguageInfo) -> Unit,
    onPlayAudio: (String) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val database = remember { VartalDatabase.getInstance(context) }
    val onDemandManager = remember { OnDemandLanguageManager(context, database) }

    var previewingLanguage by remember { mutableStateOf<LanguageInfo?>(null) }
    var downloadingLanguageId by remember { mutableStateOf<String?>(null) }
    var downloadProgress by remember { mutableStateOf(0f) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Language, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("On-Demand Language Catalog", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            Column {
                Text(
                    text = "💾 Save device storage: Download only the languages you want to learn!",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(LanguageCatalog.languages) { lang ->
                        val isSelected = lang.id == selectedLanguageId
                        val isDownloading = downloadingLanguageId == lang.id
                        val isInstalled = onDemandManager.isLanguageInstalled(lang.id)

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (isInstalled || lang.availability == LanguageAvailability.AVAILABLE) {
                                        onLanguageSelected(lang)
                                        onDismiss()
                                    } else {
                                        previewingLanguage = lang
                                    }
                                },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = lang.name,
                                                fontWeight = FontWeight.Bold,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "(${lang.nativeName})",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        Text(
                                            text = "${lang.region} • Size: ${lang.downloadSizeMb}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = when {
                                            isInstalled || lang.availability == LanguageAvailability.AVAILABLE -> Color(0xFF4CAF50)
                                            lang.availability == LanguageAvailability.PREVIEW -> MaterialTheme.colorScheme.tertiary
                                            else -> MaterialTheme.colorScheme.surfaceVariant
                                        }
                                    ) {
                                        Text(
                                            text = when {
                                                isInstalled || lang.availability == LanguageAvailability.AVAILABLE -> "Installed"
                                                lang.availability == LanguageAvailability.PREVIEW -> "Preview (${lang.downloadSizeMb})"
                                                else -> "On-Demand"
                                            },
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                if (isDownloading) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    LinearProgressIndicator(
                                        progress = { downloadProgress },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(4.dp)
                                            .clip(RoundedCornerShape(2.dp)),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )

    // Preview Phrases Modal with On-Demand Download Button
    previewingLanguage?.let { lang ->
        val isInstalled = onDemandManager.isLanguageInstalled(lang.id)

        AlertDialog(
            onDismissRequest = { previewingLanguage = null },
            title = { Text("${lang.name} (${lang.nativeName}) — ${lang.downloadSizeMb}", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Preview code-mixed city mover phrases for ${lang.name}. Download feature pack to save phone storage!",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    lang.previewPhrases.forEach { phrase ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = phrase.kannada, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Text(text = phrase.transliteration, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                    Text(text = phrase.english, fontSize = 12.sp)
                                }
                                IconButton(onClick = { onPlayAudio(phrase.kannada) }) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = "Listen")
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        downloadingLanguageId = lang.id
                        coroutineScope.launch {
                            onDemandManager.downloadLanguagePack(lang) { prog ->
                                downloadProgress = prog
                            }
                            downloadingLanguageId = null
                            Toast.makeText(context, "${lang.name} pack downloaded successfully (${lang.downloadSizeMb})!", Toast.LENGTH_SHORT).show()
                            previewingLanguage = null
                        }
                    }
                ) {
                    Icon(Icons.Default.Download, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isInstalled) "Re-download (${lang.downloadSizeMb})" else "Download Pack (${lang.downloadSizeMb})")
                }
            },
            dismissButton = {
                TextButton(onClick = { previewingLanguage = null }) {
                    Text("Close")
                }
            }
        )
    }
}
