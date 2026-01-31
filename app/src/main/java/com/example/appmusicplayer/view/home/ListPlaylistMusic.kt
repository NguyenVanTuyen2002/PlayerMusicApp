package com.example.appmusicplayer.view.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.appmusicplayer.databinding.ListPlaylistMusicBinding
import com.example.appmusicplayer.model.Music
import com.example.appmusicplayer.model.PlaylistRepository
import com.example.appmusicplayer.viewmodel.ListPlaylistMusicViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class ListPlaylistMusic(
    private val activity: FragmentActivity,
    private val music: Music
) : BottomSheetDialog(activity) {
    private lateinit var binding: ListPlaylistMusicBinding
    private lateinit var adapter: ListPlaylistMusicAdapter
    private lateinit var viewModel: ListPlaylistMusicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListPlaylistMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setDimAmount(0.8f)

        adapter = ListPlaylistMusicAdapter(arrayListOf()) { playlist ->
            viewModel.addMusicToPlaylist(
                playlist.id,
                music.path
            )
            dismiss()
        }

        binding.rvPlaylistMusic.adapter = adapter

        viewModel = ViewModelProvider(activity)[ListPlaylistMusicViewModel::class.java]
        viewModel.init(activity)

        observeData()
    }

    fun observeData() {
        viewModel.getAllPlaylists().observe(activity) {
            adapter.updateData(ArrayList(it))
        }
    }
}