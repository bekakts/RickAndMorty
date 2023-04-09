package com.example.rickandmortytest.domain.repository

import com.example.rickandmortytest.domain.model.Character
import com.example.rickandmortytest.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getCharacters(): Flow<Resource<List<Character>>>

}