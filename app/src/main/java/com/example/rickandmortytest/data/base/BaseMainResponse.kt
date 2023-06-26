package com.example.rickandmortytest.data.base

data class BaseMainResponse<T>(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<T>? = null
)