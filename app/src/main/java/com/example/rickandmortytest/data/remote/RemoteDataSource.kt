package com.example.rickandmortytest.data.remote

import com.example.rickandmortytest.data.base.BaseDataSource
import com.example.rickandmortytest.data.model.*
import com.example.rickandmortytest.domain.utils.Resource
import org.koin.core.module.Module
import org.koin.dsl.module

val remoteDataSource: Module = module {
    factory { RemoteDataSource(get()) }
}

class RemoteDataSource(private val api: Api) : BaseDataSource() {

    suspend fun getCharacter(id: List<Int>): Resource<List<CharacterEntity>> {
        return getResult { api.getCharacterByID(id) }
    }

    suspend fun getLocation(id: Int): Resource<LocationEntity> {
        return getResult { api.getLocationByID(id) }
    }

    suspend fun getEpisode(id: List<Int>): Resource<List<EpisodeEntity>> {
        return getResult { api.getEpisodeByID(id) }
    }

}