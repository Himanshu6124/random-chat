package com.himanshu.whatsapp.ui.theme.viewmodels.uiStates

import androidx.compose.runtime.Immutable
import com.himanshu.whatsapp.data.model.User
import com.himanshu.whatsapp.ui.theme.components.ChatCardData

@Immutable
data class FriendsUIState(
    val friends: List<ChatCardData> = emptyList(),
    val pendingRequests: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val exception: Exception?  = null
)