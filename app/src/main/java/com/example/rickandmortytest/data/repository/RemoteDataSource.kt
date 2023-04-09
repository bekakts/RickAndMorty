package com.example.rickandmortytest.data.repository

import android.util.Log
import com.example.rickandmortytest.BaseDataSource
import com.example.rickandmortytest.RetrofitClient
import com.example.rickandmortytest.data.model.CharactersEntity
import com.example.rickandmortytest.data.remote.Api
import com.example.rickandmortytest.domain.utils.Resource

class RemoteDataSource:BaseDataSource() {

    private val api: Api by lazy {
        RetrofitClient.create()
    }

    suspend fun getCharacters(): Resource<CharactersEntity>{
        val result = getResult {
            api.getCharacters()
        }
        return result

    }


}