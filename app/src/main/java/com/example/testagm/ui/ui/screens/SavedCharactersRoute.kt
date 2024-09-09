@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.testagm.ui.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.testagm.R
import com.example.testagm.common.PlaceholderStateType
import com.example.testagm.ui.state.SavedCharactersState
import com.example.testagm.ui.ui.components.LoadingComponent
import com.example.testagm.ui.ui.components.PlaceholderState
import com.example.testagm.ui.ui.components.SavedCharacterItem
import com.example.testagm.ui.viewmodel.CharactersViewModel

/**
 * Ruta para mostrar los personajes guardados, y maneja el estado de la vista.
 * @param navController Controlador de navegación para manejar las rutas.
 * @param viewModel ViewModel que proporciona el estado y las acciones relacionadas con los personajes guardados
 */
@Composable
fun SavedCharactersRoute(
    navController: NavHostController,
    viewModel: CharactersViewModel
) {
    val state by viewModel.savedCharactersState.collectAsState()

    // Carga los personajes guardados cuando se inicializa el composable
    LaunchedEffect(Unit) {
        viewModel.loadSavedCharacters()
    }

    SavedCharactersScreen(
        state = state,
        onClose = { navController.popBackStack() }
    )
}

/**
 * Composable que muestra la pantalla de personajes guardados
 * @param state El estado actual de los personajes guardados.
 * @param onClose Función que se ejecuta cuando se cierra la pantalla.
 */
@Composable
fun SavedCharactersScreen(
    state: SavedCharactersState,
    onClose: () -> Unit,
) {
    Scaffold(
        topBar = {
            SavedCharactersTopAppBar { onClose() }
        },
        content = {
            SavedCharactersContent(
                paddingValues = it,
                state = state
            )
        }
    )
}

@Composable
fun SavedCharactersTopAppBar(onClose: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.common_title_saved)) },
        navigationIcon = {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .clickable { onClose() }
                    .padding(16.dp)
            )
        }
    )
}

/**
 * Composable que muestra el contenido de la pantalla de personajes guardados, incluyendo el estado de carga, errores, y lista de personajes.
 * @param paddingValues Espaciado que se aplicará alrededor del contenido. Esto asegura que el contenido respete el diseño general.
 * @param state El estado actual de los personajes guardados.
 */
@Composable
fun SavedCharactersContent(
    paddingValues: PaddingValues,
    state: SavedCharactersState
) {
    // Muestra un indicador de carga si el estado indica que se está cargando
    if (state.isLoading) {
        LoadingComponent(paddingValues = paddingValues)
    }

    // Muestra un mensaje de error si existe
    if (state.error != null) {
        PlaceholderState(
            type = PlaceholderStateType.ERROR,
            message = state.error
        )
    }

    // Muestra un mensaje de estado vacío si no hay personajes y no hay errores ni carga
    if (state.characters.isEmpty() && !state.isLoading && state.error == null) {
        PlaceholderState(
            type = PlaceholderStateType.EMPTY,
            message = stringResource(id = R.string.common_no_characters_saved)
        )
    }

    // Muestra la lista de personajes guardados
    if (state.characters.isNotEmpty()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(state.characters) {
                SavedCharacterItem(character = it)
            }
        }
    }
}