package com.example.playlist_maker_android_nikolotovayulia.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String?,
    val playlistId: Long,
    val favorite: Boolean
)
