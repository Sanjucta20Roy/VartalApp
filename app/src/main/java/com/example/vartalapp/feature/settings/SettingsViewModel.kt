package com.example.vartalapp.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vartalapp.core.model.DisplayScript
import com.example.vartalapp.core.model.UserPreferences
import com.example.vartalapp.data.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val preferencesRepository: UserPreferencesRepository) : ViewModel() {

    val userPreferences: StateFlow<UserPreferences> = preferencesRepository.userPreferencesFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserPreferences()
    )

    fun setDisplayScript(script: DisplayScript) {
        viewModelScope.launch {
            preferencesRepository.updateDisplayScript(script)
        }
    }

    fun setDailyGoal(phrases: Int) {
        viewModelScope.launch {
            preferencesRepository.updateDailyGoal(phrases)
        }
    }

    fun setAudioSpeed(speed: Float) {
        viewModelScope.launch {
            preferencesRepository.updateAudioSpeed(speed)
        }
    }

    fun setThemeMode(theme: String) {
        viewModelScope.launch {
            preferencesRepository.updateThemeMode(theme)
        }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.updateNotificationsEnabled(enabled)
        }
    }
}
