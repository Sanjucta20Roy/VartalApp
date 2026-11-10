package com.example.coldtext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.coldtext.data.audio.TtsHelper
import com.example.coldtext.data.model.Topic
import com.example.coldtext.data.repository.PhraseRepositoryImpl
import com.example.coldtext.ui.navigation.BOTTOM_BAR_SCREENS
import com.example.coldtext.ui.navigation.Screen
import com.example.coldtext.ui.screens.HomeScreen
import com.example.coldtext.ui.screens.PhraseLessonScreen
import com.example.coldtext.ui.screens.PhrasebookScreen
import com.example.coldtext.ui.screens.PracticeScreen
import com.example.coldtext.ui.screens.SettingsScreen
import com.example.coldtext.ui.screens.WelcomeScreen
import com.example.coldtext.ui.theme.VartaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VartaTheme {
                VartaAppMain()
            }
        }
    }
}

@Composable
fun VartaAppMain() {
    val context = LocalContext.current
    val repository = remember { PhraseRepositoryImpl(context) }
    val ttsHelper = remember { TtsHelper(context) }

    DisposableEffect(Unit) {
        onDispose {
            ttsHelper.shutdown()
        }
    }

    val prefs by repository.userPreferencesFlow.collectAsState()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val startDestination = if (prefs.isOnboardingCompleted) Screen.Home.route else Screen.Welcome.route

    val showBottomBar = currentRoute in BOTTOM_BAR_SCREENS.map { it.route }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    BOTTOM_BAR_SCREENS.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                screen.icon?.let { icon ->
                                    Icon(imageVector = icon, contentDescription = screen.title)
                                }
                            },
                            label = { Text(screen.title) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Welcome.route) {
                WelcomeScreen(
                    repository = repository,
                    onStartLearning = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    repository = repository,
                    onContinueLearning = {
                        navController.navigate(Screen.Lesson.createRoute())
                    },
                    onTopicSelected = { topic ->
                        navController.navigate(Screen.Lesson.createRoute(topic = topic.name))
                    }
                )
            }

            composable(
                route = Screen.Lesson.route,
                arguments = listOf(
                    navArgument("topic") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },
                    navArgument("phraseId") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) { backStackEntry ->
                val topicStr = backStackEntry.arguments?.getString("topic")
                val phraseId = backStackEntry.arguments?.getString("phraseId")
                val topic = topicStr?.let {
                    try { Topic.valueOf(it) } catch (e: Exception) { null }
                }

                PhraseLessonScreen(
                    repository = repository,
                    ttsHelper = ttsHelper,
                    initialTopic = topic,
                    initialPhraseId = phraseId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.Practice.route,
                arguments = listOf(
                    navArgument("topic") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) { backStackEntry ->
                val topicStr = backStackEntry.arguments?.getString("topic")
                val topic = topicStr?.let {
                    try { Topic.valueOf(it) } catch (e: Exception) { null }
                }

                PracticeScreen(
                    repository = repository,
                    ttsHelper = ttsHelper,
                    initialTopic = topic
                )
            }

            composable(Screen.Phrasebook.route) {
                PhrasebookScreen(
                    repository = repository,
                    ttsHelper = ttsHelper,
                    onSelectPhrase = { phraseId ->
                        navController.navigate(Screen.Lesson.createRoute(phraseId = phraseId))
                    }
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(repository = repository)
            }
        }
    }
}
