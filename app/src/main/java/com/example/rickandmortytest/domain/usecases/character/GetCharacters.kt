package com.example.rickandmortytest.domain.usecases.character

import com.example.rickandmortytest.domain.paging.PagingSource

class GetCharacters(private val pagingSource: PagingSource) {
    operator fun invoke(name: String?, status: String?, species: String?, gender: String?) =
        pagingSource.getCharacters(name, status, species, gender)

}