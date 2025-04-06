package com.himanshu.whatsapp.di

import com.himanshu.whatsapp.data.repository.StompRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StompModule {

    @Provides
    @Singleton
    fun provideStompRepository(): StompRepository {
        return StompRepository()
    }
}
