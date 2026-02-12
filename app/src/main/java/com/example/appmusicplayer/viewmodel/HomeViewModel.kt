package com.example.appmusicplayer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appmusicplayer.model.AppDatabase
import com.example.appmusicplayer.model.music.Music
import com.example.appmusicplayer.model.music.MusicRepository
import com.example.appmusicplayer.model.playlist.PlaylistEntity.Companion.PLAYLIST_FAVOURITE_ID
import com.example.appmusicplayer.model.playlist.PlaylistRepository
import com.example.appmusicplayer.model.playlistSong.PlaylistSongEntity
import com.example.appmusicplayer.model.playlistSong.PlaylistSongRepository
import kotlinx.coroutines.launch

class HomeViewModel (application: Application) :
    AndroidViewModel(application) {

    private val repoMusic = MusicRepository(application)
    private val playlistRepo: PlaylistRepository
    init {
        val db = AppDatabase.getInstance(application)
        playlistRepo = PlaylistRepository(
            db.playlistDao(),
            db.playlistSongDao()
        )
    }

    private val playlistSongRepo: PlaylistSongRepository
    init {
        val db = AppDatabase.getInstance(application)
        playlistSongRepo = PlaylistSongRepository(db.playlistSongDao())
    }

    private val _musicList = MutableLiveData<List<Music>>()
    val musicList: LiveData<List<Music>> = _musicList

    fun loadMusic() = viewModelScope.launch {
        val musicList = repoMusic.getAllMusic()

        val favIds = playlistSongRepo.getFavouriteMusicIds()

        musicList.forEach { music ->
            music.isFavourite = favIds.contains(music.id)
        }

        _musicList.postValue(musicList)
    }

    fun toggleFavourite(music: Music) = viewModelScope.launch {
        val favId = PLAYLIST_FAVOURITE_ID

        val exists = playlistRepo.isSongInFavourite(favId, music.id)

        if (exists) {
            playlistRepo.removeFromFavourite(favId, music.id)
            music.isFavourite = false
        } else {
            playlistRepo.addToFavourite(
                PlaylistSongEntity(
                    playlistId = favId,
                    musicId = music.id,
                    musicName = music.title,
                    musicPath = music.path,
                    duration = music.duration
                )
            )
            music.isFavourite = true
        }

        _musicList.postValue(_musicList.value)
    }
}