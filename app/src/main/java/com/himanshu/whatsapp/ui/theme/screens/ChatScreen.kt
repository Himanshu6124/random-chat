package com.himanshu.whatsapp.ui.theme.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.himanshu.whatsapp.ui.theme.components.ChatCardData
import com.himanshu.whatsapp.ui.theme.components.ChatScreenTopBar
import com.himanshu.whatsapp.ui.theme.components.Message
import com.himanshu.whatsapp.ui.theme.components.MessageCard
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
    val isRandomMatch = navController.previousBackStackEntry?.savedStateHandle?.get<Boolean>("isRandom") ?: false
    var inputText by remember { mutableStateOf("") }
    val messages = viewModel.uiState.collectAsState().value.messages
    val isOnline = viewModel.isOnline.collectAsState()
    val isTyping = viewModel.isTyping.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val message by viewModel.message.collectAsState()
    val context = LocalContext.current

    var toastMessage by remember { mutableStateOf<String?>(null) }

    // Watch for friend request success/error and show toast
    LaunchedEffect(viewModel.requestSuccess, viewModel.error) {
        if (viewModel.requestSuccess.value == true) {
            toastMessage = "Friend request sent successfully!"
            // Optionally, clear the flag in your ViewModel/state here if necessary.
        } else if (viewModel.error.value != null) {
            toastMessage = viewModel.error.value
        }
    }
    // Actually show the toast when toastMessage is set
    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            toastMessage = null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addMessage(newMessage: Message) {
        viewModel.addMessage(newMessage)
        inputText = ""
        scope.launch {
            val index = messages.size - 1
            if (index != -1) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    LaunchedEffect(message) {
        message?.let { addMessage(it) }
    }


    LaunchedEffect(Unit) {
        viewModel.getMessages(chat?.conversationId ?: "")
        viewModel.connectToSocket(
            friendUserId = chat?.friendUserId ?: "",
            conversationId = chat?.conversationId ?: "",
            senderId = userId.orEmpty()
        )
        viewModel.sendOnlineStatus(userId ?: "", chat?.conversationId ?: "")
        viewModel.getUserStatus(
            friendId = chat?.friendUserId.orEmpty(),
            conversationId = chat?.conversationId.orEmpty()
        )
    }


    fun deleteMessage(message: Message) {
//        messages.remove(message)
    }

    Scaffold(
        topBar = {
            if (chat != null) {
                ChatScreenTopBar(
                    chat = chat,
                    isOnline = isOnline.value,
                    onBackPress = { navController.popBackStack() },
                    onAddFriend = {
                        viewModel.sendFriendRequest(
                            userId = userId.orEmpty(),
                            friendId = chat.friendUserId
                        )
                    }
                )
            }
        },
    ) { padding ->

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
                    MessageCard(userId, message) {
                        deleteMessage(it)
                    }
                }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                if (isTyping.value) {
                    Text(
                        "typing...",
                        modifier = Modifier.padding(
                            start = 20.dp,
                            bottom = 5.dp
                        )
                    )
                }
                SendMessageButton(
                    userId = userId ?: "",
                    conversationId = chat?.conversationId ?: "",
                    inputText = inputText,
                    onTextUpdate = {
                        inputText = it
                        viewModel.onUserTyping(
                            senderId = userId ?: "",
                            conversationId = chat?.conversationId.orEmpty(),
                            inputText = it
                        )
                    },
                    onSendMessage = {
                        viewModel.sendMessage(message = it, isRandom = isRandomMatch)
                        addMessage(it)
                    }
                )
            }

        }
    }
}

