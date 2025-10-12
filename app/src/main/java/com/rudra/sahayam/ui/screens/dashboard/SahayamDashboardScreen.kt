package com.rudra.sahayam.ui.screens.dashboard

import android.Manifest
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rudra.sahayam.ui.components.SahayamBottomBar
import com.rudra.sahayam.ui.components.SahayamTopBar
import com.rudra.sahayam.ui.navigation.Routes
import com.rudra.sahayam.util.ConnectivityObserver

@Composable
fun SahayamDashboardScreen(navController: NavController, viewModel: DashboardViewModel = hiltViewModel()) {
    val alerts = viewModel.alertsState
    val resources = viewModel.resourcesState
    val userAddress = viewModel.userAddress
    val networkStatus = viewModel.networkStatus
    val isBluetoothEnabled = viewModel.isBluetoothEnabled
    val isGuest = viewModel.isGuest

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                viewModel.loadData()
            }
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    LaunchedEffect(networkStatus) {
        if (networkStatus.status == ConnectivityObserver.Status.Unavailable || networkStatus.status == ConnectivityObserver.Status.Lost) {
            val result = snackbarHostState.showSnackbar(
                message = "No Internet Connection",
                actionLabel = "Settings"
            )
            if (result == SnackbarResult.ActionPerformed) {
                context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
            }
        }
    }

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
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Routes.REPORTS) },
                icon = { Icon(Icons.Default.Report, contentDescription = "Report Incident") },
                text = { Text(text = "Report Incident") }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        DashboardContent(
            modifier = Modifier.padding(innerPadding),
            userAddress = userAddress,
            networkStatus = networkStatus,
            isBluetoothEnabled = isBluetoothEnabled,
            isGuest = isGuest,
            alerts = alerts ?: emptyList(),
            resources = resources ?: emptyList(),
            onOpenMap = { navController.navigate(Routes.MAP) },
            onReportClick = { navController.navigate(Routes.REPORTS) },
            onCreateOfflineReport = viewModel::createOfflineReport
        )
    }
}
