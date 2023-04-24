package com.example.rickandmortytest.domain.model
import java.io.Serializable

data class Location(
    val id:Int,
    val name:String?= null,
    val type: String? = null,
    val dimension: String? = null,
    val residents: List<String>? = null,
):Serializable