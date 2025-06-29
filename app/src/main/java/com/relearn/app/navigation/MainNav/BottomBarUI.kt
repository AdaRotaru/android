package com.relearn.app.navigation.MainNav



import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(NavRoutes.Home.route, Icons.Default.Home, "Home"),
        BottomNavItem(NavRoutes.Journal.route, Icons.Default.Edit, "Journal"),
        BottomNavItem(NavRoutes.Progress.route, Icons.Default.ShowChart, "Progress"),
        BottomNavItem(NavRoutes.Settings.route, Icons.Default.Settings, "Settings"),
        BottomNavItem(NavRoutes.Extra.route, Icons.Default.Star, "Extra")
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
