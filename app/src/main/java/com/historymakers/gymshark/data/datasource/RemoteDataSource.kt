package com.historymakers.gymshark.data.datasource

import com.historymakers.gymshark.core.common.AppError
import com.historymakers.gymshark.core.common.DataResult
import com.historymakers.gymshark.data.model.ProductResponseDto

interface RemoteDataSource {
    suspend fun getProducts(): DataResult<ProductResponseDto, AppError.Network>
}