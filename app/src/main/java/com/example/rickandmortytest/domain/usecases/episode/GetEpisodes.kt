package com.example.rickandmortytest.domain.usecases.episode

import com.example.rickandmortytest.domain.repository.Repository

class GetEpisodes(private val repository: Repository) {

    operator fun invoke(name:String? = null)= repository.getEpisodes(name)

}