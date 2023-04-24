package com.example.rickandmortytest.presentation.ui.characterdetail

import com.example.rickandmortytest.domain.model.Episode
import com.example.rickandmortytest.domain.model.Location
import com.example.rickandmortytest.domain.usecases.character.GetCharacter
import com.example.rickandmortytest.domain.usecases.episode.GetEpisode
import com.example.rickandmortytest.domain.usecases.location.GetLocation
import com.example.rickandmortytest.presentation.base.BaseViewModel
import com.example.rickandmortytest.presentation.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterDetailViewModel(
    private val getCharacterUseCase: GetCharacter,
    private val getLocationUseCase: GetLocation,
    private val getEpisodeUseCase: GetEpisode
) : BaseViewModel() {

    private val _getCharacterState =
        MutableStateFlow<UIState<com.example.rickandmortytest.domain.model.Character>>(UIState.Empty())
    val getCharacterState = _getCharacterState.asStateFlow()

    fun getCharacter(id: Int) {
        getCharacterUseCase(id).collectDetailFlow(_getCharacterState)
    }

    private val _getLocationState = MutableStateFlow<UIState<Location>>(UIState.Empty())
    val getLocationState = _getLocationState.asStateFlow()

    fun getLocation(id: Int) {
        getLocationUseCase(id).collectDetailFlow(_getLocationState)
    }

    private val _getEpisodeState = MutableStateFlow<UIState<List<Episode>>>(UIState.Empty())
    val getEpisodeState = _getEpisodeState.asStateFlow()

    fun getEpisode(id: List<Int>) {
        getEpisodeUseCase(id).collectDetailFlow(_getEpisodeState)
    }
}