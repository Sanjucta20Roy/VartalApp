package com.example.vartalapp.feature.learning

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vartalapp.core.model.DisplayScript
import com.example.vartalapp.core.model.Lesson
import com.example.vartalapp.core.model.Phrase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonDetailScreen(
    lesson: Lesson?,
    displayScript: DisplayScript,
    onBackClick: () -> Unit,
    onPlayAudio: (String) -> Unit,
    onLessonCompleted: (String) -> Unit
) {
    if (lesson == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Lesson not found")
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onBackClick) { Text("Go Back") }
        }
        return
    }

    var isCompleted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(lesson.title, fontWeight = FontWeight.Bold) },
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
            // Situation Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "📍 Real-Life Situation",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = lesson.situation,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "🎯 Objective: ${lesson.objective}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            // Phrase Cards Title
            item {
                Text(
                    text = "Key Natural Phrases (${lesson.phrases.size})",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            }

            // Phrases
            itemsIndexed(lesson.phrases) { index, phrase ->
                LessonPhraseCard(
                    index = index + 1,
                    phrase = phrase,
                    displayScript = displayScript,
                    onPlayAudio = { onPlayAudio(phrase.kannada) }
                )
            }

            // Completion Action
            item {
                Spacer(modifier = Modifier.height(8.dp))
                if (isCompleted) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("Lesson Completed! 🎉", fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
                                Text("Your progress and daily goal have been updated.", fontSize = 12.sp, color = Color(0xFF2E7D32))
                            }
                        }
                    }
                } else {
                    Button(
                        onClick = {
                            isCompleted = true
                            onLessonCompleted(lesson.id)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Complete Lesson", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun LessonPhraseCard(
    index: Int,
    phrase: Phrase,
    displayScript: DisplayScript,
    onPlayAudio: () -> Unit
) {
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "$index",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = phrase.formality,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onPlayAudio,
                    modifier = Modifier
                        .size(36.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play Audio", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Kannada Script
            if (displayScript == DisplayScript.BOTH || displayScript == DisplayScript.NATIVE) {
                Text(
                    text = phrase.kannada,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Latin Transliteration
            if (displayScript == DisplayScript.BOTH || displayScript == DisplayScript.TRANSLITERATION) {
                Text(
                    text = phrase.transliteration,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // English Meaning
            Text(
                text = "Meaning: ${phrase.english}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Explanation & Context
            Text(
                text = "💡 Usage: ${phrase.explanation}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )

            // Alternative if available
            phrase.alternative?.let { alt ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "🔄 Alternative: $alt",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
