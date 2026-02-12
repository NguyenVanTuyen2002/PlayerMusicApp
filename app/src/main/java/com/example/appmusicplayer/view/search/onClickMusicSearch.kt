package com.example.appmusicplayer.view.search

import com.example.appmusicplayer.model.music.Music

interface onClickMusicSearch {
    fun onClickMusic(music: Music)
    fun onFavouriteMusic(music: Music)
    fun onDetailMusic(music: Music)
}