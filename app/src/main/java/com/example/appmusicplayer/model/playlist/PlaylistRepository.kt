package com.example.appmusicplayer.model.playlist

import androidx.lifecycle.LiveData
import com.example.appmusicplayer.model.playlistSong.PlaylistSongDao
import com.example.appmusicplayer.model.playlistSong.PlaylistSongEntity

class PlaylistRepository(private val playlistDao: PlaylistDao, private val playlistSongDao: PlaylistSongDao) {

    fun getPlaylist(): LiveData<List<PlaylistEntity>> {
        return playlistDao.getAllPlaylists()
    }

    suspend fun createPlaylist(name: String) {
        val playlist = PlaylistEntity(name = name)
        playlistDao.insertPlaylist(playlist)
    }

    suspend fun deletePlaylist(playlistId: Int) {
        playlistSongDao.deleteSongsByPlaylistId(playlistId)
        playlistDao.deletePlaylistById(playlistId)
    }
}