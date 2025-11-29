package com.example.playlist_maker_android_nikolotovayulia.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.MainScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.SearchScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.SettingsScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.FavoritesScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.PlaylistsScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.NewPlaylistScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.TrackDetailsScreen

enum class Screen {
    Main, Search, Settings, Playlists, Favorites, NewPlaylist, TrackDetails
}

@Composable
fun PlaylistHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Main.name) {

        composable(Screen.Main.name) {
            MainScreen(
                onNavigateToSearch = { navController.navigate(Screen.Search.name) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.name) },
                onNavigateToPlaylists = { navController.navigate(Screen.Playlists.name) },
                onNavigateToFavorites = { navController.navigate(Screen.Favorites.name) }
            )
        }

        composable(Screen.Search.name) {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                navController = navController
            )
        }

        composable(Screen.Settings.name) {
            SettingsScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(Screen.Playlists.name) {
            PlaylistsScreen(
                addNewPlaylist = { navController.navigate(Screen.NewPlaylist.name) },
                navigateToPlaylist = { playlistId ->
                    // Здесь позже добавим переход на экран деталей плейлиста
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.NewPlaylist.name) {
            NewPlaylistScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Favorites.name) {
            FavoritesScreen(
                onNavigateBack = { navController.popBackStack() },
                onTrackClick = { track ->
                    navController.navigate("${Screen.TrackDetails.name}/${track.id}")
                }
            )
        }

        composable("${Screen.TrackDetails.name}/{trackId}") { backStackEntry ->
            val trackId = backStackEntry.arguments?.getString("trackId")?.toLongOrNull()
            if (trackId != null) {
                TrackDetailsScreen(
                    trackId = trackId,
                    onNavigateBack = { navController.popBackStack() }
                )
            } else {
                navController.popBackStack()
            }
        }
    }
}
