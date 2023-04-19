package com.example.rickandmortytest.data.remote

import com.example.rickandmortytest.data.base.BaseDataSource
import com.example.rickandmortytest.data.model.CharacterEntity
import com.example.rickandmortytest.data.model.EpisodeEntity
import com.example.rickandmortytest.data.model.EpisodesEntity
import com.example.rickandmortytest.data.model.LocationEntity
import com.example.rickandmortytest.domain.utils.Resource

class RemoteDataSource() : BaseDataSource() {

    private val api: Api by lazy {
        RetrofitClient.create()
    }

    suspend fun getCharacter(id: Int): Resource<CharacterEntity> {

        return getResult { api.getCharacterByID(id) }
    }


    suspend fun getLocation(id:Int):Resource<LocationEntity>{
        return getResult { api.getLocationByID(id) }
    }

    suspend fun getEpisode(id:List<Int>):Resource<List<EpisodeEntity>>{
        return getResult { api.getEpisodeByID(id) }
    }

}