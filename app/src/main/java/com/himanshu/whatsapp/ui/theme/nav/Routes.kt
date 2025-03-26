package com.himanshu.whatsapp.ui.theme.nav

sealed class Screen(val route : String){
    data object Home : Screen("home")
    data object Call : Screen("call")
    data object Status : Screen("status")
    data object ChatDetail : Screen("chat_detail")
}