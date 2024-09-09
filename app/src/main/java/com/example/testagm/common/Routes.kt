package com.example.testagm.common

/**
 * Representa las rutas de navegación en la aplicación.
 * @param route La cadena que define la ruta para la navegación.
 */
sealed class Routes(val route: String) {
    data object CharactersRoute : Routes("characters")
    data object SavedCharactersRoute : Routes("saved_characters")
}