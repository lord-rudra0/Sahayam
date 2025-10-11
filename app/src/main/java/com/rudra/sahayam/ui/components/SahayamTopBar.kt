package com.rudra.sahayam.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SahayamTopBar(
    title: String,
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = onNotificationClick) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
            }
            IconButton(onClick = onProfileClick) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile")
            }
        }
    )
}
