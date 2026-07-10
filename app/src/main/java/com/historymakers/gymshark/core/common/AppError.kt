package com.historymakers.gymshark.core.common

sealed interface AppError {
    sealed interface Network : AppError {
        data object NoInternet : Network
        data object Timeout : Network
        data object Serialization : Network

        data object IOException : Network
        data object Unknown : Network

        data class Http(
            val code: Int,
            val message: String
        ) : Network
    }

    sealed interface Local : AppError {
        data object DatabaseError : Local
        data object DiskFull : Local
        data object Unknown : Local
    }
}