package com.example.appmusicplayer.model.playlistSong

class PlaylistSongRepository(private val playlistSongDao: PlaylistSongDao) {
    suspend fun insertSongToPlaylist(playlistSong: PlaylistSongEntity) {
        playlistSongDao.insertPlaylistSong(playlistSong)
    }

    suspend fun getSongsInPlaylist(playlistId: Int): List<PlaylistSongEntity> {
        return playlistSongDao.getSongsByPlaylistId(playlistId)
    }
}