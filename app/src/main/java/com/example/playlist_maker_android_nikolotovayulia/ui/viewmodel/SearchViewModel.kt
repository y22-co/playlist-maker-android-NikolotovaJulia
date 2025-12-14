package com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_nikolotovayulia.App
import com.example.playlist_maker_android_nikolotovayulia.data.SearchHistoryRepositoryImpl
import com.example.playlist_maker_android_nikolotovayulia.data.TracksRepositoryImpl
import com.example.playlist_maker_android_nikolotovayulia.domain.SearchHistoryRepository
import com.example.playlist_maker_android_nikolotovayulia.domain.TracksRepository
import com.example.playlist_maker_android_nikolotovayulia.ui.state.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class SearchViewModel(
    private val searchHistoryRepository: SearchHistoryRepository = SearchHistoryRepositoryImpl(App.instance)
) : ViewModel() {

    private val tracksRepository: TracksRepository = TracksRepositoryImpl(App.instance)

    private val _allTracksScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val allTracksScreenState: StateFlow<SearchState> = _allTracksScreenState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private var searchJob: Job? = null
    private var lastQuery: String = ""
    private val _history = MutableStateFlow<List<String>>(emptyList())
    val history: StateFlow<List<String>> = _history.asStateFlow()

    init {
        viewModelScope.launch {
            _history.value = searchHistoryRepository.getEntries().take(3)
        }
    }

    private fun saveQueryToHistory() {
        val q = _query.value.trim()
        if (q.isEmpty()) return
        searchHistoryRepository.addEntry(q)
        viewModelScope.launch {
            _history.value = searchHistoryRepository.getEntries().take(3)
        }
    }

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
                    onFailure = {
                        _allTracksScreenState.value = SearchState.Error
                    }
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _allTracksScreenState.value = SearchState.Error
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
