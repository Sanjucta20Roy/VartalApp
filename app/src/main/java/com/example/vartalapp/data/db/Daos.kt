package com.example.vartalapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PhraseDao {
    @Query("SELECT * FROM phrases")
    fun getAllPhrases(): Flow<List<PhraseEntity>>

    @Query("SELECT * FROM phrases WHERE category = :category")
    fun getPhrasesByCategory(category: String): Flow<List<PhraseEntity>>

    @Query("SELECT * FROM phrases WHERE packId = :packId")
    fun getPhrasesByPack(packId: String): Flow<List<PhraseEntity>>

    @Query("SELECT * FROM phrases WHERE id = :id")
    suspend fun getPhraseById(id: String): PhraseEntity?

    @Query("SELECT * FROM phrases WHERE english LIKE '%' || :query || '%' OR kannada LIKE '%' || :query || '%' OR transliteration LIKE '%' || :query || '%'")
    fun searchPhrases(query: String): Flow<List<PhraseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhrases(phrases: List<PhraseEntity>): List<Long>

    @Query("SELECT COUNT(*) FROM phrases")
    suspend fun getPhraseCount(): Int
}

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE phraseId = :phraseId")
    suspend fun getProgressForPhrase(phraseId: String): UserProgressEntity?

    @Query("SELECT * FROM user_progress")
    fun getAllProgress(): Flow<List<UserProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProgress(progress: UserProgressEntity): Long

    @Query("SELECT COUNT(*) FROM user_progress WHERE isLearned = 1")
    fun getLearnedCount(): Flow<Int>

    @Query("SELECT * FROM user_progress WHERE nextReviewTimestamp <= :now AND attempts > 0 ORDER BY nextReviewTimestamp ASC")
    suspend fun getPhrasesDueForReview(now: Long): List<UserProgressEntity>
}

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE phraseId = :phraseId)")
    fun isBookmarked(phraseId: String): Flow<Boolean>

    @Query("SELECT * FROM bookmarks WHERE phraseId = :phraseId")
    suspend fun getBookmarkByPhraseId(phraseId: String): BookmarkEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: BookmarkEntity): Long

    @Query("DELETE FROM bookmarks WHERE phraseId = :phraseId")
    suspend fun removeBookmark(phraseId: String): Int
}

@Dao
interface LessonProgressDao {
    @Query("SELECT * FROM lesson_progress")
    fun getAllLessonProgress(): Flow<List<LessonProgressEntity>>

    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId")
    suspend fun getLessonProgress(lessonId: String): LessonProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessonProgress(progress: LessonProgressEntity): Long

    @Query("SELECT COUNT(*) FROM lesson_progress WHERE isCompleted = 1")
    fun getCompletedLessonCount(): Flow<Int>
}

@Dao
interface DailyActivityDao {
    @Query("SELECT * FROM daily_activity ORDER BY dateIso DESC")
    fun getAllActivities(): Flow<List<DailyActivityEntity>>

    @Query("SELECT * FROM daily_activity WHERE dateIso = :dateIso")
    suspend fun getActivityForDate(dateIso: String): DailyActivityEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateActivity(activity: DailyActivityEntity): Long
}
