package com.example.rickandmortytest.data.pagingsource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortytest.data.base.BaseMyPagingSource
import com.example.rickandmortytest.data.model.CharacterEntity
import com.example.rickandmortytest.data.model.EpisodeEntity
import com.example.rickandmortytest.data.model.LocationEntity
import com.example.rickandmortytest.data.model.mappers.toCharacter
import com.example.rickandmortytest.data.model.mappers.toEpisode
import com.example.rickandmortytest.data.model.mappers.toLocation
import com.example.rickandmortytest.data.remote.Api
import com.example.rickandmortytest.domain.paging.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.example.rickandmortytest.domain.model.Character
import com.example.rickandmortytest.domain.model.Episode
import com.example.rickandmortytest.domain.model.Location

class PagingSourceImpl(private val api: Api) :
    PagingSource {

    override fun getCharacters(
        name: String?,
        status: String?,
        species: String?,
        gender: String?
    ): Flow<PagingData<Character>> {
        val remotePagingSource =
            BaseMyPagingSource(
                request = { position ->
                    api.getCharacters(
                        position,
                        name,
                        status,
                        species,
                        gender
                    )
                },
                mapper = { response -> response.toCharacter() }
            )

        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { remotePagingSource }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getLocations(names: String?): Flow<PagingData<Location>> {
        val remotePagingSource = BaseMyPagingSource({ position ->
            api.getLocations(position, names)
        },
            mapper = { response -> response.toLocation() }
        )
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { remotePagingSource }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getEpisodes(names: String?): Flow<PagingData<Episode>> {
        val remotePagingSource = BaseMyPagingSource({ position ->
            api.getEpisodes(position, names)
        },
            mapper = { response -> response.toEpisode() }
        )
        return Pager(
            config = PagingConfig(20),
            pagingSourceFactory = { remotePagingSource }
        ).flow.flowOn(Dispatchers.IO)
    }
}