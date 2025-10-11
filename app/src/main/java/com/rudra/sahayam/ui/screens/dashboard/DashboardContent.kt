package com.rudra.sahayam.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rudra.sahayam.domain.model.AlertItem
import com.rudra.sahayam.domain.model.ResourceItem

@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    alerts: List<AlertItem>,
    resources: List<ResourceItem>,
    onOpenMap: () -> Unit = {},
    onReportClick: () -> Unit = {}
) {
    LazyColumn(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Active Alerts", style = MaterialTheme.typography.titleMedium)
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                alerts.forEach { alert ->
                    AlertCard(alert)
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Nearby Resources", style = MaterialTheme.typography.titleMedium)
        }
        items(resources) { resource ->
            ResourceCard(resource)
        }
        item {
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}
