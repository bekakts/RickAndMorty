package com.example.rickandmortytest.domain.usecases.character

import com.example.rickandmortytest.domain.repository.Repository

class GetCharacters(private val repository: Repository) {


  operator fun invoke(name:String?=null) = repository.getCharacters(name)

}