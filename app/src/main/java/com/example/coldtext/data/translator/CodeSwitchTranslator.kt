package com.example.coldtext.data.translator

import com.example.coldtext.data.model.WordGloss
import com.example.coldtext.data.repository.PhraseRepository

enum class MatchType(val label: String, val confidenceLevel: String) {
    VERIFIED_EXACT("🟢 Verified Exact Match", "High"),
    INTENT_TEMPLATE("🔵 Parameterized Template Match", "Medium-High"),
    PATTERN_TRANSFORMED("🟡 Code-Switch Blend", "Medium"),
    UNSUPPORTED("⚪ Unsupported Input", "None")
}

data class CodeSwitchResult(
    val englishInput: String,
    val codeSwitchLatin: String,
    val codeSwitchNative: String,
    val wordGloss: List<WordGloss>,
    val literalGloss: String,
    val matchType: MatchType,
    val phraseId: String? = null,
    val isNegated: Boolean = false,
    val extractedParameters: Map<String, String> = emptyMap()
)

class CodeSwitchTranslator(private val repository: PhraseRepository) {

    private val negationKeywords = setOf("don't", "dont", "not", "never", "do not", "no")

    fun translate(englishInput: String, targetLanguageId: String = "kn"): CodeSwitchResult {
        val trimmed = englishInput.trim()
        if (trimmed.isEmpty()) {
            return CodeSwitchResult(
                englishInput = "",
                codeSwitchLatin = "",
                codeSwitchNative = "",
                wordGloss = emptyList(),
                literalGloss = "Type any English sentence to generate code-switching...",
                matchType = MatchType.UNSUPPORTED,
                phraseId = null
            )
        }

        val q = trimmed.lowercase()
        val words = q.split("\\s+".toRegex())

        // 1. Negation Detection
        val containsNegation = words.any { it in negationKeywords } || q.contains("don't") || q.contains("do not")

        // 2. Dynamic Number / Parameter Extraction
        val numbersInInput = "\\d+".toRegex().findAll(trimmed).map { it.value }.toList()
        val extractedFlatNumber = numbersInInput.firstOrNull { it.length >= 3 }
        val extractedMinutes = numbersInInput.firstOrNull { it.length <= 2 }

        val phrases = repository.getPhrases(targetLanguageId)

        // 3. Exact repository match (ONLY if negations match)
        if (!containsNegation) {
            val cleanQ = q.replace(".", "").replace("!", "").replace("?", "").trim()
            val exactMatch = phrases.firstOrNull { phrase ->
                val meaningLower = phrase.englishMeaning.lowercase().replace(".", "").replace("!", "").replace("?", "").trim()
                val latinLower = phrase.latinTransliteration.lowercase().replace(".", "").replace("!", "").replace("?", "").trim()

                meaningLower == cleanQ ||
                        latinLower == cleanQ ||
                        (cleanQ.length > 5 && meaningLower.contains(cleanQ))
            }

            if (exactMatch != null) {
                return CodeSwitchResult(
                    englishInput = trimmed,
                    codeSwitchLatin = exactMatch.latinTransliteration,
                    codeSwitchNative = exactMatch.nativeScript,
                    wordGloss = exactMatch.wordGloss,
                    literalGloss = exactMatch.literalGloss ?: "Word breakdown: ${exactMatch.pronunciationNotes}",
                    matchType = MatchType.VERIFIED_EXACT,
                    phraseId = exactMatch.id
                )
            }
        }

        // 4. Intent & Template Matching with Parameter Extraction
        return matchIntentAndTemplate(trimmed, q, containsNegation, extractedFlatNumber, extractedMinutes)
    }

