package com.example.vartalapp.feature.learning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vartalapp.core.model.Lesson
import com.example.vartalapp.data.repository.PhraseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class LearnUiState(
    val selectedLevel: Int = 1,
    val lessons: List<Lesson> = emptyList(),
    val completedLessonIds: Set<String> = emptySet(),
    val isLoading: Boolean = true
)

class LearnViewModel(private val repository: PhraseRepository) : ViewModel() {

    val selectedLevel = MutableStateFlow(1)

    val uiState: StateFlow<LearnUiState> = combine(
        selectedLevel.flatMapLatest { level -> repository.getLessonsForLevel(level) },
        repository.completedLessonIds,
        selectedLevel
    ) { lessons, completedIds, level ->
        LearnUiState(
            selectedLevel = level,
            lessons = lessons,
            completedLessonIds = completedIds,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LearnUiState()
    )

    fun selectLevel(level: Int) {
        selectedLevel.value = level
    }

    fun completeLesson(lessonId: String) {
        viewModelScope.launch {
            repository.markLessonCompleted(lessonId)
        }
    }
}
