package com.himanshu.whatsapp.ui.theme.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.whatsapp.data.repository.ChatRepository
import com.himanshu.whatsapp.ui.theme.viewmodels.uiStates.ConversationUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(ConversationUIState())
    val uiState : State<ConversationUIState> = _uiState


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
}