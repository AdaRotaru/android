package com.relearn.app.navigation.MainNav

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Journal : NavRoutes("journal")
    object Progress : NavRoutes("progress")
    object Settings : NavRoutes("settings")
    object Extra : NavRoutes("extra")
    object Profile : NavRoutes("profile")
}