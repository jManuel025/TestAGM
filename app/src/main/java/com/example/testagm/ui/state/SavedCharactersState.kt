package com.example.testagm.ui.state

import com.example.testagm.domain.model.Character

/**
 * Representa el estado de la pantalla o flujo que maneja los personajes guardados.
 * @property characters Lista de personajes que han sido guardados por el usuario.
 * @property isLoading Indicador de si se están cargando los personajes guardados.
 * @property error Mensaje de error en caso de que ocurra un problema durante la carga de los personajes.
 */
data class SavedCharactersState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
