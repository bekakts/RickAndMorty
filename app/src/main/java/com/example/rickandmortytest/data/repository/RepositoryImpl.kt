package com.example.rickandmortytest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortytest.RetrofitClient
import com.example.rickandmortytest.data.remote.Api
import com.example.rickandmortytest.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl : Repository {


    private val api: Api by lazy {
        RetrofitClient.create()
    }

    override fun getCharacters(): Flow<PagingData<com.example.rickandmortytest.domain.model.Character>> {
        return Pager(
            config = PagingConfig(pageSize = 2),
            pagingSourceFactory = { RemotePagingSource(api) }
        ).flow
    }



}