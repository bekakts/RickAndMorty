package com.example.rickandmortytest.data.model.mappers

import com.example.rickandmortytest.data.model.*
import com.example.rickandmortytest.domain.model.*


fun CharacterEntity.toCharacter(): Character {
    return Character(
        id,
        image,
        status,
        name,
        episode,
        species,
        location.toLocationCharacter(),
        origin.toOrigin(),
        gender
    )
}

fun OriginEntity.toOrigin(): Origin {
    return Origin(
        name,
        url
    )
}

fun LocationCharacterEntity.toLocationCharacter(): LocationCharacter {
    return LocationCharacter(
        name,
        url
    )
}


fun LocationEntity.toLocation(): Location {
    return Location(
        id,
        name,
        type,
        dimension,
        residents
    )
}

fun EpisodeEntity.toEpisode(): Episode {
    return Episode(
        id,
        name,
        air_date,
        episode,
        characters
    )
}