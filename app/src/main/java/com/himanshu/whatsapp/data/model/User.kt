package com.himanshu.whatsapp.data.model

data class User(
    val deviceId: String,
    val name: String? = null,
    val gender: String? = null,
    val location: String? = null,
    val status: String? = null,
    val suspectLevel: String? = null,
    val bio: String? = null,
    val photoId: String? = null,
    val lastOnline: String? = null,
    val fcmToken: String? = null
)

