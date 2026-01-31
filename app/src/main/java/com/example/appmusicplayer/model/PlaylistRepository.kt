package com.example.appmusicplayer.model

import android.content.Context
import androidx.lifecycle.LiveData

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

    // PlaylistSong
    suspend fun insertSongToPlaylist(playlistSong: PlaylistSongEntity) {
        playlistSongDao.insertPlaylistSong(playlistSong)
    }

    suspend fun getSongsInPlaylist(playlistId: Int): List<PlaylistSongEntity> {
        return playlistSongDao.getSongsByPlaylistId(playlistId)
    }
}