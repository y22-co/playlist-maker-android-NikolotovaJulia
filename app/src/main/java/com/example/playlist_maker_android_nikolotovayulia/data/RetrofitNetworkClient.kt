package com.example.playlist_maker_android_nikolotovayulia.data

class RetrofitNetworkClient(
    private val storage: Storage
) : NetworkClient {

    override fun doRequest(dto: Any): Any {
        return when (dto) {
            is TracksSearchRequest -> storage.search(dto)
            else -> error("Unknown request type")
        }
    }
}
