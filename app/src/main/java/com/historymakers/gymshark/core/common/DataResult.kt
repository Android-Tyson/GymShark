package com.historymakers.gymshark.core.common

sealed interface DataResult<out T, out E> {
    data class Success<out T>(val data: T) : DataResult<T, Nothing>
    data class Error<out E>(val error: E) : DataResult<Nothing, E>
}