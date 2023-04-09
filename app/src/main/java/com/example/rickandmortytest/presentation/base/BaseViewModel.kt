package com.example.rickandmortytest.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortytest.domain.utils.Resource
import com.example.rickandmortytest.presentation.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel : ViewModel() {

   protected fun <T> Flow<Resource<T>>.collectFlow(
        _state: MutableStateFlow<UIState<T>>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            this@collectFlow.collect { res ->
                when (res) {
                    is Resource.Error -> {
                        if (res.message != null) {
                            _state.value = UIState.Error(res.message)
                        }
                    }
                    is Resource.Loading -> {
                        _state.value = UIState.Loading()
                    }
                    is Resource.Success -> {
                        if (res.data != null) {
                            _state.value = UIState.Success(res.data)
                        }
                    }
                }
            }
        }
    }
}