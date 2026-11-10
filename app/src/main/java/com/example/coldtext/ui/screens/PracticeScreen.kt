package com.example.coldtext.ui.screens

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coldtext.data.audio.TtsHelper
import com.example.coldtext.data.model.Phrase
import com.example.coldtext.data.model.Topic
import com.example.coldtext.data.repository.PhraseRepository

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PracticeScreen(
    repository: PhraseRepository,
    ttsHelper: TtsHelper,
    initialTopic: Topic? = null
) {
    val prefs by repository.userPreferencesFlow.collectAsState()

    var selectedTopicFilter by remember { mutableStateOf<Topic?>(initialTopic) }

    val phrases = remember(selectedTopicFilter, prefs.targetLanguageId) {
        repository.getPhrases(prefs.targetLanguageId, selectedTopicFilter)
    }

    if (phrases.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No phrases available for practice.")
        }
        return
    }

    var questionIndex by remember { mutableIntStateOf(0) }
    val currentPhrase = phrases[questionIndex % phrases.size]

    // Determine practice question mode: 0 -> Multiple choice meaning, 1 -> Listening match, 2 -> Word Builder
    val modeIndex = questionIndex % 3

    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var isAnswerChecked by remember { mutableStateOf(false) }

    // Word builder state
    var constructedWords by remember { mutableStateOf<List<String>>(emptyList()) }
    val correctWords = remember(currentPhrase) {
        currentPhrase.latinTransliteration.split(" ").filter { it.isNotBlank() }
    }
    val shuffledWords = remember(currentPhrase) {
        correctWords.shuffled()
    }

    // Generate 4 options for multiple choice
    val choices = remember(currentPhrase, phrases) {
        val wrong = phrases.filter { it.id != currentPhrase.id }
            .map { it.englishMeaning }
            .shuffled()
            .take(3)
        (wrong + currentPhrase.englishMeaning).shuffled()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Topic Filter Chips
        Text(
            text = "Practice Topic:",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = selectedTopicFilter == null,
                onClick = { selectedTopicFilter = null },
                label = { Text("All Topics", fontSize = 11.sp) }
            )
            Topic.values().take(3).forEach { topic ->
                FilterChip(
                    selected = selectedTopicFilter == topic,
                    onClick = { selectedTopicFilter = topic },
                    label = { Text(topic.displayName, fontSize = 11.sp) }
                )
            }
        }

        // Question Progress Header
        Text(
            text = "Question ${questionIndex + 1} • ${
                when (modeIndex) {
                    0 -> "Multiple Choice Meaning"
                    1 -> "Listening & Native Script Match"
                    else -> "Phrase Builder"
                }
            }",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // Question Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                when (modeIndex) {
                    0 -> {
                        Text(
                            text = "What does this phrase mean?",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "“${currentPhrase.latinTransliteration}”",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = currentPhrase.nativeScript,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    1 -> {
                        Text(
                            text = "Listen to the audio and select the correct native script:",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                ttsHelper.speak(
                                    currentPhrase.nativeScript,
                                    prefs.audioSpeed,
                                    "kn_IN"
                                )
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.VolumeUp, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Play Audio")
                        }
                    }

                    2 -> {
                        Text(
                            text = "Build the phrase in order: “${currentPhrase.englishMeaning}”",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Answer Section based on Mode
        if (modeIndex == 0) {
            // Multiple Choice Options
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                choices.forEach { option ->
                    val isSelected = selectedAnswer == option
                    val isCorrectChoice = option == currentPhrase.englishMeaning
                    val backgroundColor = when {
                        !isAnswerChecked && isSelected -> MaterialTheme.colorScheme.secondaryContainer
                        isAnswerChecked && isCorrectChoice -> Color(0xFFC8E6C9)
                        isAnswerChecked && isSelected && !isCorrectChoice -> Color(0xFFFFCDD2)
                        else -> MaterialTheme.colorScheme.surface
                    }

                    OutlinedCard(
                        onClick = {
                            if (!isAnswerChecked) {
                                selectedAnswer = option
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = backgroundColor),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = option,
                                fontSize = 15.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        } else if (modeIndex == 1) {
            // Listening Match Script Options
            val scriptChoices = remember(currentPhrase, phrases) {
                val wrong = phrases.filter { it.id != currentPhrase.id }
                    .map { it.nativeScript }
                    .shuffled()
                    .take(3)
                (wrong + currentPhrase.nativeScript).shuffled()
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                scriptChoices.forEach { script ->
                    val isSelected = selectedAnswer == script
                    val isCorrectChoice = script == currentPhrase.nativeScript
                    val backgroundColor = when {
                        !isAnswerChecked && isSelected -> MaterialTheme.colorScheme.secondaryContainer
                        isAnswerChecked && isCorrectChoice -> Color(0xFFC8E6C9)
                        isAnswerChecked && isSelected && !isCorrectChoice -> Color(0xFFFFCDD2)
                        else -> MaterialTheme.colorScheme.surface
                    }

                    OutlinedCard(
                        onClick = {
                            if (!isAnswerChecked) {
                                selectedAnswer = script
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = backgroundColor),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = script,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        } else {
            // Word Builder Mode
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Your phrase:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (constructedWords.isEmpty()) "Tap words below to assemble phrase..." else constructedWords.joinToString(" "),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (constructedWords.isNotEmpty() && !isAnswerChecked) {
                        OutlinedButton(
                            onClick = { constructedWords = emptyList() },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Text("Reset")
                        }
                    }
                }
            }

            Text("Word options:", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                shuffledWords.forEach { word ->
                    val isUsed = constructedWords.contains(word)
                    Button(
                        onClick = {
                            if (!isUsed && !isAnswerChecked) {
                                constructedWords = constructedWords + word
                                selectedAnswer = constructedWords.joinToString(" ")
                            }
                        },
                        enabled = !isUsed && !isAnswerChecked,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(word)
                    }
                }
            }
        }

        // Action & Feedback Row
        if (isAnswerChecked) {
            val isCorrect = when (modeIndex) {
                0 -> selectedAnswer == currentPhrase.englishMeaning
                1 -> selectedAnswer == currentPhrase.nativeScript
                else -> constructedWords == correctWords
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCorrect) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Error,
                            contentDescription = null,
                            tint = if (isCorrect) Color(0xFF2E7D32) else Color(0xFFC62828)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isCorrect) "Awesome! Correct Answer!" else "Not quite right",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isCorrect) Color(0xFF2E7D32) else Color(0xFFC62828)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Phrase: “${currentPhrase.latinTransliteration}” (${currentPhrase.nativeScript}) = “${currentPhrase.englishMeaning}”",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Gloss: ${currentPhrase.pronunciationNotes}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (isCorrect) {
                                repository.markPhraseCompleted(currentPhrase.id)
                            }
                            // Reset state for next question
                            questionIndex++
                            selectedAnswer = null
                            isAnswerChecked = false
                            constructedWords = emptyList()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Next Question")
                    }
                }
            }
        } else {
            Button(
                onClick = {
                    if (modeIndex == 2) {
                        selectedAnswer = constructedWords.joinToString(" ")
                    }
                    if (selectedAnswer != null) {
                        isAnswerChecked = true
                    }
                },
                enabled = selectedAnswer != null || constructedWords.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Check Answer", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
