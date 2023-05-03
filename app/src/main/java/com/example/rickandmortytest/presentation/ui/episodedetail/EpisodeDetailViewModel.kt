package com.example.rickandmortytest.presentation.ui.episodedetail

import com.example.rickandmortytest.domain.usecases.character.GetCharacter
import com.example.rickandmortytest.presentation.base.BaseViewModel
import com.example.rickandmortytest.presentation.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.rickandmortytest.domain.model.Character
import com.example.rickandmortytest.domain.model.Episode
import com.example.rickandmortytest.domain.usecases.episode.GetEpisode
import kotlinx.coroutines.flow.asStateFlow

class EpisodeDetailViewModel(
    private val getCharacterUseCase: GetCharacter,
    private val getEpisodeUseCase: GetEpisode
) : BaseViewModel() {

    private val _getCharacterState = MutableStateFlow<UIState<List<Character>>>(UIState.Empty())
    val getCharacterState = _getCharacterState.asStateFlow()

    fun getCharacter(id: List<Int>) {
        getCharacterUseCase(id).collectDetailFlow(_getCharacterState)
    }

    private val _getEpisodeState = MutableStateFlow<UIState<List<Episode>>>(UIState.Empty())
    val getEpisodeState = _getEpisodeState.asStateFlow()

    fun getEpisode(id: List<Int>) {
        getEpisodeUseCase(id).collectDetailFlow(_getEpisodeState)
    }


}