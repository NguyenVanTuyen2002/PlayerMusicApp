package com.example.appmusicplayer.view.search

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.appmusicplayer.databinding.ListPlaylistMusicBinding
import com.example.appmusicplayer.model.music.Music
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

        viewModel = ViewModelProvider(
            activity,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application)
        )[ListPlaylistMusicViewModel::class.java]

        adapter = ListPlaylistMusicAdapter(mutableListOf()) { playlist ->
            viewModel.addMusicToPlaylist(playlist.id, music.path, music.title, music.id, music.duration)
            dismiss()
        }

        binding.rvPlaylistMusic.adapter = adapter

        observeData()
    }

    fun observeData() {
        viewModel.getAllPlaylists().observe(activity) {
            adapter.updateData(ArrayList(it))
        }
    }
}