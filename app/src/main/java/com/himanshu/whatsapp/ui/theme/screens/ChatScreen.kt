package com.himanshu.whatsapp.ui.theme.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.himanshu.whatsapp.ui.theme.components.ChatCardData
import com.himanshu.whatsapp.ui.theme.components.ChatScreenTopBar
import com.himanshu.whatsapp.ui.theme.components.Message
import com.himanshu.whatsapp.ui.theme.components.MessageCard
import com.himanshu.whatsapp.ui.theme.components.MessageStatus
import com.himanshu.whatsapp.ui.theme.components.SendMessageButton
import com.himanshu.whatsapp.ui.theme.viewmodels.ChatViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    navController: NavController,
    modifier: Modifier
) {
    val viewModel = hiltViewModel<ChatViewModel>()
    val chat = navController.previousBackStackEntry?.savedStateHandle?.get<ChatCardData>("chatData")
    val userId = navController.previousBackStackEntry?.savedStateHandle?.get<String>("userId")
    var inputText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Message>() }
    val isOnline = viewModel.isOnline.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val message by viewModel.messages.collectAsState()
    val context = LocalContext.current

    @RequiresApi(Build.VERSION_CODES.O)
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

    LaunchedEffect(message) {
        message?.let { addMessage(it) }
    }


    LaunchedEffect(Unit) {
        viewModel.getMessages(chat?.conversationId?:"")
        viewModel.sendOnlineStatus(userId?:"",chat?.conversationId?:"")
        viewModel.connectToSocket(
            friendUserId = chat?.friendUserId ?:"" ,
            conversationId = chat?.conversationId?: ""
        )
    }


    fun deleteMessage(message: Message){
//        messages.remove(message)
    }

    Scaffold(
        topBar = {
            if (chat != null) {
                ChatScreenTopBar(chat , isOnline = isOnline.value ){
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
                    MessageCard(userId,message){
                        deleteMessage(it)
                    }
                }
            }
            SendMessageButton(
                userId = userId ?:"",
                conversationId = chat?.conversationId ?:"",
                inputText = inputText,
                onTextUpdate = { inputText = it},
                onSendMessage = {
                    viewModel.sendMessage(it)
                    addMessage(it)
                }
            )
        }
    }
}

