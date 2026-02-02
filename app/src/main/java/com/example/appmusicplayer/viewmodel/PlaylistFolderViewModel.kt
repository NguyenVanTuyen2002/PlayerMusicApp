package com.example.appmusicplayer.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmusicplayer.model.AppDatabase
import com.example.appmusicplayer.model.playlist.PlaylistRepository
import com.example.appmusicplayer.model.playlistSong.PlaylistSongEntity
import com.example.appmusicplayer.model.playlistSong.PlaylistSongRepository
import kotlinx.coroutines.launch

class PlaylistFolderViewModel : ViewModel() {
    private lateinit var repoPlaylistSong: PlaylistSongRepository
    private lateinit var repoPlaylist: PlaylistRepository

    val songs = MutableLiveData<List<PlaylistSongEntity>>()

    fun init(context: Context) {
        val db = AppDatabase.getInstance(context.applicationContext)

        repoPlaylistSong = PlaylistSongRepository(db.playlistSongDao())
        repoPlaylist = PlaylistRepository(
            db.playlistDao(),
            db.playlistSongDao()
        )
    }

    fun deletePlaylist(playlistId: Int) {
        viewModelScope.launch {
            repoPlaylist.deletePlaylist(playlistId)
        }
    }

    fun loadSongs(playlistId: Int) {
        viewModelScope.launch {
            songs.postValue(repoPlaylistSong.getSongsInPlaylist(playlistId))
        }
    }
}