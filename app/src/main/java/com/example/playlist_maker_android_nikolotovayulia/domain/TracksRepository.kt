package com.example.playlist_maker_android_nikolotovayulia.domain

import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
    suspend fun getAllTracks(): List<Track>
}
