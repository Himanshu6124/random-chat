package com.himanshu.whatsapp.di

import android.content.Context
import android.content.Intent
import com.himanshu.core.Navigator
import com.himanshu.whatsapp.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {


    @Provides
    @Singleton
    fun provideNavigator() : Navigator = object : Navigator {
        override fun navigateToMainActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

        override fun navigateToLoginActivity(context: Context) {
            val intent = Intent(context, Class.forName("com.himanshu.login.LoginActivity"))
            context.startActivity(intent)
        }
    }
}