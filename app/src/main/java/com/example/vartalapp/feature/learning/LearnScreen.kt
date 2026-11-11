package com.example.vartalapp.feature.learning

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vartalapp.core.model.Lesson

val levelTitles = listOf(
    "Level 1: Absolute Beginner",
    "Level 2: Basic Conversations",
    "Level 3: Everyday Communication",
    "Level 4: Intermediate Conversations",
    "Level 5: Natural & Fluent Expression"
)

@Composable
fun LearnScreen(
    uiState: LearnUiState,
    onLevelSelected: (Int) -> Unit,
    onLessonClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Kannada Learning Course",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = "Master practical Bengaluru Kannada through real-life situation packs",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Level Tabs
        ScrollableTabRow(
            selectedTabIndex = uiState.selectedLevel - 1,
            edgePadding = 16.dp,
            divider = {}
        ) {
            levelTitles.forEachIndexed { index, title ->
                val levelNum = index + 1
                Tab(
                    selected = uiState.selectedLevel == levelNum,
                    onClick = { onLevelSelected(levelNum) },
                    text = {
                        Text(
                            text = "Level $levelNum",
                            fontWeight = if (uiState.selectedLevel == levelNum) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Level Banner Header
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = levelTitles.getOrElse(uiState.selectedLevel - 1) { "Level ${uiState.selectedLevel}" },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Lessons List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.lessons) { lesson ->
                val isCompleted = uiState.completedLessonIds.contains(lesson.id)
                LessonCard(
                    lesson = lesson,
                    isCompleted = isCompleted,
                    onClick = { onLessonClick(lesson.id) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun LessonCard(
    lesson: Lesson,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "Pack ${lesson.packIndex}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                if (isCompleted) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = "Completed", tint = Color(0xFF4CAF50))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Completed", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = lesson.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "📍 Situation: ${lesson.situation}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "🎯 Goal: ${lesson.objective}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${lesson.phrases.size} Phrases in Pack",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isCompleted) "Review Lesson" else "Start Lesson")
                }
            }
        }
    }
}
