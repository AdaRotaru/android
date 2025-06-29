package com.relearn.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import com.relearn.app.navigation.AppNavGraph
import com.relearn.app.navigation.mainnav.BottomBar
import com.relearn.app.navigation.mainnav.MainNav
import com.relearn.app.navigation.mainnav.TopBar


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //com.relearn.app.feature.HOME.data.ChallengeSeeder.seedChallenges()
        setContent {
            val rootNavController = rememberNavController()
            val showMainApp = remember { mutableStateOf(false) }

            if (showMainApp.value) {
                // Ecranele aplicației principale
                Scaffold(
                    topBar = { TopBar(rootNavController) },
                    bottomBar = { BottomBar(rootNavController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainNav(
                            navController = rootNavController,
                            onLogout = { showMainApp.value = false }
                        )
                    }
                }
            } else {
                // Ecranele de autentificare
                AppNavGraph(
                    navController = rootNavController,
                    onLoginSuccess = { showMainApp.value = true }
                )
            }
        }
    }
}