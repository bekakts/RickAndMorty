package com.example.rickandmortytest.domain.model

import java.io.Serializable

data class Characters(
    val results: List<Character>
) : Serializable

data class Character(
    val id: Int? = null,
    val image: String? = null,
    val status: String? = null,
    val name: String? = null,
    val episode:List<String>? = null,
    val species:String?= null,
    val location :LocationCharacter,
    val origin:Origin,
    val gender:String? = null
) : Serializable

data class Origin(
    val name:String? = null,
    val url:String? = null
)
data class LocationCharacter(
    val name:String? = null,
    val url:String? =null
)