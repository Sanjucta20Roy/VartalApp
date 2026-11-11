package com.example.vartalapp.core.model

enum class DisplayScript {
    BOTH,
    NATIVE,
    TRANSLITERATION
}

enum class VerificationStatus {
    NATIVE_VERIFIED,
    REVIEWED,
    DRAFT
}

enum class QuestionType {
    MULTIPLE_CHOICE,
    LISTENING,
    PHRASE_CONSTRUCTION,
    RECALL,
    TRANSLITERATION_MATCH,
    REVISION
}

data class Phrase(
    val id: String,
    val english: String,
    val kannada: String,
    val transliteration: String,
    val meaning: String,
    val explanation: String,
    val category: String,
    val level: Int, // 1 to 5
    val formality: String, // e.g. "Polite", "Casual", "Neutral"
    val alternative: String? = null,
    val verificationStatus: String = "Native-speaker verified"
)

data class Lesson(
    val id: String,
    val title: String,
    val situation: String,
    val objective: String,
    val level: Int,
    val packIndex: Int,
    val phrases: List<Phrase>
)

data class CourseCategory(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val level: Int,
    val phraseCount: Int
)

data class TranslationResult(
    val kannadaScript: String,
    val transliteration: String,
    val englishMeaning: String,
    val context: String,
    val formality: String,
    val alternative: String? = null,
    val confidence: String // "Exact phrase match", "Suggested conversational expression", etc.
)

data class UserPreferences(
    val learningLanguage: String = "Kannada (Bengaluru)",
    val displayScript: DisplayScript = DisplayScript.BOTH,
    val dailyGoalPhrases: Int = 5,
    val audioSpeed: Float = 1.0f,
    val themeMode: String = "SYSTEM", // "SYSTEM", "LIGHT", "DARK"
    val notificationsEnabled: Boolean = true
)

data class PracticeQuestion(
    val id: String,
    val type: QuestionType,
    val prompt: String,
    val phrase: Phrase,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String
)

data class UserStats(
    val totalLessonsCompleted: Int = 0,
    val totalPhrasesLearned: Int = 0,
    val currentStreakDays: Int = 0,
    val longestStreakDays: Int = 0,
    val dailyGoalPhrases: Int = 5,
    val todayPhrasesLearned: Int = 0,
    val quizAccuracyPercentage: Int = 100,
    val totalBookmarks: Int = 0
)
