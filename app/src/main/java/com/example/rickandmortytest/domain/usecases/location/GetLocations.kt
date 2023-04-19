package com.example.rickandmortytest.domain.usecases.location

import com.example.rickandmortytest.data.repository.RepositoryImpl
import com.example.rickandmortytest.domain.repository.Repository

class GetLocations {

    private val repository:Repository by lazy {
        RepositoryImpl()
    }

    operator fun invoke(name:String? = null) = repository.getLocations(name)

}