package com.example.rickandmortytest.domain.usecases.episode

import com.example.rickandmortytest.data.repository.RepositoryImpl
import com.example.rickandmortytest.domain.repository.Repository

class GetEpisode {


    private val repository: Repository by lazy {
        RepositoryImpl()
    }

    operator fun invoke(id:List<Int>) = repository.getEpisode(id)

}