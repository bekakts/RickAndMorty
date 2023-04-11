package com.example.rickandmortytest.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.rickandmortytest.RetrofitClient
import com.example.rickandmortytest.data.model.CharacterEntity
import com.example.rickandmortytest.data.remote.Api
import com.example.rickandmortytest.data.repository.RemotePagingSource
import com.example.rickandmortytest.domain.usecases.GetCharacters
import com.example.rickandmortytest.domain.utils.Resource
import com.example.rickandmortytest.presentation.base.BaseViewModel
import com.example.rickandmortytest.presentation.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    private val getCharacterUseCase: GetCharacters by lazy {
        GetCharacters()
    }

    private val api: Api by lazy {
        RetrofitClient.create()
    }

    private val _getCharacterState =
        MutableStateFlow<UIState<PagingData<com.example.rickandmortytest.domain.model.Character>>>(
            UIState.Empty()
        )
    val getCharacterState = _getCharacterState.asStateFlow()

    fun getCharacter() {
        viewModelScope.launch {
            _getCharacterState.value = UIState.Loading()
            try {
                getCharacterUseCase().cachedIn(viewModelScope).collect {
                    _getCharacterState.value = UIState.Success(it)
                }
            }catch (e:Exception){
                _getCharacterState.value = UIState.Error(e.message ?:"An error occurred")
            }
        }
        //getCharacterUseCase().collectFlow(_getCharacterState)
    }

}