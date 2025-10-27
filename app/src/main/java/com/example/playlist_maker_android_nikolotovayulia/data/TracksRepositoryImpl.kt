package com.example.playlist_maker_android_nikolotovayulia.data

import com.example.playlist_maker_android_nikolotovayulia.domain.*
import kotlinx.coroutines.delay

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1000)
        return if (response.resultCode == 200) {
            (response as TracksSearchResponse).results.map {
                val seconds = it.trackTimeMillis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d:%02d".format(minutes, seconds % 60)
                Track(it.trackName, it.artistName, trackTime)
            }
        } else {
            emptyList()
        }
    }

    override suspend fun getAllTracks(): List<Track> {
        delay(1000)
        val searchResponse = networkClient.doRequest(TracksSearchRequest(""))
        return if (searchResponse.resultCode == 200) {
            (searchResponse as TracksSearchResponse).results.map {
                val seconds = it.trackTimeMillis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d:%02d".format(minutes, seconds % 60)
                Track(it.trackName, it.artistName, trackTime)
            }
        } else emptyList()
    }
}
