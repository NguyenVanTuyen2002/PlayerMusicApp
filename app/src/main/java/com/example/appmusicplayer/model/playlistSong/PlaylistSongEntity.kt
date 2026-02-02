package com.example.appmusicplayer.model.playlistSong

import androidx.room.Entity

@Entity(primaryKeys = ["playlistId", "musicPath"])
data class PlaylistSongEntity(
    val playlistId: Int = 0,
    val musicPath: String
)
