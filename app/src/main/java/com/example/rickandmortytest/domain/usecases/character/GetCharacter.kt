package com.example.rickandmortytest.domain.usecases.character

import com.example.rickandmortytest.data.repository.RepositoryImpl
import com.example.rickandmortytest.domain.repository.Repository

class GetCharacter {

    private val repository: Repository by lazy {
        RepositoryImpl()
    }

    operator fun invoke(id:Int) = repository.getCharacter(id)

}