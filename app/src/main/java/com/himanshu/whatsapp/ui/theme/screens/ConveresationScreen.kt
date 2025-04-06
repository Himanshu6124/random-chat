package com.himanshu.whatsapp.ui.theme.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.himanshu.whatsapp.ui.theme.components.ChatCard
import com.himanshu.whatsapp.ui.theme.nav.Screen
import com.himanshu.whatsapp.ui.theme.viewmodels.ConversationsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConversationScreen(navController: NavController, modifier: Modifier) {

    val viewModel = hiltViewModel<ConversationsViewModel>()
    val uiState = viewModel.uiState.value
    val userId = navController.previousBackStackEntry?.savedStateHandle?.get<String>("userId")

    LaunchedEffect(Unit) {
        userId?.let { viewModel.getConversations(userId = userId) }
    }


    LazyColumn(
        modifier = modifier
    ) {
        items(uiState.conversations){
            ChatCard(it) {chat->
                navController.currentBackStackEntry?.savedStateHandle?.set("chatData", chat)
                navController.currentBackStackEntry?.savedStateHandle?.set("userId", userId)
                navController.navigate("${Screen.ChatDetail.route}/${chat.conversationId}")
            }
        }
    }
}
