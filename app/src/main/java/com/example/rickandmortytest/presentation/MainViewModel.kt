package com.example.rickandmortytest.presentation

import com.example.rickandmortytest.domain.usecases.GetCharacters
import com.example.rickandmortytest.presentation.base.BaseViewModel
import com.example.rickandmortytest.presentation.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : BaseViewModel() {

    private val getCharacterUseCase: GetCharacters by lazy {
        GetCharacters()
    }

    private val _getCharacterState =
        MutableStateFlow<UIState<List<com.example.rickandmortytest.domain.model.Character>>>(UIState.Empty())
    val getCharacterState = _getCharacterState.asStateFlow()

    fun getCharacter(){
        getCharacterUseCase().collectFlow(_getCharacterState)
    }

}