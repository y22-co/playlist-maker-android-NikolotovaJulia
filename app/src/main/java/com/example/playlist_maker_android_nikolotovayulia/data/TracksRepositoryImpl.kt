package com.example.playlist_maker_android_nikolotovayulia.data

import android.content.Context
import com.example.playlist_maker_android_nikolotovayulia.data.itunes.ITunesApi
import com.example.playlist_maker_android_nikolotovayulia.data.itunes.toDomain
import com.example.playlist_maker_android_nikolotovayulia.data.local.AppDatabase
import com.example.playlist_maker_android_nikolotovayulia.data.local.mapper.toDomain
import com.example.playlist_maker_android_nikolotovayulia.data.local.mapper.toEntity
import com.example.playlist_maker_android_nikolotovayulia.domain.TracksRepository
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TracksRepositoryImpl(
    context: Context
) : TracksRepository {

    private val db = AppDatabase.get(context)
    private val trackDao = db.trackDao()

    // iTunes
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ITunesApi::class.java)

    // сеть
    override suspend fun searchRemoteTracks(expression: String): Result<List<Track>> {
        return try {
            if (expression.isBlank()) return Result.success(emptyList())
            val response = api.search(term = expression.trim())
            val list = response.results
                ?.mapNotNull { it.toDomain() }
                .orEmpty()
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // база
    override fun getFavoriteTracks(): Flow<List<Track>> =
        trackDao.getFavoriteTracks().map { list -> list.map { it.toDomain() } }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        val existing = trackDao.getTrackById(track.id)
        val base = existing?.toDomain() ?: track

        val updated = base.copy(playlistId = playlistId)
        trackDao.insertTrack(updated.toEntity())
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        val existing = trackDao.getTrackById(track.id)
        val base = existing?.toDomain() ?: track

        val updated = base.copy(playlistId = 0)
        trackDao.insertTrack(updated.toEntity())
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        val existing = trackDao.getTrackById(track.id)
        val base = existing?.toDomain() ?: track

        val updated = base.copy(favorite = isFavorite)
        trackDao.insertTrack(updated.toEntity())
    }

    override suspend fun getTrackById(id: Long): Track? =
        trackDao.getTrackById(id)?.toDomain()

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        trackDao.deleteTracksByPlaylistId(playlistId)
    }

    override fun getTracksByPlaylistId(playlistId: Long): Flow<List<Track>> =
        trackDao.getTracksByPlaylistId(playlistId).map { list -> list.map { it.toDomain() } }

    override suspend fun searchTracks(expression: String): List<Track> = emptyList()
}
