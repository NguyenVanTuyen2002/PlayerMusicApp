package com.example.appmusicplayer.view.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusicplayer.databinding.ItemCreatePlaylistRvBinding
import com.example.appmusicplayer.databinding.ItemFavouritePlaylistRvBinding
import com.example.appmusicplayer.databinding.ItemHistoryPlaylistRvBinding
import com.example.appmusicplayer.databinding.ItemPlaylistRecycleViewBinding
import com.example.appmusicplayer.databinding.MenuItemMusicBinding
import com.example.appmusicplayer.model.Music
import com.example.appmusicplayer.model.PlaylistEntity
import com.example.appmusicplayer.model.PlaylistItem

class PlaylistAdapter(
    private val data: MutableList<PlaylistItem>,
val playlist: onPlaylistClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is PlaylistItem.Create -> 0
            is PlaylistItem.Favourite -> 1
            is PlaylistItem.History -> 2
            is PlaylistItem.Playlist -> 3
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> Create(ItemCreatePlaylistRvBinding.inflate(inflater, parent, false))
            1 -> Favourite(ItemFavouritePlaylistRvBinding.inflate(inflater, parent, false))
            2 -> History(ItemHistoryPlaylistRvBinding.inflate(inflater, parent, false))
            else -> Playlist(ItemPlaylistRecycleViewBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is Create -> holder.bindView()
            is Favourite -> holder.bindView()
            is History -> holder.bindView()
            is Playlist -> holder.bindView((data[position] as PlaylistItem.Playlist).data)
        }
    }

    override fun getItemCount(): Int = data.size

    fun updateData(playlist: List<PlaylistEntity>) {
        data.clear()
        data.add(PlaylistItem.Create)
        data.add(PlaylistItem.Favourite)
        data.add(PlaylistItem.History)
        playlist.forEach {
            data.add(PlaylistItem.Playlist(it))
        }
        notifyDataSetChanged()
    }

    inner class Create(val binding: ItemCreatePlaylistRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView() {
            binding.btnCreatePlaylist.setOnClickListener {
                playlist.onCreatePlaylist()
            }
        }
    }

    inner class Favourite(binding: ItemFavouritePlaylistRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView() {

        }
    }

    inner class History(binding: ItemHistoryPlaylistRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView() {

        }
    }

    inner class Playlist(val binding: ItemPlaylistRecycleViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: PlaylistEntity) {
            binding.txtNamePlaylist.text = data.name

            binding.btnPlaylistMusic.setOnClickListener {
                playlist.onClickPlaylist(data)
            }
        }
    }
}