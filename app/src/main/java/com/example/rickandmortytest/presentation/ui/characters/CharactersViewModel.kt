package com.example.rickandmortytest.presentation.ui.characters

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.rickandmortytest.domain.usecases.character.GetCharacters
import com.example.rickandmortytest.presentation.base.BaseViewModel
import com.example.rickandmortytest.presentation.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharactersViewModel(private val getCharacterUseCase: GetCharacters) : BaseViewModel() {

    private val _getCharacterState =
        MutableStateFlow<UIState<PagingData<com.example.rickandmortytest.domain.model.Character>>>(
            UIState.Empty()
        )
    val getCharacterState = _getCharacterState.asStateFlow()

    fun getCharacter(name: String?, status: String?, species: String?, gender: String?) {
        invalidate()
        getCharacterUseCase(name, status, species, gender).collectFlow(_getCharacterState)
    }

    private fun invalidate() {
        _getCharacterState.value = UIState.Empty()
    }

    val status = MutableLiveData<String>()
    val species = MutableLiveData<String>()
    val gender = MutableLiveData<String>()

    fun addFilter(status: String?, species: String?, gender: String?) {
        this.status.value = status
        this.species.value = species
        this.gender.value = gender
    }

}