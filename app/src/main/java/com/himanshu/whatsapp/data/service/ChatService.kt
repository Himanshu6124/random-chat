package com.himanshu.whatsapp.data.service

import com.himanshu.whatsapp.data.model.User
import com.himanshu.whatsapp.ui.theme.components.ChatCardData
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

    @GET("/conversations/user/{user_id")
    suspend fun getConversations() : Response<ArrayList<ChatCardData>>

}