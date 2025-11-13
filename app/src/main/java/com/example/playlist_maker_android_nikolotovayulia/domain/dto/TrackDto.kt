package com.example.playlist_maker_android_nikolotovayulia.domain.dto

data class TrackDto(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val favorite: Boolean
)