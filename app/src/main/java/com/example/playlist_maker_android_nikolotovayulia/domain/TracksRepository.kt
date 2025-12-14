package com.example.playlist_maker_android_nikolotovayulia.domain

import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    // сеть
    suspend fun searchRemoteTracks(expression: String): Result<List<Track>>

    // база
    suspend fun searchTracks(expression: String): List<Track>
    fun getFavoriteTracks(): Flow<List<Track>>
    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)
    suspend fun deleteTrackFromPlaylist(track: Track)
    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)
    suspend fun getTrackById(id: Long): Track?
    suspend fun deleteTracksByPlaylistId(playlistId: Long)
}
