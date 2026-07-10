package com.historymakers.gymshark.data.api

import com.historymakers.gymshark.data.model.ProductResponseDto
import retrofit2.http.GET

interface ApiService {
    @GET("training/mock-product-responses/algolia-example-payload.json")
    suspend fun getProducts(): ProductResponseDto
}