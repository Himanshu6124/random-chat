package com.himanshu.whatsapp.ui.theme.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.himanshu.whatsapp.ui.theme.components.ChatCard
import com.himanshu.whatsapp.dummydata.chatList

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier) {

    LazyColumn(
        modifier = modifier
    ) {
        items(chatList){
            ChatCard(it) {chat->
                navController.currentBackStackEntry?.savedStateHandle?.set("chatData", chat)
                navController.navigate("chat_detail/${chat.id}")
            }
        }
    }
}
