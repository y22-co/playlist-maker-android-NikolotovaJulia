// navigation/Navigation.kt
package com.example.playlist_maker_android_nikolotovayulia.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.MainScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.SearchScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.SettingsScreen

enum class Screen { Main, Search, Settings }

@Composable
fun PlaylistHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.name
    ) {
        composable(Screen.Main.name) {
            MainScreen(
                onNavigateToSearch = { navController.navigate(Screen.Search.name) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.name) }
            )
        }
        composable(Screen.Search.name) {
            SearchScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Settings.name) {
            SettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
