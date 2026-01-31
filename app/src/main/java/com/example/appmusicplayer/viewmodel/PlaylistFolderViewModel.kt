package com.example.appmusicplayer.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmusicplayer.model.AppDatabase
import com.example.appmusicplayer.model.PlaylistEntity
import com.example.appmusicplayer.model.PlaylistRepository
import kotlinx.coroutines.launch

class PlaylistFolderViewModel : ViewModel() {
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

    fun deletePlaylist(playlistId: Int) {
        viewModelScope.launch {
            repository.deletePlaylist(playlistId)
        }
    }
}