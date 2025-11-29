package com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_nikolotovayulia.creator.Creator
import com.example.playlist_maker_android_nikolotovayulia.domain.TracksRepository
import com.example.playlist_maker_android_nikolotovayulia.ui.state.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track


class SearchViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {

    private val _allTracksScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val allTracksScreenState: StateFlow<SearchState> = _allTracksScreenState.asStateFlow()

    private var searchJob: kotlinx.coroutines.Job? = null

    fun search(query: String) {
        if (query.isBlank()) {
            _allTracksScreenState.update { SearchState.Initial }
            return
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            _allTracksScreenState.update { SearchState.Loading }
            try {
                kotlinx.coroutines.delay(350)
                val list = tracksRepository.searchTracks(query)
                _allTracksScreenState.update { SearchState.Success(foundList = list) }
            } catch (e: Exception) {
                _allTracksScreenState.update { SearchState.Error(e.message ?: "Unknown error") }
            }
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(Creator.getTracksRepository()) as T
                }
            }
    }
}
