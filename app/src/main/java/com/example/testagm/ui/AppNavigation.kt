package com.example.testagm.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testagm.common.Routes
import com.example.testagm.ui.ui.screens.CharactersRoute
import com.example.testagm.ui.ui.screens.SavedCharactersRoute
import com.example.testagm.ui.viewmodel.CharactersViewModel

/**
 * Composable que configura y maneja la navegación en la aplicación.
 * Define las rutas de navegación y las pantallas asociadas
 */
@Composable
fun AppNavigation() {
    val navigationController = rememberNavController()
    val viewModel: CharactersViewModel = hiltViewModel()

    NavHost(
        navController = navigationController,
        startDestination = Routes.CharactersRoute.route
    ) {
        // Define la pantalla de personajes de la api
        composable(route = Routes.CharactersRoute.route) {
            CharactersRoute(navigationController, viewModel)
        }
        // Define la pantalla de personajes guardados
        composable(route = Routes.SavedCharactersRoute.route) {
            SavedCharactersRoute(navigationController, viewModel)
        }
    }
}