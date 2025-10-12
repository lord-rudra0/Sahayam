package com.rudra.sahayam.ui.screens.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import com.rudra.sahayam.ui.theme.TextSecondary
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
        // Header Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Location Display
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
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
                // System Status
                SystemStatusRow(networkStatus, isBluetoothEnabled)
            }
        }

        // Alerts Section
        item {
            Text(
                text = "Active Alerts",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        items(alerts, key = { it.id }) { alert ->
            AnimatedVisibility(
                visible = true, // This should be tied to a loading state
                enter = fadeIn(animationSpec = tween(500))
            ) {
                AlertCard(alert)
            }
        }

        // Resources Section
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Nearby Resources",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        items(resources, key = { it.id }) { resource ->
            AnimatedVisibility(
                visible = true, // This should be tied to a loading state
                enter = fadeIn(animationSpec = tween(500, delayMillis = 200))
            ) {
                ResourceCard(resource)
            }
        }
        
        // Temporary Guest Button
        if (isGuest) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onCreateOfflineReport) {
                    Text("Create Offline Report (Guest Mode Test)")
                }
            }
        }
    }
}
