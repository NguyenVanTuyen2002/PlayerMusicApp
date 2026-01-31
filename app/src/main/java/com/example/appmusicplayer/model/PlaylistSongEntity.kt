package com.example.appmusicplayer.model

import androidx.room.Entity

@Entity(primaryKeys = ["playlistId", "musicPath"])
data class PlaylistSongEntity(
    val playlistId: Int = 0,
    val musicPath: String
)
