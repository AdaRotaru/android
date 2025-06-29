package com.relearn.app.navigation.mainnav


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.relearn.app.Screens.ExtraScreen
import com.relearn.app.Screens.JournalScreen
import com.relearn.app.Screens.ProfileScreen
import com.relearn.app.Screens.ProgressScreen
import com.relearn.app.Screens.SettingsScreen
import com.relearn.app.feature.HOME.ui.HomeScreen
import com.relearn.app.feature.HOME.viewmodel.ChallengeViewModel
import com.relearn.app.feature.HOME.viewmodel.CheckInViewModel
import com.relearn.app.feature.HOME.viewmodel.ToDoViewModel


@Composable
fun MainNav(navController: NavHostController, onLogout: () -> Unit) {
    val checkInViewModel: CheckInViewModel = hiltViewModel()
    val challengeViewModel: ChallengeViewModel = hiltViewModel()
    val toDoViewModel: ToDoViewModel = hiltViewModel()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        NavHost(
            navController = navController,
            startDestination = NavRoutes.Home.route
        ) {
            composable(NavRoutes.Home.route) {
                HomeScreen(
                    checkInViewModel = checkInViewModel,
                    challengeViewModel = challengeViewModel,
                    toDoViewModel = toDoViewModel,
                    userId = userId
                )
            }
            composable(NavRoutes.Journal.route) { JournalScreen() }
            composable(NavRoutes.Progress.route) { ProgressScreen() }
            composable(NavRoutes.Settings.route) { SettingsScreen() }
            composable(NavRoutes.Extra.route) { ExtraScreen() }
            composable(NavRoutes.Profile.route) {
                ProfileScreen(navController = navController, onLogout = onLogout)
            }
        }
    }


