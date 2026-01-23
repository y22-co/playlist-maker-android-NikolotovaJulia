package com.example.playlist_maker_android_nikolotovayulia.data.local.mapper

import com.example.playlist_maker_android_nikolotovayulia.data.local.entity.PlaylistEntity
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Playlist
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track

fun PlaylistEntity.toDomain(
    tracks: List<Track>
): Playlist =
    Playlist(
        id = id,
        name = name,
        description = description,
        coverImageUri = coverImageUri,
        tracks = tracks,
        tracksCount = tracksCount
    )

fun Playlist.toEntity(): PlaylistEntity =
    PlaylistEntity(
        id = id,
        name = name,
        description = description,
        coverImageUri = coverImageUri,
        tracksCount = tracksCount
    )

