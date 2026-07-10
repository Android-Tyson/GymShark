package com.historymakers.gymshark.domain.model

data class Product(
    val id: Long,
    val title: String,
    val sku: String,
    val handle: String,
    val colour: String,
    val priceInPence: Int,
    val labels: List<String>,
    val inStock: Boolean,
    val imageUrl: String,
    val heroImageUrls: List<String>,
    val type: String,
    val gender: List<String>,
    val sizeInStock: List<String>,
    val availableSizes: List<String>,
    val description: String
)
