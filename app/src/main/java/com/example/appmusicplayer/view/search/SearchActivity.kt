package com.example.appmusicplayer.view.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.appmusicplayer.databinding.ActivitySearchBinding
import com.example.appmusicplayer.model.music.Music
import com.example.appmusicplayer.model.music.MusicRepository
import com.example.appmusicplayer.view.home.MenuItemMusic
import com.example.appmusicplayer.view.playerSong.PlayerSongActivity
import com.example.appmusicplayer.viewmodel.SearchViewModel
import com.google.gson.Gson

class SearchActivity : AppCompatActivity(), onClickMusicSearch {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var viewmodel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SearchAdapter(arrayListOf(), this)
        binding.listMusic.adapter = adapter

        viewmodel = ViewModelProvider(this)[SearchViewModel::class.java]
        val repository = MusicRepository(this)
        viewmodel.init(this)
        viewmodel.setRepository(repository)

        observeData()
        search()
        backView()
    }

    fun observeData() {
        viewmodel.musicList.observe(this) { list ->
            adapter.updateData(list)
        }
    }

    fun search() {
        binding.edtSearch.addTextChangedListener { text ->
            viewmodel.search(text.toString())
        }
    }

    fun backView() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onClickMusic(music: Music) {
        val intent = Intent(this, PlayerSongActivity::class.java)

        val gson = Gson()
        val musicJson = gson.toJson(adapter.data)
        val musicIndex = adapter.data.indexOf(music)

        intent.putExtra("music_list_json", musicJson)
        intent.putExtra("music_index", musicIndex)

        startActivity(intent)
    }

    override fun onFavouriteMusic(music: Music) {
        viewmodel.toggleFavourite(music)
    }

    override fun onDetailMusic(music: Music) {
        val menuItem = MenuItemMusic(this, music)
        menuItem.show()
    }
}