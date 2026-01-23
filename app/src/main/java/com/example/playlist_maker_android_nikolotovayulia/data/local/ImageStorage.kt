package com.example.playlist_maker_android_nikolotovayulia.data.local

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun savePlaylistCoverToInternalStorage(
    context: Context,
    sourceUri: Uri,
    playlistId: Long = System.currentTimeMillis()
): String? {
    val contentResolver = context.contentResolver
    val input = contentResolver.openInputStream(sourceUri) ?: return null

    val dir = File(context.filesDir, "playlist_covers")
    if (!dir.exists()) dir.mkdirs()

    val file = File(dir, "playlist_cover_$playlistId.jpg")

    input.use { inStream ->
        FileOutputStream(file).use { outStream ->
            inStream.copyTo(outStream)
        }
    }

    return file.toURI().toString()
}
