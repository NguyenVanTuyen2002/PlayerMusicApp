package com.example.appmusicplayer.view.search

import android.R.attr.data
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.appmusicplayer.databinding.ItemMusicRecycleViewBinding
import com.example.appmusicplayer.model.Music

class SearchAdapter(var data: ArrayList<Music>, val search: onClickMusicSearch) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    fun updateData(newdata: List<Music>) {
        data.clear()
        data.addAll(newdata)
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
        holder.binView(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder (private val binding: ItemMusicRecycleViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binView(data: Music) {
            binding.txtMusicName.text = data.title

            binding.itemMusic.setOnClickListener {
                search.onClickMusic(data)
            }
        }
    }
}