package com.historymakers.gymshark.data.datasource

import com.historymakers.gymshark.data.local.dao.ProductDao
import com.historymakers.gymshark.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val productDao: ProductDao
): LocalDataSource {
    override fun observeProducts(): Flow<List<ProductEntity>> {
        return productDao.observeProducts()
    }

    override fun observeProduct(id: Long): Flow<ProductEntity?> {
        return productDao.observeProduct(id)
    }

    override suspend fun replaceProducts(products: List<ProductEntity>) {
        productDao.replaceAll(products)
    }

}