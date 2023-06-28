package com.example.rickandmortytest.presentation.ui.locationdetail

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rickandmortytest.R
import com.example.rickandmortytest.databinding.FragmentLocationDetailBinding
import com.example.rickandmortytest.presentation.base.BaseFragment
import com.example.rickandmortytest.presentation.utils.ConnectionLiveData
import com.example.rickandmortytest.presentation.utils.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel


class LocationDetailFragment : BaseFragment(R.layout.fragment_location_detail) {

    private val binding: FragmentLocationDetailBinding by viewBinding(FragmentLocationDetailBinding::bind)
    private val characterAdapter = LocationDetailAdapter(this::onClick)
    private val viewModel: LocationDetailViewModel by viewModel()
    private var id = 1
    private val idCharacters = arrayListOf<Int>()
    private lateinit var cld: ConnectionLiveData

    override fun initialize() {
        super.initialize()
        cld = ConnectionLiveData(requireActivity().application)
        receiveId()
        checkConnection(cld) { viewModel.getLocation(id) }
        setUpRecyclerView()
    }

    override fun initClickListeners() {
        super.initClickListeners()
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun setupSubscribers() {
        super.setupSubscribers()
        with(binding) {
            viewModel.getLocationState.collectUIState(
                state = {
                    binding.progress.progress.isVisible = it is UIState.Loading
                    binding.characteristicContainer.isVisible = it !is UIState.Loading
                },
                onSuccess = {
                    if (it.residents?.isNotEmpty() == true) {
                        it.residents.map { id ->
                            idCharacters.add(id.substringAfterLast("/").toInt())
                        }
                    }
                    viewModel.getCharacter(idCharacters)
                    tvGreenDimension.text = it.dimension
                    tvGreenName.text = it.name
                    tvGreenType.text = it.type
                }
            )

            viewModel.getCharacterState.collectUIState(
                state = null,
                onSuccess = {
                    characterAdapter.submitList(it)
                    binding.shimmerLocationDetail.isVisible = false
                    binding.recyclerLocation.isVisible = true
                }
            )
        }
    }

    private fun setUpRecyclerView() {
        with(binding) {
            recyclerLocation.layoutManager = LinearLayoutManager(requireContext())
            recyclerLocation.adapter = characterAdapter
        }
    }

    private fun receiveId() {
        arguments?.let {
            id = it.getInt("keyLocation")
        }
    }

    private fun onClick(id: Int) {
        findNavController().navigate(R.id.characterDetailFragment, bundleOf("key" to id))
    }

}