package com.rudra.sahayam.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rudra.sahayam.domain.model.AlertItem

@Composable
fun AlertCard(item: AlertItem) {
    val bg = when(item.severity) {
        "high" -> Color(0xFFFFCDD2)
        "medium" -> Color(0xFFFFF9C4)
        else -> Color(0xFFC8E6C9)
    }
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(bg)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(item.title)
                Text("${item.location} • ${item.time}", style = MaterialTheme.typography.bodySmall)
            }
            Text(item.severity.uppercase(), style = MaterialTheme.typography.titleSmall)
        }
    }
}
