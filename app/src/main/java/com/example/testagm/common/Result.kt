package com.example.testagm.common

/**
 * Representa el resultado de una operación que puede ser exitosa, fallida o estar en proceso de carga.
 * @param T El tipo de dato que se espera en caso de éxito.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String? = null, val exception: Exception? = null) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}