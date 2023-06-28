package com.example.rickandmortytest.data.repository

import com.example.rickandmortytest.data.base.BaseRepository
import com.example.rickandmortytest.data.model.mappers.*
import com.example.rickandmortytest.data.remote.RemoteDataSource
import com.example.rickandmortytest.domain.model.*
import com.example.rickandmortytest.domain.repository.Repository
import com.example.rickandmortytest.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoryImpl(private val dataSource: RemoteDataSource) : Repository,BaseRepository() {
    override fun getCharacter(id: List<Int>): Flow<Resource<List<Character>>> = fetchData {
        val response = dataSource.getCharacter(id)
        Resource.Success(response.data?.map { it.toCharacter() }?: emptyList())
    }

    override fun getLocation(id: Int): Flow<Resource<Location>> = fetchData {
        val response = dataSource.getLocation(id)
        Resource.Success(response.data?.toLocation())
    }

    override fun getEpisode(id: List<Int>): Flow<Resource<List<Episode>>> = fetchData{
        val response = dataSource.getEpisode(id)
        Resource.Success(response.data?.map { it.toEpisode() })
    }

}