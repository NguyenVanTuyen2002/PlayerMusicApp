package com.example.appmusicplayer.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Query("DELETE FROM playlist WHERE id = :playlistId")
    suspend fun deletePlaylistById(playlistId: Int)

    @Query("SELECT * FROM playlist")
    fun getAllPlaylists(): LiveData<List<PlaylistEntity>>
}