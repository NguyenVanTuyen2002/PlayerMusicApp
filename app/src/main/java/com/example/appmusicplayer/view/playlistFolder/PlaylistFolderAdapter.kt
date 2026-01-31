package com.example.appmusicplayer.view.playlistFolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusicplayer.databinding.ItemMusicRecycleViewBinding
import com.example.appmusicplayer.model.PlaylistEntity

class PlaylistFolderAdapter(val data: ArrayList<PlaylistEntity>) : RecyclerView.Adapter<PlaylistFolderAdapter.ViewHolder>() {
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

    inner class ViewHolder(val binding: ItemMusicRecycleViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: PlaylistEntity) {
            binding.txtMusicName.text = data.name


        }
    }
}