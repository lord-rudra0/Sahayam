package com.rudra.sahayam.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rudra.sahayam.ui.navigation.NavGraph
import com.rudra.sahayam.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topBarRoutes = listOf(Routes.HOME, Routes.ALERTS, Routes.MAP, Routes.RESOURCES, Routes.REPORTS, Routes.PROFILE)
    val bottomBarRoutes = listOf(Routes.HOME, Routes.ALERTS, Routes.MAP, Routes.RESOURCES, Routes.REPORTS, Routes.PROFILE)

    Scaffold(
        topBar = {
            if (currentRoute in topBarRoutes) {
                TopAppBar(
                    title = { Text("Sahayam") },
                    actions = {
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Default.FilterList, contentDescription = "Filter")
                        }
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More")
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                BottomAppBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        selected = currentRoute == Routes.HOME,
                        onClick = { navController.navigate(Routes.HOME) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Notifications, contentDescription = "Alerts") },
                        label = { Text("Alerts") },
                        selected = currentRoute == Routes.ALERTS,
                        onClick = { navController.navigate(Routes.ALERTS) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Chat, contentDescription = "Chat") },
                        label = { Text("Chat") },
                        selected = currentRoute == Routes.CHAT,
                        onClick = { navController.navigate(Routes.CHAT) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Place, contentDescription = "Map") },
                        label = { Text("Map") },
                        selected = currentRoute == Routes.MAP,
                        onClick = { navController.navigate(Routes.MAP) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.List, contentDescription = "Resources") },
                        label = { Text("Resources") },
                        selected = currentRoute == Routes.RESOURCES,
                        onClick = { navController.navigate(Routes.RESOURCES) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                        label = { Text("Profile") },
                        selected = currentRoute == Routes.PROFILE,
                        onClick = { navController.navigate(Routes.PROFILE) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
