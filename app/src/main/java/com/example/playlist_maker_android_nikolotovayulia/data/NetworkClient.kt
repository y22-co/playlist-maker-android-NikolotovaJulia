package com.example.playlist_maker_android_nikolotovayulia.data

interface NetworkClient {
    fun doRequest(dto: Any): Any
}
