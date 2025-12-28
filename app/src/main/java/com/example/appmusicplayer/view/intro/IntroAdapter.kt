package com.example.appmusicplayer.view.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusicplayer.model.Intro
import com.example.appmusicplayer.databinding.ViewpagerIntroBinding

class IntroAdapter(var data: ArrayList<Intro>,
                   val listener: onClickNextListener
) : RecyclerView.Adapter<IntroAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewpagerIntroBinding.inflate(inflater, parent, false)
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
        private val binding: ViewpagerIntroBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(intro: Intro) {
            binding.imgIntro.setImageResource(intro.img)
            binding.txtTitle.text = intro.title
            binding.txtContent.text = intro.content

            binding.btnNext.setOnClickListener {
                if (listener != null) {
                    val pos = bindingAdapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onNextListener(pos)
                    }
                }
            }
        }
    }
}