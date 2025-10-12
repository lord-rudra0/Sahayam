package com.rudra.sahayam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rudra.sahayam.ui.screens.alerts.AlertsScreen
import com.rudra.sahayam.ui.screens.auth.LoginScreen
import com.rudra.sahayam.ui.screens.auth.SignUpScreen
import com.rudra.sahayam.ui.screens.auth.SplashScreen
import com.rudra.sahayam.ui.screens.auth.WelcomeScreen
import com.rudra.sahayam.ui.screens.dashboard.SahayamDashboardScreen
import com.rudra.sahayam.ui.screens.map.MapScreen
import com.rudra.sahayam.ui.screens.profile.ProfileScreen
import com.rudra.sahayam.ui.screens.reports.ReportsScreen
import com.rudra.sahayam.ui.screens.resources.ResourcesScreen
import com.rudra.sahayam.ui.screens.settings.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Routes.SPLASH, modifier = modifier) {
        // Auth Flow
        composable(Routes.SPLASH) { SplashScreen(navController) }
        composable(Routes.WELCOME) { WelcomeScreen(navController) }
        composable(Routes.LOGIN) { LoginScreen(navController) }
        composable(Routes.SIGNUP) { SignUpScreen(navController) }

        // Main App Flow
        composable(Routes.HOME) {
            SahayamDashboardScreen()
        }
        composable(Routes.ALERTS) { AlertsScreen(navController) }
        composable(Routes.MAP) { MapScreen(navController) }
        composable(Routes.RESOURCES) { ResourcesScreen(navController) }
        composable(Routes.PROFILE) { ProfileScreen(navController) }
        composable(Routes.REPORTS) { ReportsScreen(navController) }
        
        // Settings
        composable(Routes.SETTINGS) { SettingsScreen(navController) }
    }
}
