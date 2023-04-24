package com.example.rickandmortytest.presentation.ui.characters

import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rickandmortytest.R
import com.example.rickandmortytest.databinding.FragmentCharactersBinding
import com.example.rickandmortytest.presentation.base.BaseFragment
import com.example.rickandmortytest.presentation.utils.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment : BaseFragment(R.layout.fragment_characters) {

    private val binding by viewBinding(FragmentCharactersBinding::bind)
    private val viewModel: CharactersViewModel by viewModel()
    private val characterAdapter = CharacterAdapter(this::onClick)
    private var name: String? = null

    override fun setupRequests() {
        super.setupRequests()
        viewModel.getCharacter(name)
    }

    override fun setupSubscribers() {
        super.setupSubscribers()
        viewModel.getCharacterState.collectUIState(
            state = {
                binding.bottomProgress.isVisible = it is UIState.Loading
            },
            onSuccess = {
                binding.progressBar.progress.isVisible = false
                characterAdapter.submitData(lifecycle, it)
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
                        viewModel.getCharacter(name)
                    }
                    return false
                }
            })
        }
    }

    private fun setupRecyclerView() {
       with(binding){
           recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
           recyclerView.adapter = characterAdapter
       }
    }

    private fun onClick(id:Int){
        findNavController().navigate(R.id.characterDetailFragment, bundleOf("key" to id))
    }
}