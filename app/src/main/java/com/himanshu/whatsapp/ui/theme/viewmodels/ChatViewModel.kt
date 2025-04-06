package com.himanshu.whatsapp.ui.theme.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.whatsapp.data.repository.ChatRepository
import com.himanshu.whatsapp.data.repository.StompRepository
import com.himanshu.whatsapp.ui.theme.components.Message
import com.himanshu.whatsapp.ui.theme.viewmodels.uiStates.ChatUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val stompRepository: StompRepository,
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _messages = stompRepository.messages
    val messages: StateFlow<Message?>  = _messages

    private val _uiState = mutableStateOf(ChatUIState())
    val uiState : State<ChatUIState> = _uiState




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

    fun connectToSocket(friendUserId :String,conversationId :String){
        stompRepository.connectAndSubscribe(
            friendUserId = friendUserId,
            conversationId = conversationId
        )
    }

    fun sendMessage(message: Message){
        stompRepository.sendMessage("/app/chat.send" ,message)
    }

}