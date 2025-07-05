package com.himanshu.whatsapp.ui.theme.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.himanshu.whatsapp.ui.theme.screens.ChatScreen
import com.himanshu.whatsapp.ui.theme.screens.ConversationScreen
import com.himanshu.whatsapp.ui.theme.screens.FriendsScreen
import com.himanshu.whatsapp.ui.theme.screens.OnboardingScreen
import com.himanshu.whatsapp.ui.theme.screens.SplashScreen
import com.himanshu.whatsapp.ui.theme.screens.StatusScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavigation(navController: NavHostController, modifier: Modifier) {

    val navGraph = navController.createGraph(
        startDestination = Screen.Home.route,
    ) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(
                navController = navController
            )
        }

        composable(route = Screen.Conversations.route) {
            ConversationScreen(
                navController = navController
            )
        }

        composable(route = Screen.Home.route) {
            OnboardingScreen(
                navController = navController
            )
        }
        composable(route = "${Screen.ChatDetail.route}/{id}") {
            ChatScreen(
                navController = navController,
                modifier = modifier
            )
        }
        composable(route = Screen.Friends.route) {
            FriendsScreen(
                navController = navController
            )
        }
        composable(route = Screen.Status.route) {
            StatusScreen(
                navController = navController
            )
        }
    }

    NavHost(
        navController = navController,
        graph = navGraph,
    )
}


@Composable
fun BottomBar(navController: NavHostController) {
    val destinations = arrayOf(
        Screen.Home.route,
        Screen.Friends.route,
        Screen.Status.route,
    )
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(15.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        destinations.forEachIndexed { index, route ->
            Text(
                text = route.replaceFirstChar { it.uppercaseChar() },
                fontSize = 20.sp,
                fontWeight = if (selectedItemIndex == index) FontWeight.Black else FontWeight.Light,
                modifier = Modifier
                    .clickable {
                        selectedItemIndex = index
                        navController.navigate(route = route)
                    }
            )
        }
    }
}