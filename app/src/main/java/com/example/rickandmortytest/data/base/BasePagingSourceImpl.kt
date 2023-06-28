package com.example.rickandmortytest.data.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class BasePagingRepositoryImpl {

    fun <T : Any, Entity : Any> getPage(
        pageClass: () -> BaseMyPagingSource<Entity, T>
    ): Flow<PagingData<T>> {
        val pagingSourceFactory: () -> PagingSource<Int, T> = {
            pageClass.invoke()
        }

        return Pager(
            config = PagingConfig(20),
            pagingSourceFactory = pagingSourceFactory
        ).flow.flowOn(Dispatchers.IO)
    }


}