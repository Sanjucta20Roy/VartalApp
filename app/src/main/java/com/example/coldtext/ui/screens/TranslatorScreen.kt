package com.example.coldtext.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.coldtext.data.audio.TtsHelper
import com.example.coldtext.data.repository.PhraseRepository
import com.example.coldtext.data.translator.CodeSwitchTranslator
import com.example.coldtext.data.translator.MatchType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TranslatorScreen(
    repository: PhraseRepository,
    ttsHelper: TtsHelper,
    onSelectPhrase: (String) -> Unit
) {
    val context = LocalContext.current
    val prefs by repository.userPreferencesFlow.collectAsState()
    val translator = remember(repository) { CodeSwitchTranslator(repository) }

    var inputText by remember { mutableStateOf("") }
    var copiedMessage by remember { mutableStateOf<String?>(null) }

    val currentLang = remember(prefs.targetLanguageId) {
        repository.getLanguages().firstOrNull { it.id == prefs.targetLanguageId }
    }

    val translationResult = remember(inputText, prefs.targetLanguageId) {
        translator.translate(inputText, prefs.targetLanguageId)
    }

    val isFavorite = translationResult.phraseId?.let { prefs.savedPhraseIds.contains(it) } ?: false

    val presetPrompts = listOf(
        "Give it to caretaker",
        "Wait 1 min coming",
        "Are you at flat 503?",
        "How much auto fare?",
        "Give discount costly",
        "Filter coffee without sugar",
        "Oh okay okay (Got it)"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "Code-Switch Translator & Generator",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Type any English sentence to generate natural ${currentLang?.name ?: "Kannada"} + English code-switching!",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Type Input Field
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Type English sentence (e.g., 'Give it to caretaker')...") },
            leadingIcon = { Icon(Icons.Default.Translate, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            minLines = 2
        )

        // Preset Prompt Chips
        Text(
            text = "Quick Everyday Situations:",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            presetPrompts.forEach { prompt ->
                FilterChip(
                    selected = inputText == prompt,
                    onClick = { inputText = prompt },
                    label = { Text(prompt, fontSize = 11.sp) }
                )
            }
        }

        // Translation Output Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
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
                            text = if (translationResult.matchType == MatchType.VERIFIED_EXACT) "🟢 Verified Exact Match" else "🔵 Code-Switch Blend",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Row {
                        IconButton(onClick = {
                            val textToCopy = "${translationResult.codeSwitchLatin} (${translationResult.codeSwitchNative})"
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                            clipboard?.setPrimaryClip(ClipData.newPlainText("CodeSwitch", textToCopy))
                            copiedMessage = "Copied to clipboard!"
                        }) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                        }

                        translationResult.phraseId?.let { phraseId ->
                            IconButton(onClick = { repository.toggleFavorite(phraseId) }) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Save Favorite",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Code-Switching Version:",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )

                // Latin Code-Switching Output
                Text(
                    text = "“${translationResult.codeSwitchLatin}”",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Native Script Code-Switching Output
                Text(
                    text = translationResult.codeSwitchNative,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = translationResult.literalGloss,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        ttsHelper.speak(
                            text = translationResult.codeSwitchNative,
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

                copiedMessage?.let { msg ->
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(msg, fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimaryContainer, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Available Phrases Showcase Cards
        Text(
            text = "Featured Available Phrases",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            repository.getPhrases(prefs.targetLanguageId).take(5).forEach { phrase ->
                OutlinedCard(
                    onClick = { onSelectPhrase(phrase.id) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "“${phrase.latinTransliteration}”",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = phrase.nativeScript,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = phrase.englishMeaning,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(onClick = {
                            ttsHelper.speak(phrase.nativeScript, prefs.audioSpeed, "kn_IN")
                        }) {
                            Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
