package com.example.coldtext.data.translator

import com.example.coldtext.data.model.Phrase
import com.example.coldtext.data.model.WordGloss
import com.example.coldtext.data.repository.PhraseRepository

enum class MatchType {
    VERIFIED_EXACT,
    PATTERN_TRANSFORMED
}

data class CodeSwitchResult(
    val englishInput: String,
    val codeSwitchLatin: String,
    val codeSwitchNative: String,
    val wordGloss: List<WordGloss>,
    val literalGloss: String,
    val matchType: MatchType,
    val phraseId: String? = null
)

class CodeSwitchTranslator(private val repository: PhraseRepository) {

    fun translate(englishInput: String, targetLanguageId: String = "kn"): CodeSwitchResult {
        if (englishInput.isBlank()) {
            return CodeSwitchResult(
                englishInput = "",
                codeSwitchLatin = "Caretaker-ge kodi",
                codeSwitchNative = "ಕೇರ್‌ಟೇಕರ್‌ಗೆ ಕೊಡಿ",
                wordGloss = listOf(
                    WordGloss("Caretaker-ge", "ಕೇರ್‌ಟೇಕರ್‌ಗೆ", "To caretaker", "Noun + Postposition"),
                    WordGloss("kodi", "ಕೊಡಿ", "Give (polite)", "Verb")
                ),
                literalGloss = "Word breakdown: Caretaker-ge (to caretaker) + kodi (give - polite request)",
                matchType = MatchType.VERIFIED_EXACT,
                phraseId = "kn_delivery_1"
            )
        }

        val q = englishInput.trim().lowercase()
        val phrases = repository.getPhrases(targetLanguageId)

        // 1. Try exact or high confidence repository match
        val exactMatch = phrases.firstOrNull { phrase ->
            phrase.englishMeaning.lowercase().contains(q) ||
                    q.contains(phrase.englishMeaning.lowercase().replace(".", "").replace("!", "")) ||
                    phrase.latinTransliteration.lowercase().contains(q)
        }

        if (exactMatch != null) {
            return CodeSwitchResult(
                englishInput = englishInput,
                codeSwitchLatin = exactMatch.latinTransliteration,
                codeSwitchNative = exactMatch.nativeScript,
                wordGloss = exactMatch.wordGloss,
                literalGloss = exactMatch.literalGloss ?: "Word breakdown: ${exactMatch.pronunciationNotes}",
                matchType = MatchType.VERIFIED_EXACT,
                phraseId = exactMatch.id
            )
        }

        // 2. Apply Rule-Based Code-Switching Pattern Generator for English -> English + Regional Code-Switch
        return generateCodeSwitchPattern(englishInput, q, targetLanguageId)
    }

