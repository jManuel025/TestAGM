package com.example.testagm.data.remote.model

data class CharactersEndpointDTO(
    val info: InfoDTO,
    val results: List<CharacterDTO>
)