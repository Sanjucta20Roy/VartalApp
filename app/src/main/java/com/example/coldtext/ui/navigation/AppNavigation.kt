package com.example.coldtext.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Welcome : Screen("welcome", "Welcome")
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Lesson : Screen("lesson?topic={topic}&phraseId={phraseId}", "Lesson") {
        fun createRoute(topic: String? = null, phraseId: String? = null): String {
            return when {
                phraseId != null -> "lesson?phraseId=$phraseId"
                topic != null -> "lesson?topic=$topic"
                else -> "lesson"
            }
        }
    }
    object Practice : Screen("practice?topic={topic}", "Practice", Icons.Default.Quiz) {
        fun createRoute(topic: String? = null): String {
            return if (topic != null) "practice?topic=$topic" else "practice"
        }
    }
    object Phrasebook : Screen("phrasebook", "Phrasebook", Icons.Default.Book)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
}

val BOTTOM_BAR_SCREENS = listOf(
    Screen.Home,
    Screen.Practice,
    Screen.Phrasebook,
    Screen.Settings
)
