package com.example.rickandmortytest.data.remote

import com.example.rickandmortytest.data.model.CharactersEntity
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("character")
    suspend fun getCharacters(): Response<CharactersEntity>

}