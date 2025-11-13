package com.example.playlist_maker_android_nikolotovayulia.data

import com.example.playlist_maker_android_nikolotovayulia.domain.dto.TrackDto

class Storage {
    private val listTracks = listOf(
        TrackDto(1L, "Владивосток 2000", "Мумий Троль", 158000, favorite = false),
        TrackDto(2L, "Группа крови", "Кино", 283000, favorite = true),
        TrackDto(3L, "Не смотри назад", "Ария", 312000, favorite = false),
        TrackDto(4L, "Звезда по имени Солнце", "Кино", 225000, favorite = false),
        TrackDto(5L, "Лондон", "Аквариум", 272000, favorite = false),
        TrackDto(6L, "На заре", "Альянс", 230000, favorite = false),
        TrackDto(7L, "Перемен", "Кино", 296000, favorite = true),
        TrackDto(8L, "Розовый фламинго", "Сплин", 195000, favorite = false),
        TrackDto(9L, "Танцевать", "Мельница", 222000, favorite = false),
        TrackDto(10L, "Чёрный бумер", "Серега", 241000, favorite = false)
    )

    fun search(request: String): List<TrackDto> {
        val q = request.trim().lowercase()
        if (q.isEmpty()) return emptyList()
        return listTracks.filter {
            it.trackName.lowercase().contains(q) || it.artistName.lowercase().contains(q)
        }
    }
}
