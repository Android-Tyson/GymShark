package com.historymakers.gymshark.domain.repo

import com.historymakers.gymshark.core.common.AppError
import com.historymakers.gymshark.core.common.DataResult
import com.historymakers.gymshark.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeProducts(): Flow<List<Product>>

    fun observeProduct(id: Long): Flow<Product?>

    suspend fun refreshProducts(): DataResult<Unit, AppError>
}