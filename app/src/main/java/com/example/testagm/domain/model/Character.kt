package com.example.testagm.domain.model

import com.example.testagm.data.local.model.CharacterEntity

data class Character(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
) {
    fun toEntity(): CharacterEntity {
        return CharacterEntity().apply {
            this.name = this@Character.name
            this.status = this@Character.status
            this.species = this@Character.species
            this.type = this@Character.type
            this.gender = this@Character.gender
        }
    }
}
