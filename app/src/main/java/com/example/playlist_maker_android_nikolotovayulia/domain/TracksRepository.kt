package com.example.playlist_maker_android_nikolotovayulia.domain

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
    suspend fun getAllTracks(): List<Track>
}
