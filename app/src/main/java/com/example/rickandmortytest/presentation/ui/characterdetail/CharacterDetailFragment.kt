package com.example.rickandmortytest.presentation.ui.characterdetail

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.rickandmortytest.R
import com.example.rickandmortytest.databinding.FragmentCharacterDetailBinding
import com.example.rickandmortytest.presentation.base.BaseFragment
import com.example.rickandmortytest.presentation.utils.UIState

class CharacterDetailFragment : BaseFragment(R.layout.fragment_character_detail) {

    private val binding: FragmentCharacterDetailBinding by viewBinding(
        FragmentCharacterDetailBinding::bind
    )
    private val viewModel: CharacterDetailViewModel by viewModels()
    private var id = 1
    private var idEpisode = arrayListOf<Int>()
    private val episodeAdapter = CharacterEpisodeAdapter()

    override fun setupRequests() {
        super.setupRequests()
        receiveId()
        viewModel.getCharacter(id)
    }

    override fun initialize() {
        super.initialize()
        setUpRecyclerView()
    }
    private fun setUpRecyclerView(){
        with(binding){
            recyclerEpisode.layoutManager = LinearLayoutManager(requireContext())
            recyclerEpisode.adapter = episodeAdapter
        }
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
                with(binding) {
                    viewModel.getEpisode(idEpisode)

                    Glide.with(imgCharacter).load(it.image).into(imgCharacter)
                    tvName.text = it.name
                    tvGreenSpecies.text = it.species
                    tvGreenGender.text = it.gender
                    tvGreenOrigin.text = it.origin.name
                    tvGreenLocation.text = it.location.name
                }
            }
        )

        viewModel.getLocationState.collectUIState(
            state = null,
            onSuccess = {
                Log.e("ololo", "DetailFragment: ${it.name}")
            }
        )

        viewModel.getEpisodeState.collectUIState(
            state = {
                    binding.progressEpisode.isVisible = it is UIState.Loading
            },
            onSuccess = {
                episodeAdapter.submitList(it)
                Log.e("olololo", "DetailFragment: ${it}")
            }
        )
    }

    private fun receiveId() {
        arguments?.let {
            id = it.getInt("key")
        }
    }
}