package com.example.rickandmortytest.di

import com.example.rickandmortytest.domain.usecases.character.GetCharacter
import com.example.rickandmortytest.domain.usecases.character.GetCharacters
import com.example.rickandmortytest.domain.usecases.episode.GetEpisode
import com.example.rickandmortytest.domain.usecases.episode.GetEpisodes
import com.example.rickandmortytest.domain.usecases.location.GetLocation
import com.example.rickandmortytest.domain.usecases.location.GetLocations
import org.koin.core.module.Module
import org.koin.dsl.module

val useCaseModules: Module = module {
    factory { GetCharacters(get()) }
    factory { GetCharacter(get()) }
    factory { GetEpisodes(get()) }
    factory { GetEpisode(get()) }
    factory { GetLocations(get()) }
    factory { GetLocation(get()) }
}