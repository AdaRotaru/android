package com.relearn.app.navigation

import android.view.View
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.relearn.app.feature.Journal.JournalScreen
import com.relearn.app.feature.Progress.ProgressScreen
import com.relearn.app.feature.Settings.SettingsScreen
import com.relearn.app.feature.auth.AuthViewModel
import com.relearn.app.feature.auth.LoginScreen
import com.relearn.app.feature.auth.ui.HabitsSelectionScreen
import com.relearn.app.feature.auth.ui.RegisterScreen1
import com.relearn.app.feature.challenges.ChallengeViewModel
import com.relearn.app.feature.challenges.ui.ChallengeListScreen
import com.relearn.app.feature.offline.data.OfflineHabitsFragment

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Journal : Screen("journal")
    object Progress : Screen("progress")
    object Settings : Screen("settings")
    object OfflineHabits : Screen("offline_habits")
}


@Composable
fun AppNav(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "register1",
        modifier = modifier
    ) {
        // Autentificare
        composable("register1") {
            RegisterScreen1(
                onNext = { firstName, lastName, gender, email, password ->
                    navController.currentBackStackEntry?.savedStateHandle?.apply {
                        set("firstName", firstName)
                        set("lastName", lastName)
                        set("gender", gender)
                        set("email", email)
                        set("password", password)
                    }
                    navController.navigate("register2")
                },
                onLoginClick = {
                    navController.navigate("login") {
                        popUpTo("register1") { inclusive = true }
                    }
                }
            )
        }

        composable("register2") {
            val backStackEntry = navController.previousBackStackEntry
            val firstName = backStackEntry?.savedStateHandle?.get<String>("firstName") ?: ""
            val lastName = backStackEntry?.savedStateHandle?.get<String>("lastName") ?: ""
            val gender = backStackEntry?.savedStateHandle?.get<String>("gender") ?: ""
            val email = backStackEntry?.savedStateHandle?.get<String>("email") ?: ""
            val password = backStackEntry?.savedStateHandle?.get<String>("password") ?: ""

            HabitsSelectionScreen(onContinue = { selectedHabits ->
                authViewModel.register(
                    email = email,
                    password = password,
                    firstName = firstName,
                    lastName = lastName,
                    gender = gender,
                    preferences = selectedHabits
                )
                navController.navigate("login") {
                    popUpTo("register1") { inclusive = true }
                }
            })
        }

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register1") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // Ecrane principale
        composable(Screen.Home.route) {
            val viewModel: ChallengeViewModel = hiltViewModel()
            ChallengeListScreen(viewModel = viewModel)
        }

        composable(Screen.Journal.route) {
            JournalScreen()
        }

        composable(Screen.Progress.route) {
            ProgressScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }

        composable(Screen.OfflineHabits.route) {
            AndroidView(
                factory = { context ->
                    val fragmentContainerView = FragmentContainerView(context).apply {
                        id = View.generateViewId()
                    }
                    val fragment = OfflineHabitsFragment()
                    val activity = context as FragmentActivity
                    activity.supportFragmentManager.beginTransaction()
                        .replace(fragmentContainerView.id, fragment)
                        .commitNow()
                    fragmentContainerView
                }
            )
        }
    }
}
