package com.example.vartalapp.feature.translator

import com.example.vartalapp.core.model.Phrase
import com.example.vartalapp.core.model.TranslationResult
import java.util.Locale
import java.util.regex.Pattern

class TranslatorEngine(private val phraseDatabase: List<Phrase>) {

    fun translate(input: String, situationContext: String? = null): TranslationResult {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) {
            return TranslationResult(
                kannadaScript = "",
                transliteration = "",
                englishMeaning = "",
                context = "Enter an English phrase to translate.",
                formality = "Neutral",
                confidence = "Unsupported expression"
            )
        }

        // 1. Check for Exact Phrase Match in database
        val exactMatch = phraseDatabase.find {
            it.english.equals(trimmed, ignoreCase = true) ||
            it.meaning.equals(trimmed, ignoreCase = true) ||
            cleanText(it.english).equals(cleanText(trimmed), ignoreCase = true)
        }

        if (exactMatch != null) {
            return TranslationResult(
                kannadaScript = exactMatch.kannada,
                transliteration = exactMatch.transliteration,
                englishMeaning = exactMatch.meaning,
                context = exactMatch.explanation,
                formality = exactMatch.formality,
                alternative = exactMatch.alternative,
                confidence = "Exact phrase match"
            )
        }

        // 2. Extract Entities (OTP, flat numbers, times, names)
        val otpMatcher = Pattern.compile("(?i)\\b(OTP|code)\\s*(is)?\\s*(\\d{4,6})\\b").matcher(trimmed)
        if (otpMatcher.find()) {
            val code = otpMatcher.group(3)
            return TranslationResult(
                kannadaScript = "OTP $code ಬಂದಿದೆಯಾ?",
                transliteration = "OTP $code bandidya?",
                englishMeaning = "Did you get OTP $code?",
                context = "Preserved OTP $code for delivery verification.",
                formality = "Polite",
                confidence = "Suggested conversational expression"
            )
        }

        val flatMatcher = Pattern.compile("(?i)\\b(flat|room|apartment|house|door)\\s*#?\\s*(\\d+[A-Z]?)\\b").matcher(trimmed)
        if (flatMatcher.find()) {
            val num = flatMatcher.group(2)
            val lowerInput = trimmed.lowercase(Locale.ROOT)
            val isQuestion = trimmed.endsWith("?") || lowerInput.startsWith("is") || lowerInput.startsWith("where")
            return if (isQuestion) {
                TranslationResult(
                    kannadaScript = "$num ನೇ ಫ್ಲಾಟ್ ಎಲ್ಲಿದೆ?",
                    transliteration = "$num-ne flat elli ide?",
                    englishMeaning = "Where is flat $num?",
                    context = "Preserved flat number $num in location query.",
                    formality = "Polite",
                    confidence = "Suggested conversational expression"
                )
            } else {
                TranslationResult(
                    kannadaScript = "ನಾನು $num ನೇ ಫ್ಲಾಟ್‌ನಲ್ಲಿದ್ದೇನೆ.",
                    transliteration = "Naanu $num-ne flat-nalli iddene.",
                    englishMeaning = "I am at flat $num.",
                    context = "Preserved flat number $num in statement.",
                    formality = "Polite",
                    confidence = "Suggested conversational expression"
                )
            }
        }

        // 3. Rule & Intent Pattern Matching
        val lower = trimmed.lowercase(Locale.ROOT)

        // Distinguish "Wait for me" vs "Don't wait for me" vs "Why are you waiting?"
        if (lower.contains("don't wait") || lower.contains("do not wait")) {
            return TranslationResult(
                kannadaScript = "ನನಗಾಗಿ ಕಾಯಬೇಡಿ.",
                transliteration = "Nannagaagi kaaybedi.",
                englishMeaning = "Don't wait for me.",
                context = "Negative request to not wait.",
                formality = "Polite",
                alternative = "Kaaybedi sir",
                confidence = "Suggested conversational expression"
            )
        } else if (lower.startsWith("why are you waiting") || lower.contains("why waiting")) {
            return TranslationResult(
                kannadaScript = "ಏಕೆ ಕಾಯುತ್ತಿದ್ದೀರಾ?",
                transliteration = "Eke kaayuthiddeera?",
                englishMeaning = "Why are you waiting?",
                context = "Question inquiring about waiting.",
                formality = "Polite",
                confidence = "Suggested conversational expression"
            )
        } else if (lower.contains("please wait") || lower.contains("wait for me") || lower.contains("wait 1 min") || lower.contains("wait one min")) {
            return TranslationResult(
                kannadaScript = "ಒಂದು ನಿಮಿಷ ವೇಟ್ ಮಾಡಿ, ನಾನು ಬರುತ್ತಿದ್ದೀನಿ.",
                transliteration = "Ondu nimisha wait maadi, naanu barthiddini.",
                englishMeaning = "Please wait for one minute, I am coming.",
                context = "Common Bengaluru conversational request.",
                formality = "Polite",
                confidence = "Suggested conversational expression"
            )
        }

