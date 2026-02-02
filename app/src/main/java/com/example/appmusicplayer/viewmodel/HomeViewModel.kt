package com.example.appmusicplayer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmusicplayer.model.music.Music
import com.example.appmusicplayer.model.music.MusicRepository

class HomeViewModel (application: Application) :
    AndroidViewModel(application) {

    private val repository = MusicRepository(application)
    private val _musicList = MutableLiveData<List<Music>>()
    val musicList: LiveData<List<Music>> = _musicList

    fun loadMusic() {
        _musicList.value = repository.getAllMusic()
        //Log.d("Tuyen", _musicList.value.toString())
    }
}