package com.himanshu.whatsapp.ui.theme.viewmodels.uiStates

import androidx.compose.runtime.Immutable
import com.himanshu.whatsapp.ui.theme.components.Message

@Immutable
data class ChatUIState(
    val messages :ArrayList<Message> = arrayListOf(),
    val isLoading : Boolean = false,
    val exception: Exception?  = null
)