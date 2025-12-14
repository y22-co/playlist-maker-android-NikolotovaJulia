package com.example.playlist_maker_android_nikolotovayulia.data.local.dao

import androidx.room.*
import com.example.playlist_maker_android_nikolotovayulia.data.local.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Query("SELECT * FROM playlists")
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>

    @Query("SELECT * FROM playlists WHERE id = :id LIMIT 1")
    fun getPlaylistById(id: Long): Flow<PlaylistEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Query("DELETE FROM playlists WHERE id = :playlistId")
    suspend fun deletePlaylistById(playlistId: Long)

    @Query("UPDATE playlists SET tracksCount = tracksCount + 1 WHERE id = :playlistId")
    suspend fun incrementTracksCount(playlistId: Long)

    @Query("UPDATE playlists SET tracksCount = tracksCount - 1 WHERE id = :playlistId AND tracksCount > 0")
    suspend fun decrementTracksCount(playlistId: Long)
}
