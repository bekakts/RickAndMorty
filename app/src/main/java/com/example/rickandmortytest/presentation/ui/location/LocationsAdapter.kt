package com.example.rickandmortytest.presentation.ui.location

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortytest.databinding.ItemLocationsBinding
import com.example.rickandmortytest.domain.model.Location

class LocationsAdapter :
    PagingDataAdapter<Location, LocationsAdapter.LocationViewHolder>(NoteDiffUtil()) {


    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            ItemLocationsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class LocationViewHolder(private val binding: ItemLocationsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: Location) {
            with(binding) {
                tvName.text = "${data.name}  "
                tvDimension.text = "Dimension: ${data.dimension}"
                tvType.text = "Type: ${data.type}"
            }
        }
    }

    private class NoteDiffUtil : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Location, newItem: Location) = oldItem == newItem

    }

}