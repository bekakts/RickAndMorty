package com.example.rickandmortytest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortytest.data.remote.RetrofitClient
import com.example.rickandmortytest.data.mappers.toCharacter
import com.example.rickandmortytest.data.pagingsource.RemotePagingSource
import com.example.rickandmortytest.data.remote.Api
import com.example.rickandmortytest.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class RepositoryImpl : Repository {


    private val api: Api by lazy {
        RetrofitClient.create()
    }

    override fun getCharacters(names:String?): Flow<PagingData<com.example.rickandmortytest.domain.model.Character>> {
        val remotePagingSource = RemotePagingSource(names,
            requestFunction = { page,name -> api.getCharacters(page,name) },
            mapper = { response -> response.results.map { it.toCharacter() } }
        )
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { remotePagingSource }
        ).flow.flowOn(Dispatchers.IO)
    }
}