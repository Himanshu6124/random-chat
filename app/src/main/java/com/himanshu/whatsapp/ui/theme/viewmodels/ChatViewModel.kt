package com.himanshu.whatsapp.ui.theme.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.whatsapp.data.repository.ChatRepository
import com.himanshu.whatsapp.data.repository.StompRepository
import com.himanshu.whatsapp.ui.theme.components.Message
import com.himanshu.whatsapp.ui.theme.components.OnlineStatus
import com.himanshu.whatsapp.ui.theme.components.TypingStatus
import com.himanshu.whatsapp.ui.theme.viewmodels.uiStates.ChatUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val stompRepository: StompRepository,
    private val chatRepository: ChatRepository
) : ViewModel() {

    private var typingJob: Job? = null
    private var hasSentTypingStatus = false
    private val typingDelayMillis = 1000L

    private val _message = stompRepository.messages
    val message: StateFlow<Message?>  = _message

    private val _isOnline = stompRepository.onlineStatus
    val isOnline = _isOnline

    private val _isTyping = stompRepository.isTyping
    val isTyping = _isTyping

    private val _uiState = mutableStateOf(ChatUIState())
    val uiState : State<ChatUIState> = _uiState

    fun addMessage(message: Message) {
        val currentMessages = _uiState.value.messages.toMutableList()
        currentMessages.add(message)
        _uiState.value = _uiState.value.copy(messages = currentMessages)
    }


    fun getMessages(conversationId: String) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                isLoading = true
            )

            try {
                val res = chatRepository.getMessages(conversationId).body()
                _uiState.value = uiState.value.copy(
                    messages = res?: arrayListOf(),
                    isLoading = false
                )
            }catch (e : Exception){
                _uiState.value = uiState.value.copy(
                    isLoading = false
                )
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        stompRepository.disconnect()
    }

    fun connectToSocket(friendUserId :String,conversationId :String ,senderId : String){
        stompRepository.connect(userId = senderId)
        stompRepository.subscribe(topic ="/topic/room/$friendUserId-$conversationId")
    }

    fun sendMessage(message: Message ,isRandom : Boolean){
        val destination = if(isRandom) "/app/chat.random.send" else "/app/chat.send"
        stompRepository.sendMessage(destination ,message)
    }
    fun sendOnlineStatus(senderId : String, conversationId: String){
        val onlineStatus = OnlineStatus(
            senderId = senderId,
            conversationId = conversationId,
            online = true
        )
        stompRepository.sendMessage("/app/chat.online" ,onlineStatus)
    }

    private fun sendTypingStatus(senderId : String, conversationId: String, isTyping : Boolean){
        val typingStatus = TypingStatus(
            senderId = senderId,
            conversationId = conversationId,
            typing = isTyping
        )
        stompRepository.sendMessage("/app/chat.typing" ,typingStatus)
    }

    fun getUserStatus(friendId : String, conversationId: String){
        val typingStatus = OnlineStatus(
            senderId = friendId,
            conversationId = conversationId,
        )
        stompRepository.sendMessage("/app/chat.user.status" ,typingStatus)
    }

    fun onUserTyping(senderId: String, conversationId: String, inputText: String) {
        if (inputText.isNotEmpty()) {
            if (!hasSentTypingStatus) {
                hasSentTypingStatus = true
                sendTypingStatus(senderId, conversationId, isTyping = true)
            }

            typingJob?.cancel()
            typingJob = viewModelScope.launch {
                delay(typingDelayMillis)
                hasSentTypingStatus = false
                sendTypingStatus(senderId, conversationId, isTyping = false)
            }
        } else {
            sendTypingStatus(senderId, conversationId, isTyping = false)
        }
    }
}