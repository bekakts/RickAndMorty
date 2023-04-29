package com.example.rickandmortytest.presentation.ui.characterdetail

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.rickandmortytest.R
import com.example.rickandmortytest.databinding.FragmentCharacterDetailBinding
import com.example.rickandmortytest.presentation.base.BaseFragment
import com.example.rickandmortytest.presentation.utils.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : BaseFragment(R.layout.fragment_character_detail) {

    private val binding: FragmentCharacterDetailBinding by viewBinding(
        FragmentCharacterDetailBinding::bind
    )
    private val viewModel: CharacterDetailViewModel by viewModel()
    private var id = arrayListOf<Int>()
    private var idEpisode = arrayListOf<Int>()
    private var idLocation: Int? = null
    private val episodeAdapter = CharacterEpisodeAdapter(this::onClick)

    override fun setupRequests() {
        super.setupRequests()
        receiveId()
        viewModel.getCharacter(id)
    }

    override fun initialize() {
        super.initialize()
        setUpRecyclerView()
    }

    override fun initClickListeners() {
        super.initClickListeners()
        with(binding) {
            tvGreenLocation.setOnClickListener {
                if (idLocation != null) {
                    findNavController().navigate(
                        R.id.locationDetailFragment,
                        bundleOf("keyLocation" to idLocation)
                    )
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        with(binding) {
            recyclerEpisode.layoutManager = LinearLayoutManager(requireContext())
            recyclerEpisode.adapter = episodeAdapter
        }
    }

    override fun setupSubscribers() {
        super.setupSubscribers()
        viewModel.getCharacterState.collectUIState(
            state = {
                binding.progress.progress.isVisible = it is UIState.Loading
                binding.detailCharacterContainer.isVisible = it !is UIState.Loading
            },
            onSuccess = {
                if (it[0].location.url?.isNotEmpty() == true) {
                    it[0].location.url?.substringAfterLast("/")
                        ?.let { it1 -> viewModel.getLocation(it1.toInt()) }
                    it[0].location.url?.substringAfterLast("/")
                        ?.let { it1 -> idLocation = it1.toInt() }
                }
                if (it[0].episode?.isNotEmpty() == true) {
                    it[0].episode?.map { id ->
                        idEpisode.add(id.substringAfterLast("/").toInt())
                    }
                }
                with(binding) {
                    viewModel.getEpisode(idEpisode)
                    Glide.with(imgCharacter).load(it[0].image).into(imgCharacter)
                    tvName.text = it[0].name
                    tvGreenSpecies.text = it[0].species
                    tvGreenGender.text = it[0].gender
                    tvGreenOrigin.text = it[0].origin.name
                    tvGreenLocation.text = it[0].location.name
                }
            }
        )

        viewModel.getLocationState.collectUIState(
            state = null,
            onSuccess = {
            }
        )

        viewModel.getEpisodeState.collectUIState(
            state = {
                //binding.progressEpisode.isVisible = it is UIState.Loading
            },
            onSuccess = {
                episodeAdapter.submitList(it)
            }
        )
    }

    private fun onClick(id: Int) {
        findNavController().navigate(R.id.episodeDetailFragment, bundleOf("keyEpisode" to id))
    }

    private fun receiveId() {
        arguments?.let {
            id.clear()
            id.add(it.getInt("key"))
        }
    }
}