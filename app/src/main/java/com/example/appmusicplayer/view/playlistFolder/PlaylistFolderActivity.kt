package com.example.appmusicplayer.view.playlistFolder

import android.content.Intent
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
        binding.txtPlaylistName.text = playlistName

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

    override fun onClickMusic(music: PlaylistSongEntity) {
        // 1. Lấy toàn bộ danh sách bài hát của playlist hiện tại
        val playlistSongs = viewModel.songs.value ?: return

        // 2. Convert PlaylistSongEntity -> Music
        val musicList = ArrayList<Music>()
        playlistSongs.forEach {
            musicList.add(
                Music(
                    id = it.musicId,
                    title = it.musicName,
                    artist = "", // nếu chưa có thì để rỗng
                    path = it.musicPath,
                    duration = it.duration
                )
            )
        }

        // 3. Tìm index bài được click
        val index = playlistSongs.indexOfFirst {
            it.musicId == music.musicId
        }

        // 4. Gửi sang PlayerSongActivity
        val gson = Gson()
        val intent = Intent(this, PlayerSongActivity::class.java)
        intent.putExtra("music_list_json", gson.toJson(musicList))
        intent.putExtra("music_index", index)

        startActivity(intent)
    }
}