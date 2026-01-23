package com.example.playlist_maker_android_nikolotovayulia.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.playlist_maker_android_nikolotovayulia.data.local.dao.PlaylistDao
import com.example.playlist_maker_android_nikolotovayulia.data.local.dao.TrackDao
import com.example.playlist_maker_android_nikolotovayulia.data.local.entity.PlaylistEntity
import com.example.playlist_maker_android_nikolotovayulia.data.local.entity.TrackEntity

@Database(
    entities = [TrackEntity::class, PlaylistEntity::class],
    version = 8,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "playlist_maker_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}
