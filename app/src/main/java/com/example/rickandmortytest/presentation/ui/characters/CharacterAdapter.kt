package com.example.rickandmortytest.presentation.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortytest.databinding.CharacterLayoutBinding
import com.example.rickandmortytest.domain.model.Character

class CharacterAdapter(val onClick: (id: Int) -> Unit) :
    PagingDataAdapter<Character, CharacterAdapter.ImageViewHolder>(NoteDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            CharacterLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))

    }


    inner class ImageViewHolder(private val binding: CharacterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Character?) {
            with(binding) {
                itemView.setOnClickListener {
                    data?.id?.let { onClick(it) }
                }
                tvDescription.text = data?.name ?: "This character absent"
                Glide.with(imageView).load(data?.image).into(imageView)
            }
        }
    }

    private class NoteDiffUtil : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Character, newItem: Character) = oldItem == newItem

    }
}