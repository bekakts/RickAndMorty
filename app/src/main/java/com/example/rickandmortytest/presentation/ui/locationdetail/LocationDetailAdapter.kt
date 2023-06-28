package com.example.rickandmortytest.presentation.ui.locationdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortytest.databinding.ItemLocationCharactersBinding
import com.example.rickandmortytest.domain.model.Character

class LocationDetailAdapter(val onClick: (id: Int) -> Unit) :
    ListAdapter<Character, LocationDetailAdapter.LocationDetailViewHolder>(LocationDetailDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationDetailViewHolder {
        return LocationDetailViewHolder(
            ItemLocationCharactersBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: LocationDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class LocationDetailViewHolder(private val binding: ItemLocationCharactersBinding) :
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

    private class LocationDetailDiffUtil : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Character, newItem: Character) = oldItem == newItem

    }
}