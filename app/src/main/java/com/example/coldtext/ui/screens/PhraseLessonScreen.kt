package com.example.coldtext.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coldtext.data.audio.TtsHelper
import com.example.coldtext.data.model.CourseStatus
import com.example.coldtext.data.model.Topic
import com.example.coldtext.data.repository.PhraseRepository

@Composable
fun PhraseLessonScreen(
    repository: PhraseRepository,
    ttsHelper: TtsHelper,
    initialTopic: Topic? = null,
    initialPhraseId: String? = null,
    onNavigateBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val prefs by repository.userPreferencesFlow.collectAsState()
    val phrases = remember(initialTopic, prefs.targetLanguageId) {
        repository.getPhrases(prefs.targetLanguageId, initialTopic)
    }

    if (phrases.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No phrases available for this selected course.")
        }
        return
    }

    val initialIndex = remember(initialPhraseId) {
        if (initialPhraseId != null) {
            phrases.indexOfFirst { it.id == initialPhraseId }.coerceAtLeast(0)
        } else {
            0
        }
    }

    var currentIndex by remember { mutableIntStateOf(initialIndex) }
    val currentPhrase = phrases[currentIndex]

    val currentLang = remember(prefs.targetLanguageId) {
        repository.getLanguages().firstOrNull { it.id == prefs.targetLanguageId }
    }

    val isFavorite = prefs.savedPhraseIds.contains(currentPhrase.id)
    val isCompleted = prefs.completedPhraseIds.contains(currentPhrase.id)

    var showFlagDialog by remember { mutableStateOf(false) }
    var userSuggestionText by remember { mutableStateOf("") }
    var suggestionSavedMessage by remember { mutableStateOf<String?>(null) }
    var showManualTtsSteps by remember { mutableStateOf(false) }

    val ttsStatus = remember(currentPhrase) {
        ttsHelper.getLanguageSupportStatus("kn_IN")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Preview Warning Banner if Course is Preview Mode
        if (currentLang?.courseStatus == CourseStatus.PREVIEW) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ℹ️ Course Preview Mode: This language currently contains preview phrase samples while the full course is being reviewed.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${currentPhrase.topic.displayName} • ${currentIndex + 1}/${phrases.size}",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { repository.toggleFavorite(currentPhrase.id) }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Save Favorite",
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Main Mixed-Language Phrase Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Register & Native Review Badges Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = currentPhrase.register.label,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = currentPhrase.reviewStatus.label,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Latin Transliteration
                Text(
                    text = "“${currentPhrase.latinTransliteration}”",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                // Native Script (Conditional Display based on user preference)
                if (prefs.scriptPreference == "Both Native & Latin") {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentPhrase.nativeScript,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))

                // English Natural Meaning vs Literal Gloss
                Text(
                    text = "Natural English Meaning:",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                Text(
                    text = currentPhrase.englishMeaning,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                currentPhrase.literalGloss?.let { literal ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = literal,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Audio Play Button & TTS Voice Readiness Status
                Button(
                    onClick = {
                        ttsHelper.speak(
                            text = currentPhrase.nativeScript,
                            speechRate = prefs.audioSpeed,
                            languageCode = "kn_IN"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Play Audio")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Listen Audio")
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🔊 ${ttsStatus.label}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                    TextButton(onClick = {
                        val launched = ttsHelper.tryOpenTtsSettings(context)
                        if (!launched) showManualTtsSteps = true
                    }) {
                        Icon(Icons.Default.RecordVoiceOver, contentDescription = null, modifier = Modifier.height(14.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text("TTS Settings", fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }

                if (showManualTtsSteps) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Manual Steps: Open Device Settings → System → Languages & Input → Text-to-Speech output → Download Indian Voice Data",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                    )
                }
            }
        }

        // Register Variations Card (Casual vs Polite vs Formal)
        if (currentPhrase.variations.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🔄 Situation Register Variations",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    currentPhrase.variations.forEach { variation ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "“${variation.latinTransliteration}” (${variation.nativeScript})",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = variation.note,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(variation.register.label, fontSize = 10.sp, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }

        // Word-by-Word Gloss Breakdown
        Text(
            text = "Word-by-Word Breakdown",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            currentPhrase.wordGloss.forEach { item ->
                OutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = item.word,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "(${item.nativeScript})",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = item.englishMeaning,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = item.partOfSpeech,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Auditable Reviewer Metadata & Offline Flag Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "✅ Auditable Native Speaker Verification",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                currentPhrase.reviewerName?.let { reviewer ->
                    Text("Verified By: $reviewer (${currentPhrase.reviewDate ?: "2026-02"})", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
                currentPhrase.reviewMethod?.let { method ->
                    Text("Audit Method: $method", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                currentPhrase.regionVariety?.let { variety ->
                    Text("Region Variety: $variety", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { showFlagDialog = true }) {
                        Icon(Icons.Default.Flag, contentDescription = null, modifier = Modifier.height(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Flag / Suggest Correction", fontSize = 11.sp)
                    }
                }
            }
        }

        // Flag & Offline Correction Dialog
        if (showFlagDialog) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Suggest Correction (Offline & Private)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "VartalApp works 100% offline. Suggestions are saved locally on your device for export/review.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = userSuggestionText,
                        onValueChange = { userSuggestionText = it },
                        label = { Text("Enter your suggested correction or note...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showFlagDialog = false }) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (userSuggestionText.isNotBlank()) {
                                    repository.saveOfflineFeedback(currentPhrase.id, userSuggestionText)
                                    suggestionSavedMessage = "Saved locally on device!"
                                    userSuggestionText = ""
                                    showFlagDialog = false
                                }
                            }
                        ) {
                            Text("Save Locally")
                        }
                    }
                }
            }
        }

        suggestionSavedMessage?.let { msg ->
            Text(text = msg, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }

        // Action Controls Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {
                    if (currentIndex > 0) currentIndex--
                },
                enabled = currentIndex > 0
            ) {
                Icon(Icons.AutoMirrored.Filled.NavigateBefore, contentDescription = null)
                Text("Prev")
            }

            Button(
                onClick = {
                    repository.markPhraseCompleted(currentPhrase.id)
                    if (currentIndex < phrases.size - 1) {
                        currentIndex++
                    }
                }
            ) {
                if (isCompleted) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Completed")
                } else {
                    Text("Mark Done & Next")
                }
            }

            OutlinedButton(
                onClick = {
                    if (currentIndex < phrases.size - 1) currentIndex++
                },
                enabled = currentIndex < phrases.size - 1
            ) {
                Text("Next")
                Icon(Icons.AutoMirrored.Filled.NavigateNext, contentDescription = null)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
