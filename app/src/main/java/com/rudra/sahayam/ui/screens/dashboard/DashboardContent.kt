package com.rudra.sahayam.ui.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rudra.sahayam.domain.model.AlertItem
import com.rudra.sahayam.domain.model.ResourceItem
import com.rudra.sahayam.ui.components.SystemStatusRow
import com.rudra.sahayam.util.ConnectivityObserver
import com.rudra.sahayam.util.generateQrCode
import com.ustadmobile.meshrabiya.vnet.LocalNodeState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    userAddress: String,
    networkStatus: ConnectivityObserver.NetworkStatus,
    isBluetoothEnabled: Boolean,
    isGuest: Boolean,
    alerts: List<AlertItem>,
    resources: List<ResourceItem>,
    meshrabiyaState: StateFlow<LocalNodeState>,
    onOpenMap: () -> Unit = {},
    onReportClick: () -> Unit = {},
    onCreateOfflineReport: () -> Unit = {},
    onCreateHotspot: () -> Unit,
    onConnectToHotspot: () -> Unit,
    onStartBleDiscovery: () -> Unit,
) {
    val nodeState by meshrabiyaState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = userAddress,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1
                    )
                }
                SystemStatusRow(networkStatus, isBluetoothEnabled)
            }
        }

        if (!isGuest) {
            item { ProfileHeader() }
        }

        item { WeatherOverview() }

        item { 
            Text(
                text = "Live Alerts", 
                style = MaterialTheme.typography.headlineSmall, 
                fontWeight = FontWeight.Bold
            )
        }
        items(alerts, key = { it.id }) {
            AlertCard(it)
        }

        item { 
            Text(
                text = "Nearby Shelters", 
                style = MaterialTheme.typography.headlineSmall, 
                fontWeight = FontWeight.Bold
            )
        }
        items(resources, key = { it.id }) {
            ResourceCard(it)
        }
        
        item { MiniMap(onOpenMap) }

        if (!isGuest) {
            item { MissionsList() }
            item { ResourcesSummary() }
            item { QuickActions() }
            item { 
                SyncStatusInfo(
                    nodeState = nodeState,
                    onCreateHotspot = onCreateHotspot,
                    onConnectToHotspot = onConnectToHotspot,
                    onStartBleDiscovery = onStartBleDiscovery
                ) 
            }
        }
    }
}

@Composable
fun ProfileHeader() {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile", modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Welcome Back, Rudra!", style = MaterialTheme.typography.headlineSmall)
                Text("user | Pune, MH", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun WeatherOverview() {
    SectionCard(title = "Weather Overview") { 
        Text("Weather data will be shown here.")
    }
}

@Composable
fun MiniMap(onOpenMap: () -> Unit) {
    SectionCard(title = "Mini Map") { 
        Text("A mini-map view will be shown here.")
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun MissionsList() {
    SectionCard(title = "Your Missions") { 
        Text("A list of assigned missions will be shown here.")
    }
}

@Composable
fun ResourcesSummary() {
    SectionCard(title = "Resources Summary") { 
        Text("Summary of available resources will be displayed here.")
    }
}

@Composable
fun QuickActions() {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Share Location") },
            text = { Text("Choose the scope to share your location with.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Entire Network")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Nearby Only")
                }
            }
        )
    }

    SectionCard(title = "Quick Actions") {
        Button(onClick = { showDialog = true }) {
            Text("Share Location")
        }
    }
}

@Composable
fun SyncStatusInfo(
    nodeState: LocalNodeState,
    onCreateHotspot: () -> Unit,
    onConnectToHotspot: () -> Unit,
    onStartBleDiscovery: () -> Unit
) {
    var isDiscoverable by remember { mutableStateOf(false) }

    SectionCard(title = "Mesh Network Status") {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text("Discoverable", modifier = Modifier.weight(1f))
            Switch(
                checked = isDiscoverable,
                onCheckedChange = {
                    isDiscoverable = it
                    if (it) {
                        onStartBleDiscovery()
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Virtual Address: ${nodeState.address}")
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = onCreateHotspot) {
                Text("Create QR Code")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onConnectToHotspot) {
                Text("Scan QR Code")
            }
        }
        
        nodeState.connectUri?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Scan to connect:")
            val qrBitmap = generateQrCode(it)
            Image(bitmap = qrBitmap.asImageBitmap(), contentDescription = "QR Code", modifier = Modifier.size(200.dp))
        }
    }
}

@Composable
fun SectionCard(title: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}
