package com.historymakers.gymshark.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.historymakers.gymshark.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY position ASC")
    fun observeProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    fun observeProduct(id: Long): Flow<ProductEntity?>

    @Upsert
    suspend fun upsertAll(products: List<ProductEntity>)

    @Query("DELETE FROM products")
    suspend fun clearProducts()

    @Transaction
    suspend fun replaceAll(products: List<ProductEntity>) {
        clearProducts()
        upsertAll(products)
    }
}