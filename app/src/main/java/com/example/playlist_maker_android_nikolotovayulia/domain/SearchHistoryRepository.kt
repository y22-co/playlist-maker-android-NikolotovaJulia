package com.example.playlist_maker_android_nikolotovayulia.domain

interface SearchHistoryRepository {
    fun addEntry(word: String)
    suspend fun getEntries(): List<String>
}