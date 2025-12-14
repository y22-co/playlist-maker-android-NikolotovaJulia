package com.example.playlist_maker_android_nikolotovayulia.data.local.mapper

import com.example.playlist_maker_android_nikolotovayulia.data.local.entity.TrackEntity
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track

fun TrackEntity.toDomain(): Track =
    Track(
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        artworkUrl100 = artworkUrl100,
        playlistId = playlistId,
        id = id,
        favorite = favorite
    )

fun Track.toEntity(): TrackEntity =
    TrackEntity(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        artworkUrl100 = artworkUrl100,
        playlistId = playlistId,
        favorite = favorite
    )
