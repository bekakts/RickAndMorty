package com.example.rickandmortytest.domain.model

import java.io.Serializable

data class Characters (
    val results: List<Character>
):Serializable


data class Character(
    val id:Int?  = null,
    val image:String?=null,
    val status:String?=null,
    val name:String?=null
):Serializable