package com.example.vartalapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vartalapp.core.audio.KannadaAudioPlayer
import com.example.vartalapp.data.datastore.UserPreferencesRepository
import com.example.vartalapp.data.db.VartalDatabase
import com.example.vartalapp.data.repository.PhraseRepository
import com.example.vartalapp.feature.home.HomeScreen
import com.example.vartalapp.feature.home.HomeViewModel
import com.example.vartalapp.feature.learning.LearnScreen
import com.example.vartalapp.feature.learning.LearnViewModel
import com.example.vartalapp.feature.learning.LessonDetailScreen
import com.example.vartalapp.feature.phrasebook.PhrasebookScreen
import com.example.vartalapp.feature.phrasebook.PhrasebookViewModel
import com.example.vartalapp.feature.practice.PracticeScreen
import com.example.vartalapp.feature.practice.PracticeViewModel
import com.example.vartalapp.feature.settings.SettingsScreen
import com.example.vartalapp.feature.settings.SettingsViewModel
import com.example.vartalapp.feature.translator.TranslateScreen
import com.example.vartalapp.feature.translator.TranslatorViewModel
import com.example.vartalapp.ui.navigation.Screen
import com.example.vartalapp.ui.navigation.bottomNavItems

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val database = remember { VartalDatabase.getInstance(context) }
    val phraseRepository = remember { PhraseRepository(database) }
    val preferencesRepository = remember { UserPreferencesRepository(context) }
    val audioPlayer = remember { KannadaAudioPlayer(context) }

    DisposableEffect(Unit) {
        onDispose {
            audioPlayer.shutdown()
        }
    }

    LaunchedEffect(Unit) {
        phraseRepository.ensureDataSeeded()
    }

    val preferences by preferencesRepository.userPreferencesFlow.collectAsState(
        initial = com.example.vartalapp.core.model.UserPreferences()
    )

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // ViewModels
    val homeViewModel: HomeViewModel = viewModel { HomeViewModel(phraseRepository) }
    val learnViewModel: LearnViewModel = viewModel { LearnViewModel(phraseRepository) }
    val translatorViewModel: TranslatorViewModel = viewModel { TranslatorViewModel(phraseRepository) }
    val practiceViewModel: PracticeViewModel = viewModel { PracticeViewModel(phraseRepository) }
    val phrasebookViewModel: PhrasebookViewModel = viewModel { PhrasebookViewModel(phraseRepository) }
    val settingsViewModel: SettingsViewModel = viewModel { SettingsViewModel(preferencesRepository) }

    val homeUiState by homeViewModel.uiState.collectAsState()
    val learnUiState by learnViewModel.uiState.collectAsState()
    val translatorUiState by translatorViewModel.uiState.collectAsState()
    val practiceUiState by practiceViewModel.uiState.collectAsState()
    val phrasebookUiState by phrasebookViewModel.uiState.collectAsState()

    val showBottomBar = bottomNavItems.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title) },
                            selected = selected,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    uiState = homeUiState,
                    displayScript = preferences.displayScript,
                    onNavigateToLearn = { navController.navigate(Screen.Learn.route) },
                    onContinueLesson = { lessonId ->
                        navController.navigate(Screen.LessonDetail.createRoute(lessonId))
                    },
                    onNavigateToTranslate = { navController.navigate(Screen.Translate.route) },
                    onNavigateToPractice = { navController.navigate(Screen.Practice.route) },
                    onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                    onPlayAudio = { audioPlayer.speak(it, preferences.audioSpeed) }
                )
            }

            composable(Screen.Learn.route) {
                LearnScreen(
                    uiState = learnUiState,
                    onLevelSelected = { learnViewModel.selectLevel(it) },
                    onLessonClick = { lessonId ->
                        navController.navigate(Screen.LessonDetail.createRoute(lessonId))
                    }
                )
            }

            composable(Screen.LessonDetail.route) { backStackEntry ->
                val lessonId = backStackEntry.arguments?.getString("lessonId")
                val lesson = learnUiState.lessons.find { it.id == lessonId }
                LessonDetailScreen(
                    lesson = lesson,
                    displayScript = preferences.displayScript,
                    onBackClick = { navController.popBackStack() },
                    onPlayAudio = { audioPlayer.speak(it, preferences.audioSpeed) },
                    onLessonCompleted = { completedId ->
                        learnViewModel.completeLesson(completedId)
                    }
                )
            }

            composable(Screen.Translate.route) {
                TranslateScreen(
                    uiState = translatorUiState,
                    displayScript = preferences.displayScript,
                    onInputChanged = { translatorViewModel.onInputChanged(it) },
                    onClearInput = { translatorViewModel.clearInput() },
                    onSelectSituation = { translatorViewModel.selectSituation(it) },
                    onPlayAudio = { audioPlayer.speak(it, preferences.audioSpeed) }
                )
            }

            composable(Screen.Practice.route) {
                PracticeScreen(
                    uiState = practiceUiState,
                    displayScript = preferences.displayScript,
                    onOptionSelected = { practiceViewModel.submitAnswer(it) },
                    onNextQuestion = { practiceViewModel.nextQuestion() },
                    onRestartQuiz = { practiceViewModel.startNewQuiz() },
                    onPlayAudio = { audioPlayer.speak(it, preferences.audioSpeed) }
                )
            }

            composable(Screen.Phrasebook.route) {
                PhrasebookScreen(
                    uiState = phrasebookUiState,
                    displayScript = preferences.displayScript,
                    onQueryChanged = { phrasebookViewModel.onSearchQueryChanged(it) },
                    onCategorySelected = { phrasebookViewModel.selectCategory(it) },
                    onToggleFavoritesOnly = { phrasebookViewModel.toggleFavoritesOnly() },
                    onToggleBareMinimumOnly = { phrasebookViewModel.toggleBareMinimumOnly() },
                    onToggleBookmark = { phrasebookViewModel.toggleBookmark(it) },
                    onOpenDetail = { phrasebookViewModel.openPhraseDetail(it) },
                    onPlayAudio = { audioPlayer.speak(it, preferences.audioSpeed) }
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(
                    userPreferences = preferences,
                    onBackClick = { navController.popBackStack() },
                    onDisplayScriptChanged = { settingsViewModel.setDisplayScript(it) },
                    onDailyGoalChanged = { settingsViewModel.setDailyGoal(it) },
                    onAudioSpeedChanged = { settingsViewModel.setAudioSpeed(it) },
                    onThemeChanged = { settingsViewModel.setThemeMode(it) },
                    onNotificationsToggled = { settingsViewModel.setNotificationsEnabled(it) }
                )
            }
        }
    }
}
