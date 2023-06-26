package com.example.rickandmortytest.presentation.ui.episode

import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rickandmortytest.R
import com.example.rickandmortytest.databinding.FragmentEpisodeBinding
import com.example.rickandmortytest.presentation.base.BaseFragment
import com.example.rickandmortytest.presentation.utils.ConnectionLiveData
import com.example.rickandmortytest.presentation.utils.LoadStatePagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class EpisodeFragment : BaseFragment(R.layout.fragment_episode) {

    private val binding by viewBinding(FragmentEpisodeBinding::bind)
    private val viewModel: EpisodesViewModel by viewModel()
    private val episodesAdapter = EpisodesAdapter(this::onClick)
    private var name: String? = null
    private val loadStateAdapter = LoadStatePagerAdapter()
    private lateinit var cld: ConnectionLiveData

    override fun isConnection() {
        super.isConnection()
        cld = ConnectionLiveData(requireActivity().application)
        if (activity != null && isAdded) {
            cld.observe(viewLifecycleOwner) { answer ->
                if (answer) {
                    viewModel.getEpisodes(name)
                }
            }
        }
    }

    override fun setupSubscribers() {
        super.setupSubscribers()
        viewModel.getEpisodesState.collectUIState(
            state = {
            },
            onSuccess = {
                episodesAdapter.submitData(lifecycle, it)
                binding.recyclerView.scrollToPosition(0)
            }
        )
    }

    override fun initClickListeners() {
        super.initClickListeners()
        with(binding) {
            recyclerView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                if ((binding.recyclerView.adapter?.itemCount ?: 0) > 0) {
                    binding.shimmerViewContainer.isVisible = false
                }
            }
        }
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
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = episodesAdapter.withLoadStateFooter(loadStateAdapter)

        }
    }

    private fun onClick(id: Int) {
        findNavController().navigate(R.id.episodeDetailFragment, bundleOf("keyEpisode" to id))
    }

}