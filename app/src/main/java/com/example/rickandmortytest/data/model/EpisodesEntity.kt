package com.example.rickandmortytest.data.model
import java.io.Serializable

data class EpisodesEntity(
    val results:List<EpisodeEntity>
):Serializable

data class EpisodeEntity(
    val id:Int,
    val name: String? = null,
    val air_date: String? = null,
    val episode:String?= null,
    val characters:List<String>
):Serializable
