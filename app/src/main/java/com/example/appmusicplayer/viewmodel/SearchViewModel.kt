package com.example.appmusicplayer.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmusicplayer.model.AppDatabase
import com.example.appmusicplayer.model.music.Music
import com.example.appmusicplayer.model.music.MusicRepository
import com.example.appmusicplayer.model.playlist.PlaylistEntity.Companion.PLAYLIST_FAVOURITE_ID
import com.example.appmusicplayer.model.playlist.PlaylistRepository
import com.example.appmusicplayer.model.playlistSong.PlaylistSongDao
import com.example.appmusicplayer.model.playlistSong.PlaylistSongEntity
import com.example.appmusicplayer.model.playlistSong.PlaylistSongRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private lateinit var repository: MusicRepository
    private lateinit var repoPlaylist: PlaylistRepository
    private lateinit var repoPlaylistSong: PlaylistSongRepository


    fun init (context: Context) {
        val db = AppDatabase.getInstance(context.applicationContext)

        repoPlaylist = PlaylistRepository(db.playlistDao(), db.playlistSongDao())
        repoPlaylistSong = PlaylistSongRepository(db.playlistSongDao())
    }
    private val _musicList = MutableLiveData<List<Music>>()
    val musicList: LiveData<List<Music>> = _musicList

    private var allMusic: List<Music> = emptyList()

    fun setRepository(repository: MusicRepository) {
        this.repository = repository
        loadMusic()
    }

    fun loadMusic() = viewModelScope.launch {
        val musicList = repository.getAllMusic()
        val favIds = repoPlaylistSong.getFavouriteMusicIds()

        musicList.forEach { music ->
            music.isFavourite = favIds.contains(music.id)
        }

        allMusic = musicList
        _musicList.postValue(allMusic)
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

    fun toggleFavourite(music: Music) = viewModelScope.launch {
        val favId = PLAYLIST_FAVOURITE_ID

        val exists = repoPlaylist.isSongInFavourite(favId, music.id)

        if (exists) {
            repoPlaylist.removeFromFavourite(favId, music.id)
            music.isFavourite = false
        } else {
            repoPlaylist.addToFavourite(
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