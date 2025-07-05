package com.himanshu.whatsapp.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.himanshu.whatsapp.data.model.User
import com.himanshu.whatsapp.ui.theme.viewmodels.FriendsViewModel

@Composable
fun StatusScreen(
    navController: NavController
) {

    val friendsViewModel = hiltViewModel<FriendsViewModel>()
    val uiState by friendsViewModel.uiState.collectAsState()
    val userId = friendsViewModel.user.collectAsState().value?.deviceId
    val pendingFriends = uiState.pendingRequests
    val isLoading by remember { mutableStateOf(false) }
    val error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        friendsViewModel.getPendingFriendRequests(userId.orEmpty())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Friends",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = error ?: "Unknown error",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            pendingFriends.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No friends found")
                }
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(pendingFriends) { friend ->
                        PendingFriendItem(friend = friend){
                            friendsViewModel.acceptFriendRequest(
                                userId = userId.orEmpty(),
                                friendId = friend.deviceId
                            )
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PendingFriendItem(
    friend: User,
    acceptFriendRequest: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            GlideImage(
                model = friend.photoId,
                modifier = Modifier.sizeIn(maxWidth = 24.dp , maxHeight = 24.dp),
                contentDescription = "profile"
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = friend.name ?: "Unknown",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = acceptFriendRequest) {
                Text(text = "Accept")
            }
        }
    }
}