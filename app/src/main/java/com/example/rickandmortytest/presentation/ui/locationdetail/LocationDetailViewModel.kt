package com.example.rickandmortytest.presentation.ui.locationdetail

import com.example.rickandmortytest.domain.usecases.character.GetCharacter
import com.example.rickandmortytest.domain.usecases.location.GetLocation
import com.example.rickandmortytest.presentation.base.BaseViewModel
import com.example.rickandmortytest.presentation.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.rickandmortytest.domain.model.Character
import com.example.rickandmortytest.domain.model.Location
import kotlinx.coroutines.flow.asStateFlow

class LocationDetailViewModel(
    private val getCharacterUseCase: GetCharacter,
    private val getLocationUseCase: GetLocation
) : BaseViewModel() {

    private val _getCharacterState = MutableStateFlow<UIState<List<Character>>>(UIState.Empty())
    val getCharacterState = _getCharacterState.asStateFlow()

    fun getCharacter(id: List<Int>) {
        getCharacterUseCase(id).collectDetailFlow(_getCharacterState)
    }

    private val _getLocationState = MutableStateFlow<UIState<Location>>(UIState.Empty())
    val getLocationState = _getLocationState.asStateFlow()

    fun getLocation(id: Int) {
        getLocationUseCase(id).collectDetailFlow(_getLocationState)
    }


}