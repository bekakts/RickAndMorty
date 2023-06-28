package com.example.rickandmortytest.data.base

import com.example.rickandmortytest.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseRepository {
    protected fun <T> fetchData(
        apiCall: suspend () -> Resource<T>
    ): Flow<Resource<T>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiCall.invoke()
            emit(response)
        } catch (exception: Exception) {
            emit(Resource.Error(exception.message))
        }
    }.flowOn(Dispatchers.IO)
}
