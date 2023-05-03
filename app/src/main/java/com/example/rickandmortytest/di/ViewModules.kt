package com.example.rickandmortytest.di

import com.example.rickandmortytest.presentation.ui.characterdetail.CharacterDetailViewModel
import com.example.rickandmortytest.presentation.ui.characters.CharactersViewModel
import com.example.rickandmortytest.presentation.ui.episode.EpisodesViewModel
import com.example.rickandmortytest.presentation.ui.episodedetail.EpisodeDetailViewModel
import com.example.rickandmortytest.presentation.ui.location.LocationsViewModel
import com.example.rickandmortytest.presentation.ui.locationdetail.LocationDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModules: Module = module {
    viewModel { CharactersViewModel(get()) }
    viewModel { CharacterDetailViewModel(get(), get()) }
    viewModel { EpisodesViewModel(get()) }
    viewModel { EpisodeDetailViewModel(get(), get()) }
    viewModel { LocationsViewModel(get()) }
    viewModel { LocationDetailViewModel(get(), get()) }
}