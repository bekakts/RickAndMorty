package com.example.rickandmortytest.data.mappers

import com.example.rickandmortytest.data.model.CharacterEntity
import com.example.rickandmortytest.domain.model.Character


fun CharacterEntity.toCharacter(): Character{
    return Character(
        id,
        image,
        status,
        name,
    )
}
