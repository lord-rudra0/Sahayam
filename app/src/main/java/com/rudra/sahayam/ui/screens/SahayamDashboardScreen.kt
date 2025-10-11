package com.rudra.sahayam.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rudra.sahayam.ui.navigation.BottomNavigationBar
import com.rudra.sahayam.ui.navigation.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SahayamDashboardScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentRoute?.replaceFirstChar { it.uppercase() } ?: "Sahayam") },
                actions = {
                    IconButton(onClick = { navController.navigate("alerts") }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Alerts")
                    }
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) { HomeScreen() }
            composable(BottomNavItem.Chat.route) { ChatScreen() }
            composable(BottomNavItem.Map.route) { MapScreen() }
            composable(BottomNavItem.Resources.route) { ResourceScreen() }
            // These are now handled by the top app bar, but we still need destinations
            composable("alerts") { AlertsScreen() }
            composable("profile") { ProfileScreen() }
        }
    }
}
