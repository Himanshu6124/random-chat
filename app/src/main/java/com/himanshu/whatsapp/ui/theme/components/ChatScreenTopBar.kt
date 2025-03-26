package com.himanshu.whatsapp.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatScreenTopBar(chat: ChatCardData ,onBackPress: ()-> Unit) {

    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onBackPress() },
                contentDescription = "back"
            )

            Image(
                painter = painterResource(chat.profilePic),
                modifier = Modifier.sizeIn(maxWidth = 24.dp , maxHeight = 24.dp),
                contentDescription = "profile"
            )

            HorizontalSpacer(15.dp)

            Column(
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                TextComposable(
                    text = chat.name,
                    fontSize = 18.sp
                )
                TextComposable(
                    text = "online",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Outlined.Call,
                tint = Color.Black,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(30.dp)
                    .clickable {  },
                contentDescription = "back"
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}