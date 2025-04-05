package com.himanshu.whatsapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.himanshu.whatsapp.data.model.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")
private val USER_KEY = stringPreferencesKey("user_data")


class UserDataStore(private val context: Context) {

    private val gson = Gson()

    suspend fun saveUser(user: User?) {
        context.dataStore.edit { preferences ->
            preferences[USER_KEY] = gson.toJson(user)
        }
    }

    suspend fun getUser(): User? {
        val prefs = context.dataStore.data.map { preferences ->
            preferences[USER_KEY]?.let { gson.fromJson(it, User::class.java) }
        }.first()

        return prefs
    }

    suspend fun clearUser() {
        context.dataStore.edit { it.clear() }
    }
}