        // Questions vs Statements
        if (lower.startsWith("where is") || lower.startsWith("where's")) {
            val destination = trimmed.substringAfter("where is", "").substringAfter("where's", "").trim(' ', '?')
            val translatedDest = translateLocationOrObject(destination)
            return TranslationResult(
                kannadaScript = "$translatedDest ಎಲ್ಲಿದೆ?",
                transliteration = "$translatedDest elli ide?",
                englishMeaning = "Where is $destination?",
                context = "Location inquiry preserving destination name.",
                formality = "Neutral",
                confidence = "Suggested conversational expression"
            )
        }

        if (lower.startsWith("how much") || lower.contains("what is the price") || lower.contains("how much for")) {
            return TranslationResult(
                kannadaScript = "ಇದು ಎಷ್ಟು ಆಯಿತು?",
                transliteration = "Idu eshtu aaythu?",
                englishMeaning = "How much is this?",
                context = "Price & payment query.",
                formality = "Polite",
                confidence = "Suggested conversational expression"
            )
        }

        if (lower.contains("i am coming") || lower.contains("i'm coming")) {
            return TranslationResult(
                kannadaScript = "ನಾನು ಬರುತ್ತಿದ್ದೀನಿ.",
                transliteration = "Naanu barthiddini.",
                englishMeaning = "I am coming.",
                context = "Standard response when on the way.",
                formality = "Neutral",
                confidence = "Suggested conversational expression"
            )
        }

        if (lower.contains("call me") || lower.contains("ring me")) {
            return TranslationResult(
                kannadaScript = "ನನಗೆ ಕಾಲ್ ಮಾಡಿ.",
                transliteration = "Enage call maadi.",
                englishMeaning = "Call me.",
                context = "Phone instruction.",
                formality = "Polite",
                confidence = "Suggested conversational expression"
            )
        }

        // Fuzzy phrase lookup in database
        val partialMatch = phraseDatabase.find {
            lower.contains(cleanText(it.english)) || cleanText(it.english).contains(lower)
        }

        if (partialMatch != null) {
            return TranslationResult(
                kannadaScript = partialMatch.kannada,
                transliteration = partialMatch.transliteration,
                englishMeaning = partialMatch.meaning,
                context = "Matched closest phrase: ${partialMatch.english}",
                formality = partialMatch.formality,
                alternative = partialMatch.alternative,
                confidence = "Suggested conversational expression"
            )
        }

        // Fallback for unsupported sentences
        return TranslationResult(
            kannadaScript = "ಈ ಮಾತು ಇನ್ನೂ ಲಭ್ಯವಿಲ್ಲ.",
            transliteration = "Ee maathu innu labhyavilla.",
            englishMeaning = "This expression is not supported yet.",
            context = "This expression is not supported yet. Try a shorter sentence or choose a situation.",
            formality = "Neutral",
            confidence = "Unsupported expression"
        )
    }

    private fun cleanText(str: String): String {
        return str.lowercase(Locale.ROOT).replace(Regex("[^a-z0-9 ]"), "").trim()
    }

    private fun translateLocationOrObject(english: String): String {
        return when (english.lowercase(Locale.ROOT).trim()) {
            "bus stop" -> "ಬಸ್ ಸ್ಟಾಪ್ (Bus stop)"
            "metro station", "metro" -> "ಮೆಟ್ರೋ ಸ್ಟೇಷನ್ (Metro station)"
            "hospital" -> "ಆಸ್ಪತ್ರೆ (Hospital)"
            "pharmacy", "medical shop" -> "ಫಾರ್ಮಸಿ (Pharmacy)"
            "atm" -> "ATM"
            "flat", "apartment" -> "ಫ್ಲಾಟ್ (Flat)"
            "gate" -> "ಗೇಟ್ (Gate)"
            else -> english.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        }
    }
}
