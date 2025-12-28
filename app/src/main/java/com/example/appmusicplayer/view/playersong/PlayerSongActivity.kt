package com.example.appmusicplayer.view.playersong

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.appmusicplayer.R
import com.example.appmusicplayer.databinding.ActivityPlayerSongBinding
import com.example.appmusicplayer.model.MusicEntity
import android.os.Handler
import android.widget.SeekBar


class PlayerSongActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerSongBinding
    private var mediaPlayer: MediaPlayer? = null
    private var isPause: Boolean = true
    private var isRandow: Boolean = false
    private lateinit var musicList: ArrayList<MusicEntity>
    private var currentIndex = 0
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerSongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
            mediaPlayer?.pause()
        }

        musicList = intent.getParcelableArrayListExtra("music_list")!!
        currentIndex = intent.getIntExtra("music_index", 0)
        binding.txtSongName.text = musicList[currentIndex].name

        mediaPlayer = MediaPlayer.create(this, musicList[currentIndex].music)

        binding.seekbarMusic.max = mediaPlayer!!.duration

        //tắt bật music
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

        //bật bài hát hiện tại
        playMusic(currentIndex)

        //bật bài tiếp theo
        binding.btnNextMusic.setOnClickListener {
            val next = currentIndex + 1
            if (next < musicList.size) {
                currentIndex = next
            } else {
                currentIndex = 0
            }
            playMusic(currentIndex)
            Log.d("Tuyen", currentIndex.toString())
        }

        //bật bài trc đó
        binding.btnBackMusic.setOnClickListener {
            val back = currentIndex - 1
            if (back > -1) {
                currentIndex = back
            } else {
                currentIndex = musicList.size - 1
            }
            playMusic(currentIndex)
        }

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

        binding.btnPlayRandom.setOnClickListener {
            isRandow = !isRandow
            if (isRandow) {
                binding.btnPlayRandom.setColorFilter(getColor(R.color.green))
            } else {
                binding.btnPlayRandom.setColorFilter(Color.TRANSPARENT)
            }
        }
    }

    private fun playMusic(index: Int) {
        mediaPlayer?.release()

        val music = musicList[index]
        binding.txtSongName.text = music.name

        mediaPlayer = MediaPlayer.create(this, music.music)
        mediaPlayer?.start()

        binding.seekbarMusic.max = mediaPlayer!!.duration
        binding.txtTimeTotal.text = formatTime(mediaPlayer!!.duration)
        binding.txtTimeCurrent.text = "00:00"
        startSeekBar()

        mediaPlayer?.setOnCompletionListener {

            if (isRandow) {
                var nextIndex: Int
                do {
                    nextIndex = (0 until musicList.size).random()
                } while (nextIndex == currentIndex)

                currentIndex = nextIndex
            } else {
                currentIndex++
                if (currentIndex > musicList.size - 1) {
                    currentIndex = 0
                }
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
                    handler.postDelayed(this, 500) // cập nhật mỗi 0.5s
                }
            }
        }

        handler.post(runnable)
    }

    //hiển thị thời gian
    private fun formatTime(ms: Int): String {
        val minutes = ms / 1000 / 60
        val seconds = (ms / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}