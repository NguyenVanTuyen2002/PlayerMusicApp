package com.example.appmusicplayer.model.playlistSong

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaylistSongDao {
    // lấy danh sách bài hát theo musicId để cho favourite
    @Query("SELECT musicId FROM playlist_song WHERE playlistId = :playlistId")
    suspend fun getMusicIdsByPlaylist(playlistId: Int): List<Long>

    @Query("""SELECT * FROM playlist_song WHERE playlistId = :playlistId AND musicId = :musicId LIMIT 1""")
    suspend fun getSongInPlaylist(playlistId: Int, musicId: Long): PlaylistSongEntity?

    @Query("""DELETE FROM playlist_song WHERE playlistId = :playlistId AND musicId = :musicId""")
    suspend fun deleteSong(playlistId: Int, musicId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistSong(entity: PlaylistSongEntity)

    @Query("DELETE FROM playlist_song WHERE playlistId = :playlistId")
    suspend fun deleteSongsByPlaylistId(playlistId: Int)

    //lấy danh sách bài hát theo playlistId cho playlist
    @Query("SELECT * FROM playlist_song WHERE playlistId = :playlistId")
    suspend fun getSongsByPlaylistId(playlistId: Int): List<PlaylistSongEntity>
}
