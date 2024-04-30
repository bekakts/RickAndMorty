package com.example.rickandmortytest.data.pagingsource

import androidx.paging.PagingData
import com.example.rickandmortytest.data.base.BaseMyPagingSource
import com.example.rickandmortytest.data.base.BasePagingRepositoryImpl
import com.example.rickandmortytest.data.model.mappers.toCharacter
import com.example.rickandmortytest.data.model.mappers.toEpisode
import com.example.rickandmortytest.data.model.mappers.toLocation
import com.example.rickandmortytest.data.remote.Api
import com.example.rickandmortytest.domain.model.Character
import com.example.rickandmortytest.domain.model.Episode
import com.example.rickandmortytest.domain.model.Location
import com.example.rickandmortytest.domain.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class PagingSourceImpl(private val api: Api) :
    PagingSource, BasePagingRepositoryImpl() {
    override fun getCharacters(
        name: String?,
        status: String?,
        species: String?,
        gender: String?
    ): Flow<PagingData<Character>> {
        return getPage {
            BaseMyPagingSource({ position ->
                api.getCharacters(position, name, status, species, gender)
            },
                mapper = { response -> response.toCharacter() })
        }
    }

    override fun getLocations(names: String?): Flow<PagingData<Location>> {
        return getPage {
            BaseMyPagingSource({ position ->
                api.getLocations(position, names)
            },
                mapper = { response -> response.toLocation() }
            )
        }
    }

    override fun getEpisodes(names: String?): Flow<PagingData<Episode>> {
        return getPage {
            BaseMyPagingSource({ position ->
                api.getEpisodes(position, names)
            },
                mapper = { response -> response.toEpisode() }
            )
        }
    }
}