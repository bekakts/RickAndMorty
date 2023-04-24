package com.example.rickandmortytest.presentation.ui.episode

import androidx.paging.PagingData
import com.example.rickandmortytest.domain.model.Episode
import com.example.rickandmortytest.domain.usecases.episode.GetEpisodes
import com.example.rickandmortytest.presentation.base.BaseViewModel
import com.example.rickandmortytest.presentation.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EpisodesViewModel(private val getEpisodesUseCase: GetEpisodes):BaseViewModel() {

    private val _getEpisodesState = MutableStateFlow<UIState<PagingData<Episode>>>(UIState.Empty())
    val getEpisodesState = _getEpisodesState.asStateFlow()

    fun getEpisodes(name:String? = null){
        getEpisodesUseCase(name).collectFlow(_getEpisodesState)
    }

    fun invalidate(){
        _getEpisodesState.value = UIState.Empty()
    }



}