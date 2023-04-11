package com.example.rickandmortytest

import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rickandmortytest.databinding.FragmentMainBinding
import com.example.rickandmortytest.domain.model.Character
import com.example.rickandmortytest.presentation.MainViewModel
import com.example.rickandmortytest.presentation.adapter.CharacterAdapter
import com.example.rickandmortytest.presentation.base.BaseFragment
import com.example.rickandmortytest.presentation.utils.UIState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()
    private val characterAdapter = CharacterAdapter()


    override fun setupRequests() {
        super.setupRequests()
        viewModel.getCharacter()
    }

    override fun setupSubscribers() {
        super.setupSubscribers()

        viewModel.getCharacterState.collectUIState(
            state = { itState ->
                Log.e("ololo","MainFragment hello: $itState")
                binding.bottomProgress.isVisible = itState is UIState.Loading

            },
            onSuccess = {
                lifecycleScope.launch {
                    characterAdapter.submitData(it as PagingData<Character>)
                }
            }
        )

    }

    override fun initialize() {
        super.initialize()

        setupRecyclerView()
        loadData()

    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = characterAdapter
            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
        }
    }

    private fun loadData() {

//        lifecycleScope.launch {
//            viewModel.listData.collect {
//                Log.d("aaa", "load: $it")
//                characterAdapter.submitData(it)
//            }
//
//        }
    }


}