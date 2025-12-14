package com.example.playlist_maker_android_nikolotovayulia.data

import com.example.playlist_maker_android_nikolotovayulia.domain.models.Playlist
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DatabaseMock(
    private val scope: CoroutineScope,
) {
    private val historyList = mutableListOf<String>()
    private val _historyUpdates = MutableSharedFlow<Unit>()

    private val playlists = mutableListOf<Playlist>()
    private val tracks = mutableListOf<Track>()


    fun getHistory(): List<String> = historyList.toList()

    fun addToHistory(word: String) {
        historyList.add(word)
        notifyHistoryChanged()
    }

    private fun notifyHistoryChanged() {
        scope.launch(Dispatchers.IO) {
            _historyUpdates.emit(Unit)
        }
    }


    fun getAllPlaylists(): Flow<List<Playlist>> = flow {
        delay(500)
        val filteredPlaylists = playlists.map { playlist ->
            val playlistTracks = tracks.filter { track ->
                track.playlistId == playlist.id
            }
            playlist.copy(tracks = playlistTracks)
        }
        emit(filteredPlaylists)
    }

    fun getPlaylist(id: Long): Flow<Playlist?> = flow {
        val playlist = playlists.find { it.id == id }
        if (playlist == null) {
            emit(null)
        } else {
            val playlistTracks = tracks.filter { it.playlistId == playlist.id }
            emit(playlist.copy(tracks = playlistTracks))
        }
    }

    fun addNewPlaylist(name: String, description: String) {
        playlists.add(
            Playlist(
                id = playlists.size.toLong() + 1,
                name = name,
                description = description,
                tracks = emptyList()
            )
        )
    }

    fun deletePlaylistById(playlistId: Long) {
        playlists.removeIf { it.id == playlistId }
        tracks.replaceAll { track ->
            if (track.playlistId == playlistId) track.copy(playlistId = 0) else track
        }
    }

    fun deleteTrackFromPlaylist(trackId: Long) {
        tracks.replaceAll { track ->
            if (track.id == trackId) track.copy(playlistId = 0) else track
        }
    }

    fun getTrackByNameAndArtist(track: Track): Flow<Track?> = flow {
        emit(tracks.find { it.trackName == track.trackName && it.artistName == track.artistName })
    }

    fun insertTrack(track: Track) {

        tracks.removeIf { it.id == track.id }
        tracks.add(track)
    }

    fun getFavoriteTracks(): Flow<List<Track>> = flow {
        delay(300)
        val favorites = tracks.filter { it.favorite }
        emit(favorites)
    }

    fun getTrackById(id: Long): Track? = tracks.find { it.id == id }

    fun deleteTracksByPlaylistId(playlistId: Long) {
        tracks.replaceAll { track ->
            if (track.playlistId == playlistId) track.copy(playlistId = 0) else track
        }
    }


    fun searchTracks(expression: String): List<Track> {
        if (expression.isBlank()) return emptyList()
        val q = expression.trim()
        return tracks.filter {
            it.trackName.contains(q, ignoreCase = true) ||
                    it.artistName.contains(q, ignoreCase = true)
        }
    }
}

object DatabaseHolder {
    @Volatile
    private var instance: DatabaseMock? = null

    fun get(scope: CoroutineScope): DatabaseMock {
        return instance ?: synchronized(this) {
            instance ?: DatabaseMock(scope).also { instance = it }
        }
    }
}
