package com.example.appmusicplayer.model

import android.content.Context
import android.provider.MediaStore

class MusicRepository(private val context: Context) {
    fun getAllMusic(): List<MusicEntity> {
        val list = mutableListOf<MusicEntity>()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

        context.contentResolver.query(
            uri,
            projection,
            selection,
            null,
            null
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                list.add(
                    MusicEntity(
                        id = cursor.getLong(0),
                        title = cursor.getString(1),
                        artist = cursor.getString(2),
                        path = cursor.getString(3),
                        duration = cursor.getLong(4)
                    )
                )
            }
        }
        return list
    }
}