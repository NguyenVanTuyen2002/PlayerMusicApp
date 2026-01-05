package com.example.appmusicplayer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appmusicplayer.R
import com.example.appmusicplayer.model.MusicEntity

class HomeViewModel : ViewModel() {
    var data: ArrayList<MusicEntity> = ArrayList<MusicEntity>()

}