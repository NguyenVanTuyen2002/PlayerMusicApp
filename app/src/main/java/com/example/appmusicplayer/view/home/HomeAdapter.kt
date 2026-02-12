package com.example.appmusicplayer.view.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusicplayer.R
import com.example.appmusicplayer.databinding.ItemMusicRecycleViewBinding
import com.example.appmusicplayer.model.music.Music

class HomeAdapter(
    var data: ArrayList<Music>,
    var listener: onClickMusicListener
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    fun updateData(newData: ArrayList<Music>) {
        Log.d("Tuyen", "Adapter nhận ${newData.size} bài hát")
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMusicRecycleViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bindView(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        private val binding: ItemMusicRecycleViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: Music) {
            binding.txtMusicName.text = data.title

            binding.itemMusic
                .setOnClickListener {
                listener.onMusicListener(data)
            }

            binding.btnDetailMusic.setOnClickListener {
                listener.onDetailMusic(data)
            }

            binding.btnFavoriteMusic.setImageResource(
                if (data.isFavourite) R.drawable.heart
                else R.drawable.favorite
            )

            binding.btnFavoriteMusic.setOnClickListener {
                listener.onFavoriteMusic(data)
            }
        }
    }
}