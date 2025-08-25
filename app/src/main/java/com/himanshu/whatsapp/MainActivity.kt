package com.himanshu.whatsapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.himanshu.core.Navigator
import com.himanshu.whatsapp.ui.theme.WhatsAppTheme
import com.himanshu.whatsapp.ui.theme.nav.BottomBar
import com.himanshu.whatsapp.ui.theme.nav.BottomNavigation
import com.himanshu.whatsapp.ui.theme.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator
    private val userViewModel: UserViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var shouldKeepSplashScreen by mutableStateOf(true)

        splashScreen.setKeepOnScreenCondition {
            shouldKeepSplashScreen
        }

        enableEdgeToEdge()
        setContent {
            val uiState by userViewModel.uiState

            LaunchedEffect(Unit) {
                userViewModel.checkUserStatus()
            }

            LaunchedEffect(uiState.user, uiState.loading) {
                if (!uiState.loading) {
                    shouldKeepSplashScreen = false
                    if (uiState.user == null) {
                        navigator.navigateToLoginActivity(this@MainActivity)
                        finish()
                    }
                }
            }

            if (!uiState.loading && uiState.user != null) {
                WhatsAppTheme {
                    val navController = rememberNavController()
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = { BottomBar(navController = navController) }
                    )
                    { innerPadding ->
                        BottomNavigation(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
