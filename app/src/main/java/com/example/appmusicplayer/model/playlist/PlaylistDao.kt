package com.example.appmusicplayer.model.playlist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaylistDao {
    // lấy playlist theo id (lấy ra id favourite)
    @Query("SELECT * FROM playlist WHERE id = :id LIMIT 1")
    suspend fun getPlaylistById(id: Int): PlaylistEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Query("DELETE FROM playlist WHERE id = :playlistId")
    suspend fun deletePlaylistById(playlistId: Int)

    @Query("SELECT * FROM playlist WHERE isSystem = 0")
    fun getAllPlaylists(): LiveData<List<PlaylistEntity>>
}