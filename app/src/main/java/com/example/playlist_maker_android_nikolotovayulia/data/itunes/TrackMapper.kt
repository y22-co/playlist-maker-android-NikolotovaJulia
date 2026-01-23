package com.example.playlist_maker_android_nikolotovayulia.data.itunes

import android.annotation.SuppressLint
import com.example.playlist_maker_android_nikolotovayulia.domain.dto.TrackDto
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track

fun TrackDto.toDomain(): Track? {
    val id = trackId ?: return null
    val name = trackName ?: return null
    val artist = artistName ?: return null
    val durationMillis = trackTimeMillis ?: 0L
    val minutes = (durationMillis / 1000) / 60
    val seconds = (durationMillis / 1000) % 60
    val timeString = String.format("%02d:%02d", minutes, seconds)

    return Track(
        trackName = name,
        artistName = artist,
        trackTime = timeString,
        artworkUrl100 = artworkUrl100,
        playlistId = 0,
        id = id,
        favorite = false
    )
}
