package com.himanshu.whatsapp.di

import com.himanshu.whatsapp.data.repository.ChatRepository
import com.himanshu.whatsapp.data.service.ChatService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(chatService: ChatService) : ChatRepository{
        return ChatRepository(chatService)
    }
}