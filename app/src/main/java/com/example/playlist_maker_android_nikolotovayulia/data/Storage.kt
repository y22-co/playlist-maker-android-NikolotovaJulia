package com.example.playlist_maker_android_nikolotovayulia.data

import com.example.playlist_maker_android_nikolotovayulia.data.dto.TrackDto

class Storage {

    private val listTracks = listOf(
        TrackDto(1L, "Владивосток 2000", "Мумий Троль", 158000),
        TrackDto(2L, "Группа крови", "Кино", 283000),
        TrackDto(3L, "Не смотри назад", "Ария", 312000),
        TrackDto(4L, "Звезда по имени Солнце", "Кино", 225000),
        TrackDto(5L, "Лондон", "Аквариум", 272000),
        TrackDto(6L, "На заре", "Альянс", 230000),
        TrackDto(7L, "Перемен", "Кино", 296000),
        TrackDto(8L, "Розовый фламинго", "Сплин", 195000),
        TrackDto(9L, "Танцевать", "Мельница", 222000),
        TrackDto(10L, "Чёрный бумер", "Серега", 241000)
    )

    fun search(request: TracksSearchRequest): TracksSearchResponse {
        val found = listTracks.filter { dto ->
            dto.trackName.contains(request.expression, ignoreCase = true) ||
                    dto.artistName.contains(request.expression, ignoreCase = true)
        }
        return TracksSearchResponse(
            resultCode = 200,
            results = found
        )
    }
}
