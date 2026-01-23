package com.example.appmusicplayer.view.home

import com.example.appmusicplayer.model.Music

interface onClickMusicListener {
    fun onMusicListener(music: Music)
    fun onDetailMusic(music: Music)
}