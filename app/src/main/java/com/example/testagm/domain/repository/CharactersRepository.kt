package com.example.testagm.domain.repository

import com.example.testagm.common.Result
import com.example.testagm.data.local.LocalDataSource
import com.example.testagm.data.local.model.CharacterEntity
import com.example.testagm.data.remote.RemoteDataSource
import com.example.testagm.data.remote.model.CharactersEndpointDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Maneja la obtención de datos, ya sea desde una fuente remota o local.
 * @param remoteDataSource Fuente de datos remota, utilizada para obtener personajes desde la API.
 * @param localDataSource Fuente de datos local, utilizada para cargar y guardar personajes en la base de datos local.
 */
class CharactersRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    /**
     * Obtiene personajes desde la API remota.
     * @param page El número de página para la paginación de resultados.
     * @return Un `Flow` que emite objetos `Result`, que pueden ser de tipo `Loading`, `Success` o `Error`.
     */
    fun fetchCharactersEndpoint(page: Int): Flow<Result<CharactersEndpointDTO>> {
        return remoteDataSource.fetchCharactersEndpoint(page)
    }

    /**
     * Carga los personajes guardados desde la base de datos local.
     * @return Un `Flow` que emite objetos `Result`, que pueden ser de tipo `Loading`, `Success` o `Error`.
     */
    fun loadSavedCharacters(): Flow<Result<List<CharacterEntity>>> {
        return localDataSource.loadSavedCharacters()
    }

    /**
     * Guarda un personaje en la base de datos local.
     * @param character El personaje a guardar.
     * @throws IllegalStateException Si el personaje ya está guardado.
     */
    suspend fun saveCharacter(character: CharacterEntity) {
        localDataSource.saveCharacter(character)
    }
}