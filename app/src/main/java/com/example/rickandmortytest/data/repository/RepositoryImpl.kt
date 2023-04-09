package com.example.rickandmortytest.data.repository

import com.example.rickandmortytest.data.mappers.toCharacter
import com.example.rickandmortytest.domain.model.Character
import com.example.rickandmortytest.domain.repository.Repository
import com.example.rickandmortytest.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoryImpl:Repository {

    private val dataSource = RemoteDataSource()

    override fun getCharacters(): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Loading())
        val response = dataSource.getCharacters().data?.results?.map {
            it.toCharacter()
        }
        emit(Resource.Success(response))
    }.flowOn(Dispatchers.IO)
}