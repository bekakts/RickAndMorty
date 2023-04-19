package com.example.rickandmortytest.domain.usecases.location

import com.example.rickandmortytest.data.repository.RepositoryImpl
import com.example.rickandmortytest.domain.repository.Repository

class GetLocation {

    private val repository: Repository by lazy {
        RepositoryImpl()
    }

    operator fun invoke(id:Int) = repository.getLocation(id)

}