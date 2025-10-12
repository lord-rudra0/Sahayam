package com.rudra.sahayam.data

import com.rudra.sahayam.di.MeshrabiyaManager
import com.rudra.sahayam.domain.model.ChatMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatService @Inject constructor() {

    private val virtualNode = MeshrabiyaManager.virtualNode
    private val json = Json { ignoreUnknownKeys = true }
    private val chatServiceScope = CoroutineScope(Job() + Dispatchers.IO)
    private var listeningSocket: DatagramSocket? = null

    private val _incomingMessages = MutableSharedFlow<ChatMessage>()
    val incomingMessages: Flow<ChatMessage> = _incomingMessages

    companion object {
        const val CHAT_PORT = 9998
    }

    fun startListening() {
        if (listeningSocket != null && !listeningSocket!!.isClosed) return

        chatServiceScope.launch {
            listeningSocket = virtualNode.createBoundDatagramSocket(CHAT_PORT)
            val buffer = ByteArray(4096)
            val packet = DatagramPacket(buffer, buffer.size)

            while (isActive) {
                try {
                    listeningSocket?.receive(packet)
                    val jsonString = String(packet.data, 0, packet.length)
                    val message = json.decodeFromString<ChatMessage>(jsonString)
                    _incomingMessages.emit(message.copy(isMe = false))
                } catch (e: Exception) {
                    if (isActive) { // Don't log exceptions on planned socket closure
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    suspend fun sendMessage(message: ChatMessage) {
        val jsonString = json.encodeToString(message)
        val data = jsonString.toByteArray()
        val socket = virtualNode.createDatagramSocket()
        try {
            val packet = DatagramPacket(data, data.size, InetAddress.getByName("255.255.255.255"), CHAT_PORT)
            socket.send(packet)
        } finally {
            socket.close()
        }
    }

    fun stopService() {
        listeningSocket?.close()
        listeningSocket = null
        chatServiceScope.cancel()
    }
}
