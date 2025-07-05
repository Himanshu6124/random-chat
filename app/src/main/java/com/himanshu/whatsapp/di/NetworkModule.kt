package com.himanshu.whatsapp.di

import com.himanshu.whatsapp.data.service.ChatService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object{
        private const val BASE_URL = "http://192.168.31.8:8080"
    }

    @Provides
    @Singleton
    fun provideNetworkModule(): ChatService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ChatService::class.java)
    }
}