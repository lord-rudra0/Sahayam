package com.rudra.sahayam.ui.screens.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rudra.sahayam.domain.model.AlertItem
import com.rudra.sahayam.domain.model.ResourceItem
import com.rudra.sahayam.ui.components.SystemStatusRow
import com.rudra.sahayam.util.ConnectivityObserver

@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    userAddress: String,
    networkStatus: ConnectivityObserver.NetworkStatus,
    isBluetoothEnabled: Boolean,
    isGuest: Boolean,
    alerts: List<AlertItem>,
    resources: List<ResourceItem>,
    onOpenMap: () -> Unit = {},
    onReportClick: () -> Unit = {},
    onCreateOfflineReport: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Common header for both guest and logged-in user
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Location Display
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

        // Authenticated User Header
        if (!isGuest) {
            item { ProfileHeader() }
        }

        // Common Components for both users
        item { WeatherOverview() }
        
        // Live Alerts Section
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

        // Nearby Shelters Section
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

        // Authenticated User Components
        if (!isGuest) {
            item { MissionsList() }
            item { ResourcesSummary() }
            item { QuickActions(onReportClick) }
            item { SyncStatusInfo(onCreateOfflineReport) }
        }
    }
}

// Placeholder Composables for the new UI structure

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
        // In a real implementation, this would be a GoogleMap composable
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
fun QuickActions(onReportClick: () -> Unit) {
    SectionCard(title = "Quick Actions") {
        Text("Buttons for quick actions like 'Report' and 'Request' will be here.")
    }
}

@Composable
fun SyncStatusInfo(onCreateOfflineReport: () -> Unit) {
    SectionCard(title = "Sync/Offline Info") {
        Text("Information about offline data and sync status will be here.")
    }
}


// A generic card for section content
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
