package com.example.rickandmortytest.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortytest.domain.utils.Resource
import com.example.rickandmortytest.presentation.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


abstract class BaseViewModel : ViewModel() {


    protected fun <T:Any> Flow<PagingData<T>>.collectFlow(_state: MutableStateFlow<UIState<PagingData<T>>>) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UIState.Loading()
            try {
                this@collectFlow.cachedIn(viewModelScope).collectLatest {
                    _state.value = UIState.Success(it)
                }
            } catch (e: Exception) {
                _state.value = UIState.Error(e.message ?: "An error occurred")
            }
        }
    }

    protected fun <T:Any> Flow<Resource<T>>.collectDetailFlow(
        _state: MutableStateFlow<UIState<T>>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            this@collectDetailFlow.collect { res ->
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
