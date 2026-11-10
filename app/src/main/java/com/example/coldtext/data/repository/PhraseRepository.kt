package com.example.coldtext.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.coldtext.data.model.INDIAN_LANGUAGES
import com.example.coldtext.data.model.Language
import com.example.coldtext.data.model.Phrase
import com.example.coldtext.data.model.Topic
import com.example.coldtext.data.model.UserPreferences
import com.example.coldtext.data.sample.HINDI_SAMPLE_PHRASES
import com.example.coldtext.data.sample.KANNADA_SAMPLE_PHRASES
import com.example.coldtext.data.sample.MALAYALAM_SAMPLE_PHRASES
import com.example.coldtext.data.sample.MARATHI_SAMPLE_PHRASES
import com.example.coldtext.data.sample.TAMIL_SAMPLE_PHRASES
import com.example.coldtext.data.sample.TELUGU_SAMPLE_PHRASES
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface PhraseRepository {
    val userPreferencesFlow: StateFlow<UserPreferences>

    fun getLanguages(): List<Language>
    fun getPhrases(languageId: String = "kn", topic: Topic? = null): List<Phrase>
    fun getPhraseById(id: String): Phrase?
    fun searchPhrases(query: String, languageId: String = "kn"): List<Phrase>

    fun saveFavorite(phraseId: String)
    fun removeFavorite(phraseId: String)
    fun toggleFavorite(phraseId: String)

    fun markPhraseCompleted(phraseId: String)
    fun markPhraseWeak(phraseId: String)
    fun removePhraseWeak(phraseId: String)
    fun resetReviewQueue()
    fun getWeakPhrases(languageId: String = "kn"): List<Phrase>

    fun saveOfflineFeedback(phraseId: String, feedbackText: String)
    fun clearOfflineFeedbackReports()

    fun updateTargetLanguage(languageId: String)
    fun updateDisplayLanguage(language: String)
    fun updateDailyGoal(goal: Int)
    fun updateUserLevel(level: String)
    fun updateLearningGoal(goal: String)
    fun updateScriptPreference(pref: String)
    fun setOnboardingCompleted(completed: Boolean)
    fun updateAudioSpeed(speed: Float)
    fun updateTextSizeScale(scale: Float)
}

class PhraseRepositoryImpl(context: Context) : PhraseRepository {
    private val prefs: SharedPreferences = context.getSharedPreferences("varta_prefs", Context.MODE_PRIVATE)

    private val _userPreferences = MutableStateFlow(loadPreferences())
    override val userPreferencesFlow: StateFlow<UserPreferences> = _userPreferences.asStateFlow()

    private fun loadPreferences(): UserPreferences {
        val completed = prefs.getStringSet("completed_phrases", emptySet()) ?: emptySet()
        val saved = prefs.getStringSet("saved_phrases", emptySet()) ?: emptySet()
        val weak = prefs.getStringSet("weak_phrases", emptySet()) ?: emptySet()
        val feedback = prefs.getStringSet("feedback_reports", emptySet())?.toList() ?: emptyList()
        val displayLang = prefs.getString("display_lang", "English") ?: "English"
        val targetLang = prefs.getString("target_lang", "kn") ?: "kn"
        val userLevel = prefs.getString("user_level", "Beginner") ?: "Beginner"
        val learningGoal = prefs.getString("learning_goal", "Casual Hangouts") ?: "Casual Hangouts"
        val scriptPref = prefs.getString("script_pref", "Both Native & Latin") ?: "Both Native & Latin"
        val dailyGoal = prefs.getInt("daily_goal", 5)
        val streak = prefs.getInt("streak_days", 1)
        val audioSpeed = prefs.getFloat("audio_speed", 1.0f)
        val textSizeScale = prefs.getFloat("text_size_scale", 1.0f)
        val onboarding = prefs.getBoolean("onboarding_completed", false)

        return UserPreferences(
            displayLanguage = displayLang,
            targetLanguageId = targetLang,
            userLevel = userLevel,
            learningGoal = learningGoal,
            scriptPreference = scriptPref,
            dailyGoalPhrases = dailyGoal,
            streakDays = streak,
            completedPhraseIds = completed,
            savedPhraseIds = saved,
            weakPhraseIds = weak,
            offlineFeedbackReports = feedback,
            audioSpeed = audioSpeed,
            textSizeScale = textSizeScale,
            isOnboardingCompleted = onboarding
        )
    }

    override fun getLanguages(): List<Language> = INDIAN_LANGUAGES

    override fun getPhrases(languageId: String, topic: Topic?): List<Phrase> {
        val allPhrases = when (languageId) {
            "kn" -> KANNADA_SAMPLE_PHRASES
            "ta" -> TAMIL_SAMPLE_PHRASES
            "te" -> TELUGU_SAMPLE_PHRASES
            "hi" -> HINDI_SAMPLE_PHRASES
            "ml" -> MALAYALAM_SAMPLE_PHRASES
            "mr" -> MARATHI_SAMPLE_PHRASES
            else -> emptyList()
        }
        return if (topic != null) {
            allPhrases.filter { it.topic == topic }
        } else {
            allPhrases
        }
    }

    override fun getPhraseById(id: String): Phrase? {
        val all = KANNADA_SAMPLE_PHRASES + TAMIL_SAMPLE_PHRASES + TELUGU_SAMPLE_PHRASES + HINDI_SAMPLE_PHRASES + MALAYALAM_SAMPLE_PHRASES + MARATHI_SAMPLE_PHRASES
        return all.firstOrNull { it.id == id }
    }

