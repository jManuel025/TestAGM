package com.example.testagm.ui.state

import com.example.testagm.domain.model.Character
import com.example.testagm.domain.model.Info

/**
 * Representa el estado de la pantalla de personajes obtenidos de la API.
 * @property info Información adicional sobre la paginación de los resultados.
 * @property results Lista de personajes obtenidos en la consulta.
 * @property isLoading Indica si los datos están siendo cargados actualmente.
 * @property error Mensaje de error en caso de que la carga de datos falle.
 */
data class CharactersState(
    val info: Info? = null,
    val results: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
