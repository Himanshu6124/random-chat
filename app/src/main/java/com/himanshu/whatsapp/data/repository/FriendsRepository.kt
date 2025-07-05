package com.himanshu.whatsapp.data.repository

import com.himanshu.whatsapp.data.model.User
import com.himanshu.whatsapp.data.service.ChatService
import retrofit2.Response

class FriendsRepository(private val chatService: ChatService) {

    suspend fun getFriends(userId: String): Response<ArrayList<User>> {
        return chatService.getFriends(userId)
    }

    suspend fun getPendingFriendRequests(userId: String): Response<ArrayList<User>> {
        return chatService.getPendingFriendRequests(userId)
    }

    suspend fun acceptFriendRequest(userId: String, friendId: String) = chatService.friendRequest(userId, friendId ,"accept")

    suspend fun getFriendConversations(userId: String) = chatService.getFriendConversations(userId)
}