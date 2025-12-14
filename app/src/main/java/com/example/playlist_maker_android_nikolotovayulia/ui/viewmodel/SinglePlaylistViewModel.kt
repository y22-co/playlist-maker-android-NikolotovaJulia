package com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_nikolotovayulia.domain.PlaylistsRepository
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Playlist
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SinglePlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val playlistId: Long
) : ViewModel() {

    val playlist: StateFlow<Playlist?> =
        playlistsRepository.getPlaylist(playlistId)
            .stateIn(viewModelScope, SharingStarted.Lazily, null)
}
