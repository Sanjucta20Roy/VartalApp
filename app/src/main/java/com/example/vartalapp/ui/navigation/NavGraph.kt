package com.example.vartalapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Translate
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Learn : Screen("learn", "Learn", Icons.Default.School)
    object Translate : Screen("translate", "Translate", Icons.Default.Translate)
    object Practice : Screen("practice", "Practice", Icons.Default.Quiz)
    object Phrasebook : Screen("phrasebook", "Phrasebook", Icons.Default.Book)

    object LessonDetail : Screen("lesson_detail/{lessonId}", "Lesson", Icons.Default.School) {
        fun createRoute(lessonId: String) = "lesson_detail/$lessonId"
    }
    object Settings : Screen("settings", "Settings", Icons.Default.Home)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Learn,
    Screen.Translate,
    Screen.Practice,
    Screen.Phrasebook
)
