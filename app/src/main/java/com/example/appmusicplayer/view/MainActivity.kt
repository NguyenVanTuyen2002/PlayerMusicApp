package com.example.appmusicplayer.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.appmusicplayer.R
import com.example.appmusicplayer.databinding.ActivityMainBinding
import com.example.appmusicplayer.view.folder.FolderFragment
import com.example.appmusicplayer.view.home.HomeFragment
import com.example.appmusicplayer.view.playlist.PlaylistFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFragment()

        binding.navController.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.fragmentHome -> replaceFragment(HomeFragment())
                R.id.fragmentPlaylist -> replaceFragment(PlaylistFragment())
                R.id.fragmentFolder -> replaceFragment(FolderFragment())
            }
            true
        }
    }

    fun setupFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment, HomeFragment(), "home")
            .commit()
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment)
            .commit()
    }
}