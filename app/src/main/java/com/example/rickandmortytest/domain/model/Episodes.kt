package com.example.rickandmortytest.domain.model

import java.io.Serializable

data class Episode(
    val id: Int,
    val name: String? = null,
    val air_date: String? = null,
    val episode: String? = null,
    val characters: List<String>? = null
) : Serializable