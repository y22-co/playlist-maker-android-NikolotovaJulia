package com.example.playlist_maker_android_nikolotovayulia.domain

import com.example.playlist_maker_android_nikolotovayulia.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun getAllPlaylists(): Flow<List<Playlist>>
    fun getPlaylist(playlistId: Long): Flow<Playlist?>
    suspend fun addNewPlaylist(
        name: String,
        description: String,
        coverImageUri: String? = null
    )
    suspend fun deletePlaylist(playlistId: Long)

}