package com.himanshu.whatsapp.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageCard(
    message: Message ,
    onDeleteMessage : (Message)-> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = if(message.isByUser) 0.dp else 20.dp, start = 4.dp,end = 4.dp),
        horizontalArrangement = if(message.isByUser) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier.clickable { onDeleteMessage(message) },
        ){
            Text(
                text = message.text,
                modifier = Modifier.background(
                    shape = RoundedCornerShape(40),
                    color = Color.Gray
                ).padding(10.dp)
            )

            Text(
                modifier = Modifier.align(Alignment.End).padding(end = 10.dp),
                text = "12:43 pm",
                fontSize = 10.sp
            )
            if(message.isByUser){
                Text(
                    modifier = Modifier.align(Alignment.End).padding(end = 10.dp),
                    text = when(message.status){
                        MessageStatus.SENT -> "sent"
                        MessageStatus.DELIVERED -> "delivered"
                        MessageStatus.READ -> "read"
                    },
                    fontSize = 10.sp
                )
            }
        }
    }
}


enum class MessageStatus {
    SENT, DELIVERED, READ
}

data class Message(
    val id: String,
    val text: String,
    val status: MessageStatus,
    val time: Long,
    val isByUser: Boolean
)

