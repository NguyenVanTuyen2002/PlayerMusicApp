package com.example.appmusicplayer.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmusicplayer.model.AppDatabase
import com.example.appmusicplayer.model.playlist.PlaylistEntity
import com.example.appmusicplayer.model.playlist.PlaylistRepository
import com.example.appmusicplayer.model.playlistSong.PlaylistSongEntity
import com.example.appmusicplayer.model.playlistSong.PlaylistSongRepository
import kotlinx.coroutines.launch

class ListPlaylistMusicViewModel(application: Application) : AndroidViewModel(application) {
    private var repoPlaylist: PlaylistRepository
    private var repoPlaylistSong: PlaylistSongRepository

    init {
        val db = AppDatabase.getInstance(application)

        repoPlaylist = PlaylistRepository(
            db.playlistDao(),
            db.playlistSongDao()
        )

        repoPlaylistSong = PlaylistSongRepository(
            db.playlistSongDao()
        )
    }

    fun getAllPlaylists() = repoPlaylist.getPlaylist()

    fun addMusicToPlaylist(playlistId: Int, musicPath: String) {
        viewModelScope.launch {
            repoPlaylistSong.insertSongToPlaylist(
                PlaylistSongEntity(
                    playlistId = playlistId,
                    musicPath = musicPath
                )
            )
        }
    }
}