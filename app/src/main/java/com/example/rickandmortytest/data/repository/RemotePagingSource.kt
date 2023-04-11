package com.example.rickandmortytest.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortytest.data.mappers.toCharacter
import com.example.rickandmortytest.data.remote.Api

class RemotePagingSource(
    private val apiService: Api
) : PagingSource<Int, com.example.rickandmortytest.domain.model.Character>() {


    override fun getRefreshKey(state: PagingState<Int, com.example.rickandmortytest.domain.model.Character>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.example.rickandmortytest.domain.model.Character> {

        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getCharacters(currentPage)
            val responseData = mutableListOf<com.example.rickandmortytest.domain.model.Character>()
            val data = response.body()?.results ?: emptyList()
            val answer = data.map {
                it.toCharacter()
            }

            responseData.addAll(answer)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}