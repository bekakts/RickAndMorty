package com.example.rickandmortytest.presentation.ui.characterdetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortytest.databinding.ItemCharacterEpisodeBinding
import com.example.rickandmortytest.domain.model.Episode

class CharacterEpisodeAdapter(val onClick: (id: Int) -> Unit) :
    ListAdapter<Episode, CharacterEpisodeAdapter.CharacterEpisodeViewHolder>(CharacterEpisodeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterEpisodeViewHolder {
        return CharacterEpisodeViewHolder(
            ItemCharacterEpisodeBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterEpisodeViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class CharacterEpisodeViewHolder(private val binding: ItemCharacterEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(episode: Episode, position: Int) {
            with(binding) {
                itemView.setOnClickListener {
                    onClick(episode.id)

                }
                tvName.text = episode.name
                tvAirDate.text = episode.air_date
                tvNum.text = (position + 1).toString()

            }
        }
    }

    private class CharacterEpisodeDiffUtil : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode) = oldItem == newItem

    }
}