package com.example.rickandmortytest.domain.usecases.location

import com.example.rickandmortytest.domain.repository.Repository

class GetLocations(private val repository: Repository) {

    operator fun invoke(name: String? = null) = repository.getLocations(name)

}