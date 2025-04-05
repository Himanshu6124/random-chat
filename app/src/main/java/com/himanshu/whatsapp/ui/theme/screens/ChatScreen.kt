package com.himanshu.whatsapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.himanshu.whatsapp.dummydata.dummyMessages
import com.himanshu.whatsapp.ui.theme.components.ChatCardData
import com.himanshu.whatsapp.ui.theme.components.ChatScreenTopBar
import com.himanshu.whatsapp.ui.theme.components.Message
import com.himanshu.whatsapp.ui.theme.components.MessageCard
import com.himanshu.whatsapp.ui.theme.components.SendMessageButton
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    navController: NavController,
    modifier: Modifier
) {
    val chat = navController.previousBackStackEntry?.savedStateHandle?.get<ChatCardData>("chatData")
    var inputText by remember { mutableStateOf("") }
    val messages = remember { dummyMessages }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    fun addMessage(newMessage: Message) {
        messages.add(newMessage)
        inputText = ""
        scope.launch {
            val index = messages.size - 1
            if(index != -1){
                listState.animateScrollToItem(messages.size-1)
            }
        }
    }

    fun deleteMessage(message: Message){
//        messages.remove(message)
    }

    Scaffold(
        topBar = {
            if (chat != null) {
                ChatScreenTopBar(chat){
                    navController.popBackStack()
                }
            }
        },
    ){ padding->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
                .background(Color.Transparent)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState
            ) {
                items(messages) { message ->
                    MessageCard(message){
                        deleteMessage(it)
                    }
                }
            }
            SendMessageButton(
                inputText = inputText,
                onTextUpdate = { inputText = it},
                onSendMessage = { addMessage(it) }
            )
        }
    }
}

