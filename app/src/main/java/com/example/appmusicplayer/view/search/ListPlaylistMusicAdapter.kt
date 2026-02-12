package com.example.appmusicplayer.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusicplayer.databinding.ItemPlaylistRecycleViewBinding
import com.example.appmusicplayer.model.playlist.PlaylistEntity
import com.example.appmusicplayer.model.playlist.PlaylistItem

class ListPlaylistMusicAdapter(private val data: MutableList<PlaylistEntity>,
                               private val onClick: (PlaylistEntity) -> Unit
) : RecyclerView.Adapter<ListPlaylistMusicAdapter.ViewHolder>() {

    fun updateData(newData: ArrayList<PlaylistEntity>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaylistRecycleViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bindView(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(val binding: ItemPlaylistRecycleViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: PlaylistEntity) {
            binding.txtNamePlaylist.text = data.name

            binding.root.setOnClickListener {
                onClick(data)
            }
        }
    }
}