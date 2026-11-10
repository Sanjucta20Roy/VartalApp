package com.example.coldtext.data.model

data class UserPreferences(
    val displayLanguage: String = "English",
    val targetLanguageId: String = "kn",
    val userLevel: String = "Beginner",
    val dailyGoalPhrases: Int = 5,
    val streakDays: Int = 1,
    val completedPhraseIds: Set<String> = emptySet(),
    val savedPhraseIds: Set<String> = emptySet(),
    val audioSpeed: Float = 1.0f,
    val textSizeScale: Float = 1.0f,
    val isOnboardingCompleted: Boolean = false
)
