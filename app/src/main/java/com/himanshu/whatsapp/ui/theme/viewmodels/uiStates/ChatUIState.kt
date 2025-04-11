package com.himanshu.whatsapp.ui.theme.viewmodels.uiStates

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import com.himanshu.whatsapp.ui.theme.components.Message

@Immutable
data class ChatUIState(
    val messages: MutableList<Message> = mutableStateListOf(),
    val isLoading: Boolean = false,
    val isOnline : Boolean = false,
    val exception: Exception?  = null
)