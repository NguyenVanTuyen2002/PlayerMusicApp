package com.example.appmusicplayer.model.playlistSong

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaylistSongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistSong(entity: PlaylistSongEntity)

    @Query("DELETE FROM PlaylistSongEntity WHERE playlistId = :playlistId")
    suspend fun deleteSongsByPlaylistId(playlistId: Int)

    @Query("SELECT * FROM PlaylistSongEntity WHERE playlistId = :playlistId")
    suspend fun getSongsByPlaylistId(playlistId: Int): List<PlaylistSongEntity>
}
