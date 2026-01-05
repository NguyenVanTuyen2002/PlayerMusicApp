package com.example.appmusicplayer.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "music")
data class MusicEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val artist: String,
    val path: String,
    val duration: Long
): Parcelable
