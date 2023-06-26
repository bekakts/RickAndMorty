package com.example.rickandmortytest.di

import com.example.rickandmortytest.data.pagingsource.PagingSourceImpl
import com.example.rickandmortytest.domain.paging.PagingSource
import org.koin.core.module.Module
import org.koin.dsl.module

val pagingModules:Module = module {
    single<PagingSource> { PagingSourceImpl(get()) }
}