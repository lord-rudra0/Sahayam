package com.rudra.sahayam.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val senderAddress: String,
    val content: String,
    val timestamp: Long,
    val isMe: Boolean = false
)