    override fun searchPhrases(query: String, languageId: String): List<Phrase> {
        val phrases = getPhrases(languageId)
        if (query.isBlank()) return phrases
        val q = query.trim().lowercase()
        return phrases.filter { phrase ->
            phrase.latinTransliteration.lowercase().contains(q) ||
                    phrase.nativeScript.lowercase().contains(q) ||
                    phrase.englishMeaning.lowercase().contains(q) ||
                    phrase.pronunciationNotes.lowercase().contains(q)
        }
    }

    override fun saveFavorite(phraseId: String) {
        val currentSaved = _userPreferences.value.savedPhraseIds.toMutableSet()
        currentSaved.add(phraseId)
        prefs.edit().putStringSet("saved_phrases", currentSaved).apply()
        _userPreferences.value = _userPreferences.value.copy(savedPhraseIds = currentSaved)
    }

    override fun removeFavorite(phraseId: String) {
        val currentSaved = _userPreferences.value.savedPhraseIds.toMutableSet()
        currentSaved.remove(phraseId)
        prefs.edit().putStringSet("saved_phrases", currentSaved).apply()
        _userPreferences.value = _userPreferences.value.copy(savedPhraseIds = currentSaved)
    }

    override fun toggleFavorite(phraseId: String) {
        if (_userPreferences.value.savedPhraseIds.contains(phraseId)) {
            removeFavorite(phraseId)
        } else {
            saveFavorite(phraseId)
        }
    }

    override fun markPhraseCompleted(phraseId: String) {
        val currentCompleted = _userPreferences.value.completedPhraseIds.toMutableSet()
        if (!currentCompleted.contains(phraseId)) {
            currentCompleted.add(phraseId)
            val newStreak = _userPreferences.value.streakDays.coerceAtLeast(1)
            prefs.edit()
                .putStringSet("completed_phrases", currentCompleted)
                .putInt("streak_days", newStreak)
                .apply()
            _userPreferences.value = _userPreferences.value.copy(
                completedPhraseIds = currentCompleted,
                streakDays = newStreak
            )
        }
    }

    override fun markPhraseWeak(phraseId: String) {
        val currentWeak = _userPreferences.value.weakPhraseIds.toMutableSet()
        currentWeak.add(phraseId)
        prefs.edit().putStringSet("weak_phrases", currentWeak).apply()
        _userPreferences.value = _userPreferences.value.copy(weakPhraseIds = currentWeak)
    }

    override fun removePhraseWeak(phraseId: String) {
        val currentWeak = _userPreferences.value.weakPhraseIds.toMutableSet()
        currentWeak.remove(phraseId)
        prefs.edit().putStringSet("weak_phrases", currentWeak).apply()
        _userPreferences.value = _userPreferences.value.copy(weakPhraseIds = currentWeak)
    }

    override fun resetReviewQueue() {
        prefs.edit().remove("weak_phrases").apply()
        _userPreferences.value = _userPreferences.value.copy(weakPhraseIds = emptySet())
    }

    override fun getWeakPhrases(languageId: String): List<Phrase> {
        val allPhrases = getPhrases(languageId)
        val weakIds = _userPreferences.value.weakPhraseIds
        return allPhrases.filter { weakIds.contains(it.id) }
    }

    override fun saveOfflineFeedback(phraseId: String, feedbackText: String) {
        val currentReports = _userPreferences.value.offlineFeedbackReports.toMutableList()
        val entry = "[$phraseId] $feedbackText"
        currentReports.add(entry)
        prefs.edit().putStringSet("feedback_reports", currentReports.toSet()).apply()
        _userPreferences.value = _userPreferences.value.copy(offlineFeedbackReports = currentReports)
    }

    override fun clearOfflineFeedbackReports() {
        prefs.edit().remove("feedback_reports").apply()
        _userPreferences.value = _userPreferences.value.copy(offlineFeedbackReports = emptyList())
    }

    override fun updateTargetLanguage(languageId: String) {
        prefs.edit().putString("target_lang", languageId).apply()
        _userPreferences.value = _userPreferences.value.copy(targetLanguageId = languageId)
    }

    override fun updateDisplayLanguage(language: String) {
        prefs.edit().putString("display_lang", language).apply()
        _userPreferences.value = _userPreferences.value.copy(displayLanguage = language)
    }

    override fun updateDailyGoal(goal: Int) {
        prefs.edit().putInt("daily_goal", goal).apply()
        _userPreferences.value = _userPreferences.value.copy(dailyGoalPhrases = goal)
    }

    override fun updateUserLevel(level: String) {
        prefs.edit().putString("user_level", level).apply()
        _userPreferences.value = _userPreferences.value.copy(userLevel = level)
    }

    override fun updateLearningGoal(goal: String) {
        prefs.edit().putString("learning_goal", goal).apply()
        _userPreferences.value = _userPreferences.value.copy(learningGoal = goal)
    }

    override fun updateScriptPreference(pref: String) {
        prefs.edit().putString("script_pref", pref).apply()
        _userPreferences.value = _userPreferences.value.copy(scriptPreference = pref)
    }

    override fun setOnboardingCompleted(completed: Boolean) {
        prefs.edit().putBoolean("onboarding_completed", completed).apply()
        _userPreferences.value = _userPreferences.value.copy(isOnboardingCompleted = completed)
    }

    override fun updateAudioSpeed(speed: Float) {
        prefs.edit().putFloat("audio_speed", speed).apply()
        _userPreferences.value = _userPreferences.value.copy(audioSpeed = speed)
    }

    override fun updateTextSizeScale(scale: Float) {
        prefs.edit().putFloat("text_size_scale", scale).apply()
        _userPreferences.value = _userPreferences.value.copy(textSizeScale = scale)
    }
}
