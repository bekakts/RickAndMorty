package com.example.rickandmortytest.presentation.ui.characterdetail

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.rickandmortytest.R
import com.example.rickandmortytest.databinding.FragmentDetailBinding
import com.example.rickandmortytest.presentation.base.BaseFragment
import com.example.rickandmortytest.presentation.utils.UIState

class CharacterDetailFragment : BaseFragment(R.layout.fragment_character_detail) {

    private val binding: FragmentDetailBinding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel: CharacterDetailViewModel by viewModels()
    private var id = 1
    private var idEpisode = arrayListOf<Int>()

    override fun setupRequests() {
        super.setupRequests()
        receiveId()
        viewModel.getCharacter(id)
    }

    override fun setupSubscribers() {
        super.setupSubscribers()
        viewModel.getCharacterState.collectUIState(
            state = { binding.progressBar.isVisible = it is UIState.Loading },
            onSuccess = {
                it.location.url?.substringAfterLast("/")
                    ?.let { it1 -> viewModel.getLocation(it1.toInt()) }
                it.episode?.map { id ->
                    idEpisode.add(id.substringAfterLast("/").toInt())
                }
                viewModel.getEpisode(idEpisode)
                Glide.with(binding.imageView).load(it.image).into(binding.imageView)
                binding.tvDescription.text = it.name
            }
        )

        viewModel.getLocationState.collectUIState(
            state = null,
            onSuccess = {
                Log.e("ololo", "DetailFragment: ${it.name}")
            }
        )

        viewModel.getEpisodeState.collectUIState(
            state = {},
            onSuccess = {
                Log.e("ololo", "DetailFragment: ${it}")
            }
        )
    }

    private fun receiveId() {
        arguments?.let {
            id = it.getInt("key")
        }
    }
}