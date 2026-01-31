package com.example.appmusicplayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmusicplayer.model.Music
import com.example.appmusicplayer.model.MusicRepository

class SearchViewModel : ViewModel() {
    private lateinit var repository: MusicRepository

    private val _musicList = MutableLiveData<List<Music>>()
    val musicList: LiveData<List<Music>> = _musicList

    private var allMusic: List<Music> = emptyList()

    fun setRepository(repository: MusicRepository) {
        this.repository = repository
        loadMusic()
    }

    fun loadMusic() {
        allMusic = repository.getAllMusic()
        _musicList.value = allMusic
    }

    fun search(query: String) {
        if (query.isEmpty()) {
            _musicList.value = allMusic
        } else {
            _musicList.value = allMusic.filter {
                it.title.contains(query, true) ||
                it.artist.contains(query, true)
            }
        }
    }
}