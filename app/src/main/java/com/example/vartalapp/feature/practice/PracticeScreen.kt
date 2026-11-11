package com.example.vartalapp.feature.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vartalapp.core.model.DisplayScript

@Composable
fun PracticeScreen(
    uiState: PracticeUiState,
    displayScript: DisplayScript,
    onOptionSelected: (String) -> Unit,
    onNextQuestion: () -> Unit,
    onRestartQuiz: () -> Unit,
    onPlayAudio: (String) -> Unit
) {
    if (uiState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Preparing practice exercises...")
        }
        return
    }

    if (uiState.isQuizFinished) {
        // Quiz Completion Screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFE8F5E9),
                modifier = Modifier.size(90.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("🏆", fontSize = 40.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Quiz Completed!",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "You scored ${uiState.score} out of ${uiState.questions.size}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onRestartQuiz,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Start Another Quiz")
            }
        }
        return
    }

    val question = uiState.questions.getOrNull(uiState.currentIndex) ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Bar & Progress
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Question ${uiState.currentIndex + 1}/${uiState.questions.size}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Text(
                    text = "Score: ${uiState.score}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        LinearProgressIndicator(
            progress = { (uiState.currentIndex + 1).toFloat() / uiState.questions.size.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        // Prompt Card
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
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        Text(
                            text = question.type.name.replace("_", " "),
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    IconButton(
                        onClick = { onPlayAudio(question.phrase.kannada) },
                        modifier = Modifier
                            .size(36.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Play Audio", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = question.prompt,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Options List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(question.options) { option ->
                val isSelected = uiState.selectedOption == option
                val isCorrectAnswer = option == question.correctAnswer
                val backgroundColor = when {
                    uiState.isAnswered && isCorrectAnswer -> Color(0xFFE8F5E9)
                    uiState.isAnswered && isSelected && !uiState.isCorrect -> Color(0xFFFFEBEE)
                    isSelected -> MaterialTheme.colorScheme.primaryContainer
                    else -> MaterialTheme.colorScheme.surface
                }
                val borderColor = when {
                    uiState.isAnswered && isCorrectAnswer -> Color(0xFF4CAF50)
                    uiState.isAnswered && isSelected && !uiState.isCorrect -> Color(0xFFF44336)
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.outlineVariant
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !uiState.isAnswered) { onOptionSelected(option) }
                        .border(2.dp, borderColor, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    color = backgroundColor
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (isSelected || (uiState.isAnswered && isCorrectAnswer)) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.weight(1f)
                        )

                        if (uiState.isAnswered && isCorrectAnswer) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Correct", tint = Color(0xFF2E7D32))
                        } else if (uiState.isAnswered && isSelected && !uiState.isCorrect) {
                            Icon(Icons.Default.Close, contentDescription = "Wrong", tint = Color(0xFFC62828))
                        }
                    }
                }
            }
        }

        // Feedback & Next Button
        if (uiState.isAnswered) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (uiState.isCorrect) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = if (uiState.isCorrect) "Correct! 🎉" else "Not quite right!",
                        fontWeight = FontWeight.Bold,
                        color = if (uiState.isCorrect) Color(0xFF1B5E20) else Color(0xFFB71C1C)
                    )
                    Text(
                        text = question.explanation,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Button(
                onClick = onNextQuestion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Next Question", fontWeight = FontWeight.Bold)
            }
        }
    }
}
