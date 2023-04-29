package com.example.rickandmortytest.presentation.ui.episodedetail

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rickandmortytest.R
import com.example.rickandmortytest.databinding.FragmentEpisodeDetailBinding
import com.example.rickandmortytest.presentation.base.BaseFragment
import com.example.rickandmortytest.presentation.utils.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel


class EpisodeDetailFragment : BaseFragment(R.layout.fragment_episode_detail) {

    private val binding: FragmentEpisodeDetailBinding by viewBinding(FragmentEpisodeDetailBinding::bind)
    private val viewModel: EpisodeDetailViewModel by viewModel()
    private val characterAdapter = EpisodeDetailAdapter(this::onClick)
    private var id = arrayListOf<Int>()
    private val idCharacter = arrayListOf<Int>()


    override fun setupRequests() {
        super.setupRequests()
        recieveId()
        viewModel.getEpisode(id)
    }

    override fun setupSubscribers() {
        super.setupSubscribers()
        with(binding) {
            viewModel.getEpisodeState.collectUIState(
                state = {
                    binding.progress.progress.isVisible = it is UIState.Loading
                    binding.characteristicContainer.isVisible = it !is UIState.Loading
                },
                onSuccess = {
                    if (it[0].characters?.isNotEmpty() == true) {
                        it[0].characters?.map { id ->
                            idCharacter.add(id.substringAfterLast("/").toInt())
                        }
                    }
                    viewModel.getCharacter(idCharacter)
                    tvGreenName.text = it[0].name
                    tvGreenEpisode.text = it[0].episode
                    tvGreenAirDate.text = it[0].air_date
                }
            )

            viewModel.getCharacterState.collectUIState(
                state = null,
                onSuccess = {
                    binding.recyclerEpisode.isVisible = true
                    characterAdapter.submitList(it)
                    binding.shimmerEpisodeDetail.isVisible = false
                    binding.recyclerEpisode.isVisible = true
                }
            )
        }
    }

    override fun initialize() {
        super.initialize()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        with(binding) {
            recyclerEpisode.layoutManager = LinearLayoutManager(requireContext())
            recyclerEpisode.adapter = characterAdapter
        }
    }

    private fun recieveId() {
        arguments?.let {
            id.clear()
            id.add(it.getInt("keyEpisode"))
        }
    }

    private fun onClick(id: Int) {
        findNavController().navigate(R.id.characterDetailFragment, bundleOf("key" to id))
    }
}