package com.example.vartalapp.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.vartalapp.core.model.DisplayScript
import com.example.vartalapp.core.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(private val context: Context) {

    private object PreferenceKeys {
        val DISPLAY_SCRIPT = stringPreferencesKey("display_script")
        val DAILY_GOAL_PHRASES = intPreferencesKey("daily_goal_phrases")
        val AUDIO_SPEED = floatPreferencesKey("audio_speed")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data.map { prefs ->
        val scriptString = prefs[PreferenceKeys.DISPLAY_SCRIPT] ?: DisplayScript.BOTH.name
        val displayScript = try {
            DisplayScript.valueOf(scriptString)
        } catch (e: Exception) {
            DisplayScript.BOTH
        }

        UserPreferences(
            learningLanguage = "Kannada (Bengaluru)",
            displayScript = displayScript,
            dailyGoalPhrases = prefs[PreferenceKeys.DAILY_GOAL_PHRASES] ?: 5,
            audioSpeed = prefs[PreferenceKeys.AUDIO_SPEED] ?: 1.0f,
            themeMode = prefs[PreferenceKeys.THEME_MODE] ?: "SYSTEM",
            notificationsEnabled = prefs[PreferenceKeys.NOTIFICATIONS_ENABLED] ?: true
        )
    }

    suspend fun updateDisplayScript(script: DisplayScript) {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.DISPLAY_SCRIPT] = script.name
        }
    }

    suspend fun updateDailyGoal(phrasesCount: Int) {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.DAILY_GOAL_PHRASES] = phrasesCount
        }
    }

    suspend fun updateAudioSpeed(speed: Float) {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.AUDIO_SPEED] = speed
        }
    }

    suspend fun updateThemeMode(themeMode: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.THEME_MODE] = themeMode
        }
    }

    suspend fun updateNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.NOTIFICATIONS_ENABLED] = enabled
        }
    }
}
