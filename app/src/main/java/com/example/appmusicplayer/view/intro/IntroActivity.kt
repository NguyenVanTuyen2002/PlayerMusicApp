package com.example.appmusicplayer.view.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appmusicplayer.model.Intro
import com.example.appmusicplayer.databinding.ActivityIntroBinding
import com.example.appmusicplayer.view.MainActivity
import com.example.appmusicplayer.viewmodel.IntroViewModel

class IntroActivity: AppCompatActivity(), onClickNextListener {
    private lateinit var binding: ActivityIntroBinding
    private lateinit var adapter: IntroAdapter
    private lateinit var viewmodel: IntroViewModel
    private val data: ArrayList<Intro> = ArrayList<Intro>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = IntroAdapter(data, this)
        binding.viewPagerIntro.adapter = adapter
        binding.dotViewPager.attachTo(binding.viewPagerIntro)

        viewmodel = IntroViewModel()
        viewmodel.data = data
        viewmodel.loadData()
    }

    override fun onNextListener(pos: Int) {
        val next = pos + 1
        if (next < data.size) {
            binding.viewPagerIntro.setCurrentItem(next, true)
        }else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}