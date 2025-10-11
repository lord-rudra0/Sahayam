package com.rudra.sahayam.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rudra.sahayam.domain.model.AlertItem
import com.rudra.sahayam.ui.theme.HighSeverity
import com.rudra.sahayam.ui.theme.HighSeverityBg
import com.rudra.sahayam.ui.theme.LowSeverity
import com.rudra.sahayam.ui.theme.LowSeverityBg
import com.rudra.sahayam.ui.theme.MediumSeverity
import com.rudra.sahayam.ui.theme.MediumSeverityBg
import com.rudra.sahayam.ui.theme.TextSecondary

@Composable
fun AlertCard(item: AlertItem) {
    val backgroundColor = when (item.severity.lowercase()) {
        "high" -> HighSeverityBg
        "medium" -> MediumSeverityBg
        else -> LowSeverityBg
    }

    val severityColor = when (item.severity.lowercase()) {
        "high" -> HighSeverity
        "medium" -> MediumSeverity
        else -> LowSeverity
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Alert Icon",
                tint = severityColor,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${item.timestamp}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
    }
}
