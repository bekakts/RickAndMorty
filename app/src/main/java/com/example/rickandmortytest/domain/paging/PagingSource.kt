package com.example.rickandmortytest.domain.paging

import androidx.paging.PagingData
import com.example.rickandmortytest.domain.model.Episode
import com.example.rickandmortytest.domain.model.Location
import com.example.rickandmortytest.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface PagingSource {

    fun getCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ): Flow<PagingData<Character>>

    fun getLocations(names: String? = null): Flow<PagingData<Location>>
    fun getEpisodes(names: String? = null): Flow<PagingData<Episode>>


}