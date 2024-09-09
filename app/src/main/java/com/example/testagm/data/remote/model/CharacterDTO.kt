package com.example.testagm.data.remote.model

import com.example.testagm.domain.model.Character

data class CharacterDTO(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
) {
    fun toDomain(): Character {
        return Character(
            id = this.id,
            name = this.name,
            status = this.status,
            species = this.species,
            type = this.type,
            gender = this.gender,
            image = this.image
        )
    }
}