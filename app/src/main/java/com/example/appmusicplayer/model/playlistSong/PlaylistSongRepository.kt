package com.example.appmusicplayer.model.playlistSong

import com.example.appmusicplayer.model.playlist.PlaylistEntity.Companion.PLAYLIST_FAVOURITE_ID

class PlaylistSongRepository(private val playlistSongDao: PlaylistSongDao) {
    suspend fun insertSongToPlaylist(playlistSong: PlaylistSongEntity) {
        playlistSongDao.insertPlaylistSong(playlistSong)
    }

    suspend fun getSongsInPlaylist(playlistId: Int): List<PlaylistSongEntity> {
        return playlistSongDao.getSongsByPlaylistId(playlistId)
    }

    suspend fun getFavouriteMusicIds(): List<Long> {
        return playlistSongDao.getMusicIdsByPlaylist(PLAYLIST_FAVOURITE_ID)
    }
}