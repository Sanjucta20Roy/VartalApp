package com.example.vartalapp

import com.example.vartalapp.data.db.SeedData
import com.example.vartalapp.feature.translator.TranslatorEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ColdTextIMETest {

    private lateinit var translatorEngine: TranslatorEngine

    @Before
    fun setup() {
        translatorEngine = TranslatorEngine(SeedData.initialPhrases.map { it.toDomain() })
    }

    @Test
    fun coldtext_suggestionForCommonPhrase_returnsKannadaScript() {
        val result = translatorEngine.translate("Hello")
        assertTrue(result.transliteration.contains("Namaskara"))
        assertTrue(result.kannadaScript.isNotBlank())
    }

    @Test
    fun coldtext_suggestionForAutoDriverPhrase_returnsIndiranagarPhrase() {
        val result = translatorEngine.translate("Will you come to Indiranagar?")
        assertTrue(result.kannadaScript.contains("ಇಂದಿರಾನಗರ"))
        assertEquals("Exact phrase match", result.confidence)
    }
}
