package com.example.appmusicplayer.model.playlistSong

import androidx.room.Entity

@Entity(tableName = "playlist_song", primaryKeys = ["playlistId", "musicPath"])
data class PlaylistSongEntity(
    val playlistId: Int = 0,
    val musicPath: String,
    val musicName: String,
    val musicId: Long,
    val duration: Long
)
