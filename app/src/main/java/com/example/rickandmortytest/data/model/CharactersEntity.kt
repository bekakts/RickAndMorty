package com.example.rickandmortytest.data.model

import java.io.Serializable

data class CharactersEntity (
    val results: List<CharacterEntity>
):Serializable

data class CharacterEntity(
    val id:Int?  = null,
    val image:String?=null,
    val status:String?=null,
    val name:String?=null
):Serializable