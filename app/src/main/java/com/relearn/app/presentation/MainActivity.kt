package com.relearn.app.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.relearn.app.core.ui.theme.ReLearnTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.relearn.app.ui.challenge.AddChallengeScreen
import com.relearn.app.feature.challenges.viewmodel.ChallengeViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.relearn.app.feature.challenges.ui.ChallengeListScreen
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.relearn.app.navigation.AppNavGraph
import com.relearn.app.navigation.MainNav.BottomBar
import com.relearn.app.navigation.MainNav.MainNav
import com.relearn.app.navigation.MainNav.TopBar


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                        MainNav(rootNavController)
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