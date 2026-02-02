package com.example.appmusicplayer.view.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.appmusicplayer.model.music.Music
import com.example.appmusicplayer.view.playerSong.PlayerSongActivity
import com.example.appmusicplayer.view.search.SearchActivity
import com.example.appmusicplayer.viewmodel.HomeViewModel
import com.google.gson.Gson
import android.Manifest
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.appmusicplayer.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), onClickMusicListener {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: HomeAdapter
    private lateinit var viewModel: HomeViewModel
    private val REQUEST_AUDIO_PERMISSION = 100

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        adapter = HomeAdapter(arrayListOf(), this)
        _binding?.listMusic?.adapter = adapter

        requestAudioPermission()

        viewModel.musicList.observe(viewLifecycleOwner) {list ->
            Log.d("Tuyen", "Số bài hát nhận được: ${list.size}")

            if (list.isEmpty()) {
                Log.d("Tuyen", "Danh sách nhạc RỖNG")
            } else {
                Log.d("Tuyen", "Bài hát đầu tiên: ${list[0].title}")
            }

            adapter.updateData(ArrayList(list))
        }

        _binding?.btnSearch?.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }

        val audioPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    private fun requestAudioPermission() {
        val audioPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                audioPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // ✅ Fragment → dùng requestPermissions
            requestPermissions(
                arrayOf(audioPermission),
                REQUEST_AUDIO_PERMISSION
            )
        } else {
            // ✅ Đã có quyền → load nhạc
            viewModel.loadMusic()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("Tuyen", "Đã được cấp quyền audio")
                viewModel.loadMusic()
            } else {
                Log.e("Tuyen", "Người dùng từ chối quyền audio")
            }
        }
    }

    override fun onMusicListener(music: Music) {
        val intent = Intent(requireContext(), PlayerSongActivity::class.java)

        val gson = Gson()
        val musicJson = gson.toJson(adapter.data)
        val musicIndex = adapter.data.indexOf(music)

        intent.putExtra("music_list_json", musicJson)
        intent.putExtra("music_index", musicIndex)

        startActivity(intent)
    }

    override fun onDetailMusic(music: Music) {
        val menuItem = MenuItemMusic(requireActivity()
            , music)
        menuItem.show()
    }
}