package com.github.hachimori.kointestsample.network

/**
 * A generic class that holds a response of API request
 * @param <T>
 */
sealed class ApiResponse<out T> {

    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val exception: Exception) : ApiResponse<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
