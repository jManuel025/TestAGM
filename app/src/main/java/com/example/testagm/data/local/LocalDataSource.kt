package com.example.testagm.data.local

import com.example.testagm.common.Result
import com.example.testagm.data.local.model.CharacterEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val realm: Realm
) {
    /**
     * Carga los personajes guardados desde la base de datos local.
     * Utiliza un flujo para emitir los resultados de la operación, gestionando estados de carga, éxito y error.
     * @return Un `Flow` que emite objetos `Result`, que pueden ser de tipo `Loading`, `Success` o `Error`.
     */
    fun loadSavedCharacters(): Flow<Result<List<CharacterEntity>>> {
        return flow {
            emit(Result.Loading)
            try {
                val results = realm.query(CharacterEntity::class).find()
                emit(Result.Success(results.map { it }))
            } catch (exception: Exception) {
                emit(
                    Result.Error(
                        message = "Ocurrió un error inesperado. Inténtalo de nuevo más tarde.",
                        exception = exception
                    )
                )
            }
        }
    }

    /**
     * Guarda un personaje en la base de datos local.
     * Lanza una excepción, si ya esta guardado (segun nombre)
     * @param character El personaje a guardar en la base de datos.
     * @throws IllegalStateException Si el personaje ya está guardado.
     */
    suspend fun saveCharacter(character: CharacterEntity) {
        realm.write {
            val existing = query<CharacterEntity>("name == $0", character.name).find().firstOrNull()
            if (existing == null) {
                copyToRealm(character)
            } else {
                throw IllegalStateException("El personaje ya esta guardado")
            }
        }
    }
}