package com.himanshu.whatsapp.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.himanshu.whatsapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(1000)
        navController.navigate("home") { popUpTo("splash") { inclusive = true } }
    }

    Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = R.mipmap.app_icon), contentDescription = "Logo")
    }
}
