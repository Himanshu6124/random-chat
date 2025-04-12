package com.himanshu.whatsapp.ui.theme.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.whatsapp.data.repository.ChatRepository
import com.himanshu.whatsapp.data.repository.StompRepository
import com.himanshu.whatsapp.ui.theme.viewmodels.uiStates.ConversationUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val stompRepository: StompRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(ConversationUIState())
    val uiState : State<ConversationUIState> = _uiState

    private val _conversation = stompRepository.chatCardData
    val conversation = _conversation


    fun getConversations(userId: String) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                isLoading = true
            )

            try {
                val res = chatRepository.getConversations(userId).body()
                _uiState.value = uiState.value.copy(
                    conversations = res?: arrayListOf(),
                    isLoading = false
                )
            }catch (e : Exception){
                _uiState.value = uiState.value.copy(
                    isLoading = false
                )
            }
        }
    }

    fun connectToSocket(userId :String){
        stompRepository.connectAndSubscribe(
            userId = userId,
            isForChat = false
        )
        startMatching(userId)
    }

    fun startMatching(userId : String){
        stompRepository.sendMessage("/app/chat.random" ,userId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatChatTimestampWithoutZone(dateTimeString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val messageTime = LocalDateTime.parse(dateTimeString, formatter)
        val now = LocalDateTime.now()

        val today = now.toLocalDate()
        val messageDate = messageTime.toLocalDate()

        return when {
            messageDate.isEqual(today) -> {
                messageTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
            }
            messageDate.isEqual(today.minusDays(1)) -> {
                "Yesterday"
            }
            messageDate.isAfter(today.minusDays(7)) -> {
                messageTime.format(DateTimeFormatter.ofPattern("EEE")) // e.g., Mon
            }
            else -> {
                messageTime.format(DateTimeFormatter.ofPattern("dd/MM/yy"))
            }
        }
    }

}