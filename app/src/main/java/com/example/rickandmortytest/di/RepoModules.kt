package com.example.rickandmortytest.di

import com.example.rickandmortytest.data.repository.RepositoryImpl
import com.example.rickandmortytest.domain.repository.Repository
import org.koin.core.module.Module
import org.koin.dsl.module

val repoModules: Module = module {
    single<Repository> { RepositoryImpl(get(),get()) }
}
