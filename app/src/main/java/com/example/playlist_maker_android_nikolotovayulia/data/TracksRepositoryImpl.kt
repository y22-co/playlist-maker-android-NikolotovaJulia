package com.example.playlist_maker_android_nikolotovayulia.data

import com.example.playlist_maker_android_nikolotovayulia.domain.TracksRepository
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import com.example.playlist_maker_android_nikolotovayulia.data.itunes.toDomain
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.playlist_maker_android_nikolotovayulia.data.itunes.ITunesApi
class TracksRepositoryImpl(
    private val scope: CoroutineScope
) : TracksRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(ITunesApi::class.java)
    private val database = DatabaseHolder.get(scope)


    override suspend fun searchTracks(expression: String): List<Track> {
        return database.searchTracks(expression)
    }

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

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return database.getTrackByNameAndArtist(track)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        database.insertTrack(track.copy(playlistId = playlistId))
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        database.insertTrack(track.copy(playlistId = 0))
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        database.insertTrack(track.copy(favorite = isFavorite))
    }

    override fun getTrackById(id: Long): Track? = database.getTrackById(id)

    override fun deleteTracksByPlaylistId(playlistId: Long) {
        database.deleteTracksByPlaylistId(playlistId)
    }
}
