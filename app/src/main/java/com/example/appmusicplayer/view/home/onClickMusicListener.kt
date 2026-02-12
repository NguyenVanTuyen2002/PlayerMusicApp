package com.example.appmusicplayer.view.home

import com.example.appmusicplayer.model.music.Music

interface onClickMusicListener {
    fun onMusicListener(music: Music)
    fun onDetailMusic(music: Music)
    fun onFavoriteMusic(music: Music)
}