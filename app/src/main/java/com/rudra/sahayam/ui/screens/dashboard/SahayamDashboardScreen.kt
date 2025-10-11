package com.rudra.sahayam.ui.screens.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rudra.sahayam.ui.components.SahayamBottomBar
import com.rudra.sahayam.ui.components.SahayamTopBar
import com.rudra.sahayam.ui.navigation.Routes

@Composable
fun SahayamDashboardScreen(navController: NavController, viewModel: DashboardViewModel = hiltViewModel()) {
    val alerts = viewModel.alertsState
    val resources = viewModel.resourcesState
    Scaffold(
        topBar = { SahayamTopBar(title = "Sahayam Dashboard",
            onNotificationClick = { navController.navigate(Routes.ALERTS) },
            onProfileClick = { navController.navigate(Routes.PROFILE) }
        ) },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            SahayamBottomBar(navController = navController, currentRoute = currentRoute)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* navController.navigate("reports") */ }) {
                Icon(Icons.Default.Report, contentDescription = "Report")
            }
        }
    ) { innerPadding ->
        DashboardContent(
            modifier = Modifier.padding(innerPadding).padding(12.dp),
            alerts = alerts,
            resources = resources,
            onOpenMap = { navController.navigate(Routes.MAP) },
            onReportClick = { /* navController.navigate("reports") */ }
        )
    }
}
