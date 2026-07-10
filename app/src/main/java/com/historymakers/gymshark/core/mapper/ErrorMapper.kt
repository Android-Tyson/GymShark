package com.historymakers.gymshark.core.mapper

import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import com.google.gson.JsonParseException
import com.historymakers.gymshark.core.common.AppError
import com.historymakers.gymshark.core.common.AppError.Local
import com.historymakers.gymshark.core.common.AppError.Network
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toNetworkError(): Network {
    return when (this) {
        is UnknownHostException -> Network.NoInternet
        is SocketTimeoutException -> Network.Timeout
        is JsonParseException -> Network.Serialization
        is HttpException -> Network.Http(
            code = code(),
            message = message()
        )
        is IOException -> Network.IOException
        else -> Network.Unknown
    }
}

fun Throwable.toLocalError(): Local {
    return when(this) {
        is SQLiteFullException -> Local.DiskFull
        is SQLiteException -> Local.DatabaseError

        else -> AppError.Local.Unknown
    }
}