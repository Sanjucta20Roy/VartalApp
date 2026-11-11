package com.example.vartalapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vartalapp.core.model.CourseCategory
import com.example.vartalapp.core.model.Phrase
import com.example.vartalapp.core.model.UserStats
import com.example.vartalapp.data.repository.PhraseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val userStats: UserStats = UserStats(),
    val categories: List<CourseCategory> = emptyList(),
    val dailyPhrase: Phrase? = null,
    val nextIncompleteLessonId: String? = null,
    val isLoading: Boolean = true
)

class HomeViewModel(private val repository: PhraseRepository) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        repository.userStats,
        repository.courseCategories,
        repository.allPhrases,
        repository.completedLessonIds
    ) { stats, categories, phrases, completedIds ->
        val dailyPhrase = if (phrases.isNotEmpty()) {
            val dayOfYear = java.time.LocalDate.now().dayOfYear
            phrases[dayOfYear % phrases.size]
        } else null

        // Find the first incomplete pack/lesson ID across categories
        val nextLessonId = categories.find { !completedIds.contains(it.id) }?.id ?: categories.firstOrNull()?.id

        HomeUiState(
            userStats = stats,
            categories = categories,
            dailyPhrase = dailyPhrase,
            nextIncompleteLessonId = nextLessonId,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )
}
