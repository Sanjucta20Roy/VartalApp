package com.example.vartalapp

import com.example.vartalapp.core.model.DisplayScript
import com.example.vartalapp.core.model.UserPreferences
import com.example.vartalapp.data.db.SeedData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PhraseRepositoryTest {

    @Test
    fun seedData_contains16PacksAndPhrases() {
        assertEquals(16, SeedData.packs.size)
        assertTrue(SeedData.initialPhrases.isNotEmpty())
    }

    @Test
    fun seedData_phrasesHaveValidFields() {
        SeedData.initialPhrases.forEach { phrase ->
            assertNotNull(phrase.id)
            assertNotNull(phrase.english)
            assertNotNull(phrase.kannada)
            assertNotNull(phrase.transliteration)
            assertNotNull(phrase.meaning)
            assertNotNull(phrase.explanation)
            assertNotNull(phrase.category)
            assertTrue(phrase.level in 1..5)
        }
    }

    @Test
    fun userPreferences_defaultValuesAreValid() {
        val prefs = UserPreferences()
        assertEquals("Kannada (Bengaluru)", prefs.learningLanguage)
        assertEquals(DisplayScript.BOTH, prefs.displayScript)
        assertEquals(5, prefs.dailyGoalPhrases)
        assertEquals(1.0f, prefs.audioSpeed)
        assertEquals("SYSTEM", prefs.themeMode)
        assertTrue(prefs.notificationsEnabled)
    }
}
