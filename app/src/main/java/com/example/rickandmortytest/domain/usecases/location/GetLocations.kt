package com.example.rickandmortytest.domain.usecases.location

import com.example.rickandmortytest.domain.paging.PagingSource

class GetLocations(private val pagingSource:PagingSource) {

    operator fun invoke(name: String? = null) = pagingSource.getLocations(name)

}