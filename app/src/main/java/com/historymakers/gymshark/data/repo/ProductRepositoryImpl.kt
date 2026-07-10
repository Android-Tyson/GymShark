package com.historymakers.gymshark.data.repo

import com.historymakers.gymshark.core.common.AppError
import com.historymakers.gymshark.core.common.DataResult
import com.historymakers.gymshark.core.mapper.toLocalError
import com.historymakers.gymshark.core.mapper.toNetworkError
import com.historymakers.gymshark.data.datasource.LocalDataSource
import com.historymakers.gymshark.data.datasource.RemoteDataSource
import com.historymakers.gymshark.data.mapper.toDomain
import com.historymakers.gymshark.data.mapper.toEntity
import com.historymakers.gymshark.domain.model.Product
import com.historymakers.gymshark.domain.repo.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ProductRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : ProductRepository {
    override fun observeProducts(): Flow<List<Product>> {
        return localDataSource
            .observeProducts()
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override fun observeProduct(id: Long): Flow<Product?> {
        return localDataSource
            .observeProduct(id)
            .map { it?.toDomain() }
    }

    override suspend fun refreshProducts(): DataResult<Unit, AppError> {
        return when (val result = remoteDataSource.getProducts()) {
            is DataResult.Success -> {
                val entities = try {
                    result.data.hits.mapIndexed { index, productDto ->
                        productDto.toEntity(position = index)
                    }
                } catch (e: CancellationException) {
                    throw e
                } catch (_: Exception) {
                    return DataResult.Error(AppError.Network.Serialization)
                }
                try {
                    localDataSource.replaceProducts(entities)
                    DataResult.Success(Unit)
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    DataResult.Error(e.toLocalError())
                }
            }

            is DataResult.Error -> {
                DataResult.Error(result.error)
            }
        }
    }
}