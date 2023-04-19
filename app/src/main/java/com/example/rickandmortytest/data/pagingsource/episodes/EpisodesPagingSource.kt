package com.example.rickandmortytest.data.pagingsource.episodes

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortytest.domain.model.Episode
import retrofit2.Response

class EpisodesPagingSource<T>(
    private val name:String? = null,
    private val requestFunction: suspend (page:Int, name:String?) ->Response<T>,
    private val mapper:(T) ->List<Episode>
):PagingSource<Int,Episode>() {


    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition?.let{
            val page = state.closestPageToPosition(it)?.prevKey?:1
            page-1
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        return try {
            val currentPage = params.key ?: 1
            val response = requestFunction(currentPage,name)

            if (response.isSuccessful) {
                val data = response.body()?.let { mapper(it) } ?: emptyList()
                Log.e("ololo", "EpisodesPagingSource: $data")
                LoadResult.Page(
                    data = data,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (data.isEmpty()) null else currentPage + 1
                )
            } else {
                LoadResult.Error(Throwable("Request failed: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}