package com.example.playlist_maker_android_nikolotovayulia.ui.state

import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track


sealed class SearchState {
    data object Initial : SearchState()
    data object Loading : SearchState()
    data class Success(val foundList: List<Track>) : SearchState()
    data object EmptyResult : SearchState()
    data class Error(val error: String?) : SearchState()
}
