package com.example.rickandmortytest.data.model

import java.io.Serializable

data class LocationsEntity(
    val results: List<LocationEntity>
) : Serializable

data class LocationEntity(
    val id: Int,
    val name: String? = null,
    val type: String? = null,
    val dimension: String? = null,
    val residents: List<String>? = null,
) : Serializable