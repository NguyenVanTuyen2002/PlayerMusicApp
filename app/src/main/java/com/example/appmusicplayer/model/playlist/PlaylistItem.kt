package com.example.appmusicplayer.model.playlist

sealed class PlaylistItem {
    object Create : PlaylistItem()
    object Favourite : PlaylistItem()
    object History : PlaylistItem()
    data class Playlist(val data: PlaylistEntity) : PlaylistItem()
}