package com.example.playlist_maker_android_nikolotovayulia.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope = CoroutineScope(
        CoroutineName("search-history-preferences") + SupervisorJob()
    )
) {
    private val preferencesKey = stringPreferencesKey("search_history")

    fun addEntry(word: String) {
        if (word.isEmpty()) return

        coroutineScope.launch {
            dataStore.edit { preferences ->
                val historyString = preferences[preferencesKey].orEmpty()
                val history = if (historyString.isNotEmpty()) {
                    historyString.split(SEPARATOR).toMutableList()
                } else {
                    mutableListOf()
                }

                history.remove(word)
                history.add(0, word)

                val subList = history.take(MAX_ENTRIES)
                preferences[preferencesKey] = subList.joinToString(SEPARATOR)
            }
        }
    }

    suspend fun getEntries(): List<String> {
        val prefs = dataStore.data.first()
        val historyString = prefs[preferencesKey].orEmpty()
        if (historyString.isEmpty()) return emptyList()
        return historyString.split(SEPARATOR)
    }

    companion object {
        private const val MAX_ENTRIES = 10
        private const val SEPARATOR = ","
    }
}
