package com.himanshu.whatsapp.data.service

import com.himanshu.whatsapp.data.model.User
import com.himanshu.whatsapp.ui.theme.components.ChatCardData
import com.himanshu.whatsapp.ui.theme.components.Message
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatService {

    @POST("/users")
    suspend fun saveUser(@Body user: User): Response<User>

    @GET("/users/{user_id}")
    suspend fun getUser(@Path("user_id") userId : String): Response<User>

    @GET("/users/profile-pictures")
    suspend fun getProfilePictures() : Response<ArrayList<String>>

    @GET("/conversations/user/{user_id}")
    suspend fun getConversations(@Path("user_id") userId : String) : Response<ArrayList<ChatCardData>>

    @GET("/messages/conversation/{conversationId}")
    suspend fun getMessages(@Path("conversationId") userId : String) : Response<ArrayList<Message>>

    @GET("/friendships/list/{userId}")
    suspend fun getFriends(@Path("userId") userId: String): Response<ArrayList<User>>

    @POST("/friendships/request/{userId}/{friendId}/{send_or_accept}")
    suspend fun friendRequest(
        @Path("userId") userId: String,
        @Path("friendId") friendId: String,
        @Path("send_or_accept") param: String,
    ): Response<Unit>

    @GET("/friendships/pending-sent/{userId}")
    suspend fun getPendingFriendRequests(@Path("userId") userId: String): Response<ArrayList<User>>

    @GET("/friendships/conversations/{user_id}")
    suspend fun getFriendConversations(@Path("user_id") userId : String) : Response<ArrayList<ChatCardData>>
}