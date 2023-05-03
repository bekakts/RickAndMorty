package com.example.rickandmortytest.domain.usecases.character

import com.example.rickandmortytest.domain.repository.Repository

class GetCharacter(private val repository: Repository) {

    operator fun invoke(id: List<Int>) = repository.getCharacter(id)

}