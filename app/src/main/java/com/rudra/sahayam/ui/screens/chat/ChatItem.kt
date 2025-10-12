package com.rudra.sahayam.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rudra.sahayam.domain.model.ChatMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatItem(message: ChatMessage) {
    val alignment = if (message.isMe) Alignment.CenterEnd else Alignment.CenterStart
    val backgroundColor = if (message.isMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    val textColor = if (message.isMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary

    val timeFormatter = SimpleDateFormat("h:mm a", Locale.getDefault())
    val timeString = timeFormatter.format(Date(message.timestamp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        contentAlignment = alignment
    ) {
        Column(horizontalAlignment = if (message.isMe) Alignment.End else Alignment.Start) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(backgroundColor)
                    .padding(12.dp)
            ) {
                Text(
                    text = message.content,
                    color = textColor,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = timeString,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}
