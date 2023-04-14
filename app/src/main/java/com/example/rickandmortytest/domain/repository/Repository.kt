package com.example.rickandmortytest.domain.repository

import androidx.paging.PagingData
import com.example.rickandmortytest.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getCharacters(names:String? = null): Flow<PagingData<Character>>

}