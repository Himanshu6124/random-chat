package com.himanshu.whatsapp.ui.theme.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    val conversation by viewModel.conversation.collectAsState()

    LaunchedEffect(Unit) {
        userId?.let { viewModel.getConversations(userId = userId) }
    }

    LaunchedEffect(conversation) {
        if (conversation.conversationId.isNotEmpty()){
            navController.currentBackStackEntry?.savedStateHandle?.set("chatData", conversation)
            navController.currentBackStackEntry?.savedStateHandle?.set("userId", userId)
            navController.navigate("${Screen.ChatDetail.route}/${conversation.conversationId}")
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.conversations) { chatItem ->
                ChatCard(chatItem) { chat ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("chatData", chat)
                    navController.currentBackStackEntry?.savedStateHandle?.set("userId", userId)
                    navController.navigate("${Screen.ChatDetail.route}/${chat.conversationId}")
                }
            }
        }

        Button(
            onClick = { viewModel.connectToSocket(userId.orEmpty()) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 66.dp)
        ) {
            Text("Match")
        }
    }
}
