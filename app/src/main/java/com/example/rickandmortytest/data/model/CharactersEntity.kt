package com.example.rickandmortytest.data.model

import java.io.Serializable

data class CharactersEntity(
    val results: List<CharacterEntity>
) : Serializable

data class CharacterEntity(
    val id: Int? = null,
    val image: String? = null,
    val status: String? = null,
    val name: String? = null,
    val episode:List<String>? = null,
    val location :LocationCharacterEntity
) : Serializable


data class LocationCharacterEntity(
    val name:String? = null,
    val url:String? =null
)