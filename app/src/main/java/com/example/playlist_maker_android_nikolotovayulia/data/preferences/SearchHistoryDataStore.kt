package com.example.playlist_maker_android_nikolotovayulia.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.searchHistoryDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "search_history_prefs"
)
