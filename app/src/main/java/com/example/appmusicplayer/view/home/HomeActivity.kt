package com.example.appmusicplayer.view.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appmusicplayer.databinding.ActivityHomeBinding
import com.example.appmusicplayer.model.MusicEntity
import com.example.appmusicplayer.view.playerSong.PlayerSongActivity
import com.example.appmusicplayer.view.search.SearchActivity
import com.example.appmusicplayer.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity(), onClickMusicListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: HomeAdapter
    private lateinit var viewModel: HomeViewModel
    private var data: ArrayList<MusicEntity> = ArrayList<MusicEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = HomeAdapter(data, this)
        binding.listMusic.adapter = adapter

        viewModel = HomeViewModel()
        viewModel.data = data
        //viewModel.loadData()

        binding.btnSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onMusicListener(music: MusicEntity) {
        val intent = Intent(this, PlayerSongActivity::class.java)
        intent.putParcelableArrayListExtra("music_list", data)
        intent.putExtra("music_index", data.indexOf(music))
        startActivity(intent)
    }
}