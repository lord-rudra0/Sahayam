package com.rudra.sahayam.ui.screens.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SahayamDashboardScreen(viewModel: DashboardViewModel = hiltViewModel()) {

    val userAddress by viewModel.userAddress
    val isGuest by viewModel.isGuest
    val networkStatus by viewModel.networkStatus.collectAsState()
    val isBluetoothEnabled by viewModel.isBluetoothEnabled

    DashboardContent(
        userAddress = userAddress,
        networkStatus = networkStatus,
        isBluetoothEnabled = isBluetoothEnabled,
        isGuest = isGuest,
        alerts = viewModel.alerts,
        resources = viewModel.resources,
        meshrabiyaState = viewModel.meshrabiyaState,
        onOpenMap = { /*TODO*/ },
        onReportClick = { /*TODO*/ },
        onCreateOfflineReport = { /*TODO*/ },
        onCreateHotspot = viewModel::onCreateHotspot,
        onConnectToHotspot = viewModel::onConnectToHotspot,
        onStartBleDiscovery = viewModel::onStartBleDiscovery,
    )
}
