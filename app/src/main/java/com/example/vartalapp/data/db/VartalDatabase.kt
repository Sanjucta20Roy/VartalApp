package com.example.vartalapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        PhraseEntity::class,
        UserProgressEntity::class,
        BookmarkEntity::class,
        LessonProgressEntity::class,
        DailyActivityEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class VartalDatabase : RoomDatabase() {
    abstract fun phraseDao(): PhraseDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun lessonProgressDao(): LessonProgressDao
    abstract fun dailyActivityDao(): DailyActivityDao

    companion object {
        @Volatile
        private var INSTANCE: VartalDatabase? = null

        fun getInstance(context: Context): VartalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VartalDatabase::class.java,
                    "vartal_database"
                )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seed initial phrases in background scope
                        CoroutineScope(Dispatchers.IO).launch {
                            getInstance(context).phraseDao().insertPhrases(SeedData.initialPhrases)
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
