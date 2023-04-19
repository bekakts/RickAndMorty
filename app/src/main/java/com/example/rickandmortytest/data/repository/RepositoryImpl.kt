package com.example.rickandmortytest.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortytest.data.mappers.toCharacter
import com.example.rickandmortytest.data.mappers.toEpisode
import com.example.rickandmortytest.data.mappers.toLocation
import com.example.rickandmortytest.data.model.LocationEntity
import com.example.rickandmortytest.data.pagingsource.characters.CharactersPagingSource
import com.example.rickandmortytest.data.pagingsource.episodes.EpisodesPagingSource
import com.example.rickandmortytest.data.pagingsource.locations.LocationsPagingSource
import com.example.rickandmortytest.data.remote.Api
import com.example.rickandmortytest.data.remote.RemoteDataSource
import com.example.rickandmortytest.data.remote.RetrofitClient
import com.example.rickandmortytest.domain.model.Episode
import com.example.rickandmortytest.domain.model.Location
import com.example.rickandmortytest.domain.repository.Repository
import com.example.rickandmortytest.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoryImpl : Repository {


    private val api: Api by lazy {
        RetrofitClient.create()
    }

    private val dataSource: RemoteDataSource by lazy {
        RemoteDataSource()
    }

    override fun getCharacters(names:String?): Flow<PagingData<com.example.rickandmortytest.domain.model.Character>> {
        val remotePagingSource = CharactersPagingSource(names,
            requestFunction = { page,name -> api.getCharacters(page,name) },
            mapper = { response -> response.results.map { it.toCharacter() } }
        )
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { remotePagingSource }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getLocations(names: String?): Flow<PagingData<Location>> {
        val remotePagingSource = LocationsPagingSource(names,
            requestFunction = {page,name -> api.getLocations(page,name)},
            mapper = { response-> response.results.map {it.toLocation() } }
            )
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {remotePagingSource}
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getEpisodes(names: String?): Flow<PagingData<Episode>> {
        val remotePagingSource = EpisodesPagingSource(names,
            requestFunction = {page,name ->api.getEpisodes(page, name)},
            mapper = {response-> response.results.map { it.toEpisode() }}
            )
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {remotePagingSource}
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getCharacter(id: Int): Flow<Resource<com.example.rickandmortytest.domain.model.Character>> = flow {
        emit(Resource.Loading())
        val response = dataSource.getCharacter(id).data?.toCharacter()

        Log.e("ololo","RepositoryImpl: ${response?.name}")
        emit(Resource.Success(response))
    }.flowOn(Dispatchers.IO)

    override fun getLocation(id: Int): Flow<Resource<Location>> = flow {
        emit(Resource.Loading())
        val response = dataSource.getLocation(id).data?.toLocation()
        emit(Resource.Success(response))
    }.flowOn(Dispatchers.IO)

    override fun getEpisode(id: List<Int>): Flow<Resource<List<Episode>>> = flow{
        emit(Resource.Loading())
        Log.e("ololo","RepositoryImplEpisode:ok")
        val response = dataSource.getEpisode(id).data?.map { it.toEpisode() }
        emit(Resource.Success(response))
    }.flowOn(Dispatchers.IO)

}