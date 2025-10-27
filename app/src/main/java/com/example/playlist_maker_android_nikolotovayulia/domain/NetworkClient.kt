package com.example.playlist_maker_android_nikolotovayulia.domain

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}
