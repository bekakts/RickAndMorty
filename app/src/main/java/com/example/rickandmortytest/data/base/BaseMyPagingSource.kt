package com.example.rickandmortytest.data.base

import androidx.paging.PagingSource
import androidx.paging.PagingState

class BaseMyPagingSource<Entity,Value:Any>(
    private val request:suspend(position: Int) -> BaseMainResponse<Entity>,
    private val mapper:(Entity) ->Value
):PagingSource<Int,Value>() {

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let {
            val page = state.closestPageToPosition(it)?.prevKey ?: 1
            page - 1
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        return try {
            val currentPage = params.key?:1
            val response = request(currentPage)
                val data = response.results?.map { mapper(it) }?: emptyList()
                LoadResult.Page(
                    data = data,
                    prevKey = if (currentPage==1) null else currentPage -1,
                    nextKey = if (data.isEmpty()) null else currentPage +1
                )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}


