package com.rudra.sahayam.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.rudra.sahayam.ui.navigation.Routes

data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)

val BottomItems = listOf(
    BottomNavItem(Routes.HOME, "Home", Icons.Default.Home),
    BottomNavItem(Routes.ALERTS, "Alerts", Icons.Default.Warning),
    BottomNavItem(Routes.MAP, "Map", Icons.Default.Map),
    BottomNavItem(Routes.RESOURCES, "Resources", Icons.Default.LocalHospital),
    BottomNavItem(Routes.PROFILE, "Profile", Icons.Default.Person)
)

@Composable
fun SahayamBottomBar(navController: NavController, currentRoute: String?) {
    NavigationBar {
        BottomItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) navController.navigate(item.route) {
                        // pop up to start destination to avoid building up stack
                        popUpTo(Routes.HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
