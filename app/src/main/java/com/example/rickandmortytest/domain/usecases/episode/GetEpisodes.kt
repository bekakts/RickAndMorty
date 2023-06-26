package com.example.rickandmortytest.domain.usecases.episode

import com.example.rickandmortytest.domain.paging.PagingSource

class GetEpisodes(private val pagingSource: PagingSource) {

    operator fun invoke(name: String? = null) = pagingSource.getEpisodes(name)

}