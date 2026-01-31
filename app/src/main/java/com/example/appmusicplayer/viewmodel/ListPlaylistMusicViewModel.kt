package com.example.appmusicplayer.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmusicplayer.model.AppDatabase
import com.example.appmusicplayer.model.PlaylistEntity
import com.example.appmusicplayer.model.PlaylistRepository
import com.example.appmusicplayer.model.PlaylistSongEntity
import kotlinx.coroutines.launch

class ListPlaylistMusicViewModel : ViewModel() {
    private lateinit var repository: PlaylistRepository

    fun init(context: Context) {
        if (!::repository.isInitialized) {
            val db = AppDatabase.getInstance(context.applicationContext)
            repository = PlaylistRepository(
                db.playlistDao(),
                db.playlistSongDao()
            )
        }
    }

    fun getAllPlaylists(): LiveData<List<PlaylistEntity>> {
        return repository.getPlaylist()
    }

    fun addMusicToPlaylist(playlistId: Int, musicPath: String) {
        viewModelScope.launch {
            repository.insertSongToPlaylist(
                PlaylistSongEntity(
                    playlistId = playlistId,
                    musicPath = musicPath
                )
            )
        }
    }
}