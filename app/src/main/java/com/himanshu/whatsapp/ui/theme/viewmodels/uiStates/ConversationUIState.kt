package com.himanshu.whatsapp.ui.theme.viewmodels.uiStates

import androidx.compose.runtime.Immutable
import com.himanshu.whatsapp.ui.theme.components.ChatCardData

@Immutable
data class ConversationUIState(
    val conversations :ArrayList<ChatCardData> = arrayListOf(),
    val matchingConversation :ChatCardData = ChatCardData() ,
    val isLoading : Boolean = false,
    val exception: Exception?  = null
)