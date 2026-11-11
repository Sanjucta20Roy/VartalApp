package com.example.vartalapp.data.repository

import com.example.vartalapp.core.model.CourseCategory
import com.example.vartalapp.core.model.Lesson
import com.example.vartalapp.core.model.Phrase
import com.example.vartalapp.core.model.UserStats
import com.example.vartalapp.data.db.BookmarkEntity
import com.example.vartalapp.data.db.DailyActivityEntity
import com.example.vartalapp.data.db.LessonProgressEntity
import com.example.vartalapp.data.db.SeedData
import com.example.vartalapp.data.db.UserProgressEntity
import com.example.vartalapp.data.db.VartalDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class PhraseRepository(private val database: VartalDatabase) {

    private val phraseDao = database.phraseDao()
    private val userProgressDao = database.userProgressDao()
    private val bookmarkDao = database.bookmarkDao()
    private val lessonProgressDao = database.lessonProgressDao()
    private val dailyActivityDao = database.dailyActivityDao()

    suspend fun ensureDataSeeded() {
        if (phraseDao.getPhraseCount() == 0) {
            phraseDao.insertPhrases(SeedData.initialPhrases)
        }
    }

    val allPhrases: Flow<List<Phrase>> = phraseDao.getAllPhrases().map { entities ->
        entities.map { it.toDomain() }
    }

    fun getPhrasesByCategory(category: String): Flow<List<Phrase>> {
        return phraseDao.getPhrasesByCategory(category).map { list -> list.map { it.toDomain() } }
    }

    fun searchPhrases(query: String): Flow<List<Phrase>> {
        if (query.isBlank()) return allPhrases
        return phraseDao.searchPhrases(query).map { list -> list.map { it.toDomain() } }
    }

    suspend fun getPhraseById(id: String): Phrase? {
        return phraseDao.getPhraseById(id)?.toDomain()
    }

    val bookmarkedPhraseIds: Flow<Set<String>> = bookmarkDao.getAllBookmarks().map { bookmarks ->
        bookmarks.map { it.phraseId }.toSet()
    }

    fun isBookmarked(phraseId: String): Flow<Boolean> = bookmarkDao.isBookmarked(phraseId)

    suspend fun toggleBookmark(phraseId: String) {
        val existing = bookmarkDao.getBookmarkByPhraseId(phraseId)
        if (existing != null) {
            bookmarkDao.removeBookmark(phraseId)
        } else {
            bookmarkDao.addBookmark(BookmarkEntity(phraseId = phraseId))
        }
    }

    suspend fun setBookmark(phraseId: String, bookmarked: Boolean) {
        if (bookmarked) {
            bookmarkDao.addBookmark(BookmarkEntity(phraseId = phraseId))
        } else {
            bookmarkDao.removeBookmark(phraseId)
        }
    }

    val courseCategories: Flow<List<CourseCategory>> = flow {
        val packs = SeedData.packs.map { map ->
            val packId = map["id"]!!
            val title = map["title"]!!
            val situation = map["situation"]!!
            val level = map["level"]!!.toInt()
            val phrasesInPack = SeedData.initialPhrases.filter { it.packId == packId }
            CourseCategory(
                id = packId,
                title = title,
                description = situation,
                iconName = getIconForCategory(title),
                level = level,
                phraseCount = phrasesInPack.size
            )
        }
        emit(packs)
    }

    fun getLessonsForLevel(level: Int): Flow<List<Lesson>> = flow {
        val levelPacks = SeedData.packs.filter { it["level"]!!.toInt() == level }
        val lessons = levelPacks.mapIndexed { index, map ->
            val packId = map["id"]!!
            val phrases = SeedData.initialPhrases.filter { it.packId == packId }.map { it.toDomain() }
            Lesson(
                id = packId,
                title = map["title"]!!,
                situation = map["situation"]!!,
                objective = "Master key natural expressions for ${map["title"]}",
                level = level,
                packIndex = index + 1,
                phrases = phrases
            )
        }
        emit(lessons)
    }

    val completedLessonIds: Flow<Set<String>> = lessonProgressDao.getAllLessonProgress().map { progressList ->
        progressList.filter { it.isCompleted }.map { it.lessonId }.toSet()
    }

    suspend fun markLessonCompleted(lessonId: String, score: Int = 100) {
        lessonProgressDao.insertLessonProgress(
            LessonProgressEntity(
                lessonId = lessonId,
                isCompleted = true,
                completedAt = System.currentTimeMillis(),
                score = score
            )
        )
        val todayIso = LocalDate.now().toString()
        val activity = dailyActivityDao.getActivityForDate(todayIso) ?: DailyActivityEntity(todayIso)
        dailyActivityDao.insertOrUpdateActivity(
            activity.copy(
                phrasesLearnedCount = activity.phrasesLearnedCount + 5,
                quizCompletedCount = activity.quizCompletedCount + 1
            )
        )
    }

    suspend fun recordPhrasePractice(phraseId: String, isCorrect: Boolean) {
        val existing = userProgressDao.getProgressForPhrase(phraseId) ?: UserProgressEntity(phraseId = phraseId)
        val now = System.currentTimeMillis()
        val nextReview = if (isCorrect) now + 86400000L * 3 else now + 86400000L
        val updated = existing.copy(
            attempts = existing.attempts + 1,
            correctCount = if (isCorrect) existing.correctCount + 1 else existing.correctCount,
            incorrectCount = if (!isCorrect) existing.incorrectCount + 1 else existing.incorrectCount,
            lastReviewedTimestamp = now,
            nextReviewTimestamp = nextReview,
            isLearned = existing.isLearned || isCorrect
        )
        userProgressDao.insertOrUpdateProgress(updated)
    }

    val userStats: Flow<UserStats> = combine(
        lessonProgressDao.getCompletedLessonCount(),
        userProgressDao.getLearnedCount(),
        bookmarkDao.getAllBookmarks(),
        dailyActivityDao.getAllActivities()
    ) { completedLessons, learnedPhrases, bookmarks, activities ->
        val todayIso = LocalDate.now().toString()
        val todayActivity = activities.find { it.dateIso == todayIso }

        var streakDays = 0
        var checkDate = LocalDate.now()
        val activityDateSet = activities.map { it.dateIso }.toSet()
        while (activityDateSet.contains(checkDate.toString())) {
            streakDays++
            checkDate = checkDate.minusDays(1)
        }

        UserStats(
            totalLessonsCompleted = completedLessons,
            totalPhrasesLearned = learnedPhrases,
            currentStreakDays = streakDays,
            longestStreakDays = maxOf(streakDays, 1),
            dailyGoalPhrases = 5,
            todayPhrasesLearned = todayActivity?.phrasesLearnedCount ?: 0,
            totalBookmarks = bookmarks.size
        )
    }

    private fun getIconForCategory(title: String): String = when {
        title.contains("Greeting") -> "waving_hand"
        title.contains("Money") || title.contains("Numbers") -> "payments"
        title.contains("Transport") || title.contains("Auto") -> "directions_car"
        title.contains("Apartment") || title.contains("Security") -> "apartment"
        title.contains("Delivery") -> "local_shipping"
        title.contains("Housekeeper") -> "cleaning_services"
        title.contains("Shopping") -> "shopping_cart"
        title.contains("Food") || title.contains("Restaurant") -> "restaurant"
        title.contains("Office") || title.contains("Work") -> "work"
        title.contains("Direction") -> "explore"
        title.contains("Health") || title.contains("Pharmacy") -> "local_pharmacy"
        title.contains("Talk") -> "forum"
        title.contains("Phone") -> "phone"
        title.contains("Emergency") -> "warning"
        title.contains("Bengaluru") -> "location_city"
        else -> "schedule"
    }
}
