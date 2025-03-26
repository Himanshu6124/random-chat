package com.himanshu.whatsapp.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SendMessageButton(
    inputText : String,
    onTextUpdate : (String)-> Unit,
    onSendMessage : (Message)-> Unit,
) {
    Row(
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputText,
            leadingIcon =  {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                ) } ,
            trailingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = null,
                    modifier = Modifier.clickable {

                        if(inputText == "") return@clickable

                        val newMessage = Message(
                            id = "11",
                            text = inputText,
                            status = MessageStatus.SENT,
                            time = System.currentTimeMillis(),
                            isByUser = true
                        )
                        onSendMessage(newMessage)
                    }
                ) },
            placeholder = {
                TextComposable(
                    text = "Type your message here ...",
                    fontWeight = FontWeight.Normal,
                ) },
            onValueChange = onTextUpdate
        )
    }
}