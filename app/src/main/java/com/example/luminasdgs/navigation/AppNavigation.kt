package com.example.luminasdgs.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.luminasdgs.ui.components.BottomNavigationBar
import com.example.luminasdgs.ui.screens.game.GameScreen
import com.example.luminasdgs.ui.screens.game.matchcard.MatchCardScreen
import com.example.luminasdgs.ui.screens.game.mythfacts.MythFactScreen
import com.example.luminasdgs.ui.screens.game.quiz.QuizScreen
import com.example.luminasdgs.ui.screens.game.river.CleanRiverScreen
import com.example.luminasdgs.ui.screens.game.trashsort.TrashSortScreen
import com.example.luminasdgs.ui.screens.onboarding.OnboardingImpactScreen
import com.example.luminasdgs.ui.screens.onboarding.OnboardingLearnScreen
import com.example.luminasdgs.ui.screens.onboarding.OnboardingRewardsScreen
import com.example.luminasdgs.ui.screens.home.HomeScreen
import com.example.luminasdgs.ui.screens.profile.ProfileScreen
import com.example.luminasdgs.ui.screens.profile.achievement.AchievementHubScreen
import com.example.luminasdgs.ui.screens.profile.achievement.MatchCardAchievementDetailScreen
import com.example.luminasdgs.ui.screens.profile.achievement.MatchCardAchievementScreen
import com.example.luminasdgs.ui.screens.profile.achievement.MythFactAchievementScreen
import com.example.luminasdgs.ui.screens.profile.achievement.QuizAchievementScreen
import com.example.luminasdgs.ui.screens.profile.achievement.TrashSortAchievementScreen
import com.example.luminasdgs.ui.screens.actions.ActionsScreen
import com.example.luminasdgs.ui.screens.splash.SplashScreen
import com.example.luminasdgs.ui.screens.tree.TreeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomRoutes = setOf(
        Screen.Home.route,
        Screen.Actions.route,
        Screen.Game.route,
        Screen.Tree.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomRoutes) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) { SplashScreen(navController = navController) }
            composable(Screen.OnboardingLearn.route) { OnboardingLearnScreen(navController = navController) }
            composable(Screen.OnboardingRewards.route) { OnboardingRewardsScreen(navController = navController) }
            composable(Screen.OnboardingImpact.route) { OnboardingImpactScreen(navController = navController) }
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Actions.route) { ActionsScreen() }
            composable(Screen.Game.route) { GameScreen(navController = navController) }
            composable(Screen.Tree.route) { TreeScreen() }
            composable(Screen.Profile.route) { ProfileScreen(navController = navController) }
            composable(Screen.Quiz.route) { QuizScreen(navController = navController) }
            composable(Screen.TrashSort.route) { TrashSortScreen(navController = navController) }
            composable(Screen.MatchCard.route) { MatchCardScreen(navController = navController) }
            composable(Screen.CleanRiver.route) { CleanRiverScreen(navController = navController) }
            composable(Screen.MythFact.route) { MythFactScreen(navController = navController) }
            composable(Screen.AchievementHub.route) { AchievementHubScreen(navController = navController) }
            composable(Screen.AchievementQuiz.route) { QuizAchievementScreen(navController = navController) }
            composable(Screen.AchievementMatchCard.route) { MatchCardAchievementScreen(navController = navController) }
            composable(Screen.AchievementMythFact.route) { MythFactAchievementScreen(navController = navController) }
            composable(Screen.AchievementTrashSort.route) { TrashSortAchievementScreen(navController = navController) }
            composable(
                route = Screen.AchievementMatchCardDetail.route,
                arguments = listOf(navArgument("sdgId") { type = NavType.IntType })
            ) { backStackEntry ->
                val sdgId = backStackEntry.arguments?.getInt("sdgId") ?: 1
                MatchCardAchievementDetailScreen(navController = navController, sdgId = sdgId)
            }
        }
    }
}
