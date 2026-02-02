package com.example.appmusicplayer.view.home

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.appmusicplayer.databinding.MenuItemMusicBinding
import com.example.appmusicplayer.model.music.Music
import com.google.android.material.bottomsheet.BottomSheetDialog

class MenuItemMusic(private val activity: FragmentActivity,private val music: Music) : BottomSheetDialog(activity) {
    private lateinit var binding: MenuItemMusicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuItemMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setDimAmount(0.8f)

        binding.txtMusicName.text = music.title

        binding.btnAddPlaylist.setOnClickListener {
            ListPlaylistMusic(
                activity,
                music
            ).show()
        }
    }
}