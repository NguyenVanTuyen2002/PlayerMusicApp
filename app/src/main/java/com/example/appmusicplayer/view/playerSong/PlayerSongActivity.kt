package com.example.appmusicplayer.view.playerSong

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.appmusicplayer.R
import com.example.appmusicplayer.databinding.ActivityPlayerSongBinding
import com.example.appmusicplayer.model.music.Music
import android.os.Handler
import android.widget.SeekBar
import com.google.gson.Gson


class PlayerSongActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerSongBinding
    private var mediaPlayer: MediaPlayer? = null
    private var isPause: Boolean = true
    private var isRandom: Boolean = false
    private lateinit var musicList: ArrayList<Music>
    private var currentIndex = 0
    private var handler: Handler? = null
    private lateinit var runnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerSongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backView()

        //nhận data từ home
        val musicJson = intent.getStringExtra("music_list_json")
        currentIndex = intent.getIntExtra("music_index", 0)
        val gson = Gson()
        val type = object : com.google.gson.reflect.TypeToken<ArrayList<Music>>() {}.type
        musicList = gson.fromJson(musicJson, type)

        //PHÁT BÀI Đc CLICK
        playMusic(currentIndex)

        setupControls()

        changeRandom()

        touchSeekbar()
    }

    fun backView() {
        binding.btnBack.setOnClickListener {
            mediaPlayer?.release()
            mediaPlayer = null
            finish()
        }
    }

    fun changeRandom() {
        //đổi màu nút random
        binding.btnPlayRandom.setOnClickListener {
            isRandom = !isRandom
            if (isRandom) {
                binding.btnPlayRandom.setColorFilter(getColor(R.color.green))
            } else {
                binding.btnPlayRandom.setColorFilter(Color.TRANSPARENT)
            }
        }
    }

    fun touchSeekbar() {
        //kéo seekbar để tua
        binding.seekbarMusic.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun playMusic(index: Int) {
        mediaPlayer?.release()

        val music = musicList[index]
        binding.txtSongName.text = music.title

        mediaPlayer = MediaPlayer().apply {
            setDataSource(music.path)
            prepare()
            start()
        }

        binding.seekbarMusic.max = mediaPlayer!!.duration
        binding.txtTimeTotal.text = formatTime(mediaPlayer!!.duration)
        binding.txtTimeCurrent.text = "00:00"

        startSeekBar()

        mediaPlayer?.setOnCompletionListener {

            currentIndex = if (isRandom) {
                (musicList.indices).random()
            } else {
                (currentIndex + 1) % musicList.size
            }
            playMusic(currentIndex)
        }

        isPause = false
        binding.btnPlayMusic.setImageResource(R.drawable.on)
    }

    //thanh seekbar chạy theo nhạc
    private fun startSeekBar() {
        handler = Handler(Looper.getMainLooper())

        runnable = object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    val current = it.currentPosition
                    binding.seekbarMusic.progress = it.currentPosition
                    binding.txtTimeCurrent.text = formatTime(current)
                    handler?.postDelayed(this, 500) // cập nhật mỗi 0.5s
                }
            }
        }

        handler?.post(runnable)
    }

    //hiển thị thời gian
    private fun formatTime(ms: Int): String {
        val minutes = ms / 1000 / 60
        val seconds = (ms / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun setupControls() {
        binding.btnPlayMusic.setOnClickListener {
            isPause = !isPause
            if (isPause) {
                mediaPlayer?.pause()
                binding.btnPlayMusic.setImageResource(R.drawable.frame_1000005827)
            } else {
                mediaPlayer?.start()
                startSeekBar()
                binding.btnPlayMusic.setImageResource(R.drawable.on)
            }
        }

        binding.btnNextMusic.setOnClickListener {
            currentIndex = (currentIndex + 1) % musicList.size
            playMusic(currentIndex)
        }

        binding.btnBackMusic.setOnClickListener {
            currentIndex =
                if (currentIndex - 1 < 0) musicList.size - 1 else currentIndex - 1
            playMusic(currentIndex)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(runnable)
        mediaPlayer?.release()
        mediaPlayer = null
    }
}