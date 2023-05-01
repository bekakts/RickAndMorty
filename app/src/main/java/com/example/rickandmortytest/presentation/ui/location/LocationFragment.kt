package com.example.rickandmortytest.presentation.ui.location

import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rickandmortytest.R
import com.example.rickandmortytest.databinding.FragmentLocationBinding
import com.example.rickandmortytest.presentation.base.BaseFragment
import com.example.rickandmortytest.presentation.utils.LoadStatePagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class LocationFragment : BaseFragment(R.layout.fragment_location) {

    private val binding by viewBinding(FragmentLocationBinding::bind)
    private val viewModel: LocationsViewModel by viewModel()
    private val locationsAdapter = LocationsAdapter(this::onClick)
    private var name: String? = null

    override fun setupRequests() {
        super.setupRequests()
        viewModel.getLocations(name)
    }

    override fun setupSubscribers() {
        super.setupSubscribers()
        viewModel.getLocationState.collectUIState(
            state = {
            },
            onSuccess = {
                locationsAdapter.submitData(lifecycle, it)
                binding.recyclerView.scrollToPosition(0)
            }
        )
    }

    override fun initClickListeners() {
        super.initClickListeners()
        with(binding) {
            recyclerView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                if ((recyclerView.adapter?.itemCount ?: 0) > 0) {
                    shimmerViewContainer.isVisible = false
                }
            }
        }
    }

    override fun initialize() {
        super.initialize()
        setUpRecyclerView()
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
                        viewModel.getLocations(name)
                    }
                    return false
                }
            })
        }
    }

    private fun setUpRecyclerView() {
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = locationsAdapter.withLoadStateFooter(LoadStatePagerAdapter())

        }
    }

    private fun onClick(id: Int) {
        findNavController().navigate(R.id.locationDetailFragment, bundleOf("keyLocation" to id))
    }

}