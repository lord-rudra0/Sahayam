package com.rudra.sahayam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sahayam.app.ui.screens.onboarding.OnboardingScreen
import com.sahayam.app.ui.screens.dashboard.DashboardScreen

object Routes {
    const val Onboarding = "onboarding"
    const val Dashboard = "dashboard"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Routes.Onboarding) {
        composable(Routes.Onboarding) {
            OnboardingScreen(onContinue = {
                navController.navigate(Routes.Dashboard)
            })
        }
        composable(Routes.Dashboard) {
            DashboardScreen()
        }
    }
}
