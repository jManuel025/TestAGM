package com.example.testagm.data.remote

import com.example.testagm.common.Result
import com.example.testagm.data.remote.api.RickAndMortyAPI
import com.example.testagm.data.remote.model.CharactersEndpointDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: RickAndMortyAPI
) {
    /**
     * Realiza una solicitud para obtener la lista de personajes desde el endpoint de la API.
     * Utiliza un flujo para emitir los resultados de la operación, gestionando estados de carga, éxito y error.
     * @param page Número de la página para la paginación de los resultados.
     * @return Un `Flow` que emite objetos `Result` que pueden ser de tipo `Loading`, `Success` o `Error`.
     */
    fun fetchCharactersEndpoint(page: Int): Flow<Result<CharactersEndpointDTO>> {
        return flow {
            emit(Result.Loading)
            try {
                val response: CharactersEndpointDTO = api.fetchCharactersEndpoint(page)
                emit(Result.Success(response))
            } catch (exception: Exception) {
                val message = if (exception is UnknownHostException) {
                    "No se puede conectar. Verifica tu conexión a internet."
                } else {
                    "Ocurrió un error inesperado. Inténtalo de nuevo más tarde."
                }
                emit(Result.Error(message = message, exception = exception))
            }
        }.flowOn(Dispatchers.IO)
    }
}