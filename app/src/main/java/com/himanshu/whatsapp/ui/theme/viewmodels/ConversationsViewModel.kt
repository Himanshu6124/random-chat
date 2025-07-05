package com.himanshu.whatsapp.ui.theme.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.whatsapp.data.model.User
import com.himanshu.whatsapp.data.repository.ChatRepository
import com.himanshu.whatsapp.data.repository.StompRepository
import com.himanshu.whatsapp.data.repository.UserDataStore
import com.himanshu.whatsapp.ui.theme.viewmodels.uiStates.ConversationUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val stompRepository: StompRepository,
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        viewModelScope.launch {
            _user.value = userDataStore.getUser()
        }
    }

    private val _uiState = MutableStateFlow(ConversationUIState())
    val uiState: StateFlow<ConversationUIState> = _uiState
    private val _conversation = stompRepository.chatCardData

    init {
        viewModelScope.launch {
            _conversation.collect { conversation->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        matchingConversation = conversation
                    )
                }
            }
        }
    }


    fun getConversations(userId: String) {
        viewModelScope.launch {

            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            try {
                val res = chatRepository.getConversations(userId).body()
                _uiState.update { state ->
                    state.copy(
                        conversations = res ?: arrayListOf(),
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun connectToSocketAndSubscribe(userId: String) {
        stompRepository.connect(
            userId = userId,
        )
        stompRepository.subscribe(
            topic = "/topic/room/random/$userId"
        )
        startMatching(userId)
    }

    private fun startMatching(userId: String) {
        _uiState.update { state ->
            state.copy(
                isLoading = true
            )
        }
        stompRepository.sendMessage("/app/chat.random", userId)
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