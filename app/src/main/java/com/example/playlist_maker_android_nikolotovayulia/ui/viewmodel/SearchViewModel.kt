package com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_nikolotovayulia.data.TracksRepositoryImpl
import com.example.playlist_maker_android_nikolotovayulia.domain.TracksRepository
import com.example.playlist_maker_android_nikolotovayulia.ui.state.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val tracksRepository: TracksRepository = TracksRepositoryImpl(scope = viewModelScope)

    private val _allTracksScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val allTracksScreenState: StateFlow<SearchState> = _allTracksScreenState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private var searchJob: Job? = null
    private var lastQuery: String = ""

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery

        val q = newQuery.trim()
        if (q.isBlank()) {
            searchJob?.cancel()
            _allTracksScreenState.value = SearchState.Initial
            return
        }
        
        search()
    }

    fun search() {
        val q = _query.value.trim()
        if (q.isBlank()) {
            searchJob?.cancel()
            _allTracksScreenState.value = SearchState.Initial
            return
        }

        lastQuery = q
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                _allTracksScreenState.value = SearchState.Loading
                val result = tracksRepository.searchRemoteTracks(q)
                result.fold(
                    onSuccess = { list ->
                        _allTracksScreenState.value =
                            if (list.isEmpty()) SearchState.EmptyResult
                            else SearchState.Success(list)
                    },
                    onFailure = { e ->
                        _allTracksScreenState.value = SearchState.Error(e.message)
                    }
                )
            } catch (e: Exception) {
                _allTracksScreenState.value = SearchState.Error(e.message)
            }
        }
    }

    fun retryLastSearch() {
        if (lastQuery.isNotBlank()) {
            _query.value = lastQuery
            search()
        }
    }

    fun clearQuery() {
        _query.value = ""
        searchJob?.cancel()
        _allTracksScreenState.value = SearchState.Initial
    }
}
