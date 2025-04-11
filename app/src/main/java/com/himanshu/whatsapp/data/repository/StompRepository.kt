package com.himanshu.whatsapp.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.himanshu.whatsapp.ui.theme.components.Message
import com.himanshu.whatsapp.ui.theme.components.OnlineStatus
import com.himanshu.whatsapp.ui.theme.components.TypingStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StompRepository @Inject constructor() {

    private val BASE_URL = "ws://192.168.31.8:8080/ws-chat"

    private val stompClient: StompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, BASE_URL)
    private val gson = Gson()

    private val _messages = MutableStateFlow<Message?>(null)
    val messages: StateFlow<Message?> = _messages

    private val _onlineStatus = MutableStateFlow(false)
    val onlineStatus: StateFlow<Boolean> = _onlineStatus

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping

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

            try {
                val jsonObject = JsonParser.parseString(topicMessage.payload).asJsonObject

                when {
                    jsonObject.has("message") -> {
                        val message = gson.fromJson(topicMessage.payload, Message::class.java)
                        _messages.value = message
                    }
                    jsonObject.has("online") -> {
                        val status = gson.fromJson(topicMessage.payload, OnlineStatus::class.java)
                        _onlineStatus.value = status.online
                    }

                    jsonObject.has("typing") -> {
                        val status = gson.fromJson(topicMessage.payload, TypingStatus::class.java)
                        _isTyping.value = status.typing
                    }
                    else -> {
                        Log.w("STOMP", "Unknown payload: ${topicMessage.payload}")
                    }
                }
            } catch (e: Exception) {
                Log.e("STOMP", "Parse error", e)
            }
        }
    }

    fun sendMessage(destination: String, message: Any) {
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