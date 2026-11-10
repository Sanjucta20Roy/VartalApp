package com.example.coldtext.data.model

data class UserPreferences(
    val displayLanguage: String = "English",
    val targetLanguageId: String = "kn",
    val userLevel: String = "Beginner",
    val learningGoal: String = "Casual Hangouts",
    val scriptPreference: String = "Both Native & Latin",
    val dailyGoalPhrases: Int = 5,
    val streakDays: Int = 1,
    val completedPhraseIds: Set<String> = emptySet(),
    val savedPhraseIds: Set<String> = emptySet(),
    val weakPhraseIds: Set<String> = emptySet(),
    val weakPhraseScores: Map<String, Int> = emptyMap(),
    val offlineFeedbackReports: List<String> = emptyList(),
    val audioSpeed: Float = 1.0f,
    val textSizeScale: Float = 1.0f,
    val isOnboardingCompleted: Boolean = false
)
