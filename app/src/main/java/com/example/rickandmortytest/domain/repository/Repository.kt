package com.example.rickandmortytest.domain.repository

import com.example.rickandmortytest.domain.model.Character
import com.example.rickandmortytest.domain.model.Episode
import com.example.rickandmortytest.domain.model.Location
import com.example.rickandmortytest.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getCharacter(id: List<Int>): Flow<Resource<List<Character>>>
    fun getLocation(id: Int): Flow<Resource<Location>>
    fun getEpisode(id: List<Int>): Flow<Resource<List<Episode>>>

}