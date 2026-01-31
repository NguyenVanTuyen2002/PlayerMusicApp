package com.example.appmusicplayer.view.playlistFolder

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.appmusicplayer.databinding.ActivityPlaylistMusicBinding
import com.example.appmusicplayer.model.PlaylistRepository
import com.example.appmusicplayer.viewmodel.PlaylistFolderViewModel

class PlaylistFolderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaylistMusicBinding
    private lateinit var adapter: PlaylistFolderAdapter
    private lateinit var viewModel: PlaylistFolderViewModel

    private var playlistId: Int = -1

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PlaylistFolderAdapter(arrayListOf())
        binding.rvPlaylistMusic.adapter = adapter

        viewModel = ViewModelProvider(this)[PlaylistFolderViewModel::class.java]
        viewModel.init(this)

        playlistId = intent.getIntExtra("playlist_id", -1)
        val playlistName = intent.getStringExtra("playlist_name")
        binding.txtPlaylistName.text = playlistName

        backView()

        deletePlaylist()
    }

    fun backView() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    fun deletePlaylist() {
        binding.btnDelete.setOnClickListener {
            viewModel.deletePlaylist(playlistId)
            finish()
        }
    }
}