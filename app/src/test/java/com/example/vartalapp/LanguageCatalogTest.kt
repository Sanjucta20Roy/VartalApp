package com.example.vartalapp

import com.example.vartalapp.data.LanguageAvailability
import com.example.vartalapp.data.LanguageCatalog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LanguageCatalogTest {

    @Test
    fun catalog_contains49Languages() {
        assertEquals(49, LanguageCatalog.languages.size)
    }

    @Test
    fun catalog_kannadaIsActiveAndAvailable() {
        val kannada = LanguageCatalog.languages.find { it.id == "kannada" }
        assertNotNull(kannada)
        assertEquals(LanguageAvailability.AVAILABLE, kannada?.availability)
        assertEquals(160, kannada?.totalPhrasesCount)
    }

    @Test
    fun catalog_previewLanguagesHaveSamplePhrases() {
        val previews = LanguageCatalog.languages.filter { it.availability == LanguageAvailability.PREVIEW }
        assertEquals(48, previews.size)
        previews.forEach { lang ->
            assertTrue(lang.previewPhrases.isNotEmpty())
        }
    }
}