    private fun generateCodeSwitchPattern(original: String, query: String, langId: String): CodeSwitchResult {
        return when {
            query.contains("caretaker") || query.contains("watchman") || query.contains("security") -> {
                CodeSwitchResult(
                    englishInput = original,
                    codeSwitchLatin = "Caretaker-ge kodi",
                    codeSwitchNative = "ಕೇರ್‌ಟೇಕರ್‌ಗೆ ಕೊಡಿ",
                    wordGloss = listOf(
                        WordGloss("Caretaker-ge", "ಕೇರ್‌ಟೇಕರ್‌ಗೆ", "To caretaker", "Noun"),
                        WordGloss("kodi", "ಕೊಡಿ", "Give (polite)", "Verb")
                    ),
                    literalGloss = "Word breakdown: Caretaker-ge (to caretaker) + kodi (give)",
                    matchType = MatchType.PATTERN_TRANSFORMED,
                    phraseId = "kn_delivery_1"
                )
            }

            query.contains("503") || query.contains("flat") || query.contains("door") -> {
                CodeSwitchResult(
                    englishInput = original,
                    codeSwitchLatin = "Flat munde ideera?",
                    codeSwitchNative = "ಫ್ಲಾಟ್ ಮುಂದೆ ಇದೀರಾ?",
                    wordGloss = listOf(
                        WordGloss("Flat munde", "ಫ್ಲಾಟ್ ಮುಂದೆ", "In front of flat", "Preposition"),
                        WordGloss("ideera", "ಇದೀರಾ", "Are you present?", "Verb")
                    ),
                    literalGloss = "Word breakdown: Flat munde (in front of flat) + ideera (are you there?)",
                    matchType = MatchType.PATTERN_TRANSFORMED,
                    phraseId = "kn_delivery_3"
                )
            }

            query.contains("wait") || query.contains("min") || query.contains("coming") -> {
                CodeSwitchResult(
                    englishInput = original,
                    codeSwitchLatin = "Ondu nimisha wait maadi, naanu barthiddini",
                    codeSwitchNative = "ಒಂದು ನಿಮಿಷ ವೇಟ್ ಮಾಡಿ, ನಾನು ಬರುತ್ತಿದ್ದೀನಿ",
                    wordGloss = listOf(
                        WordGloss("Ondu nimisha", "ಒಂದು ನಿಮಿಷ", "One minute", "Time"),
                        WordGloss("wait maadi", "ವೇಟ್ ಮಾಡಿ", "Please wait", "Verb"),
                        WordGloss("naanu", "ನಾನು", "I / Myself", "Pronoun"),
                        WordGloss("barthiddini", "ಬರುತ್ತಿದ್ದೀನಿ", "Am coming", "Verb")
                    ),
                    literalGloss = "Word breakdown: Ondu nimisha (one min) + wait maadi (please wait) + barthiddini (am coming)",
                    matchType = MatchType.PATTERN_TRANSFORMED,
                    phraseId = "kn_delivery_4"
                )
            }

            query.contains("auto") || query.contains("fare") || query.contains("meter") -> {
                CodeSwitchResult(
                    englishInput = original,
                    codeSwitchLatin = "Auto fare eṣṭu āgutte? Meter hāki!",
                    codeSwitchNative = "Auto fare ಎಷ್ಟು ಆಗುತ್ತೆ? Meter ಹಾಕಿ!",
                    wordGloss = listOf(
                        WordGloss("Auto fare", "Auto fare", "Rickshaw fare", "Noun"),
                        WordGloss("eṣṭu āgutte", "ಎಷ್ಟು ಆಗುತ್ತೆ", "How much will it be?", "Question"),
                        WordGloss("Meter hāki", "Meter ಹಾಕಿ", "Turn on meter", "Verb")
                    ),
                    literalGloss = "Word breakdown: Auto fare eṣṭu āgutte (how much fare) + Meter hāki (put meter)",
                    matchType = MatchType.PATTERN_TRANSFORMED,
                    phraseId = "kn_travel_1"
                )
            }

            query.contains("discount") || query.contains("cheap") || query.contains("expensive") || query.contains("costly") -> {
                CodeSwitchResult(
                    englishInput = original,
                    codeSwitchLatin = "Discount koḍi, full costly ide!",
                    codeSwitchNative = "Discount ಕೊಡಿ, full costly ಇದೆ!",
                    wordGloss = listOf(
                        WordGloss("Discount koḍi", "Discount ಕೊಡಿ", "Give discount", "Verb"),
                        WordGloss("full costly ide", "full costly ಇದೆ", "Is very expensive", "Adjective")
                    ),
                    literalGloss = "Word breakdown: Discount koḍi (give discount) + full costly ide (is very pricey)",
                    matchType = MatchType.PATTERN_TRANSFORMED,
                    phraseId = "kn_shop_1"
                )
            }

            query.contains("okay") || query.contains("got it") || query.contains("done") -> {
                CodeSwitchResult(
                    englishInput = original,
                    codeSwitchLatin = "Aaytu aaytu! (Sari, gottaytu)",
                    codeSwitchNative = "ಆಯ್ತು ಆಯ್ತು! (ಸರಿ, ಗೊತ್ತಾಯಿತು)",
                    wordGloss = listOf(
                        WordGloss("Aaytu", "ಆಯ್ತು", "Done / Okay / Got it", "Interjection"),
                        WordGloss("gottaytu", "ಗೊತ್ತಾಯಿತು", "Understood", "Verb")
                    ),
                    literalGloss = "Word breakdown: Aaytu (okay/done) + gottaytu (understood)",
                    matchType = MatchType.PATTERN_TRANSFORMED,
                    phraseId = "kn_greet_3"
                )
            }

            else -> {
                // Smart generic code-switch transformer: blends English nouns/verbs with polite target suffixes
                val words = original.split(" ")
                val transformedLatin = words.mapIndexed { index, w ->
                    when {
                        w.lowercase() in listOf("give", "send") -> "$w kodi"
                        w.lowercase() in listOf("wait", "pause") -> "$w maadi"
                        w.lowercase() in listOf("meet", "see") -> "$w madonava?"
                        w.lowercase() in listOf("hot", "spicy") -> "full $w ide"
                        w.lowercase() in listOf("late", "delayed") -> "full $w agutte"
                        else -> w
                    }
                }.joinToString(" ")

                CodeSwitchResult(
                    englishInput = original,
                    codeSwitchLatin = transformedLatin,
                    codeSwitchNative = original, // Keep English words with regional blend
                    wordGloss = listOf(
                        WordGloss("English Blend", original, "Natural code-switching blend", "Code-Switch")
                    ),
                    literalGloss = "Natural English + Regional Code-Switching sentence blend",
                    matchType = MatchType.PATTERN_TRANSFORMED
                )
            }
        }
    }
}
