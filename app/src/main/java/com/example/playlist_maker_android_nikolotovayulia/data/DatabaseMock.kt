package com.example.playlist_maker_android_nikolotovayulia.data

import com.example.playlist_maker_android_nikolotovayulia.domain.models.Playlist
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track
import com.example.playlist_maker_android_nikolotovayulia.domain.models.NO_PLAYLIST
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DatabaseMock(
    private val scope: CoroutineScope,
) {
    private val historyList = mutableListOf<String>()
    private val _historyUpdates = MutableSharedFlow<Unit>()

    private val playlists = mutableListOf<Playlist>()

    // сразу заполняем треками
    private val tracks = mutableListOf(
        Track("Владивосток 2000", "Мумий Троль", "02:38", playlistId = NO_PLAYLIST, id = 1L, favorite = false),
        Track("Группа крови", "Кино", "04:43", playlistId = NO_PLAYLIST, id = 2L, favorite = false),
        Track("Не смотри назад", "Ария", "05:12", playlistId = NO_PLAYLIST, id = 3L, favorite = false),
        Track("Звезда по имени Солнце", "Кино", "03:45", playlistId = NO_PLAYLIST, id = 4L, favorite = false),
        Track("Лондон", "Аквариум", "04:32", playlistId = NO_PLAYLIST, id = 5L, favorite = false),
        Track("На заре", "Альянс", "03:50", playlistId = NO_PLAYLIST, id = 6L, favorite = false),
        Track("Перемен", "Кино", "04:56", playlistId = NO_PLAYLIST, id = 7L, favorite = false),
        Track("Розовый фламинго", "Сплин", "03:15", playlistId = NO_PLAYLIST, id = 8L, favorite = false),
        Track("Танцевать", "Мельница", "03:42", playlistId = NO_PLAYLIST, id = 9L, favorite = false),
        Track("Чёрный бумер", "Серега", "04:01", playlistId = NO_PLAYLIST, id = 10L, favorite = false)
    )

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
        val filteredPlaylists = mutableListOf<Playlist>()
        playlists.forEach { playlist ->
            val playlistTracks = tracks.filter { track ->
                track.playlistId == playlist.id
            }
            filteredPlaylists.add(playlist.copy(tracks = playlistTracks))
        }
        emit(filteredPlaylists.toList())
    }

    fun getPlaylist(id: Long): Flow<Playlist?> = flow {
        emit(playlists.find { it.id == id })
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
    }

    fun deleteTrackFromPlaylist(trackId: Long) {
        tracks.removeIf { it.id == trackId }
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
        tracks.removeIf { it.playlistId == playlistId }
    }

    fun searchTracks(expression: String): List<Track> {
        if (expression.isBlank()) return emptyList()
        val q = expression.trim()
        return tracks.filter {
            it.trackName.contains(q, ignoreCase = true) ||
                    it.artistName.contains(q, ignoreCase = true)
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
}
