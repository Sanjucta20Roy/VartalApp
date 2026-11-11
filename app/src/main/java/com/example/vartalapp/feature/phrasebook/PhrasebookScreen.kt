package com.example.vartalapp.feature.phrasebook

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vartalapp.core.model.DisplayScript
import com.example.vartalapp.core.model.Phrase

val categoriesList = listOf(
    "Greetings & Introductions",
    "Numbers & Money",
    "Transport & Auto Drivers",
    "Apartment & Security Guard",
    "Delivery & Courier",
    "Housekeeper & Maid",
    "Shopping & Grocery Store",
    "Food & Restaurants",
    "Office & Work",
    "Directions & Asking Help",
    "Health & Pharmacy",
    "Everyday Small Talk",
    "Phone Conversations",
    "Emergencies & Urgent Needs",
    "Bengaluru Specific Expressions",
    "Time, Dates & Schedules"
)

@Composable
fun PhrasebookScreen(
    uiState: PhrasebookUiState,
    displayScript: DisplayScript,
    onQueryChanged: (String) -> Unit,
    onCategorySelected: (String?) -> Unit,
    onToggleFavoritesOnly: () -> Unit,
    onToggleBareMinimumOnly: () -> Unit = {},
    onToggleBookmark: (String) -> Unit,
    onOpenDetail: (Phrase?) -> Unit,
    onPlayAudio: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        // Title
        Text(
            text = "Conversational Phrasebook",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = "Search and explore Bengaluru Kanglish & City Mover expressions",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onQueryChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Search English, Kanglish, or Kannada...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = {
                if (uiState.searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onQueryChanged("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Filter Chips Row
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)
        ) {
            item {
                FilterChip(
                    selected = uiState.showBareMinimumOnly,
                    onClick = onToggleBareMinimumOnly,
                    label = { Text("⚡ Bare Minimum Kanglish") }
                )
            }
            item {
                FilterChip(
                    selected = uiState.showFavoritesOnly,
                    onClick = onToggleFavoritesOnly,
                    label = { Text("⭐ Favorites Only") }
                )
            }
            item {
                FilterChip(
                    selected = uiState.selectedCategory == null,
                    onClick = { onCategorySelected(null) },
                    label = { Text("All Categories") }
                )
            }
            items(categoriesList) { cat ->
                FilterChip(
                    selected = uiState.selectedCategory == cat,
                    onClick = { onCategorySelected(cat) },
                    label = { Text(cat) }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Showing ${uiState.phrases.size} phrases",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Phrase Cards List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.phrases) { phrase ->
                val isBookmarked = uiState.bookmarkedIds.contains(phrase.id)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenDetail(phrase) },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = phrase.category,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }

                            Row {
                                IconButton(onClick = { onPlayAudio(phrase.kannada) }) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = MaterialTheme.colorScheme.primary)
                                }
                                IconButton(onClick = { onToggleBookmark(phrase.id) }) {
                                    Icon(
                                        imageVector = if (isBookmarked) Icons.Default.Star else Icons.Default.StarBorder,
                                        contentDescription = "Bookmark",
                                        tint = if (isBookmarked) Color(0xFFFFB300) else MaterialTheme.colorScheme.outline
                                    )
                                }
                            }
                        }

                        if (displayScript == DisplayScript.BOTH || displayScript == DisplayScript.NATIVE) {
                            Text(
                                text = phrase.kannada,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        if (displayScript == DisplayScript.BOTH || displayScript == DisplayScript.TRANSLITERATION) {
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
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Phrase Detail Dialog
    uiState.selectedPhraseDetail?.let { phrase ->
        AlertDialog(
            onDismissRequest = { onOpenDetail(null) },
            title = { Text(phrase.english, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Kannada Script:\n${phrase.kannada}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(text = "Transliteration: ${phrase.transliteration}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    Text(text = "Meaning: ${phrase.meaning}")
                    Text(text = "💡 Explanation: ${phrase.explanation}", fontSize = 12.sp)
                    Text(text = "Formality: ${phrase.formality}", fontSize = 12.sp)
                    phrase.alternative?.let { Text(text = "🔄 Alternative: $it", fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary) }
                    Text(text = "Verification: ${phrase.verificationStatus}", fontSize = 11.sp, color = Color(0xFF4CAF50))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onPlayAudio(phrase.kannada)
                }) {
                    Text("🔊 Listen")
                }
            },
            dismissButton = {
                TextButton(onClick = { onOpenDetail(null) }) {
                    Text("Close")
                }
            }
        )
    }
}
