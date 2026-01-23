package com.example.appmusicplayer.view.home

import android.content.Context
import android.os.Bundle
import com.example.appmusicplayer.databinding.MenuItemMusicBinding
import com.example.appmusicplayer.model.Music
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson

class MenuItemMusic(context: Context,private val music: Music) : BottomSheetDialog(context) {
    private lateinit var binding: MenuItemMusicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuItemMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setDimAmount(0.8f)

        binding.txtMusicName.text = music.title
    }
}