package com.example.appmusicplayer.view.playlist

import com.example.appmusicplayer.model.PlaylistEntity

interface onPlaylistClick {
    fun onCreatePlaylist()
    fun onClickPlaylist(playlist: PlaylistEntity)
}