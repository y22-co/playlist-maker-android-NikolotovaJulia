package com.example.playlist_maker_android_nikolotovayulia.ui.state

import com.example.playlist_maker_android_nikolotovayulia.domain.Track

sealed class SearchState {
    object Initial : SearchState()
    object Loading : SearchState()
    data class Success(val foundList: List<Track>) : SearchState()
    data class Error(val error: String) : SearchState()
}
