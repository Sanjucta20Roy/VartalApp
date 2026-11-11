package com.example.vartalapp.feature.translator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vartalapp.core.model.TranslationResult
import com.example.vartalapp.data.repository.PhraseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TranslatorUiState(
    val inputText: String = "",
    val result: TranslationResult? = null,
    val selectedSituation: String? = null,
    val isBookmarked: Boolean = false,
    val bookmarkedIds: Set<String> = emptySet()
)

class TranslatorViewModel(private val repository: PhraseRepository) : ViewModel() {

    private val inputText = MutableStateFlow("")
    private val selectedSituation = MutableStateFlow<String?>(null)
    private var translatorEngine: TranslatorEngine? = null

    init {
        viewModelScope.launch {
            repository.allPhrases.collect { phrases ->
                translatorEngine = TranslatorEngine(phrases)
            }
        }
    }

    val uiState: StateFlow<TranslatorUiState> = combine(
        inputText,
        selectedSituation,
        repository.bookmarkedPhraseIds
    ) { input, situation, bookmarks ->
        val engine = translatorEngine
        val result = if (input.isNotBlank() && engine != null) {
            engine.translate(input, situation)
        } else null

        TranslatorUiState(
            inputText = input,
            result = result,
            selectedSituation = situation,
            bookmarkedIds = bookmarks
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TranslatorUiState()
    )

    fun onInputChanged(newText: String) {
        inputText.value = newText
    }

    fun clearInput() {
        inputText.value = ""
    }

    fun selectSituation(situation: String?) {
        selectedSituation.value = situation
    }

    fun toggleBookmark(phraseId: String) {
        viewModelScope.launch {
            repository.toggleBookmark(phraseId)
        }
    }
}
