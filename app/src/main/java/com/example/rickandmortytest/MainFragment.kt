package com.example.rickandmortytest

import android.util.Log
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rickandmortytest.databinding.FragmentMainBinding
import com.example.rickandmortytest.presentation.MainViewModel
import com.example.rickandmortytest.presentation.base.BaseFragment

class MainFragment :BaseFragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()


    override fun setupRequests() {
        super.setupRequests()
        viewModel.getCharacter()
    }

    override fun setupSubscribers() {
        super.setupSubscribers()
        viewModel.getCharacterState.collectUIState(
            state = {

            },
            onSuccess = {
                Log.e("ololo",it[0].name.toString())
            }
        )
    }



}