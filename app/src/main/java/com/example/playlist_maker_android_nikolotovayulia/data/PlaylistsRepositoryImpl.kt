package com.example.playlist_maker_android_nikolotovayulia.data

import com.example.playlist_maker_android_nikolotovayulia.domain.PlaylistsRepository
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Playlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class PlaylistsRepositoryImpl(
    private val scope: CoroutineScope
) : PlaylistsRepository {

    private val database = DatabaseHolder.get(scope)

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> =
        database.getPlaylist(playlistId)

    override fun getAllPlaylists(): Flow<List<Playlist>> =
        database.getAllPlaylists()

    override suspend fun addNewPlaylist(name: String, description: String) {
        database.addNewPlaylist(name = name, description = description)
    }

    override suspend fun deletePlaylistById(id: Long) {
        database.deletePlaylistById(playlistId = id)
    }
}