    private fun matchIntentAndTemplate(
        original: String,
        query: String,
        isNegated: Boolean,
        flatNumber: String?,
        minutes: String?
    ): CodeSwitchResult {
        return when {
            // Caretaker Delivery (Positive vs Negated)
            query.contains("caretaker") || query.contains("watchman") || query.contains("security") -> {
                if (isNegated) {
                    CodeSwitchResult(
                        englishInput = original,
                        codeSwitchLatin = "Caretaker-ge koḍabēḍi!",
                        codeSwitchNative = "ಕೇರ್‌ಟೇಕರ್‌ಗೆ ಕೊಡಬೇಡಿ!",
                        wordGloss = listOf(
                            WordGloss("Caretaker-ge", "ಕೇರ್‌ಟೇಕರ್‌ಗೆ", "To caretaker", "Noun"),
                            WordGloss("koḍabēḍi", "ಕೊಡಬೇಡಿ", "Do not give (polite negation)", "Verb")
                        ),
                        literalGloss = "Word breakdown: Caretaker-ge (to caretaker) + koḍabēḍi (do NOT give)",
                        matchType = MatchType.INTENT_TEMPLATE,
                        isNegated = true
                    )
                } else {
                    CodeSwitchResult(
                        englishInput = original,
                        codeSwitchLatin = "Caretaker-ge kodi",
                        codeSwitchNative = "ಕೇರ್‌ಟೇಕರ್‌ಗೆ ಕೊಡಿ",
                        wordGloss = listOf(
                            WordGloss("Caretaker-ge", "ಕೇರ್‌ಟೇಕರ್‌ಗೆ", "To caretaker", "Noun"),
                            WordGloss("kodi", "ಕೊಡಿ", "Give (polite)", "Verb")
                        ),
                        literalGloss = "Word breakdown: Caretaker-ge (to caretaker) + kodi (give)",
                        matchType = MatchType.INTENT_TEMPLATE,
                        phraseId = "kn_delivery_1"
                    )
                }
            }

            // Apartment Flat / Door Location with Preserved Dynamic Number
            query.contains("flat") || query.contains("door") || query.contains("building") -> {
                val numStr = flatNumber ?: "503"
                CodeSwitchResult(
                    englishInput = original,
                    codeSwitchLatin = "$numStr flat munde ideera?",
                    codeSwitchNative = "$numStr ಫ್ಲಾಟ್ ಮುಂದೆ ಇದೀರಾ?",
                    wordGloss = listOf(
                        WordGloss("$numStr flat munde", "$numStr ಫ್ಲಾಟ್ ಮುಂದೆ", "In front of flat $numStr", "Preposition"),
                        WordGloss("ideera", "ಇದೀರಾ", "Are you present?", "Verb")
                    ),
                    literalGloss = "Word breakdown: $numStr flat munde (in front of flat $numStr) + ideera (are you there?)",
                    matchType = MatchType.INTENT_TEMPLATE,
                    phraseId = "kn_delivery_4",
                    extractedParameters = mapOf("flat_number" to numStr)
                )
            }

            // Waiting / Time Intent with Preserved Dynamic Minutes
            query.contains("wait") || query.contains("coming") || query.contains("min") -> {
                val minStr = minutes ?: "1"
                CodeSwitchResult(
                    englishInput = original,
                    codeSwitchLatin = "$minStr min wait maadi, naanu barthiddini",
                    codeSwitchNative = "$minStr ನಿಮಿಷ ವೇಟ್ ಮಾಡಿ, ನಾನು ಬರುತ್ತಿದ್ದೀನಿ",
                    wordGloss = listOf(
                        WordGloss("$minStr min", "$minStr ನಿಮಿಷ", "$minStr minute(s)", "Time"),
                        WordGloss("wait maadi", "ವೇಟ್ ಮಾಡಿ", "Please wait", "Verb"),
                        WordGloss("naanu barthiddini", "ನಾನು ಬರುತ್ತಿದ್ದೀನಿ", "I am coming", "Phrase")
                    ),
                    literalGloss = "Word breakdown: $minStr min (minute) + wait maadi (please wait) + barthiddini (am coming)",
                    matchType = MatchType.INTENT_TEMPLATE,
                    phraseId = "kn_delivery_5",
                    extractedParameters = mapOf("minutes" to minStr)
                )
            }

            // Side Giving / Crowd Manners
            query.contains("side") || query.contains("excuse") || query.contains("space") -> {
                CodeSwitchResult(
                    englishInput = original,
                    codeSwitchLatin = "Swalpa side kodi, please",
                    codeSwitchNative = "ಸ್ವಲ್ಪ side ಕೊಡಿ, please",
                    wordGloss = listOf(
                        WordGloss("Swalpa", "ಸ್ವಲ್ಪ", "A little", "Adverb"),
                        WordGloss("side kodi", "side ಕೊಡಿ", "Give side", "Verb")
                    ),
                    literalGloss = "Word breakdown: Swalpa (a little) + side kodi (give side) + please",
                    matchType = MatchType.INTENT_TEMPLATE,
                    phraseId = "kn_greet_5"
                )
            }

            // Directions & Metro
            query.contains("metro") || query.contains("straight") || query.contains("left") || query.contains("direction") -> {
                CodeSwitchResult(
                    englishInput = original,
                    codeSwitchLatin = "Left-nalli straight hogi, metro station ide",
                    codeSwitchNative = "Left ನಲ್ಲಿ straight ಹೋಗಿ, metro station ಇದೆ",
                    wordGloss = listOf(
                        WordGloss("Left-nalli", "Left ನಲ್ಲಿ", "On left", "Preposition"),
                        WordGloss("straight hogi", "straight ಹೋಗಿ", "Go straight", "Verb")
                    ),
                    literalGloss = "Word breakdown: Left-nalli (on left) + straight hogi (go straight) + metro station ide (station is there)",
                    matchType = MatchType.INTENT_TEMPLATE
                )
            }

            // Auto & Fare
            query.contains("auto") || query.contains("fare") || query.contains("meter") -> {
                CodeSwitchResult(
                    englishInput = original,
                    codeSwitchLatin = "Auto fare eṣṭu āgutte? Meter hāki!",
                    codeSwitchNative = "Auto fare ಎಷ್ಟು ಆಗುತ್ತೆ? Meter ಹಾಕಿ!",
                    wordGloss = listOf(
                        WordGloss("Auto fare", "Auto fare", "Rickshaw fare", "Noun"),
                        WordGloss("eṣṭu āgutte", "ಎಷ್ಟು ಆಗುತ್ತೆ", "How much will it be?", "Question")
                    ),
                    literalGloss = "Word breakdown: Auto fare eṣṭu āgutte (how much fare) + Meter hāki (put meter)",
                    matchType = MatchType.INTENT_TEMPLATE,
                    phraseId = "kn_travel_1"
                )
            }

            // Agreement / Acknowledgment
            query.contains("okay") || query.contains("got it") || query.contains("understood") -> {
                CodeSwitchResult(
                    englishInput = original,
                    codeSwitchLatin = "Aaytu aaytu! (Sari, gottaytu)",
                    codeSwitchNative = "ಆಯ್ತು ಆಯ್ತು! (ಸರಿ, ಗೊತ್ತಾಯಿತು)",
                    wordGloss = listOf(
                        WordGloss("Aaytu", "ಆಯ್ತು", "Done / Okay / Got it", "Interjection"),
                        WordGloss("gottaytu", "ಗೊತ್ತಾಯಿತು", "Understood", "Verb")
                    ),
                    literalGloss = "Word breakdown: Aaytu (okay/done) + gottaytu (understood)",
                    matchType = MatchType.INTENT_TEMPLATE,
                    phraseId = "kn_greet_3"
                )
            }

            else -> {
                // If input query is too complex or ambiguous, return an honest unsupported result rather than inventing bad translations
                if (original.split(" ").size > 6) {
                    CodeSwitchResult(
                        englishInput = original,
                        codeSwitchLatin = original,
                        codeSwitchNative = original,
                        wordGloss = emptyList(),
                        literalGloss = "Complex sentence: No high-confidence verified code-switch template available.",
                        matchType = MatchType.UNSUPPORTED
                    )
                } else {
                    val words = original.split(" ")
                    val transformed = words.map { w ->
                        when (w.lowercase()) {
                            "give" -> "kodi"
                            "wait" -> "wait maadi"
                            "late" -> "late agutte"
                            else -> w
                        }
                    }.joinToString(" ")

                    CodeSwitchResult(
                        englishInput = original,
                        codeSwitchLatin = transformed,
                        codeSwitchNative = original,
                        wordGloss = listOf(
                            WordGloss("Code-Switch Blend", transformed, "Natural English + Regional blend", "Blend")
                        ),
                        literalGloss = "Rule-based code-switch blend",
                        matchType = MatchType.PATTERN_TRANSFORMED
                    )
                }
            }
        }
    }
}
