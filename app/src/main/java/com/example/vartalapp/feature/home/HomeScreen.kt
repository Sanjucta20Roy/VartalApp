package com.example.vartalapp.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vartalapp.core.model.CourseCategory
import com.example.vartalapp.core.model.DisplayScript
import com.example.vartalapp.core.model.Phrase
import com.example.vartalapp.core.model.UserStats
import com.example.vartalapp.feature.language.LanguageSelectionDialog
import com.example.vartalapp.ui.animation.StreakFireAnimation
import com.example.vartalapp.ui.theme.VartalAppTheme

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    displayScript: DisplayScript,
    selectedLanguageName: String = "Kannada (Bengaluru)",
    onNavigateToLearn: () -> Unit,
    onContinueLesson: (String) -> Unit,
    onNavigateToTranslate: () -> Unit,
    onNavigateToPractice: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onPlayAudio: (String) -> Unit
) {
    val stats = uiState.userStats
    var showLanguageDialog by remember { mutableStateOf(false) }

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            selectedLanguageId = "kannada",
            onDismiss = { showLanguageDialog = false },
            onLanguageSelected = { showLanguageDialog = false },
            onPlayAudio = onPlayAudio
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header with greeting & language chip & settings
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Namaskara! 🙏",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Learn Bengaluru Kannada daily",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Streak Pill
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFFFF3E0),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                StreakFireAnimation(modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${stats.currentStreakDays}d",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE65100),
                                    fontSize = 14.sp
                                )
                            }
                        }

                        IconButton(onClick = onNavigateToSettings) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Interactive Language Selector Chip
                Surface(
                    modifier = Modifier.clickable { showLanguageDialog = true },
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Language, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.secondary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Language: $selectedLanguageName (38 Available) ▾",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }

        // Daily Goal Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Daily Goal Progress",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "${stats.todayPhrasesLearned}/${stats.dailyGoalPhrases} phrases",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    val progressFraction = (stats.todayPhrasesLearned.toFloat() / stats.dailyGoalPhrases.toFloat()).coerceIn(0f, 1f)
                    LinearProgressIndicator(
                        progress = { progressFraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = {
                            val targetLessonId = uiState.nextIncompleteLessonId
                            if (targetLessonId != null) {
                                onContinueLesson(targetLessonId)
                            } else {
                                onNavigateToLearn()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.School, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Continue Learning")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null)
                    }
                }
            }
        }

        // Daily Phrase of the Day
        item {
            uiState.dailyPhrase?.let { phrase ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Daily Phrase",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                            IconButton(
                                onClick = { onPlayAudio(phrase.kannada) },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        if ((displayScript == DisplayScript.BOTH) || (displayScript == DisplayScript.NATIVE)) {
                            Text(
                                text = phrase.kannada,
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        if ((displayScript == DisplayScript.BOTH) || (displayScript == DisplayScript.TRANSLITERATION)) {
                            Text(
                                text = phrase.transliteration,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = phrase.meaning,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "💡 ${phrase.explanation}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }

        // Quick Feature Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Translator Quick Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onNavigateToTranslate),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Translate, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                        Text(
                            text = "Translator",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "English to natural Bengaluru Kannada",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                // Practice Quick Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onNavigateToPractice),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Quiz, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                        Text(
                            text = "Practice",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Quiz & revision exercises",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }
        }

        // Course Categories Title
        item {
            Text(
                text = "Course Categories (16 Phrase Packs)",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Categories List
        items(uiState.categories.chunked(2).size) { rowIndex ->
            val rowCategories = uiState.categories.chunked(2)[rowIndex]
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (category in rowCategories) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = onNavigateToLearn),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = category.title,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = category.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline,
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = "Level ${category.level} • ${category.phraseCount} phrases",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
                if (rowCategories.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
