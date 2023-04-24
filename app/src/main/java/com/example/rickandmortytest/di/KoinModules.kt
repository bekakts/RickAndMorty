package com.example.rickandmortytest.di

import com.example.rickandmortytest.data.remote.networkModules
import com.example.rickandmortytest.data.remote.remoteDataSource
import org.koin.core.module.Module

val koinModules: List<Module> = listOf(
    repoModules, viewModules, useCaseModules, networkModules, remoteDataSource
)