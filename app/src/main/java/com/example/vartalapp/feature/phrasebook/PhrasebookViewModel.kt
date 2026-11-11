package com.example.vartalapp.feature.phrasebook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vartalapp.core.model.Phrase
import com.example.vartalapp.data.repository.PhraseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PhrasebookUiState(
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val showFavoritesOnly: Boolean = false,
    val showBareMinimumOnly: Boolean = false,
    val phrases: List<Phrase> = emptyList(),
    val bookmarkedIds: Set<String> = emptySet(),
    val selectedPhraseDetail: Phrase? = null,
    val isLoading: Boolean = true
)

class PhrasebookViewModel(private val repository: PhraseRepository) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val selectedCategory = MutableStateFlow<String?>(null)
    private val showFavoritesOnly = MutableStateFlow(false)
    private val showBareMinimumOnly = MutableStateFlow(false)
    private val selectedPhraseDetail = MutableStateFlow<Phrase?>(null)

    @Suppress("UNCHECKED_CAST")
    val uiState: StateFlow<PhrasebookUiState> = combine(
        searchQuery.flatMapLatest { q -> repository.searchPhrases(q) },
        selectedCategory,
        showFavoritesOnly,
        showBareMinimumOnly,
        repository.bookmarkedPhraseIds,
        selectedPhraseDetail
    ) { flows: Array<Any?> ->
        val phrases = flows[0] as List<Phrase>
        val category = flows[1] as String?
        val favoritesOnly = flows[2] as Boolean
        val bareMinimumOnly = flows[3] as Boolean
        val bookmarks = flows[4] as Set<String>
        val selectedDetail = flows[5] as Phrase?

        var filtered = phrases
        if (category != null) {
            filtered = filtered.filter { it.category == category }
        }
        if (favoritesOnly) {
            filtered = filtered.filter { bookmarks.contains(it.id) }
        }
        if (bareMinimumOnly) {
            filtered = filtered.filter { it.formality.contains("Kanglish") || it.formality.contains("Survival") || it.transliteration.split(" ").size <= 3 }
        }

        PhrasebookUiState(
            searchQuery = searchQuery.value,
            selectedCategory = category,
            showFavoritesOnly = favoritesOnly,
            showBareMinimumOnly = bareMinimumOnly,
            phrases = filtered,
            bookmarkedIds = bookmarks,
            selectedPhraseDetail = selectedDetail,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PhrasebookUiState()
    )

    fun onSearchQueryChanged(newQuery: String) {
        searchQuery.value = newQuery
    }

    fun selectCategory(category: String?) {
        selectedCategory.value = category
    }

    fun toggleFavoritesOnly() {
        showFavoritesOnly.value = !showFavoritesOnly.value
    }

    fun toggleBareMinimumOnly() {
        showBareMinimumOnly.value = !showBareMinimumOnly.value
    }

    fun toggleBookmark(phraseId: String) {
        viewModelScope.launch {
            repository.toggleBookmark(phraseId)
        }
    }

    fun openPhraseDetail(phrase: Phrase?) {
        selectedPhraseDetail.value = phrase
    }
}
