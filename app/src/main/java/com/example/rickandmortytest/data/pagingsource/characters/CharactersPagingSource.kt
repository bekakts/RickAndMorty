package com.example.rickandmortytest.data.pagingsource.characters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.Response
import com.example.rickandmortytest.domain.model.Character

class CharactersPagingSource<T : Any>(
    private val name:String? = null,
    private val requestFunction: suspend (page: Int,name:String?) -> Response<T>,
    private val mapper: (T) -> List<Character>
) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int,Character>): Int?{
        return state.anchorPosition?.let{
            val page = state.closestPageToPosition(it)?.prevKey?:1
            page-1
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,Character> {
        return try {
            val currentPage = params.key ?: 1
            val response = requestFunction(currentPage,name)

            if (response.isSuccessful) {
                val data = response.body()?.let { mapper(it) } ?: emptyList()
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

