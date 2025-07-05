package com.himanshu.whatsapp.ui.theme.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.himanshu.whatsapp.ui.theme.components.TextComposable
import com.himanshu.whatsapp.ui.theme.nav.Screen
import com.himanshu.whatsapp.ui.theme.viewmodels.ConversationsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConversationScreen(navController: NavController) {

    val viewModel = hiltViewModel<ConversationsViewModel>()
    val uiState = viewModel.uiState.collectAsState().value
    val userId = navController.previousBackStackEntry?.savedStateHandle?.get<String>("userId")
    val conversation = uiState.matchingConversation

    LaunchedEffect(conversation) {
        if (conversation.conversationId.isNotEmpty()) {
            navController.currentBackStackEntry?.savedStateHandle?.set("chatData", conversation)
            navController.currentBackStackEntry?.savedStateHandle?.set("isRandom", true)
            navController.currentBackStackEntry?.savedStateHandle?.set("userId", userId)
            navController.navigate("${Screen.ChatDetail.route}/${conversation.conversationId}")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
            )
        } else {
            Button(
                onClick = { viewModel.connectToSocketAndSubscribe(userId.orEmpty()) },
                modifier = Modifier.size(140.dp),
            ) {
                TextComposable(
                    "Match",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
