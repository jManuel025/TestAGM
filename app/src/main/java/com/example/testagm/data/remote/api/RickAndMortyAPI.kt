package com.example.testagm.data.remote.api

import com.example.testagm.data.remote.model.CharactersEndpointDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyAPI {

    /**
     * Obtiene la respuesta del endpoint character de la API.
     * @param page Número de la página para la paginación de los resultados.
     * @return Un objeto `CharactersEndpointDTO` que contiene la información sobre los personajes y la paginacion
     */
    @GET("character")
    suspend fun fetchCharactersEndpoint(
        @Query("page") page: Int
    ): CharactersEndpointDTO
}