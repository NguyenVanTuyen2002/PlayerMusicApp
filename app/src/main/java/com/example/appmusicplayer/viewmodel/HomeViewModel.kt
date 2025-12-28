package com.example.appmusicplayer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appmusicplayer.R
import com.example.appmusicplayer.model.MusicEntity

class HomeViewModel : ViewModel() {
    var data: ArrayList<MusicEntity> = ArrayList<MusicEntity>()
    fun loadData() {
        data.add(MusicEntity(0,"Nơi này có anh", R.raw.noinaycoanh))
        data.add(MusicEntity(1,"Beautiful Girl", R.raw.beautiful_girl))
        data.add(MusicEntity(2,"Lạc Trôi", R.raw.lactroi))
        data.add(MusicEntity(3,"Summer Time", R.raw.summertime))
    }
}