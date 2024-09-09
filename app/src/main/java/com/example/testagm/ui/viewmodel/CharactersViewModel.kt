package com.example.testagm.ui.viewmodel

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testagm.common.Result
import com.example.testagm.domain.model.Character
import com.example.testagm.domain.repository.CharactersRepository
import com.example.testagm.ui.state.CharactersState
import com.example.testagm.ui.state.SavedCharactersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository
) : ViewModel() {
    // Estado mutable para los personajes de la API
    private val _charactersState = MutableStateFlow(CharactersState())
    val charactersState: StateFlow<CharactersState> = _charactersState

    // Estado mutable para los personajes guardados
    private val _savedCharactersState = MutableStateFlow(SavedCharactersState())
    val savedCharactersState: StateFlow<SavedCharactersState> = _savedCharactersState

    // Mensajes para mostrar mensaje de insercion en toast
    private val _insertToastMessage = MutableSharedFlow<String>()
    val insertToastMessage: SharedFlow<String> = _insertToastMessage

    /**
     * Inicia la carga de personajes desde la API.
     */
    fun loadCharacters() {
        viewModelScope.launch {
            // Observa cambios en la información de la página actual y carga mas personajes
            snapshotFlow { _charactersState.value.info }.distinctUntilChanged().collect { info ->
                fetchCharactersEndpoint(info?.next ?: 1)
            }
        }
    }

    /**
     * Obtiene los personajes de la API para la página especificada y actualiza el estado.
     * @param page Número de página para la solicitud de personajes.
     */
    private fun fetchCharactersEndpoint(page: Int) {
        viewModelScope.launch {
            charactersRepository.fetchCharactersEndpoint(page).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _charactersState.update { state ->
                            state.copy(
                                info = result.data.info.toDomain(),
                                results = _charactersState.value.results.plus(
                                    result.data.results.map { it.toDomain() }
                                ).distinctBy { it.id },
                                isLoading = false,
                                error = null
                            )
                        }
                    }

                    is Result.Error -> {
                        _charactersState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }

                    is Result.Loading -> {
                        _charactersState.update { state ->
                            state.copy(
                                isLoading = true,
                                error = null
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Carga los personajes guardados desde la base de datos y actualiza el estado.
     */
    fun loadSavedCharacters() {
        viewModelScope.launch {
            charactersRepository.loadSavedCharacters().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _savedCharactersState.update { state ->
                            state.copy(
                                characters = result.data.map { it.toDomain() },
                                isLoading = false,
                                error = null
                            )
                        }
                    }

                    is Result.Error -> {
                        _savedCharactersState.update { state ->
                            state.copy(
                                characters = emptyList(),
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }

                    is Result.Loading -> {
                        _savedCharactersState.update { state ->
                            state.copy(
                                characters = emptyList(),
                                isLoading = true,
                                error = null
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Guarda un personaje en la base de datos.
     * @param character Personaje a guardar.
     */
    fun saveCharacter(character: Character) {
        viewModelScope.launch {
            try {
                charactersRepository.saveCharacter(character.toEntity())
                _insertToastMessage.emit("Personaje guardado!")
            } catch (ise: IllegalStateException) {
                _insertToastMessage.emit(ise.message ?: "Ocurrió un error al guardar")
            } catch (exception: Exception) {
                _insertToastMessage.emit("Ocurrió un error al guardar")
            }
        }
    }
}