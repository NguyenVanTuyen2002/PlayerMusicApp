package com.example.appmusicplayer.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmusicplayer.model.AppDatabase
import com.example.appmusicplayer.model.playlist.PlaylistEntity
import com.example.appmusicplayer.model.playlist.PlaylistItem
import com.example.appmusicplayer.model.playlist.PlaylistRepository
import kotlinx.coroutines.launch

class PlaylistViewModel : ViewModel(){
    private lateinit var repository: PlaylistRepository
    private var _playlistList = MutableLiveData<List<PlaylistEntity>>()
    val playlistList: LiveData<List<PlaylistEntity>> = _playlistList

    fun init(context: Context) {
        if (::repository.isInitialized) return

        val db = AppDatabase.getInstance(context.applicationContext)

        repository = PlaylistRepository(
            playlistDao = db.playlistDao(),
            playlistSongDao = db.playlistSongDao()
        )

        repository.getPlaylist().observeForever {
            _playlistList.postValue(it)
        }
    }

    fun buildPlaylistItems(playlists: List<PlaylistEntity>): List<PlaylistItem> {
        val items = mutableListOf<PlaylistItem>()

        // item tạo playlist
        items.add(PlaylistItem.Create)

        // item favourite (luôn luôn có)
        items.add(PlaylistItem.Favourite)

        // playlist user tạo
        playlists
            .filter { !it.isSystem } // ẩn system playlist khỏi list thường
            .forEach {
                items.add(PlaylistItem.Playlist(it))
            }

        return items
    }

    fun createPlaylist(name: String) {
        viewModelScope.launch {
            repository?.createPlaylist(name)
        }
    }
}