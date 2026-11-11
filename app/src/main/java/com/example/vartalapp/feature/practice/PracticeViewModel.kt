package com.example.vartalapp.feature.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vartalapp.core.model.PracticeQuestion
import com.example.vartalapp.data.repository.PhraseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PracticeUiState(
    val questions: List<PracticeQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedOption: String? = null,
    val isAnswered: Boolean = false,
    val isCorrect: Boolean = false,
    val score: Int = 0,
    val isQuizFinished: Boolean = false,
    val isLoading: Boolean = true
)

class PracticeViewModel(private val repository: PhraseRepository) : ViewModel() {

    private val questions = MutableStateFlow<List<PracticeQuestion>>(emptyList())
    private val currentIndex = MutableStateFlow(0)
    private val selectedOption = MutableStateFlow<String?>(null)
    private val isAnswered = MutableStateFlow(false)
    private val isCorrect = MutableStateFlow(false)
    private val score = MutableStateFlow(0)
    private val isQuizFinished = MutableStateFlow(false)

    private val engine = PracticeEngine()

    init {
        startNewQuiz()
    }

    fun startNewQuiz() {
        viewModelScope.launch {
            repository.allPhrases.collect { phrases ->
                if (phrases.isNotEmpty()) {
                    val quiz = engine.generateQuiz(phrases, count = 5)
                    questions.value = quiz
                    currentIndex.value = 0
                    selectedOption.value = null
                    isAnswered.value = false
                    isCorrect.value = false
                    score.value = 0
                    isQuizFinished.value = false
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    val uiState: StateFlow<PracticeUiState> = combine(
        questions,
        currentIndex,
        selectedOption,
        isAnswered,
        isCorrect,
        score,
        isQuizFinished
    ) { flows: Array<Any?> ->
        val qList = flows[0] as List<PracticeQuestion>
        val idx = flows[1] as Int
        val opt = flows[2] as String?
        val answered = flows[3] as Boolean
        val correct = flows[4] as Boolean
        val sc = flows[5] as Int
        val finished = flows[6] as Boolean

        PracticeUiState(
            questions = qList,
            currentIndex = idx,
            selectedOption = opt,
            isAnswered = answered,
            isCorrect = correct,
            score = sc,
            isQuizFinished = finished,
            isLoading = qList.isEmpty()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PracticeUiState()
    )

    fun submitAnswer(option: String) {
        if (isAnswered.value) return
        val currentQ = questions.value.getOrNull(currentIndex.value) ?: return

        selectedOption.value = option
        isAnswered.value = true
        val correct = option == currentQ.correctAnswer
        isCorrect.value = correct

        if (correct) {
            score.value += 1
        }

        viewModelScope.launch {
            repository.recordPhrasePractice(currentQ.phrase.id, correct)
        }
    }

    fun nextQuestion() {
        if (currentIndex.value + 1 < questions.value.size) {
            currentIndex.value += 1
            selectedOption.value = null
            isAnswered.value = false
            isCorrect.value = false
        } else {
            isQuizFinished.value = true
        }
    }
}
