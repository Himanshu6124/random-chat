package com.himanshu.whatsapp.data.repository

import com.himanshu.whatsapp.data.model.User
import com.himanshu.whatsapp.data.service.ChatService

class ChatRepository(private val chatService: ChatService) {

    suspend fun saveUser(user: User) = chatService.saveUser(user)

    suspend fun getUser(userId:String) = chatService.getUser(userId)

    suspend fun getProfilePictures() = chatService.getProfilePictures()

    suspend fun getConversations(userId: String) = chatService.getConversations(userId)

    suspend fun getMessages(conversationId : String) = chatService.getMessages(conversationId)
}