package com.example.appmusicplayer.view.playlist

import com.example.appmusicplayer.model.playlist.PlaylistEntity

interface onPlaylistClick {
    fun onCreatePlaylist()
    fun onClickPlaylist(playlist: PlaylistEntity)
}