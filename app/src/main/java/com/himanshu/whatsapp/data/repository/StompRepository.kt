package com.himanshu.whatsapp.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.himanshu.whatsapp.ui.theme.components.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StompRepository @Inject constructor() {

    private val BASE_URL = "wss://randomchat-d3gv.onrender.com/ws-chat"

    private val stompClient: StompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, BASE_URL)
    val gson = Gson()

    private val _messages = MutableStateFlow<Message?>(null)
    val messages: StateFlow<Message?> = _messages

    @SuppressLint("CheckResult")
    fun connectAndSubscribe(friendUserId: String, conversationId: String) {
        if(stompClient.isConnected) return
        stompClient.connect()

        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> Log.d("STOMP", "Connection opened")
                LifecycleEvent.Type.ERROR -> Log.e("STOMP", "Error", lifecycleEvent.exception)
                LifecycleEvent.Type.CLOSED -> Log.d("STOMP", "Connection closed")
                else -> Unit
            }
        }

        val topicPath = "/topic/room/$friendUserId-$conversationId"

        stompClient.topic(topicPath).subscribe { topicMessage ->
            Log.d("STOMP", "Received: ${topicMessage.payload}")
            val messageObject = gson.fromJson(topicMessage.payload, Message::class.java)
            _messages.value = messageObject
        }
    }

    fun sendMessage(destination: String, message: Message) {
        val json = Gson().toJson(message)
        stompClient.send(destination, json).subscribe()
    }

    fun disconnect() {
        if (stompClient.isConnected) {
            stompClient.disconnect()
            Log.d("STOMP", "Disconnected")
        }
    }
}

data class ChatMessage(
    val id : String? = null,
    val senderId: String,
    val message: String,
    val conversationId: String
)
