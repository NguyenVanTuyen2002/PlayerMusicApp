package com.example.appmusicplayer.view.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appmusicplayer.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}