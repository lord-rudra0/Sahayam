package com.rudra.sahayam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rudra.sahayam.ui.screens.alerts.AlertsScreen
import com.rudra.sahayam.ui.screens.dashboard.SahayamDashboardScreen
import com.rudra.sahayam.ui.screens.map.MapScreen
import com.rudra.sahayam.ui.screens.profile.ProfileScreen
import com.rudra.sahayam.ui.screens.resources.ResourcesScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Routes.HOME, modifier = modifier) {
        composable(Routes.HOME) {
            SahayamDashboardScreen(navController)
        }
        composable(Routes.ALERTS) { AlertsScreen(navController) }
        composable(Routes.MAP) { MapScreen(navController) }
        composable(Routes.RESOURCES) { ResourcesScreen(navController) }
        composable(Routes.PROFILE) { ProfileScreen(navController) }
    }
}
