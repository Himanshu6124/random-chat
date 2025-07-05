package com.himanshu.whatsapp.ui.theme.components

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.himanshu.whatsapp.ui.theme.viewmodels.ConversationsViewModel
import kotlinx.parcelize.Parcelize

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ChatCard(chat: ChatCardData, onClick : (ChatCardData)-> Unit) {

    val viewModel = hiltViewModel<ConversationsViewModel>()

    Row(
        modifier = Modifier
            .clickable { onClick(chat) }
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        GlideImage(
            model = chat.photoUrl,
            modifier = Modifier
                .height(50.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.LightGray)
                .padding(12.dp),
            contentDescription = null
        )

        Column(
            modifier = Modifier.padding(start = 12.dp)
        ) {
            TextComposable(
                text = chat.friendUserName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )

            if( !chat.lastMessage.isNullOrEmpty()){
                TextComposable(text = chat.lastMessage)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
//        TextComposable(text = viewModel.formatChatTimestampWithoutZone(chat.lastMessageTime))
    }
    HorizontalDivider(thickness = 2.dp )

}

@Parcelize
data class ChatCardData(
    val conversationId : String = "",
    val friendUserName: String = "",
    val friendUserId: String = "",
    val isTyping : Boolean= false,
    val photoUrl: String = "",
    val lastMessage: String? = "",
    val isByYou : Boolean = false,
    val messageStatus : String? = "SENT",
    val lastMessageTime: String? = "",
    val messageType  :String? = ""
):Parcelable



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PrevChatCard() {
    val chat = ChatCardData(
        conversationId = "1",
        friendUserName = "Himanshu",
        friendUserId = "",
        lastMessage = "How are you doing ?",
        photoUrl = "",
        lastMessageTime = "Sunday",
        messageType = "Audio"
    )
    ChatCard(chat){

    }
}

@Composable
fun TextComposable(
    text: String,
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.SemiBold
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight
    )
}

@Composable
fun HorizontalSpacer( width : Dp){
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun VerticalSpacer( height : Dp){
    Spacer(modifier = Modifier.height(height))
}