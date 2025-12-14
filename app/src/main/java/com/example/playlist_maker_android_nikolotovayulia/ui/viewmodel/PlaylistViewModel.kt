package com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_nikolotovayulia.App
import com.example.playlist_maker_android_nikolotovayulia.data.PlaylistsRepositoryImpl
import com.example.playlist_maker_android_nikolotovayulia.data.TracksRepositoryImpl
import com.example.playlist_maker_android_nikolotovayulia.domain.PlaylistsRepository
import com.example.playlist_maker_android_nikolotovayulia.domain.TracksRepository
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Playlist
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository = PlaylistsRepositoryImpl(App.instance),
    private val tracksRepository: TracksRepository = TracksRepositoryImpl(App.instance)
) : ViewModel() {

    val playlists: Flow<List<Playlist>> = playlistsRepository.getAllPlaylists()
    val favoriteList: Flow<List<Track>> = tracksRepository.getFavoriteTracks()
    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack = _currentTrack.asStateFlow()
    private val _currentPlaylistTracks = MutableStateFlow<List<Track>>(emptyList())
    val currentPlaylistTracks = _currentPlaylistTracks.asStateFlow()

    fun loadTrack(trackId: Long) {
        viewModelScope.launch {
            _currentTrack.value = tracksRepository.getTrackById(trackId)
        }
    }
    fun getTrackById(id: Long): Track? = runBlocking {
        tracksRepository.getTrackById(id)
    }

    fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        viewModelScope.launch {
            tracksRepository.insertTrackToPlaylist(track, playlistId)
        }
    }

    fun toggleFavorite(track: Track, isFavorite: Boolean) {
        viewModelScope.launch {
            tracksRepository.updateTrackFavoriteStatus(track, isFavorite)
            _currentTrack.value = tracksRepository.getTrackById(track.id)
        }
    }

    fun insertNewPlaylist(name: String, description: String) {
        viewModelScope.launch {
            playlistsRepository.addNewPlaylist(name, description)
        }
    }
    fun deletePlaylist(playlistId: Long) {
        viewModelScope.launch {
            playlistsRepository.deletePlaylist(playlistId)
        }
    }

    fun removeFromFavorites(track: Track) {
        viewModelScope.launch {
            tracksRepository.updateTrackFavoriteStatus(track, isFavorite = false)
        }
    }
    fun loadPlaylistTracks(playlistId: Long) {
        viewModelScope.launch {
            tracksRepository
                .getTracksByPlaylistId(playlistId)
                .collect { list ->
                    _currentPlaylistTracks.value = list
                }
        }
    }
    fun removeTrackFromPlaylist(track: Track) {
        viewModelScope.launch {
            tracksRepository.deleteTrackFromPlaylist(track)
        }
    }
}
