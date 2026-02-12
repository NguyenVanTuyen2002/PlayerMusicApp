package com.example.appmusicplayer.model.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val isSystem: Boolean = false
)
{
    companion object {
        const val PLAYLIST_FAVOURITE_ID = 1
    }
}