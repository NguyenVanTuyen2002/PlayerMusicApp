package com.example.appmusicplayer.view.playlistFolder

import com.example.appmusicplayer.model.music.Music
import com.example.appmusicplayer.model.playlistSong.PlaylistSongEntity

interface onClickMusicFolder {
    fun onClickMusic(music: Music)
    fun onFavouriteMusic(music: Music)
    fun onDetailMusic(music: Music)
}