package com.example.playlist_maker_android_nikolotovayulia.data

import com.example.playlist_maker_android_nikolotovayulia.data.dto.TrackDto

data class TracksSearchResponse(
    val resultCode: Int,
    val results: List<TrackDto>
)
