package com.example.appmusicplayer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appmusicplayer.R
import com.example.appmusicplayer.model.Intro

class IntroViewModel : ViewModel() {
    var data: ArrayList<Intro> = ArrayList<Intro>()
    fun loadData() {
        data.add(
            Intro(
                R.drawable.ig_intro1,
                "Discover and enjoy all your music any time you want",
                "Esily access all the songs stored on your device in just seconds."
            )
        )
        data.add(
            Intro(
                R.drawable.ig_intro2,
                "Create playlists that match your mood and style",
                "Organize your favorite tracks into custom playlists made just for you."
            )
        )
        data.add(
            Intro(
                R.drawable.ig_intro3,
                "Listen to music smoothly, even when you're offline",
                "Enjoy a seamless listening experience without interruptions or internet connection."
            )
        )
    }
}