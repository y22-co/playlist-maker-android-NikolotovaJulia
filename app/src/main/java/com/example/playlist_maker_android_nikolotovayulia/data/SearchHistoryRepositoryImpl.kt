package com.example.playlist_maker_android_nikolotovayulia.data

import android.content.Context
import com.example.playlist_maker_android_nikolotovayulia.data.preferences.SearchHistoryPreferences
import com.example.playlist_maker_android_nikolotovayulia.data.preferences.searchHistoryDataStore
import com.example.playlist_maker_android_nikolotovayulia.domain.SearchHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchHistoryRepositoryImpl(
    context: Context
) : SearchHistoryRepository {

    private val prefs = SearchHistoryPreferences(context.searchHistoryDataStore)

    override fun addEntry(word: String) {
        prefs.addEntry(word)
    }

    override suspend fun getEntries(): List<String> =
        withContext(Dispatchers.IO) {
            prefs.getEntries()
        }
}
