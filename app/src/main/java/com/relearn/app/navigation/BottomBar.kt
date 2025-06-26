package com.relearn.app.navigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.List

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

data class BottomNavItem(
    val label: String,
    val screen: Screen,
    val icon: ImageVector
)

@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String?
) {
    val items = listOf(
        BottomNavItem("Home", Screen.Home, Icons.Filled.Home),
        BottomNavItem("Journal", Screen.Journal, Icons.Filled.Edit),
        BottomNavItem("Progress", Screen.Progress, Icons.Filled.BarChart),
        BottomNavItem("Settings", Screen.Settings, Icons.Filled.Settings),
        BottomNavItem("Offline", Screen.OfflineHabits, Icons.Filled.List)
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    if (currentRoute != item.screen.route) {
                        navController.navigate(item.screen.route) {
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
