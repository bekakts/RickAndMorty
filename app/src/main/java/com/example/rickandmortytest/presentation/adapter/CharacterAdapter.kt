package com.example.rickandmortytest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.rickandmortytest.databinding.CharacterLayoutBinding
import com.example.rickandmortytest.presentation.adapter.CharacterAdapter.ImageViewHolder

class CharacterAdapter : PagingDataAdapter<com.example.rickandmortytest.domain.model.Character,
        ImageViewHolder>(diffCallback) {


    inner class ImageViewHolder(
        val binding: CharacterLayoutBinding
    ) :
        ViewHolder(binding.root)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<com.example.rickandmortytest.domain.model.Character>() {
            override fun areItemsTheSame(oldItem: com.example.rickandmortytest.domain.model.Character, newItem: com.example.rickandmortytest.domain.model.Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: com.example.rickandmortytest.domain.model.Character, newItem: com.example.rickandmortytest.domain.model.Character): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            CharacterLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currChar = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                tvDescription.text = "${currChar?.name}"

                val imageLink = currChar?.image
                imageView.load(imageLink) {
                    crossfade(true)
                    crossfade(1000)
                }

            }
        }

    }


}