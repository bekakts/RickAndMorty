package com.example.rickandmortytest.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.rickandmortytest.data.model.CharacterEntity
import com.example.rickandmortytest.domain.model.Character
import com.example.rickandmortytest.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    //fun getCharacters(): Flow<Resource<Pager<Int,CharacterEntity>>>
    fun getCharacters(): Flow<PagingData<Character>>

}