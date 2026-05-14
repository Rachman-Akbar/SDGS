package com.example.luminasdgs.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object OnboardingLearn : Screen("onboarding_learn")
    data object OnboardingRewards : Screen("onboarding_rewards")
    data object OnboardingImpact : Screen("onboarding_impact")
    data object Home : Screen("home")
    data object Actions : Screen("actions")
    data object Game : Screen("game")
    data object Tree : Screen("tree")
    data object Rewards : Screen("rewards")
    data object Profile : Screen("profile")
    data object Quiz : Screen("quiz")
    data object TrashSort : Screen("trash_sort")
    data object MatchCard : Screen("match_card")
    data object CleanRiver : Screen("clean_river")
    data object MythFact : Screen("myth_fact")
    data object AchievementHub : Screen("achievement_hub")
    data object AchievementQuiz : Screen("achievement_quiz")
    data object AchievementMatchCard : Screen("achievement_match_card")
    data object AchievementMatchCardDetail : Screen("achievement_match_card/{sdgId}") {
        fun createRoute(sdgId: Int): String = "achievement_match_card/$sdgId"
    }
    data object AchievementMythFact : Screen("achievement_myth_fact")
    data object AchievementTrashSort : Screen("achievement_trash_sort")
}
