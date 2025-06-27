package com.relearn.app.navigation.MainNav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.relearn.app.Screens.ExtraScreen
import com.relearn.app.Screens.HomeScreen
import com.relearn.app.Screens.JournalScreen
import com.relearn.app.Screens.ProfileScreen
import com.relearn.app.Screens.ProgressScreen
import com.relearn.app.Screens.SettingsScreen


@Composable
fun MainNav(navController: NavHostController, onLogout: () -> Unit) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route) { HomeScreen() }
        composable(NavRoutes.Journal.route) { JournalScreen() }
        composable(NavRoutes.Progress.route) { ProgressScreen() }
        composable(NavRoutes.Settings.route) { SettingsScreen() }
        composable(NavRoutes.Extra.route) { ExtraScreen() }
        composable(NavRoutes.Profile.route) {
            ProfileScreen(navController = navController, onLogout = onLogout)
        } }
    }


