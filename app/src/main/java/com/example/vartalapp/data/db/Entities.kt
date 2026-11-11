package com.example.vartalapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vartalapp.core.model.Phrase

@Entity(tableName = "phrases")
data class PhraseEntity(
    @PrimaryKey val id: String,
    val english: String,
    val kannada: String,
    val transliteration: String,
    val meaning: String,
    val explanation: String,
    val category: String,
    val level: Int,
    val formality: String,
    val alternative: String?,
    val verificationStatus: String,
    val packId: String
) {
    fun toDomain(): Phrase = Phrase(
        id = id,
        english = english,
        kannada = kannada,
        transliteration = transliteration,
        meaning = meaning,
        explanation = explanation,
        category = category,
        level = level,
        formality = formality,
        alternative = alternative,
        verificationStatus = verificationStatus
    )
}

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val phraseId: String,
    val attempts: Int = 0,
    val correctCount: Int = 0,
    val incorrectCount: Int = 0,
    val lastReviewedTimestamp: Long = 0L,
    val nextReviewTimestamp: Long = 0L,
    val difficultyScore: Float = 1.0f,
    val isLearned: Boolean = false
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val phraseId: String,
    val bookmarkedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "lesson_progress")
data class LessonProgressEntity(
    @PrimaryKey val lessonId: String,
    val isCompleted: Boolean = false,
    val completedAt: Long = 0L,
    val score: Int = 100
)

@Entity(tableName = "daily_activity")
data class DailyActivityEntity(
    @PrimaryKey val dateIso: String, // e.g. "2026-03-30"
    val phrasesLearnedCount: Int = 0,
    val quizCompletedCount: Int = 0
)
