package com.example.rickandmortytest.domain.usecases

import com.example.rickandmortytest.data.repository.RepositoryImpl
import com.example.rickandmortytest.domain.repository.Repository

class GetCharacters {

    private val repository: Repository by lazy {
        RepositoryImpl()
    }

    operator fun invoke() = repository.getCharacters()

}