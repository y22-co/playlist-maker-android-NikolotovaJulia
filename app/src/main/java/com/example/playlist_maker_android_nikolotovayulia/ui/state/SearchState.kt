package com.example.playlist_maker_android_nikolotovayulia.ui.state

import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track

sealed class SearchState {
    object Initial : SearchState()
    object Loading : SearchState()
    object EmptyResult : SearchState()
    object Error : SearchState()
    data class Success(val tracks: List<Track>) : SearchState()
}
