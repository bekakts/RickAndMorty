package com.example.rickandmortytest.presentation.ui.episode

import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rickandmortytest.R
import com.example.rickandmortytest.databinding.FragmentEpisodeBinding
import com.example.rickandmortytest.presentation.base.BaseFragment
import com.example.rickandmortytest.presentation.utils.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel


class EpisodeFragment : BaseFragment(R.layout.fragment_episode) {

    private val binding by viewBinding(FragmentEpisodeBinding::bind)
    private val viewModel: EpisodesViewModel by viewModel()
    private val episodesAdapter = EpisodesAdapter()
    private var name: String? = null


    override fun setupRequests() {
        super.setupRequests()
        viewModel.getEpisodes(name)
    }


    override fun setupSubscribers() {
        super.setupSubscribers()
        viewModel.getEpisodesState.collectUIState(
            state = {
                binding.bottomProgress.isVisible = it is UIState.Loading
            },
            onSuccess = {
                binding.progressBar.progress.isVisible = false
                episodesAdapter.submitData(lifecycle, it)
                binding.recyclerView.scrollToPosition(0)
            }
        )
    }

    override fun initialize() {
        super.initialize()
        setupRecyclerView()
        searchLogic()
    }

    private fun searchLogic() {
        with(binding) {
            searchCharacters.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    searchCharacters.clearFocus()
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    p0?.let {
                        name = it
                        viewModel.invalidate()
                        viewModel.getEpisodes(name)
                    }
                    return false
                }
            })
        }
    }

    private fun setupRecyclerView() {

        with(binding){
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = episodesAdapter
        }
    }

}