package com.example.vartalapp

import com.example.vartalapp.core.model.Phrase
import com.example.vartalapp.feature.translator.TranslatorEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TranslatorEngineTest {

    private lateinit var translatorEngine: TranslatorEngine
    private val testPhrases = listOf(
        Phrase(
            id = "p1",
            english = "Hello",
            kannada = "ನಮಸ್ಕಾರ",
            transliteration = "Namaskara",
            meaning = "Hello",
            explanation = "Greeting",
            category = "Greetings",
            level = 1,
            formality = "Polite"
        ),
        Phrase(
            id = "p2",
            english = "Where is the bus stop?",
            kannada = "ಬಸ್ ಸ್ಟಾಪ್ ಎಲ್ಲಿದೆ?",
            transliteration = "Bus stop elli ide?",
            meaning = "Location of bus stop",
            explanation = "Location query",
            category = "Directions",
            level = 1,
            formality = "Neutral"
        )
    )

    @Before
    fun setup() {
        translatorEngine = TranslatorEngine(testPhrases)
    }

    @Test
    fun translate_exactMatch_returnsExactConfidence() {
        val result = translatorEngine.translate("Hello")
        assertEquals("Namaskara", result.transliteration)
        assertEquals("Exact phrase match", result.confidence)
    }

    @Test
    fun translate_otpPreservation_extractsCode() {
        val result = translatorEngine.translate("OTP is 4921")
        assertTrue(result.kannadaScript.contains("4921"))
        assertTrue(result.transliteration.contains("4921"))
        assertEquals("Suggested conversational expression", result.confidence)
    }

    @Test
    fun translate_flatNumber_preservesFlat() {
        val result = translatorEngine.translate("I live in flat 503")
        assertTrue(result.kannadaScript.contains("503"))
        assertTrue(result.transliteration.contains("503"))
    }

    @Test
    fun translate_negativeWait_distinguishesFromPositive() {
        val posResult = translatorEngine.translate("Please wait for one minute")
        val negResult = translatorEngine.translate("Don't wait for me")

        assertTrue(posResult.kannadaScript.contains("ಒಂದು ನಿಮಿಷ"))
        assertTrue(negResult.kannadaScript.contains("ಕಾಯಬೇಡಿ"))
    }

    @Test
    fun translate_unsupportedInput_returnsUnsupportedConfidence() {
        val result = translatorEngine.translate("XYZ Quantum Mechanics Theory 987")
        assertEquals("Unsupported expression", result.confidence)
    }
}
