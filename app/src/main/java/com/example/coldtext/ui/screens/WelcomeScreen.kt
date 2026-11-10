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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coldtext.data.model.CourseStatus
import com.example.coldtext.data.model.INDIAN_LANGUAGES
import com.example.coldtext.data.repository.PhraseRepository

@Composable
fun WelcomeScreen(
    repository: PhraseRepository,
    onStartLearning: () -> Unit
) {
    val prefs by repository.userPreferencesFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Hero Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Translate,
                    contentDescription = null,
                    modifier = Modifier.height(56.dp).width(56.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "VartalApp",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Learn Indian regional languages naturally through everyday code-switching phrases!",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Example Code-Switching Banner
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "💡 How it works:",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "“Swalpa chill māḍi” → “Let’s chill a little / Relax a bit.”",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Learn natural conversational code-switching phrases with native script, transliteration & literal gloss!",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Target Language Selection (Explicit Status Badges & Phrase Counts)
        Text(
            text = "Choose Course Language:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            INDIAN_LANGUAGES.forEach { lang ->
                val isSelected = prefs.targetLanguageId == lang.id
                val isAvailable = lang.courseStatus == CourseStatus.AVAILABLE

                Card(
                    onClick = {
                        if (isAvailable) {
                            repository.updateTargetLanguage(lang.id)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected)
                            MaterialTheme.colorScheme.secondaryContainer
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = lang.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "(${lang.nativeName})",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = if (isAvailable) "${lang.phraseCount} Reviewed Phrases • ${lang.region}" else "${lang.courseStatus.label} • ${lang.region}",
                                fontSize = 12.sp,
                                color = if (isAvailable) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                            )
                        }

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        } else if (!isAvailable) {
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (lang.courseStatus == CourseStatus.IN_DEVELOPMENT) "Dev" else "Roadmap",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }
            }
        }

        // Learner Primary Motivation / Need
        Text(
            text = "What is your main goal?",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Casual Hangouts", "Travel & Transit", "Work & Office", "Moving to City").forEach { goal ->
                FilterChip(
                    selected = prefs.learningGoal == goal,
                    onClick = { repository.updateLearningGoal(goal) },
                    label = { Text(goal, fontSize = 11.sp) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Preferred Script Mode
        Text(
            text = "Preferred Display Script:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Both Native & Latin", "Latin Script Only").forEach { pref ->
                FilterChip(
                    selected = prefs.scriptPreference == pref,
                    onClick = { repository.updateScriptPreference(pref) },
                    label = { Text(pref, fontSize = 12.sp) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // User Level Selection
        Text(
            text = "Your Experience Level:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Absolute Beginner", "Casual Learner", "Conversational").forEach { level ->
                FilterChip(
                    selected = prefs.userLevel == level,
                    onClick = { repository.updateUserLevel(level) },
                    label = { Text(level, fontSize = 12.sp) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Daily Goal
        Text(
            text = "Daily Goal:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(3 to "3 / day", 5 to "5 / day", 10 to "10 / day").forEach { (goal, label) ->
                FilterChip(
                    selected = prefs.dailyGoalPhrases == goal,
                    onClick = { repository.updateDailyGoal(goal) },
                    label = { Text(label, fontSize = 13.sp) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Start Learning Button
        val selectedLang = INDIAN_LANGUAGES.firstOrNull { it.id == prefs.targetLanguageId }?.name ?: "Kannada"
        Button(
            onClick = {
                repository.setOnboardingCompleted(true)
                onStartLearning()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Language, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Start Learning $selectedLang Now", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
