package com.example.playlist_maker_android_nikolotovayulia.creator

import com.example.playlist_maker_android_nikolotovayulia.data.RetrofitNetworkClient
import com.example.playlist_maker_android_nikolotovayulia.data.Storage
import com.example.playlist_maker_android_nikolotovayulia.data.TracksRepositoryImpl
import com.example.playlist_maker_android_nikolotovayulia.domain.TracksRepository

object   Creator {
    fun getTracksRepository(): TracksRepository {
        val storage = Storage()
        val networkClient = RetrofitNetworkClient(storage)
        return TracksRepositoryImpl(networkClient)
    }
}
