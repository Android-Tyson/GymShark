package com.historymakers.gymshark.data.datasource

import com.historymakers.gymshark.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun observeProducts(): Flow<List<ProductEntity>>

    fun observeProduct(id: Long): Flow<ProductEntity?>

    suspend fun replaceProducts(products: List<ProductEntity>)

}