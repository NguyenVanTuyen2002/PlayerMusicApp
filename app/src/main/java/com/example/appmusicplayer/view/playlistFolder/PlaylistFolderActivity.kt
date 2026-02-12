package com.example.appmusicplayer.view.playlistFolder

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.appmusicplayer.databinding.ActivityPlaylistMusicBinding
import com.example.appmusicplayer.model.music.Music
import com.example.appmusicplayer.model.playlistSong.PlaylistSongEntity
import com.example.appmusicplayer.view.playerSong.PlayerSongActivity
import com.example.appmusicplayer.viewmodel.PlaylistFolderViewModel
import com.google.gson.Gson

class PlaylistFolderActivity : AppCompatActivity(), onClickMusicFolder {
    private lateinit var binding: ActivityPlaylistMusicBinding
    private lateinit var adapter: PlaylistFolderAdapter
    private lateinit var viewModel: PlaylistFolderViewModel

    private var playlistId: Int = -1

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PlaylistFolderAdapter(arrayListOf(), this)
        binding.rvPlaylistMusic.adapter = adapter

        viewModel = ViewModelProvider(this)[PlaylistFolderViewModel::class.java]
        viewModel.init(this)

        playlistId = intent.getIntExtra("playlist_id", -1)
        val playlistName = intent.getStringExtra("playlist_name")
        val isSystem = intent.getBooleanExtra("playlist_is_system", false)

        binding.txtPlaylistName.text = playlistName

        // ẩn nút xóa nếu không phải là playlist favourite
        if (isSystem) {
            binding.btnDelete.visibility = View.GONE
        }

        // load danh sách bài hát theo playlistid
        viewModel.loadSongs(playlistId)

        viewModel.songs.observe(this) {
            adapter.updateData(it)
        }

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

    override fun onClickMusic(music: Music) {
        val musicList = viewModel.songs.value ?: return
        val index = musicList.indexOf(music)

        val gson = Gson()
        val intent = Intent(this, PlayerSongActivity::class.java)
        intent.putExtra("music_list_json", gson.toJson(musicList))
        intent.putExtra("music_index", index)

        startActivity(intent)
    }

    override fun onFavouriteMusic(music: Music) {
        viewModel.toggleFavourite(music)
    }

    override fun onDetailMusic(music: Music) {
        val menuItem = MenuItemMusic(this, music)
        menuItem.show()
    }
}