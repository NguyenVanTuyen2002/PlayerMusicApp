package com.example.appmusicplayer.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusicplayer.databinding.RecycleviewHomeBinding
import com.example.appmusicplayer.model.MusicEntity

class HomeAdapter(
    var data: ArrayList<MusicEntity>,
    var listener: onClickMusicListener
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecycleviewHomeBinding.inflate(inflater, parent, false)
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
        private val binding: RecycleviewHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: MusicEntity) {
            binding.txtMusicName.text = data.name

            binding.root.setOnClickListener {
                listener.onMusicListener(data)
            }
        }
    }
}