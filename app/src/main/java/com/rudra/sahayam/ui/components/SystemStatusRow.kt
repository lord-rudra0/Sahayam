package com.rudra.sahayam.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rudra.sahayam.ui.theme.HighSeverity
import com.rudra.sahayam.ui.theme.LowSeverity
import com.rudra.sahayam.util.ConnectivityObserver

@Composable
fun SystemStatusRow(
    networkStatus: ConnectivityObserver.NetworkStatus,
    isBluetoothEnabled: Boolean
) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {

        // Network Status
        val isOnline = networkStatus.status == ConnectivityObserver.Status.Available
        val networkColor = if (isOnline) LowSeverity else HighSeverity
        val networkText = if(isOnline) "Connected" else "Offline"
        val networkIcon = when (networkStatus.type) {
            ConnectivityObserver.ConnectionType.Wifi -> Icons.Default.Wifi
            ConnectivityObserver.ConnectionType.Cellular -> Icons.Default.SignalCellularAlt
            else -> Icons.Default.WifiOff
        }

        Icon(
            imageVector = networkIcon, 
            contentDescription = "Network Status", 
            tint = networkColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = networkText, 
            color = networkColor, 
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Bluetooth Status
        val bluetoothColor = if (isBluetoothEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        val bluetoothText = if(isBluetoothEnabled) "On" else "Off"

        Icon(
            imageVector = Icons.Default.Bluetooth, 
            contentDescription = "Bluetooth Status", 
            tint = bluetoothColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = bluetoothText, 
            color = bluetoothColor, 
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
    }
}
