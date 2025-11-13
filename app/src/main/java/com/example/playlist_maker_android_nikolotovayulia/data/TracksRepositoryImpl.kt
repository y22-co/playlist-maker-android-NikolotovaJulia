package com.example.playlist_maker_android_nikolotovayulia.data

import com.example.playlist_maker_android_nikolotovayulia.domain.NetworkClient
import com.example.playlist_maker_android_nikolotovayulia.domain.TracksRepository
import com.example.playlist_maker_android_nikolotovayulia.domain.TracksSearchRequest
import com.example.playlist_maker_android_nikolotovayulia.domain.TracksSearchResponse
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track
import kotlinx.coroutines.delay
import kotlin.collections.map
import com.example.playlist_maker_android_nikolotovayulia.domain.models.NO_PLAYLIST


class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        if (expression.isBlank()) return emptyList()
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(300) // легкий дебаунс имитации сети
        return if (response.resultCode == 200) {
            (response as TracksSearchResponse).results.map { dto ->
                val seconds = dto.trackTimeMillis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d:%02d".format(minutes, seconds % 60)
                Track(
                    trackName = dto.trackName,
                    artistName = dto.artistName,
                    trackTime = trackTime,
                    playlistId = NO_PLAYLIST, // пока нет реальной привязки
                    id = dto.id,
                    favorite = dto.favorite
                )
            }
        } else {
            emptyList()
        }
    }

    override suspend fun getAllTracks(): List<Track> {
        // По требованиям поиска пустая строка не должна возвращать все треки,
        // поэтому здесь можно вернуть пусто или предусмотреть отдельный кейс списка.
        return emptyList()
    }
}
