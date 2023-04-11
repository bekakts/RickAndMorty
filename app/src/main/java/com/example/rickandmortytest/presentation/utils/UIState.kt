package com.example.rickandmortytest.presentation.utils

import androidx.paging.Pager
import com.example.rickandmortytest.data.model.CharacterEntity

sealed class UIState<T> {
    class Loading<T>: UIState<T>()
    class Success<T>(val data: Any): UIState<T>()
    class Error<T>(val message: String): UIState<T>()
    class Empty<T>: UIState<T>()
}