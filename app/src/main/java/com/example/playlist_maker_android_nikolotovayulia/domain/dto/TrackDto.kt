package com.example.playlist_maker_android_nikolotovayulia.data.itunes

import com.google.gson.annotations.SerializedName

data class TrackDto(
    @SerializedName("trackId") val trackId: Long?,
    @SerializedName("trackName") val trackName: String?,
    @SerializedName("artistName") val artistName: String?,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Long?,
    @SerializedName("artworkUrl100") val artworkUrl100: String?
)

data class SearchResponseDto(
    @SerializedName("resultCount") val resultCount: Int?,
    @SerializedName("results") val results: List<TrackDto>?
)
