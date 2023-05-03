package com.example.rickandmortytest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortytest.data.model.mappers.toCharacter
import com.example.rickandmortytest.data.model.mappers.toEpisode
import com.example.rickandmortytest.data.model.mappers.toLocation
import com.example.rickandmortytest.data.pagingsource.characters.CharactersPagingSource
import com.example.rickandmortytest.data.pagingsource.episodes.EpisodesPagingSource
import com.example.rickandmortytest.data.pagingsource.locations.LocationsPagingSource
import com.example.rickandmortytest.data.remote.Api
import com.example.rickandmortytest.data.remote.RemoteDataSource
import com.example.rickandmortytest.domain.model.Character
import com.example.rickandmortytest.domain.model.Episode
import com.example.rickandmortytest.domain.model.Location
import com.example.rickandmortytest.domain.repository.Repository
import com.example.rickandmortytest.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoryImpl(private val dataSource: RemoteDataSource, private val api: Api) : Repository {

    override fun getCharacters(
        name: String?,
        status: String?,
        species: String?,
        gender: String?
    ): Flow<PagingData<Character>> {
        val remotePagingSource = CharactersPagingSource(name, status, species, gender,
            requestFunction = { page, names, statuses, specieses, genders ->
                api.getCharacters(
                    page,
                    names,
                    statuses,
                    specieses,
                    genders
                )
            },
            mapper = { response -> response.results.map { it.toCharacter() } }
        )
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { remotePagingSource }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getLocations(names: String?): Flow<PagingData<Location>> {
        val remotePagingSource = LocationsPagingSource(names,
            requestFunction = { page, name -> api.getLocations(page, name) },
            mapper = { response -> response.results.map { it.toLocation() } }
        )
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { remotePagingSource }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getEpisodes(names: String?): Flow<PagingData<Episode>> {
        val remotePagingSource = EpisodesPagingSource(names,
            requestFunction = { page, name -> api.getEpisodes(page, name) },
            mapper = { response -> response.results.map { it.toEpisode() } }
        )
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { remotePagingSource }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getCharacter(id: List<Int>): Flow<Resource<List<Character>>> =
        flow {
            emit(Resource.Loading())
            val response = dataSource.getCharacter(id).data?.map { it.toCharacter() }
            emit(Resource.Success(response))
        }.flowOn(Dispatchers.IO)

    override fun getLocation(id: Int): Flow<Resource<Location>> = flow {
        emit(Resource.Loading())
        val response = dataSource.getLocation(id).data?.toLocation()
        emit(Resource.Success(response))
    }.flowOn(Dispatchers.IO)

    override fun getEpisode(id: List<Int>): Flow<Resource<List<Episode>>> = flow {
        emit(Resource.Loading())
        val response = dataSource.getEpisode(id).data?.map { it.toEpisode() }
        emit(Resource.Success(response))
    }.flowOn(Dispatchers.IO)

}