package com.example.appmusicplayer.model

import android.app.Application
import com.example.appmusicplayer.model.playlist.PlaylistRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val db = AppDatabase.getInstance(this)
        val repo = PlaylistRepository(
            db.playlistDao(),
            db.playlistSongDao()
        )

        CoroutineScope(Dispatchers.IO).launch {
            repo.initFavouritePlaylists()
        }
    }
}