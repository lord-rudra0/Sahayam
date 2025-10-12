package com.rudra.sahayam.ui.screens.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.sahayam.data.ChatService
import com.rudra.sahayam.data.db.ChatMessageDao
import com.rudra.sahayam.di.MeshrabiyaManager
import com.rudra.sahayam.domain.model.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatMessageDao: ChatMessageDao,
    private val chatService: ChatService
) : ViewModel() {

    private val virtualNode = MeshrabiyaManager.virtualNode

    val messages = chatMessageDao.getAllMessages()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    var newMessage by mutableStateOf("")
        private set

    init {
        chatService.startListening()
        viewModelScope.launch {
            chatService.incomingMessages.collect {
                chatMessageDao.insertMessage(it)
            }
        }
    }

    fun onNewMessageChange(message: String) {
        newMessage = message
    }

    fun sendMessage() {
        viewModelScope.launch {
            if(newMessage.isNotBlank()) {
                val message = ChatMessage(
                    senderAddress = "Me",
                    content = newMessage.trim(),
                    timestamp = System.currentTimeMillis(),
                    isMe = true
                )
                chatService.sendMessage(message)
                chatMessageDao.insertMessage(message)
                newMessage = ""
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        chatService.stopService()
    }
}
