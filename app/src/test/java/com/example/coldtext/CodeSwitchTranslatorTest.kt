package com.example.coldtext

import com.example.coldtext.data.model.Phrase
import com.example.coldtext.data.model.Topic
import com.example.coldtext.data.model.UserPreferences
import com.example.coldtext.data.repository.PhraseRepository
import com.example.coldtext.data.sample.KANNADA_SAMPLE_PHRASES
import com.example.coldtext.data.translator.CodeSwitchTranslator
import com.example.coldtext.data.translator.MatchType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MockPhraseRepository : PhraseRepository {
    override val userPreferencesFlow: StateFlow<UserPreferences> = MutableStateFlow(UserPreferences())
    override fun getLanguages() = emptyList<com.example.coldtext.data.model.Language>()
    override fun getPhrases(languageId: String, topic: Topic?) = KANNADA_SAMPLE_PHRASES
    override fun getPhraseById(id: String) = KANNADA_SAMPLE_PHRASES.firstOrNull { it.id == id }
    override fun searchPhrases(query: String, languageId: String) = KANNADA_SAMPLE_PHRASES
    override fun saveFavorite(phraseId: String) {}
    override fun removeFavorite(phraseId: String) {}
    override fun toggleFavorite(phraseId: String) {}
    override fun markPhraseCompleted(phraseId: String) {}
    override fun markPhraseWeak(phraseId: String) {}
    override fun removePhraseWeak(phraseId: String) {}
    override fun resetReviewQueue() {}
    override fun getWeakPhrases(languageId: String): List<Phrase> = emptyList()
    override fun saveOfflineFeedback(phraseId: String, feedbackText: String) {}
    override fun clearOfflineFeedbackReports() {}
    override fun updateTargetLanguage(languageId: String) {}
    override fun updateDisplayLanguage(language: String) {}
    override fun updateDailyGoal(goal: Int) {}
    override fun updateUserLevel(level: String) {}
    override fun updateLearningGoal(goal: String) {}
    override fun updateScriptPreference(pref: String) {}
    override fun setOnboardingCompleted(completed: Boolean) {}
    override fun updateAudioSpeed(speed: Float) {}
    override fun updateTextSizeScale(scale: Float) {}
}

class CodeSwitchTranslatorTest {

    private lateinit var repository: PhraseRepository
    private lateinit var translator: CodeSwitchTranslator

    @Before
    fun setUp() {
        repository = MockPhraseRepository()
        translator = CodeSwitchTranslator(repository)
    }

    @Test
    fun testEmptyInput_returnsNeutralUnsupportedState() {
        val result = translator.translate("", "kn")
        assertEquals("", result.codeSwitchLatin)
        assertEquals("", result.codeSwitchNative)
        assertEquals(MatchType.UNSUPPORTED, result.matchType)
    }

    @Test
    fun testNegationHandling_doesNotMatchPositivePhrase() {
        val result = translator.translate("Don't give it to caretaker", "kn")
        assertTrue(result.isNegated)
        assertTrue(result.codeSwitchLatin.contains("koḍabēḍi") || result.codeSwitchLatin.contains("dont"))
        assertFalse(result.codeSwitchLatin == "Caretaker-ge kodi")
    }

    @Test
    fun testDynamicNumberPreservation_retainsFlatNumber() {
        val result = translator.translate("Are you in front of flat 1204?", "kn")
        assertTrue(result.codeSwitchLatin.contains("1204"))
        assertEquals("1204", result.extractedParameters["flat_number"])
    }

    @Test
    fun testDynamicTimePreservation_retainsMinutes() {
        val result = translator.translate("Wait 5 mins I am coming", "kn")
        assertTrue(result.codeSwitchLatin.contains("5"))
        assertEquals("5", result.extractedParameters["minutes"])
    }

    @Test
    fun testExactPhraseMatching_returnsVerifiedMatch() {
        val result = translator.translate("Let's chill a little / Relax a bit.", "kn")
        assertNotNull(result)
        assertEquals("kn_greet_1", result.phraseId)
    }

    @Test
    fun testUnsupportedLongComplexInput_returnsUnsupported() {
        val result = translator.translate("Can you please reschedule the international delivery to next week Tuesday morning?", "kn")
        assertEquals(MatchType.UNSUPPORTED, result.matchType)
    }
}
