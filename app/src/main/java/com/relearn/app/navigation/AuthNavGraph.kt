package com.relearn.app.navigation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.relearn.app.feature.auth.AuthViewModel
import com.relearn.app.feature.auth.LoginScreen
import com.relearn.app.feature.auth.ui.HabitsSelectionScreen
import com.relearn.app.feature.auth.ui.RegisterScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    onLoginSuccess: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var tempEmail by remember { mutableStateOf("") }
    var tempPassword by remember { mutableStateOf("") }
    var tempFirstName by remember { mutableStateOf("") }
    var tempLastName by remember { mutableStateOf("") }
    var tempGender by remember { mutableStateOf("F") }

    AnimatedNavHost(
        navController = navController,
        startDestination = "register1",
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
    ) {
        composable("register1") {
            RegisterScreen(
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
                    preferences = selectedHabits,
                    onResult = { success, _ ->
                        if (success) {
                            navController.navigate("login") {
                                popUpTo("register1") { inclusive = true }
                            }
                        }
                    }
                )
            })
        }

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    onLoginSuccess() //  ieșim din AppNavGraph și trecem în aplicația principală
                },
                onNavigateToRegister = {
                    navController.navigate("register1") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
    }
}
