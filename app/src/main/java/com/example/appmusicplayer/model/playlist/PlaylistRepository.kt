package com.example.appmusicplayer.model.playlist

import androidx.lifecycle.LiveData
import com.example.appmusicplayer.model.music.Music
import com.example.appmusicplayer.model.playlist.PlaylistEntity.Companion.PLAYLIST_FAVOURITE_ID
import com.example.appmusicplayer.model.playlistSong.PlaylistSongDao
import com.example.appmusicplayer.model.playlistSong.PlaylistSongEntity

class PlaylistRepository(private val playlistDao: PlaylistDao, private val playlistSongDao: PlaylistSongDao) {

    suspend fun initFavouritePlaylists() {
        val favourite = playlistDao.getPlaylistById(PLAYLIST_FAVOURITE_ID)

        if (favourite == null) {
            playlistDao.insertPlaylist(
                PlaylistEntity(
                    id = PLAYLIST_FAVOURITE_ID,
                    name = "Favourite",
                    isSystem = true
                )
            )
        }
    }

    suspend fun isSongInFavourite(playlistId: Int, musicId: Long): Boolean {
        return playlistSongDao
            .getSongInPlaylist(playlistId, musicId) != null
    }

    suspend fun addToFavourite(entity: PlaylistSongEntity) {
        playlistSongDao.insertPlaylistSong(entity)
    }

    suspend fun removeFromFavourite(playlistId: Int, musicId: Long) {
        playlistSongDao.deleteSong(playlistId, musicId)
    }



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