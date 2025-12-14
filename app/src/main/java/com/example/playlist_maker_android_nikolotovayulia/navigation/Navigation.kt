package com.example.playlist_maker_android_nikolotovayulia.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.MainScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.SearchScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.SettingsScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.FavoritesScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.PlaylistsScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.NewPlaylistScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.PlaylistScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.screens.TrackDetailsScreen
import com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel.PlaylistViewModel

enum class Screen { Main, Search, Settings, Playlists, Favorites, NewPlaylist, TrackDetails, Playlist }


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
                navigateToPlaylist = { id ->
                    navController.navigate("${Screen.Playlist.name}/$id")
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

        composable(
            route = "${Screen.TrackDetails.name}/{trackId}",
            arguments = listOf(navArgument("trackId") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("trackId") ?: 0L
            TrackDetailsScreen(
                trackId = id,
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "${Screen.Playlist.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            val playlistsVm: PlaylistViewModel = viewModel()

            val playlists by playlistsVm.playlists.collectAsState(initial = emptyList())
            val playlist = playlists.firstOrNull { it.id == id }

            PlaylistScreen(
                playlist = playlist,
                navigateBack = { navController.popBackStack() }
            )
        }

    }
}
