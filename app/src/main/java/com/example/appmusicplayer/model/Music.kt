package com.example.appmusicplayer.model

data class Music(
    val id: Long,
    val title: String,
    val artist: String,
    val path: String,
    val duration: Long
)
