package com.example.playlist_maker_android_nikolotovayulia.data

import com.example.playlist_maker_android_nikolotovayulia.domain.TracksRepository
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track

class TracksRepositoryImpl(
    private val networkClient: NetworkClient
) : TracksRepository {

    override fun searchTracks(expression: String): List<Track> {
        if (expression.isBlank()) return emptyList()

        val response = networkClient.doRequest(
            TracksSearchRequest(expression)
        ) as TracksSearchResponse

        if (response.resultCode != 200) return emptyList()

        return response.results.map { dto ->
            Track(
                id = dto.trackId,
                trackName = dto.trackName,
                artistName = dto.artistName,
                trackTimeMillis = dto.trackTimeMillis
            )
        }
    }
}
