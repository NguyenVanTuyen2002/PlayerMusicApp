package com.example.appmusicplayer.view.playlistFolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusicplayer.databinding.ItemMusicRecycleViewBinding
import com.example.appmusicplayer.model.playlist.PlaylistEntity
import com.example.appmusicplayer.model.playlistSong.PlaylistSongEntity

class PlaylistFolderAdapter(val data: ArrayList<PlaylistSongEntity>) : RecyclerView.Adapter<PlaylistFolderAdapter.ViewHolder>() {
    fun updateData(newData: List<PlaylistSongEntity>) {
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

    inner class ViewHolder(val binding: ItemMusicRecycleViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(song: PlaylistSongEntity) {
            binding.txtMusicName.text = song.musicPath.substringAfterLast("/")


        }
    }
}