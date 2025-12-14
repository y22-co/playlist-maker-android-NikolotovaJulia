package com.example.playlist_maker_android_nikolotovayulia.data

import android.content.Context
import com.example.playlist_maker_android_nikolotovayulia.data.local.AppDatabase
import com.example.playlist_maker_android_nikolotovayulia.data.local.entity.PlaylistEntity
import com.example.playlist_maker_android_nikolotovayulia.data.local.mapper.toDomain
import com.example.playlist_maker_android_nikolotovayulia.data.local.mapper.toEntity
import com.example.playlist_maker_android_nikolotovayulia.domain.PlaylistsRepository
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlin.collections.emptyList

class PlaylistsRepositoryImpl(
    context: Context
) : PlaylistsRepository {

    private val db = AppDatabase.get(context)
    private val playlistDao = db.playlistDao()
    private val trackDao = db.trackDao()

    override fun getAllPlaylists(): Flow<List<Playlist>> =
        playlistDao.getAllPlaylists().map { playlists ->
            playlists.map { entity ->
                val tracks = trackDao.getTracksByPlaylistId(entity.id)
                    .first()
                    .map { it.toDomain() }
                entity.toDomain(tracks)
            }
        }

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> =
        playlistDao.getPlaylistById(playlistId).map { entity ->
            if (entity == null) null
            else {
                val tracks = trackDao.getTracksByPlaylistId(entity.id)
                    .first()
                    .map { it.toDomain() }
                entity.toDomain(tracks)
            }
        }

    override suspend fun addNewPlaylist(name: String, description: String) {
        playlistDao.insertPlaylist(
            PlaylistEntity(
                name = name,
                description = description
            )
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        trackDao.deleteTracksByPlaylistId(id)
        playlistDao.deletePlaylistById(id)
    }
}
