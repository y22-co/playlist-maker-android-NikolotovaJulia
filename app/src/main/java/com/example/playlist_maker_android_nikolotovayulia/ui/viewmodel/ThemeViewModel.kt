package com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_nikolotovayulia.data.ThemePreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(app: Application): AndroidViewModel(app) {
    private val prefs = ThemePreferences(app)

    val darkTheme: StateFlow<Boolean> = prefs.darkThemeFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun setDarkTheme(enabled: Boolean) {
        viewModelScope.launch { prefs.setDarkTheme(enabled) }
    }
}
