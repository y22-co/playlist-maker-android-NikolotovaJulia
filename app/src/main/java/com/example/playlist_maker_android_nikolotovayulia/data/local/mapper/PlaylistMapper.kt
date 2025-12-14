package com.example.playlist_maker_android_nikolotovayulia.data.local.mapper

import com.example.playlist_maker_android_nikolotovayulia.data.local.entity.PlaylistEntity
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Playlist

fun PlaylistEntity.toDomain(tracks: List<com.example.playlist_maker_android_nikolotovayulia.domain.models.Track>): Playlist =
    Playlist(
        id = id,
        name = name,
        description = description,
        tracks = tracks
    )

fun Playlist.toEntity(): PlaylistEntity =
    PlaylistEntity(
        id = id,
        name = name,
        description = description
    )
