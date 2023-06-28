package com.example.rickandmortytest.presentation.ui.episodedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortytest.databinding.ItemEpisodeCharacterBinding
import com.example.rickandmortytest.domain.model.Character

class EpisodeDetailAdapter(val onClick: (id: Int) -> Unit) :
    ListAdapter<Character, EpisodeDetailAdapter.EpisodeDetailViewHolder>(EpisodeDetailDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeDetailViewHolder {
        return EpisodeDetailViewHolder(
            ItemEpisodeCharacterBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: EpisodeDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EpisodeDetailViewHolder(private val binding: ItemEpisodeCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            with(binding) {
                itemView.setOnClickListener {
                    character.id?.let { it1 -> onClick(it1) }
                }
                Glide.with(imgCharacter).load(character.image).into(imgCharacter)
                tvName.text = character.name
                tvOrigin.text = character.origin.name
            }
        }
    }

    private class EpisodeDetailDiffUtil : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Character, newItem: Character) = oldItem == newItem

    }

}