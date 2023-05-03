package com.example.rickandmortytest.data.remote

import com.example.rickandmortytest.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("gender") gender: String? = null
    ): Response<CharactersEntity>

    @GET("character/{id}")
    suspend fun getCharacterByID(
        @Path("id") id: List<Int>
    ): Response<List<CharacterEntity>>

    @GET("location")
    suspend fun getLocations(
        @Query("page") page: Int,
        @Query("name") name: String? = null
    ): Response<LocationsEntity>

    @GET("location/{id}")
    suspend fun getLocationByID(
        @Path("id") id: Int
    ): Response<LocationEntity>

    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int,
        @Query("name") name: String? = null
    ): Response<EpisodesEntity>

    @GET("episode/{id}")
    suspend fun getEpisodeByID(
        @Path("id") id: List<Int>
    ): Response<List<EpisodeEntity>>
}