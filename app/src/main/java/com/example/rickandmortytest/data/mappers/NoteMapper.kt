package com.example.rickandmortytest.data.mappers

import com.example.rickandmortytest.data.model.CharacterEntity
import com.example.rickandmortytest.data.model.EpisodeEntity
import com.example.rickandmortytest.data.model.LocationCharacterEntity
import com.example.rickandmortytest.data.model.LocationEntity
import com.example.rickandmortytest.domain.model.Character
import com.example.rickandmortytest.domain.model.Episode
import com.example.rickandmortytest.domain.model.Location
import com.example.rickandmortytest.domain.model.LocationCharacter


fun CharacterEntity.toCharacter(): Character{
    return Character(
        id,
        image,
        status,
        name,
        episode,
        location.toLocationCharacter()
    )
}

fun LocationCharacterEntity.toLocationCharacter(): LocationCharacter{
    return LocationCharacter(
        name,
        url
    )
}


fun LocationEntity.toLocation():Location{
    return Location(
        id,
        name,
        type,
        dimension,
        residents
    )
}

fun EpisodeEntity.toEpisode():Episode{
    return Episode(
        id,
        name,
        air_date,
        episode,
        characters
    )
}