package com.example.appmusicplayer.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmusicplayer.model.AppDatabase
import com.example.appmusicplayer.model.music.Music
import com.example.appmusicplayer.model.playlist.PlaylistEntity
import com.example.appmusicplayer.model.playlist.PlaylistEntity.Companion.PLAYLIST_FAVOURITE_ID
import com.example.appmusicplayer.model.playlist.PlaylistRepository
import com.example.appmusicplayer.model.playlistSong.PlaylistSongEntity
import kotlinx.coroutines.launch

class PlayerSongViewModel : ViewModel() {
    private lateinit var repoPlaylist: PlaylistRepository

    fun init(context: Context) {
        val db = AppDatabase.getInstance(context.applicationContext)
        repoPlaylist = PlaylistRepository(db.playlistDao(), db.playlistSongDao())
    }

    private val _musicList = MutableLiveData<List<Music>>()

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

    suspend fun checkIsFavourite(musicId: Long): Boolean {
        return repoPlaylist.isSongInFavourite(
            PlaylistEntity.PLAYLIST_FAVOURITE_ID,
            musicId
        )
    }
}