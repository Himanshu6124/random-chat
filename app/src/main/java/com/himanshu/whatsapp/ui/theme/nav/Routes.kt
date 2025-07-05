package com.himanshu.whatsapp.ui.theme.nav

sealed class Screen(val route : String){
    data object Home : Screen("home")
    data object Friends : Screen("friends")
    data object Conversations : Screen("conversations")
    data object Status : Screen("status")
    data object ChatDetail : Screen("chat_detail")
    data object SplashScreen : Screen("splash")
}