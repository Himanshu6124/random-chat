package com.himanshu.whatsapp.ui.theme.components

import android.os.Parcelable
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshu.whatsapp.R
import kotlinx.parcelize.Parcelize

@Composable
fun ChatCard(chat: ChatCardData, onClick : (ChatCardData)-> Unit) {

    Row(
        modifier = Modifier
            .clickable { onClick(chat) }
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            imageVector = ImageVector.vectorResource(chat.profilePic),
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
                text = chat.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )
            TextComposable(text = chat.message)
        }

        Spacer(modifier = Modifier.weight(1f))
        TextComposable(text = chat.lastMessageTime)
    }
    HorizontalDivider(thickness = 2.dp )

}

@Parcelize
data class ChatCardData(
    val id : String,
    val name: String,
    val message: String,
    val profilePic: Int,
    val lastMessageTime: String
):Parcelable



@Preview(showBackground = true)
@Composable
fun PrevChatCard() {
    val chat = ChatCardData(
        id = "1",
        name = "Himanshu",
        message = "How are you doing ?",
        profilePic = R.drawable.user_profile,
        lastMessageTime = "Sunday"
    )
//    ChatCard(chat)
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