package com.historymakers.gymshark.data.datasource

import com.historymakers.gymshark.core.common.AppError
import com.historymakers.gymshark.core.common.DataResult
import com.historymakers.gymshark.core.mapper.toNetworkError
import com.historymakers.gymshark.data.api.ApiService
import com.historymakers.gymshark.data.model.ProductResponseDto
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {
    override suspend fun getProducts(): DataResult<ProductResponseDto, AppError.Network> {
        return try {
            val response = apiService.getProducts()
            DataResult.Success(response)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            DataResult.Error(e.toNetworkError())
        }
    }
}