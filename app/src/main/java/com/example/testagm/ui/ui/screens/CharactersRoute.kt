@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.testagm.ui.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.testagm.R
import com.example.testagm.common.PlaceholderStateType
import com.example.testagm.common.Routes
import com.example.testagm.domain.model.Character
import com.example.testagm.ui.state.CharactersState
import com.example.testagm.ui.ui.components.CharacterItem
import com.example.testagm.ui.ui.components.LoadingComponent
import com.example.testagm.ui.ui.components.PlaceholderState
import com.example.testagm.ui.viewmodel.CharactersViewModel

/**
 * Ruta principal para la pantalla de personajes, maneja la navegación y la comunicación con el ViewModel para obtener y actualizar el estado de la UI.
 * @param navController Controlador de navegación para manejar las rutas.
 * @param viewModel El ViewModel que gestiona el estado y las acciones relacionadas con los personajes obtenidos del api.
 */
@Composable
fun CharactersRoute(
    navController: NavHostController,
    viewModel: CharactersViewModel
) {
    val state by viewModel.charactersState.collectAsState()
    val context = LocalContext.current
    var toast: Toast? = null

    // Muestra un toast para informar que se guardo un personaje
    LaunchedEffect(Unit) {
        viewModel.insertToastMessage.collect {
            toast?.cancel()  // Cancela el Toast anterior si existe
            toast = Toast.makeText(context, it, Toast.LENGTH_SHORT)
            toast?.show()
        }
    }

    CharactersScreen(
        state = state,
        onLoadMore = { viewModel.loadCharacters() },
        onSaveCharacter = { viewModel.saveCharacter(it) },
        onSavedCharactersClick = {
            navController.navigate(Routes.SavedCharactersRoute.route) {
                launchSingleTop = true
            }
        }
    )
}

/**
 * Composable que define la estructura de la pantalla de personajes,
 * @param state Estado actual de la pantalla, proporcionado por el ViewModel.
 * @param onLoadMore Callback para cargar más personajes cuando se llega al final de la lista.
 * @param onSaveCharacter Callback para guardar un personaje.
 * @param onSavedCharactersClick Callback para navegar a la pantalla de personajes guardados.
 */
@Composable
fun CharactersScreen(
    state: CharactersState,
    onLoadMore: () -> Unit,
    onSaveCharacter: (Character) -> Unit,
    onSavedCharactersClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CharactersTopAppBar { onSavedCharactersClick() }
        },
        content = {
            CharactersContent(
                paddingValues = it,
                state = state,
                onLoadNextPage = { onLoadMore() },
                onSaveCharacter = { character -> onSaveCharacter(character) }
            )
        }
    )
}

@Composable
private fun CharactersTopAppBar(onSavedCharactersClick: () -> Unit) {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Rick and Morty logo",
                modifier = Modifier.height(60.dp)
            )
        },
        actions = {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmarks),
                    contentDescription = "Saved characters",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onSavedCharactersClick() }
                )
            }
        }
    )
}

/**
 * Composable que representa el contenido principal de la pantalla de personajes, la lógica para manejar la carga de más personajes y diferentes estados de la UI.
 * @param paddingValues Valores de padding para respetar el espacio ocupado por la barra superior.
 * @param state Estado actual de la pantalla de personajes.
 * @param onLoadNextPage Callback para cargar más personajes cuando se alcanza el final de la lista.
 * @param onSaveCharacter Callback para guardar un personaje.
 */
@Composable
fun CharactersContent(
    paddingValues: PaddingValues,
    state: CharactersState,
    onLoadNextPage: () -> Unit,
    onSaveCharacter: (Character) -> Unit
) {
    val listState = rememberLazyListState()

    // Efecto lanzado para detectar cuando se alcanza el final de la lista
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }.collect {
            if (hasReachedEnd(listState) && !state.isLoading) onLoadNextPage()
        }
    }

    // Muestra un indicador de carga si está cargando y no hay resultados aún
    if (state.isLoading && state.results.isEmpty()) {
        LoadingComponent(paddingValues = paddingValues)
    }

    // Muestra un estado de error si hay un mensaje de error y no hay resultados
    if (state.error != null && state.results.isEmpty()) {
        PlaceholderState(
            type = PlaceholderStateType.ERROR,
            message = state.error
        ) { onLoadNextPage() }
    }

    // Muestra un estado vacío si no hay resultados y no está cargando ni hay error
    if (state.results.isEmpty() && !state.isLoading && state.error == null) {
        PlaceholderState(
            type = PlaceholderStateType.EMPTY,
            message = stringResource(id = R.string.common_empty_results)
        ) { onLoadNextPage() }
    }

    // Muestra la lista de personajes si hay resultados
    if (state.results.isNotEmpty()) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(paddingValues),
        ) {
            items(state.results) {
                CharacterItem(character = it) { onSaveCharacter(it) }
            }
            // Muestra un indicador de carga al final de la lista si sigue cargando
            if (state.isLoading) item { LoadingItem() }
            // Muestra un elemento de error al final de la lista si ocurre un error
            if (state.error != null) item { ErrorItem(message = state.error) { onLoadNextPage() } }
        }
    }
}

@Composable
private fun LoadingItem() {
    Box(modifier = Modifier.fillMaxWidth()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun ErrorItem(message: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        TextButton(onClick = { onClick() }) {
            Text(text = stringResource(id = R.string.button_retry))
        }
    }
}

/**
 * Función que determina si la lista ha alcanzado el final visible.
 * @param listState Estado de la lista.
 * @return True si se ha alcanzado el final de la lista, de lo contrario False.
 */
private fun hasReachedEnd(listState: LazyListState): Boolean {
    val visibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
    val totalItemCount = listState.layoutInfo.totalItemsCount
    return visibleItemIndex >= totalItemCount - 1
}
